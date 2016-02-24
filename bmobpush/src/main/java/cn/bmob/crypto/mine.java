package cn.bmob.crypto;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-11-12  .
 * *********    Time : 11:00 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public final class mine {
    private static String bP = "bmob";
    private Cipher bQ;
    private Cipher bR;

    public mine() throws Exception {
        this(bP);
    }

    private mine(String var1) throws Exception {
        this.bQ = null;
        this.bR = null;
        Key var2 = L(var1.getBytes());
        this.bQ = Cipher.getInstance("DES");
        this.bQ.init(1, var2);
        this.bR = Cipher.getInstance("DES");
        this.bR.init(2, var2);
    }

    public static String D(byte[] var0) throws Exception {
        int var1 = var0.length;
        StringBuffer var2 = new StringBuffer(var1 << 1);

        for(int var3 = 0; var3 < var1; ++var3) {
            int var4;
            for(var4 = var0[var3]; var4 < 0; var4 += 256) {
                ;
            }

            if(var4 < 16) {
                var2.append("0");
            }

            var2.append(Integer.toString(var4, 16));
        }

        return var2.toString();
    }

    private static byte[] u(String var0) throws Exception {
        int var1;
        byte[] var5;
        byte[] var2 = new byte[(var1 = (var5 = var0.getBytes()).length) / 2];

        for(int var3 = 0; var3 < var1; var3 += 2) {
            String var4 = new String(var5, var3, 2);
            var2[var3 / 2] = (byte)Integer.parseInt(var4, 16);
        }

        return var2;
    }

    public final byte[] S(byte[] var1) throws Exception {
        return this.bQ.doFinal(var1);
    }
    public final byte[] S1(byte[] var1) throws Exception {
        return this.bR.doFinal(var1);
    }

    public final String v(String var1) throws Exception {
        byte[] var2 = u(var1);
        return new String(this.bR.doFinal(var2));
    }

    private static Key L(byte[] var0) throws Exception {
        byte[] var1 = new byte[8];

        for(int var2 = 0; var2 < var0.length && var2 < 8; ++var2) {
            var1[var2] = var0[var2];
        }

        return new SecretKeySpec(var1, "DES");
    }
}
