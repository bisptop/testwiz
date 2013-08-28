package com.wizarm.android.home;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;





public class OverlaySettingApplicationsStack extends Activity implements OnItemClickListener,
OnItemLongClickListener
{
	
	
private FilesAdapter filesAdapter;	

private static final String LOG_TAG = "WizarmTV";

 @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);

    //getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
      //                   WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
   filesAdapter = new FilesAdapter(this);
    
    setContentView(R.layout.overlaysetting);

      GridView gridview = (GridView) findViewById(R.id.gridview);
      gridview.setAdapter(filesAdapter);
      gridview.setOnItemClickListener(this);
      gridview.setOnItemLongClickListener(this);
        
    
  }

@Override
public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
		long arg3) {
	Toast.makeText(OverlaySettingApplicationsStack.this, "This is Toast!!!", Toast.LENGTH_SHORT).show();

	return false;
}

@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	// TODO Auto-generated method stub
	Toast.makeText(OverlaySettingApplicationsStack.this, "This is Toast!!!", Toast.LENGTH_SHORT).show();

	
}






}
