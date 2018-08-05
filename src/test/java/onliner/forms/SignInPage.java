package onliner.forms;

import framework.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInPage {

    private WebDriver driver;

    private By txbUserNameOrEmail = By.xpath("//div[@class='auth-box__line']//input[@type='text']");
    private By txtPassword = By.xpath("//input[@type='password']");
    private By btnSignIn = By.xpath("//button[@type='submit'][contains(text(),'Войти')]");
    private By mappingLogo = By.xpath("//img[@class=\"onliner_logo\"]");
    private final static int EXPLICIT_WAIT = Integer.parseInt(PropertyReader.getProperty("Wait"));


    public SignInPage(WebDriver driver) {
        this.driver = driver;
    }

    public MainPage sigInProcess(String userNameOrEmail, String password){

        System.out.println(driver.findElement(txbUserNameOrEmail).getAttribute("data-field"));

        driver.findElement(txbUserNameOrEmail).sendKeys(userNameOrEmail);
        driver.findElement(txtPassword).sendKeys(password);
        driver.findElement(btnSignIn).click();
        explicitWait(mappingLogo);
        return  new MainPage(driver);

    }

    private WebElement explicitWait(By waitElement){
        WebDriverWait dynamicElement = (new WebDriverWait(driver, EXPLICIT_WAIT));
        return dynamicElement.until(ExpectedConditions.presenceOfElementLocated(waitElement));
    }

}
