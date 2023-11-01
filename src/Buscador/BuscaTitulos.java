package Buscador;

import Buscador.Converter.ConverterOmdb;
import Buscador.calculos.CalculadoraDeTempo;
import Buscador.modelos.Titulo;
import Buscador.exc.ErroFormatoInvalido;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BuscaTitulos {
    public static void main(String[] args) throws IOException, InterruptedException {
        CalculadoraDeTempo calculaTempo = new CalculadoraDeTempo();
        Scanner scanner = new Scanner(System.in);
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).setPrettyPrinting().create();
        String pesquisa = "";
        List<Titulo> titulos = new ArrayList<>();
        String api = "9d06745a";
        System.out.println("Digite 'sair' para encerrar");
        while (!pesquisa.equalsIgnoreCase("sair")) {
            System.out.println("Digite o título desejado: ");

            pesquisa = scanner.nextLine();
            if (pesquisa.equalsIgnoreCase("sair")) {
                break;
            }
            String endereco = "http://www.omdbapi.com/?t=" + pesquisa.replace(" ", "+") + "&apikey=" + api;
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();

                ConverterOmdb meuTitulo = gson.fromJson(json, ConverterOmdb.class);
                Titulo meutitulo = new Titulo(meuTitulo);


                calculaTempo.inclui(meutitulo);

                titulos.add(meutitulo);
            } catch (NumberFormatException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (ErroFormatoInvalido e) {
                System.out.println(e.getMessage());
            }

        }
        System.out.println(titulos);
        System.out.println("Você precisa de " + calculaTempo.exibeTempoTotal() + " minutos para assistir a todos os títulos");

        FileWriter escrita = new FileWriter("Titulos.json");
        escrita.write(gson.toJson(titulos));
        escrita.close();
    }

}
