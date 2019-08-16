package com.example.testrabbitmq.dto.xml;

import com.example.testrabbitmq.dto.xml.common.XmlDataListAbstrace;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class XmlItemDto extends XmlDataListAbstrace implements Serializable {
    @XmlElements(value = {@XmlElement(type = PmItemFlat.class, name = "Item")})
    private List<PmItemFlat> item;

    public XmlItemDto(){}
    public XmlItemDto(List<PmItemFlat> item) {
        this.item = item;
    }

    @XmlTransient
    public List<PmItemFlat> getItem() {
        return item;
    }

    public void setItem(List<PmItemFlat> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "XmlItemDto{" +
                "item=" + item +
                '}';
    }

}
