package rabbitproduce.jacy;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author jacy
 */
@Component
//@Slf4j
public class SenderService
{

    public final static String QUEUE_NAME="rabbitMQ.test";

    @Autowired
    private RabbitTemplate rabbitTemplate;
    public static void Send(@Payload String message, String queueName)throws IOException, TimeoutException {
        if(queueName==null || queueName.length()==0)
            queueName=QUEUE_NAME;

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("localhost");
//        factory.setUsername("jacy");
//        factory.setPassword("jacy");
//        factory.setPort(2088);
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //  声明一个队列        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //发送消息到队列中
        channel.basicPublish("", queueName, null, message.getBytes("UTF-8"));
        System.out.println("Producer Send +'" + message + "'");
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}