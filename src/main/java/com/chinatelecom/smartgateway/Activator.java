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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * 作为客户端发送数据
 * 
 * @author liuxueliang
 *
 */
public class Activator implements BundleActivator {

	private TcpClient client;
	private TcpServer server;

	public void start(BundleContext context) throws Exception {
		System.out.println("hello osgi!!");
		// 作为客户端发送数据
		client = new TcpClient(context);

		// 作为服务端接收数据
		server = new TcpServer(context);

	}

	public void stop(BundleContext context) throws Exception {
		
//		if (client != null) {
//			client.destory();
//			client = null;
//		}
		
		if (server != null) {

			server.destory();
			server = null;
		}

		System.out.println("Goodbye osgi!!");
	}

}
