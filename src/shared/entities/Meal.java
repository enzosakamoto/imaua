package shared.entities;

public class Meal {
    private String name;
    private double price;

    public Meal(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Meal() {
        this.name = "";
        this.price = 0;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }
}
