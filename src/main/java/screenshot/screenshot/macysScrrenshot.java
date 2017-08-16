package screenshot.screenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class macysScrrenshot extends methods {

	@BeforeTest
	public void openBrowser() throws InterruptedException, IOException {

		// OPEN chrome
		System.setProperty("webdriver.chrome.driver", "/home/parag/Music/chromedriver");
		driver = new ChromeDriver();
		// driver = new FirefoxDriver();
		driver.manage().window().maximize();

		/*
		 * String key="https://www.modaoperandi.com/,https://www.macys.com/"; //
		 * the String to be split String[] array = key.split(","); // split
		 * according to the hyphen and put them in an array for(String subString
		 * : array){ // cycle through the array System.out.println(subString);
		 * driver.get(subString); }
		 */

		// Dimension dimension = new Dimension(500, 300);
		// driver.manage().window().setSize(dimension);
		// open application
		// driver.get("https://www.jcrew.com/in/");
	}

	@Test

	public void scrrenShot() throws InterruptedException, IOException, SQLException {

		try {
			// Create FileInputStream Object
			FileInputStream fileInput = new FileInputStream(new File("./properties/config.properties"));
			// Create Properties object
			Properties prop = new Properties();
			// load properties file
			prop.load(fileInput);

			Enumeration enuKeys = prop.keys();
			while (enuKeys.hasMoreElements()) {
				// for (Map.Entry<Object, Object> entry : prop.entrySet()) {
				// System.out.println(entry.getKey() + " = " +
				// entry.getValue());

				String key1 = (String) enuKeys.nextElement();
				String value = prop.getProperty(key1);
				System.out.println(key1 + ": " + value);
				driver.get(value);
				Thread.sleep(5000);
				xpath(key1);
				image(key1);
				file(key1);
			}
			// }
			try {
				java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");
				// System.out.println("connection sucess");
				String quiry = "SELECT * FROM student_table";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(quiry);
				while (rs.next()) {
					System.out.println("Name :" + rs.getString("s_class"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// driver.get("https://www.macys.com/");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Crew
		// driver.findElement(By.xpath(".//*[@id='global__header']/div[3]/div[1]/div/div[1]/div[6]/a[2]")).click();

		// driver.findElement(By.xpath("/html[@class=' js no-touch svg
		// no-firefox']/body[@class='NavAppHomePage']/div[@id='tinybox']/div[@id='tinycontent']/div[@id='modal']/div[@id='local']/div[@id='notshipLink']/a")).click();

		// capture screenshots

		// Generate file

	}

	@AfterTest
	public void quit() {
		driver.quit();
	}

}
