package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

/**
 * 船舶基本信息
 * Created by zhangjunjie on 2017/11/12.
 */

public class ShipBaseInfoEntity extends BaseEntity {


    /**
     * data : {"departureTime":"2017-12-10 00:00:00","breadth":234,"hatch":4,"length":234,"buildDate":"2017-12-12","shipName":"船舶1","berthingTime":"2017-12-10 00:00:00","shipEname":"ship1","depth":222,"load":234,"imoNo":"M00001","draft":"22.5","id":"1","beginTime":"2017-11-27 00:00:00","endTime":"2017-12-11 00:00:00","cabinNum":4,"freeboardHeight":"234","cable":"55/3","cabintype":"blablalba"}
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
         * departureTime : 2017-12-10 00:00:00
         * breadth : 234
         * hatch : 4
         * length : 234
         * buildDate : 2017-12-12
         * shipName : 船舶1
         * berthingTime : 2017-12-10 00:00:00
         * shipEname : ship1
         * depth : 222
         * load : 234
         * imoNo : M00001
         * draft : 22.5
         * id : 1
         * beginTime : 2017-11-27 00:00:00
         * endTime : 2017-12-11 00:00:00
         * cabinNum : 4
         * freeboardHeight : 234
         * cable : 55/3
         * cabintype : blablalba
         */

        private String enterPortTime;
        private String departureTime;
        private String breadth;
        private String hatch;
        private String length;
        private String buildDate;
        private String shipName;
        private String berthingTime;
        private String shipEname;
        private String depth;
        private String load;
        private String imoNo;
        private String draft;
        private String id;
        private String beginTime;
        private String endTime;
        private String cabinNum;
        private String freeboardHeight;
        private String cable;
        private String cabintype;

        public String getEnterPortTime() {
            return enterPortTime;
        }

        public void setEnterPortTime(String enterPortTime) {
            this.enterPortTime = enterPortTime;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public String getBreadth() {
            return breadth;
        }

        public void setBreadth(String breadth) {
            this.breadth = breadth;
        }

        public String getHatch() {
            return hatch;
        }

        public void setHatch(String hatch) {
            this.hatch = hatch;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getBuildDate() {
            return buildDate;
        }

        public void setBuildDate(String buildDate) {
            this.buildDate = buildDate;
        }

        public String getShipName() {
            return shipName;
        }

        public void setShipName(String shipName) {
            this.shipName = shipName;
        }

        public String getBerthingTime() {
            return berthingTime;
        }

        public void setBerthingTime(String berthingTime) {
            this.berthingTime = berthingTime;
        }

        public String getShipEname() {
            return shipEname;
        }

        public void setShipEname(String shipEname) {
            this.shipEname = shipEname;
        }

        public String getDepth() {
            return depth;
        }

        public void setDepth(String depth) {
            this.depth = depth;
        }

        public String getLoad() {
            return load;
        }

        public void setLoad(String load) {
            this.load = load;
        }

        public String getImoNo() {
            return imoNo;
        }

        public void setImoNo(String imoNo) {
            this.imoNo = imoNo;
        }

        public String getDraft() {
            return draft;
        }

        public void setDraft(String draft) {
            this.draft = draft;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getCabinNum() {
            return cabinNum;
        }

        public void setCabinNum(String cabinNum) {
            this.cabinNum = cabinNum;
        }

        public String getFreeboardHeight() {
            return freeboardHeight;
        }

        public void setFreeboardHeight(String freeboardHeight) {
            this.freeboardHeight = freeboardHeight;
        }

        public String getCable() {
            return cable;
        }

        public void setCable(String cable) {
            this.cable = cable;
        }

        public String getCabintype() {
            return cabintype;
        }

        public void setCabintype(String cabintype) {
            this.cabintype = cabintype;
        }
    }
}
