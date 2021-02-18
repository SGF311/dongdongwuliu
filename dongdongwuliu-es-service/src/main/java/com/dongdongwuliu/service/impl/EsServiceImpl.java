package com.dongdongwuliu.service.impl;

import com.alibaba.fastjson.JSON;
import com.dongdongwuliu.constant.EsConstants;
import com.dongdongwuliu.domain.dto.TbOrderDTO;
import com.dongdongwuliu.domain.vo.TbOrderVO;
import com.dongdongwuliu.service.EsService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 你哥
 * @Date: 2021/2/3 16:00
 * @Description:
 */
@Component
public class EsServiceImpl implements EsService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public List<TbOrderVO> selectOrderEs(String phoneOrOrderId,String orderId,String username) throws IOException {
        //根据登录用户查询数据，然后在修改
        SearchRequest searchRequest = new SearchRequest("order");

        BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
        mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档

        mustQuery.must(QueryBuilders.matchPhraseQuery("username", username));//添加第2条must的条件 title字段必须为【时间简史】
        // ↑ 放入筛选条件(termQuery为精确搜索，大小写敏感且不支持*) 实验发现matchPhraseQuery可对中文精确匹配term  [matchPhraseQuery可对中文精确匹配]
//        mustQuery.must(QueryBuilders.matchQuery("orderId", phoneOrOrderId)); // 添加第3条must的条件
        if (phoneOrOrderId != null){
            mustQuery.must(QueryBuilders.matchQuery("senderMobile", phoneOrOrderId)); // 添加第4条must的条件
        }
        if (orderId != null){
            mustQuery.must(QueryBuilders.matchQuery("orderId", orderId)); // 添加第4条must的条件
        }

        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(mustQuery);
        searchSourceBuilder.fetchSource(new String[]{"orderId", "cargoName", "status","receiverAreaName"
                ,"receiverDetailedAddress","senderAreaName","senderDetailedAddress","visitTime","amountPayable","username"}, new String[]{});
        // 向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);

