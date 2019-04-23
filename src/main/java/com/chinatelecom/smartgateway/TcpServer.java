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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.osgi.framework.BundleContext;

/**
 * 作为服务端接收数据
 * 
 * @author liuxueliang
 *
 */
class TcpServer {
	private final int port;
	private final Acceptor acceptor;
	private final Thread serverThread;

	// 构造方法
	public TcpServer(BundleContext context) throws IOException {
		port = 7000;
		acceptor = new Acceptor();
		serverThread = new Thread(acceptor, "telnetconsole.Listener");
		serverThread.start();

	}

	// 关闭相关线程和流
	public void destory() {
		try {
			acceptor.close();
			serverThread.join();
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
		
	}

	private class Acceptor implements Runnable {

		private volatile boolean m_stop = false;
		private final ServerSocket serverSocket;

		private Acceptor() throws IOException {

			serverSocket = new ServerSocket(port);
		}

		public void close() throws IOException {
			m_stop = true;
			serverSocket.close();
		}

		// @Override
		public void run() {
			try {
				do {
					try {
						Socket socket = serverSocket.accept();
						InputStream ips = socket.getInputStream();
						InputStreamReader ipsr = new InputStreamReader(ips);
						BufferedReader br = new BufferedReader(ipsr);
						String s;
						while ((s = br.readLine()) != null) {
							// 输出接收到的数据
							System.out.println(s);
						}
						br.close();
						socket.close();
					} catch (SocketException ex) {

					} catch (SocketTimeoutException ste) {
						// Caught socket timeout exception. continue
					}
				} while (!m_stop);

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
