package com.mckinsey.db;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;

public class CustomObjectMapper extends ObjectMapper {

    private static final Logger logger = LogManager.getLogger(CustomObjectMapper.class);

    public CustomObjectMapper() {

    }

    public String asJson(ResultSet rs) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new SerializableResultSet());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("results", rs);

        StringWriter  stringWriter = new StringWriter();
        try {
            objectMapper.writeValue(stringWriter, objectNode);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage());
            logger.error(e.getStackTrace().toString());
        } catch (JsonMappingException e) {
            logger.error(e.getMessage());
            logger.error(e.getStackTrace().toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error(e.getStackTrace().toString());
        }

        return stringWriter.toString();
    }
}
