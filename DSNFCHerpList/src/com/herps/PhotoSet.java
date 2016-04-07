package com.herps;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.herps.R;





import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class PhotoSet extends Activity {
	
	String pathway;
	public static final String TAG = PhotoSet.class.getSimpleName();
	URL photosUrl;
	protected JSONObject mBlogData;
	static protected String[] mBlogPostTitles;
	
	//protected ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_set);
		//mProgressBar = (ProgressBar)findViewById(R.id.progressBar1);
		
		System.out.println("I am in photoset");
		try {
		Intent photosetIntent = getIntent();
		 Uri photosetUrl = photosetIntent.getData();
		String photoset =photosetUrl.toString();
		
		
			photosUrl = new URL(photoset);
			Bundle extras= photosetIntent.getExtras();
			pathway = extras.getString("PATHWAY");
			
			
			
			
			//mProgressBar.setVisibility(View.VISIBLE);
			GetPhotoSet getPhotoSet = new GetPhotoSet();
			getPhotoSet.execute();
			
			
		
			
		//	System.out.println("in main activity "+ mBlogPostTitles[0]);
			//System.out.println("in oncreate"+pathway);
			
			//
			
			
			
			
			
			
		
		} catch (MalformedURLException e) {
			
			Log.e(TAG,"Exception caught" +e);
		}
		
		
	}
	
	
	
	public void handleBlogResponse() {
		//mProgressBar.setVisibility(View.INVISIBLE);
		if(mBlogData==null){
			updateDisplayForError();
		}
		else{
			try {
				JSONArray jsonPosts = mBlogData.getJSONArray("images");
				
				mBlogPostTitles= new String[jsonPosts.length()];					
				
				
				for(int i =0; i <jsonPosts.length();i++){
					JSONObject post = jsonPosts.getJSONObject(i);
					String title =post.getString("url");
					title = Html.fromHtml(title).toString();
					mBlogPostTitles[i]= title;
					//blogPosts.add(blogPost);
					
					
				}
				System.out.println("in handleresponse");
				for(int j =0;j<jsonPosts.length();j++){
					System.out.println("value of mblogposttitle");
					System.out.println("The images are photoset:"+mBlogPostTitles[j] );
				}
				
				
				Intent photodownloadIntent = new Intent(this,PhotoDownload.class);
				photodownloadIntent.putExtra("PATHWAY", pathway);
				photodownloadIntent.putExtra("PHOTOURL", mBlogPostTitles);
				
				startActivity(photodownloadIntent);
				
				
			} catch (JSONException e) {
				Log.e(TAG,"Exception caught" +e);
			}  
		}
	}
	
	private void updateDisplayForError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.error_title));
		builder.setMessage(getString(R.string.error_message));
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
		
		
		//TextView emptyTextView = (TextView) getListView().getEmptyView();
		//emptyTextView.setText(getString(R.string.no_items));
	}
	
	
	
	class GetPhotoSet extends AsyncTask<Object, Void, JSONObject>{

		@Override
		protected JSONObject doInBackground(Object... params) {
			int responseCode = -1;
			int count;
			JSONObject jsonResponse = null;
			
			try {
			//URL blogFeedURL = new URL("http://blog.teamtreehouse.com/api/get_recent_summary/?count=" + NUMBER_OF_POSTS);
			//URL blogFeedURL = new URL("http://thecity.sfsu.edu/~elizajoe/Android/Config_list.json");
				
				
				HttpURLConnection connection = (HttpURLConnection) ((photosUrl).openConnection());
				connection.connect();				
				 responseCode = connection.getResponseCode();
				 
				 if (responseCode == HttpURLConnection.HTTP_OK){
					 InputStream inputstream = connection.getInputStream();
					 Reader reader = new InputStreamReader(inputstream);
					 				
					int contentLength = connection.getContentLength();
					char[] charArray = new char[contentLength];
					reader.read(charArray);
					String responseData = new String(charArray);
					Log.v(TAG,responseData);
					
					jsonResponse = new JSONObject(responseData);
				 }
				 else{
					 Log.i(TAG, "Unsuccessful HTTP Response Code: " + responseCode);
				 }
				 				 
				
			} catch (MalformedURLException e) {
				Log.e(TAG,"Exception caught" +e);
			} catch (IOException e) {
				Log.e(TAG,"Exception caught" +e);
				
			}
			catch (Exception e){
				Log.e(TAG,"Exception caught" +e);
			}
			return jsonResponse;
		}
		
		@Override
		protected void onPostExecute(JSONObject result){
			mBlogData = result;
			handleBlogResponse();
			System.out.println("Im outta handleresponse");
			
			
			
		}

		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_set, menu);
		return true;
	}

}
