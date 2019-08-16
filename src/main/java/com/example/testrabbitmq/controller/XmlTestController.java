package com.example.testrabbitmq.controller;

import com.example.testrabbitmq.dto.xml.XmlEntItemDto;
import com.example.testrabbitmq.dto.xml.common.child.XmlRequestHeadDto;
import com.example.testrabbitmq.dto.xml.common.child.XmlRequestInfoDto;
import com.example.testrabbitmq.dto.xml.common.child.XmlResponseHeadDto;
import com.example.testrabbitmq.service.look.LookService;
import com.example.testrabbitmq.service.xml.XmlMsgService;
import com.example.testrabbitmq.tools.SunTools;
import com.example.testrabbitmq.tools.bean2xml.Bean_XmlTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class XmlTestController {

    @Autowired
    private XmlMsgService xmlMsgService;

    @Autowired
    private LookService lookService;

    private SunTools sunTools = SunTools.getInstance();

    private Bean_XmlTool bean_xml = new Bean_XmlTool();

    @RequestMapping("/xml/list")
    public String XmlT(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //response.setHeader("Content-Type","application/xml;charset=UTF-8");
        response.setContentType("application/xml");
        response.setCharacterEncoding("UTF-8");
        File path = new File("C:/TEST/request.xml");
        String s = xmlMsgService.msgResponse(path);

       /* XmlMessageDto xmlMessageDto = new XmlMessageDto();
        xmlMessageDto.setRequest(requestHeadDto());*/


       // String s = bean_xml.beanToXmlResponse(responseHeadDto(),XmlEntItemDto.class);

      //  XmlRequestHeadDto xmlRequestHeadDto = bean_xml.xmlToBean(XmlRequestHeadDto.class, "C:/TEST/request.xml");
      //  System.out.println(xmlRequestHeadDto);
        FileWriter fileWriter = new FileWriter("C:/TEST/response.xml");
       // FileOutputStream fileOutputStream = new FileOutputStream("C:/TEST/response.xml");
        fileWriter.write(s);
        fileWriter.close();
        return s;
    }


    /**
     * 响应信息
     * */
    private XmlResponseHeadDto responseHeadDto(){

        XmlResponseHeadDto dto = new XmlResponseHeadDto();
        dto.setMsgId(sunTools.get32UUID());
        dto.setStatus("200");
        dto.setSendId(sunTools.get32UUID());
        dto.setStatusInfo("SUCCESS");
        dto.setMsgTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        dto.setRequest(requestHeadDto());

        List entItem1 = lookService.getEntItem("ZZJGD1543477957388");
        XmlEntItemDto xmlEntItemDto = new XmlEntItemDto(entItem1);
        dto.setData(xmlEntItemDto);

        return dto;
    }


// 请求头
    private XmlRequestHeadDto requestHeadDto(){
        XmlRequestHeadDto request = new XmlRequestHeadDto();
        request.setMsgId(sunTools.get32UUID());
        request.setSendId(sunTools.get32UUID());
        request.setMsgTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        XmlRequestInfoDto requestInfoDto = new XmlRequestInfoDto();
        requestInfoDto.setEntId("ZZJGD1543477957388");
        requestInfoDto.setRequestType("2");
        request.setRequestInfoinfo(requestInfoDto);
        return request;
    }


}
