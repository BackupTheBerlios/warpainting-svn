/*
 * Created on 01.10.2004
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
 * along with Foobar; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package map;

import java.awt.*;

import xml.*;

import java.awt.geom.*;
import java.util.*;
/**
 * @author Sammy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DrawAP {

	
	/* the list of TrackLog objects */
	ArrayList aplist;

	/* the lon and lat of the center of the map */
	double zero_lon;
	double zero_lat;

	/* the height and width of the map image */
	int map_height;
	int map_width;

	/* Alpha blending of Access Point painting*/
	float alpha = 1.0F;
	
	/* infos of the AP*/
/*	String bssid;
	String ssid;
	String type;
	int channel;
	float maxrate;
	boolean wep;
	boolean cloaked;
*/	
	/** Switches on the Gui */
	boolean display_wep = true;
	boolean display_nonwep = true;
	boolean display_speed1 = true;
	boolean display_speed2 = true;
	
	public void displayWEP(boolean display_wep) {
		this.display_wep = display_wep;
	}
	public void displayNONWEP(boolean display_nonwep) {
		this.display_nonwep = display_nonwep;
	}
	public void displaySPEED1(boolean display_speed1) {
		this.display_speed1 = display_speed1;
	}
	public void displaySPEED2(boolean display_speed2) {
		this.display_speed2 = display_speed2;
	}
	
	public DrawAP(ArrayList aplist,double zero_lat, double zero_lon, int map_height, int map_width) {
		this.aplist = aplist;
		this.zero_lon = zero_lon;
		this.zero_lat = zero_lat;
		this.map_height = map_height;
		this.map_width = map_width;
	/*	this.bssid = bssid;
		this.ssid = ssid;
		this.type = type;
		this.channel = channel;
		this.maxrate = maxrate;
		this.wep = wep;
		this.cloaked = cloaked;
		*/
	}
//    Shape circle = new  Ellipse2D.Double(50,50,50,50);

	 private AlphaComposite makeComposite(float alpha) {
	    int type = AlphaComposite.SRC_OVER;
	    return(AlphaComposite.getInstance(type, alpha));
	 }



	 /** Prints */
	 public void draw(Graphics g,boolean recomputeCoords) {
		Graphics2D g2d = (Graphics2D)g;
		int x = 10;
		int y = 10;
		int AP_width = 15;
		int AP_high = 15;
		int map_scale = 15000;
		double lat = 0;
		double lon = 0;
		int[] pixelCoords = new int[2];
		Color AP_color = Color.blue;
		
		
		for(Iterator iterator = aplist.iterator(); iterator.hasNext(); ) {
			WirelessNetwork wlan = (WirelessNetwork)iterator.next();
			GPSInfo gpsinfo = wlan.getGPSInfo();

			// wep maxrate,type
// yellow blue red green magenta
			
			AP_width = 15;
			AP_color = Color.blue;
			//alpha = 0.4F;
			
			// make WEP lans dark blue
			if(wlan.getWep()) {
				AP_color = new Color(250, 0, 0);
			}
			
//			 make Non-WEP lans light blue
			if(!wlan.getWep()) {
				AP_color = new Color(0, 0, 250);
			}
			
			if (!display_wep && wlan.getWep() ){
				alpha = 0.00F;
			}

			if (!display_nonwep && !wlan.getWep()) {
				alpha = 0.00F;
			}
			
			if (!display_speed1 && (wlan.getMaxrate() <= 11.0) ){
				alpha = 0.00F;
			}
			
			if (!display_speed2 && (wlan.getMaxrate() > 11.0) ){
				alpha = 0.00F;
			}
			
				
/*				
			if (wlan.getMaxrate() > 11.0){
//				AP_width = AP_width * 2;
				AP_color = Color.black;

			}
			
			if (wlan.getType().equals("ad-hoc")){
				alpha = 0.0F;
//				AP_width = AP_width * 2;
//				AP_color = Color.green;
			}
			if (wlan.getWep() == true) {
				AP_color = Color.red;
			} 
			
*/

//			AP punkt entsprechend dem Kartenzoom mitzoomen
//			z.Z. noch statisch
			
/*			if (map_scale < 10000) {
				AP_width = 20000 - 100/20;
			} else if (map_scale > 20000) {
				AP_width = 10000 - 100/20;
			} else {
				AP_width = map_scale - 1000/20;
			}
*/					
			
			AP_high = AP_width;
			
			lat = gpsinfo.getLat();
			lon = gpsinfo.getLon();
			//convert into pixel coords /* FIXME: scale hardcoded */
			pixelCoords = CoordsConv.calcxy(lat, lon, map_scale/CoordsConv.PIXELFACT, zero_lat, zero_lon, map_width, map_height);
			drawcircle(g2d,(int) pixelCoords[0], (int)pixelCoords[1], AP_width, AP_high,alpha,AP_color);
		}
        
      
        
		
	    
	}	 
	 
	 private void drawcircle(Graphics2D g2d, int x, int y, int width, int high, float alpha,Color color) {
	 	Composite originalComposite = g2d.getComposite();	  
		g2d.setComposite(makeComposite(alpha));
		g2d.setPaint(color);
		Shape circle = new Ellipse2D.Double(x,y,width,high);
		g2d.fill(circle);
		g2d.setComposite(originalComposite);  	  
	 }
}
