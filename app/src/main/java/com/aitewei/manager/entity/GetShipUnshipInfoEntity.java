package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

/**
 * 获取船舶卸船情况列表
 * Created by zhangjunjie on 2018/1/17.
 */

public class GetShipUnshipInfoEntity extends BaseEntity {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int cargoId;
        private String cargoName;
        private double total;
        private double finished;
        private double remainder;
        private double clearance;

        public int getCargoId() {
            return cargoId;
        }

        public void setCargoId(int cargoId) {
            this.cargoId = cargoId;
        }

        public String getCargoName() {
            return cargoName;
        }

        public void setCargoName(String cargoName) {
            this.cargoName = cargoName;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
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

        public double getClearance() {
            return clearance;
        }

        public void setClearance(double clearance) {
            this.clearance = clearance;
        }
    }

}
