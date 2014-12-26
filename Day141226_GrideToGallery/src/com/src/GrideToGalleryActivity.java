package com.src;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GrideToGalleryActivity extends Activity {

	private GridView gallery;
	private static int[] effectDrawable = {R.drawable.a01, R.drawable.a02, 
		R.drawable.a03};
	private ArrayList<GalleryInfo> list;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);   
        list = new ArrayList<GalleryInfo>();
        initData();
        final ImageAdapter adapter = new ImageAdapter(this, list);
        gallery = (GridView)findViewById(R.id.gallery);
        int num = list.size();
        gallery.setNumColumns(num);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_wd);
        int width = num*DensityUtil.dip2px(getApplicationContext(), 200)+(num-1)*DensityUtil.dip2px(getApplicationContext(), 8);
        layout.setLayoutParams(new LayoutParams(width, LayoutParams.WRAP_CONTENT));
        
        
        
        gallery.setAdapter(adapter);
        gallery.setOnItemClickListener(new OnItemClickListener() {			
			public void onItemClick(AdapterView<?> arg0, View arg1, int select,
					long arg3) {
				Toast.makeText(getApplicationContext(), "dianji "+select, Toast.LENGTH_SHORT).show();
			}
		});
        gallery.setOnTouchListener(new OnTouchListener() {						
			public boolean onTouch(View v, MotionEvent event) {
				v.clearFocus();
				return false;
			}
		});
    }


	private void initData() {
		for(int i=0;i<effectDrawable.length;i++){
			GalleryInfo galleryInfo = new GalleryInfo();
			galleryInfo.drawable = effectDrawable[i];
			list.add(galleryInfo);
		}
	}
}