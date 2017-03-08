package com.gh.myrxretrofitdemo;

import com.gh.myrxretrofitdemo.entity.SubjectResulte;
import com.gh.myrxretrofitdemo.entity.genshang.LeaderScheduleVo;
import com.gh.rxretrofitlibrary.api.BaseResultEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author: gh
 * @description: 测试接口service-post相关
 * @date: 2017/2/27 18:07
 * @note:
 */

public interface HttpPostService {

    @GET("AppFiftyToneGraph/videoLink/{once_no}")
    Observable<BaseResultEntity<List<SubjectResulte>>> getAllVedioBys(@Query("once_no") boolean once_no);

    //测试跟上数据     BaseResultEntity中data必须改成对应的参数名....所以很麻烦
    /**
     * v2
     * 获取领队行程列表
     * 1：出行中；2：即将出行；3：已出行
     */

    String TIMESTAMP = "timestamp";//时间戳
    String SIGN = "sign";//签名
    String ACCESS_TOKEN = "access_token";//access_token

    String VER_KEY = "ver";//API版本号KEY
    String CLIENTID_KEY = "client_id";//应用的唯一ID的key

    @POST("api/schedule/getLeaderScheduleList")
    Observable<BaseResultEntity<List<LeaderScheduleVo>>> getLeaderScheduleList(@Query(ACCESS_TOKEN) String access_token,
                                                                               @Query(VER_KEY) String ver,
                                                                               @Query(CLIENTID_KEY) String client_id,
                                                                               @Query(TIMESTAMP) String timestamp,
                                                                               @Query(SIGN) String sign
    );

}
