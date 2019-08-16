package com.example.testrabbitmq.tools.bean2xml;

import com.example.testrabbitmq.dto.xml.XmlEntItemDto;
import com.example.testrabbitmq.dto.xml.common.XmlDataListAbstrace;
import com.example.testrabbitmq.dto.xml.common.XmlMessageDto;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileExistsException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;


@Slf4j
public class Bean_XmlTool {

    /**
     *  对象 转换为 xml格式数据
     * */
    public String bean2xml(Object ob){
        StringWriter writer = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ob.getClass(), XmlEntItemDto.class);
            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
                @Override
                public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
                    if (namespaceUri.equals("http://www.pmuppc.cn")){
                        return "pm";
                    }

                    return suggestion;
                }
            });
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.marshal(ob,writer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

    /**
     *
     *  xml 请求和响应 封装成xml字符串
     * @param ob xml模板
     * @Param  chiled  返回的数据对象，必须继承 XmlDataListAbstrace
     * */
    public String beanToXmlResponse(Object ob ,Class <? extends XmlDataListAbstrace> chiled){
        StringWriter writer = new StringWriter();
        try {
            if (chiled == null){
                chiled = XmlDataListAbstrace.class;
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlMessageDto.class,XmlDataListAbstrace.class,chiled);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
                @Override
                public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
                    if (namespaceUri.equals("http://www.pmuppc.cn")){
                        return "pm";
                    }
                    return suggestion;
                }
            });

            marshaller.marshal(ob,writer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

/**
 *  根据xml文件路径 ，转换为对象
 * */
    public <T> T xmlToBean(Class<T> cl,File file) throws JAXBException {



        if (!file.exists()){
            new FileExistsException("文件不存在");
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JAXBContext jaxbContext = null;
        T msg = null;

            jaxbContext = JAXBContext.newInstance(cl);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            msg =(T) unmarshaller.unmarshal(fileInputStream);



        return msg;
    }

}
