package com.example.testrabbitmq.dto.xml.common.child;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 *  请求头签名
 * */
@XmlRootElement(namespace = "http://www.pmuppc.cn")
@XmlType(propOrder = {"requestType","entId",})
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class XmlRequestInfoDto {

    @XmlElement(name = "REQUESTTYPE",namespace = "http://www.pmuppc.cn")
    private String requestType;

    @XmlElement(name = "ENTID",namespace = "http://www.pmuppc.cn")
    private String entId ;




}
