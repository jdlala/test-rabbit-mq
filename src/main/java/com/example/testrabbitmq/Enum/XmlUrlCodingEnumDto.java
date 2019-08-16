package com.example.testrabbitmq.Enum;

import com.example.testrabbitmq.dto.xml.XmlEntItemDto;
import com.example.testrabbitmq.dto.xml.common.XmlDataListAbstrace;
import org.apache.commons.lang3.StringUtils;

/**
 *  Urlcoding 自定义编码，
 * */
public enum XmlUrlCodingEnumDto {
    NoCode(null,500,null,null,"报文格式错误", XmlDataListAbstrace.class),
    NoParam(null,500,null,null,"报文格式错误",XmlDataListAbstrace.class),
    NoEnt(null,50001,null,null,"企业未在平台注册",XmlDataListAbstrace.class),
    NoEntItem(null,50002,null,null,"企业未公示费项",XmlDataListAbstrace.class),
    PassEx("102",500,null,null,"报文格式错误",XmlDataListAbstrace.class),
    Item("1", 200,null ,null,"SUCCESS", XmlEntItemDto.class),
    EntItem("2", 200,null,null,"SUCCESS",XmlEntItemDto.class);



    // 请求编码
    private  String name;
    // 验证状态吗
    private Integer code;
    // 获取数据的实体类
    private XmlDataListAbstrace dataPojo;

    private Class aClass;
    // 参数
    private String params;
    // 状态信息
    private String statusInfo;

    private   XmlUrlCodingEnumDto(String name, Integer code, XmlDataListAbstrace dataPojo, String params, String msgInfo,Class aClass) {
        this.name = name;
        this.code = code;
        this.dataPojo = dataPojo;
        this.params = params;
        this.statusInfo = msgInfo;
        this.aClass =aClass;
    }

    public static XmlUrlCodingEnumDto getEnum(String name, String params,String code){
        XmlUrlCodingEnumDto a = null;
        switch (name){
            case "1":a = XmlUrlCodingEnumDto.Item;break;
            case "2":a= XmlUrlCodingEnumDto.EntItem.entItem(params,code);break;
            case "102": a = XmlUrlCodingEnumDto.PassEx;
            default:a=XmlUrlCodingEnumDto.NoCode;
        }
        return a;
    }

    /**
     *  验证企业费项参数
     * */
    private XmlUrlCodingEnumDto entItem(String params,String code){
        if (StringUtils.isEmpty(params)){
            return XmlUrlCodingEnumDto.NoParam;
        }
        if (StringUtils.equals(code,"notRegister")){
            //企业未注册
            return XmlUrlCodingEnumDto.NoEnt;
        } else if (StringUtils.equals(code,"notHasItem")){
            //企业没有公示费项
            return XmlUrlCodingEnumDto.NoEntItem;
        }
        this.params = params;
        return this;

    }


    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    public XmlDataListAbstrace getDataPojo() {
        return dataPojo;
    }

    public String getParams() {
        return params;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setDataPojo(XmlDataListAbstrace dataPojo) {
        this.dataPojo = dataPojo;
    }

    public Class getaClass() {
        return aClass;
    }
}
