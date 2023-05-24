
import java.util.ArrayList;

import database.Crud;
import shared.domain.entities.Client;
import shared.domain.entities.Order;

public class Main {
    public static void main(String[] args) throws Exception {
        Crud crud = new Crud();

        // Client client = new Client("matueeeeee", "123");
        // crud.createClient(client);
        // String id_client = client.getId();

        String id = crud.getIdClientByLogin("oie", "123");
        System.out.println(id);
        ArrayList<Order> orders = crud.getAllOrdersByIdClient(id);
        for (Order order : orders) {
            System.out.println(order.getMeal());
        }
        // Order order = new Order(1, id, "loira gelada");
        // crud.createOrder(order);
        // crud.updateClientCredits(id, 999);

    }
}
