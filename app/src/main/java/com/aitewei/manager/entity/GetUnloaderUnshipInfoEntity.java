package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.util.List;

/**
 * 卸船机卸船情况列表
 * Created by zhangjunjie on 2018/1/21.
 */

public class GetUnloaderUnshipInfoEntity extends BaseEntity {


    /**
     * code : 1
     * data : [{"task_id":67,"cmsid":"ABB_GSU_1","unloaderName":"#1","usedTime":40.77,"unloading":1350.09,"efficiency":33.12},{"task_id":67,"cmsid":"ABB_GSU_2","unloaderName":"#2","usedTime":0,"unloading":400,"efficiency":90909.09},{"task_id":67,"cmsid":"ABB_GSU_3","unloaderName":"#3","usedTime":0.1,"unloading":365.67,"efficiency":3502.57}]
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
         * task_id : 67
         * cmsid : ABB_GSU_1
         * unloaderName : #1
         * usedTime : 40.77
         * unloading : 1350.09
         * efficiency : 33.12
         */

        private int task_id;
        private String unloaderId;
        private String unloaderName;
        private double usedTime;
        private double unloading;
        private double efficiency;

        public int getTask_id() {
            return task_id;
        }

        public void setTask_id(int task_id) {
            this.task_id = task_id;
        }

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
