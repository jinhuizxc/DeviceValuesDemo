package com.example.devicevaluesdemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ServiceUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;

/**
 * 获取设备信息
 *
 * 设备相关 -> DeviceUtils.java -> Demo
 *
 * isDeviceRooted   : 判断设备是否 rooted
 * getSDKVersion    : 获取设备系统版本号
 * getAndroidID     : 获取设备 AndroidID
 * getMacAddress    : 获取设备 MAC 地址
 * getManufacturer  : 获取设备厂商
 * getModel         : 获取设备型号
 * shutdown         : 关机
 * reboot           : 重启
 * reboot2Recovery  : 重启到 recovery
 * reboot2Bootloader: 重启到 bootloader
 *
 *
 * 网络相关 -> NetworkUtils.java -> Demo
 *
 * openWirelessSettings  : 打开网络设置界面
 * isConnected           : 判断网络是否连接
 * isAvailableByPing     : 判断网络是否可用
 * getMobileDataEnabled  : 判断移动数据是否打开
 * setMobileDataEnabled  : 打开或关闭移动数据
 * isMobileData          : 判断网络是否是移动数据
 * is4G                  : 判断网络是否是 4G
 * getWifiEnabled        : 判断 wifi 是否打开
 * setWifiEnabled        : 打开或关闭 wifi
 * isWifiConnected       : 判断 wifi 是否连接状态
 * isWifiAvailable       : 判断 wifi 数据是否可用
 * getNetworkOperatorName: 获取移动网络运营商名称
 * getNetworkType        : 获取当前网络类型
 * getIPAddress          : 获取 IP 地址
 * getDomainAddress      : 获取域名 ip 地址
 *
 * 手机相关 -> PhoneUtils.java -> Demo
 *
 * isPhone            : 判断设备是否是手机
 * getIMEI            : 获取 IMEI 码
 * getIMSI            : 获取 IMSI 码
 * getPhoneType       : 获取移动终端类型
 * isSimCardReady     : 判断 sim 卡是否准备好
 * getSimOperatorName : 获取 Sim 卡运营商名称
 * getSimOperatorByMnc: 获取 Sim 卡运营商名称
 * getPhoneStatus     : 获取手机状态信息
 * dial               : 跳至拨号界面
 * call               : 拨打 phoneNumber
 * sendSms            : 跳至发送短信界面
 * sendSmsSilent      : 发送短信
 * getAllContactInfo  : 获取手机联系人
 * getContactNum      : 打开手机联系人界面点击联系人后便获取该号码
 * getAllSMS          : 获取手机短信并保存到 xml 中
 *
 * 服务相关 -> ServiceUtils.java
 *
 * getAllRunningService: 获取所有运行的服务
 * startService        : 启动服务
 * stopService         : 停止服务
 * bindService         : 绑定服务
 * unbindService       : 解绑服务
 * isServiceRunning    : 判断服务是否运行
 *
 *
 * 1. 当前手机的手机号码，如果是双卡，都显示出来
 * wifi 名称
 * bssid;
 *
 * 【Android】 mobile information android手机可以获取的所有信息
 * https://github.com/guxiaonian/MobileInfo
 *
 *
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_PHONE_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Logger.d("获取系统版本号 = " + DeviceUtils.getSDKVersionName());  // D/DeviceInfo: │ 获取系统版本号 = 9
//        Logger.d("获取系统版本号 = " + DeviceUtils.getSDKVersionCode());  // D/DeviceInfo: │ 获取系统版本号 = 28
//        Logger.d("获取设备型号 = " + DeviceUtils.getModel());            // D/DeviceInfo: │ 获取设备型号 = V1824BA
//        Logger.d("获取设备厂商 = " + DeviceUtils.getManufacturer());     // D/DeviceInfo: │ 获取设备厂商 = vivo
//        Logger.d("获取设备 MAC 地址 = " + DeviceUtils.getMacAddress());   // D/DeviceInfo: │ 获取设备 MAC 地址 = dc:31:d1:d9:92:67
//
//        // 获取wifi信息
//        Logger.d("获取移动网络运营商名称 = " + NetworkUtils.getNetworkOperatorName());  // D/DeviceInfo: │ 获取移动网络运营商名称 = CMCC
//        Logger.d("获取 IP 地址 = " + NetworkUtils.getIpAddressByWifi());     // D/DeviceInfo: │ 获取 IP 地址 = 192.168.5.39
//        Logger.d("获取域名 ip 地址 = " + NetworkUtils.getDomainAddress(""));   // D/DeviceInfo: │ 获取域名 ip 地址 = ::1

        // 手机相关
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
//                != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
//                    REQUEST_CODE);
//        }else {
//            Logger.d("获取 IMEI 码 = " + PhoneUtils.getIMEI());   // 获取 IMEI 码 = 868726048181227
//            Logger.d("获取 IMSI 码 = " + PhoneUtils.getIMSI());   // 获取 IMSI 码 = 460077730180758
//            Logger.d("获取移动终端类型 = " + PhoneUtils.getPhoneType());   // 获取移动终端类型 = 1
//            Logger.d("获取 Sim 卡运营商名称 = " + PhoneUtils.getSimOperatorName());  // 获取 Sim 卡运营商名称 = CMCC
//            Logger.d("获取 Sim 卡运营商名称 1 = " + PhoneUtils.getSimOperatorByMnc());  // 获取 Sim 卡运营商名称 1 = 中国移动
//        }
//        Logger.d("获取手机状态信息 = " + PhoneUtils.getPhoneStatus());  // D/DeviceInfo: │ 获取手机状态信息 = DeviceId(IMEI) = 868726048181235

        // 服务相关
//        Logger.d("获取所有运行的服务 = " + ServiceUtils.getAllRunningServices());


        String phoneInfo = getPhoneInfo();
        Logger.d("获取手机信息phoneInfo = " + phoneInfo);

    }

    @SuppressLint("HardwareIds")
    public String getPhoneInfo() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder sb = new StringBuilder();

        if (ActivityCompat.checkSelfPermission(App.getApp(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(App.getApp(), Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(App.getApp(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS,
                            Manifest.permission.READ_PHONE_NUMBERS,
                            Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PHONE_CODE);
        }else {
            sb.append("\nLine1Number = ").append(tm.getLine1Number());
            sb.append("\nNetworkOperator = ").append(tm.getNetworkOperator());//移动运营商编号
            sb.append("\nNetworkOperatorName = ").append(tm.getNetworkOperatorName());//移动运营商名称
            sb.append("\nSimCountryIso = ").append(tm.getSimCountryIso());
            sb.append("\nSimOperator = ").append(tm.getSimOperator());
            sb.append("\nSimOperatorName = ").append(tm.getSimOperatorName());
            sb.append("\nSimSerialNumber = ").append(tm.getSimSerialNumber());
            sb.append("\nSubscriberId(IMSI) = ").append(tm.getSubscriberId());
        }
        return sb.toString();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   ToastUtils.showShort("do something!");
                } else {
                    ToastUtils.showShort("请同意系统权限后继续");
                }
                break;
            case REQUEST_PHONE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ToastUtils.showShort("do something!");
                } else {
                    ToastUtils.showShort("请同意系统权限后继续");
                }
                break;
        }
    }
}
