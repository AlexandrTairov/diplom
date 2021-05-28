package com.example.diplom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

class CryptoUtil {
    private static String hmac(String plainText, String key, String algorithm, String format) throws Exception {
        if (plainText == null || key == null) {
            return null;
        }

        byte[] hmacResult = null;

        Mac mac = Mac.getInstance(algorithm);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        mac.init(secretKeySpec);
        hmacResult = mac.doFinal(plainText.getBytes());
        return String.format(format, new BigInteger(1, hmacResult));
    }

    public static String hmacSha256(String plainText, String key) throws Exception {
        return hmac(plainText,key,"HmacSHA256","%064x");
    }
}

public class MqttSign {
    private String username = "";

    private String password = "";

    private String clientid = "";

//    private String address = "";

//    public MqttSign(String username, String password, String address) {
//        this.username = username;
//        this.password = password;
//        this.address = address;
//    }

    public String getUsername() { return this.username;}

    public String getPassword() { return this.password;}

    public String getClientid() { return this.clientid;}

//    public String getAddress() { return this.address;}

    public void calculate(String productKey, String deviceName, String deviceSecret) {
        if (productKey == null || deviceName == null || deviceSecret == null) {
            return;
        }

        try {
            this.username = deviceName + "&" + productKey;

            String timestamp = Long.toString(System.currentTimeMillis());
            String plainPasswd = "clientId" + productKey + "." + deviceName + "deviceName" +
                    deviceName + "productKey" + productKey + "timestamp" + timestamp;
            this.password = CryptoUtil.hmacSha256(plainPasswd, deviceSecret);

            this.clientid = productKey + "." + deviceName + "|" + "timestamp=" + timestamp +
                    ",_v=paho-java-1.0.0,securemode=2,signmethod=hmacsha256|";
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
