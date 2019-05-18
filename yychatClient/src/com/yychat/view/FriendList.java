package com.yychat.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;//�����ӿ�
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.*;

public class FriendList extends JFrame implements ActionListener,MouseListener{//����,�ӿ�
	public static HashMap hmFriendChat1=new HashMap<String,FriendChat1>();
	
	//��Ա����
	CardLayout cardLayout;
	
	//��һ����Ƭ
	JPanel myFriendPanel;
	
	JButton myFriendButton;//����
	
	JScrollPane myFriendListJScrollPane;
	JPanel myFriendListJPanel;
	public static final int MYFRIENDCOUNT=51;
	JLabel[] myFriendJLabel=new JLabel[MYFRIENDCOUNT];//50��������,��������
	
	JPanel myStrangerBlackListPanel;
	JButton myStrangerButton;
	JButton blackListButton;
	
	//�ڶ�����Ƭ
	JPanel myStrangerPanel;
	//����
	JPanel myFriendStrangerPanel;
	JButton myFriendButton1;
	JButton myStrangerButton1;
	
	//�в�
	
	//�ϲ�
	JButton blackListButton1;
	String userName;//��Ա����
	public FriendList(String userName,String friendString){//�β�
		this.userName=userName;
		//������һ�ſ�Ƭ
		myFriendPanel = new JPanel(new BorderLayout());//���ֵ�����,�߽粼��
		//System.out.println(myFriendPanel.getLayout());
		
		//����
		myFriendButton=new JButton("�ҵĺ���");
		myFriendPanel.add(myFriendButton,"North");
		
		//�в�
		String[] friendName=friendString.split(" ");
		int MYCOUNT=friendName.length;
		myFriendListJPanel=new JPanel(new GridLayout(MYCOUNT,1));//���񲼾�
		for(int i=0;i<MYCOUNT;i++){
			myFriendJLabel[i]=new JLabel(friendName[i]+"",new ImageIcon("images/duck.gif"),JLabel.LEFT);
			//myFriendJLabel[i].setEnabled(false);
			//if(Integer.parseInt(userName)==i) myFriendJLabel[i].setEnabled(true);
			//myFriendJLabel[Integer.parseInt(userName)].setEnabled(true);
			myFriendJLabel[i].addMouseListener(this);
			myFriendListJPanel.add(myFriendJLabel[i]);
		}
		/*myFriendListJScrollPane=new JScrollPane();
		myFriendListJScrollPane.add(myFriendListJPanel);*/
		myFriendListJScrollPane=new JScrollPane(myFriendListJPanel);
		myFriendPanel.add(myFriendListJScrollPane);
		
		//�ϲ�
		myStrangerBlackListPanel=new JPanel(new GridLayout(2,1));//���񲼾�
		//System.out.println(myStrangerBlackListPanel.getLayout());
		myStrangerButton=new JButton("�ҵ�İ����");
		myStrangerButton.addActionListener(this);//�¼�������
		blackListButton=new JButton("������");
		myStrangerBlackListPanel.add(myStrangerButton);
		myStrangerBlackListPanel.add(blackListButton);
		myFriendPanel.add(myStrangerBlackListPanel,"South");
		
		//�����ڶ��ſ�Ƭ
		myStrangerPanel=new JPanel(new BorderLayout());//���ֵ�����,�߽粼��
		//����
		myFriendStrangerPanel=new JPanel(new GridLayout(2,1));//���񲼾�;
		myFriendButton1=new JButton("�ҵĺ���");
		myFriendButton1.addActionListener(this);//�¼�����
		myStrangerButton1=new JButton("�ҵ�İ����"); ;
		myFriendStrangerPanel.add(myFriendButton1);
		myFriendStrangerPanel.add(myStrangerButton1);
		myStrangerPanel.add(myFriendStrangerPanel,"North");
		
		//�в�
		
		//�ϲ�
		blackListButton1=new JButton("������");
		myStrangerPanel.add(blackListButton1,"South");
		
		//���2����Ƭ
		cardLayout=new CardLayout();//��Ƭ����
		this.setLayout(cardLayout);
		this.add(myFriendPanel,"1");		
		this.add(myStrangerPanel,"2");
		
		this.setSize(150,500);
		this.setTitle(userName+"�ĺ����б�");
		this.setIconImage(new ImageIcon("images/duck2.gif").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		//FriendList friendList=new FriendList("pdh");
	}
	public void setEnabledOnlineFriend(String onlineFriend){
	String[] friendName=onlineFriend.split(" ");
	int count=friendName.length;
	for(int i=0;i<count;i++){
		myFriendJLabel[Integer.parseInt(friendName[i])].setEnabled(true);
	}
	}
	@Override
	public void actionPerformed(ActionEvent e) {//��Ӧ�¼��ķ���
		if(e.getSource()==myStrangerButton) cardLayout.show(this.getContentPane(), "2");
		if(e.getSource()==myFriendButton1) cardLayout.show(this.getContentPane(), "1");		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getClickCount()==2){
			JLabel jlbl=(JLabel)arg0.getSource();
			String receiver=jlbl.getText();
			//new FriendChat(this.userName,receiver);
			//new Thread(new FriendChat(this.userName,receiver)).start();
			
			//��ô�������������治�ظ����������⣿
			//˼·��Ҫ��hmFriendChat1���������û�иö����еĻ���������û�в��½���
			FriendChat1 friendChat1=(FriendChat1)hmFriendChat1.get(userName+"to"+receiver);
			if(friendChat1==null){
				friendChat1=new FriendChat1(this.userName,receiver);//������friendChat1�����������Ǵ����Ķ���
				hmFriendChat1.put(userName+"to"+receiver, friendChat1);
			}else{
				friendChat1.setVisible(true);
				System.out.println("test!");
			}			
		}		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		JLabel jlbl1=(JLabel)arg0.getSource();
		jlbl1.setForeground(Color.red);
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		JLabel jlbl1=(JLabel)arg0.getSource();
		jlbl1.setForeground(Color.BLACK);
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
