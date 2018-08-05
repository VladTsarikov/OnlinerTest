package onliner.tests;

import framework.Browser;
import framework.CsvCreator;
import framework.PropertyReader;
import onliner.forms.MainPage;
import onliner.forms.ProductPage;
import onliner.forms.SignInPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class OnlinerPageTest {

    private WebDriver driver;
    private MainPage mainPage;
    private ProductPage productPage;
    private SignInPage signInPage;
    private final static String URL = PropertyReader.getProperty("URL");
    private final static int IMPLICITLY_WAIT = Integer.parseInt(PropertyReader.getProperty("Wait"));


    @BeforeMethod
    public void setUp(){

        driver = Browser.getDriver();
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(URL);

        CsvCreator.isExist();

    }

    @Parameters({"email", "password"})
    @Test
    public void testOnlinerPage_shouldPerformAllSteps(String email, String password){

        mainPage = new MainPage(driver);

        //signInPage = mainPage.clickSignIn();
        //signInPage.sigInProcess(email,password);

        productPage = mainPage.goToRandomProductPage();
        assertEquals(productPage.obtainMappingText(),mainPage.getNameRandomProductPage());

        mainPage = productPage.returnToMainPage();
        mainPage.findAllOppinionsTitles();

        //mainPage = mainPage.logOutButton();

    }

    @AfterMethod
    public void tearDown(){

       driver.quit();

    }

}
