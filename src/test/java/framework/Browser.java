package framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.nio.file.FileSystems;

public class Browser {

    private static WebDriver driver;
    private final static String BROWSER = PropertyReader.getProperty("Browser");
    private final static String OPERATING_SYSTEM = "Windows";
    private final static String CHROME_PROPERTY = "webdriver.chrome.driver";
    private final static String FIREFOX_PROPERTY = "webdriver.gecko.driver";
    private final static String CHROME_DRIVER_PATH = "src/test/java/resources/drivers/%s/chromedriver%s";
    private final static String FIREFOX_DRIVER_PATH = "src/test/java/resources/drivers/%s/geckodriver%s";
    private final static String WINDOWS_DRIVER_EXT = ".exe";
    private final static String LINUX_DRIVER_EXT = "";
    private static String folderName;
    private static String formatDriverPath;
    private static String absoluteDriverPath;

    private Browser() {

    }

    /**
     * The driver is selected, based on the operating system type.
     *
     */
    public static WebDriver getDriver() {

        folderName = OPERATING_SYSTEM.toLowerCase();

        if (driver == null) {
            switch (OPERATING_SYSTEM) {
                case "Windows":
                    switchBrowser(WINDOWS_DRIVER_EXT);
                    break;
                case "Linux":
                    switchBrowser(LINUX_DRIVER_EXT);
                    break;
            }
        }
        return driver;
    }

    /**
     * The driver is selected, based on the browser type.
     *
     */
   private static WebDriver switchBrowser(String extension){

       switch (BROWSER) {
           case "Chrome":
               getSystemProperty(CHROME_DRIVER_PATH,extension,CHROME_PROPERTY);
               driver = new ChromeDriver();
               break;
           case "Firefox":
               getSystemProperty(FIREFOX_DRIVER_PATH,extension,FIREFOX_PROPERTY);
               driver = new FirefoxDriver();
               break;
       }
       return driver;
   }

    /**
     * path formatting
     * get absolute path
     * return system property for browser
     */
   private static String getSystemProperty(String driverPath, String driverExtension,
                                           String property){

       formatDriverPath = String.format(driverPath, folderName,
               driverExtension);
       absoluteDriverPath = FileSystems.getDefault().getPath(formatDriverPath)
               .normalize().toAbsolutePath().toString();
       return System.setProperty(property, absoluteDriverPath);
   }

}
