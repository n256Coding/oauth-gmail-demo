package com.n256coding.oauthgmaildemo.helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.n256coding.oauthgmaildemo.Environments;
import com.n256coding.oauthgmaildemo.models.Message;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WebRequestHelper {


    public static String requestOAuthToken(String requestCode) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//        SSLContext sslContext = new SSLContextBuilder()
//                .loadTrustMaterial(null, (certificate, authType) -> true)
//                .build();

//        HttpClient client = HttpClients.custom()
//                .setSSLContext(sslContext)
//                .setSSLHostnameVerifier(new NoopHostnameVerifier())
//                .build();
        HttpClient client = HttpClients.createDefault();

        HttpPost postRequest = new HttpPost("https://www.googleapis.com/oauth2/v4/token");
        postRequest.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/6");
        postRequest.setHeader("Host", "www.googleapis.com");
        postRequest.setHeader("Accept", "*/*");
        postRequest.setHeader("Accept-Language", "en-US,en;q=0.5");
        postRequest.setHeader("Accept-Encoding", "gzip, deflate, br");
        postRequest.setHeader("Connection", "keep-alive");
        postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> urlParameters = new ArrayList<>();
        try {
            urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
            urlParameters.add(new BasicNameValuePair("redirect_uri", Environments.REDIRECT_URI));
            urlParameters.add(new BasicNameValuePair("client_id", Environments.CLIENT_ID));
            urlParameters.add(new BasicNameValuePair("code", requestCode));
            urlParameters.add(new BasicNameValuePair("client_secret", Environments.CLIENT_SECRET));

            postRequest.setEntity(new UrlEncodedFormEntity(urlParameters));

        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObject responseJson = null;
        try {
            HttpResponse postResponse = client.execute(postRequest);
            responseJson = new JsonParser()
                    .parse(EntityUtils.toString(postResponse.getEntity()))
                    .getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseJson.get("access_token").getAsString();
    }

    public static List<String> requestMailIds(String oAuthToken, int limit) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException {
//        SSLContext sslContext = new SSLContextBuilder()
//                .loadTrustMaterial(null, (certificate, authType) -> true)
//                .build();
//
//        HttpClient client = HttpClients.custom()
//                .setSSLContext(sslContext)
//                .setSSLHostnameVerifier(new NoopHostnameVerifier())
//                .build();

        HttpClient client = HttpClients.createDefault();

        HttpGet getRequest = new HttpGet("https://www.googleapis.com/gmail/v1/users/me/messages?maxResults=" + limit);
        getRequest.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/6");
        getRequest.setHeader("Host", "www.googleapis.com");
        getRequest.setHeader("Authorization", "Bearer " + oAuthToken);

        HttpResponse response = client.execute(getRequest);
        JsonObject mails = new JsonParser().parse(EntityUtils.toString(response.getEntity())).getAsJsonObject();
        JsonArray messages = mails.getAsJsonArray("messages");

        List<String> messageIds = new ArrayList<>();
        for (JsonElement message : messages) {
            messageIds.add(message.getAsJsonObject().get("id").getAsString());
        }

        return messageIds;
    }

    public static List<Message> getMailMessages(String oAuthToken, List<String> mailIds) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException {
//        SSLContext sslContext = new SSLContextBuilder()
//                .loadTrustMaterial(null, (certificate, authType) -> true)
//                .build();
//
//        HttpClient client = HttpClients.custom()
//                .setSSLContext(sslContext)
//                .setSSLHostnameVerifier(new NoopHostnameVerifier())
//                .build();

        HttpClient client = HttpClients.createDefault();

        HttpGet getRequest = new HttpGet();
        getRequest.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/6");
        getRequest.setHeader("Host", "www.googleapis.com");
        getRequest.setHeader("Authorization", "Bearer " + oAuthToken);

        List<Message> messages = new ArrayList<>();
        for (String mailId : mailIds) {
            getRequest.setURI(new URI("https://www.googleapis.com/gmail/v1/users/me/messages/" + mailId));
            JsonObject mailObject = new JsonParser().parse(EntityUtils.toString(client.execute(getRequest).getEntity())).getAsJsonObject();
            messages.add(extractMessageFrom(mailObject));
        }

        return messages;
    }

    private static Message extractMessageFrom(JsonObject jsonObject) {
        Message message = new Message();
        message.setBody(jsonObject.get("snippet").getAsString());

        JsonArray headersJsonArray = jsonObject.get("payload")
                .getAsJsonObject()
                .get("headers")
                .getAsJsonArray();

        Iterator<JsonElement> headers = headersJsonArray.iterator();
        while (headers.hasNext()) {
            JsonObject header = headers.next().getAsJsonObject();

            switch (header.get("name").getAsString()) {
                case "From":
                    message.setFrom(header.get("value").getAsString());
                    break;
                case "To":
                    message.setTo(header.get("value").getAsString());
                    break;
                case "Subject":
                    message.setSubject(header.get("value").getAsString());
                    break;
                case "Date":
                    message.setDate(header.get("value").getAsString());
                    break;
            }
        }

        return message;
    }


}
