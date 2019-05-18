package com.chatserver.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.omg.CORBA.LocalObject;

import com.yychat.model.Message;
import com.yychat.model.User;

public class StartServer {
	ServerSocket ss;
	Socket s;
	Message mess;
	String userName;
	String passWord;
	ObjectOutputStream oos;
	
	public static HashMap hmSocket=new HashMap<String,Socket>();//���ͣ�ͨ����
	
	public StartServer(){
		try {
			ss=new ServerSocket(3456);//�������˿ڼ���3456
			System.out.println("�������Ѿ�����������3456�˿�...");
			while(true){//?���߳�����
				s=ss.accept();//�ȴ��ͻ��˽�������
				System.out.println(s);//�������Socket����
				
				//�ֽ������� ��װ�� ����������
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				User user=(User)ois.readObject();//�����û���¼����user
				this.userName=user.getUserName();
				this.passWord=user.getPassWord();
				System.out.println(userName);
				System.out.println(passWord);
			
				
				boolean loginSuccess=new YychatDbUtil().loginValidate(userName,passWord);
				/*//ʹ�����ݿ���֤����
				//1��������������
				Class.forName("com.mysql.jdbc.Driver");
				//2����������
				String url="jdbc:mysql://127.0.0.1:3306/yychat";
				String dbuser="root";
				String dbpass="";	
				Connection conn=DriverManager.getConnection(url,dbuser,dbpass);
				
				//3����һ��PreparedStatement
				String user_Login_Sql="select * from user where username=? and password=?";
				PreparedStatement ptmt=conn.prepareStatement(user_Login_Sql);
				ptmt.setString(1, userName);
				ptmt.setString(2, passWord);
				
				//4ִ��
				ResultSet rs=ptmt.executeQuery();
				
				//5�����ݽ�������ж��Ƿ��ܵ�¼
				boolean loginSuccess=rs.next();	*/
				
				
				//Server����֤�����Ƿ�123456��
				mess=new Message();
				mess.setSender("Server");
				mess.setReceiver(user.getUserName());
				//if(user.getPassWord().equals("123456")){//������"==",����Ƚ�
					if(loginSuccess){	
					//��Ϣ���ݣ�����һ��Message����				
					mess.setMessageType(Message.message_LoginSuccess);//��֤ͨ��	
					
					String friendString=new YychatDbUtil().getFriengString(userName);
					//1�������ݿ��е� relation���ж�ȡ�����б������º����б�1��������������������
					/*String friend_Relation_Sql="select slaveuser from relation where majoruser=? and relationtype='1'";
					ptmt=conn.prepareStatement(friend_Relation_Sql);
					ptmt.setString(1, userName);
					rs=ptmt.executeQuery();
					String friendString="";
					while(rs.next()){
						
						friendString=friendString+rs.getString("slaveuser")+" ";
					}*/
					mess.setContent(friendString);
					System.out.println(userName+"��ȫ������:"+friendString);
					
				}
				else{				
					mess.setMessageType(Message.message_LoginFailure);//��֤��ͨ��	
				}				
				sendMessage(s,mess);
				
				if(loginSuccess){
					//���������ߺ���ͼ��1���������û�������Ϣ
					mess.setMessageType(Message.message_NewOnlineFriend);
					mess.setSender("Server");
					mess.setContent(this.userName);//����ͼ����û���
					Set friendSet=hmSocket.keySet();
					Iterator it=friendSet.iterator();
					String friendName;
					while(it.hasNext()){
						friendName=(String)it.next();//ȡ��ÿһ�����ڵ�����
						mess.setReceiver(friendName);
						hmSocket.get(friendName);
						Socket s1=(Socket)hmSocket.get(friendName);
						sendMessage(s1,mess);
					}
					
					//����ÿһ���û���Ӧ��Socket
					hmSocket.put(userName,s);
					System.out.println("�����û���Socket"+userName+s);
					//��ν��տͻ��˵�������Ϣ����һ���߳�������������Ϣ
					new ServerReceiverThread(s,hmSocket).start();//�����̣߳������߳̾���
					System.out.println("�����̳߳ɹ�");
				}
				
			}			
			
		} catch (IOException | ClassNotFoundException e) {			
			e.printStackTrace();
		}
	}

	private void sendMessage(Socket s,Message mess) throws IOException {
		oos=new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);
	}
}

