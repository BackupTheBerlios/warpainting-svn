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


public class XMLParser {
	
	ArrayList wnlist;	// holds list of wlan hotspots
	
	public ArrayList getWNList() {
		return wnlist;
	}
	
	/**
	 * 
	 * @param filename of xml file
	 */
	public XMLParser(String filename) throws SAXException, IOException{
		Object document = new Object();
		wnlist = new ArrayList();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        	//factory.setValidating(true);   
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
	
		String d_bssid="", d_ssid="", d_type="";
		int d_channel=0;
		float d_maxrate=0;
		boolean d_wep=false, d_cloaked=false;
		double d_maxlat=0, d_minlat=0,d_minlon=0,d_maxlon=0,d_minalt=0,d_maxalt=0;
	
	
		if (((n=((Document) document).getLastChild()) != null) &&
			(n.getNodeName().equals("detection-run"))) {
			
			nodelist=n.getChildNodes();
	
			// wireless networks
			for(int i=1;i<nodelist.getLength();i+=2) {
				nodelist2=nodelist.item(i).getChildNodes();
				for (int j=1;j<nodelist2.getLength();j+=2) {
					if (nodelist2.item(j).getNodeName().equals("BSSID")) {
						d_bssid=nodelist2.item(j).getFirstChild().getNodeValue();
					} else if (nodelist2.item(j).getNodeName().equals("SSID")) {
						d_ssid=nodelist2.item(j).getFirstChild().getNodeValue();
					} else if (nodelist2.item(j).getNodeName().equals("channel")) {
						d_channel=Integer.parseInt(nodelist2.item(j).getFirstChild().getNodeValue());
					} else if (nodelist2.item(j).getNodeName().equals("maxrate")) {
						d_maxrate=Float.parseFloat(nodelist2.item(j).getFirstChild().getNodeValue());
					} else if (nodelist2.item(j).getNodeName().equals("gps-info")) {
						nodelist3=nodelist2.item(j).getChildNodes();
						for(int k=1; k<nodelist3.getLength();k+=2) {
							if (nodelist3.item(k).getNodeName().equals("min-lat")) {
								d_minlat=Double.parseDouble(nodelist3.item(k).getFirstChild().getNodeValue());
							} else if (nodelist3.item(k).getNodeName().equals("max-lat")) {
								d_maxlat=Double.parseDouble(nodelist3.item(k).getFirstChild().getNodeValue());
							} else if (nodelist3.item(k).getNodeName().equals("min-lon")) {
								d_minlon=Double.parseDouble(nodelist3.item(k).getFirstChild().getNodeValue());
							} else if (nodelist3.item(k).getNodeName().equals("max-lon")) {
								d_maxlon=Double.parseDouble(nodelist3.item(k).getFirstChild().getNodeValue());
							} else if (nodelist3.item(k).getNodeName().equals("max-alt")) {
								d_maxalt=Double.parseDouble(nodelist3.item(k).getFirstChild().getNodeValue());
							} else if (nodelist3.item(k).getNodeName().equals("min-alt")) {
								d_minalt=Double.parseDouble(nodelist3.item(k).getFirstChild().getNodeValue());
							}
						}
					}
				}
				d_type=nodelist.item(i).getAttributes().getNamedItem("type").getNodeValue();
				
				d_wep=(nodelist.item(i).getAttributes().getNamedItem("wep").getNodeValue().equals("true"));
				d_cloaked=(nodelist.item(i).getAttributes().getNamedItem("cloaked").getNodeValue().equals("true"));
	
				GPSInfo gpsinfo = new GPSInfo((d_minlat + d_maxlat) / 2, (d_minlon + d_maxlon) / 2
							, (d_minalt + d_maxalt) / 2);
				WirelessNetwork wn = new WirelessNetwork(d_bssid, d_ssid, d_type, d_channel, d_maxrate, d_wep, d_cloaked, gpsinfo);
				this.wnlist.add(wn);
	
			}
		} else {
			System.err.println("ERROR: No kismet XML file!");
		}
		
	}

}
