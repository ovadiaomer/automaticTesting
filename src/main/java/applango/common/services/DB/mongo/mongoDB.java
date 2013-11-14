package applango.common.services.DB.mongo;

import applango.common.SeleniumTestBase;
import applango.common.services.DB.mongo.beans.Database;
import applango.common.services.objectMapper;
import com.mongodb.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 05/11/13
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
public class mongoDB extends SeleniumTestBase {
    public static final String DATABASE = "database";
    public static Map configPropertiesMapper;
    public static Database db1;


    public static DB connectToServer() throws IOException, ParserConfigurationException, SAXException {

        GetDb getDb = new GetDb().invoke();
        String database = getDb.getDatabase();
        DB db = getDb.getDb();

        logger.info("Authenticate to DB: " + database + " with User: " + db1.getUsername() + " Password: " + db1.getPassword().toCharArray());
        boolean auth = db.authenticate(db1.getUsername(), db1.getPassword().toCharArray());
        if (auth) {
            logger.info("Authentication succeeded");
        }
        else if (!auth) {
            logger.info("Authentication failed");
        }
        return db;
    }


    public static DB connectToServer(String host, String database, int port) throws UnknownHostException {
        logger.info("connect to DB: " + host + " - " + database);

        MongoClient mongoClient = new MongoClient(host, port);
        DB db = mongoClient.getDB(database);

        return db;
    }

    public static void insertToDB(DB db, String collection, DBObject document) {
        logger.info("Inserting " + document + " to collection " + collection);
        DBCollection table = db.getCollection(collection);

        table.insert(document);


    }

    public static void deleteFromDB(DB db, String collection, DBObject document) {
        logger.info("Deleting " + document + " from collection " + collection);
        db.getCollection(collection).remove(document);
//        DBCursor cursor = table.find(document);
//        while (cursor.hasNext()) {
//            table.findAndRemove(document);
//            cursor = table.find(document);
//
//        }
    }


    public static void updateDB(DB db, String collection, DBObject queryDocument, DBObject newDocument) {
        logger.info("Updating " + queryDocument + " on collection " + collection);
        DBCollection table = db.getCollection(collection);
        table.findAndModify(queryDocument, newDocument);
    }

    protected static List<DBObject> readDB(DB db, String collection, DBObject document) throws Exception {
        DBCursor cursor;
        logger.info("Reading " + document + " from collection " + collection);
        List<DBObject> list = new ArrayList<DBObject>();
        try {
            boolean moreResultsInCursor = false;
            DBObject dbObjects = BasicDBObjectBuilder.start().add("", "").get();
//            dbObjects.put("", "");
            document.removeField("_id");
            DBCollection table = db.getCollection(collection);
            cursor = table.find(document);

            if (cursor.hasNext()) {
                moreResultsInCursor = true;
                dbObjects = cursor.next();
            } else {
                dbObjects.put("", "");
            }


            logger.info("Inserting results to result list");
            while ((moreResultsInCursor) && !(((BasicDBObject) dbObjects).isEmpty())) {
                System.out.println(" Current DBObject" + dbObjects);
                list.add(dbObjects);
                if (cursor.hasNext()) {
                    dbObjects =  cursor.next();
                } else {
                    moreResultsInCursor = false;
                }


            }

//            return list;
        } catch (Exception e) {
            logger.error("Document not found in DB \n" + e);

        }
        return list;
    }

    public static List<DBObject> connectAndReadFromDB(String collection, DBObject document, String searchBy) throws Exception {
        DB db = connectToServer();
        return readFromDB(db, collection, document, searchBy);

    }
    public static List<DBObject> readFromDB(DB db, String collection, DBObject document, String searchBy) throws Exception {

        if (searchBy != null) {
//            return readDB(db, collection, document).get(searchBy);

        }
        return readDB(db, collection, document);
    }



    public static void connectAndInsertToDB(String collection, DBObject document) throws IOException, ParserConfigurationException, SAXException {

        DB db = connectToServer();
        insertToDB(db, collection, document);

    }
    public static void connectAndDeleteFromDB(String collection, DBObject document) throws IOException, ParserConfigurationException, SAXException {

        DB db = connectToServer();
        deleteFromDB(db, collection, document);

    }
    public static void connectAndUpdateDB(String collection, DBObject document, DBObject newDocument) throws IOException, ParserConfigurationException, SAXException {

        DB db = connectToServer();
        updateDB(db, collection, document, newDocument);

    }

    public static class GetDb {
        private String database;
        private DB db;

        public String getDatabase() {
            return database;
        }

        public DB getDb() {
            return db;
        }

        public GetDb invoke() throws IOException, ParserConfigurationException, SAXException {
            configPropertiesMapper = objectMapper.getConfigProperties();
            database = configPropertiesMapper.get(DATABASE).toString();
            logger.info("Connect to DB: " + database);
            db1 = readFromDatabaseConfigurationFile.getDatabaseConfigurationFileByDbName(database);

            MongoClient mongoClient = new MongoClient(db1.getUrl(), db1.getPort());
            db = mongoClient.getDB(database);
            return this;
        }
    }
}

