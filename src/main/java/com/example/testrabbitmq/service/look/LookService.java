package com.example.testrabbitmq.service.look;

import com.example.testrabbitmq.dto.xml.PmItemFlat;
import com.example.testrabbitmq.mapper.itemAndEntEntlook.LookMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
public class LookService {

    @Autowired
    private LookMapper lookMapper;


    /**
     *  所有费项
     *
     * */
    public List getItemAll(){
        return lookMapper.selectItemAll();
    }


   /**
    * 企业已公示费项
    *  */
    public List getEntItem(String entCode){
       if (StringUtils.isEmpty(entCode)){
           return Arrays.asList("entCode is null");
       }else {
           entCode = entCode.trim();
       }

        List<PmItemFlat> pmItemFlats = lookMapper.selectEntItem(entCode);
       if (pmItemFlats == null || pmItemFlats.size() == 0){
           PmItemFlat pmItemFlat = new PmItemFlat();
           pmItemFlat.setErr("企业代码不存在");
            pmItemFlats = new ArrayList<PmItemFlat>();
            pmItemFlats.add(pmItemFlat);
       }

        return pmItemFlats;
    }
}
