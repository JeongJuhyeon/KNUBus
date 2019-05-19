package kr.knu.busreservations;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.mongodb.client.model.Filters.*;

public class DBManagement {
    public static final String ID_KEY = "user_id";
    private static MongoClient mongoClient = null;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private static final String USERS_COLLECTION = "users";

    public static void main(String[] args) {
        DBManagement dbManagement = new DBManagement();
        dbManagement.connectionTest();
    }


    public DBManagement(String database) {
        connect();
        setDatabase(database);
    }

    public DBManagement(){
        this("testdb");
    }


    static void connect() {
        if (mongoClient == null) {
            String mongoAddress = String.format("mongodb://myUserAdmin:%s@155.230.91.220", getDBPW());
            mongoClient = MongoClients.create(mongoAddress);
        }
    }

    void connectionTest() {
        String mongoAddress = String.format("mongodb://myUserAdmin:%s@155.230.91.220", getDBPW());

        MongoClient testMongoClient = MongoClients.create(mongoAddress);
        MongoDatabase testDatabase = testMongoClient.getDatabase("testdb");
        MongoCollection<Document> testCollection = testDatabase.getCollection("testcollection");
        testCollection.find().forEach(printBlock);
    }

    private static Block<Document> printBlock = document -> System.out.println(document.toJson());

    boolean usernameAlreadyExists(String username){
        setCollection(USERS_COLLECTION);
        Document queryResult = collection.find(eq("username", username)).first();
        if (queryResult == null)
            return false;
        return true;
    }

    void createNewUser(Map<String, String> userDetails) {
        setCollection(USERS_COLLECTION);
        Document newUserDocument = new Document();
        for (Map.Entry<String, String> entry : userDetails.entrySet()) {
            newUserDocument.append(entry.getKey(), entry.getValue());
        }
        newUserDocument.append("user_id", getNextUserID());
        collection.insertOne(newUserDocument);
    }

    User verifyUserDetails(String username, String password){
        setCollection(USERS_COLLECTION);
        // Debugging: collection.find().forEach(printBlock);

        BasicDBObject query = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<>();
        obj.add(new BasicDBObject("username", username));
        obj.add(new BasicDBObject("password", password));
        query.put("$and", obj);

        Document result = collection.find(query).first();
        if (result == null) {
            return null;
        }

        int user_id;
        if (result.get("user_id").getClass() == Double.class) {
            user_id = ((Double) result.get("user_id")).intValue();
        }
        else
            user_id = (int) result.get(ID_KEY);

        return new User(user_id, username);
    }

    private static String getDBPW(){
        String encodedString = "";
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

    private void setCollection(String collectionName){
        if (collection == null || !collection.getNamespace().getCollectionName().equals(collectionName))
            collection = database.getCollection(collectionName);
    }

    private void setDatabase(String dbName){
        this.database = mongoClient.getDatabase(dbName);
    }

    private int getNextUserID(){
        setCollection(USERS_COLLECTION);
        Document result = collection.find().sort(new Document().append("user_id", -1)).first();
        Double user_id = (Double) result.get("user_id");
        return user_id.intValue();
    }
}
