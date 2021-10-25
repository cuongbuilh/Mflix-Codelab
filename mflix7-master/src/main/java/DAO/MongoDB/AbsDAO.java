package DAO.MongoDB;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public abstract class AbsDAO {

    static MongoDatabase db;

    MongoDatabase getDB() {
        if (db == null) {
            //ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
            ConnectionString connectionString = new ConnectionString("mongodb+srv://root:root@cluster0.lh5rj.mongodb.net");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .codecRegistry(fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build())))
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            db = mongoClient.getDatabase("sample_mflix");
            System.out.println("Connect to DB");
        }
        return db;
    }
}