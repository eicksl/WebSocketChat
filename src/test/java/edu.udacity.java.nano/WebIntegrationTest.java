package edu.udacity.java.nano;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {WebSocketChatApplicationTest.class})
public class WebIntegrationTest {

    private static final String ROOT_URL = "http://localhost:3000";
    private static final String GECKO_DRIVER_FILENAME = "geckodriver";
    private static WebDriver driver;
    private static String username;

    static {
        String geckoDriverPath = System.getProperty("user.dir") + "/" + GECKO_DRIVER_FILENAME;
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        username = "SeleniumUser";
        //ROOT_URL = "http://localhost:3000";
    }

    @AfterClass
    public static void tearDown() { driver.quit(); }

    @Test
    public void testAll() {
        testLogin();
        testUserJoin();
        testUserChat();
        testUserLeave();
    }

    private void testLogin() {
        String expectedUrl = ROOT_URL + "/chat/" + username;
        driver.get(ROOT_URL);
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.className("submit")).click();
        Assert.assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    private void testUserJoin() {
        if (driver.findElements(By.className("mdui-card")).size() == 0) {
            Assert.fail("The mdui-card element indicating that the user has joined is not present");
        }
    }

    private void testUserChat() {
        int oldMsgSize = driver.findElements(By.className("mdui-card")).size();
        driver.findElement(By.id("msg")).sendKeys("this is a test message");
        driver.findElement(By.id("send-msg")).click();
        int newMsgSize = driver.findElements(By.className("mdui-card")).size();
        Assert.assertEquals(oldMsgSize + 1, newMsgSize);
    }

    private void testUserLeave() {
        int oldMsgSize = driver.findElements(By.className("mdui-card")).size();
        String username2 = "SeleniumUser2";
        WebDriver driver2 = new FirefoxDriver();
        driver2.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver2.get(ROOT_URL + "/chat/" + username2);
        driver2.quit();
        int newMsgSize = driver.findElements(By.className("mdui-card")).size();
        Assert.assertEquals(oldMsgSize + 2, newMsgSize);
    }

}
