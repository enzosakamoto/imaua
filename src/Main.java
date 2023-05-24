import database.Crud;
import shared.domain.entities.Client;

public class Main {
    public static void main(String[] args) throws Exception {
        Client cliente = new Client("heitas", "adsasdasdsadsa");
        Crud crud = new Crud();
        crud.createClient(cliente);
    }
}
