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
 * along with Foobar; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package xml;

class TestXML {

	public static void main(String args[]) {
		try {

		XMLParser xml = new XMLParser(args[0] + ".xml");
		GPSParser gps = new GPSParser(args[0] + ".gps");
		
		for(int i=0; i<xml.getWNList().size();i++) {
			WirelessNetwork wn = (WirelessNetwork) xml.getWNList().get(i);
			System.out.println(wn.wep);
		}
		
		/*for(int i=0; i<gps.getGPSTrack().size();i++) {
			TrackLog tl = (TrackLog) gps.getGPSTrack().get(i);
			System.out.println(tl.sec);
		}*/
		
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
