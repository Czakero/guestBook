//package com.codecool.guestBook.Controller;
//
//
//import com.codecool.guestBook.DAO.EntryDAO;
//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Controller implements HttpHandler {
//    private EntryDAO dao = new EntryDAO();
//
//    @Override
//    public void handle(HttpExchange httpExchange) throws IOException {
//        String response = "";
//        String method = httpExchange.getRequestMethod();
//
//        if (method.equalsIgnoreCase("post")) {
//            Map<String, String> inputs = getInputs(httpExchange);
//            for (String key : inputs.keySet()) {
//                System.out.println(inputs.get(key));
//            }
//
//        }
//    }
//
//    private Map<String, String> getInputs(HttpExchange httpExchange) throws IOException {
//        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "UTF-8");
//        BufferedReader br = new BufferedReader(isr);
//        String formData = br.readLine();
//        return parseFormData(formData);
//
//    }
//
//    private Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
//        Map<String, String> map = new HashMap<>();
//        String[] pairs = formData.split("&");
//        for (String pair : pairs) {
//            String[] key = pair.split("=");
//            String value = new URLDecoder().decode(key[1], "UTF-8");
//            map.put(key[0], value);
//        }
//        return map;
//    }
//}