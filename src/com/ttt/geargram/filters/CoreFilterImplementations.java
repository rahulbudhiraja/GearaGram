package com.ttt.geargram.filters;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.graphics.Bitmap;
import android.graphics.ColorMatrix;


public class CoreFilterImplementations
{

	private static double DELTA_INDEX[] = {
	    0,    0.01, 0.02, 0.04, 0.05, 0.06, 0.07, 0.08, 0.1,  0.11,
	    0.12, 0.14, 0.15, 0.16, 0.17, 0.18, 0.20, 0.21, 0.22, 0.24,
	    0.25, 0.27, 0.28, 0.30, 0.32, 0.34, 0.36, 0.38, 0.40, 0.42,
	    0.44, 0.46, 0.48, 0.5,  0.53, 0.56, 0.59, 0.62, 0.65, 0.68, 
	    0.71, 0.74, 0.77, 0.80, 0.83, 0.86, 0.89, 0.92, 0.95, 0.98,
	    1.0,  1.06, 1.12, 1.18, 1.24, 1.30, 1.36, 1.42, 1.48, 1.54,
	    1.60, 1.66, 1.72, 1.78, 1.84, 1.90, 1.96, 2.0,  2.12, 2.25, 
	    2.37, 2.50, 2.62, 2.75, 2.87, 3.0,  3.2,  3.4,  3.6,  3.8,
	    4.0,  4.3,  4.7,  4.9,  5.0,  5.5,  6.0,  6.5,  6.8,  7.0,
	    7.3,  7.5,  7.8,  8.0,  8.4,  8.7,  9.0,  9.4,  9.6,  9.8, 
	    10.0
	};
	
	
	
	public Bitmap sharpenImage(Bitmap bmpmutable)
	{
		
		Mat frame,image;
		frame=new Mat();
	
		
		Utils.bitmapToMat(bmpmutable, frame);
		
		image=frame.clone();
		
		Size tempSize=new Size(0,0);
		Imgproc.GaussianBlur(frame, image, tempSize, 3);
		Core.addWeighted(frame, 1.5, image, -0.5, 0, image);
		Utils.matToBitmap(image, bmpmutable);
	
		return bmpmutable;
	}
	
	protected static float cleanValue(float p_val, float p_limit)
	{
	    return Math.min(p_limit, Math.max(-p_limit, p_val));
	}
	
	 public  ColorMatrix adjustContrast(int value) {
		    value = (int)cleanValue(value,100);
		    if (value == 0) { 
		        return null; 
		    }
		    float x;
		    if (value < 0) {
		        x = 127 + value / 100*127;
		    } else {
		        x = value % 1;
		        if (x == 0) {
		            x = (float)DELTA_INDEX[value];
		        } else {
		            //x = DELTA_INDEX[(p_val<<0)]; // this is how the IDE does it.
		            x = (float)DELTA_INDEX[(value<<0)]*(1-x) + (float)DELTA_INDEX[(value<<0)+1] * x; // use linear interpolation for more granularity.
		        }
		        x = x*127+127;
		    }

		    float[] mat = new float[]
		    { 
		            x/127,0,0,0, 0.5f*(127-x),
		            0,x/127,0,0, 0.5f*(127-x),
		            0,0,x/127,0, 0.5f*(127-x),
		            0,0,0,1,0,
		            0,0,0,0,1
		    };
		   // cm.postConcat(new ColorMatrix(mat));
		    return new ColorMatrix(mat);
		}

		public ColorMatrix adjustSaturation( float value) {
		   
		
			value = cleanValue(value,100);
		    if (value == 0) {
		        return null;
		    }

		    float x = 1+((value > 0) ? 3 * value / 100 : value / 100);
		    float lumR = 0.3086f;
		    float lumG = 0.6094f;
		    float lumB = 0.0820f;

		    float[] mat = new float[]
		    { 
		        lumR*(1-x)+x,lumG*(1-x),lumB*(1-x),0,0,
		        lumR*(1-x),lumG*(1-x)+x,lumB*(1-x),0,0,
		        lumR*(1-x),lumG*(1-x),lumB*(1-x)+x,0,0,
		        0,0,0,1,0,
		        0,0,0,0,1
		    };
		   // cm.postConcat(new ColorMatrix(mat));
		    return new ColorMatrix(mat);
		}
}
