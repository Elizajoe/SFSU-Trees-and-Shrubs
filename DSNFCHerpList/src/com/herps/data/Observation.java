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

import android.os.Parcel;
import android.os.Parcelable;

import com.herps.HerpDetails;

public class Observation implements Parcelable {
	
	private int id;
	private String commonName;
	private String timeStamp;
	private String imageName;
	private String family;
	private String latitude;
	private String longitude;
	private String species;
	
	
	 // Empty constructor
    public Observation(){
 
    }
    // constructor
    public Observation(int id, String commonName, String imageName, String family, String latitude, String longitude, String species,String timestamp){
        this.id = id;
        this.commonName = commonName;
        this.imageName = imageName;
        this.family = family;
        this.latitude = latitude;
        this.longitude = longitude;
        this.species = species;
        this.timeStamp = timestamp;
    }
 
    // constructor
    public Observation(String commonName, String imageName,String family, String latitude, String longitude, String species,String timestamp){
    	this.commonName = commonName;
        this.imageName = imageName;
        this.family = family;
        this.latitude = latitude;
        this.longitude = longitude;
        this.species = species;
        this.timeStamp = timestamp;
    }
    
   
	public Observation(Parcel in) {
		readFromParcel(in);
	}
    
    
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	 public String getFamily() {
			return family;
		}
		public void setFamily(String family) {
			this.family = family;
		}
		public String getLatitude() {
			return latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		public String getLongitude() {
			return longitude;
		}
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
		public String getSpecies() {
			return species;
		}
		public void setSpecies(String species) {
			this.species = species;
		}
	
		
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((commonName == null) ? 0 : commonName.hashCode());
		result = prime * result + ((family == null) ? 0 : family.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((imageName == null) ? 0 : imageName.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((species == null) ? 0 : species.hashCode());
		result = prime * result
				+ ((timeStamp == null) ? 0 : timeStamp.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Observation other = (Observation) obj;
		if (commonName == null) {
			if (other.commonName != null)
				return false;
		} else if (!commonName.equals(other.commonName))
			return false;
		if (family == null) {
			if (other.family != null)
				return false;
		} else if (!family.equals(other.family))
			return false;
		if (id != other.id)
			return false;
		if (imageName == null) {
			if (other.imageName != null)
				return false;
		} else if (!imageName.equals(other.imageName))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (species == null) {
			if (other.species != null)
				return false;
		} else if (!species.equals(other.species))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
		dest.writeString(this.commonName);
		dest.writeString(this.imageName);
		dest.writeString(this.family);
		dest.writeString(this.latitude);
		dest.writeString(this.longitude);
		dest.writeString(this.species);
		dest.writeString(this.timeStamp);
		
		
	}
	
	private void readFromParcel(Parcel in) {
		
		this.commonName = in.readString();
		this.imageName = in.readString();
		this.family = in.readString();
		this.latitude = in.readString();
		this.longitude = in.readString();
		this.species = in.readString();
		this.timeStamp = in.readString();
		
	}
	public static final Parcelable.Creator CREATOR =
    	new Parcelable.Creator() {
            public Observation createFromParcel(Parcel in) {
                return new Observation(in);
            }
 
            public HerpDetails[] newArray(int size) {
                return new HerpDetails[size];
            }
        };
     
	
	
	
	

}
