package com.kineticskunk.auto.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.collect.ImmutableMap;
import com.kineticskunk.utilities.Converter;

public class ConfigurationLoader {
	
	private final Logger logger = LogManager.getLogger(ConfigurationLoader.class.getName());
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
	
	public boolean jsonKeyExists(JSONObject jsonObject, String jsonKey) {
		if (jsonObject.containsKey(jsonKey)) {
			return true; 
		} else {
			this.logger.error(CONFIGURATIONLOADER, "JSONObject " + (char)34 + jsonObject.toJSONString() + (char)34 + " object doesn't contain key " + (char)34 + jsonKey + (char)34);
		}
		return false;
	}
	
	public HashMap<String, String> convertJSONtoMap(JSONObject jsonObject) {
		HashMap<String, String> map = new HashMap<String, String>();
		Iterator<?> iterator = jsonObject.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			map.put(key, value);
		}
		return map;
	}
	
	public JSONObject getConfiguration() {
		return this.jsonObject;
	}

}
