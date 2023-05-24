package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import shared.domain.entities.Client;
import shared.domain.entities.Order;

public class Crud {
    public void createClient(Client client) {
        String sqlCreateClient = "INSERT INTO clients VALUES (?, ?, ?, ?);";
        Connection connection = Connector.getConn();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sqlCreateClient);
            stmt.setString(1, client.getId());
            stmt.setString(2, client.getName());
            stmt.setString(3, client.getPassword());
            stmt.setString(4, String.valueOf(client.getCredits()));
            stmt.executeUpdate();
            System.out.println("Cliente incluído com sucesso!");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Erro ao incluir os dados do cliente no banco de dados: " + ex.toString());
            }
        } finally {
            Connector.closeConn(connection, stmt);
        }
    }

    public void createOrder(Order order) {
        String sqlCreateOrder = "INSERT INTO orders VALUES (?, ?, ?, ?, ?);";
        Connection connection = Connector.getConn();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sqlCreateOrder);
            stmt.setString(1, order.getId());
            stmt.setString(2, Integer.toString(order.getIdRestaurant()));
            stmt.setString(3, order.getIdClient());
            stmt.setString(4, order.getMeal());
            stmt.setString(5, (order.getIsDone() ? "1" : "0"));
            stmt.executeUpdate();
            System.out.println("Pedido incluído com sucesso!");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Erro ao incluir os dados do pedido no banco de dados: " + ex.toString());
            }
        } finally {
            Connector.closeConn(connection, stmt);
        }
    }

    public ArrayList<Order> getAllOrdersByClient(String clientId) {
        System.out.println("Getting all orders by client");
        return new ArrayList<Order>();
    }

    public String getIdClientByName(String name) {
        System.out.println("Getting id client by name");
        return "id_client";
    }

    public void updateOrder(String orderId) {
        System.out.println("Order updated");
    }

    public void deleteClient(String clientId) {
        System.out.println("Client deleted");
    }
}
