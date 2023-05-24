
import database.Crud;
import shared.domain.entities.Client;
import shared.domain.entities.Order;

public class Main {
    public static void main(String[] args) throws Exception {
        Crud crud = new Crud();
        // Client client = new Client("matueeeeee", "123");
        // crud.createClient(client);
        // String id_client = client.getId();

        // Order order = new Order(1, id_client, "Hamburguer");
        // crud.createOrder(order);
        String id = crud.getIdClientByLogin("matueeeeee", "123");
        System.out.println(id);
        crud.updateClientCredits(id, 999);
    }
}
