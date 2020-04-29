package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import controller.CommandCenter;

public class GameOver {
	public Font f1 =  new Font("times new roman",Font.PLAIN,20);
	public Font f2 =  new Font("times new roman",Font.PLAIN,20);
	public JButton t1;
	public JButton t2;

public GameOver()
{
	 try {
		 f1=  Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("MyFont.ttf"))).deriveFont(Font.PLAIN,25);
		 f2= Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Minecraft.ttf"))).deriveFont(Font.PLAIN,20); 
					
	} catch (FontFormatException e) {
	} catch (IOException e) {
	}
JFrame start = new JFrame();
JLabel pic = new JLabel(new ImageIcon("gameover.gif"));
pic.setLayout(new GridLayout(1,3));
start.getContentPane().add(pic);
start.setUndecorated(true);
start.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
start.setVisible(true);
start.validate();
start.repaint();
JPanel main = new JPanel();
JPanel ma = new JPanel( new BorderLayout());
main.setLayout(new GridLayout(2,1));
JButton startBut =new JButton("Reset");
JButton exitBut =new JButton("Valar Morghulis");
ma.add(main,BorderLayout.SOUTH);
startBut.setPreferredSize(new Dimension(100, 25));
startBut.setFont(f1.deriveFont(Font.PLAIN, 10));
startBut.setBackground(Color.LIGHT_GRAY);
startBut.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		start.dispose();
		try {
			CommandCenter cn= new CommandCenter();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
});
exitBut.setPreferredSize(new Dimension(100, 25));
exitBut.setFont(f1.deriveFont(Font.PLAIN, 7));
exitBut.setBackground(Color.LIGHT_GRAY);
exitBut.addActionListener( new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		start.dispose();

	}
});

main.setPreferredSize(new Dimension(100, 48));
JPanel main2 = new JPanel();
main2.setLayout(new GridLayout(5,1));
main2.setOpaque(false);
JPanel temp1 = new JPanel();
JPanel temp2 = new JPanel();
temp1.setOpaque(false);
temp2.setOpaque(false);
JPanel temp3 = new JPanel();
JPanel temp4 = new JPanel();
temp3.setOpaque(false);
temp4.setOpaque(false);
ma.setOpaque(false);

main2.add(temp1);
main2.add(temp3);
main2.add(temp2);
main2.add(temp4);

main2.add(ma);

t1 = new JButton();
t2 = new JButton();
t1.setFont(f2.deriveFont(Font.PLAIN,10));
t1.setBackground(Color.orange);
t2.setFont(f2.deriveFont(Font.PLAIN,10));
t2.setBackground(Color.orange);

//main2.add(main,BorderLayout.CENTER);
main.add(t1);
main.add(t2);
main.add(startBut);
main.add(exitBut);
main.setOpaque(false);
JPanel temp5 = new JPanel();
JPanel temp6 = new JPanel();
temp5.setOpaque(false);
temp6.setOpaque(false);
pic.add(temp5);
pic.add(main2);

pic.add(temp6);
start.add(pic);
start.pack();
start.validate();
start.repaint();
start.setLocationRelativeTo(null);
}
public static void main (String []args)
{
GameOver cc = new GameOver();	
}
}
