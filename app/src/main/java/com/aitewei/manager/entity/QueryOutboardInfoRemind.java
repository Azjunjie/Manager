package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

/**
 * 舱外作业量提醒查询
 */
public class QueryOutboardInfoRemind extends BaseEntity{

    /**
     * whetherOutboardInfo : 1
     */

    private int whetherOutboardInfo;

    public int getWhetherOutboardInfo() {
        return whetherOutboardInfo;
    }

    public void setWhetherOutboardInfo(int whetherOutboardInfo) {
        this.whetherOutboardInfo = whetherOutboardInfo;
    }
}
