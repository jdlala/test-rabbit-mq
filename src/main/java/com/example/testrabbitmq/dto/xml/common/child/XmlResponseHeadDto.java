package com.example.testrabbitmq.dto.xml.common.child;

import com.example.testrabbitmq.dto.xml.common.XmlDataListAbstrace;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name ="RESPONSE",namespace = "http://www.pmuppc.cn")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"sendId","msgId","msgTime","Status","statusInfo","request","data",})
@Data
public class XmlResponseHeadDto implements Serializable {

    // 响应方ID
    @XmlElement(namespace = "http://www.pmuppc.cn",name = "SENDID")
    private String sendId;

    //  响应报文ID
    @XmlElement(name = "MSGID",namespace = "http://www.pmuppc.cn")
    private String msgId;

    // 时间
    @XmlElement(name = "MSGTIME",namespace = "http://www.pmuppc.cn")
    private String msgTime;

    @XmlElement(name = "STATUS_CODE",namespace = "http://www.pmuppc.cn")
    private String Status;
    // 状态描述 或原因
    @XmlElement(name = "STATUS_INFO",namespace = "http://www.pmuppc.cn")
    private String statusInfo;


 // 原请求头信息
    @XmlElementRef
    private XmlRequestHeadDto request;


    @XmlElementRef
    private XmlDataListAbstrace data;

}
