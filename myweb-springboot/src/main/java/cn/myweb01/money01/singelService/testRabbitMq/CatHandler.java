package cn.myweb01.money01.singelService.testRabbitMq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import java.io.IOException;

public class CatHandler implements ChannelAwareMessageListener {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onMessage(Message msg, Channel channel)  {
        try {
            //msg就是rabbitmq传来的消息
            // 使用jackson解析
            JsonNode jsonData = MAPPER.readTree(msg.getBody());
            System.out.println("我是可爱的小猫,我的id是" + jsonData.get("id").asText()
                    + ",我的名字是" + jsonData.get("name").asText());
            String name = jsonData.get("name").textValue();
            System.out.println("name = " + name);
            //手动ack确认
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
