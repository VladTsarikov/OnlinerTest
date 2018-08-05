package onliner.forms;

import framework.CsvCreator;
import framework.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MainPage {

    private WebDriver driver;
    private By btnSignIn = By.xpath("//div[@id='userbar']//div[text()='Вход ']");
    private By mappingImage = By.xpath("//div[@class='b-top-profile__item b-top-profile__item_arrow']");
    private By mappingImageText = By.xpath("//a[contains(text(),'Выйти')]");
    private By mappingLogo = By.xpath("//img[@class=\"onliner_logo\"]");
    private String regularExspression = "<div class=\"b-opinions-main-2__text\">(.*)<\\/div>";
    private By productList = By.xpath("//span[@class='project-navigation__sign']");
    private static final int FLUENT_WAIT = Integer.parseInt(PropertyReader.getProperty("Wait"));
    private static final int POLLING_FLUENT_WAIT = Integer.parseInt(PropertyReader.getProperty("Wait"));
    private final static int EXPLICIT_WAIT = Integer.parseInt(PropertyReader.getProperty("Wait"));
    private static String nameRandomProductPage = "";


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public SignInPage clickSignIn(){

        Actions action = new Actions(driver);
        action.moveToElement( driver.findElement(btnSignIn)).click().build().perform();
        return  new SignInPage(driver);

    }

    public ProductPage goToRandomProductPage(){

        List<WebElement> webList = driver.findElements(productList);
        int randomValue = new Random().nextInt(webList.size());
        WebElement randomProduct = webList.get(randomValue);

        Coordinates coordinate = ((Locatable)randomProduct).getCoordinates();
        coordinate.onPage();
        coordinate.inViewPort();

        nameRandomProductPage = randomProduct.getText();
        randomProduct.click();
        fluentWait();

        return new ProductPage(driver);

    }


    public By getMappingLogo(){
        return mappingLogo;
    }


    public void findAllOppinionsTitles(){

        String pageSource = driver.getPageSource();

        Pattern pattern = Pattern.compile(regularExspression);
        Matcher matcher = pattern.matcher(pageSource);
        List<String> opinions = new ArrayList<>();

        while(matcher.find()) {
            opinions.add(matcher.group(1));
        }

       CsvCreator.createFile(opinions);

    }

    public MainPage logOutButton(){

        driver.findElement(mappingImage).click();
        driver.findElement(mappingImageText).click();
        return new MainPage(driver);

    }

    public WebElement fluentWait(){

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(FLUENT_WAIT, SECONDS)
                .pollingEvery(POLLING_FLUENT_WAIT, SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement waitElement = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
            return driver.findElement(mappingLogo);
        }
        });
        return waitElement;
    }

    private WebElement explicitWait(By waitElement){
        WebDriverWait dynamicElement = (new WebDriverWait(driver, EXPLICIT_WAIT));
        return dynamicElement.until(ExpectedConditions.presenceOfElementLocated(waitElement));
    }

    public static String getNameRandomProductPage() {
        return nameRandomProductPage;
    }

}
