package com.example.testrabbitmq.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.ConnectionFactory;
@Component
public class ConnectionUtil {
	//文件落地的根目录
	private static String MQHOST;
	private static int MQPORT;
	//发送的队列名
	private static String MQUSER;
	//路由键
	private static String MQPASSWORD;
	//消息头键
	private static String MQVHOST;

	@Value("${MQHOST}")
	public void setMQHOST(String MQHOST) {
		ConnectionUtil.MQHOST=MQHOST;
	}
	@Value("${MQPORT}")
	public void setMQPORT(int MQPORT) {
		ConnectionUtil.MQPORT=MQPORT;
	}
	@Value("${MQUSER}")
	public void setMQUSER(String MQUSER) {
		ConnectionUtil.MQUSER=MQUSER;
	}
	@Value("${MQPASSWORD}")
	public void setMQPASSWORD(String MQPASSWORD) {
		ConnectionUtil.MQPASSWORD=MQPASSWORD;
	}
	@Value("${MQVHOST}")
	public void setMQVHOST(String MQVHOST) {
		ConnectionUtil.MQVHOST=MQVHOST;
	}


	 public static ConnectionFactory getConnectionFactory() {
		 System.out.println(MQHOST+"============="+MQPORT+"============="+MQUSER+"============="+MQPASSWORD+"============="+MQVHOST);
	        //创建连接工程，下面给出的是默认的case
	        ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost(MQHOST);
	        factory.setPort(MQPORT);
	        factory.setUsername(MQUSER);
	        factory.setPassword(MQPASSWORD);
//	        factory.setVirtualHost(MQVHOST);
	       /* factory.setHost("192.168.1.60");
	        factory.setPort(5672);
	        factory.setUsername("szz");
	        factory.setPassword("111111");
	        factory.setVirtualHost("/");*/
	        return factory;
	    }
}
