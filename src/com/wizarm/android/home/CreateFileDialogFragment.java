package com.wizarm.android.home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class CreateFileDialogFragment extends Activity implements DialogInterface.OnClickListener {
	private static final String TAG = "CreateFileDialogFragment";
	ContextThemeWrapper ctw;
	AlertDialog.Builder builder;
	private Gallery gallery;
	private static ArrayList<ApplicationInfo> mApplications;


	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
			setTheme(R.style.CustomTheme);
			
		       final boolean customTitleSupported = 
		                requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		   //    setTitleColor(#1a557c);
		       
	        setContentView(R.layout.create_link_dialog);

	        
	        // Title bar
	          getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
	                  R.layout.custom_title);


	        String name="Fahad";
	        String mime="Icon";
	        String path="/helloe/";
	        
	        
	        gallery = (Gallery) findViewById(R.id.gallery);
	         gallery.setAdapter(new AddImgAdp(this));
	        

			final EditText nameText = (EditText) findViewById(R.id.name);
			nameText.setText(name);
			
			//change icon based on mime type
			int icon = getIconForType(mime);
			
			ImageView image = (ImageView) findViewById(R.id.icon);
			image.setImageResource(icon);
			Log.d(TAG, "iconid"+icon);
			Log.d(TAG,image.getWidth()+" w -- h "+image.getHeight());
		//	getArguments().putInt("icon", icon);
			TextView uriText = (TextView) findViewById(R.id.uri);
			uriText.setText(path);
			TextView mimeText = (TextView) findViewById(R.id.mime);
			mimeText.setText("Type: "+mime);
			
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
				uriText.setTextColor(Color.WHITE);
				mimeText.setTextColor(Color.WHITE);
			}
			
			((ImageButton) findViewById(R.id.clear)).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					nameText.setText("");
					setTheme(R.style.Theme);
				}

			});
			
			nameText.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					if (s.toString().equals("")) {
					//	((AlertDialog)getDialog()).getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
					} else {
						//((AlertDialog)getDialog()).getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
					}
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
			});

	        
	 }
	


	 
	 public void setctw(ContextThemeWrapper setctw)
	 { 
		 ctw=setctw; 
	 }


    
	private int getIconForType(String mime) {
	//	return 0;
		return R.drawable.iconmailover;
		/*if (mime == null)
			return R.drawable.document;
		
		String[] type = mime.split("/");
		Log.d(TAG, type[0] + " == " + type[1]);
		if (type[0].equalsIgnoreCase("image")) {
			return R.drawable.image_any;
		} else if (type[0].equalsIgnoreCase("audio")) {
			return R.drawable.audio_any;
		} else if (type[0].equalsIgnoreCase("video")) {
			return R.drawable.video_any;
		} else if (type[0].equalsIgnoreCase("text")) {
			return R.drawable.text_any;
		} else if (type[0].equalsIgnoreCase("application")) {
			if (type[1].equalsIgnoreCase("pdf")) {
				return R.drawable.pdf;
			} else if (type[1].equalsIgnoreCase("zip") || type[1].equalsIgnoreCase("x-rar-compressed")) {
				return R.drawable.archive;
			} else if (type[1].equalsIgnoreCase("msword") || 
					type[1].equalsIgnoreCase("vnd.openxmlformats-officedocument.wordprocessingml.document") ||
					type[1].equalsIgnoreCase("vnd.oasis.opendocument.text")) {
				return R.drawable.word;
			} else if (type[1].equalsIgnoreCase("vnd.ms-excel") || 
					type[1].equalsIgnoreCase("vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
					type[1].equalsIgnoreCase("vnd.oasis.opendocument.spreadsheet")) {
				return R.drawable.spreadsheet;
			} else if (type[1].equalsIgnoreCase("vnd.ms-powerpoint") || 
					type[1].equalsIgnoreCase("vnd.openxmlformats-officedocument.presentationml.presentation") ||
					type[1].equalsIgnoreCase("vnd.oasis.opendocument.presentation")) {
				return R.drawable.presentation;
			} else {
				return R.drawable.application_any;
			}
		} else {
			return R.drawable.document;
		}
		*/
	}
	
	

	@Override
	public void onClick(DialogInterface dialog, int which) {
	}
	
	
    private void loadApplications(boolean isLaunching) {
    	
    	   PackageManager manager = getPackageManager();
    	   
           Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
           mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
           
           final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
           
           Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));

           if (apps != null) {
               final int count = apps.size();

               if (mApplications == null) {
                   mApplications = new ArrayList<ApplicationInfo>(count);
               }
               mApplications.clear();

               for (int i = 0; i < count; i++) {
                   ApplicationInfo application = new ApplicationInfo();
                   ResolveInfo info = apps.get(i);

                   application.title = info.loadLabel(manager);
                   application.setActivity(new ComponentName(
                           info.activityInfo.applicationInfo.packageName,
                           info.activityInfo.name),
                           Intent.FLAG_ACTIVITY_NEW_TASK
                           | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                   application.icon = info.activityInfo.loadIcon(manager);

                   mApplications.add(application);
               }
           }
    	
    }
    
    
    public class AddImgAdp extends BaseAdapter {
        int GalItemBg;
        private Context cont;
        private Integer[] Imgid = {
                R.drawable.iconradio, R.drawable.iconmail, R.drawable.iconcloud, R.drawable.iconfirefox, R.drawable.iconchatover, R.drawable.iconchat, R.drawable.iconplay
        };


        public AddImgAdp(Context c) {
            cont = c;
          //  TypedArray typArray = obtainStyledAttributes(R.styleable.GalleryTheme);
         //   GalItemBg = typArray.getResourceId(R.styleable.GalleryTheme_android_galleryItemBackground, 0);
          //  typArray.recycle();
        }

        public int getCount() {
            return Imgid.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imgView = new ImageView(cont);

            imgView.setImageResource(Imgid[position]);
            imgView.setLayoutParams(new Gallery.LayoutParams(50, 50));
            imgView.setScaleType(ImageView.ScaleType.FIT_XY);
            imgView.setBackgroundResource(GalItemBg);

            return imgView;
        }
    }

    
	
}
	
