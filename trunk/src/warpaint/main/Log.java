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
import java.util.logging.*;
import java.io.*;

/**
 * @author chris
 *
 * Provides application logging means.
 */
public class Log {
	
	private static Logger log;
	private static FileHandler logFile, warnlogFile;
	private static String logFilename, warnlogFilename;
	
	
	/**
	 * Returns the Logger instance for this application.
	 */
	public static synchronized Logger getLog() {
		if(log == null) {
			log = Logger.getLogger("warpaint");
			try {
				// define two loggers, one for warning msgs and one for detailed stuff
				logFilename = Config.get("logfilename", "warpainting.log");
				warnlogFilename = Config.get("logfilename-warning", "warpainting-warning.log");
				// defining a file logging mechanism that has no rotating log files, 
				// (just one file) each with a maximum of 50 MB
				if(logFile == null) {
					logFile = new FileHandler(logFilename, true);
					// by default an XML formatter is used, we want a simple formatter
					logFile.setFormatter(new SimpleFormatter());
					logFile.setLevel(Level.ALL);
					warnlogFile = new FileHandler(warnlogFilename, true);
					// by default an XML formatter is used, but for now we want a simple formatter (syslog like)
					warnlogFile.setFormatter(new SimpleFormatter());
					warnlogFile.setLevel(Level.WARNING);
					
					log.addHandler(logFile);
					log.addHandler(warnlogFile);
					/* print all logging handlers
					Handler[] handlers = log.getHandlers();
					for(int i = 0; i < handlers.length; i++) {
						System.out.println("Handler" + i + ": " + handlers[i]);
					}
					*/
					log.setLevel(Level.parse(Config.get("loglevel", "ALL")));
					log.info("Application logger ready. Current log level: " + log.getLevel());
					//System.out.println("logFile opened.");
				} else {
					log.severe("Class evof.util.EvofUtil reloaded.");
				}
			} catch(IOException e) {
				System.err.println("Error initializing log file '"+logFilename+"', probably permission problem:");
				e.printStackTrace();
			}
		}
		return log;
	}
	
	
}
