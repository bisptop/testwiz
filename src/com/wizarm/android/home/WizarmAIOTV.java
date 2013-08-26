/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wizarm.android.home;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import com.wizarm.android.home.OverlaySettingApplicationsStack;


public class WizarmAIOTV extends Activity {
    /**
     * Tag used for logging errors.
     */
    private static final String LOG_TAG = "WizarmTV";

    /**
     * Keys during freeze/thaw.
     */
    
    private static final String KEY_SAVE_GRID_OPENED = "grid.opened";

    private static final String DEFAULT_FAVORITES_PATH = "/data/wizarm/wizfavorites.xml";

    private static final String TAG_FAVORITES = "favorites";
    private static final String TAG_FAVORITE = "item";
    private static final String TAG_PACKAGE = "package";
    private static final String TAG_ICON = "icon";
    private static final String TAG_FUNC = "funccase";    

    // Identifiers for option menu items
    private static final int MENU_WALLPAPER_SETTINGS = Menu.FIRST + 1;
    private static final int MENU_SEARCH = MENU_WALLPAPER_SETTINGS + 1;
    private static final int MENU_SETTINGS = MENU_SEARCH + 1;

    
    private OverlaySettingApplicationsStack OApplicationsStack;
    
    /**
     * Maximum number of recent tasks to query.
     */
    private static final int MAX_RECENT_TASKS = 20;

    private static boolean mWallpaperChecked;
    private static ArrayList<ApplicationInfo> mApplications;
    private static LinkedList<ApplicationInfo> mFavorites;


    private final BroadcastReceiver mApplicationsReceiver = new ApplicationsIntentReceiver();

    private GridView mGrid;
    private LinearLayout main; 

    private LayoutAnimationController mShowLayoutAnimation;
    private LayoutAnimationController mHideLayoutAnimation;

    private boolean mBlockAnimation;

    private boolean mHomeDown;
    private boolean mBackDown;
    
    private View mShowApplications;
    private CheckBox mShowApplicationsCheck;



    private Animation mGridEntry;
    private Animation mGridExit;
	

    protected Intent Ointent;
    protected Context thiscontext;
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        setContentView(R.layout.home);
        main=(LinearLayout) findViewById(R.id.wizhome);

        thiscontext=this;
        
        setDefaultWallpaper();

        loadApplications(true);

        bindApplications();

        bindRecents();
        bindButtons();

        
        
        mGridEntry = AnimationUtils.loadAnimation(this, R.anim.grid_entry);
        mGridExit = AnimationUtils.loadAnimation(this, R.anim.grid_exit);
       
        Ointent =new Intent(this, OverlaySettingApplicationsStack.class);

;
	// fahad we need the icons at startup
	showApplications(true);
 
        //REQUIRES ROOT PERMISSION TO INVESTIGATE fahad 
        String ProcID = "42";
        Process proc = null;
        String command;
        command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib service call activity 42 s16 com.android.systemui";
        
