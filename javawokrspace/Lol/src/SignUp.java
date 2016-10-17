import java.awt.BorderLayout;
import java.sql.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import javax.swing.*;
public class SignUp extends JFrame implements ActionListener{
	JPanel panel;
	JLabel label1, label2, label3, label4, label5;
	final JTextField text1,text4, text5;
	JPasswordField text2, text3;
	String a, b, c, d, e;
	JButton button01, button02;
	
	SignUp() {
		setSize(400, 200);
		setTitle("Sign up form");
		
		label1 = new JLabel();
		label1.setText("Username");
		text1 = new JTextField(15);
		
		label2 = new JLabel();
		label2.setText("Password");
		text2 = new JPasswordField(15);
		
		label3 = new JLabel();
		label3.setText("Password check");
		text3 = new JPasswordField(15);
		
		label4 = new JLabel();
		label4.setText("Name");
		text4 = new JTextField(15);
		
		label5 = new JLabel();
		label5.setText("Dept");
		text5 = new JTextField(15);
		
		button01 = new JButton("Sign up");
		button02 = new JButton("cancel");
		
		panel = new JPanel(new GridLayout(6,1));
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(label3);
		panel.add(text3);
		panel.add(label4);
		panel.add(text4);
		panel.add(label5);
		panel.add(text5);
		panel.add(button01);	
		panel.add(button02);
		add(panel, BorderLayout.CENTER);
		button01.addActionListener(this);
		button02.addActionListener(this);
		setTitle("SIGN UP FORM");		
	
	
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		Object src = ae.getSource();
		if(src==button01){
			if(!text2.getText().equals(text3.getText())){
				
				System.out.println(text2.getPassword());
				System.out.println(text3.getPassword());
				JOptionPane.showMessageDialog(this, "both password and check are not correct", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				
				
			
			Connection con = null;
			PreparedStatement pstm = null;
			try {
				String url = "jdbc:mysql://192.168.17.128:3306/tw1";
				String id="GBK_Admin";
				String pw = "!1234abcd";
				
				Class.forName("com.mysql.jdbc.Driver");
				
				con=DriverManager.getConnection(url,id,pw);
				
				System.out.println("제대로 연결되었습니다");
				System.out.println(text2.getPassword());
			
				String sql = "insert into login values(?,password(?),?,?, default, default, default)";
				pstm = con.prepareStatement(sql);
				
				pstm.setString(1, (String)text1.getText());
				pstm.setString(2, (String)text2.getText());
				pstm.setString(3, (String)text4.getText());
				pstm.setString(4, (String)text5.getText());
				
				pstm.executeUpdate();
				dispose();
				
				
				JOptionPane.showMessageDialog(null, "Sign up success");
				
				
				System.out.println("업데이트 성공");
			} catch(Exception e){
				e.printStackTrace();
				System.out.println("실패");
			} finally {
				if(pstm != null) try {pstm.close();} catch(SQLException sqle){}
				if(pstm != null) try {con.close();} catch(SQLException sqle){}
			}
			
			
				
				
				
				
			}
				
			
			
			
		}
		else if(src==button02){
			dispose();
			
		}
	}

}
