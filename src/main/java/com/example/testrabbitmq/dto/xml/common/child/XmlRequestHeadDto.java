package com.example.testrabbitmq.dto.xml.common.child;


import lombok.Data;
import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
* @author Shun
 *  xml 请求头信息
 *
 *
* */

@XmlRootElement(namespace = "http://www.pmuppc.cn",name = "REQUEST")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "sendId","msgId","msgTime","requestInfoinfo",
})
@Data
public class XmlRequestHeadDto implements Serializable {
    // 发送方ID

   @XmlElement(namespace = "http://www.pmuppc.cn",name = "SENDID")
    protected String sendId;
    // 报文Id
    @XmlElement(name = "MSGID",namespace = "http://www.pmuppc.cn")
    protected String msgId;

    // 请求时间
    @XmlElement(name = "MSGTIME",namespace = "http://www.pmuppc.cn")
    protected String msgTime;


    // 签名信息
    //@XmlElement(name = "signatureDto",namespace = "http://www.pmuppc.cn")
   // private XmlSignatureDto signatureDto = new XmlSignatureDto();
    @XmlElement(name = "REQUESTTYPE",namespace = "http://www.pmuppc.cn")
    protected XmlRequestInfoDto requestInfoinfo;



}
