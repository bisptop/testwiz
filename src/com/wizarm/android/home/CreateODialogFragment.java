
package com.wizarm.android.home;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class CreateODialogFragment extends DialogFragment  {

private static final String TAG = "WizarmTV";


        @Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
        	Log.e(TAG, "startWallpaper ___________________________________________00000 ");
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");  
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");  
 	Log.e(TAG, "startWallpaper ___________________________________________00000 "); 
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");  
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");  
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");  
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");  	
        	Log.e(TAG, "startWallpaper ___________________________________________00000 ");
        	
        String title = getArguments().getString("title");
     //   View view = createView(getActivity().getLayoutInflater(), (ViewGroup) getView());
		return null;
       }

        @Override
 public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
{
        	Log.e(TAG, "startWallpaper ___________________________________________00000 ");
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");  
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");  
 	Log.e(TAG, "startWallpaper ___________________________________________00000 "); 
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");  
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");  
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");  
 	Log.e(TAG, "startWallpaper ___________________________________________00000 ");  	
        	Log.e(TAG, "startWallpaper ___________________________________________00000 ");
	 Log.e(TAG, "startWallpaper ___________________________________________11111 ");
View v=inflater.inflate(R.layout.overlaysetting,container,false);

return v;
}


public void onClick(DialogInterface arg0, int arg1) {
	// TODO Auto-generated method stub
	
}


}
