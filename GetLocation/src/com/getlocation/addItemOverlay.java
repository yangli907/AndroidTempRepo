package com.getlocation;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class addItemOverlay extends ItemizedOverlay<OverlayItem> {
	ArrayList<OverlayItem> list = new ArrayList<OverlayItem>();
	public addItemOverlay(Drawable defaultMarker) {
		super(boundCenter(defaultMarker));
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return list.get(i);
	}

	@Override
	public int size() {
		return list.size();
	}
	
	public void addOverlay(OverlayItem item){
		list.add(item);
		//setLastFocusedIndex(-1); 
		populate();
	}
}
