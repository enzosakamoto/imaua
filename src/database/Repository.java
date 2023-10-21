package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import home.Home;
import shared.domain.entities.Client;
import shared.domain.entities.Order;
import shared.domain.entities.Restaurant;

public class Repository {
    private ResourceBundle bn = null;
    public Restaurant restaurant_moleza = new Restaurant(1, "Restaurant Moleza");
    public Restaurant restaurant_biba = new Restaurant(2, "Restaurant Biba");
    public Restaurant restaurant_techfood = new Restaurant(3, "Restaurant TechFood");

    public Repository() {
        this.bn = ResourceBundle.getBundle("Bundle", new java.util.Locale(Home.language.split(",")[0],
                Home.language.split(",")[1]));

        this.restaurant_moleza.setMenu(new String[] { bn.getString("rep.moleza.item1"), bn.getString("rep.moleza.item2"), bn.getString("rep.moleza.item3"), bn.getString("rep.moleza.item4") });
        this.restaurant_moleza.setPrices(new double[] { 23.0, 10.0, 15.0, 8.0 });

        this.restaurant_biba
                .setMenu(new String[] { bn.getString("rep.biba.item1"), bn.getString("rep.biba.item2"),  bn.getString("rep.biba.item3"), bn.getString("rep.biba.item4") });
        this.restaurant_biba.setPrices(new double[] { 8.0, 6.0, 8.0, 24.0 });

        this.restaurant_techfood
                .setMenu(new String[] { bn.getString("rep.techfood.item1"), bn.getString("rep.techfood.item2"), bn.getString("rep.techfood.item3"), bn.getString("rep.techfood.item4") });
        this.restaurant_techfood.setPrices(new double[] { 12.0, 22.0, 5.0, 10.0 });
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
            stmt.setString(3, client.getPassword());
            stmt.setString(4, String.valueOf(client.getCredits()));
            stmt.executeUpdate();
            System.out.println(bn.getString("rep.client.created"));
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.out.println(e.getMessage());

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            throw new SQLException(bn.getString("rep.client.error"));
        } finally {
            Connector.closeConn(connection, stmt);
        }
    }

    public void createOrder(Order order) {
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
            stmt.setBoolean(6, order.getIsDone());
            stmt.executeUpdate();
            System.out.println(bn.getString("rep.order.created"));
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
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("id");
            }
            throw new SQLException();
        } catch (SQLException ex) {
            throw new SQLException(bn.getString("rep.client.error"));
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

    public Client getClientByIdClient(String IdClient) {
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

    public ArrayList<Order> getAllOrdersByIdClient(String IdClient) {
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
                    order.setIsDone(rs.getString("isdone").equals("1") ? true : false);
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

    public void updateStatusOrderByOrderId(String orderId, boolean isDone) {
        System.out.println(bn.getString("rep.order.updated"));
    }

    public void updateClientCreditsByIdClient(String clientId, double credits) {
        String sqlUpdateClient = "UPDATE clients SET credits = ? WHERE id = ?;";
        Connection connection = Connector.getConn();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sqlUpdateClient);
            stmt.setDouble(1, credits);
            stmt.setString(2, clientId);
            stmt.executeUpdate();
            System.out.println(bn.getString("rep.credits.updated"));
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
}
