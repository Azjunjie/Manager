package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.util.List;

public class GetUnloaderPlcParamDetailListEntity extends BaseEntity {


    /**
     * code : 1
     * data : [{"unloaderId":"#1","unloaderName":"1号卸船机","deliveryRate":10,"doumenOpeningDegree":20,"hopperLoad":30,"updateTime":"2020-12-04 10:14:06","systemState":"在线"},{"unloaderId":"#2","unloaderName":"2号卸船机","deliveryRate":20,"doumenOpeningDegree":40,"hopperLoad":60,"updateTime":"2020-11-29 10:30:06","systemState":"在线"},{"unloaderId":"#3","unloaderName":"3号卸船机","deliveryRate":0,"doumenOpeningDegree":0,"hopperLoad":0,"updateTime":"2020-11-17 12:14:06","systemState":"在线"},{"unloaderId":"#4","unloaderName":"4号卸船机","deliveryRate":8,"doumenOpeningDegree":6,"hopperLoad":10,"updateTime":"2020-11-12 10:14:06","systemState":"在线"},{"unloaderId":"#5","unloaderName":"5号卸船机","deliveryRate":0,"doumenOpeningDegree":0,"hopperLoad":0,"updateTime":"2020-12-02 10:56:06","systemState":"在线"},{"unloaderId":"#6","unloaderName":"6号卸船机","deliveryRate":20,"doumenOpeningDegree":30,"hopperLoad":0,"updateTime":"2020-11-30 12:59:06","systemState":"在线"}]
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
         * unloaderId : #1
         * unloaderName : 1号卸船机
         * deliveryRate : 10
         * doumenOpeningDegree : 20
         * hopperLoad : 30
         * updateTime : 2020-12-04 10:14:06
         * systemState : 在线
         */

        private String unloaderId;
        private String unloaderName;
        private double deliveryRate;
        private double doumenOpeningDegree;
        private double hopperLoad;
        private String updateTime;
        private String systemState;

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

        public double getDeliveryRate() {
            return deliveryRate;
        }

        public void setDeliveryRate(double deliveryRate) {
            this.deliveryRate = deliveryRate;
        }

        public double getDoumenOpeningDegree() {
            return doumenOpeningDegree;
        }

        public void setDoumenOpeningDegree(double doumenOpeningDegree) {
            this.doumenOpeningDegree = doumenOpeningDegree;
        }

        public double getHopperLoad() {
            return hopperLoad;
        }

        public void setHopperLoad(double hopperLoad) {
            this.hopperLoad = hopperLoad;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getSystemState() {
            return systemState;
        }

        public void setSystemState(String systemState) {
            this.systemState = systemState;
        }
    }
}
