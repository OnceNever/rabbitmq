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
@RabbitListener(queues = {"email.fanout.queue"})
@Service
public class EmailConsumer {
    Log log = LogFactory.getLog(EmailConsumer.class);

    //标记为消息的落脚点
    @RabbitHandler
    public void getEmail(String message){
        log.info("EmailConsumer接收到消息 ===》 " + message);
    }
}
