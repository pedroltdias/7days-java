package br.com.github;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Movie> movies = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        String json = new ImdbApiClient().getResponseBody();

        List<Movie> movies = new ImdbMovieJsonParser().parse(json);

        HTMLGenerator htmlGenerator = new HTMLGenerator();
        htmlGenerator.generate(movies);
    }
}
