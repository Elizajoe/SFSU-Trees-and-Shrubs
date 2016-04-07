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

package com.herps.data;

import java.util.Comparator;

import com.herps.HerpDetails;

public class HerpDetailsComparator implements Comparator {

	@Override
	public int compare(Object object1, Object object2) {
		// TODO Auto-generated method stub
		
		 
		
		 String commonName1 = ((HerpDetails)object1).getCommonName();        
	     String commonName2 = ((HerpDetails)object2).getCommonName();
	     
	     commonName1.replaceAll("\\s","");
	     commonName2.replaceAll("\\s","");
	        //uses compareTo method of String class to compare names of the employee
	        return commonName1.compareTo(commonName2);
		
	}

}
