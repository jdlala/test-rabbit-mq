package com.example.testrabbitmq.dto.xml;

import com.example.testrabbitmq.dto.xml.common.XmlDataListAbstrace;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;


@XmlRootElement(name = "ENT_ITEM",namespace = "http://www.pmuppc.cn")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class XmlEntItemDto extends XmlDataListAbstrace implements Serializable {
    @XmlElements(value = {@XmlElement(type = PmItemFlat.class,name = "EntItem")})
    private List<PmItemFlat> item;


    public XmlEntItemDto(){};
    public XmlEntItemDto(List<PmItemFlat> pmItem) {
        this.item = pmItem;
    }
}
