package com.aitewei.manager.common;

import java.util.List;

/**
 * 权限列表
 * Created by zhangjunjie on 2017/11/19.
 */

public class PermissionsCode {
    //查看船舶列表
    public static String shipList = "12";
    //设置船舱位置
    public static String setLocation = "56";
    //设置清舱状态
    public static String clearStatus = "78";
    //设置完成状态
    public static String completeStatus = "21";
    //报表查看
    public static String watch = "7";

    /**
     * 检查权限
     */
    public static boolean isHasPermission(String permissionCode) {
        User user = User.newInstance();
        List<String> data = user.getData();
        if (data != null && data.contains(permissionCode)) {
            return true;
        }
        return false;
    }

}
