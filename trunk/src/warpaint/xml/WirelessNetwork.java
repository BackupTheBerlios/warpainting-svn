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
/**
 * 
 * @author feanor
 *
 * WirelessNetwork holds information about a WLAN hotspot
 * 
 */
public class WirelessNetwork {

	String bssid, ssid, type; 
	int channel;
	float maxrate;
	boolean wep, cloaked;
	GPSInfo gpsinfo;

	public WirelessNetwork(String bssid, String ssid, String type, int channel, float maxrate, boolean wep, boolean cloaked, GPSInfo gpsinfo) {
		this.bssid=bssid;
		this.ssid=ssid;
		this.type=type;
		this.channel=channel;
		this.maxrate=maxrate;
		this.wep=wep;
		this.cloaked=cloaked;
		this.gpsinfo=gpsinfo;
	}
	
	public String getBssid() {
		return this.bssid;
	}

	public String getSsid() {
		return this.ssid;
	}

	public String getType() {
		return this.type;
	}
	
	public int getChannel() {
		return this.channel;
	}

	public float getMaxrate() {
		return this.maxrate;
	}
	
	public boolean getWep() {
		return this.wep;
	}
	
	public boolean getCloaked() {
		return this.cloaked;
	}
	
	public GPSInfo getGPSInfo() {
		return this.gpsinfo;
	}
}
