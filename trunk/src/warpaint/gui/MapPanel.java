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
package warpaint.gui;

import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;

import org.xml.sax.*;

import warpaint.xml.*;
import warpaint.map.*;
import java.io.File;
import java.io.IOException;


/**
 * @author jan
 * 
 * This container-class displays a picture within a JPanel.
 */
public class MapPanel extends JPanel implements MouseMotionListener {
	/** Image displayed in the panel. */
	private BufferedImage map;
	/** the filename of the background map */
	private String mapfilename;
	
	XMLParser xmlparser;
	GPSParser gpsparser;
	DrawTrack track;
	DrawAP drawAP;
	/* the graphics instance created by the buffered image object */
	Graphics2D graphics;
	
	/* controls whether the track coords have changed, usually only after user interaction */
	boolean recomputeCoords = true;
	
	
	/**
	 * Creates a MapPanel with specified image as map.
	 * @param map the map to display
	 */
	public MapPanel(String mapfilename) {
		super(true);	// guarantee that we are double-buffered
		map =  loadMap(mapfilename);
		this.mapfilename = mapfilename;
		
		this.setPreferredSize(this.getPreferredSize());
		// let the user scroll by dragging to outside the window
		this.setAutoscrolls(true);
		this.addMouseMotionListener(this);
		
		rebuildMap();
	}
	
	
	/**
	 * Loads a map from an image-file on disk.
	 * @param filename the name of the image-file
	 * @return the loaded image
	 */
    private BufferedImage loadMap(String filename) {
    	BufferedImage map = null;
		try {
			map = ImageIO.read(new File(filename));
		} catch (javax.imageio.IIOException e) {
			// at runtime, image file not found
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return map;
    }
    
    
    
	// Methods required by the MouseMotionListener interface
	public void mouseMoved(MouseEvent e) { }
	public void mouseDragged(MouseEvent e) {
		// The user is dragging us, so scroll!
		Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
		this.scrollRectToVisible(r);
	}
	
	public Dimension getPreferredSize() {
		// check whether a map was loaded
		if(map == null) {
			// no map loaded yet, return default size
			return new Dimension(1024, 768);
		}
		return new Dimension(this.map.getWidth(), this.map.getHeight());
	}
	
	/**
	 *  Called by UI to refresh the component if necessary.
	 */
	public void paintComponent(Graphics g) {
		//clear(g);
		
		//Graphics2D graphics = (Graphics2D)g;
		
		// check whether we have a valid map loaded
		if(map == null || graphics == null) {
			// do/draw whatever you want when no map is available
			g.drawString("No map loaded or graphics device unavailable.", 15, 15);
			return;
		}
		g.drawImage(map, 0, 0, map.getWidth(), map.getHeight(), this);
		
		/* reset the need to recompute coords */
		if(recomputeCoords) recomputeCoords = false;
	}
	
	/**
	 * Loads a new map into the component and displays it.
	 * @param map the new map
	 */
	public void updateMap(BufferedImage map) {
		this.map = map;
		this.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
		// force update of component
		this.repaint();
	}
	
	/**
	 * Returns the height of the actual loaded map.
	 * @return the height of the map
	 */
	public int getMapHeight() {
		return this.map.getHeight();
	}
	
	/**
	 * Returns the width of the actual loaded map.
	 * @return the width of the map
	 */
	public int getMapWidth() {
		return this.map.getWidth();
	}
	
	/**
	 * Clears pixmap of the component.
	 * (super.paintComponent clears offscreen pixmap since we're using double buffering by default)
	 */
	protected void clear(Graphics g) {
		super.paintComponent(g);
	}
	
	/**
	 * Control displaying the track.
	 * @param displayTrack
	 */
	public void displayTrack(boolean displayTrack) {
		if(track != null) track.displayTrack(displayTrack);
		rebuildMap();
	}
	
	public void displayWep(boolean state) {
		if(drawAP != null) drawAP.displayWEP(state);
		rebuildMap();
	}
	
	public void displayNonWep(boolean state) {
		if(drawAP != null) drawAP.displayNONWEP(state);
		rebuildMap();
	}
	
	public void displaySlowNets(boolean state) {
		if(drawAP != null) drawAP.displaySPEED1(state);
		rebuildMap();
	}
	
	public void displayFastNets(boolean state) {
		if(drawAP != null) drawAP.displaySPEED2(state);
		rebuildMap();
	}
	
	/**
	 * Repaint the component.
	 */
	public void repaint() {
		super.repaint();
	}
	
	/**
	 * Rebuild the map from the saved backgroundMap.
	 */
	private void rebuildMap() {
		// rebuild the map from the background map
		if(mapfilename == null) {
			//System.err.println("MapPanel: map filename is null, cannot repaint");
			return;
		}
		map =  loadMap(mapfilename);
		if(map == null) {
			// map was not loaded correctly
			return;
		}
		// get the graphics instance of the map
		graphics = map.createGraphics();
		graphics.setStroke(new BasicStroke(2));	// FIXME: stroke = line width for track
		if(track!=null) {
			track.draw(graphics, recomputeCoords);
		}
		if(drawAP != null) {
			drawAP.draw(graphics, recomputeCoords);
		}
		repaint();
	}
	
	/**
	 * Loads a given GPS filename that contains the GPS track.
	 * @param filename
	 */
	public void loadGPSfile(File file) {
		try {
			gpsparser = new GPSParser(file.getAbsolutePath());
	        track = new DrawTrack(gpsparser.getGPSTrack(), 50.775, 6.082, 1024, 1280);
		} catch(SAXException se) {
			Dialogs.errorMsg(se + "; Probably the DTD file was not found, because you don't have an Internet connection. Change the XML file to point to a local DTD.\n\n" + se.getMessage());
		} catch(IOException e) {
			if(e instanceof FileNotFoundException) {
				Dialogs.errorMsg(e + "; The file was not found.\n\n" + e.getMessage());
			} else {
				Dialogs.errorMsg(e.toString() + "\n\n" + e.getMessage());
			}
		}
		/* reset the need to recompute coords */
		recomputeCoords = true;
		rebuildMap();
	}
	
	/**
	 * Loads a given XML filename that contains the XML track.
	 * @param filename
	 */
	public void loadXMLfile(File file) {
		try {
			xmlparser = new XMLParser(file.getAbsolutePath());
	        drawAP = new DrawAP(xmlparser.getWNList(), 50.775, 6.082, 1024, 1280);
		} catch(SAXException se) {
			Dialogs.errorMsg(se + "; Probably the DTD file was not found, because you don't have an Internet connection. Change the XML file to point to a local DTD.\n\n" + se.getMessage());
		} catch(IOException e) {
			if(e instanceof FileNotFoundException) {
				Dialogs.errorMsg(e + "; The file was not found.\n\n" + e.getMessage());
			} else {
				Dialogs.errorMsg(e.toString() + "\n\n" + e.getMessage());
			}
		}
		/* reset the need to recompute coords */
		recomputeCoords = true;
		// get the graphics instance of the map
		graphics = map.createGraphics();
		graphics.setStroke(new BasicStroke(2));	// FIXME: stroke = line width for track
		if(drawAP != null) {
			drawAP.draw(graphics, recomputeCoords);
		}
		repaint();
	}
}
