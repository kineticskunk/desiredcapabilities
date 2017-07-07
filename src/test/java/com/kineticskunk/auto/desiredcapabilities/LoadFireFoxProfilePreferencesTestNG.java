package com.kineticskunk.auto.desiredcapabilities;

import org.testng.annotations.Test;

import com.kineticskunk.auto.firefox.LoadFireFoxProfilePreferences;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class LoadFireFoxProfilePreferencesTestNG {
	
	private static final String FIREFOXPROFILEPREFERENCES = "firefoxprofilepreferences";
	
	private LoadFireFoxProfilePreferences lfpp;
	private FirefoxProfile profile = new FirefoxProfile();
	private JSONParser parser = new JSONParser();
	
	private JSONObject desiredCapabilitiesJSONObject;
	private JSONObject fireFoxProfilePreferences;
	
	@BeforeClass
	@Parameters( { "browserType", "desiredCapabilitiesConfigJSON" } )
	public void beforeLoadFireFoxProfilePreferencesTestNG(String browserType, String desiredCapabilitiesConfigJSON) throws FileNotFoundException, IOException, ParseException {
		this.desiredCapabilitiesJSONObject = (JSONObject) this.parser.parse(new FileReader(new File(this.getClass().getClassLoader().getResource(desiredCapabilitiesConfigJSON).getPath())));
		this.fireFoxProfilePreferences = (JSONObject) this.desiredCapabilitiesJSONObject.get(FIREFOXPROFILEPREFERENCES);
		this.lfpp = new LoadFireFoxProfilePreferences(this.fireFoxProfilePreferences);
		this.lfpp.loadFireFoxExtensionsAndExtensionPreferences();
		this.profile = this.lfpp.getFirefoxProfile();
		System.out.println(this.profile.toString());
	}
	
	@Test(priority = 0)
	public void verifyLoadedExtenstions() {
		//Assert.assertTrue(this.profile.areNativeEventsEnabled());
	}

}
