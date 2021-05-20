package com.dingtalk.developers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;

/**
 * 获取access_token
 */
public class GetAccessToken {
    //Appkey
    private static final String APPKEY="";
    //AppSecret
    private static final String APPSECRET="";

    /**
     * 私有方法获取access_token
     * @return
     * @throws ApiException
     */
    private static String accessToken() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(APPKEY);
        request.setAppsecret(APPSECRET);
        OapiGettokenResponse response = client.execute(request);
        return response.getBody();
    }

    /**
     * 开放一个方法
     * @return
     * @throws ApiException
     */
    public String getAccessToken() throws ApiException {
        //获取请求返回值
        String body = accessToken();
        JSONObject json_body = JSON.parseObject(body);
        //获取access_token
        String access_token = json_body.get("access_token").toString();
        return access_token;
    }

    public static void main(String[] args) throws ApiException {
        String body = accessToken();
        System.out.println(body);
    }
}
