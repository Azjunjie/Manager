package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

/**
 * 货物详情
 * Created by zjj on 2017/11/17.
 */

public class ShipGoodsDetailEntity extends BaseEntity {

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
         * owner :
         * cargoCategory : 1002
         * loadingPort : 南京港
         * moisture : 65
         * warehouse : 2
         * quality : 良
         */

        private String cargoType;
        private String owner;
        private String cargoCategory;
        private String loadingPort;
        private String moisture;
        private String warehouse;
        private String quality;
        private String stowage;

        public String getStowage() {
            return stowage;
        }

        public void setStowage(String stowage) {
            this.stowage = stowage;
        }

        public String getCargoType() {
            return cargoType;
        }

        public void setCargoType(String cargoType) {
            this.cargoType = cargoType;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getCargoCategory() {
            return cargoCategory;
        }

        public void setCargoCategory(String cargoCategory) {
            this.cargoCategory = cargoCategory;
        }

        public String getLoadingPort() {
            return loadingPort;
        }

        public void setLoadingPort(String loadingPort) {
            this.loadingPort = loadingPort;
        }

        public String getMoisture() {
            return moisture;
        }

        public void setMoisture(String moisture) {
            this.moisture = moisture;
        }

        public String getWarehouse() {
            return warehouse;
        }

        public void setWarehouse(String warehouse) {
            this.warehouse = warehouse;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }
    }

}
