package DAO.MovieDB;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public  abstract class AbsDAO {
    static WebTarget apiTarget;

    WebTarget getApiTarget() {
        if (apiTarget == null) {
            Client client = ClientBuilder.newClient();
            apiTarget = client.target("https://api.themoviedb.org/3").queryParam("api_key", "62da0532e34e31705aee0abf47ddeeff");
            System.out.println("Connect to API");
        }
        return apiTarget;
    }

}
