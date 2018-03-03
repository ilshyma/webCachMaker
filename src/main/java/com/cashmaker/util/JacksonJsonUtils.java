package com.cashmaker.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Вспомогательный класс для работы с Jackson
 * @author author
 */
public class JacksonJsonUtils {
    
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final ObjectWriter OBJECT_WRITER =
            OBJECT_MAPPER.writerWithDefaultPrettyPrinter();
    
    /**
     * Пытается убрать экранирующие символы из исходной строки json
     * @param src исходная строка json
     * @return строка без экранирующих символов
     */
    public static String tryUnescape(String src) {
        checkArgument(src != null, "Не передана строка для снятия экранирования символов");
        // пытаемся убрать экранирующие символы
        try {
            String unescaped = OBJECT_MAPPER.readValue(src, String.class);
            return unescaped;
        // если не получилось убрать экранирующие символы - возвращаем исходную строку
        } catch (Exception ex) {
            return src;
        }
    }

    /**
     * Получает ObjectMapper
     * @return ObjectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static String toPrettyJson(Object o){
        try {
            return OBJECT_WRITER.writeValueAsString(o);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String toJson(Object o){
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference){
        try {
            return OBJECT_MAPPER.<T>readValue(json, typeReference);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T fromJson(String json, Class<T> valueType){
        try {
            return OBJECT_MAPPER.<T>readValue(json, valueType);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
