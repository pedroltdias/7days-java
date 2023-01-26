package br.com.github;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImdbMovieJsonParser implements JsonParser{
    private String[] moviesarray;
    private List<String> titles;
    private List<String> urlImages;
    private List<String> ratings;
    private List<String> years;
    private List<Movie> movies = new ArrayList<>();

    @Override
    public List<Movie> parse(String json){
        this.moviesarray = this.parseJsonMovies(json);
        this.titles = this.parseTitles(this.moviesarray);
        this.urlImages = this.parseUrlImages(this.moviesarray);
        this.ratings = this.parseRatings(this.moviesarray);
        this.years = this.parseYears(this.moviesarray);

        return this.addMoviesInList(this.moviesarray, this.movies, this.titles, this.urlImages, this.ratings, this.years);
    }

    private static List<Movie> addMoviesInList(String[] moviesArray, List<Movie> movies, List<String> titles, List<String> urlImages, List<String> ratings, List<String> years){
        System.out.println("[PARSE] Adicionando filmes a lista...");
        for (int i = 0; i < moviesArray.length; i++) {
            movies.add(new Movie(titles.get(i), urlImages.get(i), ratings.get(i), years.get(i)));
        }

        return movies;
    }

    private static String[] parseJsonMovies(String json) {
        System.out.println("[PARSE] Parseando json...");
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
        System.out.println("[PARSE] Parseando titles...");
        return parseAttribute(moviesArray, 3);
    }

    private static List<String> parseYears(String[] moviesArray) {
        System.out.println("[PARSE] Parseando years...");
        return parseAttribute(moviesArray, 4);
    }

    private static List<String> parseUrlImages(String[] moviesArray) {
        System.out.println("[PARSE] Parseando images...");
        return parseAttribute(moviesArray, 5);
    }

    private static List<String> parseRatings(String[] moviesArray) {
        System.out.println("[PARSE] Parseando ratings...");
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
