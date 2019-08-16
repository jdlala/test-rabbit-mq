package com.example.testrabbitmq.controller;

import com.example.testrabbitmq.tools.ReceiveMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
@Order
@Slf4j
public class ListenQueue implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                        long start = System.currentTimeMillis();
//                        ReceiveMsg.consumerMsg("", MQQUEUS2, "");
                    ReceiveMsg.receive();
//                        Long sec = (System.currentTimeMillis() - start) / 1000;
//                        log.info("alarm ==> 执行完毕！用时{}秒", sec.intValue());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
