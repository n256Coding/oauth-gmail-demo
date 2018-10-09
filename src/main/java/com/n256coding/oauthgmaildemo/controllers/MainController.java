package com.n256coding.oauthgmaildemo.controllers;

import com.n256coding.oauthgmaildemo.helpers.WebRequestHelper;
import com.n256coding.oauthgmaildemo.models.Message;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class MainController {


    @GetMapping("/callback")
    public String getCallback(HttpServletRequest request, HttpServletResponse response, Model model) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException, URISyntaxException {
        String requestCode = request.getParameter("code");

        String oAuthToken = WebRequestHelper.requestOAuthToken(requestCode);
        List<String> mailIds = WebRequestHelper.requestMailIds(oAuthToken, 20);
        List<Message> mailMessages = WebRequestHelper.getMailMessages(oAuthToken, mailIds);

        model.addAttribute("mails", mailMessages);

        return "works";
    }


}
