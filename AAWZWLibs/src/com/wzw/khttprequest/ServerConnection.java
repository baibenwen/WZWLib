package com.wzw.khttprequest;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.HttpResponse;

import android.app.Activity;

import com.wzw.khttprequest.GalHttpRequest.GalHttpRequestListener;
import com.wzw.wzwtools.Tools;


public class ServerConnection  {
	private String host ;
	private String baseUrl = "";
	private static final int DefaultTimeOutSeconds = 10;
	private GalHttpRequest request;
	//private ASIHTTPRequest request;
	
	private PGBasicBlock startedBlock;
	private PGIdBlock finishedBlock;
	private PGErrorTypeBlock failedBlock;
	
	public static String host(){
		return "http://idennis.polegame.net/";
	}
	
	public ServerConnection(String host) {
		// TODO Auto-generated constructor stub
		this.host = host;
	}
	/**
	 * @param pActivity
	 * @param controller exa:  "device/bind/"
	 * @param params 
	 */
	public void requestWithController(Activity pActivity ,String controller,HashMap<String,String> params){
		this.requestWithController(pActivity,controller, params, DefaultTimeOutSeconds);
	}

	public void requestWithController(final Activity pActivity ,String url){
		String urlStr = url;
		Tools.showLog("url  = "+urlStr);
		request = GalHttpRequest.requestWithURL(pActivity, urlStr);
		
		request.startAsynchronous();
		
		if(ServerConnection.this.startedBlock!=null){
			
			pActivity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					ServerConnection.this.startedBlock.callback();
				}
			});
		}
		
		request.setListener(new GalHttpRequestListener(){

			@Override
			public void loadFailed(HttpResponse arg0, InputStream arg1) {
				if(ServerConnection.this.failedBlock!=null){
				pActivity.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							ServerConnection.this.failedBlock.callback(PGNetworkErrorType.PGConnectionFailureErrorType);
							
						}
					});
					
				}
			}

			@Override
			public void loadFinished(InputStream arg0, boolean arg1) {
				if(ServerConnection.this.finishedBlock!=null){
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int i=-1;
					try {
						while((i=arg0.read())!=-1){
							baos.write(i);
						}
						final String string = baos.toString();
						//JSONObject jsonObject = new JSONObject(string);
						
						pActivity.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								ServerConnection.this.finishedBlock.callback(string);
							}
						});
						
					} catch (IOException e) {
						e.printStackTrace();
					} 
					
				}			
			}
			
		});
	}
	
	
	public void requestWithController(final Activity pActivity ,String controller,HashMap<String,String> params,int timeOutSeconds){
		String getUrl = "";
		if(params != null){
			Set<String> keys= params.keySet();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				Tools.showLog(this,"key = "+key+" value = "+params.get(key));
				getUrl = getUrl+key+"/"+URLEncoder.encode(params.get(key))+"/";
			}
		}
		String urlStr = host+baseUrl+controller+getUrl;
		Tools.showLog("url  = "+urlStr);
		request = GalHttpRequest.requestWithURL(pActivity, urlStr);
		
		request.startAsynchronous();
		
		if(ServerConnection.this.startedBlock!=null){
			pActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					ServerConnection.this.startedBlock.callback();
				}
			});
		}
		
		request.setListener(new GalHttpRequestListener(){

			@Override
			public void loadFailed(HttpResponse arg0, InputStream arg1) {
				if(ServerConnection.this.failedBlock!=null){
				pActivity.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							ServerConnection.this.failedBlock.callback(PGNetworkErrorType.PGConnectionFailureErrorType);
							
						}
					});
					
				}
			}

			@Override
			public void loadFinished(InputStream arg0, boolean arg1) {
				if(ServerConnection.this.finishedBlock!=null){
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int i=-1;
					try {
						while((i=arg0.read())!=-1){
							baos.write(i);
						}
						final String string = baos.toString();
						//JSONObject jsonObject = new JSONObject(string);
						
						pActivity.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								ServerConnection.this.finishedBlock.callback(string);
							}
						});
						
					} catch (IOException e) {
						e.printStackTrace();
					} 
					
				}			
			}
			
		});
	}
	
	public static ServerConnection getConn(String host){
		return new ServerConnection(host);
	}
	
	
	
	public PGBasicBlock getStartedBlock() {
		return startedBlock;
	}
	public void setStartedBlock(PGBasicBlock startedBlock) {
		this.startedBlock = startedBlock;
	}



	public PGIdBlock getFinishedBlock() {
		return finishedBlock;
	}
	public void setFinishedBlock(PGIdBlock finishedBlock) {
		this.finishedBlock = finishedBlock;
	}



	public PGErrorTypeBlock getFailedBlock() {
		return failedBlock;
	}
	public void setFailedBlock(PGErrorTypeBlock failedBlock) {
		this.failedBlock = failedBlock;
	}



	public interface PGBasicBlock{
		public abstract void callback();
	}
	public interface PGStringBlock{
		public void callback(String result);
	}
	public interface PGIdBlock{
		public abstract void callback(String result);
	}
	public interface PGErrorTypeBlock{
		public void callback(PGNetworkErrorType error);
	}
	
	public enum PGNetworkErrorType{
		PGConnectionFailureErrorType,
	    PGRequestTimedOutErrorType,
	    PGAuthenticationErrorType,
	    PGRequestCancelledErrorType,
	    PGUnableToCreateRequestErrorType,
	    PGInternalErrorWhileBuildingRequestType,
	    PGInternalErrorWhileApplyingCredentialsType,
		PGFileManagementError,
		PGTooMuchRedirectionErrorType,
		PGUnhandledExceptionError,
		PGCompressionError
	}
}
