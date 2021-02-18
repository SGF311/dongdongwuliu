package com.dongdongwuliu.config;

import org.springframework.context.annotation.Configuration;

/**
 * @Auther: 你哥
 * @Date: 2020/12/23 21:16
 * @Description:
 */
@Configuration
public class AlipayConfig {

    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000116679153";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCQNtdnpFMe87LXC0YKV/nAqDQr4qlBifWok6ZA1aUIxMm66N1zoM/RlCXIYrIfIdNaoOBYWU3Uo8EEB0vu8taQraTiB6S4l3gsAUS9lg5bZMnvDn6Z7sTH+Is8WUdekzCZRqrYrnBkZ/BvbX0HM3Z6OFXUiPVJE8arwANLBHAPk2jG9nofujIBxn8i3/Rnxj8VmEdbkOAuDwGvCQA1C/ebZ2hybhGoSMmWvVPma+OKAHMDdmFkwfSCsyK9+a1yzbEmF7Xubx8TJTsCcx6SU4b33PV+hDap1Yu+UcDWkswfEsBVF1u04d5/+e1vIz/BDa93bKCdqa3SH4PRl9ymGjmXAgMBAAECggEAUQvxDJcF+6wbgs0cJK4CdypQf6VXV7TPsVihI2it4fquIHPEGLp0cKTggvgsgGCDJjipPB2cJveMftYW74fb290qf7ip8u91Is3nsuBE20oOTmIH9jyArJ1aqqTF/jmcI1fapgGKunLLZePTUxux5sgIPHRn3RTXHyi+8Qbib22gjQ8SdLVM3aT304lWnqI8tFxX+T7iubzZ4cdTZ8JCRHfyDHzGEU+QVxQwyApYBjP71hIpSrGeRzeIl2WbjNifnbxLgD6Jg1O54tuvjlYlGBLDeW6DdXLYOznz92T3n2Tlpjx+hp+Ehg0pg8PH5w6mts5FLjNsMlmHnWTpOTrcoQKBgQDTbjb5jaoZo/UWTdN5JJp57Kn9v6FV6QZv/Zp8O4MzUNFXJkRffcEFKzIKcQevx8y7q1Ltgh9W1dXyrno5JqITjknm5TPCsllN5c4cxw+LuUZ4GJIKS8QVnDhCgJ03ULDiIoUnEwVBBus0AgHcCCdbExqoJC2Apa6aOY2S92JypwKBgQCunVGqCi3VRabVWXb6gGEprueSJGYsnna0f2Y8asNP4iv7n1Cfbvniv5LvA89IoK3MI+sLpavqheEIZMDcRPfg6iYrwROlfHVlWPAHlXERQXdnFAoXYG+v2d3yH+Bql58PswLwqveDYEmbPzut5NoVjDeWAhUTD8AoQvsxkdSPkQKBgFYCK9Lw0hWEIRtlpXVOcpjOsYhMADGWjcvY+pM3EnQ5mC8/nejKm06i98RPnrTfLmXZl+6OVHlam6IRYB8HnD8gTufirV+ydam+pk4GnEhWolgQhrgxgh7YYfqLn2j1Jj+Xk1sF3clKKzwI5QDBQGFIMDveG7SNsa2DZBg5Eo9ZAoGANTMLEXJeem7pIHFMdY+tiqSWOwlJ8+gnpGB4RooEjY8L9XKZfqmyCmpxms6fVbJWoS6ESx0jnqVBZWxE8hc1KlpMXOJLWQztO354Suc19prqhQYu+OZRwNLw8vbRSch+Fqtkhuyf0nAAnQML7LQf2ZbJzLM/tnoDAoE9xOF4UHECgYBxlWjfbVBHFJvtzLH/9388dz1+EilPA9deCUGRrogZgFhlzWshl8x9FQR9JMH8sIeBie6SGk4r6MM5AXZZXYy9aISxUrH/rn1mR0yloWryb7pymPdlA9w7nLnzFT4uhzrqP1wCZeFsQbNGGCkqWA6/dtMxhXD2E2eFhnfuRDJctA==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxnhFHJQ1LGp15X9IBC5VB0OYNNOD/UxkQBH+9+G2G/WvIQhHzlnt56upoxLk8LMAC8CMJMxMz9Htu5XVaqgBtz6Gj+mOAMPI59ceexQXavdJq0JLD1ON5ip+nggzroRcYEohIN/h+LQZxmixnIc5R4PXidOKiH7em+G1ckxSpklNKsgZFf0xg/N55MgvaS6DArXb0L/XPjHnZDyH4gXbUZeIHdpcP0MH1PjJtC1whKX/g6s+8JoXP4Xm5EZxvqTxEW4mMfFHHmYVxIDX+qtUJi6PZQxUCwr8higxpxo13ULxaD0Zp5qnTDswFvSWmd26+vVkd2J7+Um/B0xxkNcjBwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://36a31x8097.wicp.vip/trade/notifyUrl";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://36a31x8097.wicp.vip/trade/returnUrl";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "E:\\";

}
