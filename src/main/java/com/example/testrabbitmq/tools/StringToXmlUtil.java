package com.example.testrabbitmq.tools;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class StringToXmlUtil{

    /**
     * 字符串转xml文件并保存指定路径
     * @param xmlStr  xml字符串
     * @param fileName  文件名称
     * @param type  文件类型(后缀)
     * @param path  (存放路径)
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    @SuppressWarnings("deprecation")
    public static String createXml(String xmlStr,String fileName,String type,String path) throws Exception{
        try {
            Document doc = strToDocument(xmlStr);
            String realPath = path + fileName + type;
            // 判断文件是否存在，如存在就删掉它
            File file = new File(realPath);
            if (!file.getParentFile().exists()) {
                //如果不存在则创建
                file.getParentFile().mkdirs();
            }
            //将document中的内容写入文件中
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(realPath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("文件保存失败！");
        }
        //返回文件保存路径
        return path + fileName + type;
    }

    /**
     * 字符串转Document
     * @param xmlStr
     */
    public static Document strToDocument(String xmlStr) throws ParserConfigurationException, SAXException, IOException {
        Document doc = null;
        StringReader sr = new StringReader(xmlStr);
        InputSource is = new InputSource(sr);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        doc = builder.parse(is);
        return doc;
    }
}
