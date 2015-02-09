/*
 Copyright (c) 2013 Roman Truba

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ru.truba.touchgallery.GalleryWidget;

import android.content.Context;
import android.view.ViewGroup;
import ru.truba.touchgallery.TouchView.UrlTouchImageView;

import java.util.List;


/**
 Class wraps URLs to adapter, then it instantiates {@link ru.truba.touchgallery.TouchView.UrlTouchImageView} objects to paging up through them.
 */
public class UrlPagerAdapter extends BasePagerAdapter {

    // image size limit
    int maxWidth = 1280;
    int maxHeight = 720;
    int maxPreloadWidth = 320;
    int maxPreloadHeight = 240;

    UrlTouchImageView currUrlTouchImageView;

	public UrlPagerAdapter(Context context, List<String> resources)
	{
		super(context, resources);
	}

    /**
     * Set max size of decoded bitmap.
     * @param width
     * @param height
     * @param preloadWidth size for non-current item.
     * @param preloadHeight size for non-current item.
     */
    public void setBmpSizeLimit(int width, int height, int preloadWidth, int preloadHeight) {
        maxWidth = width;
        maxHeight = height;
        maxPreloadWidth = preloadWidth;
        maxPreloadHeight = preloadHeight;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (currUrlTouchImageView != null && mCurrentPosition != position) {
            currUrlTouchImageView.setUrl(mResources.get(mCurrentPosition), maxPreloadWidth, maxPreloadHeight);
        }

        super.setPrimaryItem(container, position, object);
        ((GalleryViewPager)container).mCurrentView = ((UrlTouchImageView)object).getImageView();
        ((UrlTouchImageView)object).setUrl(mResources.get(position), maxWidth, maxHeight);
        currUrlTouchImageView = (UrlTouchImageView)object;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position){
        final UrlTouchImageView iv = new UrlTouchImageView(mContext);
        if (position == mCurrentPosition)
            iv.setUrl(mResources.get(position), maxWidth, maxHeight);
        else
            iv.setUrl(mResources.get(position), maxPreloadWidth, maxPreloadHeight);
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        collection.addView(iv, 0);
        return iv;
    }
}
