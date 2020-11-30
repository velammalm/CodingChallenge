# CodingChallenge
Implemented the solution to the coding challenge using the Hybrid framework which is combination of TestNG with Page Object Model and test data driven Framework

# Pre-requisite:
1. Install Java and Eclipse
2. Install testng plugin to eclipse IDE <br />
Reference site for testng plugin intallation - https://www.guru99.com/install-testng-in-eclipse.html
3. Also ensure the driver software is updated under the 'drivers' folder under the root directory of the project based on the chrome browser version of your system <br />
Note: I used Chrome Browser Version 87.0.4280.66 (Official Build) (64-bit) and driver version ChromeDriver 87.0.4280.20
4. Ensure to import the project as Maven project in eclipse  <br />
File -->Import-->Maven-->Existing Maven Project
5. Ensure to change the Project compliance and JRE to 1.8

# Steps to execute the test script:
1. Find the testng.xml file and the following path in the project folder #src/main/resources -->testng.xml <br />
2.Rightclick on testng.xml and Run as-->TestNG Suite <br />
3.The Reports can be obtained from 'Reports' Folder

# Framework Description
i. com.lampenwelt.pages - The object repositories and the methods for each pages are implemented  <br />
ii. com.lampenwelt.testcases - The testscripts for each of the testcases are created and addressed using the testng attribute @Test  <br />
iii. com.lampenwelt.utils - It consists of the following  <br />
         &nbsp;&nbsp;&nbsp; 1. ExcelReader.java -Code for fetching data from excel has been implemented in this file  <br />
         &nbsp;&nbsp;&nbsp; 2. Reporter.java - Extent Report software has been used and the code for html report generation with date and time has been implemented  <br />
         &nbsp;&nbsp;&nbsp; 3. WebEventlistener.java -The webdriver actions are logged and the output is casted in 'Logger' Files  <br />
iv. com.lampenwelt.wrappers - It consists of the generic methods for executing driver actions using selenium  <br />

The other utilities implemented are as follows:  <br />
1. Configuration.properties - The project configurations such as the browser name and url can be mentioned  <br />
2. log4j.properties - The specifications for generating the log files after test execution are implemented here  <br />
3. testng.xml - The tests which should be run as a suite can be described here  <br />
4. Data - The Excel test data are specified under this 'Data' folder under the root directory  <br />
5. drivers - All the webdriver softwares are placed under this 'drivers' folder under the root directory  <br />
6. LoggerFiles - The test logs after execution are placed under this folder  <br />
7. Reports - The HTML reports after the test execution can be viewed under this folder  <br />
8. Screenshots - The Screenshots during test execution are placed here





