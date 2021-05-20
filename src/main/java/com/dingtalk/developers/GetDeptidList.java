package com.dingtalk.developers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2DepartmentListsubidRequest;
import com.dingtalk.api.response.OapiV2DepartmentListsubidResponse;
import com.taobao.api.ApiException;

import java.util.List;

/**
 * 获取所有部门id
 */
public class GetDeptidList {
    /**
     * 私有方法——获取部门id
     * @param access_token
     * @return
     * @throws ApiException
     */
    private static String deptidList(String access_token) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsubid");
        OapiV2DepartmentListsubidRequest request = new OapiV2DepartmentListsubidRequest();
        request.setDeptId(1L);
        OapiV2DepartmentListsubidResponse response = client.execute(request,access_token);
        return response.getBody();
    }

    /**
     * 开放的获取部门id的方法
     * @param access_token
     * @return
     * @throws ApiException
     */
    public List getDeptidList(String access_token) throws ApiException {
        String data = deptidList(access_token);
        JSONObject json_data = JSON.parseObject(data);
        //获取结果
        JSONObject json_result = JSON.parseObject(json_data.get("result").toString());
        //获取结果下的deptidlist
        String dept_id_list = json_result.get("dept_id_list").toString();
        //转list
        List deptid_list  = JSONObject.parseArray(dept_id_list);
        //添加最大父类部门id
        deptid_list.add(0,"1");
        return deptid_list;
    }
}
