package com.example.testrabbitmq.mapper;


import org.apache.ibatis.annotations.Param;

public interface StreamMapper {

     Integer isRegister(@Param("entId") String entId);
     Integer isReCord(@Param("entId") String entId, @Param("senderId") String senderId);

     Integer isHasEntItem(@Param("entCode") String entCode);
     void insertEntInfo(@Param("ID") String id, @Param("entId") String entId, @Param("senderId") String senderId);
}
