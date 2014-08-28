package applango.common.services.DB.mongo;

import applango.common.SeleniumTestBase;
import applango.common.services.beans.Applango;
import com.mongodb.*;
import com.mongodb.util.JSON;
import junit.framework.Assert;

import static java.lang.Thread.sleep;

public class mongoDB extends SeleniumTestBase {


    public static int countFailureLogins(Applango applango, DBCollection collection) {
        logger.info("Count how many failure logins for userId : " + applango.getUsername());
        String jsonCustomer = " {$and : [{'userId' : '" + applango.getUsername() +"'} , {'status':'failure'} ] }";
        DBObject dbObjectRecordQuery = (DBObject) JSON.parse(jsonCustomer);
        return (int) collection.count(dbObjectRecordQuery);
    }
    public static int countSuccessfulLogins(Applango applango, DBCollection collection) {
        logger.info("Count how many successful logins for userId : " + applango.getUsername());
        String jsonCustomer = " {$and : [{'userId' : '" + applango.getUsername() +"'} , {'status':'success'} ] }";
        DBObject dbObjectRecordQuery = (DBObject) JSON.parse(jsonCustomer);
        return (int) collection.count(dbObjectRecordQuery);
    }

    public static int loginsThisMonth(Applango applango, DBCollection collection) {
//        logger.info("Count how many successful logins for userId : " + applango.getUsername());
        String jsonCustomer = " {$or : [{'customerId' : 'fff'}, {'loginTime' : {$gte : '(\"2014-01-01 08:15:04.000Z\")'}} ] }";
        DBObject dbObjectRecordQuery = (DBObject) JSON.parse(jsonCustomer);
        return (int) collection.count(dbObjectRecordQuery);
    }



    public static DBObject removeRecordsFromDB(Applango applango, DBCollection coll) {
        logger.info("Remove all records with customerId : " + applango.getUsername() );
        String jsonCustomer = "{'customerId' : '" + applango.getCustomerForoAuth() +"'}";
        DBObject dbObjectRecordQuery = (DBObject) JSON.parse(jsonCustomer);
        if (!(coll.count(dbObjectRecordQuery) == 0)) {
            coll.remove(dbObjectRecordQuery, WriteConcern.ACKNOWLEDGED);

        }
        return dbObjectRecordQuery;
    }


    public static String getSpecialToken(Applango applango, DBCollection coll) {
        logger.info("Get the email token of : " + applango.getUsername() );
        String token;
        int maxWait = 10;
        try {
            String jsonCustomer = "{'userId' : '" +  applango.getUsername() +"'}";
            DBObject dbObjectRecordQuery = (DBObject) JSON.parse(jsonCustomer);
            while ( (coll.count(dbObjectRecordQuery) == 0) && maxWait> 0) {
                sleep(1000);
                maxWait--;
            }
            token = coll.find(dbObjectRecordQuery).next().get("specialToken").toString();
        }
        catch (Exception ex) {
            token = null;
            logger.error(ex.getMessage());
        }
        return token;

    }

    public static String encodeToken(String token) {
        String encodedToken = token.replace("/", "%20");
        encodedToken = encodedToken.replace("+", "%2B");
        encodedToken = encodedToken.replace("=", "%3D");
        return encodedToken;
    }


    public static  void checkRecordCreatedInDB(Applango applango, String connection, DBCollection coll, DBObject dbObjectRecordQuery) {
        logger.info("Check that record created in DB table : " + connection);
        Assert.assertTrue(coll.getCount(dbObjectRecordQuery) == 1);
    }

    protected static void checkNRecordsCreatedInDB(Applango applango, String connection, DBCollection coll, DBObject dbObjectRecordQuery, int n) {
        logger.info("Check that record created in DB table : " + connection);
        Assert.assertTrue(coll.getCount(dbObjectRecordQuery) == n);
    }

    public static int getNumberOfUsersWithoutEndDate(DBCollection appInfoConnection, Applango applango) {
        String jsonCustomerWithoutEndDate =  "{$and : [{'customerId' : '" +  applango.getUserForoAuth() +"'}, {endDate : null}, {beginDate : {$ne:null} }] }";
        DBObject query = (DBObject) JSON.parse(jsonCustomerWithoutEndDate);
        return (int) appInfoConnection.count(query);

    }
    public static int getNumberOfLoginUsersInLastWeek(DBCollection appInfoConnection, Applango applango) {
        String loginOverTheLastWeek =  "( { distinct :\"loginInfo\"" + ",key: \"externalId\"" + ", query : { $and: [ {loginTime: {gte: new Date(ISODate().getTime()-1000*60*60*24*7)}}, {customerId : \"automationCustomer\"" + "}] } })";
        DBObject query = (DBObject) JSON.parse(loginOverTheLastWeek);
        return (int) appInfoConnection.count(query);

    }


    public static int getPriceByLicenseType(DBCollection appInfoConnection) {
        return getPriceByLicenseType(appInfoConnection, "PID_CHATTER");
    }

    public static int getPriceByLicenseType(DBCollection appInfoConnection, String licenseType) {

        String jsonCustomer = "{$and : [{'licenseType' : '"+ licenseType +"'}, {'customerId': 'automationCustomer'} ]}";
        DBObject dbObjectRecordQuery = (DBObject) JSON.parse(jsonCustomer);
        Object strPrice = appInfoConnection.find(dbObjectRecordQuery).next().get("price");
        System.out.println("The price for " +licenseType+ " is " + strPrice);
        return  (Integer) strPrice;

    }

    public static void updateLicensePrice(DBCollection appInfoConnection, int newPrice) {
        updateLicensePrice(appInfoConnection, "PID_CHATTER", newPrice);
    }

    public static void updateLicensePrice(DBCollection appInfoConnection, String licenseType, int newPrice) {
        System.out.println("Updating license price of " + licenseType);
        String jsonCustomer = "{$and : [{'licenseType' : '"+ licenseType +"'}, {'customerId': 'automationCustomer'} ]}";
        DBObject dbObjectRecordQuery = (DBObject) JSON.parse(jsonCustomer);

        BasicDBObject set = new BasicDBObject("$set", new BasicDBObject("price", newPrice));
        appInfoConnection.update(dbObjectRecordQuery, set);
        System.out.println("The new price for " +licenseType+ " is " + newPrice);

    }



}

