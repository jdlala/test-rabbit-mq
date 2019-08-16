package com.example.testrabbitmq.tools;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
@Component
@Slf4j
public class ReceiveMsg {

    //获取本地存储路径
	private static String filepath;

	//文件落地的根目录
//	private static String PF_RECEIVE_sharedDiskDir;
	private static String PF_RECEIVE_EXCHANGE;
	//发送的队列名
	private static String PF_RECEIVE_QUEUES;
	//路由键
	private static String PF_RECEIVE_TOUTINGKEY;
	//消息头键
//	private static String PF_RECEIVE_HEADERKEY;
	
//	@Value("${PF_RECEIVE_sharedDiskDir}")
//	public void setPF_RECEIVE_sharedDiskDir(String PF_RECEIVE_sharedDiskDir) {
//		ReceiveMsg.PF_RECEIVE_sharedDiskDir=PF_RECEIVE_sharedDiskDir;
//
//
//	}
	@Value("${PF_RECEIVE_EXCHANGE}")
	public void setPF_RECEIVE_EXCHANGE(String PF_RECEIVE_EXCHANGE) {
		ReceiveMsg.PF_RECEIVE_EXCHANGE=PF_RECEIVE_EXCHANGE;

	   
	}
	@Value("${PF_RECEIVE_QUEUES}")
	public void setPF_RECEIVE_QUEUES(String PF_RECEIVE_QUEUES) {
		
		ReceiveMsg.PF_RECEIVE_QUEUES=PF_RECEIVE_QUEUES;
		
		
	}
	@Value("${PF_RECEIVE_TOUTINGKEY}")
	public void setPF_RECEIVE_TOUTINGKEY(String PF_RECEIVE_TOUTINGKEY) {
	
		ReceiveMsg.PF_RECEIVE_TOUTINGKEY=PF_RECEIVE_TOUTINGKEY;
		
	}
//	@Value("${PF_RECEIVE_HEADERKEY}")
//	public void setPF_RECEIVE_HEADERKEY(String PF_RECEIVE_HEADERKEY) {
//
//		ReceiveMsg.PF_RECEIVE_HEADERKEY=PF_RECEIVE_HEADERKEY;
//
//	}

    @Value("${locoal.filepath}")
    public void setFilepath(String filepath){
	    ReceiveMsg.filepath = filepath;
    }

    public static void consumerMsg(String exchange, String queue, String routingKey)
            throws IOException, TimeoutException {
        ConnectionFactory factory = ConnectionUtil.getConnectionFactory();

        //创建连接
        Connection connection = factory.newConnection();

        //创建消息信道
        final Channel channel = connection.createChannel();

        //消息队列
        channel.queueDeclare(queue, true, false, false, null);
        //绑定队列到交换机
        //channel.queueBind(queue, exchange, routingKey);
        System.out.println("[*] Waiting for message. To exist press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,byte[] body) throws IOException {
//            	Object header =  properties.getHeaders().get(PF_RECEIVE_HEADERKEY);
            	String message = new String(body, "UTF-8");
                
                try {
					String str = StringToXmlUtil.createXml(message, "ITEM"+ Tools.get32UUID(),".xml",filepath);
                    log.info(str);
                    log.info("channel__1__StreamReceiver: {}", message);
                } catch (Exception e) {
					e.printStackTrace();
				} finally {
                    //System.out.println(" [x] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        // 取消自动ack
        channel.basicConsume(queue, false, consumer);
    }
    public static void receive() throws IOException, TimeoutException{
    	System.out.println(PF_RECEIVE_EXCHANGE);
    	System.out.println(PF_RECEIVE_QUEUES);
    	System.out.println(PF_RECEIVE_TOUTINGKEY);
    	ReceiveMsg.consumerMsg(PF_RECEIVE_EXCHANGE,PF_RECEIVE_QUEUES,PF_RECEIVE_TOUTINGKEY);
    }



    /*   public static void main(String[] args) throws InterruptedException, IOException, TimeoutException {
       
        String[] routingKey = new String[]{"aaa", "bbb"};
        String[] queueNames = new String[]{"qa", "qb"};


        for (int i = 0; i < 2; i++) {
        	ReciveMsg.consumerMsg("PF","PF", "abcd1234");
        }

        //Thread.sleep(1000 * 60 * 10);
    }*/
}
