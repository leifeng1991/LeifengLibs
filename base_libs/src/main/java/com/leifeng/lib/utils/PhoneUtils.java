package com.leifeng.lib.utils;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresPermission;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.leifeng.lib.base.BaseApplication;

import java.util.List;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SEND_SMS;

/**
 * 手机相关工具类
 */
public final class PhoneUtils {

    private PhoneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断设备是否是手机
     */
    public static boolean isPhone() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getBaseApplication().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取设备码
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(READ_PHONE_STATE)
    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getBaseApplication().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getDeviceId() : null;
    }

    /**
     * 获取 IMEI 码
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(READ_PHONE_STATE)
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getBaseApplication().getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tm != null ? tm.getImei() : null;
        }
        return tm != null ? tm.getDeviceId() : null;
    }

    /**
     * 获取 MEID 码
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(READ_PHONE_STATE)
    public static String getMEID() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getBaseApplication().getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tm != null ? tm.getMeid() : null;
        } else {
            return tm != null ? tm.getDeviceId() : null;
        }
    }

    /**
     * 获取 IMSI 码
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(READ_PHONE_STATE)
    public static String getIMSI() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getBaseApplication().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSubscriberId() : null;
    }

    /**
     * 获取移动终端类型
     *
     * @return PHONE_TYPE_NONE，PHONE_TYPE_GSM，PHONE_TYPE_CDMA，PHONE_TYPE_SIP
     */
    public static int getPhoneType() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getBaseApplication().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getPhoneType() : -1;
    }

    /**
     * 判断 sim 卡是否准备好
     */
    public static boolean isSimCardReady() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getBaseApplication().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * 获取 Sim 卡运营商名称
     */
    public static String getSimOperatorName() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getBaseApplication().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSimOperatorName() : null;
    }

    /**
     * 获取 Sim 卡运营商名称
     */
    public static String getSimOperatorByMnc() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getBaseApplication().getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm != null ? tm.getSimOperator() : null;
        if (operator == null) return null;
        switch (operator) {
            case "46000":
            case "46002":
            case "46007":
                return "中国移动";
            case "46001":
                return "中国联通";
            case "46003":
                return "中国电信";
            default:
                return operator;
        }
    }

    /**
     * 获取手机状态信息（添加必要权限：uses-permission android:name="android.permission.READ_PHONE_STATE）
     * <p>Must hold
     * {@code <uses-permission android:name="android.permission.READ_PHONE_STATE" />}</p>
     *
     * @return DeviceId = 99000311726612<br>
     * DeviceSoftwareVersion = 00<br>
     * Line1Number =<br>
     * NetworkCountryIso = cn<br>
     * NetworkOperator = 46003<br>
     * NetworkOperatorName = 中国电信<br>
     * NetworkType = 6<br>
     * PhoneType = 2<br>
     * SimCountryIso = cn<br>
     * SimOperator = 46003<br>
     * SimOperatorName = 中国电信<br>
     * SimSerialNumber = 89860315045710604022<br>
     * SimState = 5<br>
     * SubscriberId(IMSI) = 460030419724900<br>
     * VoiceMailNumber = *86<br>
     */
    @SuppressLint("HardwareIds")
    @RequiresPermission(READ_PHONE_STATE)
    public static String getPhoneStatus() {
        TelephonyManager tm =
                (TelephonyManager) BaseApplication.getBaseApplication().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) return "";
        String str = "";
        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
        str += "Line1Number = " + tm.getLine1Number() + "\n";
        str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
        str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
        str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
        str += "NetworkType = " + tm.getNetworkType() + "\n";
        str += "PhoneType = " + tm.getPhoneType() + "\n";
        str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
        str += "SimOperator = " + tm.getSimOperator() + "\n";
        str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
        str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
        str += "SimState = " + tm.getSimState() + "\n";
        str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
        str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
        return str;
    }

    /**
     * 跳至拨号界面
     *
     * @param phoneNumber 手机号
     */
    public static void dial(final String phoneNumber) {
        BaseApplication.getBaseApplication().startActivity(IntentUtils.getDialIntent(phoneNumber, true));
    }

    /**
     * 拨打 phoneNumber（添加必要权限：uses-permission android:name="android.permission.CALL_PHONE）
     *
     * @param phoneNumber 手机号
     */
    @RequiresPermission(CALL_PHONE)
    public static void call(final String phoneNumber) {
        BaseApplication.getBaseApplication().startActivity(IntentUtils.getCallIntent(phoneNumber, true));
    }

    /**
     * 跳至发送短信界面
     *
     * @param phoneNumber 手机号
     * @param content     发送内容
     */
    public static void sendSms(final String phoneNumber, final String content) {
        BaseApplication.getBaseApplication().startActivity(IntentUtils.getSendSmsIntent(phoneNumber, content, true));
    }

    /**
     * 发送短信（添加必要权限：uses-permission android:name="android.permission.SEND_SMS）
     *
     * @param phoneNumber 手机号
     * @param content     发送内容
     */
    @RequiresPermission(SEND_SMS)
    public static void sendSmsSilent(final String phoneNumber, final String content) {
        if (StringUtils.isEmpty(content)) return;
        PendingIntent sentIntent = PendingIntent.getBroadcast(BaseApplication.getBaseApplication(), 0, new Intent(), 0);
        SmsManager smsManager = SmsManager.getDefault();
        if (content.length() >= 70) {
            List<String> ms = smsManager.divideMessage(content);
            for (String str : ms) {
                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
            }
        } else {
            smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
        }
    }
}
