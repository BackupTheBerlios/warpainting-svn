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
 */
package map;

import java.awt.*;
import java.util.*;
import xml.*;


/**
 * @author chris
 *
 * Draws a track by given GPS coords. This class depends on an 
 * ArrayList containing TrackLog objects provided by XMLParser. 
 * 
 */
public class DrawTrack {
	
	/* the list of TrackLog objects */
	ArrayList track;
	
	/* the lon and lat of the center of the map */
	double zero_lon;
	double zero_lat;
	
	/* the height and width of the map image */
	int map_height;
	int map_width;
	
	/* this is a cache for the pixel coords */
	int[][] pixelCoordsCache;
		  
	// whether the track shall be displayed or not
	boolean displayTrack = true;
	
	/**
	 * Create a DrawTrack object, call the draw() method to make it draw.
	 * 
	 * @param track	ArrayList containing TrackLog objects provided by XMLParser
	 * @param zero_lat	the latitude of the map's center
	 * @param zero_lon	the latitude of the map's center
	 * @param map_height	the height of the map image
	 * @param map_width	the width of the map image
	 */
	public DrawTrack(ArrayList track, double zero_lat, double zero_lon, int map_height, int map_width) {
		this.track = track;
		this.zero_lon = zero_lon;
		this.zero_lat = zero_lat;
		this.map_height = map_height;
		this.map_width = map_width;
		if(track.isEmpty()) {
			System.err.println("DrawTrack: gpsinfo track is empty, no GPS records?");
		}
	}
	
	/**
	 * Draws the route onto the given Graphics g.
	 * @param g
	 */
	public void draw(Graphics g, boolean recomputeCoords) {
		// skip if we don't need to be drawn
		if(!displayTrack) return;
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.BLACK);
		if(recomputeCoords) {
			/* create the coords cache, for each gps coord, two values are saved
			 * first the x, then the y value */
			pixelCoordsCache = new int[track.size()][2];
			int i = 0;
			for(Iterator iterator = track.iterator(); iterator.hasNext(); i++) {
				TrackLog tracklog = (TrackLog)iterator.next();
				GPSInfo gpsinfo = tracklog.getGpsinfo();
				double lat = gpsinfo.getLat();
				double lon = gpsinfo.getLon();
				
				//convert into pixel coords /* FIXME: scale hardcoded; use 15000 for the map50_ ... and 11800 for the expedia map_file0010.gif */
				pixelCoordsCache[i] = CoordsConv.calcxy(lat, lon, 11800/CoordsConv.PIXELFACT, zero_lat, zero_lon, map_width, map_height);	
			}
		}
		// check whether the pixel coords are cached, if so, draw the track
		if(pixelCoordsCache == null) {
			System.err.println("DrawTrack: draw() called, but pixelCoordsCache is null");
			return;
		}
		for(int i = 1; i < pixelCoordsCache.length; i++) {
//			System.out.println("DrawTrack: Coords: x:" + pixelCoords[0] + "; y: " + pixelCoords[1]);
			g2d.drawLine(pixelCoordsCache[i-1][0], pixelCoordsCache[i-1][1], 
					pixelCoordsCache[i][0], pixelCoordsCache[i][1]);
		} // end for
	} // end method
	
	
	/**
	 *  Controls whether the track shall be drawn or not.
	 * @param state
	 */
	public void displayTrack(boolean state) {
		displayTrack = state;
	}
}
