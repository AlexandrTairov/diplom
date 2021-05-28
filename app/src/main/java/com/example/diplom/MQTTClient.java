package com.example.diplom;

import android.util.Log;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.*;

class Mqtt3PostPropertyMessageListener implements IMqttMessageListener {
    @Override
    public void messageArrived(String var1, MqttMessage var2) throws Exception {
        Log.d("myLog ", "reply topic  : " + var1);
        Log.d("myLog ", "reply payload: " + var2.toString());
    }
}

public class MQTTClient {

    public static void userMethod(String topic0, String value, String username, String password, String address) {
        String productKey = "a1X2bEnP82z";
        String deviceSecret = "ga7XA6KdlEeiPXQPpRbAjOZXwG8ydgSe";

        MqttSign sign = new MqttSign();
        sign.calculate(productKey, username, deviceSecret);

        Log.d("myLog ", "username: " + sign.getUsername());
        Log.d("myLog ", "password: " + sign.getPassword());
        Log.d("myLog ", "clientid: " + sign.getClientid());

        //使用Paho连接阿里云物联网平台
        String port = "443";
        String broker = "ssl://" + productKey + ".iot-as-mqtt.cn-shanghai.aliyuncs.com" + ":" + port;
        MemoryPersistence persistence = new MemoryPersistence();
        try{
            //Paho Mqtt 客户端
            MqttClient sampleClient = new MqttClient(broker, sign.getClientid(), persistence);

            //Paho Mqtt 连接参数
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(180);
            connOpts.setUserName(sign.getUsername());
            connOpts.setPassword(sign.getPassword().toCharArray());
            sampleClient.connect(connOpts);
            Log.d("myLog ", "broker: " + broker + " Connected");

            //Paho Mqtt 消息订阅
            String topicReply = "/sys/" + productKey + "/" + username + "/thing/event/property/post_reply";
            sampleClient.subscribe(topicReply, new Mqtt3PostPropertyMessageListener());
            Log.d("myLog ", "subscribe: " + topicReply);

            //Paho Mqtt 消息发布
            String topic = "/sys/" + productKey + "/" + username + "/thing/event/property/post";
            String content = "{\"id\":\"1\",\"version\":\"1.0\",\"params\":{\"LightSwitch\":1}}";
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(0);
            sampleClient.publish(topic, message);
            Log.d("myLog ", "publish: " + content);

            Thread.sleep(2000);

            //Paho Mqtt 断开连接
            sampleClient.disconnect();
            Log.d("myLog ", "Disconnected");
            System.exit(0);
        } catch (MqttException e) {
            Log.d("myLog ", "reason " + e.getReasonCode());
            Log.d("myLog ", "msg " + e.getMessage());
            Log.d("myLog ", "loc " + e.getLocalizedMessage());
            Log.d("myLog ", "cause " + e.getCause());
            Log.d("myLog ", "excep " + e);
            e.printStackTrace();
        } catch (InterruptedException e ) {
            e.printStackTrace();
        }
    }

    public void publishMessage(String topic, String value, String username, String password, String address) {
        userMethod(topic, value, username, password, address);
    }
}
