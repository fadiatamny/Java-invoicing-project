package Controller;

import Model.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.text.SimpleDateFormat;

import mjson.Json;

public class Controller implements IController {

    /**
     * Function creates a new user and saves it in the database
     * 
     * @param id       the id of the user to be created
     * @param name     the name of the user to be created
     * @param password the password of the user to be created
     * @param budget   the budget of the user to be created
     * @return User / null
     */
    @Override
    public User signUp(String id, String name, String password, double budget) {
        User s = null;
        HttpClient client;
        HttpRequest request;
        HttpResponse<String> response;
        try {
            final Json x = Json.object().set("id", id).set("password", password).set("name", name).set("budget",
                    budget);

            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder()
                    .uri(URI.create("https://invoicing-java-backend.herokuapp.com/api/signup"))
                    .header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(x.toString()))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body().equals("Inserted Successfully")) {
                s = new User(id, name, password, budget);
            }

        } catch (final Exception e) {
            System.err.println(e.toString());
        } finally {
            return s;
        }
    }

    /**
     * Function retrieves a users details and verifies him otherwise returns null
     * 
     * @param id       The id of the user to retriev
     * @param password The password of the user
     * @return User or null
     */
    @Override
    public User getUserDetails(final String id, final String password) {
        User s = null;
        HttpClient client;
        HttpRequest request;
        HttpResponse<String> response;
        try {
            final Json x = Json.object().set("id", id).set("password", password);

            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder().uri(URI.create("https://invoicing-java-backend.herokuapp.com/api/login"))
                    .header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(x.toString()))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (!response.body().equals("User doesnt exist")) {
                final Json u = Json.read(response.body());
                s = new User(u.at("id").asString(), u.at("name").asString(), u.at("password").asString(),
                        u.at("budget").asDouble());
            }

        } catch (final Exception e) {
            System.err.println(e.toString());
        } finally {
            return s;
        }
    }

    /**
     * Function retrieves all invoices that belong to a certain user
     * 
     * @param s The user you want to retrieve his invoices
     */
    @Override
    public void getInvoices(final User s) {
        HttpClient client;
        HttpRequest request;
        HttpResponse<String> response;

        try {
            s.dumpList();
            client = HttpClient.newHttpClient();

            request = HttpRequest.newBuilder()
                    .uri(URI.create("https://invoicing-java-backend.herokuapp.com/invoice/all/" + s.getID()))
                    .header("Accept", "application/json").build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.body().length() != 0 && !response.body().equals("[]")) {
                final Json y = Json.read(response.body());
                final List<Json> i = y.asJsonList();
                for (final Json obj : i) {
                    s.insertInvoice(new Invoice(obj.at("id").asInteger(), obj.at("amount").asDouble(),
                            obj.at("description").asString(), obj.at("date").asString()));
                }
            }
        } catch (final Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Function inserts a new invoice to a given user
     * 
     * @param userID      The users id to insert invoice to
     * @param amount      The amount in the invoice
     * @param description The description of the invoice
     * @param date        A string represnting the date in format YYYY-MM-DD
     */
    @Override
    public void insertInvoice(final String userID, final double amount, final String description, final String date) {
        HttpClient client;
        HttpRequest request;
        HttpResponse<String> response;

        try {
            final Json x = Json.object().set("amount", amount).set("description", description).set("UserID", userID)
                    .set("date", date);

            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder()
                    .uri(URI.create("https://invoicing-java-backend.herokuapp.com/invoice/create/"))
                    .header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(x.toString()))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (final Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Function removes an invoice given its id
     * 
     * @param id The id of the invoice that needs to be deleted
     */
    @Override
    public void deleteInvoice(final int id) {
        HttpClient client;
        HttpRequest request;
        HttpResponse<String> response;
        final SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-DD");

        try {
            client = HttpClient.newHttpClient();
            request = HttpRequest.newBuilder()
                    .uri(URI.create("https://invoicing-java-backend.herokuapp.com/invoice/delete/" + id))
                    .header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.noBody()).build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
