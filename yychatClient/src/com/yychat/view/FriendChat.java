/*package com.yychat.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.*;

import com.yychat.controller.ClientConnetion;
import com.yychat.model.Message;

public class FriendChat extends JFrame implements ActionListener,Runnable{//ֻ�����̳�,���ǿ���ʵ�ֶ�ӿ�

	JScrollPane jsp;
	JTextArea jta;
	
	JPanel jp;
	JTextField jtf;
	JButton jb;
	
	String sender;
	String receiver;
	
	public FriendChat(String sender,String receiver){
		this.sender=sender;
		this.receiver=receiver;
		
		jta=new JTextArea();
		jta.setEditable(false);
		jta.setForeground(Color.red);
		jsp=new JScrollPane(jta);
		this.add(jsp,"Center");
		
		jp=new JPanel();
		jtf=new JTextField(15);
		jb=new JButton("����");
		jb.addActionListener(this);
		jp.add(jtf);jp.add(jb);
		this.add(jp,"South");
		
		this.setSize(350,240);
		this.setTitle(sender+"���ں�"+receiver+"����");
		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		FriendChat friendChat=new FriendChat("1","2");

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==jb){
			String content=jtf.getText();
			jta.append(content+"\r\n");		
			
			//����Message���󵽷�����
			Message mess=new Message();
			mess.setSender(sender);
			mess.setReceiver(receiver);
			mess.setContent(content);
			//mess.setMessageType("2");//common�������ͨ��Ϣ
			mess.setMessageType(Message.message_Common);
			ObjectOutputStream oos;
			try {
				Socket s=(Socket)ClientConnetion.hmSocket.get(sender);
				oos=new ObjectOutputStream(s.getOutputStream());//�ò����Ǿ�̬Socket����
				oos.writeObject(mess);
				
				//�ǲ�����������գ�
				ObjectInputStream ois=new ObjectInputStream(ClientConnetion.s.getInputStream());
				mess=(Message)ois.readObject();
				jta.append(mess.getSender()+"��"+mess.getReceiver()+"˵��"+mess.getContent()+"\r\n");
				
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}		
	}

	@Override
	public void run() {
		ObjectInputStream ois;
		Message mess;
		while(true){
			try {
				//���շ�����ת��������Message
				Socket s=(Socket)ClientConnetion.hmSocket.get(sender);
				ois = new ObjectInputStream(s.getInputStream());
				mess=(Message)ois.readObject();//�ȴ�Server����Message,����
				jta.append(mess.getSender()+"��"+mess.getReceiver()+"˵��"+mess.getContent()+"\r\n");
				
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}				
	}
}
*/