package com.dingtalk.developers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserListidRequest;
import com.dingtalk.api.response.OapiUserListidResponse;
import com.taobao.api.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取部门下所有用户id
 */
public class GetUseridList {
    private static List useridList(String access_token) throws ApiException {
        //存放用户id的集合
        List userid_list = new ArrayList();
        //获取部门id
        GetDeptidList get_deptid_list = new GetDeptidList();
        List deptid_list = get_deptid_list.getDeptidList(access_token);

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/listid");
        OapiUserListidRequest request = new OapiUserListidRequest();
        for (int i=0;i<deptid_list.size();i++){
            //获取集合中的部门id
            Object obj_deptid = deptid_list.get(i);
            Long deptid = Long.valueOf(String.valueOf(obj_deptid));
            //设置请求body
            request.setDeptId(deptid);
            OapiUserListidResponse response = client.execute(request,access_token);
            String body = response.getBody();
            JSONObject json_body = JSON.parseObject(body);
            //获取result
            JSONObject json_result = JSON.parseObject(json_body.get("result").toString());
            //获取userid_list
            List useridlist = JSONObject.parseArray(json_result.get("userid_list").toString());
            //如果用户id集合长度大于0
            if (useridlist.size()>0){
                for (Object userid:useridlist) {
                    //存放到集合中
                    userid_list.add(userid);
                }
            }
        }
        return userid_list;
    }

    /**
     * 开放一个接口
     * @return
     * @throws ApiException
     */
    public List getUseridList(String access_token) throws ApiException {
        return useridList(access_token);
    }
}
