package com.n256coding.oauthgmaildemo.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

public class Encoder {
    public static String urlEncode(String word) throws UnsupportedEncodingException {
        return URLEncoder.encode(word, "UTF-8");
    }

    public static String base64Encode(String word){
        return Base64.getEncoder().encodeToString(word.getBytes());
    }
}
