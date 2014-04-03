package applango.common.services.DB.mongo;

import applango.common.SeleniumTestBase;
import applango.common.services.beans.Applango;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;
import junit.framework.Assert;

public class mongoDB extends SeleniumTestBase {


    public static DBObject removeRecordsFromDB(Applango applango, DBCollection coll) {
        logger.info("Remove all records with customerId : " + applango.getCustomerForoAuth() );
        String json_Omer = "{'customerId' : '" + applango.getCustomerForoAuth() +"'}";
        DBObject dbObjectRecordQuery = (DBObject) JSON.parse(json_Omer);
        coll.remove(dbObjectRecordQuery, WriteConcern.ACKNOWLEDGED);
        return dbObjectRecordQuery;
    }

    public static  void checkRecordCreatedInDB(Applango applango, String connection, DBCollection coll, DBObject dbObjectRecordQuery) {
        logger.info("Check that record created in DB table : " + connection);
        Assert.assertTrue(coll.getCount(dbObjectRecordQuery) == 1);
//        coll.find(dbObjectRecordQuery).next().get("customerId").toString().contains(applango.getUserForoAuth());
    }
}

