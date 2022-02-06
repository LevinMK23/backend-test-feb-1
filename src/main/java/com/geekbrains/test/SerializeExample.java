package com.geekbrains.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class SerializeExample {

    public static void main(String[] args) throws JsonProcessingException {

        ImageClassifierResponse response = ImageClassifierResponse.builder()
                .category("burger")
                .status("success")
                .probability(0.9123f)
                .build();

        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(response);
//        System.out.println(json);
//
//        ImageClassifierResponse deserializedObject = mapper.readValue(json, ImageClassifierResponse.class);
//        System.out.println(deserializedObject);

        Gson gson = new Gson();
        String gsonJson = "{\"status\":\"success\",\"category\":\"burger\",\"probability\":0.9123}";

        System.out.println(gsonJson);
        ImageClassifierResponse r = gson.fromJson(gsonJson, ImageClassifierResponse.class);
        System.out.println(r);

        ImageClassifierResponse value = mapper.readValue(gsonJson, ImageClassifierResponse.class);
        System.out.println(value);

    }
}
