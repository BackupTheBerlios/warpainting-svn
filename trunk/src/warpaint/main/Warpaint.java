package warpaint.main;
import warpaint.gui.MainFrame;

/*
 * Created on 30.09.2004
 *
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
 * along with warpainting; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

/**
 * @author jan
 * 
 * Just a wrapper class to launch application (creates & runs the GUI).
 */
public class Warpaint {
	
	
	
	/** The name of the application. */
	static public final String windowName = "warPAINt v0.1";	
    
	/**
	 * The main class of the application.
	 * @param args command line parameters, we just ignore
	 */
	public static void main(String[] args) {
		
		// look for the config file in current working dir if no other location
		// was specified by property 'configfile'
		String configFile = System.getProperties().getProperty("configfile");
		if(configFile != null) {
			warpaint.main.Config.setConfigFile(configFile);
		}
		warpaint.main.Config.load();
		
		
		warpaint.main.Log.getLog().finest("Application non-gui setup finished, now launching gui.");
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	//MainFrame gui = new MainFrame(windowName, "map_50.775_6.082_15000_1280_1024.gif");
            	//MainFrame gui = new MainFrame(windowName, "map_file0010.gif");
				MainFrame gui = new MainFrame(windowName, "");
            }
        });
	}
}
