package Controller;

import Model.*;
import java.sql.Date;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.text.SimpleDateFormat;

import mjson.Json;

public class Controller implements IController {
    @Override
    public User getUserDetails(String id, String password) {
        User s = null;
        HttpClient client;
        HttpRequest request;
        HttpResponse<String> response;

        try {
            Json x = Json.object().set("id", id).set("password", password);

            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder().uri(URI.create("https://invoicing-java-backend.herokuapp.com/api/login"))
                    .header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(x.toString()))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            if (!response.body().equals("User doesnt exist")) {
                Json u = Json.read(response.body());
                s = new User(u.at("id").asString(), u.at("name").asString(), u.at("password").asString(),
                        u.at("budget").asDouble());
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            return s;
        }
    }

    @Override
    public void getInvoices(User s) {
        HttpClient client;
        HttpRequest request;
        HttpResponse<String> response;
        try {

            client = HttpClient.newHttpClient();

            request = HttpRequest.newBuilder()
                    .uri(URI.create("https://invoicing-java-backend.herokuapp.com/invoice/all/" + s.getID()))
                    .header("Accept", "application/json").build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.body().length() != 0 && !response.body().equals("[]")) {
                Json y = Json.read(response.body());
                List<Json> i = y.asJsonList();
                SimpleDateFormat d = new SimpleDateFormat("YYYY-MM-DD");
                for (Json obj : i) {
                    s.insertInvoice(new Invoice(obj.at("id").asInteger(), obj.at("amount").asDouble(),
                            obj.at("description").asString(), d.parse(obj.at("date").asString())));
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    @Override
    public void insertInvoice(String userID, double amount, String description, Date date) {
        HttpClient client;
        HttpRequest request;
        HttpResponse<String> response;
        SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-DD");

        try {
            Json x = Json.object().set("amount", amount).set("description", description).set("UserID", userID)
                    .set("date", f.format(date));

            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder()
                    .uri(URI.create("https://invoicing-java-backend.herokuapp.com/invoice/create/"))
                    .header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(x.toString())).build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
