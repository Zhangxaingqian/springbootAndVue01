package cn.myweb01.money01.listener.rabbitMqListener;

import cn.myweb01.money01.mapper.elasticsearch.JobRepository;
import cn.myweb01.money01.service.IElasticsearchService;
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

import java.io.IOException;

@Component
public class EsListener {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final static Logger log= LoggerFactory.getLogger(EsListener.class);

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private IElasticsearchService elasticsearchService;
    //监听创建和更新
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.update.queue", durable = "true"),
            exchange = @Exchange(
                    value = "IExchange02",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"job.update","job.insert"}))
    public void listenUpdate(Message message, Channel channel){
        log.info("es服务监听到更新或者新增消息");
        try {
            JsonNode jsonData = MAPPER.readTree(message.getBody());
            String id = jsonData.get("id").textValue();
            elasticsearchService.createIndex(Integer.valueOf(id));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.info("es接收消息异常");
            e.printStackTrace();
        }

    }
    //监听删除
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.delete.queue", durable = "true"),
            exchange = @Exchange(
                    value = "IExchange02",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"job.delete"}))
    public void listenDelete(Message message, Channel channel){
        log.info("es服务监听到删除消息");
        try {

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.info("es接收消息异常");
            e.printStackTrace();
        }

    }
}
