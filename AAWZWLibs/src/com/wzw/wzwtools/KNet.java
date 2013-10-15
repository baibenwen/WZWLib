package com.wzw.wzwtools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class KNet {
	
	/**
	 * NETWORK_TYPE_CDMA 网络类型为CDMA
     * NETWORK_TYPE_EDGE 网络类型为EDGE
     * NETWORK_TYPE_EVDO_0 网络类型为EVDO0
     * NETWORK_TYPE_EVDO_A 网络类型为EVDOA
     * NETWORK_TYPE_GPRS 网络类型为GPRS
     * NETWORK_TYPE_HSDPA 网络类型为HSDPA
     * NETWORK_TYPE_HSPA 网络类型为HSPA
     * NETWORK_TYPE_HSUPA 网络类型为HSUPA
     * NETWORK_TYPE_UMTS 网络类型为UMTS
     * 联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EDGE，电信的2G为CDMA，电信的3G为EVDO
	 * @param context
	 * @return if wifi: ConnectivityManager.Type_Wifi ;if mobile  ConnectivityManager.Type_Mobile ;else -1;
	 * 
	 */
	public static int wifiType(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo info = manager.getActiveNetworkInfo();
		
		if (info == null) {
			return -1;
		}else{
			int type = info.getType();
			switch (type) {
			case ConnectivityManager.TYPE_WIFI:
				return ConnectivityManager.TYPE_WIFI;
			case ConnectivityManager.TYPE_MOBILE:
				return info.getSubtype();
			default:
				return -1;
			}
		}
	}
	
	
	  public static boolean isNetworkAvailable(Context pContext)
	    {
	        Context context = pContext;
	        ConnectivityManager connect = (ConnectivityManager)context.getSystemService(
	                Context.CONNECTIVITY_SERVICE);
	        if(connect==null)
	        {
	            return false;
	        }else
	        {
	            NetworkInfo[] info = connect.getAllNetworkInfo();
	            if(info!=null)
	            {
	                for(int i=0;i<info.length;i++)
	                {
	                    if(info[i].getState()==NetworkInfo.State.CONNECTED)
	                    {
	                        return true;
	                    }
	                }
	            }
	        }
	        return false;
	    }
	  
	  /**
	   * 得到内网IP
	   * @return
	   */
	  public static String getIpAdd() {
	        try {  
	            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {  
	                NetworkInterface intf = en.nextElement();  
	                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {  
	                    InetAddress inetAddress = enumIpAddr.nextElement();  
	                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {  
	                        return inetAddress.getHostAddress().toString();  
	                    }  
	                }  
	            }  
	        } catch (Exception e) {  
	        }  
	        return ""; 
	    }
	  
	 /**
	  * 得到外网IP
	  * @return
	  */
	  public static String getNetIp()  
	    {  
	        URL infoUrl = null;  
	        InputStream inStream = null;  
	        try  
	        {  
	            //http://iframe.ip138.com/ic.asp  
	            //infoUrl = new URL("http://city.ip138.com/city0.asp");  
	            infoUrl = new URL("http://iframe.ip138.com/ic.asp");  
	            URLConnection connection = infoUrl.openConnection();  
	            HttpURLConnection httpConnection = (HttpURLConnection)connection;  
	            int responseCode = httpConnection.getResponseCode();  
	            if(responseCode == HttpURLConnection.HTTP_OK)  
	            {   
	                inStream = httpConnection.getInputStream();   
	                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"utf-8"));  
	                StringBuilder strber = new StringBuilder();  
	                String line = null;  
	                while ((line = reader.readLine()) != null)   
	                    strber.append(line + "\n");  
	                inStream.close();  
	                //从反馈的结果中提取出IP地址  
	                int start = strber.indexOf("[");  
	                int end = strber.indexOf("]", start + 1);  
	                line = strber.substring(start + 1, end);  
	                return line;   
	            }  
	        }  
	        catch(MalformedURLException e) {  
	            e.printStackTrace();  
	        }  
	        catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	    }  
}
