/*
 * (C) Copyright 2017-2019 ElasTest (http://elastest.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.elastest.demo.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class WebAppTest {

	private WebDriver driver;
	private static String eusApiURL;
	private static String appURL;

	@BeforeAll
	public static void setupClass() {
		eusApiURL = System.getenv("ET_EUS_API");
		if (eusApiURL == null) {
			ChromeDriverManager.getInstance().setup();
		}
		
		appURL = System.getenv("APP_IP");
		if(appURL == null) {
			appURL = "http://localhost:8080/";
		}
	}

	@BeforeEach
	public void setupTest() throws MalformedURLException {
		if (eusApiURL == null) {
			driver = new ChromeDriver();
		} else {
			driver = new RemoteWebDriver(new URL(eusApiURL), DesiredCapabilities.chrome());
		}
	}

	@AfterEach
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void test() throws InterruptedException {
		driver.get(appURL);

		Thread.sleep(3000);
		
		String newTitle = "MessageTitle";
		String newBody = "MessageBody";
		
		driver.findElement(By.id("title-input")).sendKeys(newTitle);
		driver.findElement(By.id("body-input")).sendKeys(newBody);
		
		Thread.sleep(3000);
		
		driver.findElement(By.id("submit")).click();
		
		Thread.sleep(3000);
		
		String title = driver.findElement(By.id("title")).getText();
		String body = driver.findElement(By.id("body")).getText();
		
		assertThat(title).isEqualTo(newTitle);
		assertThat(body).isEqualTo(newBody);
		
		Thread.sleep(3000);
	}

}