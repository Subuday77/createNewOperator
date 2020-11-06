package com.createNewOperator.beans;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;


import org.openqa.selenium.support.ui.Select;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Selenium {

    WebDriver webDriver1;

    public ResponseEntity<?> login(String name, String password) {
        try {
            WebDriver webDriver = driverSetup(webDriver1);
            webDriver1 = webDriver;
            if (webDriver == null) {
                webDriver.quit();
                return new ResponseEntity<>("Window is closed", HttpStatus.GONE);

            }

            webDriver.get("https://boint.tableslive.com/");

            webDriver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys(name);
            webDriver.findElement(By.xpath("//*[@id=\"login_tab\"]/tbody/tr[4]/td[2]/input")).sendKeys(password);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                webDriver.findElement(By.xpath("//*[@id=\"login_tab\"]/tbody/tr[6]/td/input")).click();
            } catch (NoSuchWindowException e) {
                webDriver = null;
                webDriver1 = null;
                webDriver.quit();
                return new ResponseEntity<>("Window is closed1", HttpStatus.GONE);
            }
            if (webDriver.getCurrentUrl().equals("https://boint.tableslive.com/office.php?page=login")) {

                webDriver.quit();
                webDriver1 = null;
                return new ResponseEntity<>("Invalid user or password. Try again.", HttpStatus.FORBIDDEN);


            } else {
                return new ResponseEntity<>("OK", HttpStatus.OK);
            }
        } catch (WebDriverException e) {
            if (e.getMessage().contains("no such element")) {
                return new ResponseEntity<>("Already opened", HttpStatus.OK);
            } else if (!e.getMessage().contains("chrome not reachable")) {
                webDriver1 = null;
                System.out.println(e.getMessage());
                return new ResponseEntity<>("Window is closed2", HttpStatus.GONE);
            }
            webDriver1 = null;
            ResponseEntity responseEntity = login(name, password);
            return responseEntity;
        }
    }

    public ResponseEntity<?> createOperator(long operatorId, String operatorName) {
        WebDriver webDriver = webDriver1;

        webDriver.get("https://boint.tableslive.com/office.php?action=admin&sub_act=operator_page");
        webDriver.findElement(By.xpath("//*[@id=\"center\"]/form/table/tbody/tr[1]/td[1]/a/strong")).click();
        webDriver.findElement(By.xpath("//*[@id=\"CustomID\"]")).sendKeys(String.valueOf(operatorId));
        webDriver.findElement(By.xpath("//*[@id=\"center\"]/form/table/tbody/tr[2]/td[2]/input")).sendKeys(operatorName);
        webDriver.findElement(By.xpath("//*[@id=\"center\"]/form/table/tbody/tr[3]/td[2]/input")).sendKeys(String.valueOf(1));
        webDriver.findElement(By.xpath("//*[@id=\"center\"]/form/table/tbody/tr[4]/td[2]/input")).sendKeys(String.valueOf(1));
        webDriver.findElement(By.xpath("//*[@id=\"center\"]/form/table/tbody/tr[5]/td[2]/input")).sendKeys(String.valueOf(1));
        webDriver.findElement(By.xpath("//*[@id=\"center\"]/form/table/tbody/tr[6]/td[2]/input")).sendKeys(String.valueOf(1));
        Select dropdown = new Select(webDriver.findElement(By.xpath("//*[@id=\"IsActive\"]")));
        dropdown.selectByVisibleText("Active");
        if (webDriver.findElement(By.xpath("//*[@id=\"CustomIDStatus\"]")).getText().contains("Taken By")) {
            return new ResponseEntity<>("Operator ID already taken", HttpStatus.BAD_REQUEST);
        }
        //webDriver.findElement(By.xpath("//*[@id=\"center\"]/form/table/tbody/tr[13]/td[2]/input")).click();
        webDriver.findElement(By.xpath("//*[@id=\"resize_menu\"]")).click();
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    private static WebDriver driverSetup(WebDriver webDriver1) {
        WebDriver webDriver;
        try {
            WebDriverManager.chromedriver().setup();
            if (webDriver1 == null) {
                webDriver = new ChromeDriver();
            } else {
                webDriver = webDriver1;
            }
            return webDriver;
        } catch (Exception e) {
            return null;
        }
    }
}

