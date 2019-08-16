package com.example.testrabbitmq.tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
@Component
public class SendMsg {

	//发送的队列名
	//@Value("${PF_SEND_QUEUES}")
	private static String PF_SEND_QUEUES;
	//路由键
	//@Value("${PF_SEND_TOUTINGKEY}")
	private static String PF_SEND_TOUTINGKEY;
	//消息头键
	//@Value("${PF_SEND_HEADERKEY}")
	private static String PF_SEND_HEADERKEY;
	
	@Value("${PF_SEND_QUEUES}")
	public void setPF_SEND_QUEUES(String PF_SEND_QUEUES) {
	   SendMsg.PF_SEND_QUEUES=PF_SEND_QUEUES;

	   
	}
	@Value("${PF_SEND_TOUTINGKEY}")
	public void setPF_SEND_TOUTINGKEY(String PF_SEND_TOUTINGKEY) {
		
		SendMsg.PF_SEND_TOUTINGKEY=PF_SEND_TOUTINGKEY;
		
		
	}
	@Value("${PF_SEND_HEADERKEY}")
	public void setPF_SEND_HEADERKEY(String PF_SEND_HEADERKEY) {
	
		SendMsg.PF_SEND_HEADERKEY=PF_SEND_HEADERKEY;
		
	}
	
    public static  void publishMsg(String exchange, BuiltinExchangeType exchangeType, String toutingKey, String message,String mid)
            throws IOException, TimeoutException {
    	ConnectionFactory factory = ConnectionUtil.getConnectionFactory();

        //创建连接
        Connection connection = factory.newConnection();

        //创建消息通道
        Channel channel = connection.createChannel();

        // 声明exchange中的消息为可持久化，不自动删除
        //channel.exchangeDeclare(exchange, exchangeType, true, false, null);
        //设置消息头
        Map<String, Object> map  = new HashMap<>();
        map.put(PF_SEND_HEADERKEY, mid);
        AMQP.BasicProperties  properties =new AMQP.BasicProperties.Builder()
        		 .deliveryMode(2)//2表示持久化
                 .contentEncoding("UTF-8").headers(map).build();

        // 发布消息
        channel.basicPublish(toutingKey,exchange , properties, message.getBytes());

        channel.close();
        connection.close();
    }
    /**
	 * 发送报文
	 * @param msg	报文体
	 * @param mid	报文头
	 */
    public static  void send(String msg,String mid) throws IOException, TimeoutException{
    	SendMsg.publishMsg(PF_SEND_QUEUES, BuiltinExchangeType.DIRECT, PF_SEND_TOUTINGKEY, msg,mid);
    }
   /* public static void main(String[] args) throws IOException, TimeoutException {
    	 
          //String[] routingKey = new String[]{"aaa", "bbb"};
          String msg = "hello >>> ";


        for (int i = 0; i < 10000; i++) {
        	  //SendMsg.publishMsg("firsts", BuiltinExchangeType.DIRECT, routingKey[0], "firsts"+98);
        	 SendMsg.publishMsg("PF", BuiltinExchangeType.DIRECT, "abcd1234", "p"+i);
         }
          System.out.println("----over-------");
    
    	
	}*/
}