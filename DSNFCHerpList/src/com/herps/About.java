
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.herps.R;
import com.herps.navigation.NavigationBar;

public class About extends Activity {

	TextView tv;
	RelativeLayout myLayout;
	Uri configPath;

    @SuppressLint("SetJavaScriptEnabled")
	public void onCreate(Bundle savedInstanceState)
    {

    	
        super.onCreate(savedInstanceState);
        //meenal start
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myLayout = new RelativeLayout(this);
		NavigationBar nb = new NavigationBar(this);
		nb.setLeftBarButton("SfsuTnS List");
		//nb.setRightBarButton("",false);
		nb.setBarTitle("About Sfsu Trees and Shrubs ");
		
		
		Intent viewIntent = getIntent();		
   	 	configPath = viewIntent.getData(); 
		
		
		NavigationBar.NavigationBarListener nbl = new NavigationBar.NavigationBarListener() {
		    @Override
		    public void OnNavigationButtonClick(int which) {
		    	switch(which){
		    	case 0:
		    		
		    				//startActivity(new Intent(About.this, SNFCHerpList.class));
		    		
		    		Intent intent = new Intent();
		    		intent.setData(configPath);
	                intent.setClass(About.this, TnsTabList.class);
	                startActivity(intent);
		    		
		    		
		    		
	                
		    	break;
		    	default:
		    		break;
		    	}
		    }
		
		};
		
		nb.setNavigationBarListener(nbl);

		RelativeLayout.LayoutParams nbParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		nbParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
		myLayout.addView(nb,nbParams);
		
		  
		RelativeLayout.LayoutParams aboutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		aboutParam.addRule(RelativeLayout.ALIGN_TOP,-1);
		myLayout.addView(LayoutInflater.from(this).inflate(R.layout.about, null),aboutParam);
	//	myLayout.addView(webView,aboutParam);
		
		
		
			
		setContentView(myLayout);
		
		View aboutView = myLayout.getChildAt(1);
		
		WebView webView = (WebView)  aboutView.findViewById(R.id.webview);
        //webView.getSettings().setJavaScriptEnabled(true);
       // setContentView(webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/About.html");
	
        //meenal end
//		InputStream input;
//        try {
//        	AssetManager assetManager = getAssets();
//            input = assetManager.open("About.html");
//             
//             int size = input.available();
//             byte[] buffer = new byte[size];
//             input.read(buffer);
//             input.close();
// 
//             // byte buffer into a string
//             String text1 = new String(buffer);
//             tv = (TextView) findViewById(R.id.text);
//             tv.setText(text1);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
      
        
        

        //

        //tv.setText("Set Your Text to display here.");

    }

    
}
