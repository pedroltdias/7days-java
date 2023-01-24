package br.com.github;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class HTMLGenerator {
    public PrintWriter writer;

    public HTMLGenerator() throws FileNotFoundException {
        this.writer = new PrintWriter(new File("src/web-app/movies.html"));
    }

    public void generate(List<Movie> movies){
        String divTemplate =
                """
                <div class=\"card text-white bg-dark mb-3 me-3\" style=\"max-width: 18rem;\">
                    <h4 class=\"card-header\">%s</h4>
                    <div class=\"card-body\">
                        <img class=\"card-img\" src=\"%s\" alt=\"%s\">
                        <p class=\"card-text mt-2\">Nota: %s - Ano: %s</p>
                    </div>
                </div>
                """;
        this.writer.write(htmlTop());
        System.out.println("[HTMLGenerator] Gerando corpo da pagina...");
        for (Movie movie : movies) {
            this.writer.println(String.format(divTemplate, movie.getTitle(), movie.getUrlImage(), movie.getTitle(), movie.getRating(), movie.getYear()));
        }
        this.writer.write(htmlBottom());
        this.writer.close();

    }

    private String htmlTop(){
        System.out.println("[HTMLGenerator] Gerando topo da pagina...");
        return "<!DOCTYPE html>" +
                    "<html lang=\"en\">" +
                        "<head>" +
                            "<meta charset=\"UTF-8\">" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">"+
                            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">"+
                            "<title>7Days - Movies</title>" +
                        "</head>" +
                        "<body>" +
                            "<div class=\"container d-flex flex-wrap align-items-center justify-content-between\">";
    }

    private String htmlBottom(){
        System.out.println("[HTMLGenerator] Gerando final da pagina...");
        return  "</div>" +
                "<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>" +
                "<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>" +
                "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>" +
                "</body>" +
                "</html>";
    }
}
