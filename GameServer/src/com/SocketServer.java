package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class SocketServer {
	private static ServerSocket serverSocket;
	private static final int SERVER_PORT=4321;
	private static String example_problem="醉后不知_在水 满船清梦压星河 ";
	private static String example_key="天";
	private static ArrayList<SocketClass> socketList=new ArrayList<SocketClass>();
	private static  int classId=1;//从一开始
	private static Map<Integer,Boolean> firstMap=new HashMap<Integer, Boolean>();//记录发送过的
	private boolean flag=true;
	
	public static void main(String[] args) {
		initServer();
	}
	public static void initServer() {
		try {
			serverSocket=new ServerSocket(SERVER_PORT);
			System.out.println("服务端已启动，端口号是: "+SERVER_PORT);
			System.out.println("==========等待客户端连接中==========");
			sendProblemThread();
			while(true) {
				Socket socket=serverSocket.accept();
				System.out.println("客户端已连接！客户端的IP地址是:"+socket.getInetAddress());
				startAction(socket);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 持续的读取客户端发来的消息
	 * 并判断一个socket是否还存在着
	 * @author sorryfu
	 *
	 */
	public static class readThread extends Thread{
		public SocketClass socketClass;
		public BufferedReader reader;
		public BufferedWriter writer;
		public readThread(SocketClass socketClass) {
			this.socketClass=socketClass;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
//				System.out.println("某个readThread启动了");
//				if(socketClass.getFriendId()!=0) {//不等于0表示已经匹配上了
//					//自己的输入流
//					InputStream inputStream=socketClass.getSocket().getInputStream();
//					reader=new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
//					//对方的输出流
//					OutputStream outputStream = null;
//					for(int i=0;i<socketList.size();i++) {
//						if(socketList.get(i).getFriendId()==socketClass.getUserId()) {
//							System.out.println("在readThread里，有匹配的了");
//							outputStream=socketList.get(i).getSocket().getOutputStream();
//						}
//					}
//					writer=new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));
//					while(true) {
//						
//					if(reader.ready()) {
//						System.out.println("id为"+socketClass.getId()+"的reader准备好了。。。");
//						String comeData=reader.readLine();
//						JSONObject msgJson=new JSONObject(comeData);//解析android端传过来的json数据
//						if(msgJson.has("game_my_anwser")) {
//							System.out.println("user id为"+socketClass.getUserId()+"发过来的答案是："+msgJson.getString("game_my_anwser"));
//							writer.write(msgJson.getString("game_my_anwser")+"\n");
//							writer.flush();
//							
//							firstMap.put(socketClass.getId(), true);
//							sysoutMap();
//						}
//						
//						
//					}
//					Thread.sleep(200);
//					System.out.println("id为"+socketClass.getId()+"的readThread循环中。。。");
//				}
//				}
				System.out.println("id为"+socketClass.getId()+"的readThread启动了");
				while(true) {
					if(socketClass.getFriendId()!=0) {//不等于0表示已经匹配上了
						System.out.println("id为"+socketClass.getId()+"匹配上了");
						//自己的输入流
						InputStream inputStream=socketClass.getSocket().getInputStream();
						reader=new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
						//对方的输出流
						OutputStream outputStream = null;
						for(int i=0;i<socketList.size();i++) {
							if(socketList.get(i).getFriendId()==socketClass.getUserId()) {
								outputStream=socketList.get(i).getSocket().getOutputStream();
							}
						}
						writer=new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8"));
						if(reader.ready()) {
						System.out.println("id为"+socketClass.getId()+"的reader准备好了。。。");
						String comeData=reader.readLine();
						JSONObject msgJson=new JSONObject(comeData);//解析android端传过来的json数据
						if(msgJson.has("game_my_anwser")) {
							System.out.println("user id为"+socketClass.getUserId()+"发过来的答案是："+msgJson.getString("game_my_anwser"));
							JSONObject other_anwser=new JSONObject();
							other_anwser.put("game_other_anwser", msgJson.getString("game_my_anwser"));
							writer.write(other_anwser.toString() +"\n");
							writer.flush();
							
							firstMap.put(socketClass.getId(), true);
							sysoutMap();
						}
						
						
					}
						
					
					}
					Thread.sleep(2000);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
	//对于新来的socket会进入这个流程
	public static void startAction(final Socket socket) {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					InputStream inputStream=socket.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
					
					while(true) {
						
						String message=reader.readLine();
						JSONObject jsonObject=new JSONObject(message);
						
						
						if(jsonObject.getString("game_userId")!=null) {
						int userId=Integer.parseInt(jsonObject.getString("game_userId"));
						
						System.out.println("获取到客户端的id是："+userId);
						
						firstMap.put(classId, false);//锁
						SocketClass freshSocket=new SocketClass(classId,userId, socket);
						socketList.add(freshSocket);
						
						readThread rThread=new readThread(freshSocket);//监管这个线程发过来的消息
						rThread.start();
						
						match();//由于是在while（true）循环里，所以会一直等待匹配
						firstMessage();//第一次匹配上之后呢，先互相发个id以更新ui
						
						classId++;
						}else {
							System.out.println("这个客户端啥也没传过来");
						}
					} 
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}.start();
	}
	public static void sysoutMap() {
		for(Map.Entry<Integer, Boolean> entry : firstMap.entrySet()) {
			System.out.println("key = "+entry.getKey()+", value="+entry.getValue());
		}
	}
	
	/**
	 * 第一次匹配上之后，给双方发送对方的id
	 */
	public static void firstMessage(){
		System.out.println("给新匹配项双方互发消息");
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					if(socketList.size()>=2) {
						for(int i=0;i<socketList.size();i++) {
						if(socketList.get(i).getFriendId()!=0) {
							for(int j=i+1;j<socketList.size();j++) {
								if(socketList.get(j).getFriendId()!=0) {
									if(socketList.get(i).getFriendId()==socketList.get(j).getUserId()&&socketList.get(j).getFriendId()==socketList.get(i).getUserId()) {
										System.out.println("发送前的map是这样的：");
										sysoutMap();
										//如果双双互为friendid的话，则各自发送一个id
										//1.获取输出源
										OutputStream output1=socketList.get(i).getSocket().getOutputStream();
										OutputStream output2=socketList.get(j).getSocket().getOutputStream();
										BufferedWriter writer1=new BufferedWriter(new OutputStreamWriter(output1,"utf-8"));
										BufferedWriter writer2=new BufferedWriter(new OutputStreamWriter(output2,"utf-8"));
										//2.封装
//										String b=socketList.get(i).getFriendId()+"\n";
//										output1.write(b.getBytes("utf-8"));
//										String a=socketList.get(j).getFriendId()+"\n";
//										output2.write(a.getBytes("utf-8"));
										JSONObject jsonObject1=new JSONObject();
										JSONObject jsonObject2=new JSONObject();
										jsonObject1.put("game_friendId", socketList.get(i).getFriendId());
										jsonObject2.put("game_friendId", socketList.get(j).getFriendId());
										writer1.write(jsonObject1.toString()+"\n");
										writer2.write(jsonObject2.toString()+"\n");
										
										writer1.flush();
										writer2.flush();
										
										Thread.sleep(200);
										//打开锁，让服务端给客户端发送题目
										firstMap.put(socketList.get(i).getId(), true);
										firstMap.put(socketList.get(j).getId(), true);
										
										System.out.println("已给新匹配的双方发送对方的id");
										sysoutMap();
										
									}
								}
							}
						}
						}
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}.start();
	}
	//专门用来发题目的方法
	public static void sendProblemThread() {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					//一直循环
					while(true) {
						
						if(socketList.size()>=2) {
							for(int i=0;i<socketList.size();i++) {
							if(socketList.get(i).getFriendId()!=0&&firstMap.get(socketList.get(i).getId())==true) {
								for(int j=i+1;j<socketList.size();j++) {
									if(socketList.get(j).getFriendId()!=0&&firstMap.get(socketList.get(j).getId())==true) {
										if(socketList.get(i).getFriendId()==socketList.get(j).getUserId()&&socketList.get(j).getFriendId()==socketList.get(i).getUserId()) {
											//1.获取输出流
											OutputStream output1=socketList.get(i).getSocket().getOutputStream();
											OutputStream output2=socketList.get(j).getSocket().getOutputStream();
											
											BufferedWriter writer1=new BufferedWriter(new OutputStreamWriter(output1,"utf-8"));
											BufferedWriter writer2=new BufferedWriter(new OutputStreamWriter(output2,"utf-8"));
											
											//2.封装题目和答案
											JSONObject jsonObject=new JSONObject();
											jsonObject.put("game_problem", example_problem);
											jsonObject.put("game_key", example_key);
											
											//3.写入
											writer1.write(jsonObject.toString()+"\n");
											writer2.write(jsonObject.toString()+"\n");
											writer1.flush();
											writer2.flush();
											
											//4.每次发送完题目后，锁置为false，待到两个都为true（全部都完成答题后），才发送题目
											firstMap.put(socketList.get(i).getId(), false);
											firstMap.put(socketList.get(j).getId(), false);
											
											System.out.println("给了某对匹配项发送了题目和答案");
										}
									}
								}
							}
							}
						}
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}.start();
		
	}
	
	//开始匹配
	public static void match() {
		//int默认值是0
		//大于或2才开始，否则就不能匹配咯
		if(socketList.size()>=2) {
			System.out.println("开始匹配前的状态");
			for(SocketClass mSocket:socketList) {
				System.out.println(mSocket.toString());
			}
			for(int i=0;i<socketList.size();i++) {
				if(socketList.get(i).getFriendId()==0) {//friendId(int)的基本默认值是0，所以，如果等于0的话说明没有，则进行匹配操作
					for(int j=i+1;j<socketList.size();j++) {
						if(socketList.get(j).getFriendId()==0) {//如果在此循环里，也有一个friendId为0的话，就进行两两匹配
							socketList.get(i).setFriendId(socketList.get(j).getUserId());
							socketList.get(j).setFriendId(socketList.get(i).getUserId());
						}
					}
					
				}
				
			}
			System.out.println("匹配后的状态");
		for(SocketClass mSocket:socketList) {
			System.out.println(mSocket.toString());
		}
		}
		
	}
	
}
