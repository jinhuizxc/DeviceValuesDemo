package com.example.devicevaluesdemo;

import android.Manifest;

public class Constants {

    /**
     * 配置全局需要的权限，都写在这个位置
     */
    public static class Permission {
        // 打电话权限
        public static final String CALL_PHONE = Manifest.permission.READ_PHONE_STATE;
        // 请求码
        public static final int REQUEST_CALL_PHONE = 0x01;  // 打电话
    }
    

}
