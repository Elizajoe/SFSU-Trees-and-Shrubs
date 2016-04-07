/*  Copyright (C) 2013   Divya Muthyala & Meenal Nitin Honap.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>. */

package com.herps;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
  
public class GPS implements LocationListener {  
  
    public static double latitude;  
    public static double longitude;  
  
    @Override  
    public void onLocationChanged(Location loc)  
    {  
        loc.getLatitude();  
        loc.getLongitude();  
        latitude=loc.getLatitude();  
        longitude=loc.getLongitude();  
       // String Text = "test";

        
    }  
  
    @Override  
    public void onProviderDisabled(String provider)  
    {  
        //print "Currently GPS is Disabled";  
    }  
    @Override  
    public void onProviderEnabled(String provider)  
    {  
        //print "GPS got Enabled";  
    }  
    @Override  
    public void onStatusChanged(String provider, int status, Bundle extras)  
    {  
    }  
}  