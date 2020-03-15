package com.rest.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.*;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AppTest{
    public static List<String> file1;
    public static List<String> file2;
    ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    public static List<String> getfileasList(Path path){
	List<String> list=new ArrayList<String>();
	try {
        list = Files.readAllLines(path);
	}catch(Exception e) {
		e.printStackTrace();
	}
	return list;
	}

    @BeforeTest
    public void startReport() {
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/extentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

@Test(dataProvider = "list")
public static void restapiresp(List<String> url1,List<String> url2) {
	Response resp1 = null,resp2 = null;
	for(int i=0;i<url1.size();i++) {
	System.out.println("Testing for "+(i+1)+" TC "+url1.get(i)+" VS "+url2.get(i));
    if(!url1.get(i).contains("reqres.in"))
    {
    resp1=(Response) RestAssured.given().
    when().
    get("https://reqres.in"+url1.get(i).toString()).
    then().
    statusCode(200).
    extract().response();
    }
    if(!url2.get(i).contains("reqres.in"))
    {
    resp2=(Response) RestAssured.given().
    when().
    get("https://reqres.in"+url2.get(i).toString()).
    then().
    statusCode(200).
    extract().response();	
    }
    if(url1.get(i).contains("reqres.in"))
	{
    resp1=(Response) RestAssured.given().
    when().
    get(url1.get(i).toString()).
    then().
    statusCode(200).
    extract().response();
	}
    if(url2.get(i).contains("reqres.in"))
	{
    resp2=(Response) RestAssured.given().
    when().
    get(url2.get(i).toString()).
    then().
    statusCode(200).
    extract().response();
	}
    try{
	Assert.assertEquals(resp1.asString(),resp2.asString(),"\nTC Failed as both json not matching\n");
	extent.createTest("TC "+(i+1)).pass(url1.get(i)+" equals "+url2.get(i));
	} catch (AssertionError e) {
    	e.printStackTrace();
    	extent.createTest("TC "+(i+1)).fail(url1.get(i)+" not equals "+url2.get(i));
	}
	}
}

@AfterTest
public static void endTest(){
extent.flush();
}

@DataProvider(name = "list")
public static Object[][] hashmap() {
	file1=AppTest.getfileasList(Paths.get(System.getProperty("user.dir")+"/src/test/java/resources/file1"));
	file2=AppTest.getfileasList(Paths.get(System.getProperty("user.dir")+"/src/test/java/resources/file2"));
    return new Object[][] {{file1,file2}};
}

}