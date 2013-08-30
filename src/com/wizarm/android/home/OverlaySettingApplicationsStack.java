package com.wizarm.android.home;


import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position,
		long arg3) {
	Toast.makeText(OverlaySettingApplicationsStack.this, "This is Toast!!!", Toast.LENGTH_SHORT).show();
	Toast.makeText(OverlaySettingApplicationsStack.this, "This is Toast!!!", Toast.LENGTH_SHORT).show();
	
    // Show remove dialog
    final int pos = position;
    List<FileItem> files;
    files=filesAdapter.getfileItem();
    String name = files.get(position).NAME;
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Are you sure you want to delete '" + name + "'?")
           .setCancelable(true)
           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                    // Remove from db
                       filesAdapter.removeItem(pos);
               }
           })
           .setNegativeButton("No", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
               }
           });
    AlertDialog alert = builder.create();
    alert.show();

	return false;
}

@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
	   final int pos = position;
	    List<FileItem> files;
	    files=filesAdapter.getfileItem();
	    
    String opackage = files.get(position).PACKAGE;
    String mime ="hi me"; //((FilesAdapter.FileItem) filesAdapter.getItem(position)).MIME;
    String uri = "/home/";//Uri.fromFile(new File(path));

  //  Intent intent = new Intent(Intent.ACTION_VIEW);
  //  intent.setDataAndType(uri, mime);
    


  //  Log.d(TAG, mime + " " + path + " " + uri);
 /*   try {
            startActivity(intent);
    } catch( ActivityNotFoundException e ) {
            e.printStackTrace();
    }*/
    // DialogFragment.show() will take care of adding the fragment
    // in a transaction.  We also want to remove any currently showing
    // dialog, so make our own transaction and take care of that here.
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
    if (prev != null) {
        ft.remove(prev);
    }
    ft.addToBackStack(null);
    
    int mStackLevel;
	DialogFragment newFragment = CreateFileDialogFragment.newInstance("Hi title","1.txt","/home/","mpeg");
	newFragment.show(ft,"dialog");

	
}






}
