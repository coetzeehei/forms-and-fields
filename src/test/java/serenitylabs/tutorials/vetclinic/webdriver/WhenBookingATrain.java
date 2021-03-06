package serenitylabs.tutorials.vetclinic.webdriver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

public class WhenBookingATrain {
    WebDriver driver;

    @Before
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("http://www.sydneytrains.info/");
    }

    @Test
    public void should_be_able_to_plan_a_trip() {
        driver.findElement(By.id("display_origin")).sendKeys("Town Hall");
        driver.findElement(By.id("display_destination")).sendKeys("Parramatta");

        driver.findElement(By.id("itdTripDateTimeArr")).click();

        new Select(driver.findElement(By.cssSelector("#itdDate"))).selectByIndex(1);

        Select hour = new Select(driver.findElement(By.cssSelector("#itdTimeHour")));
        hour.selectByVisibleText("10");

        Select minute = new Select(driver.findElement(By.cssSelector("#itdTimeMinute")));
        minute.selectByVisibleText("15");

        driver.findElement(By.name("btnTripPlannerSubmit")).click();

        List<WebElement> tripOptions = driver.findElements(By.cssSelector(".journeyValue"));

        String arriveOrDepart = new Select(driver.findElement(By.id("SelectArriveDepart"))).getFirstSelectedOption().getText();
        String displayedOrigin = driver.findElement(By.id("name_origin")).getAttribute("value");
        String displayedDestination = driver.findElement(By.id("name_destination")).getAttribute("value");
        String arrivalDay = new Select(driver.findElement(By.id("itdDateDayMonthYear"))).getFirstSelectedOption().getText();
        String arrivalHour = new Select(driver.findElement(By.id("selectHour"))).getFirstSelectedOption().getText();
        String arrivalMinute = new Select(driver.findElement(By.id("selectMinute"))).getFirstSelectedOption().getText();

        assertThat(tripOptions.size(), is(greaterThan(0)));

        assertThat(arriveOrDepart, equalTo("arrive before"));
        assertThat(displayedOrigin, containsString("Town Hall"));
        assertThat(displayedDestination, containsString("Parramatta"));
        assertThat(arriveOrDepart, equalTo("arrive before"));
        assertThat(arrivalDay, containsString("Tomorrow"));
        assertThat(arrivalHour, equalTo("10"));
        assertThat(arrivalMinute, equalTo("15"));
    }


    @After
    public void shutdown() {
        driver.quit();
    }
}