        // 执行搜索,向ES发起http请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        List<TbOrderVO> orderVOList = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            // 文档的主键
            String id = hit.getId();
            // 源文档内容
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            TbOrderVO tbOrderVO = JSON.parseObject(JSON.toJSONString(sourceAsMap), TbOrderVO.class);
            orderVOList.add(tbOrderVO);
        }
        return orderVOList;
    }

    @Override
    public void updateStatusByUsernameByOrderId(String username, String orderId) throws IOException {
        //根据登录用户查询数据，然后在修改
        SearchRequest searchRequest = new SearchRequest("order");

        BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
        mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档

        mustQuery.must(QueryBuilders.matchPhraseQuery("username", username));//添加第2条must的条件 title字段必须为【时间简史】
        // ↑ 放入筛选条件(termQuery为精确搜索，大小写敏感且不支持*) 实验发现matchPhraseQuery可对中文精确匹配term  [matchPhraseQuery可对中文精确匹配]
        mustQuery.must(QueryBuilders.matchQuery("orderId", orderId)); // 添加第3条must的条件

        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(mustQuery);
        searchSourceBuilder.fetchSource(new String[]{"orderId", "cargoName", "status","receiverAreaName"
                ,"receiverDetailedAddress","senderAreaName","senderDetailedAddress","visitTime","amountPayable","username"}, new String[]{});
        // 向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);

        // 执行搜索,向ES发起http请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        List<TbOrderVO> orderVOList = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            // 文档的主键
            String id = hit.getId();
            // 源文档内容
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            TbOrderVO tbOrderVO = JSON.parseObject(JSON.toJSONString(sourceAsMap), TbOrderVO.class);
            String id1 = hit.getId();
            System.out.println("id------>>>>   "+id1);
            tbOrderVO.setStatus("3");
            System.out.println("es订单数据查询----》》"+tbOrderVO);
            // 请求对象 指定索引名称，和source 数据  //将我们的数据放入请求json
            UpdateRequest updateRequest = new UpdateRequest("order",id1);
            updateRequest.doc(JSON.toJSONString(tbOrderVO),XContentType.JSON);
            // 执行得到 response
            UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            System.out.println("出参：" + update.toString());
        }
    }

    @Override
    public List<TbOrderVO> getEsOrderByPhoneByOrderId(String senderMobile, String orderId) throws IOException, ParseException {
        SearchRequest searchRequest = new SearchRequest("order");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //matchAllQuery() : 查询所有数据
        //termQuery() : 精确查询
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("senderMobile", senderMobile, "orderId", orderId);

        searchSourceBuilder.query(multiMatchQueryBuilder);
        // 设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        //orderId : 运单号
        //cargoName : 货物名称
        //status : 物流状态
        //receiverAreaName : 寄件地址
        //receiverDetailedAddress : 寄件详细地址
        //senderAreaName : 收件地址
        //senderDetailedAddress : 收件详细地址
        //visitTime : 预计到达时间
        searchSourceBuilder.fetchSource(new String[]{"orderId", "cargoName", "status","receiverAreaName"
                ,"receiverDetailedAddress","senderAreaName","senderDetailedAddress","visitTime","amountPayable","username"}, new String[]{});
        // 向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);

//        //高亮显示
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        //设置高亮前缀
//        highlightBuilder.preTags("<span style='color:red' >");
//        //设置高亮字段
//        highlightBuilder.field("");//field ： 高亮字段
//        //设置高亮后缀
//        highlightBuilder.postTags("</span>");
//        searchSourceBuilder.highlighter(highlightBuilder);
//
//        String queryJson = searchSourceBuilder.toString();
//        System.out.println(queryJson);

        // 执行搜索,向ES发起http请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        List<TbOrderVO> orderVOList = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            // 文档的主键
            String id = hit.getId();
            // 源文档内容
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Long orderIds = (Long) sourceAsMap.get("orderId");
            String cargoName = (String) sourceAsMap.get("cargoName");
            String status = (String) sourceAsMap.get("status");
            String receiverAreaName = (String) sourceAsMap.get("receiverAreaName");
            String receiverDetailedAddress = (String) sourceAsMap.get("receiverDetailedAddress");
            String senderAreaName = (String) sourceAsMap.get("senderAreaName");
            String senderDetailedAddress = (String) sourceAsMap.get("senderDetailedAddress");
            Long visitTime = (Long) sourceAsMap.get("visitTime");
            Integer amountPayable = (Integer) sourceAsMap.get("amountPayable");
            String user = (String) sourceAsMap.get("username");
            TbOrderVO tbOrderVO = JSON.parseObject(JSON.toJSONString(sourceAsMap), TbOrderVO.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newDate = sdf.format(new Date(Long.parseLong(String.valueOf(visitTime))));     // 时间戳转换成时间
            Date time = sdf.parse(newDate);
            tbOrderVO.setOrderId(orderIds);
            tbOrderVO.setCargoName(cargoName);
            tbOrderVO.setStatus(status);
            tbOrderVO.setReceiverAreaName(receiverAreaName);
            tbOrderVO.setReceiverDetailedAddress(receiverDetailedAddress);
            tbOrderVO.setSenderAreaName(senderAreaName);
            tbOrderVO.setSenderDetailedAddress(senderDetailedAddress);
            tbOrderVO.setVisitTime(time);
            tbOrderVO.setAmountPayable(new BigDecimal(amountPayable));
            tbOrderVO.setUsername(user);
//            // 获取高亮查询的内容。如果存在，则替换原来的name
//            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//            if( highlightFields != null ){
//                HighlightField nameField = highlightFields.get("goodsName");
//                if(nameField!=null){
//                    Text[] fragments = nameField.getFragments();
//                    StringBuilder stringBuilder = new StringBuilder();
//                    for (Text str : fragments) {
//                        stringBuilder.append(str.string());
//                    }
//                    name = stringBuilder.toString();
//                    goodsEs.setGoodsName(name);
//                }
//            }
            orderVOList.add(tbOrderVO);
            System.out.println("es订单数据查询----》》"+tbOrderVO);
        }
        return orderVOList;
    }

    //使用精确查询
    @Override
    public List<TbOrderVO> getEsOrderInfo(String username) throws IOException, ParseException {
        SearchRequest searchRequest = new SearchRequest("order");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //matchAllQuery() : 查询所有数据
        //termQuery() : 精确查询
        TermQueryBuilder userId = QueryBuilders.termQuery("username", username);

        searchSourceBuilder.query(userId);
        // 设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        //orderId : 运单号
        //cargoName : 货物名称
        //status : 物流状态
        //receiverAreaName : 寄件地址
        //receiverDetailedAddress : 寄件详细地址
        //senderAreaName : 收件地址
        //senderDetailedAddress : 收件详细地址
        //visitTime : 预计到达时间
        searchSourceBuilder.fetchSource(new String[]{"orderId", "cargoName", "status","receiverAreaName"
                ,"receiverDetailedAddress","senderAreaName","senderDetailedAddress","visitTime","amountPayable","username"}, new String[]{});
        // 向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);

//        //高亮显示
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        //设置高亮前缀
//        highlightBuilder.preTags("<span style='color:red' >");
//        //设置高亮字段
//        highlightBuilder.field("");//field ： 高亮字段
//        //设置高亮后缀
//        highlightBuilder.postTags("</span>");
//        searchSourceBuilder.highlighter(highlightBuilder);
//
//        String queryJson = searchSourceBuilder.toString();
//        System.out.println(queryJson);

        // 执行搜索,向ES发起http请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        List<TbOrderVO> orderVOList = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            // 文档的主键
            String id = hit.getId();
            // 源文档内容
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Long orderId = (Long) sourceAsMap.get("orderId");
            String cargoName = (String) sourceAsMap.get("cargoName");
            String status = (String) sourceAsMap.get("status");
            String receiverAreaName = (String) sourceAsMap.get("receiverAreaName");
            String receiverDetailedAddress = (String) sourceAsMap.get("receiverDetailedAddress");
            String senderAreaName = (String) sourceAsMap.get("senderAreaName");
            String senderDetailedAddress = (String) sourceAsMap.get("senderDetailedAddress");
            Integer amountPayable = (Integer) sourceAsMap.get("amountPayable");
            Long visitTime = (Long) sourceAsMap.get("visitTime");
            String user = (String) sourceAsMap.get("username");
            TbOrderVO tbOrderVO = JSON.parseObject(JSON.toJSONString(sourceAsMap), TbOrderVO.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newDate = sdf.format(new Date(Long.parseLong(String.valueOf(visitTime))));     // 时间戳转换成时间
            Date time = sdf.parse(newDate);
            tbOrderVO.setOrderId(orderId);
            tbOrderVO.setCargoName(cargoName);
            tbOrderVO.setStatus(status);
            tbOrderVO.setReceiverAreaName(receiverAreaName);
            tbOrderVO.setReceiverDetailedAddress(receiverDetailedAddress);
            tbOrderVO.setSenderAreaName(senderAreaName);
            tbOrderVO.setSenderDetailedAddress(senderDetailedAddress);
            tbOrderVO.setVisitTime(time);
            tbOrderVO.setAmountPayable(new BigDecimal(amountPayable));
            tbOrderVO.setUsername(user);
//            // 获取高亮查询的内容。如果存在，则替换原来的name
//            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//            if( highlightFields != null ){
//                HighlightField nameField = highlightFields.get("goodsName");
//                if(nameField!=null){
//                    Text[] fragments = nameField.getFragments();
//                    StringBuilder stringBuilder = new StringBuilder();
//                    for (Text str : fragments) {
//                        stringBuilder.append(str.string());
//                    }
//                    name = stringBuilder.toString();
//                    goodsEs.setGoodsName(name);
//                }
//            }
            orderVOList.add(tbOrderVO);
            System.out.println("es订单数据查询----》》"+tbOrderVO);
        }
        return orderVOList;
    }

    @Override
    public void addEsByOrder(TbOrderDTO tbOrderDTO) throws IOException {
        // 请求对象 指定索引名称，和source 数据  //将我们的数据放入请求json
        IndexRequest indexRequest = new IndexRequest(EsConstants.ES_ORDER).source(JSON.toJSONString(tbOrderDTO), XContentType.JSON);
        // 执行得到 response
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println("出参：" + indexResponse.toString());
    }

}
