package shared.infra.repository;

import java.util.ArrayList;

import shared.entities.Client;
import shared.entities.Order;

public interface IRepository {
    public void createClient(Client client) throws Exception;

    public void createOrder(Order order) throws Exception;

    public String getIdClientByLogin(String name, String password) throws Exception;

    public void getErrorByName(String name) throws Exception;

    public Client getClientByIdClient(String IdClient) throws Exception;

    public ArrayList<Order> getAllOrdersByIdClient(String IdClient) throws Exception;

    public void updateStatusOrderByOrderId(String orderId, int status) throws Exception;

    public void updateClientCreditsByIdClient(String clientId, double credits) throws Exception;
}
