package com.example.testrabbitmq.tools;
/**
* 常用工具类
 *  getDate(String date)  将时间字符串转换为Date
 *  getStartTime(String date) 获取date时间的开始时间
 *  getEndTime(String date)  获取date时间的结束时间
 *  getAllDateList(String st,String en) 获取某时间内所有日期的集合
 *  getMouthDayNumber(String yearMouth) 获取某月的天数
 *  dateJoint(Object y,Object m,Object d) 用“-”拼接字符串
* */

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class SunTools {

    private SunTools(){}

    private static class SunToolsInstance{
        private static final SunTools INSTANCE =new SunTools();
    }
    /*
    *  使用单例模式，获取对象
    * */
    public static SunTools getInstance(){
        return SunToolsInstance.INSTANCE;
    }

    /**
     * 获得同比时间
     *
     * */
    public String getYOYDate(String date) throws ParseException {
        date=replace(date);
        String formate = sdfFormat(date);
        SimpleDateFormat format = new SimpleDateFormat(formate);
        Date parse = format.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse);
        calendar.add(Calendar.YEAR,-1);
        return format.format(calendar.getTime());
    }


    /**
     * 将时间字符串转换为Date
     * @param date String
     * @return Date
     * */
    public  Date getDate(String date) throws ParseException {
        date = replace(date);
        return new SimpleDateFormat(sdfFormat(date)).parse(date);
    }

    /**
     *  获取date时间的开始时间；格式 2019-1-1 或 2019/1/1
     *  年月日通用
     * @param date
     * @return Date
    */
    public Date getStartTime(String date) {
        date = replace(date);
        Date parse = null;
        try {
            parse = new SimpleDateFormat(sdfFormat(date)).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    /**
     *  获取date时间的结束时间；格式 2019-1-1 或 2019/1/1
     *  年月日通用
     * @param date
     * @return Date
     * */
    public  Date getEndTime(String date)  {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        date = replace(date);
        Date parse = null;
        try {
            parse = new SimpleDateFormat(sdfFormat(date)).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(parse);
        if (date.indexOf("-") < 0){
            // 年
            calendar.add(Calendar.YEAR,1);
            calendar.set(Calendar.DAY_OF_YEAR,1);
        }else if (date.lastIndexOf("-") > 4){
            // 日
            calendar.add(Calendar.DATE,1);
        }else {
            // 月
            calendar.add(Calendar.MONTH,1);
            calendar.set(Calendar.DAY_OF_MONTH,1);
        }
        return calendar.getTime();
    }

    /**
     *  获取某时间内所有日期的集合
     *  类型：2018-1-1  2018/1/1
     * @param st 开始时间
     * @param en 结束时间
     * */
    public List getAllDateList(String st,String en) throws ParseException {
        st =  replace(st);
        en = replace(en);
        List list = null;
        if (st.indexOf("-") < 0){
            // 年
             list = yearList(st, en);
        }else if (st.lastIndexOf("-") > 4){
            // 日
             list = dayList(st,en);
        }else {
            // 月
            list = mouthList(st, en);
        }
        return list;
    }

    /**
     * 获取某月的天数
     * @param yearMouth  2018-1 2018/1
     * */
    public Integer getMouthDayNumber(String yearMouth) throws ParseException {
        Date date = new SimpleDateFormat(sdfFormat(yearMouth)).parse(yearMouth);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     *  用“-”拼接字符串
     *
     * */
    public String dateJoint(Object y,Object m,Object d){
        StringBuilder builder = new StringBuilder(y.toString());
        if (m == null || m.toString().isEmpty()){
            return  builder.toString();
        }
        if (m.toString().length() == 2){
            builder.append("-").append(m.toString());
        }else {
            builder.append("-").append("0" + m.toString());
        }

        if (d == null || d.toString().isEmpty()){
            return builder.toString();
        }
        if (d.toString().length() == 2){
            builder.append("-").append(d.toString());
        }else {
            builder.append("-").append("0" + d.toString());
        }

        return builder.toString();
    }


    /**
     *  获得天数的集合
     * */
    private List<String> dayList(String st,String en) throws ParseException {
        ArrayList<String> list = new ArrayList<>();
        List<String> mouthList = mouthList(st.substring(0, st.lastIndexOf("-")), en.substring(0, en.lastIndexOf("-")));
        Integer stD = Integer.valueOf(st.substring(st.lastIndexOf("-")+1));
        Integer enD = Integer.valueOf(en.substring(en.lastIndexOf("-")+1));
        String YM;
        Integer tempDay;
        for (int i = 0;i<mouthList.size();i++){
            YM = mouthList.get(i);
            tempDay =getMouthDayNumber(YM);
            if (i == mouthList.size()-1){
                tempDay = enD;
            }
            for (int j = stD;j <= tempDay;j++){
                list.add(dateJoint(YM,j,null));
            }
            stD = 1;
        }
        return  list;
    }

    /*
     * 获得某时间内月的集合
     * */
    private List<String> mouthList(String st,String en){
        ArrayList<String> list = new ArrayList<>(12);
        Integer stY = Integer.valueOf(st.split("-")[0]);
        Integer stM = Integer.valueOf(st.split("-")[1]);
        Integer enY = Integer.valueOf(en.split("-")[0]);
        Integer enM = Integer.valueOf(en.split("-")[1]);
        List<String> yearList = yearList(stY.toString(), enY.toString());
        String year;
        int emt = yearList.size() == 1?enM:12;
        for(int i = 0;i<yearList.size();i++) {
            year = yearList.get(i);
            if (i == yearList.size()-1){
                emt = enM;
            }
            for (int j = stM; j <= emt; j++) {
                list.add(dateJoint(year, j, null));
            }
            stM = 1;
        }
        return list;
    }

    /*
    *
    * 获取某时间内年的集合
    * */
    private List<String> yearList(String st,String en){
        ArrayList<String> list = new ArrayList<>(12);
        Integer start = Integer.valueOf(st);
        Integer end = Integer.valueOf(en);
        for (;start < end;){
            list.add(start.toString());
            start ++ ;
        }
        list.add(start.toString());
        return list;
    }

     // -替换/
    private String replace(String string){
        return string.replace("/","-");
    }
    // 根据参数判断SimplFormat参数格式
    private String sdfFormat(String date){
        return date.split("-").length == 1?"yyyy":date.split("-").length == 2?"yyyy-MM":"yyyy-MM-dd";
    }

    /**
     *  将集合封装成excel,xlsx格式
     *
     * */
    public XSSFWorkbook creatExcel(List<Map<String,Object>> list,Integer width) {
        if (list.isEmpty()){
            throw  new NullPointerException("数据不能为空");
        }
        XSSFRow rows;
        int row = 0;
        int cell = 0;
        List<String> title = new ArrayList<>(list.get(row).size());
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        // 第一行添加字段名
        Map fieldName = list.get(row);
        if (!fieldName.isEmpty()){
            Set keySet = fieldName.keySet();
            Iterator iterator = keySet.iterator();
            rows = sheet.createRow(row);
            // 放入字段名
            String temp;
            // 第一列放序号
            rows.createCell(cell).setCellValue("序号");
            // 设置行宽
            sheet.setColumnWidth(cell, 256*5+184);
            cell ++;
            while (iterator.hasNext()){
                temp = iterator.next().toString();
                title.add(temp);
                rows.createCell(cell).setCellValue(temp);
                cell++;
            }
            // 数据从第2行开始
            row = 1;
        }
        Map<String, Object> map;
        // 行
        for ( int i = 0; i<list.size();i++){

            sheet.setColumnWidth(row, 256*width+184);
            //列
            rows= sheet.createRow(row);
            map = list.get(i);
            cell = 0;
            rows.createCell(cell).setCellValue(i+1);
            li:
            for (;cell < map.size(); cell++ ){
                if (map.get(title.get(cell)) == null){
                    continue li;
                }
                rows.createCell(cell+1).setCellValue(map.get(title.get(cell)).toString());
            }
            row++;
        }
        return workbook;
    }

    /**
     *  设置下载响应头，
     *  fileName 包含文件后缀
     * */
    public HttpServletResponse getDownLoadHttpHead(HttpServletResponse response,String fileName) throws UnsupportedEncodingException {
        response.setContentType("application/octet-stream");// 设置强制下载不打开
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;fileName="+ new String(fileName.getBytes("GB2312"),"ISO-8859-1"));
        return response;
    }

    /*
    *  将excel 转化为list集合，
    * 返回结构 List<Map> ,
    *  每行数据保存为一个Map
    *  excel 第一行作为Map的key
    *
    * */
    public List<Map> readExcel(MultipartFile file) throws IOException, InvalidFormatException {
        List<Map> resList = new ArrayList<>();
        Map emptMap;
        InputStream inputStream = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream);
        // 获得第一张表
        Sheet sheet = workbook.getSheetAt(0);
        // 获得最后一行，下标数字
        int lastRowNum = sheet.getLastRowNum();
        // 遍历行
        Row row;
        List key = new ArrayList(lastRowNum+1);
        rows:
        for (int i = 0; i<lastRowNum + 1;i++){
             row = sheet.getRow(i);
            emptMap = new HashMap(row.getLastCellNum());

            // 读取第一行，作为key
            if (i==0){
                for (int j = 0;j<row.getLastCellNum(); j++){
                    key.add(row.getCell(j));
                }
                continue rows;
            }
            // 从第二行开始，到n行为value
             for (int j = 0;j<row.getLastCellNum(); j++){
                  emptMap.put(key.get(j).toString(),getCellValueByCellType(row.getCell(j)));
             }
             resList.add(emptMap);
        }
        return resList;
    }

    private Object getCellValueByCellType(Cell cell){
        if (cell == null){
            return null;
        }
        /*
            int CELL_TYPE_NUMERIC = 0;
        *   int CELL_TYPE_STRING = 1;
            int CELL_TYPE_FORMULA = 2;
            int CELL_TYPE_BLANK = 3;
            int CELL_TYPE_BOOLEAN = 4;
            int CELL_TYPE_ERROR = 5;
        * */
        Object object = null;
        switch (cell.getCellType()){
            case 0:
                // 判断时间类型，数字类型
                if (HSSFDateUtil.isCellDateFormatted(cell)){
                    object = cell.getDateCellValue();
                }else {
                    object = cell.getNumericCellValue();
                }
                break;
            case 1: object = cell.getStringCellValue().trim();break;
            // 数组
            case 2: object = cell.getArrayFormulaRange();break;
            case 3: object = "";break;
            case 4: object = cell.getBooleanCellValue(); break;
            case 5: object = cell.getErrorCellValue(); break;
            default:break;
        }
        return object;
    }


    /**
     *  获取客户端ip
     *
     * */
    public String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && "unKnown".equals(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1){
                return ip.substring(0,index);
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && "unKnown".equals(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     *  对实现序列化的对象，进行深度克隆
     *
     * */
    public  <T extends Serializable> T serializableClone( T  obj) {
        T object1 = null;
        try {
            //写入字节流
            ByteArrayOutputStream byt = new ByteArrayOutputStream( );
            ObjectOutputStream obs = new ObjectOutputStream(byt);
            obs.writeObject(obj);
            obs.close();
            //分配内存，写入原始对象，生成新对象
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byt.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream( inputStream);
            //返回生成的新对象
            object1 = (T)objectInputStream.readObject( );
            objectInputStream.close();
        } catch (Exception e) {
            log.info("克隆对象失败");
            e.printStackTrace( );
        }
        return object1;
    }
/**
*  将oldPath 文件 拷贝到 newPath 文件下
* */
    public String fileCopy(String oldFilePath,String newPath){
        File oldFile = new File(oldFilePath);
        if (!oldFile.exists()){
            return "文件不存在："+ oldFilePath;
        }
        File newFile = new File(newPath);
        // 文件夹是否存在
        if (!newFile.exists()){
             newFile.mkdir();
        }
        // 文件名
        String fileName = oldFilePath.substring(oldFilePath.lastIndexOf("/"));
        newFile = new File(newPath + "/" + fileName);

        // newPath 文件夹下是否存在 oldFilePath 文件
        if (newFile.exists()){
            return "文件已存在";
        }

        try {
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(oldFile));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(newFile));
            byte[] temp = new byte[(int) oldFile.length()];
            while ( input.read(temp) != -1){
                out.write(temp);
            }
            input.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "拷贝成功";
    }

    public String fileDelete(String filePath){
        if (StringUtils.isEmpty(filePath)){
            return "filePath 文件为空";
        }
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
        }else {
            return "文件不存在";
        }

        return "删除成功";
    }
    /**
     * uuid
     * */
    public  String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}