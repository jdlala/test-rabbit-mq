package com.example.testrabbitmq.service.xml;


import com.example.testrabbitmq.Enum.XmlUrlCodingEnumDto;
import com.example.testrabbitmq.dto.xml.XmlEntItemDto;
import com.example.testrabbitmq.dto.xml.common.child.XmlRequestHeadDto;
import com.example.testrabbitmq.dto.xml.common.child.XmlRequestInfoDto;
import com.example.testrabbitmq.dto.xml.common.child.XmlResponseHeadDto;
import com.example.testrabbitmq.service.itemXmlAnalysisService;
import com.example.testrabbitmq.service.look.LookService;
import com.example.testrabbitmq.tools.SunTools;
import com.example.testrabbitmq.tools.bean2xml.Bean_XmlTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  xml 报文处理
 *  报文解析、验证，返回数据封装
 *
 * */
@Component
@Slf4j
public  class XmlMsgService {


    private Bean_XmlTool bean_xml = new Bean_XmlTool();

    private SunTools sunTools = SunTools.getInstance();


    @Autowired
    private LookService lookService;
    @Autowired
    private com.example.testrabbitmq.service.itemXmlAnalysisService itemXmlAnalysisService;

    /**
     *  返回的数据，
     *  根据需求扩充
     *
     * */
    private void getData(XmlUrlCodingEnumDto enu){
        // 企业费项
        if (XmlUrlCodingEnumDto.EntItem.getName().equals(enu.getName())){
            enu.setDataPojo( new XmlEntItemDto(lookService.getEntItem(enu.getParams())));


        }else if (XmlUrlCodingEnumDto.Item.getName().equals(enu.getName())){
            enu.setDataPojo(new XmlEntItemDto(lookService.getItemAll()));
        }

    }



    /**
     *  解析xml 请求，并响应
     *
     * */
    public String msgResponse(File xmlPath){

       // 解析xml文件，转换成实体
        XmlRequestHeadDto requestHead = null;
        try {
            requestHead = bean_xml.xmlToBean(XmlRequestHeadDto.class, xmlPath);
        } catch (Exception e) {
            // 解析失败，发送错误信息
                log.error("文件解析失败");
            requestHead = new XmlRequestHeadDto();
            XmlRequestInfoDto requestInfo = new XmlRequestInfoDto();
            requestInfo.setRequestType("102");
            requestHead.setRequestInfoinfo(requestInfo);
        }

        // 获取请求信息,验证
        XmlRequestInfoDto requestInfo = requestHead.getRequestInfoinfo();

        String code = itemXmlAnalysisService.entCheck(requestInfo.getEntId(),"");

        XmlUrlCodingEnumDto anEnum = XmlUrlCodingEnumDto.getEnum(requestInfo.getRequestType(),
                requestInfo.getEntId(),code);

        XmlResponseHeadDto responseHead = responseHead(anEnum);
        // 分装请求数据
        responseHead.setRequest(requestHead);
        // 获取返回数据，封装
        if (anEnum.getCode() == 200){
            // 获取响应数据
            getData(anEnum);
            // 封装返回数据对象
            responseHead.setData(anEnum.getDataPojo());
            try{
                // 移动文件
                fileMoveN(xmlPath);
            }catch (Exception e){
                e.printStackTrace();
                log.error("移动文件失败");
            }
        }else {
            try{
                fileMoveE(xmlPath);
            }catch (Exception e){
                e.printStackTrace();
                log.error("移动文件失败");
            }
        }

        // 转换成xml 字符串
        String s = bean_xml.beanToXmlResponse(responseHead, anEnum.getaClass());
        // 移动文件 err




        return s;
    }

    /**
     *  移动文件 到normal
     * */
    private void fileMoveN(File file) throws IOException {
        String fileName = file.getName();
        String patn = "C:\\TEST\\normal\\"+fileName;
        Files.move(Paths.get(file.getAbsolutePath()),Paths.get(patn));
    }

    private void fileMoveE(File file) throws IOException {
        String fileName = file.getName();
        String patn = "C:\\TEST\\err\\"+fileName;
        Files.move(Paths.get(file.getAbsolutePath()),Paths.get(patn));

    }




    /**
     *  封装响应头信息
     *
     * */
    private XmlResponseHeadDto responseHead(XmlUrlCodingEnumDto anEnum){
        XmlResponseHeadDto response = new XmlResponseHeadDto();
        response.setStatus(anEnum.getCode().toString());
        response.setStatusInfo(anEnum.getStatusInfo());
        response.setMsgTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        response.setMsgId(sunTools.get32UUID());
        response.setSendId(sunTools.get32UUID());
        return response;
    }









}
