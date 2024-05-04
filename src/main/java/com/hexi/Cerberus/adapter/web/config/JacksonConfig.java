package com.hexi.Cerberus.adapter.web.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.hexi.Cerberus.application.report.service.DTO.create.CreateReportDTO;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class JacksonConfig {
//    @Bean
//    public ObjectMapper objectMapper() throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//
////        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
////        String payload = "{\"type\": \"inventarisation\", \"wareHouseId\": [\"26a10b59-db0a-4b27-9d62-9255f9f824ef\"], \"items\": {\"575c01f0-5c9f-4c2a-8d2b-1f924469d269\": 2}}";
////        Object report = objectMapper.readValue(payload, CreateReportDTO.class);
////        System.err.println(report.toString());
////        System.err.println(report.getClass().getName());
//
//        return objectMapper;
//    }
//}
