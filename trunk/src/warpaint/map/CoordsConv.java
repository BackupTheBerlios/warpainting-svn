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

/**
 * @author chris
 *
 * Provies static methods for coordinate conversion.
 */
public class CoordsConv {
	
	public static double PIXELFACT = 2817.947378;
	
	//	 Lifted from gpsdrive 1.7
    /**
     * This method is not my/our code, it is based on kismet's gpsmap (http://kismetwireless.net/), which in turn
     * lifted it from gpsdrive ( see http://www.gpsdrive.de ).<br> 
     * Christian Dietrich ported it from C to Java.
     * calcxy finds the x and y positions on a 1280x1024 image of a certian scale
     * centered on a given lat/lon.<br>
     * Returns a double array with two elements, the x coordinate on pos 0 , the y coord on pos 1.
     **/
    public static int[] calcxy (double lat, double lon, double pixelfact, /*FOLD00*/
            double zero_lat, double zero_lon, int map_width, int map_height) {
        double dif, posx, posy;
        int[] point = new int[2];
        
        posx = (calcR(lat) * Math.PI / 180.0) * Math.cos (Math.PI * lat / 180.0) * (lon - zero_lon);
    
        posx = (map_width/2) + posx / pixelfact;
    
        posy = (calcR(lat) * Math.PI / 180.0) * (lat - zero_lat);
    
        dif = calcR(lat) * (1 - (Math.cos ((Math.PI * (lon - zero_lon)) / 180.0)));
    
        posy = posy + dif / 1.85;
        posy = (map_height/2) - posy / pixelfact;
    
        //posx += 8; // x offset
        //posy += 30; // y offset
        //System.out.println("x = " + posx + "; y = " + posy);
        point[0] = (int)posx;
        point[1] = (int)posy;
        return point;
    }

    
    /** 
     * This method is not my/our code, it is based on kismet's gpsmap, which in turn
     * lifted it from gpsdrive.<br> 
     * Christian Dietrich ported it from C to Java.
     * This pulls the "real radius" of a lat, instead of a global guesstimate. **/
    public static double calcR (double lat) /*FOLD00*/
    {
        double a = 6378.137, r, sc, x, y, z;
        double e2 = 0.081082 * 0.081082;
        /*
         the radius of curvature of an ellipsoidal Earth in the plane of the
         meridian is given by

         R' = a * (1 - e^2) / (1 - e^2 * (sin(lat))^2)^(3/2)

         where a is the equatorial radius,
         b is the polar radius, and
         e is the eccentricity of the ellipsoid = sqrt(1 - b^2/a^2)

         a = 6378 km (3963 mi) Equatorial radius (surface to center distance)
         b = 6356.752 km (3950 mi) Polar radius (surface to center distance)
         e = 0.081082 Eccentricity
         */

        lat = lat * Math.PI / 180.0;
        sc = Math.sin (lat);
        x = a * (1.0 - e2);
        z = 1.0 - e2 * sc * sc;
        y = Math.pow (z, 1.5);
        r = x / y;

        r = r * 1000.0;
        return r;
    }


}
