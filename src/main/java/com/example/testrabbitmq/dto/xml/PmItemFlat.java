package com.example.testrabbitmq.dto.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @version 1.0.0
 * @auther ssj
 * @Date: 18-8-15 17:41
 * @Description: 费项， 扁平化， 不涉及关联关系
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class PmItemFlat  {

    @XmlElement(namespace = "http://www.pmuppc.cn")
    // 费项id itemId
    private String itemId;
    // 费项名称 itemName
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String itemName;
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String belongsId;
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String belongs;

    // 子项id itemDetailId
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String itemDetailId;
    // 子项名称 itemDetailName
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String itemDetailName;

    //  收费主体
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String businessSubject;

    // 定价类型 priceType
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String priceType;
    // 收费类型 chargeType
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String chargeType;


    // 价格id priceId
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String priceId;
    // 价格名称
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String priceName;

    // 计费规格 specifications
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String specifications;
    // 货物品类 goodsType
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String goodsType;
    // 贸易类型 tradeType
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String tradeType;


    // 币种 currency
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String currency;
    // 计费单位 chargeUnit
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String chargeUnit;
    // 最低价 priceLowerest
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String priceLowerest;
    // 最高价 priceHighest
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String priceHighest;


    // 企业部分 独有
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String entName;
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String entCode;
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String entPriceLowerest;
    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String entPriceHighest;



    @XmlElement(namespace = "http://www.pmuppc.cn")
    private String err;






}
