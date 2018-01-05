package com.aitewei.manager.retrofit;

import com.aitewei.manager.base.BaseEntity;
import com.aitewei.manager.entity.CabinPositionEntity;
import com.aitewei.manager.entity.GetPrivilegeEntity;
import com.aitewei.manager.entity.GetUploaderDetailEntity;
import com.aitewei.manager.entity.ShipBaseInfoEntity;
import com.aitewei.manager.entity.ShipCabinDetailEntity;
import com.aitewei.manager.entity.ShipCabinListEntity;
import com.aitewei.manager.entity.ShipGoodsDetailEntity;
import com.aitewei.manager.entity.ShipListEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 请求网络的API接口类
 * Created by zjj on 2017/10/30.
 */

public interface ApiService {
//        String BASEURL = "http://47.95.205.78/wcms/api/http/";//测试环境
    String BASEURL = "http://60.2.201.70:8086/wcms/api/http/";//生产环境

    /**
     * 用户登录接口
     */
    @FormUrlEncoded
    @POST("user/doLogin")
    Observable<BaseEntity> doLogin(@Field("json") String json);

    /**
     * 获取权限
     */
    @FormUrlEncoded
    @POST("user/doGetPrivileges")
    Observable<GetPrivilegeEntity> doGetPrivileges(@Field("json") String json);

    /**
     * 取船舶作业列表
     * <p>
     * status 船舶状态（01|作业船舶，02|预靠船舶，03|离港船舶）
     */
    @FormUrlEncoded
    @POST("ship/doGetShipList")
    Observable<ShipListEntity> doGetShipList(@Field("json") String json);

    /**
     * 获取船舶基本信息
     */
    @FormUrlEncoded
    @POST("ship/doGetShipDetail")
    Observable<ShipBaseInfoEntity> doGetShipDetail(@Field("json") String json);

    /**
     * 获取船舶舱位的位置信息
     */
    @FormUrlEncoded
    @POST("ship/doGetShipPosition")
    Observable<CabinPositionEntity> doGetShipPosition(@Field("json") String json);

    /**
     * 设置舱位
     */
    @FormUrlEncoded
    @POST("ship/doSetCabinPosition")
    Observable<BaseEntity> doSetCabinPosition(@Field("json") String json);

    /**
     * 获取船舱信息
     */
    @FormUrlEncoded
    @POST("ship/doGetCabinList")
    Observable<ShipCabinListEntity> doGetCabinList(@Field("json") String json);

    /**
     * 船舱卸货详情
     */
    @FormUrlEncoded
    @POST("ship/doGetCabinDetail")
    Observable<ShipCabinDetailEntity> doGetCabinDetail(@Field("json") String json);

    /**
     * 船舱卸货详情
     */
    @FormUrlEncoded
    @POST("ship/doGetUnloaderDetail")
    Observable<GetUploaderDetailEntity> doGetUnloaderDetail(@Field("json") String json);

    /**
     * 查询船舱货物信息
     */
    @FormUrlEncoded
    @POST("ship/doGetCargoDetail")
    Observable<ShipGoodsDetailEntity> doGetCargoDetail(@Field("json") String json);

    /**
     * 修改船舶状态
     */
    @FormUrlEncoded
    @POST("ship/doSetShipStatus")
    Observable<BaseEntity> doSetShipStatus(@Field("json") String json);

    /**
     * 设置船舱状态
     */
    @FormUrlEncoded
    @POST("ship/doSetCabinStatus")
    Observable<BaseEntity> doSetCabinStatus(@Field("json") String json);

}
