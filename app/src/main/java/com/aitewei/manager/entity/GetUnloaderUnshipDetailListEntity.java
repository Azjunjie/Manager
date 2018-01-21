package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.util.List;

/**
 * Created by zhangjunjie on 2018/1/21.
 */

public class GetUnloaderUnshipDetailListEntity extends BaseEntity {

    /**
     * code : 1
     * data : [{"unloaderId":"ABB_GSU_2","unloaderName":"#2","cabinId":351,"cabinNo":2,"startTime":"2018-01-21 18:06:45","endTime":"2018-01-21 19:19:41","usedTime":1.2,"unloading":400.98,"efficiency":333.3333}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * unloaderId : ABB_GSU_2
         * unloaderName : #2
         * cabinId : 351
         * cabinNo : 2
         * startTime : 2018-01-21 18:06:45
         * endTime : 2018-01-21 19:19:41
         * usedTime : 1.2
         * unloading : 400.98
         * efficiency : 333.3333
         */

        private String unloaderId;
        private String unloaderName;
        private String cabinId;
        private String cabinNo;
        private String startTime;
        private String endTime;
        private double usedTime;
        private double unloading;
        private double efficiency;

        public String getUnloaderId() {
            return unloaderId;
        }

        public void setUnloaderId(String unloaderId) {
            this.unloaderId = unloaderId;
        }

        public String getUnloaderName() {
            return unloaderName;
        }

        public void setUnloaderName(String unloaderName) {
            this.unloaderName = unloaderName;
        }

        public String getCabinId() {
            return cabinId;
        }

        public void setCabinId(String cabinId) {
            this.cabinId = cabinId;
        }

        public String getCabinNo() {
            return cabinNo;
        }

        public void setCabinNo(String cabinNo) {
            this.cabinNo = cabinNo;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public double getUsedTime() {
            return usedTime;
        }

        public void setUsedTime(double usedTime) {
            this.usedTime = usedTime;
        }

        public double getUnloading() {
            return unloading;
        }

        public void setUnloading(double unloading) {
            this.unloading = unloading;
        }

        public double getEfficiency() {
            return efficiency;
        }

        public void setEfficiency(double efficiency) {
            this.efficiency = efficiency;
        }
    }
}
