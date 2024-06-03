package com.example.finalapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    @Value("${api.ai}")
    String apiKey;

    public Map question(Map<String, String> body){
        String url = "https://api.openai.com/v1/chat/completions";
        Map<String, String> setting = new HashMap<>();
        setting.put("role", "system");
        setting.put("content", "너는 최고의 상담사야, 우리 웹사이트에 대해 뭐든 답변해줄 수 있어\n" +
                "답변할 수 없는 내용은 회사 번호로 연락하라고 알려줘야해\n" +
                "우리 회사 전화 번호는 02-1234-1234이고 고객이 물어보면 전화번호를 알려줘야해!\n" +
                "사용자가 불평하면 위로해줘야해");

        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", body.get("content"));

        List<Map> messages = List.of(setting, message);

        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("model", "gpt-3.5-turbo");
        reqBody.put("messages", messages);

        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .build();

        Map resp = webClient.post()
                .body(BodyInserters.fromValue(reqBody))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        System.out.println("resp = " + resp);

        return resp;
    }
}













