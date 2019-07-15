package cn.myweb01.money01.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping(value="rabbit")
public class RabbitmqController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("test")
    public void test() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", "1");
        map.put("name", "pig");
        //根据key发送到对应的队列
        rabbitTemplate.convertAndSend("que_pig_key", map);

    }

}


/*
* /**
 * RabbitMQ 之confirm异步
 *
 * Channel 对象提供的ConfirmListener（）回调方法只包含 deliveryTag（当前Chanel发出的消息序号），我们需要自己
 * 为每一个Channel维护一个unconfirm的消息序号集合，每publish
 * 一条数据，集合中元素加1，每回调一次handleAck方法，unconfirm集合
 * 删除相应的一条（multiple=false）或多条（multiple=true）记录，从 程序运行效率上看，这个unconfirm集合最好采用有序集合
 * SortedSet存储结构
 *
 * @author zhang
 *

public class ConfirmAsySend {

    public static final String QUEUE_NAME = "asy_test_confirm";

    public static void main(String[] args) throws IOException, TimeoutException {

        // 获取连接
        Connection connection = ConnectHelp.getConnect();

        // 建立通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 开启事务
        channel.confirmSelect();

        // 创建set
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        // 监听事务
        channel.addConfirmListener(new ConfirmListener() {

            // handleNack
            public void handleNack(long arg0, boolean arg1) throws IOException {
                // TODO Auto-generated method stub

                if (arg1) {
                    System.out.println("handleNack : " + arg1);
                    confirmSet.headSet(arg0 + 1).clear();
                } else {
                    System.out.println("handleNack : " + arg1);
                    confirmSet.remove(arg0);
                }
            }

            // 没有问题
            public void handleAck(long arg0, boolean arg1) throws IOException {
                // TODO Auto-generated method stub

                if (arg1) {
                    System.out.println("handleAck :  " + arg1);
                    confirmSet.headSet(arg0 + 1).clear();
                } else {
                    System.out.println("handleAck :  " + arg1);
                    confirmSet.remove(arg0);
                }

            }
        });

        // 发送的数据
        String msg = "msg data";

        while (true) {
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            confirmSet.add(seqNo);
        }

    }











/**
 * RabbitMQ 之comfirm 异步 消费者
 *
 * @author zhang
 *

public class ConfirmAsyRevc {

    public static final String QUEUE_NAME = "asy_test_confirm";

    public static void main(String[] args) throws IOException, TimeoutException {

        // 获取连接
        Connection connetion = ConnectHelp.getConnect();

        // 创建通道
        Channel channel = connetion.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 接受数据，并监听
        channel.basicConsume(QUEUE_NAME, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                    throws IOException {

                System.out.println("confirm Revc：" + new String(body, "utf-8"));

            }
        });

    }

}
}*/