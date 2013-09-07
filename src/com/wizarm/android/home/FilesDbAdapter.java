package com.wizarm.android.home;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static final String KEY_PACKAGE = "pack";
    public static final String KEY_ROWID = "_id";

    public static final String KEY_TITLE = "title";
    
    
    private static final String DEFAULT_FAVORITES_PATH = "/data/wizarm/wizfavorites.xml";
    
    
    private static final String TAG_FAVORITES = "favorites";
    private static final String TAG_FAVORITE = "item";
    
    private static final String TAG_PACKAGE = "package";
    private static final String TAG_ICON = "icon";
    private static final String TAG_FUNC = "funccase";
    public static final String  TAG_TITLE = "title";

    
    
    File favFile=null;
    FileReader favReader=null;

    

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
    
    
	public List<FileItem> files;
	
	
   public void Setfiles(List<FileItem> files2) {
	   	int i;
		   files=files2;
		   // create 5 object temp we will need to initialise it later
		   for (i=0;i<5;i++)
		   files.add(new FileItem(i,"",0,"","0"));
		   return;
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
         favFile= new File(DEFAULT_FAVORITES_PATH);
         	        
        	if(favFile.exists()!= true){
        		
        		createFile();
        		
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
    public long createFile() {

		try{
			favFile.createNewFile();
			if(favFile.exists()== true)
			writeDefaultXML(5,"New Button configure","NULL", R.drawable.focused_application_background_static,0);
			// write default value in first time in this elements
		}
		catch (IOException e)
		{
              	  Log.e(TAG, "Couldn't create wizarm setting file " + favFile);
     
              //	 return 0;
		}

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

    	ReadXML(true);
        return null;
    }

    /**
     * Return a Cursor positioned at the File that matches the given rowId
     * 
     * @param rowId id of file to retrieve
     * @return Cursor positioned to matching file, if found
     * @throws SQLException if file could not be found/retrieved
     */
    public Cursor fetchFile(long rowId) {

    	ReadXML(true);
  
        Cursor mCursor=null;
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
    public boolean updateFile(int ID,String title, String pack,String icon,int funccase) {

    	writeXML(ID,title,pack,icon,funccase);
    	
    	return true;
    }
  
    private void ReadXML(boolean isLaunching) {
    		int position=0;
    		String icon;
			String pack;
			String funccase;
			String title;
			
            try {
                favReader = new FileReader(favFile);
            } catch (FileNotFoundException e) {
                Log.e(TAG, "Couldn't find or open favorites file " + favFile);
                return;
            }
            
	//		files.set(position, null).setValues(result, i, result, result);
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
                  		
							
                    			icon=parser.getText();
                    			files.get(position).ICON=Integer.parseInt(icon);
                    			
                    			position++;

                    			Log.w(TAG, "__FDK__ XML"+icon);
                    	
                   		 }

		         parser.next(); // remove unwanted endtag
                    }

		    else if(name.equalsIgnoreCase("package"))
	    	    {
                    
                  	  if(parser.next()==XmlPullParser.TEXT)
                   		 {
							

                    			pack=parser.getText();
                    			Log.w(TAG, "__FDK__ XML"+pack);
                    	
                   		 }

		         parser.next(); // remove unwanted endtag
                    }

		    else if(name.equalsIgnoreCase("funccase"))
	    	    {
                    
                  	  if(parser.next()==XmlPullParser.TEXT)
                   		 {
							
                			
                			funccase=parser.getText();
                    			Log.w(TAG, "__FDK__ XML"+funccase);
                    	
                   		 }

		         parser.next(); // remove unwanted endtag
                    }
		    else if(name.equalsIgnoreCase("title"))
    	    {
                
              	  if(parser.next()==XmlPullParser.TEXT)
               		 {
						
            			
                			title=parser.getText();
                			Log.w(TAG, "__FDK__ XML"+title);
                	
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
    private void writeXML(int ID,String title, String pack,String icon,int funccase)//final ApplicationInfo info) 
    {
    
     int i;
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
			
			for(i=0;i<5;i++){
			if((ID+1)==i)
				SerializeXMl(serializer,i,icon,pack,String.valueOf(funccase),title);
			else
				SerializeXMl(serializer,i,String.valueOf(files.get(i).ICON),files.get(i).PACKAGE,String.valueOf(files.get(i).FUNCCASE),files.get(i).NAME);
			}

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


/**
Write into XML file DEfault 5 buttons with no functionality
 * 
 */
private void writeDefaultXML(int ID,String title, String pack, int icon,int funccase)//final ApplicationInfo info) 
{



 int i;
 FileOutputStream favWriter;
 XMLformat data = null;
 String s;

 
 XmlSerializer serializer=Xml.newSerializer();
 
	 try {
		favWriter=new FileOutputStream(favFile);
		serializer.setOutput(favWriter,"UTF-8");
		serializer.startDocument(null, Boolean.valueOf(true));
		serializer.startTag(null,TAG_FAVORITES);
		for(i=0;i<ID;i++){
		SerializeXMl(serializer,i,String.valueOf(icon),pack,String.valueOf(funccase),title);
		}
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


}
