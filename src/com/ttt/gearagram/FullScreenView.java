package com.ttt.gearagram;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class FullScreenView extends ImageView
{
	
	int screen_w,screen_h;
	int circle_radius;
	Paint circlePaint;
	
	int number_of_filters=5;
	int[] colorsArray;
	
	long timeLastTouchOccurred;
	
	int interfaceTransparency;
	
	

	public FullScreenView(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		
		circle_radius=15;
		circlePaint=new Paint();
		circlePaint.setColor(android.graphics.Color.WHITE);
		circlePaint.setStyle(Style.FILL);
		
		screen_w=getContext().getResources().getDisplayMetrics().widthPixels;
		screen_h=getContext().getResources().getDisplayMetrics().heightPixels;
		
		colorsArray=new int[number_of_filters];
		
		colorsArray[0]=android.graphics.Color.WHITE;
		colorsArray[1]=android.graphics.Color.BLUE;
		colorsArray[2]=android.graphics.Color.RED;
		colorsArray[3]=android.graphics.Color.GREEN;
		colorsArray[4]=android.graphics.Color.YELLOW;
		
		timeLastTouchOccurred=System.currentTimeMillis();
		
		
	}
	
	
	
	public void onDraw(Canvas canvas)
	{
    	super.onDraw(canvas);
    	canvas.drawColor(Color.BLACK);
    	drawInterface(canvas);
    	
    	invalidate();
    	
	}
	
	public void drawInterface(Canvas canvas)
	{
	
			interfaceTransparency=255-(int) ((System.currentTimeMillis()-timeLastTouchOccurred)/10);
	
			if(interfaceTransparency<0)
				interfaceTransparency=0;
	 	
			circlePaint.setColor(colorsArray[0]);
			circlePaint.setAlpha(interfaceTransparency); 
			canvas.drawCircle(circle_radius,screen_h/2, circle_radius,circlePaint);

			circlePaint.setColor(colorsArray[1]);
			circlePaint.setAlpha(interfaceTransparency);
			canvas.drawCircle(circle_radius,circle_radius, circle_radius,circlePaint);

			circlePaint.setColor(colorsArray[2]);
			circlePaint.setAlpha(interfaceTransparency);
			canvas.drawCircle(circle_radius/2+screen_w/2,circle_radius, circle_radius,circlePaint);

			circlePaint.setColor(colorsArray[3]);
			circlePaint.setAlpha(interfaceTransparency);
			canvas.drawCircle(screen_w-circle_radius,circle_radius, circle_radius,circlePaint);

			circlePaint.setColor(colorsArray[4]);
			circlePaint.setAlpha(interfaceTransparency);
			canvas.drawCircle(screen_w-circle_radius,screen_h/2, circle_radius,circlePaint);

			
			
		
	}
	
	public boolean onTouchEvent(MotionEvent event) 
	{
	 	   // TODO Auto-generated method stub
	 	   
	 	Log.d("X = "+event.getX(),"Y = "+event.getY());
	 	
	 	//Log.d(TAG,"Timings -- 1:"+System.currentTimeMillis());
	 	//Log.d(TAG,"Layers ::: "+layerID);
	 	
	 	timeLastTouchOccurred=System.currentTimeMillis();
	 
	 	return true;
	}
}
