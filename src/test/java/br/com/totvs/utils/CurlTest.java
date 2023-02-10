package br.com.totvs.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class CurlTest {


    @Test
    @DisplayName("Teste de GET de uma URL")
    void testGet() {
        String url = "https://jsonplaceholder.typicode.com/todos/1";
        String result = Curl.get(url);
        Assertions.assertTrue(result.contains("200 OK"));
    }

    @Test
    @DisplayName("Teste de POST de uma URL")
    void testPost() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        String data = "{\"title\": \"foo\",\"body\": \"bar\",\"userId\": 1}";
        String result = Curl.post(url, data);
        Assertions.assertTrue(result.contains("201 Created"));
    }

}
