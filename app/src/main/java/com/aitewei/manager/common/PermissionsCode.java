package com.aitewei.manager.common;

import java.util.List;

/**
 * 权限列表
 * Created by zhangjunjie on 2017/11/19.
 */

public class PermissionsCode {
    //报表查看
    public static String watch = "7";
    //船舶靠泊
    public static String shipBerthing = "10";
    //查看船舶、船舱卸船信息以及状态；
    public static String watchDetail = "12";
    //完成船舶
    public static String completeShip = "13";
    //查看船舶、船舱、货物信息；
    public static String watchInfo = "16";
    //设置完成状态
    public static String completeStatus = "21";
    //设置船舱位置
    public static String setLocation = "56";
    //设置清舱状态
    public static String clearStatus = "78";
    //查看plc
    public static String plcDetail = "81";

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
