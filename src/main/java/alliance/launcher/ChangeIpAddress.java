package alliance.launcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Run this class to change the ip address and account info to access database
 * @author Andy
 *
 */
public class ChangeIpAddress {

	public static void main(String[] args) {
		String ipaddress = "192.168.37.1";
		String username = "andy";
		String password = "andy";
		String network_port = "18080";
		
		String[] filename = 
			{
				"src/main/java/META-INF/spring/all.properties"
			};
		Properties prop;
		InputStream is;
		try {
			//first file
			prop = new Properties();
			is = new FileInputStream(filename[0]);
			prop.load(is);
			prop.setProperty("eproctor_jdbc.url", "jdbc:mysql://"+ipaddress+"/eproctor_db");
			prop.setProperty("eproctor_jdbc.username", username);
			prop.setProperty("eproctor_jdbc.password", password);
			prop.setProperty("network.host", ipaddress);
			prop.setProperty("network.port", network_port);
			prop.setProperty("u_jdbc.url", "jdbc:mysql://"+ipaddress+"/university_db");
			prop.setProperty("u_jdbc.username", username);
			prop.setProperty("u_jdbc.password", password);
			prop.store(new FileOutputStream(filename[0]), null);
			is.close();
			System.out.println("Succeed");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		
	}

}
