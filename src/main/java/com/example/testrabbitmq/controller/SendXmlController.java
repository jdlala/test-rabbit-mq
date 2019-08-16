package com.example.testrabbitmq.controller;

import com.example.testrabbitmq.tools.SendMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@Slf4j
public class SendXmlController {

    @RequestMapping("/send/xml/test")
    public void sendXmlTest(){
        try {
            SendMsg.send(xmlstr,"123456");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static String xmlstr="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<pm:REQUEST xmlns:pm=\"http://www.pmuppc.cn\">\n" +
            "    <pm:SENDID>73152d651e1d467792de09d072dbcbb4</pm:SENDID>\n" +
            "    <pm:MSGID>d795469352a1440dbefaec7e49407bec</pm:MSGID>\n" +
            "    <pm:REQUESTTYPE>\n" +
            "        <pm:REQUESTTYPE>2</pm:REQUESTTYPE>\n" +
            "        <pm:ENTID>ZZJGD1543477957388</pm:ENTID>\n" +
            "    </pm:REQUESTTYPE>\n" ;
}
