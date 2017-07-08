package com.kineticskunk.auto.desiredcapabilities;

/*
	Copyright [2016 - 2017] [KineticSkunk Information Technology Solutions]

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

import java.util.Iterator;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.json.simple.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kineticskunk.auto.utilities.ConfigurationLoader;
import com.kineticskunk.utilities.Converter;

public class LoadDesiredCapabilities extends ConfigurationLoader {
	
	private static final String DESIREDCAPABILITIESJSON = "desiredcapabilties.json";
	private static final String COMMONDESIREDCAPABILITIES = "commondesiredcapabilities";

	private final Logger logger = LogManager.getLogger(LoadDesiredCapabilities.class.getName());
	private final Marker LOADDESIREDCAPABILITIES = MarkerManager.getMarker("LOADDESIREDCAPABILITIES");
	
	private DesiredCapabilities dc = new DesiredCapabilities();
	
	public LoadDesiredCapabilities() {
		super(DESIREDCAPABILITIESJSON);
		this.setDesiredCapabilities(this.getConfiguration(), COMMONDESIREDCAPABILITIES);
	}

	public void setDesiredCapabilities(JSONObject jsonObj, String jsonKey) {
		if (this.jsonKeyExists(jsonObj, jsonKey)) {
			this.logger.info(LOADDESIREDCAPABILITIES, "Loading " + (char)34 + jsonKey + (char)34 + " desired capabilities");
			JSONObject jsonKeyObj = (JSONObject) jsonObj.get(jsonKey);
			Iterator<?> iterator = jsonKeyObj.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
				if (Converter.isBoolean(value)) {
					this.dc.setCapability(key, Boolean.valueOf(value));
				} else if (Converter.isNumeric(value)) {
					this.dc.setCapability(key, Integer.valueOf(value));
				} else {
					this.dc.setCapability(key, value);
				}
				this.logger.info(LOADDESIREDCAPABILITIES, "Loaded desiredCapability " + (char)34 + key + (char)34 + " with value " + (char)34 + this.dc.getCapability(key) + (char)34);
			}
		} else {
			this.logger.info(LOADDESIREDCAPABILITIES, "JSON Key " + (char)34 + jsonKey + (char)34 + " does not exist in " + (char)34 + jsonObj.toJSONString() + (char)34);
		}
	}
	
	public DesiredCapabilities getCommonDesiredCapabilties() {
		return this.dc;
	}

}