package com.herps;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String TAG = MainActivity.class.getSimpleName();
	static File testdirectory;
	protected JSONObject mBlogData;
	protected ProgressBar mProgressBar;
	String filename;
	protected String[] mBlogPostTitles;
	private final String KEY_TITLE ="title";
	private Button button;
	//private final String KEY_AUTHOR = "id";
	
	@Override
	public void onCreate( Bundle savedInstanceState ) {
		
		
		
		
		
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main); 
			//mProgressBar = (ProgressBar)findViewById(R.id.progressBar1);
			
			
			
			System.out.println("Starting main activity");
			
			String title ="SFSU Trees and Shrubs";
			String description ="This application enables you to :";
			String know ="";
			String find ="Find plants growing in SFSU,";
			String observe ="Record and Share Observations of plants.";
			String click = "View the Plant Categories for Download";
			
			//URL blogFeedURL = new URL("http://thecity.sfsu.edu/~elizajoe/ShrubsTest.txt");
			//HttpURLConnection connection = (HttpURLConnection) blogFeedURL.openConnection();
			//connection.connect();
			
			
			TextView intro = (TextView) findViewById(R.id.textView1);
			intro.setText(Html.fromHtml( "<br>"+find+"<br>"+observe));
			
			
				
			
			button = (Button) findViewById(R.id.button1);
			
			button.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					if (isNetworkAvailable()){
						//mProgressBar.setVisibility(View.VISIBLE);
						System.out.println("network is available NJ");
						GetShrubsConfigs shrubsData =new GetShrubsConfigs();					
						shrubsData.execute();
					}
					
				}
			});
			
			
			
			
			//String path = getSelection();
			System.out.println("I am here" );
			
			//download configs one xml file to configure screen layout and one downloadableconfig,jav file to perform downloads
			//set current config config one xml file to configure screen laout and setcurrentconfig.xml
			
			
		
			/*InputStream inputStream = getResources().openRawResource(R.raw.snfcherps);
			System.out.println("the value of testdirectory is " + testdirectory);*/
			
			//File fileIn  = new File("/data/data/com.example.modelviewcontroller/files/CTNSShrubsTestNJ.txt");
			//FileInputStream streamIn   = new FileInputStream(fileIn);
			
			//final MyModel myModel = new MyModel(inputStream);
			/*final MyModel myModel = new MyModel(streamIn);
			final MyView myView = new MyView(this);
			MyController myController = new MyController(this, myModel, myView);
			

			/*ViewGroup container = (ViewGroup) findViewById(R.id.myLayout);
			container.addView(myView);
			container.addView(myController);  */
			
		}
		catch (Exception e) {
			Log.e("ERROR", "ERROR IN CODE: " + e.toString());
			System.out.println("this is the errorNJ");
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
	}
	
	



	private boolean isNetworkAvailable() {
	
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		boolean isAvailable = false;
		if (networkInfo != null && networkInfo.isConnected()){
			isAvailable = true;
		}
		else {
			Toast.makeText(this,"Network unavailable", Toast.LENGTH_LONG).show();
		}
		return isAvailable;
		
		}
	
	private void logException(Exception e) {
		Log.e(TAG,"Exception caught" +e);
	}
	

	public void handleBlogResponse() {
		if(mBlogData==null){
			updateDisplayForError();
		}
		
		else{
			try {
				
				Intent intent = new Intent(this, ConfigViewActivity.class);
				intent.putExtra("JSONData", mBlogData.toString());
				startActivity(intent);
				
				
				/*
				JSONArray jsonPosts = mBlogData.getJSONArray("configurations");
				//ArrayList<HashMap<String,String>> blogPosts = new ArrayList<HashMap<String,String>>();
				mBlogPostTitles= new String[jsonPosts.length()];
				for(int i =0; i <jsonPosts.length();i++){
					JSONObject post = jsonPosts.getJSONObject(i);
					String title =post.getString(KEY_TITLE);
					title = Html.fromHtml(title).toString();
					mBlogPostTitles[i]= title;
					//blogPosts.add(blogPost);
				}
				
				//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mBlogPostTitles);
				
				//String[] keys ={KEY_TITLE,KEY_AUTHOR};
				//int[] ids ={android.R.id.text1,android.R.id.text2};
				
				//SimpleAdapter adapter = new SimpleAdapter(this, blogPosts,android.R.layout.simple_list_item_2, keys, ids);
				
				//setListAdapter(adapter);*/
				
			} catch (Exception e) {
				logException(e);
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
	}
	
	
	
	
	
	
	
	
	
	private class GetShrubsConfigs extends AsyncTask<Object, Void, JSONObject>{

		@Override
		protected JSONObject doInBackground(Object... arg0) {
			
			int responseCode = -1;
			int count;
			JSONObject jsonResponse = null;
			
			try {
				//URL blogFeedURL = new URL("http://blog.teamtreehouse.com/api/get_recent_summary/?count=" + NUMBER_OF_POSTS);
				URL blogFeedURL = new URL("http://thecity.sfsu.edu/~elizajoe/Android/Config_list.json");
					
					
					HttpURLConnection connection = (HttpURLConnection) blogFeedURL.openConnection();
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
					logException(e);
				} catch (IOException e) {
					logException(e);
					
				}
				catch (Exception e){
					logException(e);
				}
			
			
			
			
			
			return jsonResponse;
		}
		
		@Override
		protected void onPostExecute(JSONObject result){
			mBlogData = result;
			handleBlogResponse();
		}

		
	}



	@Override
    public void onBackPressed() {
	  
	   Intent intent = new Intent(Intent.ACTION_MAIN);
	   intent.addCategory(Intent.CATEGORY_HOME);
	   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	   startActivity(intent);
    }

	   @Override
	protected void onResume() {
		
		   int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		   
		   if (resultCode == ConnectionResult.SUCCESS) 
			{
			   
			   super.onResume();
			}
			   else{
					if (resultCode == ConnectionResult.SERVICE_MISSING ||
					    resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED ||
					    resultCode == ConnectionResult.SERVICE_DISABLED){
						Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,this , 1);
						dialog.show();
					}
				}
		   
		
	}



		
	
		
		
		
}
