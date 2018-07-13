package com.codecool.guestBook.Server;

import com.codecool.guestBook.DAO.EntryDAO;
import com.codecool.guestBook.Model.Entry;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;




public class StaticHandler implements HttpHandler {

    private EntryDAO dao = new EntryDAO();
    private JtwigTemplate template;
    private JtwigModel model;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {


        String method = httpExchange.getRequestMethod();

        if (method.equalsIgnoreCase("post")) {
            try {
                postMethodHandler(httpExchange);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (method.equalsIgnoreCase("get")){
            try {
                getMethodHandler(httpExchange);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }
    private void postMethodHandler(HttpExchange httpExchange) throws IOException, SQLException {
        Map<String, String> inputs = getInputs(httpExchange);
        dao.addEntry(inputs.get("name"), inputs.get("message"));
        dao.getEntries();
        getMethodHandler(httpExchange);

    }

    private void getMethodHandler(HttpExchange httpExchange) throws IOException, SQLException {
        // get file path from url
        URI uri = httpExchange.getRequestURI();
        String[] address = uri.toString().split("/");
        System.out.println("looking for: " + address[address.length - 1]);
        if (address[address.length - 1].equalsIgnoreCase("index.twig.html")) {
            ArrayList<Entry> entries = dao.getEntries();
            template = JtwigTemplate.classpathTemplate("index.twig.html");
            model = JtwigModel.newModel();
            model.with("entries", entries);

            sendResponse(httpExchange, template.render(model));
        }
        else {
            String path = "./" + address[address.length - 1];
            // get file from resources folder, see: https://www.mkyong.com/java/java-read-a-file-from-resources-folder/
            ClassLoader classLoader = getClass().getClassLoader();
            URL fileURL = classLoader.getResource(path);


            if (fileURL == null) {
                // Object does not exist or is not a file: reject with 404 error.
                send404(httpExchange);
            } else {
                // Object exists and is a file: accept with response code 200.
                sendFile(httpExchange, fileURL);
            }
        }

    }

    private void send404(HttpExchange httpExchange) throws IOException {
        String response = "404 (Not Found)\n";
        httpExchange.sendResponseHeaders(404, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void sendFile(HttpExchange httpExchange, URL fileURL) throws IOException {
        // get the file
        File file = new File(fileURL.getFile());
        System.out.println(fileURL);
        // we need to find out the mime type of the file, see: https://en.wikipedia.org/wiki/Media_type
        MimeTypeResolver resolver = new MimeTypeResolver(file);
        String mime = resolver.getMimeType();
        httpExchange.getResponseHeaders().set("Content-Type", mime);
        httpExchange.sendResponseHeaders(200, 0);

        OutputStream os = httpExchange.getResponseBody();

        // send the file

       try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                os.write(str.getBytes());
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        os.close();
    }

    private Map<String, String> getInputs(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        return parseFormData(formData);

    }

    private Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] key = pair.split("=");
            String value = new URLDecoder().decode(key[1], "UTF-8");
            map.put(key[0], value);
        }
        return map;
    }
    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {

        Reader rawString = new StringReader(response);
        BufferedReader result = new BufferedReader(rawString);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        try {
            BufferedReader in = new BufferedReader(result);
            String str;
            while ((str = in.readLine()) != null) {
                os.write(str.getBytes());
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        os.close();
        System.out.println(response);
    }


}
