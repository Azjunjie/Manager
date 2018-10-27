package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.util.List;

/**
 * 统计飘到舱外的作业量统计
 */
public class OutboardInfoStatistics extends BaseEntity{

    /**
     * code : 1
     * data : [{"startPosition":219.5,"endPosition":252.5,"leftOffset":0.1,"leftUnloading":0.1,"leftShovelNumber":0,"rightOffset":"252.7","rightUnloading":"13.4","rightShovelNumber":1,"cabinNo":1},{"startPosition":186,"endPosition":219.5,"leftOffset":0,"leftUnloading":0,"leftShovelNumber":0,"rightOffset":0,"rightUnloading":0,"rightShovelNumber":0,"cabinNo":2},{"startPosition":161.5,"endPosition":186,"leftOffset":0,"leftUnloading":0,"leftShovelNumber":0,"rightOffset":0,"rightUnloading":0,"rightShovelNumber":0,"cabinNo":3},{"startPosition":133.5,"endPosition":161.5,"leftOffset":0,"leftUnloading":0,"leftShovelNumber":0,"rightOffset":0,"rightUnloading":0,"rightShovelNumber":0,"cabinNo":4},{"startPosition":108.5,"endPosition":133.5,"leftOffset":"108.3","leftUnloading":"13.4","leftShovelNumber":1,"rightOffset":0,"rightUnloading":0,"rightShovelNumber":0,"cabinNo":5},{"startPosition":83.3,"endPosition":105,"leftOffset":0,"leftUnloading":0,"leftShovelNumber":0,"rightOffset":"105.1","rightUnloading":"12.3","rightShovelNumber":1,"cabinNo":6},{"startPosition":58.5,"endPosition":83.3,"leftOffset":0,"leftUnloading":0,"leftShovelNumber":0,"rightOffset":0,"rightUnloading":0,"rightShovelNumber":0,"cabinNo":7},{"startPosition":33.5,"endPosition":58.5,"leftOffset":0,"leftUnloading":0,"leftShovelNumber":0,"rightOffset":0,"rightUnloading":0,"rightShovelNumber":0,"cabinNo":8},{"startPosition":0,"endPosition":33.5,"leftOffset":0,"leftUnloading":0,"leftShovelNumber":0,"rightOffset":0,"rightUnloading":0,"rightShovelNumber":0,"cabinNo":9}]
     * whetherOutboardInfo : true
     */

    private boolean whetherOutboardInfo;
    private List<DataBean> data;

    public boolean isWhetherOutboardInfo() {
        return whetherOutboardInfo;
    }

    public void setWhetherOutboardInfo(boolean whetherOutboardInfo) {
        this.whetherOutboardInfo = whetherOutboardInfo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * startPosition : 219.5
         * endPosition : 252.5
         * leftOffset : 0.1
         * leftUnloading : 0.1
         * leftShovelNumber : 0
         * rightOffset : 252.7
         * rightUnloading : 13.4
         * rightShovelNumber : 1
         * cabinNo : 1
         */

        private double startPosition;
        private double endPosition;
        private double leftOffset;
        private double leftUnloading;
        private int leftShovelNumber;
        private String rightOffset;
        private String rightUnloading;
        private int rightShovelNumber;
        private int cabinNo;

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

        public double getLeftOffset() {
            return leftOffset;
        }

        public void setLeftOffset(double leftOffset) {
            this.leftOffset = leftOffset;
        }

        public double getLeftUnloading() {
            return leftUnloading;
        }

        public void setLeftUnloading(double leftUnloading) {
            this.leftUnloading = leftUnloading;
        }

        public int getLeftShovelNumber() {
            return leftShovelNumber;
        }

        public void setLeftShovelNumber(int leftShovelNumber) {
            this.leftShovelNumber = leftShovelNumber;
        }

        public String getRightOffset() {
            return rightOffset;
        }

        public void setRightOffset(String rightOffset) {
            this.rightOffset = rightOffset;
        }

        public String getRightUnloading() {
            return rightUnloading;
        }

        public void setRightUnloading(String rightUnloading) {
            this.rightUnloading = rightUnloading;
        }

        public int getRightShovelNumber() {
            return rightShovelNumber;
        }

        public void setRightShovelNumber(int rightShovelNumber) {
            this.rightShovelNumber = rightShovelNumber;
        }

        public int getCabinNo() {
            return cabinNo;
        }

        public void setCabinNo(int cabinNo) {
            this.cabinNo = cabinNo;
        }
    }
}
