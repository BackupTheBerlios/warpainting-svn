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
 * TrackLog class holds time and GPS data for logging a GPS track
 */
public class TrackLog {
	long sec,usec;	// seconds and microseconds
	GPSInfo gpsinfo;	//gps position
	
	public TrackLog(long sec, long usec, GPSInfo gpsinfo) {
		this.sec=sec;
		this.usec=usec;
		this.gpsinfo=gpsinfo;
	}
	
	public long getSec() {
		return this.sec;
	}
	
	public long getUsec() {
		return this.usec;
	}
	
	public GPSInfo getGpsinfo() {
		return this.gpsinfo;
	}
}
