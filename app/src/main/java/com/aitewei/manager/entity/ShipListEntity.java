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
        private String imoNo;
        private String id;
        private String shipName;
        private String berthName;

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
