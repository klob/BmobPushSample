package cn.bmob.v4;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-08-06  .
 * *********    Time : 10:22 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.security.auth.x500.X500Principal;

public final class Install {
 /*   private static final X500Principal bM = new X500Principal("CN=Android Debug,O=Android,C=US");
    public static String bK = "";
    public static int bL = 0;

    public Install() {
    }

    public static Map<String, String> V(Context var0, String var1) {
        HashMap var2 = new HashMap();
        String var3 = a(var0);
        var2.put("User-Agent", var3);
        if (!var1.equals("http://open.bmob.cn/8/secret")) {
            var1 = var3;
            String var4 = e(var0);
            var3 = "";
            if (!TextUtils.isEmpty(var4)) {
                var3 = madness.encode((new This(j(var1))).I(f.madness.A(var4)));
            }

            var2.put("Accept-Id", var3);
        }

        var2.put("charset", "utf-8");
        var2.put("Content-Type", "application/json");
        var2.put("Accept-Encoding", "gzip");
        return var2;
    }

    private static String j(String var0) {
        byte[] var3 = f.madness.A(var0);
        byte[] var1 = new byte[16];

        for (int var2 = 0; var2 < 16; ++var2) {
            var1[var2] = var3[var2 + 1];
        }

        return f.madness.F(var1);
    }

    public static String I(String var0, String var1) {
        byte[] var4 = f.madness.A(var0);
        byte[] var2 = new byte[16];

        for (int var3 = 0; var3 < 16; ++var3) {
            var2[var3] = var4[var4.length - 16 + var3];
        }

        var0 = f.madness.F(var2);
        This var5 = new This(var0);
        byte[] var6 = f.madness.A(var1);
        return madness.encode(var5.I(var6));
    }

    public static String V(Context var0, String var1, String var2) {
        byte[] var5 = f.madness.A(var1);
        byte[] var3 = new byte[16];

        for (int var4 = 0; var4 < 16; ++var4) {
            var3[var4] = var5[var5.length - 16 + var4];
        }

        var1 = f.madness.F(var3);
        byte[] var6 = madness.decode(var2);
        if ((var5 = (new This(var1)).Z(var6)) == null) {
            var5 = (new This(j(a(var0)))).Z(var6);
        }

        return f.madness.F(var5);
    }

    public static String k(String var0) {
        if (TextUtils.isEmpty(bK)) {
            return null;
        } else {
            This var1 = new This(bK);
            byte[] var2 = f.madness.A(var0);
            return madness.encode(var1.I(var2));
        }
    }

    public static String l(String var0) {
        byte[] var2 = madness.decode(var0);
        return f.madness.F((new This(bK)).Z(var2));
    }

    private static String L(Context var0) {
        String var1 = "volley/0";

        try {
            String var2 = var0.getPackageName();
            PackageInfo var4 = var0.getPackageManager().getPackageInfo(var2, 0);
            var1 = var2 + "/" + var4.versionCode;
        } catch (NameNotFoundException var3) {
            ;
        }

        return var1;
    }

    private static String a(Context var0) {
        StringBuilder var1;
        (var1 = new StringBuilder()).append(L(var0));
        var1.append(String.valueOf(System.currentTimeMillis()));
        var1.append("Android");
        var1.append(String.valueOf("v3.4.2"));
        return var1.toString();
    }

    public static long getTimeStamp() {
        return System.currentTimeMillis() / 1000L - (long) bL;
    }

    public static String b(Context var0) {
        String var1;
        if (d(var0)) {
            var1 = c(var0) + "/" + 0;
        } else {
            var1 = c(var0) + "/" + 1;
        }

        return var1;
    }

    public static String c(Context var0) {
        String var1 = "";

        try {
            var1 = The.S(var0.getPackageManager().getPackageInfo(var0.getPackageName(), 64).signatures[0].toByteArray());
        } catch (NameNotFoundException var2) {
            var2.printStackTrace();
        }

        return var1;
    }

    public static boolean d(Context var0) {
        boolean var1 = false;

        try {
            Signature[] var7 = var0.getPackageManager().getPackageInfo(var0.getPackageName(), 64).signatures;

            for (int var2 = 0; var2 < var7.length; ++var2) {
                CertificateFactory var3 = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream var4 = new ByteArrayInputStream(var7[var2].toByteArray());
                if (var1 = ((X509Certificate) var3.generateCertificate(var4)).getSubjectX500Principal().equals(bM)) {
                    break;
                }
            }
        } catch (NameNotFoundException var5) {
            ;
        } catch (CertificateException var6) {
            ;
        }

        return var1;
    }

    public static void I(Context c, String var1) {
        try {
            That2 var4 = new That2(c);
            var1 = (new acknowledge()).n(var1);
            var4.remove("appkey");
            var4.Z("appkey", var1);
        } catch (Exception var3) {
            ;
        }
    }

    public static String e(Context var0) {
        String var4;
        if (TextUtils.isEmpty(var4 = (new That2(var0)).getValue("appkey", ""))){
            return "";
        }else{
            String var1 = "";

            try {
                var1 = (new acknowledge()).o(var4);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return var1;
        }
    }

    public static String getInstallationId(Context context) {
        String var1 = b();
        String var5 = g(context);
        var5 = var1 + var5;
        MessageDigest var6 = null;

        try {
            var6 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        }

        var6.update(var5.getBytes(), 0, var5.length());
        byte[] var2 = var6.digest();
        var5 = new String();

        for (int var7 = 0; var7 < var2.length; ++var7) {
            int var3;
            if ((var3 = 255 & var2[var7]) <= 15) {
                var5 = var5 + "0";
            }

            var5 = var5 + Integer.toHexString(var3);
        }

        return var5.toUpperCase(Locale.CHINA);
    }

    public static String f(Context var0) {
        String var1 = Z(var0, "android.permission.READ_PHONE_STATE") ? ((TelephonyManager) var0.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId() : "";
        String var2 = b();
        String var3 = g(var0);
        String var4 = Z(var0, "android.permission.ACCESS_WIFI_STATE") ? ((WifiManager) var0.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress() : "";
        String var7 = Z(var0, "android.permission.BLUETOOTH") ? BluetoothAdapter.getDefaultAdapter().getAddress() : "";
        var7 = var1 + var2 + var3 + var4 + var7;
        MessageDigest var8 = null;

        try {
            var8 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        }

        var8.update(var7.getBytes(), 0, var7.length());
        byte[] var9 = var8.digest();
        var7 = new String();

        for (int var10 = 0; var10 < var9.length; ++var10) {
            int var11;
            if ((var11 = 255 & var9[var10]) <= 15) {
                var7 = var7 + "0";
            }

            var7 = var7 + Integer.toHexString(var11);
        }

        return var7.toUpperCase(Locale.CHINA);
    }

    private static String b() {
        return "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10;
    }

    private static String g(Context var0) {
        return Secure.getString(var0.getContentResolver(), "android_id");
    }

    private static boolean Z(Context var0, String var1) {
        PackageManager var2 = var0.getPackageManager();
        String[] var3 = null;

        try {
            var3 = var2.getPackageInfo(var0.getPackageName(), 4096).requestedPermissions;
        } catch (NameNotFoundException var4) {
            var4.printStackTrace();
        }

        ArrayList var5 = new ArrayList();
        if (var3 != null) {
            for (int var6 = 0; var6 < var3.length; ++var6) {
                var5.add(var3[var6]);
            }
        }

        return var5.contains(var1);
    }

    public static boolean Code(String var0, int var1, int var2) {
        return Pattern.compile("^[a-zA-Z]\\w{" + 1 + "," + 49 + "}$").matcher(var0).matches();
    }

    public static int Code(int var0, int var1) {
        int var2 = 100;
        if (var0 < var1) {
            var2 = (int) ((double) var0 / (double) var1 * 100.0D);
        }

        return var2;
    }

    public static String C(String[] var0) {
        StringBuilder var1 = new StringBuilder();
        if (var0 != null && var0.length > 0) {
            int var2 = var0.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                if (var3 != var2 - 1) {
                    var1.append(var0[var3]);
                    var1.append(",");
                } else {
                    var1.append(var0[var3]);
                }
            }
        }

        return var1.toString();
    }

    static class That2 {
        private Context mContext;
        private SharedPreferences cd;
        private SharedPreferences.Editor ce;

        public That2(Context var1) {
            this(var1, var1.getSharedPreferences("bmob_sp", 0));
        }
        private That2(Context var1, SharedPreferences var2) {
            this.cd = null;
            this.ce = null;
            this.cd = var2;
            this.ce = var2.edit();
        }

        public final void remove(String key) {
            this.ce.remove(key);
            this.ce.commit();
        }

        public final void Z(String var1, String var2) {
            this.ce.putString(var1, var2);
            this.ce.commit();
        }

        public final String getValue(String key, String defaultValue) {
            return this.cd.getString(key, defaultValue);
        }
    }*/
}
