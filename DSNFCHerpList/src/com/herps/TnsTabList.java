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


import com.herps.R;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TnsTabList extends TabActivity{
    /** Called when the activity is first created. */
	TabHost tabHost;
	//protected TextView title;
    //protected ImageView icon;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_tab_layout);
        
        
        Intent selectionIntent = getIntent();		
		Uri configPath = selectionIntent.getData(); 
       
        TabHost mTabHost = getTabHost();
        
        
          /*This goes to Common name tab*/
        Intent in1=new Intent(this, CommonActivityGroup.class);
        in1.setData(configPath);
	    // Initialize a TabSpec for the first tab and add it to the TabHost
        
        TextView txtTab = new TextView(this);
        txtTab.setTextSize(19);
        txtTab.setText("Common Names");
       // txtTab.setPadding(8, 9, 8, 9);
       // txtTab.setTextColor(Color.WHITE);
       // txtTab.setBackgroundColor(Color.BLACK);
        
       TabSpec spec = mTabHost.newTabSpec("ListViewGroup").setIndicator("COMMON");
       
       
        spec.setContent(in1);
        mTabHost.addTab(spec);      
        
        /*This goes to Family name tab*/
        Intent in2=new Intent(this, TaxActivityGroup.class);
        in2.setData(configPath);
	    // Initialize a TabSpec for the first tab and add it to the TabHost
        TextView txtTab1 = new TextView(this);
        txtTab1.setTextSize(19);
        txtTab1.setText("Scientific Names");
       // txtTab.setPadding(8, 9, 8, 9);
      //  txtTab1.setTextColor(Color.WHITE);
      //  txtTab1.setBackgroundColor(Color.BLACK);
        TabSpec spec2 = mTabHost.newTabSpec("Species").setIndicator("SCIENTIFIC");
        spec2.setContent(in2);
        mTabHost.addTab(spec2);	
        
        
        /*This goes to Observations name tab*/
        Intent in3=new Intent(this, ObservationActivityGroup.class);
	    // Initialize a TabSpec for the first tab and add it to the TabHost
        TextView txtTab2 = new TextView(this);
        txtTab2.setTextSize(19);
        txtTab2.setText("Observations");
       // txtTab.setPadding(8, 9, 8, 9);
      //  txtTab2.setTextColor(Color.WHITE);
      //  txtTab2.setBackgroundColor(Color.BLACK);
        
        
        
        TabSpec spec3 = mTabHost.newTabSpec("Observe").setIndicator("OBSERVATIONS");
        spec3.setContent(in3);
        mTabHost.addTab(spec3);
        
        
        mTabHost.setCurrentTab(0);
        
       
        
       mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
		
		@Override
		public void onTabChanged(String tabId) {
			// TODO Auto-generated method stub
			setTitle(tabId);
			if( tabId.equals("Observe") ){
                ObservationActivityGroup.group.onCreate(null);
            }
		}
       });
        
        
        
    }
}