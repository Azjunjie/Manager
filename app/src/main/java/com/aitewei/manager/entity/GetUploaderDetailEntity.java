package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.util.List;

/**
 * 卸货详情记录
 * Created by zhangjunjie on 2017/12/11.
 */

public class GetUploaderDetailEntity extends BaseEntity {

    private List<UnloaderDetailBean> data;

    public List<UnloaderDetailBean> getData() {
        return data;
    }

    public void setData(List<UnloaderDetailBean> data) {
        this.data = data;
    }

    public static class UnloaderDetailBean{
        private String unloaderName;
        private String startTime;
        private String endTime;
        private double usedTime;
        private double unloading;
        private double efficiency;

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
