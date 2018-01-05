package com.aitewei.manager.entity;

import com.aitewei.manager.base.BaseEntity;

/**
 * 服务器返回的用户实体类
 * Created by zjj on 2017/11/6.
 */

public class RemoteUserEntity extends BaseEntity {

    /**
     * userInfo : {"apartmentId":0,"birthday":"","cityId":0,"flag":0,"hobbies":"","image":"http://192.168.1.23:8090/null","info":"","mobile":"13552625662","nickName":"","perfectionInfo":false,"sessionId":"372CF345CB46F558EF65D1BEA0662E977","sex":0,"userId":7,"userType":1}
     */

    private UserInfoBean userInfo;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {

        private int userId;
        private String sessionId;
        private String image;
        private String nickName;
        private String birthday;
        private int sex;
        private int userType;
        private int cityId;
        private int apartmentId;
        private boolean perfectionInfo;

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public int getApartmentId() {
            return apartmentId;
        }

        public void setApartmentId(int apartmentId) {
            this.apartmentId = apartmentId;
        }

        public boolean isPerfectionInfo() {
            return perfectionInfo;
        }

        public void setPerfectionInfo(boolean perfectionInfo) {
            this.perfectionInfo = perfectionInfo;
        }
    }
}
