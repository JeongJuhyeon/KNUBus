package kr.knu.busreservations;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.MongoCursor;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class DBManagement {
    private String dbAdminPassword;
    private String mongoAddress;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public DBManagement() {
        dbAdminPassword = "";
        mongoAddress = String.format("mongodb://myUserAdmin:%s@155.230.91.220", dbAdminPassword);

        mongoClient = MongoClients.create(mongoAddress);
        database = mongoClient.getDatabase("testdb");

    }


    public static void main(String[] args) {
        connectionTest();
    }

    static void connectionTest() {
        String dbAdminPassword = "";
        String mongoAddress = String.format("mongodb://myUserAdmin:%s@155.230.91.220", dbAdminPassword);

        MongoClient mongoClient = MongoClients.create(mongoAddress);
        MongoDatabase database = mongoClient.getDatabase("testdb");
        MongoCollection<Document> collection = database.getCollection("testcollection");
        collection.find().forEach(printBlock);
    }

    static Block<Document> printBlock = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            System.out.println(document.toJson());
        }
    };

    public User verifyUserDetails(String username, String password){
        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("username", username));
        obj.add(new BasicDBObject("password", password));
        query.put("$and", obj);

        setCollection("p1_users");
        Document result = collection.find(query).first();
        if (result == null) {
            return null;
        }

        return new User((String) result.get("user_id"), username);
    }

    public void setCollection(String collectionName){
        collection = database.getCollection(collectionName);
    }
}
