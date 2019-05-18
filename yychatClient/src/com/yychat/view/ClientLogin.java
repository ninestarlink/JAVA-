package com.yychat.view;//���������ù�����

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.*;

import com.yychat.controller.ClientConnetion;
import com.yychat.model.Message;
import com.yychat.model.User;
//1��������ʵ�ֶ����������ӿ�
public class ClientLogin extends JFrame implements ActionListener{//������ClientLogin,�̳�
	public static HashMap hmFriendList=new HashMap<String,FriendList>();

	//���������
	JLabel jlbl1;
	
	//�����в������
	JTabbedPane jtp1;//ѡ����
	JPanel jp2,jp3,jp4;	
	JLabel jlbl2,jlbl3,jlbl4,jlbl5;
	JTextField jtf1;//�ı���
	JPasswordField jpf1;//�����
	JButton jb4;
	JCheckBox jcb1,jcb2;	
	
	//�����ϲ������
	JButton jb1,jb2,jb3;
	JPanel jp1;
	
	public  ClientLogin(){//���췽������ʼ������
		//�����������
		jlbl1=new JLabel(new ImageIcon("images/tou.gif"));//��ǩ����
		this.add(jlbl1,"North");//this��ʾ������
		
		//�����в����
		jtp1=new JTabbedPane();
		jp2=new JPanel(new GridLayout(3,3));//���񲼾�
		jp3=new JPanel();jp4=new JPanel();//ʵ�������ɶ��񣬹��췽��
		jlbl2=new JLabel("YY����",JLabel.CENTER);jlbl3=new JLabel("YY����",JLabel.CENTER);
		jlbl4=new JLabel("��������",JLabel.CENTER);
		jlbl4.setForeground(Color.BLUE);		
		jlbl5=new JLabel("�������뱣��",JLabel.CENTER);
		jtf1=new JTextField();
		jpf1=new JPasswordField();
		jb4=new JButton(new ImageIcon("images/clear.gif"));
		jcb1=new JCheckBox("�����¼");jcb2=new JCheckBox("��ס����");		
		jp2.add(jlbl2);jp2.add(jtf1);jp2.add(jb4);
		jp2.add(jlbl3);jp2.add(jpf1);jp2.add(jlbl4);
		jp2.add(jcb1);jp2.add(jcb2);jp2.add(jlbl5);	
		
		jtp1.add(jp2,"YY����");jtp1.add(jp3,"�ֻ�����");jtp1.add(jp4,"��������");
		this.add(jtp1);		
		
		//�����ϲ����
		jb1=new JButton(new ImageIcon("images/denglu.gif"));
		jb1.addActionListener(this);//2����Ӽ�����
		jb2=new JButton(new ImageIcon("images/zhuce.gif"));
		jb3=new JButton(new ImageIcon("images/quxiao.gif"));
		jp1=new JPanel();
		jp1.add(jb1);jp1.add(jb2);jp1.add(jb3);
		this.add(jp1,"South");
		
		this.setSize(350,240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//��;��
		this.setLocationRelativeTo(null);
		this.setVisible(true);	
		
	}
	public static void main(String[] args) {		
		ClientLogin cl=new ClientLogin();//�´����������ñ���
		//clientLogin=null;//����ͻᱻ��������������

	}
	@Override
	public void actionPerformed(ActionEvent arg0) {//3������¼��������
		if(arg0.getSource()==jb1) {
			String userName=jtf1.getText();
			String passWord=new String(jpf1.getPassword());
			User user=new User();
			user.setUserName(userName);
			user.setPassWord(passWord);			
			//������֤��������123456��֤�ɹ���������֤ʧ��
			Message mess=new ClientConnetion().loginValidate(user);
			if(mess.getMessageType().equals(Message.message_LoginSuccess)){
				String friendString=mess.getContent();
				FriendList friendList=new FriendList(userName,friendString);
				
				hmFriendList.put(userName,friendList);
				
				//��1��
				//����message_RequestOnlineFriend��Ϣ��������
				Message mess1=new Message();
				mess1.setSender(userName);
				mess1.setReceiver("Server");
				mess1.setMessageType(Message.message_RequestOnlineFriend);
				Socket s=(Socket)ClientConnetion.hmSocket.get(userName);
				ObjectOutputStream oos;
				try{
					oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(mess1);
				} catch (IOException e ){
					e.printStackTrace();
				}
				
				
				
				
				this.dispose();
			}else{
				JOptionPane.showMessageDialog(this, "�������");
			}			
			
		}
				
		
	}

}
