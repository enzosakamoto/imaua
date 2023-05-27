package shared.domain.entities;

public class Restaurant {
    private int id;
    private String name;
    private String[] menu = new String[4];
    private double[] prices = new double[4];

    public Restaurant(int id, String name) {
        this.id = id;
        this.name = name;
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

    public double[] getPrices() {
        return prices;
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

    public void setPrices(double[] prices) {
        this.prices = prices;
    }
}
