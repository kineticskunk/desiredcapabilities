package com.kineticskunk.auto.firefox;

import java.util.Iterator;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.json.simple.JSONObject;
import org.openqa.selenium.firefox.FirefoxProfile;
import com.kineticskunk.auto.utilities.ConfigurationLoader;
import com.kineticskunk.utilities.Converter;

public class LoadFireFoxProfile extends ConfigurationLoader {

	private static final String JSONFILE = "firefoxprofilepreferences.json";
	private static final String JSONKEY = "firefoxprofile";

	private final Logger logger = LogManager.getLogger(LoadFireFoxProfile.class.getName());
	private final Marker LOADFIREFOXPROFILE = MarkerManager.getMarker("LOADFIREFOXPROFILE");
	
	private FirefoxProfile profile = new FirefoxProfile();
	
	public LoadFireFoxProfile() {
		super(JSONFILE);
		this.setFirefoxProfile(this.getConfiguration(), JSONKEY);
	}
	
	private void setFirefoxProfile(JSONObject jsonObj, String jsonKey) {
		if (this.jsonKeyExists(jsonObj, jsonKey)) {
			this.logger.info(LOADFIREFOXPROFILE, "Loading " + (char)34 + jsonKey + (char)34 + " desired capabilities");
			JSONObject jsonKeyObj = (JSONObject) jsonObj.get(jsonKey);
			Iterator<?> iterator = jsonKeyObj.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				if (Converter.isBoolean(value)) {
					this.profile.setPreference(key, Converter.toBoolean(value));
				} else if (Converter.isNumeric(value)) {
					this.profile.setPreference(key, Converter.toInteger(value));
				} else {
					this.profile.setPreference(key, value);
				}
				this.logger.info(LOADFIREFOXPROFILE, "Loaded desiredCapability " + (char)34 + key + (char)34 + " with value " + (char)34 + value + (char)34);
			}
		} else {
			this.logger.info(LOADFIREFOXPROFILE, "JSON Key " + (char)34 + jsonKey + (char)34 + " does not exist in " + (char)34 + jsonObj.toJSONString() + (char)34);
		}
	}
	
	public FirefoxProfile getFirefoxProfile() {
		return this.profile;
	}
	
}