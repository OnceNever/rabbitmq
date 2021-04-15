package cn.seeyourface.springbootrabbitmqproducer.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Auther: yanglei
 * @Date: 2021/4/15 20:34
 * @Version: V1.0
 * @Description: TODO
 */
@Service
public class OrderService {
    Log log = LogFactory.getLog(OrderService.class);

    @Autowired
    private RabbitTemplate template;

    /**
     * 模拟用户下单
     * @param uId
     * @param prodId
     * @param num
     */
    public void makeOrder(String uId, String prodId, int num) {

        //1.查询商品库存是否充足
        //2.保存订单
        String orderId = UUID.randomUUID().toString();
        log.info("订单生成成功！订单ID:" + orderId);
        //3.通过消息队列完成消息分发
        //参数1：交换机 参数2：路由key/queue队列名称 参数3：消息内容
        String exchangeName = "fanout_order_exchange";
        String routeKey = "";
        template.convertAndSend(exchangeName, routeKey, orderId);
    }
}
