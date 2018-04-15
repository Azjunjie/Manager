package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.util.List;

/**
 * Created by zhangjunjie on 2017/11/12.
 */

public class ShipListEntity extends BaseEntity {


    /**
     * total : 2
     * data : [{"shipEname":"ship1","imoNo":"M00001","id":"1","shipName":"船舶1","berthName":"矿一"},{"shipEname":"ship2","imoNo":"M00002","id":"2","shipName":"船舶2","berthName":"矿二"}]
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
         * shipEname : ship1
         * imoNo : M00001
         * id : 1
         * shipName : 船舶1
         * berthName : 矿一
         */

        private String shipEname;
        private int shipType;
        private String imoNo;
        private String id;
        private String shipName;
        private String berthName;
        private String enterPortTime;//预靠时间
        private String berthingTime;//靠泊时间
        private String beginTime;//开工时间
        private String endTime;//完工时间
        private String departureTime;//离泊时间
        private String shipStatus;//船舶状态

        public String getShipStatus() {
            return shipStatus;
        }

        public void setShipStatus(String shipStatus) {
            this.shipStatus = shipStatus;
        }

        public String getEnterPortTime() {
            return enterPortTime;
        }

        public void setEnterPortTime(String enterPortTime) {
            this.enterPortTime = enterPortTime;
        }

        public String getBerthingTime() {
            return berthingTime;
        }

        public void setBerthingTime(String berthingTime) {
            this.berthingTime = berthingTime;
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

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public int getShipType() {
            return shipType;
        }

        public void setShipType(int shipType) {
            this.shipType = shipType;
        }

        public String getShipEname() {
            return shipEname;
        }

        public void setShipEname(String shipEname) {
            this.shipEname = shipEname;
        }

        public String getImoNo() {
            return imoNo;
        }

        public void setImoNo(String imoNo) {
            this.imoNo = imoNo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShipName() {
            return shipName;
        }

        public void setShipName(String shipName) {
            this.shipName = shipName;
        }

        public String getBerthName() {
            return berthName;
        }

        public void setBerthName(String berthName) {
            this.berthName = berthName;
        }
    }
}
