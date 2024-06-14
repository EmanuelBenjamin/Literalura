package com.alura.literalura.servicio;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumeAPI {
    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            System.out.println("\u001B[36m" + "Requiriendo información desde la URL: " + url + "\u001B[0m");
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String json = response.body();
                if (json.contains("\"count\":0")) {
                    System.out.println("\u001B[31m" + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + "\u001B[0m");
                    System.out.println("\u001B[31m" + "┃ " + "Lo sentimos, no se encontraron resultados para su búsqueda.   " + " ┃" + "\u001B[0m");
                    System.out.println("\u001B[31m" + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + "\u001B[0m");
                } else {
                    System.out.println("\u001B[32m" + "Datos obtenidos correctamente:" + "\u001B[0m");
                    System.out.println(json);
                }
            } else {
                System.out.println("\u001B[31m" + "Error al obtener datos. Código de estado: " + response.statusCode() + "\u001B[0m");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }
}