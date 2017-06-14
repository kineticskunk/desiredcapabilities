package com.kineticskunk.auto.desiredcapabilities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.openqa.selenium.firefox.FirefoxProfile;

public class LoadFireFoxProfile {

	private static final Logger logger = LogManager.getLogger(LoadFireFoxProfile.class.getName());
	private static final Marker FIREFOXPROFILE = MarkerManager.getMarker("FIREFOXPROFILE");

	private static final String DEFAULT_WIN_DOWNLOAD_DIRECTORY = System.getProperty("user.home") + "\\downloads\\";
	private static final String DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY = System.getProperty("user.home") + "/downloads/";

	private FirefoxProfile profile;
	private PlatformOperatingSystem pos;
	private HashMap<String, Object> params;
	
	public LoadFireFoxProfile() {
		this.profile = new FirefoxProfile();
		this.pos = new PlatformOperatingSystem();
		this.params = new HashMap<String, Object>();
	}
	
	public LoadFireFoxProfile(HashMap<String, Object> params) {
		this();
		this.params = params;
	}
	
	public LoadFireFoxProfile(HashMap<String, Object> params, FirefoxProfile profile) {
		this();
		this.params = params;
		this.profile = profile;
	}

	/**
	 * 
	 * @return
	 */
	public FirefoxProfile getFirefoxProfile() {
		return this.profile;
	}
	
	public void setPreferences(String profilePreferences) throws IOException {
		//this.params = this.ap.readPropertyFile(this.params, profilePreferences);	
	}
	
	public void setPreferences(HashMap<String, Object> preferences) throws IOException {
		this.params = preferences;	
	}
	
	
	public HashMap<String, Object> getPreferences() throws IOException {
		return params;	
	}
	
	public void addFireFoxExtension(String fireBugLocation, String fireBugName) throws IOException {
		this.profile.addExtension(new File(fireBugLocation, fireBugName));
	}
	
	public void addFireFoxExtension(String fireBugName) throws IOException {
		this.profile.addExtension(new File(fireBugName));
	}
	
	/**
	 * Set the FireFoxProfile preferences.
	 * @param params
	 */
	public void setFirefoxProfile() {
		try {
			Set<String> keys = this.params.keySet();
			for (String key : keys) {
				String value = this.params.get(key).toString();
				if (key.equalsIgnoreCase("fireBug")) {
					this.addFireFoxExtension(params.get("fireBugLocation").toString(), params.get("fireBugName").toString());
				}
				
				if (EnumUtils.isValidEnum(profileSetting.class, key.replace(".", "_"))) {
					logger.log(Level.INFO, FIREFOXPROFILE, "Preference name = '" + key + "'; Preferance value = '" + value + "'");
					if (key.equalsIgnoreCase("browser.download.dir")) {
						this.setBrowserDownloadLocation(value);
					} else if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("true")) {
						this.profile.setPreference(key, Boolean.parseBoolean(value));
					} else {
						this.profile.setPreference(key, value);
					}
				} else {
					logger.log(Level.DEBUG, "Preference name = '" + key + "' is invalid");
				}
			}
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}

	/**
	 * Set the FireFox download location.
	 * If the provided directory is invalid the download location will be set to <strong>System.getProperty("user.home") + \\downloads\\ </strong> for windows and to <strong>System.getProperty("user.home") + /downloads/</strong> for mac/linux
	 * 
	 * @param downloodLocation
	 */
	private void setBrowserDownloadLocation(String downloodLocation) {
		try {
			File downloadlocation = new File(downloodLocation);
			if (downloadlocation.isDirectory()) {
				this.profile.setPreference("browser.download.dir", downloodLocation);
			} else {
				if (this.pos.isWindows()) {
					logger.log(Level.WARN, FIREFOXPROFILE, "The provided download location '" + downloodLocation + "' is not a directory. Setting the download location to it's default value of '" + DEFAULT_WIN_DOWNLOAD_DIRECTORY + "'");
					this.profile.setPreference("browser.download.dir", DEFAULT_WIN_DOWNLOAD_DIRECTORY);
				} else if (this.pos.isMac() || this.pos.isSolaris() || this.pos.isUnix()) {
					logger.log(Level.WARN, FIREFOXPROFILE, "The provided download location '" + downloodLocation + "' is not a directory. Setting the download location to it's default value of '" + DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY + "'");
					this.profile.setPreference("browser.download.dir", DEFAULT_UNIX_BASED_DOWNLOAD_DIRECTORY);
				}
			}
		} catch (Exception ex) {
			logger.catching(ex);
		}
	}

	private enum profileSetting {
		accept_untrusted_certificates,
		always_load_no_focus_lib,
		assume_untrusted_certificate_issuer,
		browser_cache_disk_enable,
		browser_download_dir,
		browser_download_folderList,
		browser_download_manager_alertOnEXEOpen,
		browser_download_manager_closeWhenDone,
		browser_download_manager_focusWhenStarting,
		browser_download_manager_showAlertOnComplete,
		browser_download_manager_showWhenStarting,
		browser_download_manager_useWindow,
		browser_helperApps_alwaysAsk_force,
		browser_helperApps_neverAsk_openFile,
		browser_helperApps_neverAsk_saveToDisk,
		enable_native_events
	}
	
	static class FireFoxExtensions {
		
		private enum fireBugPreferences {
			extensions_firebug_currentVersion,
			extensions_firebug_console_enableSites,
			extensions_firebug_script_enableSites,
			extensions_firebug_defaultPanelName,
			extensions_firebug_net_enableSites,
			extensions_firebug_allPagesActivation,
			extensions_firebug_cookies_enableSites;
		}
		
	}
	
}