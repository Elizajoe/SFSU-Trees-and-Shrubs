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

package com.herps.navigation;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.herps.R;

public class NavigationBar extends RelativeLayout implements OnClickListener{

	public static final int NAVIGATION_BUTTON_LEFT = 0;
	public static final int NAVIGATION_BUTTON_RIGHT = 1;

	private Context mContext;
	private NavigationBarListener mListener;
	private boolean displayCameraIcon = false;

	public NavigationBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		displayCameraIcon = false;
		init(context);
	}

	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		displayCameraIcon = false;
		init(context);
	}

	public NavigationBar(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context)
	{
		mContext = context;

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(lp);
		this.setBackgroundResource(R.drawable.navigation_bar_background);
	}

	public void setLeftBarButton(String title)
	{
		setButton(title,NAVIGATION_BUTTON_LEFT);
	}

	public void setRightBarButton(String title, boolean isCamera)
	{
		displayCameraIcon = isCamera;
		setButton(title,NAVIGATION_BUTTON_RIGHT);
		
	}

	private void setButton(String title, int which)
	{
		// remove the old button (if there is one)
		Button oldButton = (Button)this.findViewWithTag(new Integer(which));
		if(oldButton != null)
			this.removeView(oldButton);

		Button newButton = new Button(mContext);
		newButton.setTag(new Integer(which)); // used to determine which button is pressed and to remove old buttons

		// set OnClickListener
		newButton.setOnClickListener(this);

		// set LayoutParams
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-2,-2);
		if(which == NAVIGATION_BUTTON_LEFT)
			lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			
		else if (which == NAVIGATION_BUTTON_RIGHT){
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		//	newButton.setBackgroundResource(R.drawable.camera);
		}
		else
			throw new IllegalArgumentException("Parameter 'which' must be 0 or 1");
		lp.addRule(RelativeLayout.CENTER_VERTICAL);
		lp.setMargins(10, 0, 10, 0);
		newButton.setLayoutParams(lp);

		// set button text
		if(which == NAVIGATION_BUTTON_LEFT){
		newButton.setText(title);
		newButton.setTextSize(10);
		newButton.setTextColor(Color.WHITE);
		}
		
		// set button drawable
		if(which == NAVIGATION_BUTTON_LEFT)
		newButton.setBackgroundResource(R.drawable.navigation_bar_button);
			//newButton.setBackgroundResource(R.drawable.wood1);
		else{
			if(displayCameraIcon)
			newButton.setBackgroundResource(R.drawable.photos2 );
			else{
				newButton.setBackgroundResource(R.drawable.navigation_bar_button);
				newButton.setText(title);
				newButton.setTextSize(10);
				newButton.setTextColor(Color.WHITE);
			}
		}

		// add button
		this.addView(newButton);
	}

	public void setBarTitle(String title)
	{
		// remove old title (if exists)
		TextView oldTitle = (TextView)this.findViewWithTag("title");
		if(oldTitle != null)
			this.removeView(oldTitle);

		TextView newTitle = new TextView(mContext);
		newTitle.setTag("title");

		// set LayoutParams
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-2,-2);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);		
		lp.setMargins(0, 30, 0, 30);		
		newTitle.setLayoutParams(lp);

		// set text
		newTitle.setText(title);
		newTitle.setTextSize(14);
		newTitle.setTextColor(Color.WHITE);

		// add title to NavigationBar
		this.addView(newTitle);
	}

	public void setNavigationBarListener(NavigationBarListener listener)
	{
		mListener = listener;
	}

	@Override
	public void onClick(View v) {
		int which = ((Integer)v.getTag()).intValue();
		if(mListener != null)
		{
			mListener.OnNavigationButtonClick(which);
		}
	}

	/**
	 * Listener for NavigationBar.
	 */
	public interface NavigationBarListener {

		/**
		 * Called when the user presses either of the buttons on the NavigationBar.
		 *
		 * @param which - indicates which button was pressed, ie: NavigationBar.NAVIGATION_BUTTON_LEFT
		 */
		public void OnNavigationButtonClick(int which);
	}
}

