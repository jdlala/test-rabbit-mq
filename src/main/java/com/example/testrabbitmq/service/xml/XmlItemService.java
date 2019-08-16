package com.example.testrabbitmq.service.xml;

import com.example.testrabbitmq.dto.xml.XmlEntItemDto;
import com.example.testrabbitmq.dto.xml.XmlItemDto;
import com.example.testrabbitmq.service.look.LookService;
import com.example.testrabbitmq.tools.bean2xml.Bean_XmlTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  返回 xml 字符串格式
 *
 *
 * */
@Service
@Slf4j
public class XmlItemService {

    @Autowired
    private LookService lookService;

    private Bean_XmlTool bean_xml = new Bean_XmlTool();

    /**
     * 所有费项
     * */
    public String getItemAll(){
        List itemAll = lookService.getItemAll();
        XmlItemDto xmlItemDto = new XmlItemDto(itemAll);
        return bean_xml.bean2xml(xmlItemDto);
    }


    /**
     *  根据企业信用代码  查询企业公示费项
     * @param entCode 不能为null
     * */
    public String getEntItem(String entCode){

        if (StringUtils.isEmpty(entCode)){
            return "entCode is null";
        }
        List entItem = lookService.getEntItem(entCode);
        if (entItem.size() == 0){
            return "ent is not exist";
        }
        XmlEntItemDto xmlEntItemDto = new XmlEntItemDto(entItem);
        return bean_xml.bean2xml(xmlEntItemDto);
    }
}
