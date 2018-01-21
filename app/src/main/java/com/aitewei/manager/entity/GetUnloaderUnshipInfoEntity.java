package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.util.List;

/**
 * 卸船机卸船情况列表
 * Created by zhangjunjie on 2018/1/21.
 */

public class GetUnloaderUnshipInfoEntity extends BaseEntity {

    /**
     * {"data": [{
     "unloaderId": "",
     "unloaderName": "",
     "usedTime": 1.01,
     "unloading": 1.11,
     "efficiency": 10
     }]}
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
         * unloaderId :
         * unloaderName :
         * usedTime : 1.01
         * unloading : 1.11
         * efficiency : 10.0
         */

        private String unloaderId;
        private String unloaderName;
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
