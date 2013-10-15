package com.wzw.wzwtools;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Tools {

	static final boolean logisopen = true;

	/**
	 * this layoutParams extends ViewGroup ,if wrap return wrap else matchParent
	 * 
	 * @param wrap
	 * @return
	 */
	public static LayoutParams getLayoutParamater(boolean wrapWidth,
			boolean wapHeight) {
		if (wrapWidth) {
			if (wapHeight) {
				return new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
			} else {
				return new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.MATCH_PARENT);
			}
		} else {
			if (wapHeight) {
				return new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
			} else {
				return new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.FILL_PARENT);
			}
		}

	}

	public static void closeInputMethodPad(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static void showLog(Object object, List<?> list) {
		if (logisopen) {
			for (int i = 0; i < list.size(); i++) {
				showLog(object, "array [" + i + "]  = " + list.get(i));
			}
		}
	}

	public static void showLog(Object object, Object[] array) {

		if (logisopen) {
			for (int i = 0; i < array.length; i++) {
				showLog(object, "array [" + i + "]  = " + array[i]);
			}
		}
	}

	public static void showLog(Object pclass, String msg) {
		if (logisopen) {
			Log.d("wzw", "class: " + pclass.getClass().getName() + " msg:"
					+ msg);
		}
	}

	/**
	 * default tag :wzw
	 * 
	 * @param pclass
	 * @param msg
	 */
	public static void showLog(String msg) {
		if (logisopen) {
			Log.d("wzw", ""+msg);
		}
	}

	public static void showToast(Context pContext, String Msg) {
		Toast.makeText(pContext, Msg, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context pContext, int id) {
		Toast.makeText(pContext, pContext.getResources().getString(id),
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * @param pContext
	 * @param pTitle
	 * @param Msg
	 * @param IconId
	 * @param pPosivateBtnString
	 *            : if this value set null ,this btn is not be set
	 * @param Plistener
	 *            PositiveBtnListener
	 * @param pNagetiveBtnString
	 *            : if this value set null ,this btn is not be set
	 * @param nListener
	 *            NagetiveBtnListener
	 * @return
	 */
	public Builder getAlertDialogBuilder(Context pContext, String pTitle,
			String Msg, int IconId, String pPosivateBtnString,
			OnClickListener Plistener, String pNagetiveBtnString,
			OnClickListener nListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(pContext)
				.setMessage(Msg).setTitle(pTitle).setIcon(IconId);
		if (pPosivateBtnString != null) {
			builder.setPositiveButton(pPosivateBtnString, Plistener);
		}
		if (pNagetiveBtnString != null) {
			builder.setNegativeButton(pNagetiveBtnString, nListener);
		}
		return builder;
	}

	/**
	 * 
	 * @param pActivity
	 * @param width
	 * @return if true: return width else height
	 */
	public static int getWindowWidthOrHeight(Activity pActivity, boolean width) {
		Display display = pActivity.getWindowManager().getDefaultDisplay();
		return width ? display.getWidth() : display.getHeight();
	}

	public static String getIMEI(Context pContext) {
		TelephonyManager telephonemanage = (TelephonyManager) pContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonemanage.getDeviceId();
	}

	public static String getMac(Context context) {
		WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();

	}
	
	/**
	 * get Phont Model
	 * @return android.os.Build.MODEL , more info see model;
	 */
	public static String getPhoneModel(){
		
		return android.os.Build.MODEL;
	}
	/**
	 * get Phone SDK Version
	 * @return  android.os.Build.VERSION.SDK; 
	 */
	public static String getPhoneSDK(){
		
		return android.os.Build.VERSION.SDK; 
		
	}
	public static  String getLocalIpAddress() { 
        try { 
            for (Enumeration<NetworkInterface> en = NetworkInterface 
                    .getNetworkInterfaces(); en.hasMoreElements();) { 
                NetworkInterface intf = en.nextElement(); 
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) { 
                    InetAddress inetAddress = enumIpAddr.nextElement(); 
                    if (!inetAddress.isLoopbackAddress()) { 
                        return inetAddress.getHostAddress().toString(); 
                    } 
                } 
            } 
        } catch (SocketException ex) { 
            Log.e("WifiPreference IpAddress", ex.toString()); 
        } 
        return null; 
    } 

}