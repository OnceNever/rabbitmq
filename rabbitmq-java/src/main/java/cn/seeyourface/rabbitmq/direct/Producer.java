package cn.seeyourface.rabbitmq.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Auther: yanglei
 * @Date: 2021/4/8 21:12
 * @Version: V1.0
 * @Description: TODO
 */
public class Producer {

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
            connection = connectionFactory.newConnection("生产者");
            //3.通过连接获取通道Channel
            channel = connection.createChannel();
            //4.通过创建交换机，声明队列，绑定关系，路由Key，发送消息和接收消息
            //页面上绑定了交换机与队列的关系，这里可以不用绑定
//            String queueName = "queue1";
//            channel.queueDeclare(queueName, true, false, false, null);//声明队列 队列名称、是否持久化、是否具有排他性、是否自动删除、携带参数
            //5.准备消息内容
            String message = "Hello World!";
            //6.准备交换机
            String exchangeName = "direct-exchange";
            //指定路由key
            String routeKey = "email";
            //指定交换机的类型
            String type = "direct";
            //7.发送消息给中间件rabbitmq-server
            channel.basicPublish(exchangeName, routeKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送成功！");
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
