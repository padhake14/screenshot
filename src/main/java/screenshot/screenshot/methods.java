package screenshot.screenshot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class methods {
	WebDriver driver;
	/* xpaths */

	public void xpath(String retailer) throws InterruptedException, IOException {

		try {
			// Create FileInputStream Object
			FileInputStream fileInput = new FileInputStream(new File("./properties/config1.properties"));
			// Create Properties object
			Properties prop = new Properties();
			// load properties file
			prop.load(fileInput);

			Enumeration enuKeys = prop.keys();
			while (enuKeys.hasMoreElements()) {

				String key2 = (String) enuKeys.nextElement();
				String value = prop.getProperty(key2);
				System.out.println(key2 + ": " + value);
				System.out.println(retailer + ":" + key2);
				if (retailer.equals(key2)) {
					driver.findElement(By.xpath(value)).click();
					Thread.sleep(5000);

				}

			}

			// driver.get("https://www.macys.com/");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// try {
		// // Create FileInputStream Object
		// FileInputStream fileInput1 = new FileInputStream(new
		// File("./properties/config1.properties"));
		// // Create Properties object
		// Properties prop1 = new Properties();
		// // load properties file
		// prop1.load(fileInput1);
		// for(String xpath : prop1.stringPropertyNames()) {
		// String value1 = prop1.getProperty(xpath);
		// String[] array1 = value1.split(",");
		// for(String subString1 : array1){
		// System.out.println(subString1);
		// // Moda operandi
		// driver.findElement(By.xpath(subString1)).click();
		//
		// Thread.sleep(5000);
		//
		// }
		//
		// }
		// // driver.get("https://www.macys.com/");
		// Thread.sleep(5000);
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		//
	}

	/* capture image */

	public void image(String retailerr) throws IOException, InterruptedException, SQLException {
		try {
			// Create FileInputStream Object
			FileInputStream fileInput = new FileInputStream(new File("./properties/config.properties"));
			// Create Properties object
			Properties prop = new Properties();
			// load properties file
			prop.load(fileInput);

			Enumeration enuKeys = prop.keys();
			while (enuKeys.hasMoreElements()) {

				String key2 = (String) enuKeys.nextElement();
				String value = prop.getProperty(key2);
				System.out.println(key2 + ": " + value);
				if (retailerr.equals(key2)) {
					try {
						Date datee = new Date();
						SimpleDateFormat dateFormatt = new SimpleDateFormat("yyyy-MM-dd HH-mm");
						TakesScreenshot ts = (TakesScreenshot) driver;
						File source = ts.getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(source, new File(
								"/var/www/html/screen/images/" + dateFormatt.format(datee) + " : " + key2 + ".png"));

						// sql connection
						java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost/test", "root",
								"root");

						Statement st = con.createStatement();
						// File imgfile = new File("./screenshot/" +
						// dateFormatt.format(datee) +" : "+ key2+ ".png");

						// FileInputStream fin = new FileInputStream(imgfile);

						PreparedStatement pre = con
								.prepareStatement("INSERT INTO image_dataa ( date, name,image) VALUES ( ?, ?, ?)");

						pre.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
						pre.setString(2, key2);
						pre.setString(3, dateFormatt.format(datee) + " : " + key2 + ".png");
						// pre.setBinaryStream(3,(InputStream)fin,(int)imgfile.length());
						pre.executeUpdate();
						System.out.println("Successfully inserted the file into the database!");

						pre.close();
						con.close();

					} catch (IOException e) {
						System.err.println("Problem while taking  screenshot");
					}
				}

			}

			// driver.get("https://www.macys.com/");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/* save source code */

	public void file(String retailerrr) throws IOException {
		try {
			// Create FileInputStream Object
			FileInputStream fileInput = new FileInputStream(new File("./properties/config.properties"));
			// Create Properties object
			Properties prop = new Properties();
			// load properties file
			prop.load(fileInput);

			Enumeration enuKeys = prop.keys();
			while (enuKeys.hasMoreElements()) {

				String key2 = (String) enuKeys.nextElement();
				String value = prop.getProperty(key2);
				System.out.println(key2 + ": " + value);
				if (retailerrr.equals(key2)) {
					try {
						Date date = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
						File statText = new File("./source/" + dateFormat.format(date) + " : " + key2 + ".txt");
						BufferedWriter out = new BufferedWriter(new FileWriter(statText));
						out.write(driver.getPageSource());
						out.close();

					} catch (IOException e) {
						System.err.println("Problem while writing to the file");
					}
				}

			}

			// driver.get("https://www.macys.com/");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
