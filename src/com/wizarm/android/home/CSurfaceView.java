
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

	private static final String LOG_TAG = "cam";
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
   	CameraPreviewThread cameraPreviewThread;


	
	public CSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);

        Log.e(LOG_TAG, "Camera init CSurfaceView IN");
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
        Log.e(LOG_TAG, "Camera init CSurfaceView FIN");
   	 }


 	@Override
 	public void surfaceCreated(SurfaceHolder holder) {
 		// TODO Auto-generated method stub
		sholder=holder;
		Log.e(LOG_TAG, "Camera init surfaceCreated IN");

		/*
		 * To reduce startup time, we start the preview in another thread.
        	 * We make sure the preview is started at the end of onCreate.
        	 */	
	//drawNosignal();

		CameraSetup();
		CameraOpenThread cameraOpenThread = new CameraOpenThread();
		//	cameraOpenThread.start();
		Log.e(LOG_TAG, "Camera init surfaceCreated FIN");
 		
 	}

	public int CameraSetup(){
		Log.e(LOG_TAG, "CameraSetup IN");
		Log.e(LOG_TAG, "NO of camera=" +checkNumberofCameras());
		
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
 	      Log.e(LOG_TAG, "CameraSetup FIN");
 	       return 1; 
	}



	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.e(LOG_TAG, "SurfaceChanged camera called IN");
		if(mCamera==null) CameraSetup();
		// TODO Auto-generated method stub
     //   drawNosignal();
		cameraPreviewThread = new CameraPreviewThread();
		cameraPreviewThread.setdelay(100);
		cameraPreviewThread.start();

		Log.e(LOG_TAG, "SurfaceChanged camera called FIN");

		
	}

	    public void onPause() {
        // Because the Camera object is a shared resource, it's very
        // important to release it when the activity is paused.
	    	Log.e(LOG_TAG, "onPause camera called IN");
   	     if (mCamera != null) {
        	 mCamera.release();
           	 mCamera = null;
       	      }
   	     Log.e(LOG_TAG, "onPause camera called FIN");
  	  }

	    public void onResume() {
	    	Log.e(LOG_TAG, "onResume camera called IN");
	    	CameraSetup();
	    	Log.e(LOG_TAG, "onResume camera called FIN");
	    }

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.e(LOG_TAG, "surfaceDestroyed camera called IN");
	 if(mCamera!=null){
       		mCamera.stopPreview();
      		mCamera.release();
        	mCamera = null;
       	 Log.e(LOG_TAG, "surfaceDestroyed mCamera = null;");
		}
	 Log.e(LOG_TAG, "surfaceDestroyed camera called FIN");

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
	       Log.e(LOG_TAG, " onDraw __FDK__ ");
	   if(Flag==0){
		   Log.e(LOG_TAG, " onDraw __FDK__ ");
//   	canvas.drawBitmap(scaled,0,0,null);
		   }
	    else{
	   // 	canvas.drawColor(0xff000000);

	    	
	   // 	mCamera.startPreview();
	    }
	    	Flag=1;
	    
	//	Bcanvas=canvas;
	}


    public class CameraOpenThread extends Thread {
        @Override
        public void run() {
		Log.e(LOG_TAG, "CAMERA SETUP RUN CALLED _FDK__");
		CameraSetup();	
         }
        }
    
    public class CameraPreviewThread extends Thread {
        @Override
        public void run() {
    		Log.e(LOG_TAG, "CAMERA PreviewThread CALLED _FDK__");   		

        	if(mCamera!=null){
        		Log.e(LOG_TAG, "CAMERA PreviewThread CALLED _FDK__ 2");
       	         mCamera.startPreview(); // disabled camera temp for speed
        	}
        //	invalidate();

            }

		public void setdelay(int i) {
			// TODO Auto-generated method stub
			
			}
        }

    }
