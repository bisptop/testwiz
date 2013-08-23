package com.wizarm.android.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;





public class OverlaySettingApplicationsStack extends ViewGroup implements View.OnClickListener
{

private static final String LOG_TAG = "WizarmTV";
private LayoutInflater minflater;
ViewGroup view_group;

View curView;




public OverlaySettingApplicationsStack(Context context) {
        super(context);
        initLayout();

}
public OverlaySettingApplicationsStack(Context context, AttributeSet attrs) {
		
	super(context, attrs);
    initLayout();
}

void initLayout() {
minflater = LayoutInflater.from(getContext());
FrameLayout mainlayout= (FrameLayout)findViewById(R.id.mainLayout);

//LinearLayout ovsetting=(LinearLayout) findViewById(R.layout.overlaysetting);
LinearLayout ovsetting=(LinearLayout) minflater.inflate(R.layout.overlayset,mainlayout,false);
addView(ovsetting);
//LinearLayout ovsetting=(LinearLayout) findViewById(R.layout.overlayset);
//addView(ovsetting);
//ovsetting.setVisibility(View.VISIBLE);
//Only initalize onclick lisners

//GridView gridview = (GridView) findViewById(R.id.gridview);

Button Buttonview = (Button) findViewById(R.id.mainbut);
//Button Buttonview = (Button) findViewById(R.id.settingbutton);
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting "+Buttonview);
Buttonview.setOnClickListener(this);






Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
Log.e(LOG_TAG, "_FDK______  onCreateOptionsMenuforOverlaySetting ");
//curView=mInflater.inflate(R.layout.overlaysetting,null);
//addView(curView);
Drawable mBackground = getBackground();
setBackgroundDrawable(null);
setWillNotDraw(false);


}


public void setView(int v){


}




@Override
protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
        // TODO Auto-generated method stub

}



@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");
       Log.e(LOG_TAG, "_FDK______  ONCLICK ");


	
}




}
