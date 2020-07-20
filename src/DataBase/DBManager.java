/*’≈”‡«‡ 2020-7-15*/
package DataBase;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
	public static final String TABLE_USERINFO = "USERINFO";
	public static final String TABLE_DRIVERINFO = "DRIVERINFO";
	public static final String TABLE_ORDERINFO = "ORDERINFO";
	public static final String TABLE_ITEM = "ITEMINFO";

    public static Connection getConnect() throws IOException, SQLException, URISyntaxException
    {
	    Properties prop=new Properties();
		//one
		String configFile = "database.properties";
		java.io.InputStream is = DBManager.class.getResourceAsStream("/"+configFile);
		
		prop.load(is);
	    String driver=prop.getProperty("driver");
	    if(driver!=null)
	    {
	            System.setProperty("jdbc.drivers", driver);
	    }
	    String url=prop.getProperty("url");
	    String username=prop.getProperty("username");
	    String password=prop.getProperty("password");
	    Connection con=DriverManager.getConnection(url, username, password);
	    return con;
    }
}

