package com.wizarm.android.home;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import com.wizarm.android.home.WizarmAIOTV.XMLformat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

public class FilesDbAdapter {

	
    public static final String KEY_ICON = "icon";

    public static final String KEY_PATH = "path";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_MIME = "mime";
    public static final String KEY_TITLE = "title";
    
    
    private static final String DEFAULT_FAVORITES_PATH = "/data/wizarm/wizfavorites.xml";
    
    
    private static final String TAG_FAVORITES = "favorites";
    private static final String TAG_FAVORITE = "item";
    
    private static final String TAG_PACKAGE = "package";
    private static final String TAG_ICON = "icon";
    private static final String TAG_FUNC = "funccase";
    public static final String  TAG_TITLE = "title";

    
    
    final File favFile;
    FileReader favReader;
    

    private static final String TAG = "FilesDbAdapter";
  
    private final Context mCtx;

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public FilesDbAdapter(Context ctx) {
        this.mCtx = ctx;
        
       
    }

    /**
     * Open the files database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public FilesDbAdapter open() {
    	
        // Environment.getRootDirectory() is a fancy way of saying ANDROID_ROOT or "/system".
         final File favFile= new File(DEFAULT_FAVORITES_PATH);
         	        
        	if(favFile.exists()!= true){
        			try{
        				favFile.createNewFile();
        				
        				// write default value in first time in this elements
        			}
        			catch (IOException e)
        			{
        	              	  Log.e(TAG, "Couldn't create wizarm setting file " + favFile);
        	     
        	              //	 return 0;
        			}
        		
        		 }
        		else{	
        	            try {
        	                favReader = new FileReader(favFile);
        	            } catch (FileNotFoundException e) {
        	                Log.e(TAG, "Couldn't find or open favorites file " + favFile);
        	                //return;
        	            }
        		}
        		
        				
        		
        return this;
    }

    public void close() {
	// close
	
    }


    /**
     * Create a new file link using the title and path provided. If the link is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param title the title of the note
     * @param body the body of the note
     * @return rowId or -1 if failed
     */
    public long createFile(String title, String path, int icon, String mime) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_PATH, path);
        initialValues.put(KEY_ICON, icon);
        initialValues.put(KEY_MIME, mime);

        return 0;
       // return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the file link with the given rowId
     * 
     * @param rowId id of file to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteFile(long rowId) {

    	return true;
	// delete 
//         return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all files in the database
     * 
     * @return Cursor over all files
     */
    public Cursor fetchAllFiles() {

        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                KEY_PATH, KEY_ICON, KEY_MIME}, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the File that matches the given rowId
     * 
     * @param rowId id of file to retrieve
     * @return Cursor positioned to matching file, if found
     * @throws SQLException if file could not be found/retrieved
     */
    public Cursor fetchFile(long rowId) 

        Cursor mCursor =

            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                    KEY_TITLE, KEY_PATH, KEY_ICON, KEY_MIME}, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /**
     * Update the file using the details provided. The file to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     * 
     * @param rowId id of file to update
     * @param title value to set file title to
     * @param path value to set file body to
     * @return true if the file was successfully updated, false otherwise
     */
    public boolean updateFile(long rowId, String title, String path, int icon, String mime) {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_PATH, path);
        args.put(KEY_ICON, icon);
        args.put(KEY_MIME, mime);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
  
    private void ReadXML(boolean isLaunching) {

            try {
                favReader = new FileReader(favFile);
            } catch (FileNotFoundException e) {
                Log.e(TAG, "Couldn't find or open favorites file " + favFile);
                return;
            }

          Log.e(TAG, "writeFavorites(int nthbutton) _______________________________________00000 ");
      	  Log.e(TAG, "writeFavorites(int nthbutton) _______________________________________00000 ");
      	  Log.e(TAG, "writeFavorites(int nthbutton) _______________________________________00000 ");
      	  Log.e(TAG, "writeFavorites(int nthbutton) _______________________________________00000 ");
      	  Log.e(TAG, "writeFavorites(int nthbutton) _______________________________________00000 ");
      	  
            
            
            try {
            int i=0;

            final XmlPullParser parser = Xml.newPullParser();

            parser.setInput(favReader);

            beginDocument(parser, TAG_FAVORITES);


            while (parser.getEventType()!=XmlPullParser.END_DOCUMENT) {
                	
		    parser.next();
            String name = parser.getName();

		    
		    if(name==null){continue;}
		    if(name.equalsIgnoreCase("item"))
		    {
			i++;
			continue;	
		    }
		    else if(name.equalsIgnoreCase("icon"))
	    	    {
                    
                  	  if(parser.next()==XmlPullParser.TEXT)
                   		 {
							
                			String result;
                    			result=parser.getText();
                    			Log.w(TAG, "__FDK__ XML"+result);
                    	
                   		 }

		         parser.next(); // remove unwanted endtag
                    }

		    else if(name.equalsIgnoreCase("package"))
	    	    {
                    
                  	  if(parser.next()==XmlPullParser.TEXT)
                   		 {
							
                			String result;
                    			result=parser.getText();
                    			Log.w(TAG, "__FDK__ XML"+result);
                    	
                   		 }

		         parser.next(); // remove unwanted endtag
                    }

		    else if(name.equalsIgnoreCase("funccase"))
	    	    {
                    
                  	  if(parser.next()==XmlPullParser.TEXT)
                   		 {
							
                			String result;
                    			result=parser.getText();
                    			Log.w(TAG, "__FDK__ XML"+result);
                    	
                   		 }

		         parser.next(); // remove unwanted endtag
                    }
		    else if(name.equalsIgnoreCase("title"))
    	    {
                
              	  if(parser.next()==XmlPullParser.TEXT)
               		 {
						
            			String result;
                			result=parser.getText();
                			Log.w(TAG, "__FDK__ XML"+result);
                	
               		 }

	         parser.next(); // remove unwanted endtag
                }



                }
            } catch (XmlPullParserException e) {
                Log.w(TAG, "Got exception parsing favorites.", e);
            } catch (IOException e) {
                Log.w(TAG, "Got exception parsing favorites.", e);
            }
        }



    private static void beginDocument(XmlPullParser parser, String firstElementName)
            throws XmlPullParserException, IOException {

        int type;
        while ((type = parser.next()) != XmlPullParser.START_TAG &&
                type != XmlPullParser.END_DOCUMENT) {
            // Empty
        }

        if (type != XmlPullParser.START_TAG) {
            throw new XmlPullParserException("No start tag found");
        }

        if (!parser.getName().equals(firstElementName)) {
            throw new XmlPullParserException("Unexpected start tag: found " + parser.getName() +
                    ", expected " + firstElementName);
        }
    }

    private static void nextElement(XmlPullParser parser) throws XmlPullParserException, IOException {
        int type;
        while ((type = parser.next()) != XmlPullParser.START_TAG &&
                type != XmlPullParser.END_DOCUMENT) {
            // Empty
        }
    }



    /**
	Write into XML file to replace funcionality , functionality for 5, buttons
     * 
     */
    private void writeXML(int nthbutton)//final ApplicationInfo info) 
    {
    



	 FileOutputStream favWriter;
	 XMLformat data = null;
	 String s;

	 
	 XmlSerializer serializer=Xml.newSerializer();
	 
		 try {
			favWriter=new FileOutputStream(favFile);
			serializer.setOutput(favWriter,"UTF-8");
			serializer.startDocument(null, Boolean.valueOf(true));
			//serializer.setFeature("https://xmlpull.org/v1/foc/featu, arg1)
			serializer.startTag(null,TAG_FAVORITES);
			SerializeXMl(serializer,1,"f.d.draw","thepackage.wizarm","50","Button1");
			SerializeXMl(serializer,2,"f.d.draw1","thepackage.wizarm","70","Button2");
			SerializeXMl(serializer,3,"f.d.draw2","the","00000","Button3");
			SerializeXMl(serializer,4,"f.d.draw2","the","00000","Button4");
			SerializeXMl(serializer,5,"f.d.draw2","the","00000","Button5");
	//		serializer.text(CreateString(1,"hi", "me","fine"));
		//	serializer.text(CreateString(2,"hi", "meee","fine"));
			serializer.endTag(null,TAG_FAVORITES);
			serializer.endDocument();
			serializer.flush();
			favWriter.close();
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
    }




/*
private static final String TAG_FAVORITES = "favorites";
private static final String TAG_FAVORITE = "item";
private static final String TAG_PACKAGE = "package";
private static final String TAG_ICON = "icon";
private static final String TAG_FUNC = "func";    */

void SerializeXMl(XmlSerializer serializer,int id,String icon,String thepackage,String func,String Title) throws IllegalArgumentException, IllegalStateException, IOException
{
	
	serializer.startTag(null,TAG_FAVORITE);
	//serializer.attribute("",String.valueOf(id),String.valueOf(id));
    //serializer.text();
//	serializer.text(String.valueOf(id));
	
	serializer.startTag(null,TAG_ICON);
	serializer.text(icon);
	serializer.endTag(null,TAG_ICON);
	
	serializer.startTag(null,TAG_PACKAGE);
	serializer.text(thepackage);
	serializer.endTag(null,TAG_PACKAGE);
	
	serializer.startTag(null,TAG_FUNC);
	serializer.text(func);
	serializer.endTag(null,TAG_FUNC);
	
	serializer.startTag(null,TAG_TITLE);
	serializer.text(func);
	serializer.endTag(null,TAG_TITLE);
	
	serializer.endTag(null,TAG_FAVORITE);
	
	return;
		
}
}
