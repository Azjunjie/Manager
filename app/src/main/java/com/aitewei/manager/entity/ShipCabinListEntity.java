package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 船舱列表信息
 * Created by zhangjunjie on 2017/11/12.
 */

public class ShipCabinListEntity extends BaseEntity implements Serializable {


    /**
     * total : 2
     * data : [{"cargoType":"钢缆","preunloading":99.82,"finished":88.1,"isFinish":"0","remainder":22,"cabinNo":1},{"cargoType":"铜锭","preunloading":22.22,"finished":88,"isFinish":"0","remainder":22,"cabinNo":2}]
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

    public static class DataBean implements Serializable {
        /**
         * cargoType : 钢缆
         * preunloading : 99.82
         * finished : 88.1
         * isFinish : 0
         * remainder : 22.0
         * cabinNo : 1
         */

        private int cabinNo;
        private int cargoId;
        private String cargoName;
        private double total;
        private double finished;
        private double remainder;
        private double clearance;
        private String clearTime;
        private double startPosition;
        private double endPosition;
        private double finishedBeforeClearance;
        private String status;

        public double getFinishedBeforeClearance() {
            return finishedBeforeClearance;
        }

        public void setFinishedBeforeClearance(double finishedBeforeClearance) {
            this.finishedBeforeClearance = finishedBeforeClearance;
        }

        public String getClearTime() {
            return clearTime;
        }

        public void setClearTime(String clearTime) {
            this.clearTime = clearTime;
        }

        public int getCabinNo() {
            return cabinNo;
        }

        public void setCabinNo(int cabinNo) {
            this.cabinNo = cabinNo;
        }

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

        public double getStartPosition() {
            return startPosition;
        }

        public void setStartPosition(double startPosition) {
            this.startPosition = startPosition;
        }

        public double getEndPosition() {
            return endPosition;
        }

        public void setEndPosition(double endPosition) {
            this.endPosition = endPosition;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
