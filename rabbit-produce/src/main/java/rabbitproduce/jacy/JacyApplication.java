package rabbitproduce.jacy;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.scheduling.annotation.Scheduled;
/**
 * @author 57073
 */
@SpringBootApplication
@EnableScheduling//启用任务调度.
//@RabbitListener(queues="foo")//启用Rabbit队列监听foo key.
//@RabbitListener(queues="news")//启用Rabbit队列监听foo key.
public class JacyApplication {

    //rabbit操作类;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private HelloSender helloSender;

    @Autowired
    private static SenderService senderService;

    public final static String QUEUE_TASK_NAME="rabbitMQ.task";


    @Scheduled(fixedDelay=3000)//3s执行1次此方法;
    public void send() throws IOException, TimeoutException {
        senderService.Send("hello this is 3s message",null);
//        rabbitTemplate.convertAndSend("foo","jacy");
//        helloSender.send();
//        rabbitTemplate.convertAndSend("news",new Foo("西游记"));
//        //2、广播
//        rabbitTemplate.convertAndSend("news",new Foo("三国演义"));
    }

    //5s执行1次此方法;
//    @Scheduled(fixedDelay=5000)
//    public void send2()
//    {
////        sendService.sendFoo2Rabbitmq(b);
//        rabbitTemplate.convertAndSend("queue.foo", new Foo("123"));
//    }
//
//    @Bean
//    public Queue fooQueue(){
//        return new  Queue("foo");
//    }
//
//    @Bean
//    public Queue newsQueue(){
//        return new  Queue("news");
//    }
//
////  接收到消息处理 接收到消息处理
//    @RabbitHandler
//   public void onMessage(@Payload String foo){
//       System.out.println(" >>> "+new Date() + ": " + foo);
//    }
//
//    @RabbitHandler
//    public void onMessage(@Payload Foo foo){
//        System.out.println(" >>> "+new Date() + ": " + foo.getName());
//    }

    public  static  int user_num=20;
    public  static  int var_user_num=1;
    private static CountDownLatch cdl =new CountDownLatch(user_num);

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException
    {
        SpringApplication.run(JacyApplication.class, args);

        new Thread(new BossLine()).start();

        String message = "Hello RabbitMQ";
        for(int i=0;i<user_num;i++){
            new Thread(new SendStringObj()).start();
            System.out.println(message+i);
            //Send(message+i,null);
        }
//        try {
//            Thread.sleep(8000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("Run produce");
    }

    public static class BossLine implements Runnable
    {
        @Override
        public void  run(){
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("到这里说明所有消息都到齐了，可以怼rabbitmq了...");

        }
    }


    public static class SendStringObj implements Runnable
    {
        @Override
        public void  run(){
            try {
                cdl.countDown();
                var_user_num++;
                senderService.Send("run======sendStringObj==========message"+var_user_num,null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }finally {
            }
        }
    }
}
