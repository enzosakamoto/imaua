package shared.infra.repository;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import shared.entities.Client;
import shared.entities.Meal;
import shared.entities.Order;
import shared.entities.Restaurant;
import shared.infra.connector.Connector;

public class Repository implements IRepository {
    private ResourceBundle bn;
    public ArrayList<Restaurant> restaurants;

    public Repository(ResourceBundle bn) {
        this.bn = bn;
        this.restaurants = new ArrayList<Restaurant>(
                Arrays.asList(
                        new Restaurant(1, "Restaurante Moleza", new ArrayList<Meal>(
                                Arrays.asList(
                                        new Meal(bn.getString("rep.moleza.item1"), 8.0),
                                        new Meal(bn.getString("rep.moleza.item2"), 16.0),
                                        new Meal(bn.getString("rep.moleza.item3"), 15.0),
                                        new Meal(bn.getString("rep.moleza.item4"), 7.0)))),
                        new Restaurant(2, "Restaurante Biba", new ArrayList<Meal>(
                                Arrays.asList(
                                        new Meal(bn.getString("rep.biba.item1"), 7.0),
                                        new Meal(bn.getString("rep.biba.item2"), 12.0),
                                        new Meal(bn.getString("rep.biba.item3"), 5.0),
                                        new Meal(bn.getString("rep.biba.item4"), 1.0)))),
                        new Restaurant(3, "Restaurante TechFood", new ArrayList<Meal>(
                                Arrays.asList(
                                        new Meal(bn.getString("rep.techfood.item1"), 22.0),
                                        new Meal(bn.getString("rep.techfood.item2"), 20.0),
                                        new Meal(bn.getString("rep.techfood.item3"), 8.0),
                                        new Meal(bn.getString("rep.techfood.item4"), 12.0))))));
    }

    public void createClient(Client client) throws SQLException {
        String sqlCreateClient = "INSERT INTO clients VALUES (?, ?, ?, ?);";
        Connection connection = Connector.getConn();
        PreparedStatement stmt = null;
        try {
            getErrorByName(client.getName());
            stmt = connection.prepareStatement(sqlCreateClient);
            stmt.setString(1, client.getId());
            stmt.setString(2, client.getName());
            stmt.setString(3, toHexString(getSHA(client.getPassword())));
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
            throw new SQLException(bn.getString("rep.client.error"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        } finally {
            Connector.closeConn(connection, stmt);
        }
    }

    public void createOrder(Order order) throws SQLException {
        String sqlCreateOrder = "INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?);";
        Connection connection = Connector.getConn();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sqlCreateOrder);
            stmt.setString(1, order.getId());
            stmt.setString(2, order.getIdClient());
            stmt.setString(3, order.getDate());
            stmt.setInt(4, order.getIdRestaurant());
            stmt.setString(5, order.getMeal());
            stmt.setInt(6, order.getIsDone());
            stmt.executeUpdate();
            System.out.println("Pedido criado com sucesso!");
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

    public String getIdClientByLogin(String name, String password) throws SQLException {
        String sqlGetClient = "SELECT id FROM clients WHERE (name = ? and password = ?);";
        Connection connection = Connector.getConn();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(sqlGetClient);
            stmt.setString(1, name);
            stmt.setString(2, toHexString(getSHA(password)));
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("id");
            }
            throw new SQLException();
        } catch (SQLException ex) {
            throw new SQLException("Usuário não encontrado!");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
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
                    throw new SQLException(bn.getString("rep.client.error2"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            Connector.closeConn(connection, stmt);
        }
    }

    public Client getClientByIdClient(String IdClient) throws SQLException {
        String sqlGetClient = "SELECT * FROM clients WHERE (id = ?);";
        Connection connection = Connector.getConn();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(sqlGetClient);
            stmt.setString(1, IdClient);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Client client = new Client(IdClient, rs.getString("name"), rs.getString("password"),
                        Double.parseDouble(rs.getString("credits")));
                return client;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            Connector.closeConn(connection, stmt);
        }
        return null;
    }

    public ArrayList<Order> getAllOrdersByIdClient(String IdClient) throws SQLException {
        String sqlGetClient = "SELECT * FROM orders WHERE (id_client = ?);";
        Connection connection = Connector.getConn();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Order> orders = new ArrayList<Order>();
        try {
            stmt = connection.prepareStatement(sqlGetClient);
            stmt.setString(1, IdClient);
            rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    Order order = new Order(rs.getInt("id_restaurant"), rs.getString("id_client"),
                            rs.getString("meal"));
                    order.setId(rs.getString("id"));
                    order.setIsDone(Integer.valueOf(rs.getString("isdone")));
                    order.setDate(rs.getString("date"));
                    orders.add(order);
                } while (rs.next());
            }
            return orders;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return orders;
        } finally {
            Connector.closeConn(connection, stmt);
        }
    }

    public void updateStatusOrderByOrderId(String orderId, int status) throws SQLException {
        System.out.println("Order updated");
    }

    public void updateClientCreditsByIdClient(String clientId, double credits) throws SQLException {
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

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public String arrayCharToString(char[] array) {
        String string = "";
        for (char c : array) {
            string += c;
        }
        return string;
    }
}
