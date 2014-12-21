/*
package applango.common.services.RestApi;

import applango.common.SeleniumTestBase;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 27/10/13
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 *//*

public class restAPI extends SeleniumTestBase {

    public static String executeGetRequest(String url) throws ClientProtocolException, IOException {

        DefaultHttpClient client = new DefaultHttpClient();
        StringBuffer buff = new StringBuffer();
        HttpGet request = new HttpGet(String.valueOf(url));

        StringEntity input = new StringEntity("product");

        StringBuilder sb = new StringBuilder();
        logger.info("Execute request");

        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

        logger.info("Print Data");
        String line = "";

        while ((line = rd.readLine()) != null) {
            sb.append(line);
            System.out.println(line);

        }
        return sb.toString();
    }



    public static String executePostRequest(String url, List<BasicNameValuePair> urlParameters) throws ClientProtocolException, IOException {



        DefaultHttpClient client = new DefaultHttpClient();
        StringBuffer buff = new StringBuffer();
//        String url = "https://selfsolve.apple.com/wcResults.do";
        HttpPost request = new HttpPost(String.valueOf(url));

        request.setEntity(new UrlEncodedFormEntity(urlParameters));

        StringEntity input = new StringEntity("product");

        StringBuilder sb = new StringBuilder();
        logger.info("Execute request");

        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

        logger.info("Print Data");
        String line = "";

        while ((line = rd.readLine()) != null) {
            sb.append(line);
            System.out.println(line);

        }
        return sb.toString();
    }

    public static String executeRequest(String requestType, String url, List<BasicNameValuePair> urlParameters) throws IOException {
        if (requestType.toUpperCase().compareToIgnoreCase("GET")==0) {
              executeGetRequest(url);
        }
        else if (requestType.toUpperCase().compareToIgnoreCase("POST")==0) {
            return executePostRequest(url, urlParameters);
        }
        return null;
    }

    @Test
    public static void main(String [] args) throws IOException {
//        logger.info("++++++++++++++++++++++++++++Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "++++++++++++++++++++++");
        executeRequest("get", "http://www.google.com", null);
        String url = "https://selfsolve.apple.com/wcResults.do";
        List<BasicNameValuePair> urlParameters = new ArrayList<BasicNameValuePair>();
        urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
        urlParameters.add(new BasicNameValuePair("cn", ""));
        urlParameters.add(new BasicNameValuePair("locale", ""));
        urlParameters.add(new BasicNameValuePair("caller", ""));
        urlParameters.add(new BasicNameValuePair("num", "12345"));
        executeRequest("post", url, urlParameters);
    }

    @Before
    public void setUp() {
        logger.info("++++++++++++++++++++++++++++Running  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "++++++++++++++++++++++");
        System.out.println("Before setup");

    }

}


*/
