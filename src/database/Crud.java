package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            getErrorByName(client.getName());
            stmt = connection.prepareStatement(sqlCreateClient);
            stmt.setString(1, client.getId());
            stmt.setString(2, client.getName());
            stmt.setString(3, client.getPassword());
            stmt.setString(4, String.valueOf(client.getCredits()));
            stmt.executeUpdate();
            System.out.println("Cliente incluído com sucesso!");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println(e.getMessage());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
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
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println(e.getMessage());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } finally {
            Connector.closeConn(connection, stmt);
        }
    }

    public String getIdClientByLogin(String name, String password) {
        String sqlGetClient = "SELECT id FROM clients WHERE (name = ? and password = ?);";
        Connection connection = Connector.getConn();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(sqlGetClient);
            stmt.setString(1, name);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("id");
            }
            return "null";
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return "null";
        } finally {
            Connector.closeConn(connection, stmt);
        }
    }

    public void getErrorByName(String name) throws SQLException {
        String sqlGetClient = "SELECT id FROM clients WHERE (name = ?);";
        Connection connection = Connector.getConn();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(sqlGetClient);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("id") != null)
                    throw new SQLException("Cliente já cadastrado!");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            Connector.closeConn(connection, stmt);
        }
    }

    public ArrayList<Order> getAllOrdersByIdClient(String clientId) {
        String sqlGetClient = "SELECT * FROM orders WHERE (id_client = ?);";
        Connection connection = Connector.getConn();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Order> orders = new ArrayList<Order>();
        try {
            stmt = connection.prepareStatement(sqlGetClient);
            stmt.setString(1, clientId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    Order order = new Order(rs.getInt("id_restaurant"), rs.getString("id_client"),
                            rs.getString("meal"));
                    order.setId(rs.getString("id"));
                    order.setIsDone(rs.getString("isdone").equals("1") ? true : false);
                    orders.add(order);
                } while (rs.next());
                return orders;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            Connector.closeConn(connection, stmt);
        }
        return null;
    }

    public String getIdClientByName(String name) {
        System.out.println("Getting id client by name");
        return "id_client";
    }

    public void updateOrder(String orderId) {
        System.out.println("Order updated");
    }

    public void updateClientCredits(String clientId, double credits) {
        String sqlUpdateClient = "UPDATE clients SET credits = ? WHERE id = ?;";
        Connection connection = Connector.getConn();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sqlUpdateClient);
            stmt.setDouble(1, credits);
            stmt.setString(2, clientId);
            stmt.executeUpdate();
            System.out.println("Créditos atualizados com sucesso!");
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println(e.getMessage());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } finally {
            Connector.closeConn(connection, stmt);
        }
    }

    public void deleteClient(String clientId) {
        System.out.println("Client deleted");
    }
}
