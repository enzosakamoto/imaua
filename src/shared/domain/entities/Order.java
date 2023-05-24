package shared.domain.entities;

import java.util.UUID;

public class Order {
    private String id;
    private int id_restaurant;
    private String id_client;
    private String meal;
    private boolean isDone;

    public Order(int id_restaurant, String id_client, String meal) {
        this.id = UUID.randomUUID().toString();
        this.id_restaurant = id_restaurant;
        this.id_client = id_client;
        this.meal = meal;
        this.isDone = false;
    }

    public String getId() {
        return id;
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

    public boolean getIsDone() {
        return isDone;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }
}
