package br.com.github;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImdbApiClient implements ApiClient{
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;

    public ImdbApiClient() throws IOException, InterruptedException {
        this.client = HttpClient.newHttpClient();
        this.request = this.generateRequest();
        this.response = this.generateResponse(this.request, this.client);
    }

    private static HttpRequest generateRequest(){
        System.out.println("[REQUEST] Gerando requisicao para a api...");
        return HttpRequest
                .newBuilder(
                        URI.create("https://imdb-api.com/en/API/Top250Movies/" + Properties.key))
                .header("accept", "application/json")
                .build();
    }

    private static HttpResponse<String> generateResponse(HttpRequest request, HttpClient client) throws IOException, InterruptedException {
        System.out.println("[RESPONSE] Gerando resposta da api...");
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String getResponseBody(){
        System.out.println("[RESPONSE] Retornando body da resposta...");
        return this.response.body();
    }
}
