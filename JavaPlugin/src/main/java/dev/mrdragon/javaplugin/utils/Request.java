package dev.mrdragon.javaplugin.utils;

import org.bson.Document;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Request {
    Config config = new Config();

    public Document req(String endpoint, String method, String body) throws IOException {
        Document result = new Document();
        HttpURLConnection con = null;
        BufferedReader in = null;

        try {
            URL url = new URL(config.get("DISCORD_API") + endpoint);

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Authorization", "Bearer " + config.get("DISCORD_API"));
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            if (body != null) {
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = body.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            StringBuffer content = new StringBuffer();

            String inputLine;
            while ((inputLine = in.readLine()) != null) content.append(inputLine);

            in.close();

            String[] pairs = content.substring(1, content.length() - 1).split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                String key = keyValue[0].replaceAll("\"", "");
                String value = keyValue.length > 1 ? keyValue[1] : "";
                result.append(key.replaceAll("\"",""), value.replaceAll("\"",""));
            }

            con.disconnect();
        } catch (IOException e) {
            InputStream error = null;
            if (con != null) {
                error = con.getErrorStream();
            }

            if (error != null) {
                in = new BufferedReader(new InputStreamReader(error, StandardCharsets.UTF_8));
                StringBuilder content = new StringBuilder();

                String inputLine;
                while ((inputLine = in.readLine()) != null) content.append(inputLine);

                String[] pairs = content.substring(1, content.length() - 1).split(",");
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":");
                    String key = keyValue[0].replaceAll("\"", "");
                    String value = keyValue.length > 1 ? keyValue[1] : "";
                    result.append(key.replaceAll("\"",""), value.replaceAll("\"",""));
                }
            } else {
                System.err.println("[JavaPlugin Utils/Request]=> " + e.getMessage());
            }
        }

        return result;
    }
}