package com.example.devicevaluesdemo.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.example.devicevaluesdemo.App;

/**
 * Created by WangJinyong on 2018/3/31.
 * 获取SIM卡信息和手机号码
 */

public class PhoneInfoUtils {

    private static final int REQUEST_CODE = 1;
    private static String TAG = "PhoneInfoUtils";

    private TelephonyManager telephonyManager;
    //移动运营商编号
    private String NetworkOperator;
    private Context context;

    public PhoneInfoUtils(Context context) {
        this.context = context;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    //获取sim卡iccid
//    public String getIccid() {
//        String iccid = "N/A";
//        if (ActivityCompat.checkSelfPermission(App.getApp(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
//        }
//        iccid = telephonyManager.getSimSerialNumber();
//        return iccid;
//    }

    //获取电话号码
    public String getNativePhoneNumber() {
        String nativePhoneNumber = "N/A";
        nativePhoneNumber = telephonyManager.getLine1Number();
        return nativePhoneNumber;
    }

    //获取手机服务商信息
    public String getProvidersName() {
        String providersName = "N/A";
        NetworkOperator = telephonyManager.getNetworkOperator();
        //IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
//        Flog.d(TAG,"NetworkOperator=" + NetworkOperator);
        if (NetworkOperator.equals("46000") || NetworkOperator.equals("46002")) {
            providersName = "中国移动";//中国移动
        } else if (NetworkOperator.equals("46001")) {
            providersName = "中国联通";//中国联通
        } else if (NetworkOperator.equals("46003")) {
            providersName = "中国电信";//中国电信
        }
        return providersName;

    }


}
