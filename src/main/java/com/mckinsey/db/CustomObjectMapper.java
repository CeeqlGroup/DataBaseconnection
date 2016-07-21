package com.mckinsey.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new SerializableResultSet());
        registerModule(module);
    }
}
