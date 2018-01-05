package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 船舶舱位位置信息
 * Created by zjj on 2017/11/17.
 */

public class CabinPositionEntity extends BaseEntity implements Serializable{
    private static final long serialVersionUID = -91234567890L;
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private static final long serialVersionUID = -123456789090L;
        /**
         * cabinNo :
         * startPosition :
         * endPosition :
         */

        private String cabinNo;
        private String startPosition;
        private String endPosition;

        public String getCabinNo() {
            return cabinNo;
        }

        public void setCabinNo(String cabinNo) {
            this.cabinNo = cabinNo;
        }

        public String getStartPosition() {
            return startPosition;
        }

        public void setStartPosition(String startPosition) {
            this.startPosition = startPosition;
        }

        public String getEndPosition() {
            return endPosition;
        }

        public void setEndPosition(String endPosition) {
            this.endPosition = endPosition;
        }
    }
}
