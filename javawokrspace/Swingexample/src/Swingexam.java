import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
public class Swingexam implements ActionListener {
	
	public void login(){
		JFrame frame1 = new JFrame();
		JPanel panel = new JPanel();
		
		JTextField id1 = new JTextField();
		JLabel jl1 = new JLabel();
		jl1.setText("I D : ");
		
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(jl1);
		panel.add(id1);
		
		//textfield setting
		id1.setColumns(20);
		id1.setText("Input your ID");	
		id1.addActionListener(this);
		id1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				id1.setText("");
			}
			
		});
		
		frame1.setTitle("LOGIN");
		frame1.setSize(300, 300);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.add(panel);
		frame1.pack();
		frame1.setVisible(true);
		
		
		
		
	}
	
	public void go(){
		JFrame frame = new JFrame();
		JMenuBar jmenubar = new JMenuBar();
		frame.setJMenuBar(jmenubar);
		JMenu jmenu = new JMenu("Menu Test");
		jmenubar.add(jmenu);
		JMenuItem jm1, jm2, jm3;
		jm1 = new JMenuItem("Text Only");
		jm2 = new JMenuItem("Text and Icon", new ImageIcon("javalogo.gif"));
		jm3 = new JMenuItem(new ImageIcon("duke.gif"));
		jmenu.add(jm1);
		jmenu.add(jm2);
		jmenu.add(jm3);
		frame.getContentPane().add(new JTextArea(), "Center");
		frame.setSize(200, 100);
		frame.setVisible(true);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Swingexam().login();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
