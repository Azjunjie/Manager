package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.util.List;

/**
 * 货物统计
 * Created by zhangjunjie on 2018/4/5.
 */

public class CargoInfoStatisticsEntity extends BaseEntity{

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

        private String cargoId;
        private String cargoName;
        private double total;
        private double finished;
        private double finishedUsedTime;
        private double finishedEfficiency;
        private double finishedBeforeClearance;
        private double finishedUsedTimeBeforeClearance;
        private double finishedEfficiencyBeforeClearance;
        private double remainder;
        private double clearance;
        private double clearanceUsedTime;
        private double clearanceEfficiency;
        private String clearTime;

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

        public double getFinishedBeforeClearance() {
            return finishedBeforeClearance;
        }

        public void setFinishedBeforeClearance(double finishedBeforeClearance) {
            this.finishedBeforeClearance = finishedBeforeClearance;
        }

        public double getFinishedUsedTimeBeforeClearance() {
            return finishedUsedTimeBeforeClearance;
        }

        public void setFinishedUsedTimeBeforeClearance(double finishedUsedTimeBeforeClearance) {
            this.finishedUsedTimeBeforeClearance = finishedUsedTimeBeforeClearance;
        }

        public double getFinishedEfficiencyBeforeClearance() {
            return finishedEfficiencyBeforeClearance;
        }

        public void setFinishedEfficiencyBeforeClearance(double finishedEfficiencyBeforeClearance) {
            this.finishedEfficiencyBeforeClearance = finishedEfficiencyBeforeClearance;
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

        public String getClearTime() {
            return clearTime;
        }

        public void setClearTime(String clearTime) {
            this.clearTime = clearTime;
        }
    }
}
