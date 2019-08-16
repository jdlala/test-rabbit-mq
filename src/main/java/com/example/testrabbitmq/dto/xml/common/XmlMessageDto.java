package com.example.testrabbitmq.dto.xml.common;
import com.example.testrabbitmq.dto.xml.common.child.XmlRequestHeadDto;
import com.example.testrabbitmq.dto.xml.common.child.XmlResponseBodyDto;
import com.example.testrabbitmq.dto.xml.common.child.XmlResponseHeadDto;
import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(namespace = "http://www.pmuppc.cn",name = "uppc_message")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"request","response","body",})
@Data
public class XmlMessageDto {
    // 请求头
    @XmlElement(namespace = "http://www.pmuppc.cn",name = "Request_Head")
    private XmlRequestHeadDto request ;

    // 响应头
    @XmlElement(namespace = "http://www.pmuppc.cn",name = "Response_Head")
    private XmlResponseHeadDto response ;

    @XmlElement(namespace = "http://www.pmuppc.cn",name = "Response_body")
    private XmlResponseBodyDto body ;

}
