package shared.domain.entities;

import java.util.UUID;

public class Client {
    private String id;
    private String name;
    private String password;
    private double credits;

    public Client(String name, String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.password = password;
        this.credits = 0;
    }

    public Client(String id, String name, String password, double credits) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.credits = credits;
    }

    public Client() {
        this.id = "";
        this.name = "";
        this.password = "";
        this.credits = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public double getCredits() {
        return credits;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

    public void setCredits(double creditos) {
        this.credits = creditos;
    }
}
