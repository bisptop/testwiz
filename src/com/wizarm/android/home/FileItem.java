package com.wizarm.android.home;
import android.database.DataSetObservable;

	
public final class FileItem extends DataSetObservable{
		public long _ID;
		public String NAME;
		public int ICON;
		public String PACKAGE;
		public int FUNCCASE;

		
		public FileItem(long id, String name, int icon, String path, String shortcut) {
			_ID = id;
			NAME = name;
			ICON = icon;
			PACKAGE = path;
			FUNCCASE = Integer.parseInt(shortcut);
		}
		
		public FileItem(long id, String name, int icon) {
			_ID = id;
			NAME = name;
			ICON = icon;
		}
		
		public void setValues(String name, int icon, String path,  String shortcut) {
			NAME = name;
			ICON = icon;
			PACKAGE = path;
			FUNCCASE = Integer.parseInt(shortcut);
			notifyChanged();
		}
	}


