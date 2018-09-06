package com.n256coding.oauthgmaildemo.controllers;

import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import java.net.URL;

@Controller
public class MainController {

    @GetMapping("/")
    public String getPosts() {
        return "index";
    }

    @GetMapping("/Log")
    public String doLogging(){
        RestTemplate restTemplate = new RestTemplate();
//        HttpClient httpClient = HttpClient.New(new URL(""));
        return "";
    }
}
