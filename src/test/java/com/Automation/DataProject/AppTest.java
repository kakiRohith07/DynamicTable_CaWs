package com.Automation.DataProject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


import java.util.List;

import static org.junit.Assert.assertEquals;

public class AppTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        try {
            
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\KAKIR\\Downloads\\chromedriver-win64\\chromedriver.exe");
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(90));
           
            driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set up WebDriver: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        try {
            
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to close WebDriver: " + e.getMessage());
        }
    }

    @Test
    public void testDynamicTableAutomation() {
        try {
        
            WebElement tableDataButton = driver.findElement(By.cssSelector("summary"));
            tableDataButton.click();
            
          
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
            WebElement inputTextArea = driver.findElement(By.id("jsondata"));
            JSONArray inputData = new JSONArray();
            inputData.put(createJsonObject("Bob", 20));
            inputData.put(createJsonObject("George", 42));
            inputData.put(createJsonObject("Sara", 42));
            inputData.put(createJsonObject("Conor", 40));
            inputData.put(createJsonObject("Jennifer", 42));

            
            inputTextArea.clear();
            inputTextArea.sendKeys(inputData.toString());

            WebElement refreshButton = driver.findElement(By.id("refreshtable"));
            refreshButton.click();

      
            WebElement firstRow = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id=\"dynamictable\"]/tr[2]")));
            wait.until(ExpectedConditions.visibilityOf(firstRow));
            List<WebElement> columns = firstRow.findElements(By.tagName("td"));


            JSONObject currentRow = inputData.getJSONObject(0);
            Thread.sleep(5000);
     
            assertEquals(currentRow.getString("name"), columns.get(0).getText());
            assertEquals(currentRow.getInt("age"), Integer.parseInt(columns.get(1).getText()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test execution failed: " + e.getMessage());
            
            
        }
    }

    private JSONObject createJsonObject(String name, int age) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("age", age);
        return jsonObject;
        
        
    }
}
