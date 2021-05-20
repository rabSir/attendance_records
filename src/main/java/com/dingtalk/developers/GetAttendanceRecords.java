package com.dingtalk.developers;

import com.alibaba.fastjson.support.hsf.HSFJSONUtils;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiAttendanceListRecordRequest;
import com.dingtalk.api.response.OapiAttendanceListRecordResponse;
import com.taobao.api.ApiException;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 获取用户的打卡记录
 */
public class GetAttendanceRecords {
    private static String AttendanceRecords() throws ApiException {
        //获取access_token
        GetAccessToken get_access_token = new GetAccessToken();
        String access_token = get_access_token.getAccessToken();
        System.out.println(access_token);
        //获取用户ids
        GetUseridList get_userid_list = new GetUseridList();
        List userid_list = get_userid_list.getUseridList(access_token);

        for (Object userid:userid_list) {
            System.out.println(userid);
        }

        //获取当天的开始时间和结束时间
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH,0);

        //当天的开始时间 yyyy:MM:dd 00:00:00
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date dayStart = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startStr = simpleDateFormat.format(dayStart);
        System.out.println(startStr);

        //当天的结束时间 yyyy:MM:dd 23:59:59
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        Date dayEnd = calendar.getTime();
        String endStr = simpleDateFormat.format(dayEnd);
        System.out.println(endStr);

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/attendance/listRecord");
        OapiAttendanceListRecordRequest request = new OapiAttendanceListRecordRequest();
        request.setUserIds(userid_list);
        request.setCheckDateFrom(startStr);
        request.setCheckDateTo(endStr);
        OapiAttendanceListRecordResponse response = client.execute(request,access_token);
        System.out.println(response.getBody());
        return response.getBody();
    }

    public static void main(String[] args) throws ApiException {
        AttendanceRecords();
    }
}
