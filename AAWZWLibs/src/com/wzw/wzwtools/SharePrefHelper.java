package com.wzw.wzwtools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

public class SharePrefHelper {
	/**
	 * Save String to sharePref
	 * 
	 * @param pContext
	 * @param fileName
	 * @param key
	 * @param value
	 */
	public static void saveString2ShardPref(Context pContext, String fileName,
			String key, String value) {
		SharedPreferences preferences = pContext.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * get String from sharePref
	 * 
	 * @param pContext
	 * @param fileName
	 * @param key
	 * @param defvalue
	 * @return
	 */
	public static String getStringFromShardPref(Context pContext,
			String fileName, String key, String defvalue) {
		SharedPreferences preferences = pContext.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return preferences.getString(key, defvalue);
	}

	/**
	 * save Object 2 sharePref which has encode64
	 * 
	 * @param pContext
	 * @param fileName
	 * @param key
	 * @param object
	 */
	public static void saveObject2ShardPref(Context pContext, String fileName,
			String key, Object object) {
		SharedPreferences preferences = pContext.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		String s = encode64(object);
		editor.putString(key, s);
		Tools.showLog(Tools.class.getName(), "save pref object = "
				+ object.getClass().getName());
		editor.commit();
	}

	/**
	 * get Object 2 sharePref which has decode64
	 * 
	 * @param pContext
	 * @param fileName
	 * @param key
	 * @param defvalue
	 * @return
	 */
	public static Object getObjectFromShardPref(Context pContext,
			String fileName, String key, String defvalue) {
		SharedPreferences preferences = pContext.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		String s = preferences.getString(key, defvalue);
		if (s == null) {
			return null;
		} else {
			Object o = decode64(s);
			return o;
		}
	}

	public static void clearShardProferAllData(Context pContext, String fileName) {
		SharedPreferences preferences = pContext.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
	}

	public static void deleteShardPreferData(Context pContext, String fileName,
			String key) {

		SharedPreferences preferences = pContext.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(key);
		editor.commit();

	}

	public static String encode64(Object o) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(o);
			String object64 = new String(Base64.encode(bos.toByteArray(),
					Base64.DEFAULT));
			return object64;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Object decode64(String pString) {

		byte[] base64Byte = Base64.decode(pString.getBytes(), Base64.DEFAULT);
		ByteArrayInputStream bis = new ByteArrayInputStream(base64Byte);
		try {
			ObjectInputStream ois = new ObjectInputStream(bis);
			return ois.readObject();

		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
