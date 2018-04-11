package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.util.List;

/**
 * 船舱统计
 * Created by zhangjunjie on 2018/4/5.
 */

public class CarbinInfoStatisticsEntity extends BaseEntity{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cargoId :
         * cargoName : cargoName
         * total :
         * finished :
         * finishedUsedTime :
         * finishedEfficiency :
         * remainder :
         * clearance :
         * clearanceUsedTime :
         * clearanceEfficiency :
         */

        private String cabinNo;
        private String cabinId;
        private String cargoId;
        private String cargoName;
        private double total;
        private double finished;
        private double finishedUsedTime;
        private double finishedEfficiency;
        private double remainder;
        private double clearance;
        private double clearanceUsedTime;
        private double clearanceEfficiency;
        private String status;
        private String clearTime;

        public String getCabinNo() {
            return cabinNo;
        }

        public void setCabinNo(String cabinNo) {
            this.cabinNo = cabinNo;
        }

        public String getCabinId() {
            return cabinId;
        }

        public void setCabinId(String cabinId) {
            this.cabinId = cabinId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getClearTime() {
            return clearTime;
        }

        public void setClearTime(String clearTime) {
            this.clearTime = clearTime;
        }

        public String getCargoId() {
            return cargoId;
        }

        public void setCargoId(String cargoId) {
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

        public double getFinishedUsedTime() {
            return finishedUsedTime;
        }

        public void setFinishedUsedTime(double finishedUsedTime) {
            this.finishedUsedTime = finishedUsedTime;
        }

        public double getFinishedEfficiency() {
            return finishedEfficiency;
        }

        public void setFinishedEfficiency(double finishedEfficiency) {
            this.finishedEfficiency = finishedEfficiency;
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

        public double getClearanceUsedTime() {
            return clearanceUsedTime;
        }

        public void setClearanceUsedTime(double clearanceUsedTime) {
            this.clearanceUsedTime = clearanceUsedTime;
        }

        public double getClearanceEfficiency() {
            return clearanceEfficiency;
        }

        public void setClearanceEfficiency(double clearanceEfficiency) {
            this.clearanceEfficiency = clearanceEfficiency;
        }
    }
}
