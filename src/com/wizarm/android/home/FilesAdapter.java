package com.wizarm.android.home;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObservable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FilesAdapter extends BaseAdapter {
	private static final String TAG = "FilesAdapter";
	public List<FileItem> files;
	private final Context mCtx;
	
	private FilesDbAdapter dbAdapter;
	private LayoutInflater li;
	
	public FilesAdapter(Context ctx) {
		mCtx = ctx;
		
		files = new ArrayList<FileItem>();
		// Connect to db
		dbAdapter = new FilesDbAdapter(ctx);
	dbAdapter.Setfiles(files);
	//	dbAdapter.open();


		
		li =  (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return 5;//files.size();
	}

	@Override
	public Object getItem(int position) {

		return files.get(position);
	}

	@Override
	public long getItemId(int position) {
		return files.get(position)._ID;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;
		if (convertView == null) {
			itemView = li.inflate(R.layout.grid_item, parent, false);
		}

/*		
		TextView text = (TextView)itemView.findViewById(R.id.grid_item_text);
		text.setText(files.get(position).NAME);
		ImageView icon = (ImageView)itemView.findViewById(R.id.grid_item_icon);
		icon.setImageResource(files.get(position).ICON);*/
		

		TextView text = (TextView)itemView.findViewById(R.id.grid_item_text);
		text.setText("MYBUTTON");
		ImageView icon = (ImageView)itemView.findViewById(R.id.grid_item_icon);
		icon.setImageResource(R.drawable.focused_application_background_static);
		
		return itemView;
	}
	
	public Boolean addItem(String name, String path, int icon, String mime) {
	/*	long rowId = dbAdapter.createFile(name, path, icon, mime);
		if (rowId > 0) {
			files.add(new FileItem(rowId, name, icon, path, null));
			notifyDataSetChanged();
			return true;
		} else {
			//Toast.makeText(mCtx, "Failed to add " + name, Toast.LENGTH_SHORT).show();
			Log.e(TAG, "Failed to add " + name);
			return false;
		}*/
		return false;
	}
	
	public Boolean removeItem(int position) {
		FileItem file = files.get(position);
		if (dbAdapter.deleteFile(file._ID)) {
			removeShortcut(position);
			files.remove(position);
			notifyDataSetChanged();
			
			return true;
		}
		return false;
	}
	
	private void removeShortcut(int position) {
		FileItem file = files.get(position);
		Uri uri = Uri.fromFile(new File(file.PACKAGE));
		final Intent shortcutIntent = new Intent(Intent.ACTION_VIEW);
	//	shortcutIntent.setDataAndType(uri, file.MIME);
		
		Intent removeIntent = new Intent();
		removeIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		removeIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, file.NAME);
		removeIntent.putExtra("duplicate", false);
		removeIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
		 
		mCtx.sendBroadcast(removeIntent);
	}

	public void close() {
		dbAdapter.close();
	}
}

