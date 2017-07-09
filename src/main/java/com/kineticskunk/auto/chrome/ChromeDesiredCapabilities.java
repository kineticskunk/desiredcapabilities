package com.kineticskunk.auto.chrome;

import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.auto.desiredcapabilities.DesiredCapabilityException;
import com.kineticskunk.auto.utilities.ConfigurationLoader;

public class ChromeDesiredCapabilities extends ConfigurationLoader  {
	
	private static final String DESIREDCAPABILITIESJSON = "chromedesiredcapabilities.json";
	private static final String CHROMEOPTIONS = "chromeoptions";
	private static final String CHROMEPREFERENCES = "chromeperferences";

	private final Logger logger = LogManager.getLogger(ChromeDesiredCapabilities.class.getName());
	private final Marker CHROMEDESIREDCAPABILITIES = MarkerManager.getMarker("CHROMEDESIREDCAPABILITIES");
	
	private DesiredCapabilities dc = DesiredCapabilities.chrome();
	private ChromeOptions options = new ChromeOptions();
	
	public ChromeDesiredCapabilities() throws DesiredCapabilityException {
		super(DESIREDCAPABILITIESJSON);
		this.setChromeOptions(this.getConfiguration(), CHROMEOPTIONS);
		this.setChromePreferences(this.getConfiguration(), CHROMEPREFERENCES);
	}
	
	private void setChromeOptions(JSONObject jsonObj, String jsonKey) throws DesiredCapabilityException {
		try {
			if (this.jsonKeyExists(jsonObj, jsonKey)) {
				this.logger.info(CHROMEDESIREDCAPABILITIES, "Loading " + (char)34 + jsonKey + (char)34);
				JSONArray optionsArray = (JSONArray) jsonObj.get(jsonKey);
				this.logger.info(CHROMEDESIREDCAPABILITIES, "Loading chrome arguments " + (char)34 + optionsArray.toJSONString() + (char)34);
				Iterator<?> iterator = optionsArray.iterator();
				while (iterator.hasNext()) {
					String argument = String.valueOf(iterator.next());
					this.options.addArguments(argument);
					this.logger.info(CHROMEDESIREDCAPABILITIES, "Loaded chrome argument " + (char)34 + argument + (char)34);
				}
			} else {
				this.logger.info(CHROMEDESIREDCAPABILITIES, "JSON Key " + (char)34 + jsonKey + (char)34 + " does not exist in " + (char)34 + jsonObj.toJSONString() + (char)34);
			}
			this.dc.setCapability(ChromeOptions.CAPABILITY, this.options);
		} catch (Exception ex) {
			
		}
	}
	
	private void setChromePreferences(JSONObject jsonObj, String jsonKey) {
		if (this.jsonKeyExists(jsonObj, jsonKey)) {
			this.logger.info(CHROMEDESIREDCAPABILITIES, "Loading " + (char)34 + jsonKey + (char)34 + " desired capabilities");
			JSONObject jsonKeyObj = (JSONObject) jsonObj.get(jsonKey);
			this.dc.setCapability("chrome.prefs", this.convertJSONtoMap(jsonKeyObj));
			this.logger.info(CHROMEDESIREDCAPABILITIES, "Loaded Chome Preferences " + (char)34 + jsonKeyObj.toJSONString() + (char)34);
		} else {
			this.logger.info(CHROMEDESIREDCAPABILITIES, "JSON Key " + (char)34 + jsonKey + (char)34 + " does not exist in " + (char)34 + jsonObj.toJSONString() + (char)34);
		}
	}
	
	public DesiredCapabilities getDesiredCapabilities() {
		return this.dc;
	}

}
