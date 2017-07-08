package com.kineticskunk.auto.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.kineticskunk.auto.desiredcapabilities.LoadDesiredCapabilities;

public class ConfigurationLoader {
	
	private final Logger logger = LogManager.getLogger(LoadDesiredCapabilities.class.getName());
	private final Marker CONFIGURATIONLOADER = MarkerManager.getMarker("CONFIGURATIONLOADER");
	
	private JSONParser parser = new JSONParser();
	private JSONObject jsonObject = null;
	
	public ConfigurationLoader(String json){
		this.setJSONObject(json);
	}
	
	public void setJSONObject(String json) {
		try {
			this.jsonObject = (JSONObject) this.parser.parse(new FileReader(new File(this.getClass().getClassLoader().getResource(json).getPath())));
		} catch (FileNotFoundException e) {
			this.logger.error(CONFIGURATIONLOADER, "Resources file " + (char)34 + json + (char)34 + " does not exist.");
		} catch (IOException e) {
			this.logger.error(CONFIGURATIONLOADER, "Resources file " + (char)34 + json + (char)34 + " caused an IO error.");
		} catch (ParseException e) {
			this.logger.error(CONFIGURATIONLOADER, "Resources file " + (char)34 + json + (char)34 + " could not be parsed as a JSON file.");
		} catch (Exception e) {
			this.logger.error(CONFIGURATIONLOADER, "Resources file " + (char)34 + json + (char)34 + " could not be parsed as a JSON file.");
		}
	}
	
	public JSONObject getConfiguration() {
		return this.jsonObject;
	}

}
