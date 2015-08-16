

import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

import org.cyberneko.html.HTMLElements.Element;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import jxl.Sheet;
import jxl.Workbook;

public class imdbGenre {
  Sheet s;	
  WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
 

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://www.imdb.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testIMDBGenre() throws Exception {
	  
	driver.get(baseUrl + "chart/top");
    FileInputStream fi = new FileInputStream("resources/Genres.xls"); 
    Workbook w = Workbook.getWorkbook(fi); 
    s = w.getSheet(0); 
    
    for(int row=1; row < s.getRows();row++) { 
    	String genre = s.getCell(0, row).getContents(); 
    	
	    try {
	      assertEquals(genre, driver.findElement(By.linkText(genre)).getText());
	      System.out.println("Passed: "+ genre + " Link Found");
	    } catch (Error e) {
	    	System.out.println("Failed: "+ genre + " Link Not Found");
	      verificationErrors.append(e.toString());
	    }
	    driver.findElement(By.linkText(genre)).click();
	    System.out.println("Selected Genre "+genre); 
	    try {
	      assertEquals("Genre: "+genre, driver.findElement(By.cssSelector("h1")).getText());
	      System.out.println("Passed: "+ genre + " Page Found");
	    } catch (Error e) {
	    	System.out.println("Failed: "+ genre + " Page Not Found");
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertTrue(isElementPresent(By.cssSelector("td.title")));
	      WebElement elem = driver.findElement(By.xpath("//*[@id='main']/div/table/tbody/tr[1]/td[3]/a"));
	      String title = elem.getText();
	      System.out.println("Passed: Top " + genre + " title is " + title + "\n");
	    } catch (Error e) {
	      System.out.println("Failed: No Titles Found");
	      verificationErrors.append(e.toString());
	    }
	    driver.navigate().to(baseUrl +"chart/top");

    }
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
