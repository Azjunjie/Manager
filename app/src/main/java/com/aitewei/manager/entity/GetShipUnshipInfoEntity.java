package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.util.List;

/**
 * 获取船舶卸船情况列表
 * Created by zhangjunjie on 2018/1/17.
 */

public class GetShipUnshipInfoEntity extends BaseEntity {

    /**
     * total : 1
     * data : [{"total":10000,"clearance":0,"finished":0,"remainder":10000}]
     */

    private int total;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total : 10000.0
         * clearance : 0.0
         * finished : 0.0
         * remainder : 10000.0
         */

        private double total;
        private double clearance;
        private double finished;
        private double remainder;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getClearance() {
            return clearance;
        }

        public void setClearance(double clearance) {
            this.clearance = clearance;
        }

        public double getFinished() {
            return finished;
        }

        public void setFinished(double finished) {
            this.finished = finished;
        }

        public double getRemainder() {
            return remainder;
        }

        public void setRemainder(double remainder) {
            this.remainder = remainder;
        }
    }
}
