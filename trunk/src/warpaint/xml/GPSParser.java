/*
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
package warpaint.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author feanor
 *	parses the .gps file
 */
public class GPSParser {

	private ArrayList gpstrack;	// holds gps track positions

	public ArrayList getGPSTrack() {
		return gpstrack;
	}
	
	/**
	 * @param filename of gps file
	 */
	public GPSParser(String filename) throws SAXException, IOException {

		gpstrack = new ArrayList();

		Object document = new Object();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        	//factory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", "false"); // disables external dtds for SAX parser, does not work with DOM currently
			factory.setValidating(false);   
        	//factory.setNamespaceAware(true);
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse( new File(filename) );
 		} catch (ParserConfigurationException pce) {
		// Parser with specified options can't be built
			pce.printStackTrace();

        }

		Node n;
		NodeList nodelist, nodelist2, nodelist3;
	
		
		if (((n=((Document) document).getLastChild()) != null) &&
			(n.getNodeName().equals("gps-run"))) {
			
			nodelist=n.getChildNodes();
	
			// gps track positions
			for(int i=0;i<nodelist.getLength();i+=1) {
				
				if (nodelist.item(i).getNodeName().equals("gps-point")) {
					
					if (nodelist.item(i).getAttributes().getNamedItem("bssid").getNodeValue().startsWith("GP")) {
						long sec,usec;
						double lat, lon, alt;
						
						sec=Long.parseLong(nodelist.item(i).getAttributes().getNamedItem("time-sec").getNodeValue());
						usec=Long.parseLong(nodelist.item(i).getAttributes().getNamedItem("time-usec").getNodeValue());
						lat=Double.parseDouble(nodelist.item(i).getAttributes().getNamedItem("lat").getNodeValue());
						lon=Double.parseDouble(nodelist.item(i).getAttributes().getNamedItem("lon").getNodeValue());
						alt=Double.parseDouble(nodelist.item(i).getAttributes().getNamedItem("alt").getNodeValue());
						
						GPSInfo gpsinfo = new GPSInfo(lat, lon, alt);
						TrackLog tl = new TrackLog(sec, usec, gpsinfo);
						
						this.gpstrack.add(tl);
						
					}
				}
			}
		} else {
			System.err.println("ERROR: No kismet XML file!");
		}
	}

}
