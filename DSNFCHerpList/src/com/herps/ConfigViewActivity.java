package com.herps;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.herps.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ConfigViewActivity extends Activity {
	protected String[] mBlogPostTitles;
	private final String KEY_TITLE ="title";
	private ListView showConfigs;
	protected JSONObject jsonResponse =null;

	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_view);
		
		//JSONObject jsonResponse =null;
				Intent intent = getIntent();		
				String JSONData = intent.getStringExtra("JSONData");
				
				try {
					jsonResponse = new JSONObject(JSONData);
					
					JSONArray jsonPosts = jsonResponse.getJSONArray("configurations");
					
					mBlogPostTitles= new String[jsonPosts.length()];
					
					for(int i =0; i <jsonPosts.length();i++){
						JSONObject post = jsonPosts.getJSONObject(i);
						String title =post.getString(KEY_TITLE);
						title = Html.fromHtml(title).toString();
						mBlogPostTitles[i]= title;
						//blogPosts.add(blogPost);
					}
					
					showConfigs = (ListView) findViewById(R.id.listView1);
					
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mBlogPostTitles);
					showConfigs.setAdapter(adapter);
					
					
					showConfigs.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View v,int position, long id) {					
							
							try {
								JSONArray jsonPosts = jsonResponse.getJSONArray("configurations");
								JSONObject jsonPost = jsonPosts.getJSONObject(position);
								String blogUrl = jsonPost.getString("url");
								String photosetUrl=jsonPost.getString("photoset");
								
								System.out.println("I have got the url "+ blogUrl);
								System.out.println("I have got the photoset "+ photosetUrl);
								
								URL blogFeedURL;
								blogFeedURL = new URL(blogUrl);
								
								
								
								Intent downloadIntent = new Intent(v.getContext(),ConfigDownloader.class);
								downloadIntent.putExtra("PHOTOSET", photosetUrl);
								downloadIntent.setData(Uri.parse(blogUrl));
								startActivity(downloadIntent);
								
							
								
								
							} catch (JSONException e) {
							
								e.printStackTrace();
							}catch (MalformedURLException e) {
								
								e.printStackTrace();
							}
							
						}
					});
								
								
			            
					
					
					
				} catch (JSONException e) {
					
					Log.e("ConfigViewActivity","Exception caught" +e);
				}
				
				
				
				
				
				
				
				//mUrl =blogUri.toString();
				
			
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config_view, menu);
		return true;
	}
	
	
/*	@Override
    public void onBackPressed() {
	  
	  finish();
    }*/
	
	
	

}
