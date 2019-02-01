package com.jieshuizhibiao.waterindex.beans.request;

/**
 * Created by songxiaotao on 2019/1/12.
 * Class Note:修改头像
 */

public class SetAvatarReqParams {
    String avatar; 	//新头像地址 	字符串(string) 	是 		调用上传接口后返回的图片地址

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
