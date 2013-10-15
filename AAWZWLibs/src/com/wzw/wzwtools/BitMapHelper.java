package com.wzw.wzwtools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class BitMapHelper {

	/**
	 * 
	 * @param pPath exam:/mnt/sdcard/zhaozhao
	 * @param pFileName exam:/mnt/sdcard/zhaozhao/1.jpg
	 * @param quality range 0~100 more lager more clearness
	 * @throws IOException
	 *             
	 */
	public static void saveBitMap2SDCARD(String pPath, String pFileName,
			Bitmap pBitmap, int quality) throws IOException {
		File file = new File(pPath);
		if (!file.exists()) {
			file.mkdir();
		}
		File myCaptureFile = new File(pPath + "/" + pFileName);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		pBitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
		bos.flush();
		bos.close();
		pBitmap.recycle();
		pBitmap = null;
	}

	/**
	 *if file not exists,return null 
	 * @param pPath
	 * @param pFileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Bitmap getBitmapFromSDCARD(String pPath, String pFileName)throws FileNotFoundException {
		File file = new File(pPath);
		return file.exists()? BitmapFactory.decodeFile(pPath + "/" + pFileName): null;
	}


	public static Bitmap getBitmapFromInputStream(InputStream is) {
		return BitmapFactory.decodeStream(is);
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx,float roundPy) {
		
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		Bitmap resultBitmap = Bitmap.createBitmap(width,height, Config.ARGB_8888);
		Canvas canvas = new Canvas(resultBitmap);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPy, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return resultBitmap;
	}
}
