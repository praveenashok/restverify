package com.rest.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AppTest{
    public static List<String> file1;
    public static List<String> file2;
    ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;
    public static String domain="reqres.in";
    private static final Logger LOG = LoggerFactory.getLogger(AppTest.class );
    private static SoftAssert softAssert = new SoftAssert();

    public static List<String> getfileasList(Path path){
	List<String> list=new ArrayList<String>();
	try {
        list = Files.readAllLines(path);
	}catch(Exception e) {
		e.printStackTrace();
	}
	return list;
	}

    @BeforeSuite
    public void startReport() {
    PropertyConfigurator.configure(System.getProperty("user.dir")+"/src/test/resources/log4j.xml");
    htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/extentReport.html");
    extent = new ExtentReports();
    extent.attachReporter(htmlReporter);
    }

    @Test(dataProvider ="lists")
    public void restapiresp(List<String> url1,List<String> url2) {
	Response resp1 = null,resp2 = null;
	for(int i=0;i<file1.size();i++) {
	try {
	LOG.info("Testing for "+(i+1)+" TC "+url1.get(i)+" VS "+url2.get(i));
	if(!url1.get(i).contains(domain))
    {
    resp1=(Response) RestAssured.given().
    when().
    get("https://"+domain+url1.get(i)).
    then().
    statusCode(200).
    extract().response();
    }
    if(!url2.get(i).contains(domain))
    {
    resp2=(Response) RestAssured.given().
    when().
    get("https://"+domain+url2.get(i)).
    then().
    statusCode(200).
    extract().response();	
    }
    if(url1.get(i).contains(domain))
	{
    resp1=(Response) RestAssured.given().
    when().
    get(url1.get(i)).
    then().
    statusCode(200).
    extract().response();
	}
    if(url2.get(i).contains(domain))
	{
    resp2=(Response) RestAssured.given().
    when().
    get(url2.get(i)).
    then().
    statusCode(200).
    extract().response();
	}
    }catch(Exception e) {
    	e.printStackTrace();
    }
	try{
    	Assert.assertTrue(resp1.asString().equals(resp2.asString()));
	    extent.createTest("TC "+(i+1)).pass(url1.get(i)+" equals "+url2.get(i));
	    LOG.info(url1.get(i)+" equals "+ url2.get(i));
    }catch (AssertionError e) {
    	e.printStackTrace();
    	extent.createTest("TC "+(i+1)).fail(url1.get(i)+" not equals "+url2.get(i));
    	LOG.info(url1.get(i)+" not equals "+url2.get(i));
    	softAssert.assertFalse(!resp1.asString().equals(resp2.asString()), "TC failed as both json not matching");
    	}
	continue;
	}	
}

    @AfterTest
    public static void endTest(){
    	extent.flush();
    }
    
    @DataProvider(name = "lists")
    public static Object[][] hashmap() {
    try {
    file1=AppTest.getfileasList(Paths.get(System.getProperty("user.dir")+"/src/test/java/resources/file1"));
    file2=AppTest.getfileasList(Paths.get(System.getProperty("user.dir")+"/src/test/java/resources/file2"));
    }catch(Exception e) {
    	e.printStackTrace();
    }
    return new Object[][] {{file1,file2}};
}
}