        try {
			proc = Runtime.getRuntime().exec(new String[] { "su", "-c", "busybox killall com.android.systemui" });
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        
		try {
			String[] envp = null;
			proc = Runtime.getRuntime().exec(new String[] { "su", "-c", "export LD_LIBRARY_PATH=/vendor/lib:/system/lib" , "service call activity "+ProcID+" s16 com.android.systemui" });

		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} //WAS 79
        try {
			proc.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

                try {
                	 OutputStreamWriter osw = null;
                        proc = Runtime.getRuntime().exec(new String[]{"su","-c","service call activity "+ ProcID +" s16 com.android.systemui"});
                     
                        osw = new OutputStreamWriter(proc.getOutputStream());
                        osw.write("/system/bin/service call activity 42 s16 com.android.systemui"); 
                        osw.flush();
                        osw.close();
                        
                        
                        
                        
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                } //WAS 79
            try {
                        proc.waitFor();
                } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                }
            
           // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Close the menu
        if (Intent.ACTION_MAIN.equals(intent.getAction())) {
            getWindow().closeAllPanels();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Remove the callback for the cached drawables or we leak
        // the previous Home screen on orientation change
        final int count = mApplications.size();
        for (int i = 0; i < count; i++) {
            mApplications.get(i).icon.setCallback(null);
        }


        unregisterReceiver(mApplicationsReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindRecents();
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        final boolean opened = state.getBoolean(KEY_SAVE_GRID_OPENED, false);
        if (opened) {
            showApplications(false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_SAVE_GRID_OPENED, mGrid.getVisibility() == View.VISIBLE);
    }


    /**
     * Creates a new appplications adapter for the grid view and registers it.
     */
    private void bindApplications() {
        if (mGrid == null) {
            mGrid = (GridView) findViewById(R.id.all_apps);
        }
        mGrid.setAdapter(new ApplicationsAdapter(this, mApplications));
        mGrid.setSelection(0);


    }

    /**
     * Binds actions to the various buttons.
     */
    private void bindButtons() {
        mShowApplications = findViewById(R.id.show_all_apps);
        mShowApplications.setOnClickListener(new ShowApplications());
        mShowApplicationsCheck = (CheckBox) findViewById(R.id.show_all_apps_check);

        mGrid.setOnItemClickListener(new ApplicationLauncher());
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * When no wallpaper was manually set, a default wallpaper is used instead.
     */



	private SurfaceView surface_view;	
	public Camera mCamera;

	int mNumberOfCameras;
   	int mCurrentCamera;  // Camera ID currently chosen
    int mCameraCurrentlyLocked;  // Camera ID that's actually acquired
	    // The first rear facing camera
	int mDefaultCameraId;
	SurfaceHolder.Callback sh_ob = null;
    SurfaceHolder surface_holder        = null;
    SurfaceHolder.Callback sh_callback  = null;
    private CSurfaceView csurface=null;
	

    private void setDefaultWallpaper() {

    	// HDMI TV View is our default wallpaper
    	
        // Find the total number of cameras available
        mNumberOfCameras = Camera.getNumberOfCameras();

        // Find the ID of the rear-facing ("default") camera
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < mNumberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
                mCurrentCamera = mDefaultCameraId = i;
            }
        }

        Log.e(LOG_TAG, "Camera init " + mNumberOfCameras);
        Log.e(LOG_TAG, "Camera init 0-----------------------000 FIN 1" + mCamera);

		mCurrentCamera=0;
      /* Temp probs with hdmi */
        csurface=(CSurfaceView)findViewById(R.id.surface);
        surface_holder=csurface.getHolder();
        surface_holder.addCallback(csurface);
        surface_holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        Log.e(LOG_TAG, "Camera init 0-----------------------000 FIN" + mNumberOfCameras);
    }







////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Refreshes the favorite applications stacked over the all apps button.
     * The number of favorites depends on the user.
     */
    
    public static class XMLformat {
    	String id;
    	String icon;
    	String pack;
    	String funcase;
	public XMLformat(String id,String icon,String pack,String funcase)
	{
	this.id=id;
	this.icon=icon;
	this.pack=pack;
	this.funcase=funcase;
	}

    };
    
  
    
    

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Refreshes the recently launched applications stacked over the favorites. The number
     * of recents depends on how many favorites are present.
     */
    private void bindRecents() {
        final PackageManager manager = getPackageManager();
        final ActivityManager tasksManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        final List<ActivityManager.RecentTaskInfo> recentTasks = tasksManager.getRecentTasks(
                MAX_RECENT_TASKS, 0);

        final int count = recentTasks.size();
        final ArrayList<ApplicationInfo> recents = new ArrayList<ApplicationInfo>();

        for (int i = count - 1; i >= 0; i--) {
            final Intent intent = recentTasks.get(i).baseIntent;

            if (Intent.ACTION_MAIN.equals(intent.getAction()) &&
                    !intent.hasCategory(Intent.CATEGORY_HOME)) {

                ApplicationInfo info = getApplicationInfo(manager, intent);
                if (info != null) {
                    info.intent = intent;
                    if (!mFavorites.contains(info)) {
                        recents.add(info);
                    }
                }
            }
        }


    }

    private static ApplicationInfo getApplicationInfo(PackageManager manager, Intent intent) {
        final ResolveInfo resolveInfo = manager.resolveActivity(intent, 0);

        if (resolveInfo == null) {
            return null;
        }

        final ApplicationInfo info = new ApplicationInfo();
        final ActivityInfo activityInfo = resolveInfo.activityInfo;
        info.icon = activityInfo.loadIcon(manager);
        if (info.title == null || info.title.length() == 0) {
            info.title = activityInfo.loadLabel(manager);
        }
        if (info.title == null) {
            info.title = "";
        }
        return info;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            mBackDown = mHomeDown = false;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    mBackDown = true;
                    return true;
                case KeyEvent.KEYCODE_HOME:
                    mHomeDown = true;
                    return true;
            }
        } else if (event.getAction() == KeyEvent.ACTION_UP) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    if (!event.isCanceled()) {
                        // Do BACK behavior.
                    }
                    mBackDown = true;
                    return true;
                case KeyEvent.KEYCODE_HOME:
                    if (!event.isCanceled()) {
                        // Do HOME behavior.
                    }
                    mHomeDown = true;
                    return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, MENU_WALLPAPER_SETTINGS, 0, R.string.menu_wallpaper)
                 .setIcon(android.R.drawable.ic_menu_gallery)
                 .setAlphabeticShortcut('i');
        menu.add(0, MENU_SEARCH, 0, R.string.menu_search)
                .setIcon(android.R.drawable.ic_search_category_default)
                .setAlphabeticShortcut(SearchManager.MENU_KEY);
        menu.add(0, MENU_SETTINGS, 0, R.string.menu_settings)
               .setIcon(android.R.drawable.ic_menu_preferences)
            .setIntent(new Intent(android.provider.Settings.ACTION_SETTINGS));
        
