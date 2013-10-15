package com.wzw.khttprequest;

import java.util.HashMap;

import android.app.Activity;

import com.wzw.khttprequest.ServerConnection.PGErrorTypeBlock;
import com.wzw.khttprequest.ServerConnection.PGIdBlock;
import com.wzw.khttprequest.ServerConnection.PGNetworkErrorType;
import com.wzw.wzwtools.Tools;


class ReadMe {

	void example(Activity activity) {
		final String host = "http://www.baidu.com";
		ServerConnection connection = ServerConnection.getConn(host);

		connection.setFinishedBlock(new PGIdBlock() {

			@Override
			public void callback(String result) {
				Tools.showLog("result = " + result);
			}
		});

		connection.setFailedBlock(new PGErrorTypeBlock() {

			@Override
			public void callback(PGNetworkErrorType error) {

				Tools.showLog("error : " + error.getClass().getName());
			}
		});

		HashMap<String, String> param = new HashMap<String, String>();
		param.put("device_serial", "123");
		connection.requestWithController(activity, "device/bind/", param);
	}
}