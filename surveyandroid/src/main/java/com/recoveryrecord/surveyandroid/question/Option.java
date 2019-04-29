package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

@JsonDeserialize(using = Option.OptionDeserializer.class)
public class Option {
    public String title;

    public static class OptionDeserializer extends StdDeserializer<Option> {

        protected OptionDeserializer() {
            super(Option.class);
        }

        @Override
        public Option deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = p.getCodec().readTree(p);
            if (node.has("title")) {
                OtherOption otherOption = new OtherOption();
                otherOption.title = node.get("title").asText();
                otherOption.type = node.get("type").asText();
                return otherOption;
            } else {
                Option option = new Option();
                option.title = node.asText();
                return option;
            }
        }
    }
}
