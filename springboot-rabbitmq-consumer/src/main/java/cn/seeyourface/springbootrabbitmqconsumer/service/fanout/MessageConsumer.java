package cn.seeyourface.springbootrabbitmqconsumer.service.fanout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @Auther: yanglei
 * @Date: 2021/4/15 21:05
 * @Version: V1.0
 * @Description: TODO
 */
@Service
@RabbitListener(queues = {"message.fanout.queue"})
public class MessageConsumer {
    Log log = LogFactory.getLog(MessageConsumer.class);

    //标记为消息的落脚点
    @RabbitHandler
    public void getMessage(String message){
        log.info("MessageConsumer接收到消息 ===》 " + message);
    }
}
