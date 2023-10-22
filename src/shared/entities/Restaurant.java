package shared.entities;

import java.util.ArrayList;

public class Restaurant {
    private int id;
    private String name;
    private ArrayList<Meal> menu;

    public Restaurant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Restaurant(int id, String name, ArrayList<Meal> menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Meal> getMenu() {
        return menu;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMenu(ArrayList<Meal> menu) {
        this.menu = menu;
    }

    public void setName(String name) {
        this.name = name;
    }
}
