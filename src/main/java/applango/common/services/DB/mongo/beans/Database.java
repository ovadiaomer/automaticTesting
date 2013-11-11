package applango.common.services.DB.mongo.beans;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 07/11/13
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
public class Database {
    String url;
    int port;
    String dbName;
    String username;
    String password;

    public void database() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}
