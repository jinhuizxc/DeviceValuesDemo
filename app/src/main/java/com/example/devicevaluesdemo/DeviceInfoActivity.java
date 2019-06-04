package com.example.devicevaluesdemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobile.mobilehardware.build.BuildHelper;
import com.mobile.mobilehardware.signal.SignalBean;
import com.mobile.mobilehardware.signal.SignalHelper;
import com.mobile.mobilehardware.simcard.SimCardHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class DeviceInfoActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final String TAG = "DeviceInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        checkPermission();

        // 申请打电话权限
        checkCallPhonePermission();   // 请求单一权限



    }

    public void getDeviceInfo(){
        // 手机卡数据获取  中国移动:CMCC   中国联通:CUCC
        JSONObject jsonObject = SimCardHelper.mobileSimInfo(getApplicationContext());
        Log.e(TAG, "手机卡数据获取: " + jsonObject.toString());
        //  E/MainActivity: 手机卡数据获取: {
        // "sim1Imei":"868726048181235",
        // "sim2Imei":"868726048181227",
        // "sim1Imsi":"460013011914834",
        // "sim2Imsi":"460077730180758",
        // "simSlotIndex":"1",
        // "meid":"A0000096BD47A9",
        // "sim1ImsiOperator":"CU",
        // "sim2ImsiOperator":"CM",
        // "sim1Ready":"true",
        // "sim2Ready":"true",
        // "isTwoCard":"true",
        // "isHaveCard":"true",
        // "operator":"CM"}

        // 信号数据获取
        JSONObject jsonObject1 = SignalHelper.mobGetNetRssi(getApplicationContext());
        Log.e(TAG, "信号数据获取: " + jsonObject1.toString());
        SignalBean signalBean = new Gson().fromJson(jsonObject1.toString(), SignalBean.class);
        Log.e(TAG, "信号数据获取 signalBean : " + signalBean.getBssid());
        // 信号数据获取: {"type":"WIFI",
        // "bssid":"34:29:12:ad:d7:20",
        // "ssid":"szzx",
        // "ipAddress":"192.168.5.39",
        // "ipAddressIpv6":"$unknown",
        // "macAddress":"DC:31:D1:D9:92:67",
        // "networkId":"2",
        // "linkSpeed":"390Mbps",
        // "rssi":"-53",
        // "level":"4",
        // "supplicantState":"COMPLETED",
        // "proxy":"false","proxyAddress":"$unknown",
        // "proxyPort":"$unknown"}

        // TODO 4G
        // 信号数据获取: {"type":"4G","bssid":"$unknown","ssid":"$unknown","ipAddress":"10.65.62.82",
        // "ipAddressIpv6":"2409:8809:8540:9f63:4432:c1ff:fe53:4cc5","macAddress":"DC:31:D1:D9:92:67",
        // "networkId":"$unknown","linkSpeed":"$unknown","rssi":"-90","level":"4","supplicantState":"$unknown",
        // "proxy":"$unknown","proxyAddress":"$unknown","proxyPort":"$unknown"}

        // 系统Build数据获取
        JSONObject jsonObject2 = BuildHelper.mobGetBuildInfo();
        Log.e(TAG, "系统Build数据获取 : " + jsonObject2.toString());
        // 系统Build数据获取 : {"board":"msmnile","bootloader":"unknown","brand":"vivo",
        // "device":"PD1824","display":"PKQ1.181216.001 release-keys",
        // "fingerprint":"vivo\/PD1824\/PD1824:9\/PKQ1.181216.001\/compiler05282140:user\/release-keys","hardware":"qcom","host":"compiler0172",
        // "id":"PKQ1.181216.001","manufacturer":"vivo","model":"V1824BA","product":"PD1824",
        // "radio":"150_GEN_PACK-1.202356.2.205267.1,150_GEN_PACK-1.202356.2.205267.1",
        // "serial":"e3aba0a","tags":"release-keys","time":"1559050747000","type":"user","user":"compiler","osVersion":"$unknown",
        // "releaseVersion":"9","codeName":"$unknown","incremental":"compiler05282140","sdkInt":"28","previewSdkInt":"0","securityPatch":"2019-04-01"}
    }

    /*************检查打电话的权限************/
    /**
     * 检查打电话的权限
     */
    @AfterPermissionGranted(Constants.Permission.REQUEST_CALL_PHONE)
    private void checkCallPhonePermission() {
        if (hasCallPhonePermission()) {
            // 获取信息
            getDeviceInfo();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.perms_call_phone),
                    Constants.Permission.REQUEST_CALL_PHONE,
                    Constants.Permission.CALL_PHONE);
        }
    }

    private boolean hasCallPhonePermission() {
        return EasyPermissions.hasPermissions(this, Constants.Permission.CALL_PHONE);
    }

    /**
     * 请求权限的结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d("tag", "onPermissionsGranted:" + requestCode + ":" + perms.size());
        getDeviceInfo();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        new AppSettingsDialog.Builder(this)
                .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置屏幕以修改应用权限")
                .setTitle("App需要该权限")
                .build()
                .show();
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);
            Toast.makeText(this, getString(R.string.returned_to_activity,
                    hasCallPhonePermission() ? yes : no), Toast.LENGTH_LONG).show();
        }
    }


    /**
     * 动态权限申请
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = null;
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissions = new ArrayList<>();
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (permissions == null) {
                    permissions = new ArrayList<>();
                }
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (permissions == null) {
                    permissions = new ArrayList<>();
                }
                permissions.add(Manifest.permission.CAMERA);
            }
            if (permissions != null) {
                String[] permissionArray = new String[permissions.size()];
                permissions.toArray(permissionArray);
                requestPermissions(permissionArray, 0);
            }
        }
    }
}
