package kr.knu.busreservations;

import com.mongodb.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Arrays;

public class DBInterface {

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
}
