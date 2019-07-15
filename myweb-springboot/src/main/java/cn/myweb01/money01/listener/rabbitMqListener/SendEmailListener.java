package cn.myweb01.money01.listener.rabbitMqListener;


import cn.myweb01.money01.singelService.sendEmail.SendEmail;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
* 这是一个监听类,专门监听rabbitMq的消息,接收到消息之后,调用发送邮件的接口*/
@Component
public class SendEmailListener {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final static Logger log= LoggerFactory.getLogger(SendEmailListener.class);

    @Autowired
    private SendEmail sendEmail;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.email.queue", durable = "true"),
            exchange = @Exchange(
                    value = "IExchange02",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"myweb.email"}))
    public void emailListen(Message message, Channel channel){{
        log.info("发送邮件监听到消息");
        try {
            //msg就是rabbitmq传来的消息
            // 使用jackson解析
            JsonNode jsonData = MAPPER.readTree(message.getBody());
            if("Y".equals(jsonData.get("sendEmail").textValue())){//发送短信
                log.info("开始发送邮件");
                sendEmail.sendEmail(jsonData.get("email").textValue(), jsonData.get("userName").textValue(),jsonData.get("code").textValue());
                //手动ack确认
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                log.info("发送邮件结束");

            }

        } catch (Exception e) {
            log.info("发送邮件异常");
            e.printStackTrace();

        }


    }

    }
}
