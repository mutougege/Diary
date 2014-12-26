package com.src;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter{
	
	private ArrayList<GalleryInfo> list;
	private LayoutInflater inflater;
	private Context context;
	public ImageAdapter(Context context, ArrayList<GalleryInfo> list) {
        super();
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    
    public int getCount() {
        return list.size();
    }

    
    public Object getItem(int position) {
        return list.get(position);
    }

    
    public long getItemId(int position) {
        return position;
    }

    
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	ViewHolder holder;
    	if (convertView == null) {
    		convertView = inflater.inflate(R.layout.gallery_item, null);
    		holder = new ViewHolder();
    		holder.effectTitle = (TextView)convertView.findViewById(R.id.effectTitle);
    		holder.effectDrawable = (ImageView)convertView.findViewById(R.id.effectDrawable);
    		convertView.setTag(holder);
    	} else {
    		holder = (ViewHolder)convertView.getTag();
    	}
    	holder.effectDrawable.setImageResource(list.get(position).drawable);
        return convertView;
    }
    
    private class ViewHolder {
    	TextView effectTitle;
    	ImageView effectDrawable;
    }
}
