/*
 * Created on 01.10.2004
 * Copyright (c) 2004 by Christian Dietrich, Boris Leidner, 
 * Jan Gall and Sammy Okasha
 *
 * This file is part of warpainting.
 *
 * warpainting is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * warpainting is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
 * Defines static constants that are used as keys for getting system properties and other static variables.
 */
package warpaint.main;

import java.util.*;
import java.io.*;

/**
 * @author chris
 *
 * Defines static constants that are used as keys for getting system properties and other static variables.
 */
public class Config {
	
	/**
	 * Use this string when getting the used scale from the system properties.
	 */
	public final static String SCALE = "SCALE";
	
	/**
	 * The version of the application.
	 */
	public final static float VERSION = 0.1f;
	
	
	// default config filename
	private static String configFilename = "warpainting.properties";
	
	// the singleton config instance
	private static Properties props = new Properties();
	
	private static void createProps() {
		File configFile = new File(configFilename);
		if(configFile.exists()) {
			try {
				props.load(new FileInputStream(configFile));
				
			} catch(IOException e) {
				// could not load properties file
				//System.err.println("Could not load configuration properties from file '" + configFilename + "'");
				warpaint.gui.Dialogs.errorMsg("Could not load configuration properties from file '" + configFilename + "'", "Error loading application properties");
				e.printStackTrace();
			}
		}
		warpaint.main.Log.getLog().finest("Created application properties based on config file: " + configFilename);
	}
	
	/**
	 * Allows setting the config file to use when loading the properties. Is 
	 * only effective when called before the first call to props().
	 */
	public static void setConfigFile(String filename) {
		configFilename = filename;
	}
	
	public static void load() {
		createProps();
	}
	
	public Properties props() {
		if(props == null) createProps();
		return props;
	}
	
	public static String get(String key) {
		if(props == null) createProps();
		return props.getProperty(key);
	}
	
	public static String get(String key, String defaultValue) {
		if(props == null) createProps();
		return props.getProperty(key, defaultValue);
	}
	
	public static Object setProperty(String key, String value) {
		if(props == null) createProps();
		Object obj = props.setProperty(key, value);
		try {
			props.store(new FileOutputStream(configFilename), "warpaint config properties");
		} catch(IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
}
