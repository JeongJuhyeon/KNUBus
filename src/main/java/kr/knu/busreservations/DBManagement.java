package kr.knu.busreservations;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.MongoClientSettings.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DBManagement {
    public static final String ID_KEY = "user_id";
    private static MongoClient mongoClient = null;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private static final String USERS_COLLECTION = "users";
    private static final String BUSES_COLLECTION = "buses";
    private static final String TERMINALS_COLLECTION = "terminals";

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

    public void initTestDB(){
        BasicDBObject document = new BasicDBObject();

        setDatabase("testdb");
        setCollection(TERMINALS_COLLECTION);
        collection.deleteMany(document);

        setCollection(BUSES_COLLECTION);
        collection.deleteMany(document);

        insertTerminalsIntoDB("testdb");
        insertBusIntoDB("testdb");
    }

    private static Block<Document> printBlock = document -> System.out.println(document.toJson());

    boolean usernameAlreadyExists(String username){
        setCollection(USERS_COLLECTION);
        Document queryResult = collection.find(eq("username", username)).first();
        return (queryResult != null);
    }

    void createNewUser(Map<String, String> userDetails) {
        setCollection(USERS_COLLECTION);
        Document newUserDocument = new Document();
        for (Map.Entry<String, String> entry : userDetails.entrySet()) {
            newUserDocument.append(entry.getKey(), entry.getValue());
        }
        newUserDocument.append(ID_KEY, getNextUserID() + 1);
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

        int userId = possibleDoubleTointeger(result.get(ID_KEY));

        return new User(userId, username);
    }

    private static String getDBPW(){
        String encodedString = "";
        try {
            String path = new File("").getAbsolutePath() + "/bin.exe";
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
        Document result = collection.find().sort(new Document().append(ID_KEY, -1)).first();
        int userId;

        if (result == null) {
            System.out.println("No database entries with 'user_id' field found");
            return 1;
        }

        userId = possibleDoubleTointeger(result.get(ID_KEY));
        
        return userId;
    }

    private int possibleDoubleTointeger(Object possibleDouble) {
        if (possibleDouble.getClass() == Double.class) {
            return ((Double) possibleDouble).intValue();
        }
        else
            return (int) possibleDouble;
    }


    /**
     * @param busId Id of the bus to search for
     * @return null if busId not in db, else the relevant bus as Bus object
     */
    Bus getBusById(int busId){



        List<Seat> seatList = new ArrayList<Seat>();

        setCollection(BUSES_COLLECTION);
        Document queryResult = collection.find(eq("id", busId)).first();

        if (queryResult == null)
            return null;

        ArrayList<Document> seatDocuments = (ArrayList<Document>) queryResult.get("seats");

        seatList = seatDocuments.stream().filter(d -> (Boolean) d.get("occupied"))
                                         .map(d -> new Seat(possibleDoubleTointeger(d.get("seatNo")),
                                     (Boolean) d.get("occupied"), possibleDoubleTointeger(d.get("ticketId")),
                                     possibleDoubleTointeger(d.get("userId"))))
                                         .collect(Collectors.toList());

        seatList.addAll(seatDocuments.stream().filter(d -> !((Boolean) d.get("occupied")))
                                              .map(d -> new Seat(possibleDoubleTointeger(d.get("seatNo")),
                                                      (Boolean) d.get("occupied"))).collect(Collectors.toList()));

        int startTerminalId = possibleDoubleTointeger(queryResult.get("startTerminalId"));
        int endTerminalId = possibleDoubleTointeger(queryResult.get("endTerminalId"));

        Terminal startTerminal = new Terminal(startTerminalId, getTerminalNameById(startTerminalId));
        Terminal endTerminal = new Terminal(endTerminalId, getTerminalNameById(endTerminalId));

        return new Bus(busId, seatList, startTerminal, endTerminal);
    }

    String getTerminalNameById(int terminalId) {
        setCollection(TERMINALS_COLLECTION);
        Document queryResult = collection.find(eq("id", terminalId)).first();
        return (String) queryResult.get("name");
    }

    private void insertTerminalsIntoDB(String dbName) {
        setDatabase(dbName);
        setCollection(TERMINALS_COLLECTION);

        Document queryResult = collection.find(eq("id", 1)).first();
        Document newTerminal = new Document();
        if (queryResult == null) {
            newTerminal.put("id", 1);
            newTerminal.put("name", "Seoul");
        }

        collection.insertOne(newTerminal);

        queryResult = collection.find(eq("id", 2)).first();
        newTerminal = new Document();
        if (queryResult == null) {
            newTerminal.put("id", 2);
            newTerminal.put("name", "Dongdaegu");
        }

        collection.insertOne(newTerminal);
    }

    private void insertBusIntoDB(String dbName) {
        setDatabase(dbName);
        setCollection(BUSES_COLLECTION);

        Document queryResult = collection.find(eq("id", 1)).first();

        if (queryResult != null)
            return;

        ArrayList<Seat> seats = new ArrayList<>();
        Random random = new Random();
        Boolean occupied;

        List<BasicDBObject> seatObjects = new ArrayList<>();
//        int seatNo;
//        boolean occupied;
//        int ticketId;
//        int userId;

        for (int i = 1; i < 29; i++) {
            occupied = random.nextBoolean();
            if (!occupied)
                seatObjects.add(new BasicDBObject("occupied", false).append("seatNo", i));
            else
                seatObjects.add(new BasicDBObject("occupied", true)
                        .append("seatNo", i)
                        .append("ticketId", random.nextInt(2000))
                        .append("userId", random.nextInt(2000)));
        }

        Document newBusDocument = new Document();
        newBusDocument.append("id", 1);
        newBusDocument.append("startTerminalId", 1);
        newBusDocument.append("endTerminalId", 2);
        newBusDocument.append("seats", seatObjects);

        collection.insertOne(newBusDocument);
    }
}