import javax.swing.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class LoginDemo extends JFrame implements ActionListener
{	
	

	static int z;
	static int admincheck;
	static String IDcheck;
	JButton SUBMIT, SIGNUP;
	JPanel panel;
	JLabel label1, label2;
	final JTextField text1;
	JPasswordField text2;
	LoginDemo()
	{
		int admincheck1 = admincheck;
		label1 = new JLabel();
		label1.setText("Username");
		text1 = new JTextField(15);
		
		label2 = new JLabel();
		label2.setText("Password");
		text2 = new JPasswordField(15);
		
		SUBMIT = new JButton("sign in");
		SIGNUP = new JButton("sign up");
		
		panel = new JPanel(new GridLayout(3,1));
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(SUBMIT);
		panel.add(SIGNUP);
		add(panel, BorderLayout.CENTER);
		SUBMIT.addActionListener(this);
		SIGNUP.addActionListener(this);
		setTitle("LOGIN FORM");		
		javax.swing.Action ok = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
		text2.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter,  "ENTER");
		text2.getActionMap().put("ENTER", ok);
	}

	





	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		String value = text1.getText();
		String value2 = text2.getText();

		Object src = ae.getSource();
		if(src==SUBMIT){
			Connection con = null;
			PreparedStatement pstm = null;
			try {
				String url = "jdbc:mysql://192.168.17.128:3306/tw1";
				String id="GBK_Admin";
				String pw = "!1234abcd";
				
				Class.forName("com.mysql.jdbc.Driver");
				
				con=DriverManager.getConnection(url,id,pw);
				
				System.out.println("제대로 연결되었습니다");
		
				pstm = con.prepareStatement("SELECT * FROM login where id=? and password=PASSWORD(?)");
				pstm.setString(1, text1.getText());
				pstm.setString(2, text2.getText());
				ResultSet rs = pstm.executeQuery();
			
				
				if(true==rs.next()) {
					admincheck = rs.getInt("admin");
					IDcheck = rs.getString("name");
					//if(admincheck == 3){
						System.out.println("로그인 성공");
						
						Main mai = new Main();
						
						
					
						System.out.println(admincheck);
						System.out.println(value);
						mai.pack();
						
						mai.setVisible(true);
						mai.setAdmincheck(admincheck);
						mai.setIdcheck(value);
						mai.getContentPane();
						z = mai.excel;
						dispose();
						
						
								
						/*NextPage page = new NextPage();			
						page.setVisible(true);
						JLabel label = new JLabel("Welcome:" + value + "권한 : " +  "Administrator");
						p
						age.getContentPane().add(label);
						dispose(); */
					//}
					}
					else{
						JOptionPane.showMessageDialog(this, "Password or ID is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
					}
			} catch(Exception e){
				e.printStackTrace();
				
				System.out.println("실패");
			} finally {
				if(pstm != null) try {pstm.close();} catch(SQLException sqle){}
				if(pstm != null) try {con.close();} catch(SQLException sqle){}
			}	
					
	}
		else if(src==SIGNUP) {
			SignUp signup = new SignUp();
			signup.setVisible(true);
			
		}
	}

}
/*public class LoginDemo 
{
	
	static HSSFRow row;
	static HSSFCell cell;
	public static void main(String arg[] )
	{
	
			System.out.println(Login.z);
		
		
		int admincheck2;
		try
		{
			
			Login frame = new Login();
			admincheck2 = frame.admincheck;
			frame.setSize(300, 100);
			frame.setVisible(true);
			
			/*if(frame.z==3){
				HSSFWorkbook workbook = new HSSFWorkbook();
				//Sheet명 설정
				HSSFSheet sheet = workbook.createSheet("mySheet");
				
				/* xlsx 파일 출력시 선언
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("mySheet");
				



				//출력 row 생성
				row = sheet.createRow(0);
				//출력 cell 생성
				row.createCell(0).setCellValue("DATA 11");
				row.createCell(1).setCellValue("DATA 12");
				row.createCell(2).setCellValue("DATA 13");

				//출력 row 생성
				row = sheet.createRow(1);
				//출력 cell 생성
				row.createCell(0).setCellValue("DATA 21");
				row.createCell(1).setCellValue("DATA 22");
				row.createCell(2).setCellValue("DATA 23");

				row = sheet.createRow(2);
				//출력 cell 생성
				row.createCell(0).setCellValue("DATA 31");
				row.createCell(1).setCellValue("DATA 32");
				row.createCell(2).setCellValue("DATA 33");

				// 출력 파일 위치및 파일명 설정
				FileOutputStream outFile;
				try {
					outFile = new FileOutputStream("XlsWrite.xls");
					workbook.write(outFile);
					outFile.close();
					
					System.out.println("파일생성 완료");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				}
		*/
			
	/*	} catch(Exception e)
		{JOptionPane.showMessageDialog(null, e.getMessage());}
	
}*/
