package hust.wang.hhyx.stock.listener.mq;
import com.rabbitmq.client.Channel;
import hust.wang.hhyx.entity.mq.stock.StockLockedTo;
import hust.wang.hhyx.entity.order.Order;
import hust.wang.hhyx.stock.service.StockService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author wangmh
 * @Date 2021/1/28 1:28 上午
 **/

@Service
@RabbitListener(queues = "stock.release.stock.queue")
public class StockReleaseListener {
    @Autowired
    StockService stockService;

    /**
     * 1.库存自动解锁。
     *   下订单成功。库存锁定成功 其他服务调用出问题导致订单回滚。
     *              之前锁定的库存就会自动解锁'
     * 2.订单失败。
     *   锁库存失败。
     *
     * @param stockLockedTo
     * @param message
     */
    /**
     * 被动
     * @param stockLockedTo
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitHandler
    public void handleStockLockedRelease(StockLockedTo stockLockedTo, Message message, Channel channel) throws IOException {
        System.out.println("收到解库存的消息");
        try {
            stockService.unlockStock(stockLockedTo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }

    }

    /**
     * 主动补偿
     * @param order
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitHandler
    public void handleOrderCloseRelease(Order order, Message message, Channel channel) throws IOException {
        System.out.println("收到订单关闭的消息，准备解锁库存");
        try {
            stockService.unlockStock(order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }

    /**
     * (rollbackFor = NoStockException.class)
     * 默认只要是运行时异常都会回滚
     * 为某个订单锁定库存
     *
     *
     * 库存解锁的场景
     * 1）、下订单成功。订单过期没有支付被系统自动
     * 2）、下订单成功。库存锁定成功 其他服务调用出问题导致订单回滚。
     *     之前锁定的库存就会自动解锁
     * @param stockSkuLockVo
     * @return
     */
}
