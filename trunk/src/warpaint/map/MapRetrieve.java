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
package warpaint.map;
import java.net.*;
import java.io.*;

//const char url_template_mb[] = "http://go.vicinity.com/homedepotvd/MakeMap.d?&CT=%f:%f:%ld&IC=&W=%d&H=%d&FAM=mblast&LB=%s";

public class MapRetrieve {

	public static final String MAP_CACHE_DIR=".warpaint";


/**
 * downloadMap retrieves a map and saves it to the user home folder/MAP_CACHE_DIR.
 * it returns 0 if the map had to be downloaded, otherwise the map is still in its
 * folder and downloadMap returns 1. 
 * 	todo: exceptions
 * 
*/
	public static int downloadMap(double avglat, double avglon, int sizeX, int sizeY, long scale)
		throws MalformedURLException, IOException 
	{
		// URL for map retrieving
		String str="http://go.vicinity.com/homedepotvd/MakeMap.d?&CT=" +
				avglat + ":" + avglon + ":" + scale + "&TC=&W=" + sizeX + "&H=" + sizeY +
				"&FAM=mblast&LB=&DU=KM";
		URL url = new URL(str);
		InputStream is = url.openStream();
		
		byte data[]={0};
		int count;

		String homedir = System.getProperty("user.home");
		String mapfilename = homedir + File.separator + MAP_CACHE_DIR + File.separator +
			"map_" + avglat + "_" + avglon + "_" + scale + "_" + sizeX + "_" + sizeY + ".gif";

		File cachedir = new File(homedir + File.separator + MAP_CACHE_DIR);
		File mapfile = new File(mapfilename);
		
		// if map exists then do nothing
		if (!mapfile.exists()) {
			// look if cachedir exists and create it if neccessary
			if (!cachedir.exists()) {
				cachedir.mkdirs();
			}
			// copy map to file
			FileOutputStream fos = new FileOutputStream(mapfile);
			while((count=is.read(data))!=-1) {
				fos.write(data);
			}
			is.close();
			fos.close();
		} else {
			return 1; // no download
		}
		return 0; // map downloaded
		
	}

}
