import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



class Id extends JFrame implements ActionListener{
	static JTextField tf = new JTextField(8);
	JButton btn = new JButton("input");

	WriteThread wt;
	ClientFrame cf;

	public Id() {}
	public Id(WriteThread wt, ClientFrame cf)
	{
		super("ID");
		this.wt = wt;
		this.cf = cf;
		
		setLayout(new FlowLayout());
		add(new JLabel("ID"));
		add(tf);
		add(btn);
		
		btn.addActionListener(this);
		
		setBounds(300, 300, 250, 100);
		setVisible(true);
	}	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		wt.sendMsg();
		cf.isFirst = false;
		cf.setVisible(true);
		this.dispose();
		
	}
	static public String getId() {
		return tf.getText();
	}
	
}

public class ClientFrame extends JFrame implements ActionListener{

	JTextArea txtA = new JTextArea();
	JTextField txtF = new JTextField(15);
	JButton btnTransfer = new JButton("submit");
	JButton btnExit = new JButton("Exit");
	boolean isFirst = true;
	JPanel p1 = new JPanel();
	Socket socket;
	WriteThread wt;
	
	public ClientFrame(Socket socket) {
		super("Chat with Admin");
		this.socket = socket;
		wt = new WriteThread(this);
		new Id(wt, this);
		
		add("Center", txtA);
		
		p1.add(txtF);
		p1.add(btnTransfer);
		p1.add(btnExit);
		add("South", p1);
		
		//transfr message processing code
		
		btnTransfer.addActionListener(this);
		btnExit.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(300, 300, 350, 300);
		setVisible(false);
	}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	String id = Id.getId();
	if(e.getSource()==btnTransfer) //if press the Submit button 
	{ //Press the submit button without message
		if(txtF.getText().equals(""))
		{
			return;
		}
		txtA.append("["+id+"]" + txtF.getText() +"\n");
		wt.sendMsg();
		txtF.setText("");
	} else {
		this.dispose();
	}
	
}
}
