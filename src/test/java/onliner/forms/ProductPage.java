package onliner.forms;

import framework.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    private WebDriver driver;
    private By lnkMainPage = By.xpath("//img[@class=\"onliner_logo\"]");
    private By mappingText = By.xpath("//h1[@class='schema-header__title']");
    private By mappingLogo = By.xpath("//img[@class=\"onliner_logo\"]");
    private final static int EXPLICIT_WAIT = Integer.parseInt(PropertyReader.getProperty("Wait"));

    public ProductPage(WebDriver driver) {

        this.driver = driver;

    }

    public String obtainMappingText(){
        return driver.findElement(mappingText).getText();
    }

    public MainPage returnToMainPage(){

        driver.findElement(lnkMainPage).click();
        explicitWait(mappingLogo);
        return new MainPage(driver);

    }


    private WebElement explicitWait(By waitElement){
        WebDriverWait dynamicElement = (new WebDriverWait(driver, EXPLICIT_WAIT));
        return dynamicElement.until(ExpectedConditions.presenceOfElementLocated(waitElement));
    }

}
