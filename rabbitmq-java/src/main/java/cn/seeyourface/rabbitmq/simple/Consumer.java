package cn.seeyourface.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: yanglei
 * @Date: 2021/4/6 21:36
 * @Version: V1.0
 * @Description: TODO
 */
public class Consumer {

    public static void main(String[] args) {

        //所有的中间件技术都是基于tcp/ip协议基础之上构建新型的协议规范，只不过rabbitmq遵循的是amqp
        //ip port
        //1.创建连接工程
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("154.8.171.90");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");


        Connection connection = null;
        Channel channel = null;
        try {
            //2.创建连接Connection
            connection = connectionFactory.newConnection("消费者");
            //3.通过连接获取通道Channel
            channel = connection.createChannel();
            //4.通过创建交换机，声明队列，绑定关系，路由Key，发送消息和接收消息
            channel.basicConsume("queue1", true, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery delivery) throws IOException {
                    System.out.println("收到消息是：" + new String(delivery.getBody(), "UTF-8"));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {
                    System.out.println("消息接收失败！");
                }
            });
            System.out.println("开始接受消息！");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //7.关闭通道
            if (channel != null && channel.isOpen()){
                try {
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //8.关闭连接
            if (connection != null && connection.isOpen()){
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
