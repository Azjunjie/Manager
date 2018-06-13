package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.util.List;

/**
 * Created by zhangjunjie on 2018/1/21.
 */

public class GetUnloaderUnshipDetailListEntity extends BaseEntity {


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
         * startTime : 2018-01-21 17:51:26
         * endTime : 2018-01-21 17:52:21
         * usedTime : 0.02
         * unloading : 300.09
         * efficiency : 19607.84
         */

        private int task_id;
        private int cabinNo;
        private int cabinId;
        private String cmsid;
        private String cargoName;
        private String unloaderName;
        private String startTime;
        private String endTime;
        private double usedTime;
        private double unloading;
        private double efficiency;

        public String getCargoName() {
            return cargoName;
        }

        public void setCargoName(String cargoName) {
            this.cargoName = cargoName;
        }

        public int getCabinNo() {
            return cabinNo;
        }

        public void setCabinNo(int cabinNo) {
            this.cabinNo = cabinNo;
        }

        public int getCabinId() {
            return cabinId;
        }

        public void setCabinId(int cabinId) {
            this.cabinId = cabinId;
        }

        public int getTask_id() {
            return task_id;
        }

        public void setTask_id(int task_id) {
            this.task_id = task_id;
        }

        public String getCmsid() {
            return cmsid;
        }

        public void setCmsid(String cmsid) {
            this.cmsid = cmsid;
        }

        public String getUnloaderName() {
            return unloaderName;
        }

        public void setUnloaderName(String unloaderName) {
            this.unloaderName = unloaderName;
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
