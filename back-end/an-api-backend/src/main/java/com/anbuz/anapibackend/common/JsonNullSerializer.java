package com.anbuz.anapibackend.common;

import cn.hutool.json.JSONNull;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * 自定义的 JSON 序列化器，
 * 专门用于序列化 cn.hutool.json.JSONNull 对象
 */
@JsonComponent
public class JsonNullSerializer extends JsonSerializer<JSONNull> {
    @Override
    public void serialize(JSONNull jsonNull, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNull();
    }
}