package com.src;
import android.content.Context;   
   
public class DensityUtil {   
   
    /**  
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)  
     */   
    public static int dip2px(Context context, float dpValue) {  
    	float scale = 1;
    	try {
    		 scale = context.getResources().getDisplayMetrics().density;   
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return (int) (dpValue * scale + 0.5f);
    }   
   
    /**  
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp  
     */   
    public static int px2dip(Context context, float pxValue) {   
    	float scale = 1;
    	try {
    		scale = context.getResources().getDisplayMetrics().density;   
		} catch (Exception e) {
			e.printStackTrace();
		}
        return (int) (pxValue / scale + 0.5f);   
    }   
} 