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

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class CommonActivityGroup extends ActivityGroup {

	public static CommonActivityGroup group;
	private ArrayList<View> history;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      this.history = new ArrayList<View>();
	      group = this;
	      
	      Intent in1 = getIntent();		
			Uri configPath = in1.getData(); 
			
			
			Intent viewIntent = new Intent(this,ObserverAppV2.class);
			viewIntent.setData(configPath);
			
			
			
	      
 
              // Start the root activity within the group and get its view
	      View view = getLocalActivityManager().startActivity("ObserverAppV2", viewIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
	    	                                .getDecorView();
 
              // Replace the view of this ActivityGroup
	      replaceView(view);
 
	   }
 
	public void replaceView(View v) {
                // Adds the old one to history
		history.add(v);
                // Changes this Groups View to the new View.
		setContentView(v);
	}
 
	public void back() {
		if(history.size() > 0) {
			
			setContentView(history.get(history.size()-1));
			history.remove(history.size()-1);
			
			
		}else 
		{
			finish();
		}
			
				
			
	}
 
   @Override
    public void onBackPressed() {
	   CommonActivityGroup.group.back();
        return;
    }
   
//   @Override
//	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//	   if (requestCode == 1337 && resultCode == Activity.RESULT_OK){
//		   Activity activity = getLocalActivityManager().getCurrentActivity();
//	        activity.onActivityResult(requestCode, resultCode, data);
//	        
//	   }
//   }
   
   
	
	
}
