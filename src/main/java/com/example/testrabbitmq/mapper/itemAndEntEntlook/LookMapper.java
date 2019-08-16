package com.example.testrabbitmq.mapper.itemAndEntEntlook;

import com.example.testrabbitmq.dto.xml.PmItemFlat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LookMapper {
    // 所有 已经公示费项价格
    List<PmItemFlat> selectItemAll();


    // 查询企业 已经公示 费项
    List<PmItemFlat> selectEntItem(@Param("entCode") String entCode);
}
