package kr.knu.busreservations;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.MongoCursor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Base64;

import static com.mongodb.client.model.Filters.*;

public class DBManagement {
    private String dbAdminPassword;
    private String mongoAddress;
    private static MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public DBManagement(String database) {
        String DBPW = getDBPW();

        this.mongoAddress = String.format("mongodb://myUserAdmin:%s@155.230.91.220", getDBPW());
        this.mongoClient = MongoClients.create(mongoAddress);
        this.database = mongoClient.getDatabase(database);
    }

    private static String getDBPW(){
        String encodedString = new String();
        try {
            String path = new File("").getAbsolutePath() + "\\bin.exe";
            Scanner scanner = new Scanner(new File(path));
            encodedString = scanner.next();
            scanner.close();
        }
        catch(FileNotFoundException e) {
            System.err.println(e);
            System.exit(3);
        }

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }


    public DBManagement(){
        this("testdb");
    }

    public static void main(String[] args) {
        connectionTest();
    }

    static void connectionTest() {
        String mongoAddress = String.format("mongodb://myUserAdmin:%s@155.230.91.220", getDBPW());

        MongoClient mongoClient = MongoClients.create(mongoAddress);
        MongoDatabase database = mongoClient.getDatabase("testdb");
        MongoCollection<Document> collection = database.getCollection("testcollection");
        collection.find().forEach(printBlock);
    }

    private static Block<Document> printBlock = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            System.out.println(document.toJson());
        }
    };

    public User verifyUserDetails(String username, String password){
        setCollection("users");
        // Debugging: collection.find().forEach(printBlock);

        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("username", username));
        obj.add(new BasicDBObject("password", password));
        query.put("$and", obj);

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
