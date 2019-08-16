package com.example.testrabbitmq.dto.xml.common.child;


import com.example.testrabbitmq.dto.xml.common.XmlDataListAbstrace;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *  xml 返回数据对象
 *  返回数据实体必须继承 XmlDataListAbstrace 抽象类
 * */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class XmlResponseBodyDto implements Serializable {

    @XmlElementRef
    private XmlDataListAbstrace data ;


}
