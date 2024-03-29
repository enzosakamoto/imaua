package shared.entities;

import java.util.UUID;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private String id;
    private String id_client;
    private String date;
    private int id_restaurant;
    private String meal;
    private int isDone;

    public Order(int id_restaurant, String id_client, String meal) {
        this.id = UUID.randomUUID().toString();
        this.date = getLocalDate();
        this.id_restaurant = id_restaurant;
        this.id_client = id_client;
        this.meal = meal;
        this.isDone = 0;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getIdRestaurant() {
        return id_restaurant;
    }

    public String getIdClient() {
        return id_client;
    }

    public String getMeal() {
        return meal;
    }

    public int getIsDone() {
        return isDone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIdRestaurant(int id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    public void setIdClient(String id_client) {
        this.id_client = id_client;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }

    public static String getLocalDate() {
        // Obter a data atual
        LocalDateTime dataHoraAtual = LocalDateTime.now();

        // Definir o formato desejado
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");

        String dataFormatada = dataHoraAtual.format(formato);

        // Formatando a data e hora
        return dataFormatada;
    }
}
