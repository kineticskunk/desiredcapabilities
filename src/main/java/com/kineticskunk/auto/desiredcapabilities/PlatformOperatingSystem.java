/*
 	Copyright [2016] [KineticSkunk Information Technology Solutions]

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

package com.kineticskunk.auto.desiredcapabilities;

import org.openqa.selenium.Platform;

/**
 * 
 * @author yodaqua
 *
 */

public class PlatformOperatingSystem {

	protected static String OS;
	
	public PlatformOperatingSystem() {
		OS = System.getProperty("os.name").toLowerCase();
	}

	public boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}

	public boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

	public String getOS(){
		if (isWindows()) {
			return "win";
		} else if (isMac()) {
			return "osx";
		} else if (isUnix()) {
			return "uni";
		} else if (isSolaris()) {
			return "sol";
		} else {
			return "err";
		}
	}

	public Platform getHostPlatform() {
		String os = getOS();
		if (os.contains("linux")) {
			return Platform.LINUX;
		}
		else if (os.contains("mac") || (os.contains("osx"))) {
			return Platform.MAC;
		}
		else if (os.contains("xp")) {
			return Platform.XP;
		}
		else if (os.contains("win7")) {
			return Platform.VISTA;
		}
		else if (os.contains("win8")) {
			return Platform.WIN8;
		}
		else if (os.contains("win")) {
			return Platform.WINDOWS;
		}
		else if (os.contains("android")) {
			return Platform.ANDROID;
		}
		else {
			return Platform.ANY;
		}
	}
	
	public Platform getPlatform(String os) {
		if (os.contains("linux")) {
			return Platform.LINUX;
		}
		else if (os.contains("mac") || (os.contains("osx"))) {
			return Platform.MAC;
		}
		else if (os.contains("xp")) {
			return Platform.XP;
		}
		else if (os.contains("win7")) {
			return Platform.VISTA;
		}
		else if (os.contains("win8")) {
			return Platform.WIN8;
		}
		else if (os.contains("win")) {
			return Platform.WINDOWS;
		}
		else if (os.contains("android")) {
			return Platform.ANDROID;
		}
		else {
			return Platform.ANY;
		}
	}

}
