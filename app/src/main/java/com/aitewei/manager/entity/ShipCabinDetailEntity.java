package com.aitewei.manager.entity;


import com.aitewei.manager.base.BaseEntity;

/**
 * 舱位详情
 * Created by zjj on 2017/11/17.
 */

public class ShipCabinDetailEntity extends BaseEntity {

    /**
     * data : {"cargoType":"铝","efficiency":"","detailId":3,"unloadingTonnage":7.56,"cabinNo":"3#舱","total":5,"usedTime":2,"clearance":33.2,"finish":7.56,"startTime":"2017-11-14 10:37:43","endTime":"2017-11-16 10:37:45","remainder":2.22,"status":"00"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cargoType : 铝
         * efficiency :
         * detailId : 3
         * unloadingTonnage : 7.56
         * cabinNo : 3#舱
         * total : 5
         * usedTime : 2
         * clearance : 33.2
         * finish : 7.56
         * startTime : 2017-11-14 10:37:43
         * endTime : 2017-11-16 10:37:45
         * remainder : 2.22
         * status : 00
         */

        private int cabinNo;
        private String cargoName;
        private double total;
        private double finished;
        private double remainder;
        private double clearance;
        private String status;

        public int getCabinNo() {
            return cabinNo;
        }

        public void setCabinNo(int cabinNo) {
            this.cabinNo = cabinNo;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
