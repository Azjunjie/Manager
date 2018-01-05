package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

import java.util.List;

/**
 * 权限列表
 * Created by zhangjunjie on 2017/11/19.
 */

public class GetPrivilegeEntity extends BaseEntity {

    /**
     * code : 1
     * data : ["12","56","78","21","7"]
     */
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
