package com.happy.samuelalva.bcykari.support.image;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.happy.samuelalva.bcykari.support.Constants;

/**
 * This class holds our bitmap caches (memory).
 * Mod by google's iosched (com.google.samples.apps.iosched.util.BitmapCache)
 */
public class BitmapCache {
    private static final String TAG = BitmapCache.class.getSimpleName();

    private static final float DEFAULT_MEM_CACHE_PERCENT = 0.5f;

    private static BitmapCache bitmapCache;

    private LruCache<String, Bitmap> mMemoryCache;

    private BitmapCache(int memCacheSize) {
        init(memCacheSize);
    }

    public static BitmapCache getInstance(float memCachePercent) {
        if (bitmapCache == null) {
            bitmapCache = new BitmapCache(calculateMemCacheSize(memCachePercent));
        }

        return bitmapCache;
    }

    public static BitmapCache getInstance() {
        return getInstance(DEFAULT_MEM_CACHE_PERCENT);
    }

    private void init(int memCacheSize) {
        if (Constants.DEBUG)
            Log.d(TAG, "Memory cache created (size = " + memCacheSize + "KB)");

        mMemoryCache = new LruCache<String, Bitmap>(memCacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                final int bitmapSize = getBitmapSize(bitmap) / 1024;
                return bitmapSize == 0 ? 1 : bitmapSize;
            }
        };
    }

    public void addBitmapToCache(String data, Bitmap bitmap) {
        if (data == null || bitmap == null) {
            return;
        }

        synchronized (mMemoryCache) {
            if (mMemoryCache.get(data) == null) {
                if (Constants.DEBUG)
                    Log.d(TAG, "Memory cache put - " + data);

                mMemoryCache.put(data, bitmap);
            }
        }
    }

    public Bitmap getBitmapFromMemCache(String data) {
        if (data != null) {
            synchronized (mMemoryCache) {
                final Bitmap memBitmap = mMemoryCache.get(data);
                if (memBitmap != null) {
                    if (Constants.DEBUG)
                        Log.d(TAG, "Memory cache hit - " + data);

                    return memBitmap;
                }
            }
            if (Constants.DEBUG)
                Log.d(TAG, "Memory cache miss - " + data);
        }
        return null;
    }

    public void clearCache() {
        if (mMemoryCache != null) {
            mMemoryCache.evictAll();
        }
    }

    public static int calculateMemCacheSize(float percent) {
        if (percent < 0.05f || percent > 0.8f) {
            throw new IllegalArgumentException("setMemCacheSizePercent - percent must be " + "between 0.05 and 0.8 (inclusive)");
        }
        return Math.round(percent * Runtime.getRuntime().maxMemory() / 1024);
    }

    public static int getBitmapSize(Bitmap bitmap) {
        return bitmap.getByteCount();
    }

    public Bitmap getBitmap(String key) {
        return getBitmapFromMemCache(key);
    }

    public void putBitmap(String key, Bitmap bitmap) {
        addBitmapToCache(key, bitmap);
    }

}
