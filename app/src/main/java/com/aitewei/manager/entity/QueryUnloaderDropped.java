package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.util.List;

/**
 * 查询卸船机掉线信息
 */
public class QueryUnloaderDropped extends BaseEntity{

    /**
     * whetherToDrop : 1
     * data : [{"unloaderId":"1","unloaderName":"unloaderName","dropTime":"dropTime"}]
     */

    private int whetherToDrop;
    private List<DataBean> data;

    public int getWhetherToDrop() {
        return whetherToDrop;
    }

    public void setWhetherToDrop(int whetherToDrop) {
        this.whetherToDrop = whetherToDrop;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * unloaderId : 1
         * unloaderName : unloaderName
         * dropTime : dropTime
         */

        private String unloaderId;
        private String unloaderName;
        private String dropTime;

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

        public String getDropTime() {
            return dropTime;
        }

        public void setDropTime(String dropTime) {
            this.dropTime = dropTime;
        }
    }
}
