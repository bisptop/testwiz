
package com.wizarm.android.home;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class CSurfaceView extends SurfaceView implements Callback{

	private static final String LOG_TAG = "WizarmTV";
	private GestureDetector gesture=null;
	int mCurrentCamera = 0;
	SurfaceHolder sholder=null;
	public Camera mCamera=null;
	CameraInfo cameraInfo = new CameraInfo();
	Bitmap scaled;
	Canvas Bcanvas;
	int newWidth ;
	int newHeight;
	int Flag=0;


	protected boolean mOpenCameraFail;
   	protected boolean mCameraDisabled;


	
	public CSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);

        Log.e(LOG_TAG, "Camera init CSurfaceView");
        // Acquire the next camera and request Preview to reconfigure
            // parameters.
       //     mCurrentCamera = (mCameraCurrentlyLocked + 1) % mNumberOfCameras;
         //   mCamera = Camera.open(mCurrentCamera);
         //   mCameraCurrentlyLocked = mCurrentCamera;
	 //   surface_view.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
	   // surface_holder.setFormat(PixelFormat.TRANSLUCENT);
	  //  surface_holder.setFormat(PixelFormat.OPAQUE);
          // surface_view.setZOrderMediaOverlay(true);
	//surface_holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setWillNotDraw(false);
    //    getHolder.addCallback(this);
   	 }


 	@Override
 	public void surfaceCreated(SurfaceHolder holder) {
 		// TODO Auto-generated method stub
		sholder=holder;

		/*
        	 * To reduce startup time, we start the preview in another thread.
        	 * We make sure the preview is started at the end of onCreate.
        	 */	
//		drawNosignal();

		    	CameraSetup();
		CameraOpenThread cameraOpenThread = new CameraOpenThread();
		//	cameraOpenThread.start();
 		
 	}

	public int CameraSetup(){
		
		Log.e(LOG_TAG, "100000000000000000000000000000000000000000000000000000000 NO of camera=" +checkNumberofCameras());
		
		 if(checkNumberofCameras()==0) {
				Log.e(LOG_TAG, "111111111111111111111111111111111111111111111111");
               		mCamera=null;
               		drawNosignal();
                	return 0;
       		 }

      	 	if(mCamera==null) mCamera = Camera.open(mCurrentCamera);

 	        try {
        	      mCamera.setPreviewDisplay(sholder);
     		    } catch (IOException exception) {
	               mCamera.release();
        	       mCamera = null;
        	       drawNosignal();
		 }

 	       return 1; 
	}



	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
        	drawNosignal();
		CameraPreviewThread cameraPreviewThread = new CameraPreviewThread();
		cameraPreviewThread.setdelay(100);
		cameraPreviewThread.start();

		Log.e(LOG_TAG, "SurfaceChanged camera called");

		
	}


	    public void onPause() {

        // Because the Camera object is a shared resource, it's very
        // important to release it when the activity is paused.
   	     if (mCamera != null) {
        	 mCamera.release();
           	 mCamera = null;
       	      }
  	  }


	    public void onResume() {
	    	//CameraSetup();
	    }



	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	 if(mCamera!=null){
       		mCamera.stopPreview();
      		mCamera.release();
        	mCamera = null;
		}

	}

	public int checkNumberofCameras(){
		return Camera.getNumberOfCameras();
	}

	public void drawNosignal() {        
		Log.e(LOG_TAG, "Camera drawNosignal() Start");
		Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.nosignal);
        float scaleW = (float) background.getWidth()/(float)getWidth();
        float scaleH = (float) background.getHeight()/(float)getHeight();
        newWidth = Math.round(background.getWidth()/scaleW);
        newHeight = Math.round(background.getHeight()/scaleH);
        scaled = Bitmap.createScaledBitmap(background, newWidth, newHeight, true); 
 
        Log.e(LOG_TAG, "Camera drawNosignal() Finish");
   	 }

	public void onDraw(Canvas canvas) {
		
	       Log.e(LOG_TAG, " __FDK__ ");
	  // if(Flag==0)
		canvas.drawBitmap(scaled,0,0,null);
//	    else
//	    	canvas.drawColor(0xff000000);
	    Flag=1;
	    
	//	Bcanvas=canvas;
	}


    public class CameraOpenThread extends Thread {
        @Override
        public void run() {

		Log.e(LOG_TAG, "CAMERA SETUP RUN CALLED _FDK__");
		Log.e(LOG_TAG, "CAMERA SETUP RUN CALLED _FDK__");
		Log.e(LOG_TAG, "CAMERA SETUP RUN CALLED _FDK__");
		Log.e(LOG_TAG, "CAMERA SETUP RUN CALLED _FDK__");
		CameraSetup();	
            }
        }

    public class CameraPreviewThread extends Thread {
        @Override
        public void run() {
    		Log.e(LOG_TAG, "CAMERA PreviewThread CALLED _FDK__");
    		Log.e(LOG_TAG, "CAMERA PreviewThread CALLED _FDK__");
//Paint paint=new Paint();
//paint.setStyle(Style.FILL);
//paint.setColor(0xff000000);
//Bcanvas.drawRect(0,0,newWidth, newHeight, paint);

    		//Bcanvas.drawColor(0xff000000);
    		
    		

        	if(mCamera!=null){
        		Log.e(LOG_TAG, "CAMERA PreviewThread CALLED _FDK__ 2");
        		Log.e(LOG_TAG, "CAMERA PreviewThread CALLED _FDK__ 2");
       	    // mCamera.startPreview(); // disabled camera temp for speed
        	}
        	invalidate();

            }

		public void setdelay(int i) {
			// TODO Auto-generated method stub
			
		}
        }

    }





















/*	
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction()==MotionEvent.ACTION_UP) {
			gestureListener.onSingleTapUp(event);
		}
		
		return(true);
	}
	
	public void addTapListener(TapListener l) {
		listeners.add(l);
	}
	
	public void removeTapListener(TapListener l) {
		listeners.remove(l);
	}
	
	private GestureDetector.SimpleOnGestureListener gestureListener=
		new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			for (TapListener l : listeners) {
				l.onTap(e);
			}
			
			return(true);
		}
	};
	
	public interface TapListener {
		void onTap(MotionEvent event);
	}
*/
