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
 * @author feanor
 *
 * class GPSInfo holds GPS data
 */

public class GPSInfo {
	double lat, alt, lon;

	public GPSInfo(double lat, double lon, double alt) {
		this.lat=lat;
		this.alt=alt;
		this.lon=lon;
	}

	public GPSInfo(GPSInfo gpsinfo) {
		this.lat=gpsinfo.lat;
		this.alt=gpsinfo.alt;
		this.lon=gpsinfo.lon;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public double getAlt() {
		return alt;
	}

}
