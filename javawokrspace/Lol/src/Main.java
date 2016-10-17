import java.sql.Connection;
import java.sql.Statement;
import java.util.Vector;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class Main extends JFrame implements ActionListener{
	private int admincheck;
	private String idcheck;
	public int excel;
	public int getAdmincheck()
	{
		return admincheck;
	}
	public void setAdmincheck(int a)
	{
		this.admincheck = a;
	}
	
	
	public String getIdcheck() {
		return idcheck;
	}
	public void setIdcheck(String idcheck) {
		this.idcheck = idcheck;
	}


	JLabel label, known, id, cate, item, unit, qty, check;
	JTextField fid, fcate, fitem, funit, fqty, fcheck, search;
	JButton add, delete, update, clear, all, subutton;
	String items[] = {"Cate", "Item", "Unit", "Qty", "Enough"};
	JComboBox combo;
	JPanel bottompanel,leftpanel,center, pid,pname,pitem,punit,pqty, pcheck;
	Font f1;
	
	JScrollPane sp;
	Vector outer,title,noresult,msg; //noresult: 검색결과 없을때 테이블 때문에
	JTable table;
	DefaultTableModel model;
	
	String connect = new String("jdbc:mysql://192.168.17.128:3306/tw1");
	String user = new String("GBK_Admin");
	String passwd = new String("!1234abcd");
	
	Connection conn;
	Statement stat;
	PreparedStatement pre1, pre2, pre3;
	ResultSet rs;
	private JMenuItem mntmSave;
	private JMenuItem mntmNewMenuItem;
	private JMenu mnNewMenu;
	private JMenuItem mntmExcel;
	JMenuItem mntmChat;
	
	
	public Main() {
		
		makeGui(); //화면구성
		prepareDB(); //DB 준비작업
		select(null); //첫화면에서 테이블의 모든 내용 보여주기 위해 Select하는 함수
		model.setDataVector(outer, title);
	
	}
	
	public void makeGui() {
		f1 = new Font("Times", Font.BOLD, 14);
		LoginDemo  lo1 = new LoginDemo();
		this.admincheck = lo1.admincheck;
		this.idcheck = lo1.IDcheck;
		label = new JLabel(""
				+ "IT inventory", JLabel.CENTER);
		id = new JLabel("I D : " , JLabel.CENTER);
		
		
		if(admincheck==3)
		{
			known = new JLabel("name : " + idcheck +"    authority :  Admin");
		}
		else
			
			
		{
			known = new JLabel("name : " + idcheck +"    authority :  Guest");
		}
		known.setFont(f1);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile); 
		
		mntmSave = new JMenuItem("Logout");
		mnFile.add(mntmSave);
		mntmSave.addActionListener(this);
		
		mnNewMenu = new JMenu("import");
		mnFile.add(mnNewMenu);
		
		mntmExcel = new JMenuItem("Excel");
		mnNewMenu.add(mntmExcel);
		mntmExcel.addActionListener(this);
		
		mntmChat = new JMenuItem("Chat");
		mnFile.add(mntmChat);
		mntmChat.addActionListener(this);
		mntmNewMenuItem = new JMenuItem("");
		mnFile.add(mntmNewMenuItem);
		
		fid = new JTextField(15);
		pid = new JPanel();
		pid.add(id);
		pid.add(fid);
		
		cate = new JLabel("Category : ");
		fcate = new JTextField(15);
		pname = new JPanel();
		pname.add(cate);
		pname.add(fcate);
	
		item = new JLabel("Item : ");
		fitem = new JTextField(15);
		pitem = new JPanel();
		pitem.add(item);		
		pitem.add(fitem);
		
		unit = new JLabel("Unit : " );
		funit = new JTextField(15);
		punit = new JPanel();
		punit.add(unit);
		punit.add(funit);
		
		qty = new JLabel("qty : ");
		fqty = new JTextField(15);
		pqty = new JPanel();
		pqty.add(qty);
		pqty.add(fqty);		
	
		leftpanel = new JPanel();
		leftpanel.setLayout(new GridLayout(7, 1));
		leftpanel.add(known);
		leftpanel.add(pid);
		leftpanel.add(pname);
		leftpanel.add(pitem);
		leftpanel.add(punit);
		leftpanel.add(pqty);
	
		
		add = new JButton("Add");
		add.addActionListener(this);
		if(admincheck != 3)
		add.setEnabled(false);
		
		delete = new JButton("Delete");
		delete.addActionListener(this);
		if(admincheck != 3)
			delete.setEnabled(false);
		
		update = new JButton("Modify");
		update.addActionListener(this);
		if(admincheck != 3)
			update.setEnabled(false);		
		combo = new JComboBox(items);
		
		search = new JTextField(15);
		search.addActionListener(this);
		
		subutton = new JButton("search");
		subutton.addActionListener(this);
		
		clear = new JButton("Clear");
		clear.addActionListener(this);
		
		all = new JButton("See All");
		all.addActionListener(this);
		
		bottompanel = new JPanel();
		bottompanel.add(add);
		bottompanel.add(delete);
		bottompanel.add(update);
		bottompanel.add(combo);
		bottompanel.add(search);
		bottompanel.add(subutton);
		bottompanel.add(clear);
		bottompanel.add(all);
		
		title = new Vector();
		outer = new Vector();
		noresult = new Vector();
		msg = new Vector();
		title = new Vector();
		
		title.add("Id");
		title.add("Category");
		title.add("Item");
		title.add("Unit");
		title.add("Qty");
	
		
		setTitle("Grand BK IT Inventory Check System");
		
		noresult.add("Processing");
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent w) {
				// TODO Auto-generated method stub
				try {
					rs.close();
					stat.close();
					conn.close();
					
					setVisible(false);
					dispose();
					System.exit(0);
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				super.windowClosing(w);
			}
			
			
		});
		
		Container c = getContentPane();
		
		//DefaultTableModel Create
		model = new DefaultTableModel();
		
		//Using model create JTable
		table = new JTable(model);
		
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent me) {
				// TODO Auto-generated method stub
				//To know index what you click.				
				int index = table.getSelectedRow();
				
				//Using index, out안의 작은 벡터 in 꺼내기
				Vector msg = (Vector)outer.get(index);
				
				//in안에 들어있는 번호, 이름, 주소 알아내서 텍스트 필드에 넣어주기
				String id = String.valueOf(msg.get(0));
				String cate = String.valueOf(msg.get(1));
				String item = String.valueOf(msg.get(2));
				String unit = String.valueOf(msg.get(3));
				String qty = String.valueOf(msg.get(4));
				
				/*String cate = (String)msg.get(1);
				String item = (String)msg.get(2);
				String unit = (String)msg.get(2);
				String qty = (String)msg.get(2);
				String check = (String)msg.get(2); */
				
				fid.setText(id);
				fcate.setText(cate);
				fitem.setText(item);
				funit.setText(unit);
				fqty.setText(qty);
			
				
				fid.setEditable(false);
				
				
				super.mouseClicked(me);
			}
			
		});
		sp = new JScrollPane(table);
		
		center = new JPanel();
		center.add(leftpanel);
		center.add(sp);
		
		c.add(label, "North");
		//c.add(known, "North");
		c.add(center, "Center");
		c.add(bottompanel, "South");
		
	}
	
	public void prepareDB(){
		
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			conn=DriverManager.getConnection(connect, user, passwd);
			
			stat = conn.createStatement();
			
			pre1 = conn.prepareStatement("insert into Inventory values(?,?,?,?,?)");
			pre2 = conn.prepareStatement("delete from Inventory where catenum= ?");
			pre3 = conn.prepareStatement("update Inventory set Category=?, Item=?, Unit=?, Qty=? where catenum=?");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
	}
	
	public void add() {
		
		String id = fid.getText();
		String cate = fcate.getText();
		String item = fitem.getText();
		String unit = funit.getText();
		String qty = fqty.getText();				
		
try {		
	
	pre1.setString(1,  id);
	pre1.setString(2,  cate);
	pre1.setString(3,  item);
	pre1.setString(4,  unit);
	pre1.setString(5,  qty);
	

	
	pre1.executeUpdate();
	
} catch (SQLException e) {
	
	// TODO: handle exception
	e.printStackTrace();
}
	}
	
	public void delete(){
		String id = fid.getText();
		
		try {
			pre2.setString(1, id);
			pre2.executeUpdate();
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void update()
	{
		String id = fid.getText();	
		String cate = fcate.getText();
		String item = fitem.getText();
		String unit = funit.getText();
		String qty = fqty.getText();		
		
		
		try {			
			pre3.setString(1, cate);
			pre3.setString(2, item);
			pre3.setString(3, unit);
			pre3.setString(4,  qty);		
			pre3.setString(5,  id);
			
			pre3.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	
	public void search() {
		String keyword = search.getText();
		
		int i = combo.getSelectedIndex();
		
		String index;
		if(i==0)
			index = "Category";
		else if(i==1)
			index = "Item";
		else
			index="Enough";
		String q = "select * from Inventory where "+index+"="+keyword+" order by catenum";
		select(q);
		search.setText("");
			
	}
	
	public void clear() {
		fid.setText("");
		fcate.setText("");
		fitem.setText("");
		funit.setText("");
		fqty.setText("");	
		fid.setEditable(true);
	}
	
	public void select(String query){
		try {
			if(query==null)
				query = "select * from Inventory order by catenum";
			
			rs=stat.executeQuery(query);
			outer.clear();
			while(rs.next()){
				msg = new Vector<String>();
				
				msg.add(rs.getString(1));
				msg.add(rs.getString(2));
				msg.add(rs.getString(3));
				msg.add(rs.getString(4));
				msg.add(rs.getString(5));				
				outer.add(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}	

	@Override
	public void actionPerformed(ActionEvent w) {
		// TODO Auto-generated method stub
		Object o = w.getSource();
		
		try {
			if(o==add) //add
			{
				if(fid.getText().length()>0)
				add();
				select(null);
					
			}else if(o==delete) //delete
			{
				if(fid.getText().length()>0)
					delete();
				select(null);
				
			}else if(o==clear)
			{
				clear();
				
			}else if(o==all)
			{
				select(null);
			}
			
			
			else if(o==update)
			{
				if(fid.getText().length()>0)
					update();
				select(null);
			}else if(o==search||o==subutton)
			{
			search();	
			}else if(o==mntmExcel)
			{
				excel = 3;
				
			}else if (o==mntmChat){
			MultiChatServer mul1 = new MultiChatServer();
			mul1.Multi();
		//	mu1.Socket();
			}
			else if (o==mntmSave){
				
				
				LoginDemo lo1 = new LoginDemo();
				lo1.setSize(300,100);
				lo1.setVisible(true);
				dispose();
			}
			
			//model로 값 설정
			if(outer.isEmpty()){
				outer.clear();
				
				msg.clear();
				
				msg.add("Data is not found");
				outer.add(msg);
				model.setDataVector(outer, title);
				
				
			}			
			
			clear();
		} catch (Exception ew) {
			// TODO: handle exception
			ew.printStackTrace();
		}
	}
	
	
	public static void main(String arg[])
	{
		try
		{
			
		LoginDemo frame = new LoginDemo();		
		frame.setSize(300, 100);
		System.out.println(frame.admincheck);
		frame.setVisible(true);
		}catch(Exception e)
		
		{JOptionPane.showMessageDialog(null, e.getMessage());}
	}
}
