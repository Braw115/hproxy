/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chinatelecom.smartgateway;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.osgi.framework.BundleContext;

public class TcpClient {

	 private final String host ="183.2.217.51";
	 private final int port = 1980;
//	private final String host = "172.16.2.31";
//	private final int port = 3000;
	private final BundleContext context;
	private SendData sendData;
	private Thread clientThread;

	TcpClient(BundleContext context) {
		this.context = context;
		sendData = new SendData(); 
		clientThread = new Thread(sendData, "clientThread");
		clientThread.start();
	}

	private class SendData implements Runnable {
		
		SendData() {
			
		}

		public void run() {
			try {
				// 发送数据
				while (true) {
					Socket socket = new Socket(host, port);
					OutputStream os = socket.getOutputStream();
					DataOutputStream ds = new DataOutputStream(os);
					ds.writeUTF("test");
					ds.close();
					os.close();
					socket.close();
					Thread.sleep(3000);
					
					//
				}
			}catch(ConnectException ec) {
				System.out.println("远程连接失败,请检查远程端口"+port+"是否打开");
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
	}

}
