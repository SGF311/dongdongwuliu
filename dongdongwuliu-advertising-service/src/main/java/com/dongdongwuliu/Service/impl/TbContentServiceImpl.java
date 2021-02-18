package com.dongdongwuliu.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dongdongwuliu.Service.TbContentService;
import com.dongdongwuliu.config.IdWorker;
import com.dongdongwuliu.constant.ContentContstant;
import com.dongdongwuliu.domain.dto.TbContentDTO;
import com.dongdongwuliu.mapper.TbContentMapper;
import com.dongdongwuliu.pojo.TbContent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class TbContentServiceImpl implements TbContentService {
    @Resource
    private TbContentMapper tbContentMapper;
    @Resource
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> pageInfo(Integer pageNum, Integer pageSize) {
        QueryWrapper<TbContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort_order");
        Page<TbContent> page = new Page<>(pageNum, pageSize);
        IPage<TbContent> tUserIPage = tbContentMapper.selectPage(page, queryWrapper);
        long pages = tUserIPage.getPages();//pages
        List<TbContent> records = tUserIPage.getRecords();
        String s = JSONObject.toJSONString(records);

        List list = JSONObject.parseObject(s, List.class);//shuju

        long current = tUserIPage.getCurrent();//pageNum
        long size = tUserIPage.getSize();//pageSize
        long total = tUserIPage.getTotal();//total

        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        map.put("total", total);
        map.put("pageNumber", current);
        map.put("pageSize", size);
        map.put("pages", pages);

        return map;
    }

    @Override
    public void insert(TbContentDTO tbContent) {
        try {
            long id = idWorker.nextId();
            tbContent.setId(id);
            TbContent tbContent1 = new TbContent();
            BeanUtils.copyProperties(tbContent, tbContent1);
            tbContentMapper.insert(tbContent1);
            redisTemplate.opsForHash().delete(ContentContstant.CONTENT_KEY + tbContent.getCategoryId(), tbContent.getCategoryId());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            TbContent tbContent = tbContentMapper.selectById(id);
            tbContentMapper.deleteById(id);
            redisTemplate.opsForHash().delete(ContentContstant.CONTENT_KEY + tbContent.getCategoryId(), tbContent.getCategoryId());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteBatch(Long[] ids) {
        try {

            List<Long> list = (List<Long>) Arrays.asList(ids);//基本数据...
            tbContentMapper.deleteBatchIds(list);
            List<TbContent> tbContents = tbContentMapper.selectBatchIds(list);
            for (TbContent tbContent : tbContents) {
                redisTemplate.opsForHash().delete(ContentContstant.CONTENT_KEY + tbContent.getCategoryId(), tbContent.getCategoryId());
            }

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public TbContentDTO toUpdate(Long id) {
        try {
            TbContent tbContent = tbContentMapper.selectById(id);
            TbContentDTO tbContentDTO = new TbContentDTO();
            BeanUtils.copyProperties(tbContent, tbContentDTO);
            return tbContentDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void update(TbContentDTO tbContentDTO) {
        try {
            TbContent tbContent1 = new TbContent();
            BeanUtils.copyProperties(tbContentDTO, tbContent1);
            tbContentMapper.updateById(tbContent1);
            redisTemplate.opsForHash().delete(ContentContstant.CONTENT_KEY + tbContentDTO.getCategoryId(), tbContentDTO.getCategoryId());

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void upDown(Long id, Integer flag) {
        try {
            //获取当前数据
            TbContent tbContent = tbContentMapper.selectById(id);
            //判断用户需求  上移 下移
            TbContent upDown = null;
            if (flag == 1) {//上移
                //查询上一条数据
                upDown = tbContentMapper.selectByUp(tbContent.getSortOrder());
            } else {//下移
                //查询下一条数据
                upDown = tbContentMapper.selectByDown(tbContent.getSortOrder());
            }
            //交换数据
            int i = tbContent.getSortOrder();
            tbContent.setSortOrder(upDown.getSortOrder());
            upDown.setSortOrder(i);

            //修改数据库
            int i1 = tbContentMapper.updateById(tbContent);
            int i2 = tbContentMapper.updateById(upDown);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<TbContentDTO> getContentByContentCategoryId(Long contentCategoryId) {
        List<TbContentDTO> list = new ArrayList<>();
        //查看redis中是否有数据
        //如果有 直接从redis中取
        if (redisTemplate.opsForHash().hasKey(ContentContstant.CONTENT_KEY + contentCategoryId, contentCategoryId)) {
             list = (List<TbContentDTO>) redisTemplate.opsForHash().get(ContentContstant.CONTENT_KEY + contentCategoryId, contentCategoryId);
            if (list == null) {
                list = new ArrayList<>();
            }

        } else {
            //如果没有 从数据库中取
            QueryWrapper<TbContent> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByAsc("sort_order");
            queryWrapper.eq("category_id", contentCategoryId);
            queryWrapper.eq("status", 1);
            List<TbContent> tbContents = tbContentMapper.selectList(queryWrapper);
            String s = JSONObject.toJSONString(tbContents);
            list = JSONObject.parseObject(s, List.class);
            redisTemplate.opsForHash().put(ContentContstant.CONTENT_KEY + contentCategoryId, contentCategoryId, list);
        }
        return list;
    }
}
