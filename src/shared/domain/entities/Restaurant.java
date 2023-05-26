package shared.domain.entities;

public class Restaurant {
    private int id;
    private String name;
    private String[] menu;

    public Restaurant(int id, String name, String[] menu) {
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

    public String[] getMenu() {
        return menu;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMenu(String[] menu) {
        this.menu = menu;
    }

    public void setName(String name) {
        this.name = name;
    }
}
