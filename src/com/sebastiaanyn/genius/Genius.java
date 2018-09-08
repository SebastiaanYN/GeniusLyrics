package com.sebastiaanyn.genius;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sebastiaanyn.genius.request.Route;
import com.sebastiaanyn.genius.request.Routes;
import com.sebastiaanyn.genius.request.data.Search;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Genius {

    private static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    private final String token;

    public Genius(String token) {
        this.token = token;
    }

    /**
     * Make HTTP requests to the genius api endpoints.
     *
     * @param method   The HTTP method to use.
     * @param endpoint The api endpoint to use for the request.
     * @param schema   Class that will be filled with data from the JSON response.
     * @param <T>      Any form of class.
     * @return Class with the JSON response data. This will only include fields that were specified in the schema class.
     */
    private <T> T request(String method, String endpoint, Class<T> schema) {
        if (schema == null) {
            throw new IllegalArgumentException("schema cannot be null");
        }

        try {
            URL url = new URL("https://api.genius.com/" + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method.toUpperCase());
            connection.setRequestProperty("Authorization", "Bearer " + this.token);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
            return GSON.fromJson(sb.toString(), schema);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Search search(String query) {
        Route<Search> route = Routes.SEARCH;
        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String endpoint = String.format(route.getEndpoint(), encoded);
        return request(route.getMethod(), endpoint, route.getSchema());
    }

    public String getLyrics(String songUrl) {
        try {
            URL url = new URL(songUrl);
            Scanner input = new Scanner(url.openStream());
            StringBuilder sb = new StringBuilder();
            while (input.hasNext()) {
                String next = input.nextLine();
                sb.append(next);
            }
            Document doc = Jsoup.parse(sb.toString());
            Elements lyricsElement = doc.select("div.lyrics");
            return br2nl(lyricsElement.html()).trim();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private static String br2nl(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));
        document.select("br").append("\\n");
        document.select("p").prepend("\\n\\n");
        String s = document.html().replace("\\n", "\n");
        return Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
    }

}
