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

import android.os.Parcel;
import android.os.Parcelable;

import com.herps.data.ListItem;


public class HerpDetails implements ListItem,Parcelable {
	
	private String group;
	private String commonName;
	private String family;
	private String genus;
	private String epithet;
	private String alternate;
	private String imageName;
	
	private String coordinate;
	public static int k=0;

	
	

	
	
	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	
	
	

	//constructor
	public HerpDetails(){
		
	}
	
	public HerpDetails(Parcel in) {
		readFromParcel(in);
	}
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getGenus() {
		return genus;
	}
	public void setGenus(String genus) {
		this.genus = genus;
	}
	public String getEpithet() {
		return epithet;
	}
	public void setEpithet(String epithet) {
		this.epithet = epithet;
	}
	public String getStatus() {
		return alternate;
	}
	public void setAlternate(String alternate) {
		this.alternate = alternate;
	}

	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	@Override
	public boolean isSection() {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override
	public String toString() {
		return this.commonName;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(this.group);
		dest.writeString(this.commonName);
		dest.writeString(this.family);
		dest.writeString(this.genus);
		dest.writeString(this.epithet);
		dest.writeString(this.alternate);
		dest.writeString(this.imageName);
		dest.writeString(this.coordinate);
		
		
		
	}
	
	private void readFromParcel(Parcel in) {
		 
		
		this.group = in.readString();
		this.commonName = in.readString();
		this.family = in.readString();
		this.genus = in.readString();
		this.epithet = in.readString();
		this.alternate = in.readString();
		this.imageName = in.readString();
		this.coordinate=in.readString();
		
		
		
	}

	public static final Parcelable.Creator CREATOR =
    	new Parcelable.Creator() {
            public HerpDetails createFromParcel(Parcel in) {
                return new HerpDetails(in);
            }
 
            public HerpDetails[] newArray(int size) {
                return new HerpDetails[size];
            }
        };


	
	
	
	


}
