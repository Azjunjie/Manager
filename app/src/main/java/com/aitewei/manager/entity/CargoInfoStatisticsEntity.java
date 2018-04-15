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
        private String total;
        private String finished;
        private String finishedUsedTime;
        private String finishedEfficiency;
        private String remainder;
        private String clearance;
        private String clearanceUsedTime;
        private String clearanceEfficiency;
        private String clearTime;

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

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getFinished() {
            return finished;
        }

        public void setFinished(String finished) {
            this.finished = finished;
        }

        public String getFinishedUsedTime() {
            return finishedUsedTime;
        }

        public void setFinishedUsedTime(String finishedUsedTime) {
            this.finishedUsedTime = finishedUsedTime;
        }

        public String getFinishedEfficiency() {
            return finishedEfficiency;
        }

        public void setFinishedEfficiency(String finishedEfficiency) {
            this.finishedEfficiency = finishedEfficiency;
        }

        public String getRemainder() {
            return remainder;
        }

        public void setRemainder(String remainder) {
            this.remainder = remainder;
        }

        public String getClearance() {
            return clearance;
        }

        public void setClearance(String clearance) {
            this.clearance = clearance;
        }

        public String getClearanceUsedTime() {
            return clearanceUsedTime;
        }

        public void setClearanceUsedTime(String clearanceUsedTime) {
            this.clearanceUsedTime = clearanceUsedTime;
        }

        public String getClearanceEfficiency() {
            return clearanceEfficiency;
        }

        public void setClearanceEfficiency(String clearanceEfficiency) {
            this.clearanceEfficiency = clearanceEfficiency;
        }
    }
}
