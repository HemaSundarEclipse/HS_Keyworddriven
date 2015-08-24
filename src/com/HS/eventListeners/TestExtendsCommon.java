package com.HS.eventListeners;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.Test;

import com.utility.Configuration;

import bsh.commands.dir;

public class TestExtendsCommon {

	@Test
	public void sample() throws InterruptedException {
		
		Configuration.getInstance().eDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//		eDriver.get("http://google.com");
		Configuration.getInstance().eDriver.navigate().to("http://gmail.com/");
//		Configuration.getInstance().eDriver.findElement(By.id("gbqfq")).sendKeys("Selenium");
//		Configuration.getInstance().eDriver.findElement(By.id("gbqfba")).click();
		
	}
}
