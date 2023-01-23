package br.com.github;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder(
                        URI.create("https://imdb-api.com/en/API/Top250Movies/" + Properties.key))
                .header("accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        String[] moviesArray = parseJsonMovies(json);

        System.out.println(moviesArray[1]);

        List<String> titles = parseTitles(moviesArray);
        titles.forEach(System.out::println);

        List<String> urlImages = parseUrlImages(moviesArray);
        urlImages.forEach(System.out::println);

        List<String> ratings = parseRatings(moviesArray);
        ratings.forEach(System.out::println);

        List<String> years = parseYears(moviesArray);
        years.forEach(System.out::println);
    }

    private static String[] parseJsonMovies(String json) {
        Matcher matcher = Pattern.compile(".*\\[(.*)\\].*").matcher(json); //acha o json

        if (!matcher.matches()) {
            throw new IllegalArgumentException("no match in " + json);
        }

        String[] moviesArray = matcher.group(1).split("\\},\\{"); //separa os objetos no json
        moviesArray[0] = moviesArray[0].substring(1); //a primeira posição do array é um "{", a partir dessa linha a primeira posição vira o primeiro filme
        int last = moviesArray.length - 1; //pega o tamanho da string -1 = 249
        String lastString = moviesArray[last]; //salva o ultimo objeto
        moviesArray[last] = lastString.substring(0, lastString.length() - 1); //retira o "}" da ultima posição do array e coloca um objeto
        return moviesArray;
    }

    private static List<String> parseTitles(String[] moviesArray) {
        return parseAttribute(moviesArray, 3);
    }

    private static List<String> parseYears(String[] moviesArray) {
        return parseAttribute(moviesArray, 4);
    }

    private static List<String> parseUrlImages(String[] moviesArray) {
        return parseAttribute(moviesArray, 5);
    }

    private static List<String> parseRatings(String[] moviesArray) {
        return parseAttribute(moviesArray, 7);
    }

    private static List<String> parseAttribute(String[] moviesArray, int pos) {
        return Stream.of(moviesArray)
                .map(e -> e.split("\",\"")[pos])
                .map(e -> e.split(":\"")[1])
                .map(e -> e.replaceAll("\"", ""))
                .collect(Collectors.toList());
    }
}
