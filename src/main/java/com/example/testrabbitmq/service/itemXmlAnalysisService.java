package com.example.testrabbitmq.service;

import com.example.testrabbitmq.mapper.StreamMapper;
import com.example.testrabbitmq.tools.Tools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> description: 报文解析
 * <p> 2019/08/12
 *
 * @author ls
 * @version 1.0.0
 */
@Service
@Slf4j
public class itemXmlAnalysisService {
    @Autowired
    StreamMapper streamMapper;

    // 企业验证逻辑
    public String entCheck(String entId, String senderId) {
        if (StringUtils.isBlank(entId)) {
            return "error";
        }
        String result = "error";
        // 验证企业是否注册
        Integer count = streamMapper.isRegister(entId);
        // 企业已注册
        if (count > 0) {
            //企业有没有公示费项
            Integer entItemCount = streamMapper.isHasEntItem(entId);
            if (entItemCount == 0) {
                return "notHasItem";
            }
            // 记录企业以及发送方对应关系
            Integer isRecord = streamMapper.isReCord(entId, senderId);
            if (isRecord > 0) {
                result = "recorded";
            }
            // 记录新请求的企业信息
            else {
                streamMapper.insertEntInfo(Tools.get32UUID(), entId, senderId);
                result = "newrecord";
            }

        }
        // 未注册则直接返回
        else {
            result = "notRegister";
        }
        return result;
    }

}
