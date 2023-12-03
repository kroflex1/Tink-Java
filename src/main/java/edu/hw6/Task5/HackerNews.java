package edu.hw6.Task5;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HackerNews {
    private static final String TOP_STORIES_ENDPOINT = "https://hacker-news.firebaseio.com/v0/topstories.json";

    public static long[] hackerNewsTopStories() {
        Optional<String> responseBody = getResponseBody(TOP_STORIES_ENDPOINT);
        if (responseBody.isEmpty()) {
            return new long[] {};
        }
        return convertJSONToArray(responseBody.get());
    }

    public static Optional<String> news(long id) {
        String endPoint = String.format("https://hacker-news.firebaseio.com/v0/item/%d.json", id);
        Optional<String> responseBody = getResponseBody(endPoint);
        if (responseBody.isEmpty()) {
            return Optional.empty();
        }
        return getFieldValue(responseBody.get(), "title");
    }

    private static Optional<String> getResponseBody(String endpoint) {
        String result;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(endpoint))
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .build();
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            result = response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return Optional.empty();
        }
        return Optional.of(result);
    }

    private static long[] convertJSONToArray(String body) {
        return Arrays.stream(body.substring(1, body.length() - 1)
                .split(","))
            .mapToLong(Long::valueOf)
            .toArray();
    }

    private static Optional<String> getFieldValue(String body, String field) {
        Pattern pattern = Pattern.compile(String.format("\"%s\":\"([^\"]+)\"", field));
        Matcher matcher = pattern.matcher(body);
        if (matcher.find()) {
            return Optional.of(matcher.group(1));
        }
        return Optional.empty();
    }

    private HackerNews() {
    }

}
