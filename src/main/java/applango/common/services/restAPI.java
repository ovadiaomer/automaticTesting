package applango.common.services;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 27/10/13
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
public class restAPI {
    @Test
    public void createHttpRequest() {
        HttpURLConnection connection = null;
        PrintWriter outWriter = null;
        BufferedReader serverResponse = null;
        StringBuffer buff = new StringBuffer();
        try
        {
//OPEN CONNECTION
            connection = (HttpURLConnection) new URL( "http://www.google.com/" ).openConnection();

//SET REQUEST INFO
            connection.setRequestMethod( "get" );
            connection.setDoOutput( true );

//CREATE A WRITER FOR OUTPUT
            outWriter = new PrintWriter( connection.getOutputStream() );

//PARAMETERS
//            buff.append( "?#q=" );
//            buff.append( URLEncoder.encode("Omer", "UTF-8") );
//            buff.append( "&" );
//            buff.append( "param2=" );
//            buff.append( URLEncoder.encode( "Param 2 Value", "UTF-8" ) );

//SEND PARAMETERS
            outWriter.println( buff.toString() );
            outWriter.flush();
            outWriter.close();

//RESPONSE STREAM
            serverResponse = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );

//READ THE RESPOSNE
            String line;
            while ( (line = serverResponse.readLine() ) != null )
            {
                System.out.println( line );
            }
        }
        catch (MalformedURLException mue)
        {
            mue.printStackTrace();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally
        {
            if ( connection != null )
                connection.disconnect();

            if ( serverResponse != null )
            {
                try { serverResponse.close(); } catch (Exception ex) {}
            }
        }
    }
}
