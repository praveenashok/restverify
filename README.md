# restverify

## Brief:
This is the rest API test framework which reads 2 files, where each file will have set of APIs stored line by line, the framework will read both file contents first & stores in memory(lists), the program will traverse through the stored lists to execute each set of APIs available from the lists & verifies the json responses between them.

# Tools used:

### Maven:
to compile,build,test the suite  

### Rest Assured:
to hit the API & get the JSON response

### Testng:
To use Annotations to create test suite

### SLF4J:
To log the errors/debug/info to consoleAppender & fileAppender

### Extent:
The best ever reporting tool with GUI

## Note:
* We are using jdk11 for this project
* The program is capable of reading >1000 APIs from files & to execute > 1000 APIs verification
* In our case, we have 2 files named file1 & file2, both have same set of 100 APIs stored, to test for negative, please change any API in file1 or file2 & re execute to see the failure report for that TC in extent report.
* The program supports to take API URL as "/api/users/4" instead of "http://reqres.in/api/users/4" in file, the program will attach the domain name and execute that API.

## How to run the project:
git clone git@github.com:praveenashok/restverify.git

cd restverify

mvn clean

mvn compile

mvn test -Dsuitefile=testng.xml

Which will start execution of the suite, when its executing we can read the console content displays the results for each API:

------------------------------------------------------------------------------------

16 Mar 2020 10:19:44  INFO AppTest - Testing for 1 TC https://reqres.in/api/users/5 VS https://reqres.in/api/users/6

16 Mar 2020 10:19:45  INFO AppTest - https://reqres.in/api/users/5 *NOT EQUALS* https://reqres.in/api/users/6

16 Mar 2020 10:19:45  INFO AppTest - Testing for 2 TC https://reqres.in/api/users/6 VS https://reqres.in/api/users/6

16 Mar 2020 10:19:45  INFO AppTest - https://reqres.in/api/users/6 *EQUALS* https://reqres.in/api/users/6:

------------------------------------------------------------------------------------

After above completion of the execution we can read the extent report which will be placed at:

restverify/test-output/extentReport.html

Open above file in any browser to see GUI level test report which will show each API level test output.