        // overlay setting
      //  onCreateOptionsMenuforOverlaySetting(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_WALLPAPER_SETTINGS:
                startWallpaper();
                return true;
            case MENU_SEARCH:
                onSearchRequested();
                return true;
        }
        
        // fahad overlay setting
     //   onOptionsItemSelectedOverlaySetting(item);
        
        
        return super.onOptionsItemSelected(item);
    }

    private void startWallpaper() {
        final Intent pickWallpaper = new Intent(Intent.ACTION_SET_WALLPAPER);
        startActivity(Intent.createChooser(pickWallpaper, getString(R.string.menu_wallpaper)));
	  Log.e("Wallpaper", "startWallpaper ___________________________________________00000 ");

  }

    /**
     * Loads the list of installed applications in mApplications.
     */
    private void loadApplications(boolean isLaunching) {
        if (isLaunching && mApplications != null) {
            return;
        }

        PackageManager manager = getPackageManager();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

// fahad since we dont want the application from the launcher but we would need to check weather the applications we have in our launcher 
// is removed or uninstalled. so we keep the list
        final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
    //    Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));


        if (apps != null) {
            final int count = apps.size();
	    final int overlaycount = 12 ;

            if (mApplications == null) {
                mApplications = new ArrayList<ApplicationInfo>(count);
            }
            mApplications.clear();

            for (int i = 0; i < overlaycount; i++) {
                ApplicationInfo application = new ApplicationInfo();
                ResolveInfo info = apps.get(i);

                //	application.title = info.loadLabel(manager);
/*
                application.setActivity(new ComponentName(
                        info.activityInfo.applicationInfo.packageName,
                        info.activityInfo.name),
                        Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
*/

			application.funccase=-1; // No operation
// fahad that works 
		switch(i){

		case 0:
		        application.title="Watch";	
			application.icon=getResources().getDrawable(R.drawable.icon_watch); // since we dont have external application to launch we dont intialize it	
			application.funccase=1;  // index to function to call
			break;


		case 1:
		        application.title="Scale";	
			application.icon=getResources().getDrawable(R.drawable.icon_scale); // since we dont have external application to launch we dont intialize it	
			application.funccase=2;  // index to function to call
			break;


		case 2:
                        application.title="User settings";

			application.setActivity(new ComponentName(
                                         "com.android.settings",
                                         "com.android.settings.Settings"),
                                          Intent.FLAG_ACTIVITY_NEW_TASK
                                         | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

                        application.icon=getResources().getDrawable(R.drawable.icon_usettings); // since we dont have external application to launch we dont intialize it   
                        application.funccase=0;  // index to function to call
                        break;

		case 3:
			application.title = "Overlay Settings";
			application.icon=getResources().getDrawable(R.drawable.icon_osettings);
                        application.funccase=3;  // index to function to call
                        break;

		case 5:
                	application.title = "Browse Android";
               		application.setActivity(new ComponentName(
                       			 "com.android.launcher",
                       			 "com.android.launcher2.Launcher"),
                      			  Intent.FLAG_ACTIVITY_NEW_TASK
                       			 | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

                    Log.e(LOG_TAG, "Initizlizeing start activity " + 
                        info.activityInfo.applicationInfo.packageName +
                        info.activityInfo.name+  info.activityInfo.loadIcon(manager));

                    application.icon =getResources().getDrawable(R.drawable.icon_android);                 // android.R.drawable.iconchat // info.activityInfo.loadIcon(manager);
		//application.icon = info.activityInfo.loadIcon(manager);
			application.funccase=0;  // index to function to call
		break;


		case 6:

			// TO-DO func get first custome menu getmenu(1);
			// check item installed ? else blank menu


		break;


                case 7:
                        // TO-DO func get first custome menu getmenu(1);
			// check item installed ?


                break;





                case 4:
                case 8:

               		ApplicationInfo applicationa = new ApplicationInfo();
			ResolveInfo infofx=apps.get(0);  // bad initailization
			String a="Aurora";

			if(i==4)
			{
				a="XBMC";
			}
	
		for (int j = 0; j < count; j++) {
			applicationa.title = apps.get(j).loadLabel(manager);
                    Log.e(LOG_TAG, "HI FDK___ 00000 " + applicationa.title);
			if(applicationa.title.toString().compareTo(a)==0){
				infofx=apps.get(j);	
				application.funccase=0;
                   // Log.e(LOG_TAG, "HI FDK___ this are the appss " + applicationa.title);
			}
		
		}

			/* TO-DO Check if particular application is present else launch normal browser */
                    application.title = "Browse firefox";
                    application.icon =getResources().getDrawable(R.drawable.icon_firefox);                 // android.R.drawable.iconchat // info.activityInfo.loadIcon(manager);

			if(i==4)
                        {
                   		 application.icon =getResources().getDrawable(R.drawable.icon_xbmc);
                    		 application.title = "Play Movies";
                        }

                        application.setActivity(new ComponentName(
	                        infofx.activityInfo.applicationInfo.packageName,
                        infofx.activityInfo.name),

                             //            "Aurora",
                               //          "App"),
                                  //       "org.mozilla.firefox_beta",
                                    //     "org.mozilla.firefox_beta.App"),
                                          Intent.FLAG_ACTIVITY_NEW_TASK
                                         | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

                //application.icon = info.activityInfo.loadIcon(manager);
                        application.funccase=0;  // index to function to call
                break;


	
	default:
		 application.funccase=-1;

		break;
	}// finish switch case
    	            mApplications.add(application);
            }
        }

    }

    /**
     * Shows all of the applications by playing an animation on the grid.
     */
    private void showApplications(boolean animate) {
        if (mBlockAnimation) {
            return;
        }
        mBlockAnimation = true;

        mShowApplicationsCheck.toggle();

        if (mShowLayoutAnimation == null) {
            mShowLayoutAnimation = AnimationUtils.loadLayoutAnimation(
                    this, R.anim.show_applications);
        }

        // This enables a layout animation; if you uncomment this code, you need to
        // comment the line mGrid.startAnimation() below
//        mGrid.setLayoutAnimationListener(new ShowGrid());
 //       mGrid.setLayoutAnimation(mShowLayoutAnimation);
  //      mGrid.startLayoutAnimation();

        if (animate) {
            mGridEntry.setAnimationListener(new ShowGrid());
	      mGrid.startAnimation(mGridEntry);
        }

        mGrid.setVisibility(View.VISIBLE);
        mShowApplications.bringToFront();
        mShowApplications.setVisibility(View.VISIBLE);

        if (!animate) {
            mBlockAnimation = false;
        }

        // ViewDebug.startHierarchyTracing("Home", mGrid);
    }


    /**
     * Hides all of the applications by playing an animation on the grid.
     */
    private void hideApplications() {
        if (mBlockAnimation) {
            return;
        }
        mBlockAnimation = true;

        mShowApplicationsCheck.toggle();

        if (mHideLayoutAnimation == null) {
            mHideLayoutAnimation = AnimationUtils.loadLayoutAnimation(
                    this, R.anim.hide_applications);
        }

        mGridExit.setAnimationListener(new HideGrid());
        mGrid.startAnimation(mGridExit);
        mGrid.setVisibility(View.INVISIBLE);
        mShowApplications.requestFocus();
        // This enables a layout animation; if you uncomment this code, you need to
        // comment the line mGrid.startAnimation() above
//        mGrid.setLayoutAnimationListener(new HideGrid());
//        mGrid.setLayoutAnimation(mHideLayoutAnimation);
//        mGrid.startLayoutAnimation();
    }



    private void HideOverlayapp() {
    	if(main.getVisibility() == View.VISIBLE);
    		main.setVisibility(View.GONE);
		
    }

    private void ShowOverlayapp() {
    	
    	if(main.getVisibility() == View.GONE);{
    		main.setVisibility(View.VISIBLE);
		main.bringToFront();
		main.requestLayout();
//    		showApplications(false);
    	}
		
    }


            @Override
        public boolean onTouchEvent(MotionEvent event){

		if((event.getDownTime() > 300000 )){
   	  	if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {

   	  			ShowOverlayapp();

                }
		}

                return super.onTouchEvent(event);

        }

    /**
     * Receives notifications when applications are added/removed.
     */
    private class ApplicationsIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadApplications(false);
            bindApplications();
            bindRecents();
        }
    }

    /**
     * GridView adapter to show the list of all installed applications.
     */
    private class ApplicationsAdapter extends ArrayAdapter<ApplicationInfo> {
        private Rect mOldBounds = new Rect();

        public ApplicationsAdapter(Context context, ArrayList<ApplicationInfo> apps) {
            super(context, 0, apps);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ApplicationInfo info = mApplications.get(position);

            if (convertView == null) {
                final LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.application, parent, false);
            }

            Drawable icon = info.icon;

            if (!info.filtered) {
                final Resources resources = getContext().getResources();
                int width = 500;//(int) resources.getDimension(android.R.dimen.app_icon_size);
                int height = 500;//(int) resources.getDimension(android.R.dimen.app_icon_size);

                final int iconWidth=300;//icon.getIntrinsicWidth();
                final int iconHeight=300;//icon.getIntrinsicHeight();

                if (icon instanceof PaintDrawable) {
                    PaintDrawable painter = (PaintDrawable) icon;
                    painter.setIntrinsicWidth(width);
                    painter.setIntrinsicHeight(height);
                }

                if (width > 0 && height > 0 && (width < iconWidth || height < iconHeight)) {
                    final float ratio = (float) iconWidth / iconHeight;

                    if (iconWidth > iconHeight) {
                        height = (int) (width / ratio);
                    } else if (iconHeight > iconWidth) {
                        width = (int) (height * ratio);
                    }

                    final Bitmap.Config c =
                            icon.getOpacity() != PixelFormat.OPAQUE ?
                                Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
                    final Bitmap thumb = Bitmap.createBitmap(width, height, c);
                    final Canvas canvas = new Canvas(thumb);
                    canvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG, 0));
                    // Copy the old bounds to restore them later
                    // If we were to do oldBounds = icon.getBounds(),
                    // the call to setBounds() that follows would
                    // change the same instance and we would lose the
                    // old bounds
               //    mOldBounds.set(icon.getBounds());
                    icon.setBounds(0, 0, width, height);
                    icon.draw(canvas);
                 //   icon.setBounds(mOldBounds);
                    icon = info.icon = new BitmapDrawable(thumb);
                    info.filtered = true;
                }
            }

            final TextView textView = (TextView) convertView.findViewById(R.id.label);
            textView.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
            textView.setText(info.title);

	    Log.w(LOG_TAG," __FDK__ ONCREATE Overlay show activity");
	
            return convertView;
        }

    }

    /**
     * Shows and hides the applications grid view.
     */
    private class ShowApplications implements View.OnClickListener {
        public void onClick(View v) {
/*            if (mGrid.getVisibility() != View.VISIBLE) {
                showApplications(true);
            } else {
//                hideApplications();
            }*/
        }
    }

    /**
     * Hides the applications grid when the layout animation is over.
     */
    private class HideGrid implements Animation.AnimationListener {
        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            mBlockAnimation = false;
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /**
     * Shows the applications grid when the layout animation is over.
     */
    private class ShowGrid implements Animation.AnimationListener {
        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            mBlockAnimation = false;
            // ViewDebug.stopHierarchyTracing();
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /**
     * Starts the selected activity/application in the grid view.
     */
    private class ApplicationLauncher implements AdapterView.OnItemClickListener {
        /* (non-Javadoc)
         * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
         */
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            ApplicationInfo app = (ApplicationInfo) parent.getItemAtPosition(position);
            
            
	switch (app.funccase){
	case -1:
		// something wrong or not set get called
		break; 
	case 0:
	    // zero is for default app launching
            	startActivity(app.intent);
	    break;
	case 1:
     //        hideApplications();
             	HideOverlayapp();
	//	mShowApplicationsCheck.setVisibility(View.INVISIBLE); this works
	   break;
	case 3:
        	hideApplications();
        	
        	startActivityForResult(Ointent, 1111);
        	//startActivity(Ointent);

		break;

	default:

	   break;
        }
	
      }
}
    
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent result) {
      if (requestCode==1111 && resultCode==RESULT_OK) {
  //      mixer.setColor(result.getIntExtra(ColorMixerActivity.COLOR,
   //                                       mixer.getColor()));
    	  showApplications(true);
    	  
      }
      else {
        super.onActivityResult(requestCode, resultCode, result);
      }
    }

    
    





}
