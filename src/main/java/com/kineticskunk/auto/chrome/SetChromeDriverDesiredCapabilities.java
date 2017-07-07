package com.kineticskunk.auto.chrome;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.auto.desiredcapabilities.DesiredCapabilityException;

public class SetChromeDriverDesiredCapabilities {

	private static final Logger logger = LogManager.getLogger(SetChromeDriverDesiredCapabilities.class.getName());
	private static final Marker CHROMEDESIREDCAPABILITIES = MarkerManager.getMarker("CHROMEDESIREDCAPABILITIES");

	private DesiredCapabilities dc;
	private ChromeDriver cd; 
	private ChromeDriverService cs;
	private HashMap<String, Object> params;

	public SetChromeDriverDesiredCapabilities(HashMap<String, Object> params) {
		this.dc = DesiredCapabilities.chrome();
		this.cd = null;
		this.cs = null;
		this.params = params;
	}

	public void configureDriver() throws Exception {
		this.setChromeService();
		this.setDesiredCapabilities();
		this.setDriver();
		this.startChromeService();
		this.cd.manage().window().maximize();
	}

	public ChromeDriver getDriver() {
		return cd;
	}

	private void startChromeService() throws IOException {
		this.cs.start();
	}

	public void stopChromeService() {
		this.cs.stop();
	}

	private void setChromeService() {
		this.cs = new ChromeDriverService.Builder().usingDriverExecutable(new File(this.params.get("chromeDriverExecutable").toString())).usingAnyFreePort().build();
	}

	public ChromeDriverService getChromeDriverService() {
		return cs;
	}

	/**
	 * Set FireFox DesiredCapabilities and profile
	 * @throws DesiredCapabilityException
	 */	
	public void setDesiredCapabilities() throws DesiredCapabilityException {
		try {
			for (String key : this.params.keySet()) {
				Object value = params.get(key);
				if (!value.equals(null) && !value.equals("") && !value.equals(Keys.SPACE)) {
					logger.log(Level.INFO, CHROMEDESIREDCAPABILITIES, "Capability type '" + key.toUpperCase() + "' has been set to '" + value + "'");
					if (key.equalsIgnoreCase("chromeOptions")) {
						this.dc.setCapability(ChromeOptions.CAPABILITY, (ChromeOptions) value);
					} else if (key.equalsIgnoreCase("chromePreferences")) {
						this.dc.setCapability("chrome.prefs", value);
					} else if (key.equalsIgnoreCase("platform")) {
						this.dc.setCapability(key.toUpperCase(), System.getProperty("os.name"));							
					} else {
						this.dc.setCapability(key.toUpperCase(), value);
					}
				} else {
					logger.log(Level.DEBUG, CHROMEDESIREDCAPABILITIES, "Capability type '" + key.toUpperCase() + "' has not being set as the value is either empty, null or equals " + (char)34 + (char)34);
				}
			}
		} catch (Exception ex) {
			
		}
	}

	public DesiredCapabilities getDesiredCapabilities() {
		return this.dc;
	}

	private void setDriver() throws Exception {
		this.cd = new ChromeDriver(this.dc);
	}
}
