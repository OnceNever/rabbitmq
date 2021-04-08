package cn.seeyourface.rabbitmq.topics;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Auther: yanglei
 * @Date: 2021/4/8 21:12
 * @Version: V1.0
 * @Description: TODO
 */
public class Consumer {

    public static void main(String[] args) {
        new Thread(runnable, "queue1").start();
        new Thread(runnable, "queue2").start();
        new Thread(runnable, "queue3").start();
    }

    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
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
                connection = connectionFactory.newConnection("生产者");
                //3.通过连接获取通道Channel
                channel = connection.createChannel();
                //4.通过创建交换机，声明队列，绑定关系，路由Key，发送消息和接收消息
                String queueName = Thread.currentThread().getName();
                channel.basicConsume(queueName, true, new DeliverCallback() {
                    @Override
                    public void handle(String s, Delivery delivery) throws IOException {
                        System.out.println(queueName + "收到消息是：" + new String(delivery.getBody(), "UTF-8"));
                    }
                }, new CancelCallback() {
                    @Override
                    public void handle(String s) throws IOException {
                        System.out.println(queueName + "消息接收失败！");
                    }
                });
                System.out.println(queueName + "开始接受消息！");
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
    };
}
