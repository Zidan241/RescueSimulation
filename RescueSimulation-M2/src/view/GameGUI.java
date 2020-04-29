package view;


import java.io.FileInputStream;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.List;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;
import javax.swing.text.StyleConstants.FontConstants;

import model.people.Citizen;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;

@SuppressWarnings("serial")
public class GameGUI extends JFrame 
{
	
	
	public JButton b[][] = new JButton[10][10];
	public JPanel RescuePanel;
	private JPanel UnitsPanel;
	private JPanel TextPanel;
	private JPanel ExtraButtons;
	private JTextArea Logs;
	private JTextArea InfoPanel;
	private JPanel UnitsIdle;
	private JPanel UnitsResponding;
	public JPanel UnitSelect;
	public Font f1 =  new Font("times new roman",Font.PLAIN,20);
	public Font f2 =  new Font("times new roman",Font.PLAIN,20);
	public Font f3 =  new Font("times new roman",Font.PLAIN,20);
	public JTextArea ActiveDisasters;
	public Clip clip;

	
	
	

	
	public GameGUI(){
		try {
			 String soundName = "sound.wav";    
			 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			 clip = AudioSystem.getClip();
			 clip.open(audioInputStream);
			 clip.start();
			 FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			 control.setValue(-20.0f);
			 clip.loop(Clip.LOOP_CONTINUOUSLY);
			 
		}
		catch(Exception e) {
			
		}
		
		
		try {
			 f1=  Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("MyFont.ttf"))).deriveFont(Font.PLAIN,25);
			 f2= Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Minecraft.ttf"))).deriveFont(Font.PLAIN,20); 
			 f3= Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("INFECTED.ttf"))).deriveFont(Font.PLAIN,30); 
						
		} 
		catch (Exception e) {
		}
		 	
			
	
	 //setTitle("Game");
	 //setSize(1500,1000);
	 //setBounds(50,50,800,600);
	 setExtendedState(JFrame.MAXIMIZED_BOTH); 
	 //setUndecorated(true);
	 setVisible(true);
	 setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		
	 
	 RescuePanel = new JPanel();
	 RescuePanel.setBackground(Color.DARK_GRAY);
	 RescuePanel.setLayout(new GridLayout(10, 10));
	 add(RescuePanel,BorderLayout.CENTER);
	 RescuePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "MAP", TitledBorder.CENTER, TitledBorder.ABOVE_TOP,f3, Color.red));
	 
	 TextPanel = new JPanel();
	 TextPanel.setBorder(BorderFactory.createEmptyBorder());;
	  TextPanel.setLayout(new GridLayout(3, 1,0,10));
	  TextPanel.setPreferredSize(new Dimension(300, getHeight()));
	  add(TextPanel, BorderLayout.WEST);
	  
	  UnitsPanel = new JPanel();
	  UnitsPanel.setPreferredSize(new Dimension(200, getHeight()));
	  add(UnitsPanel, BorderLayout.EAST);
	 // UnitsPanel.setBorder(BorderFactory.createTitledBorder(null, "Units", TitledBorder.CENTER, TitledBorder.TOP,  f1.deriveFont(Font.PLAIN,15), Color.blue));
	  UnitsPanel.setLayout(new GridLayout(3, 1,0,10));
	  
	  UnitsIdle = new JPanel();
	  UnitsIdle.setBackground(Color.DARK_GRAY);
	  UnitsIdle.setLayout(new GridLayout(5, 1));
	  UnitsIdle.setBorder(BorderFactory.createTitledBorder(null, "Units", TitledBorder.CENTER, TitledBorder.TOP, f1.deriveFont(Font.PLAIN,15), Color.orange));
	  UnitsPanel.add(UnitsIdle);
	  
	  
	  
	  UnitsResponding = new JPanel();
	  JScrollPane p5 = new JScrollPane(UnitsResponding);
	  JPanel j1= new JPanel(new GridLayout(1,1));
	  j1.add(p5);
	  j1.setBackground(Color.DARK_GRAY);
	  UnitsResponding.setBackground(Color.DARK_GRAY);
	  UnitsResponding.setLayout(new GridLayout(5, 1));
	  j1.setBorder(BorderFactory.createTitledBorder(null, "Active Units", TitledBorder.CENTER, TitledBorder.TOP,f1.deriveFont(Font.PLAIN,15), Color.orange));
	  UnitsPanel.add(j1);
	  
	  UnitSelect = new JPanel();
	  UnitSelect.setBackground(Color.DARK_GRAY);
	  JScrollPane p3 = new JScrollPane(UnitSelect);
	  JPanel j5= new JPanel(new GridLayout(1,1));
	  j5.setBackground(Color.DARK_GRAY);
	  j5.add(p3);
	  p3.setVisible(true);
	  j5.setBorder(BorderFactory.createTitledBorder(null, "Select Units", TitledBorder.CENTER, TitledBorder.TOP, f1.deriveFont(Font.PLAIN,15), Color.orange));
	  UnitsPanel.add(j5);
	  p3.setBorder(BorderFactory.createEmptyBorder());

	  
	  ExtraButtons = new JPanel(new BorderLayout());
	  ExtraButtons.setBorder(BorderFactory.createEmptyBorder());;
	  ExtraButtons.setPreferredSize(new Dimension(getWidth(), 100));
	  ExtraButtons.setLayout(new GridLayout(1,3));
	  ExtraButtons.setBorder(BorderFactory.createEmptyBorder());
	  add(ExtraButtons, BorderLayout.SOUTH);

	  InfoPanel = new JTextArea();
	  InfoPanel.setFont(f2);
	  InfoPanel.setForeground(Color.white);
	  

	  InfoPanel.setEditable(false);
	  InfoPanel.setBackground(Color.DARK_GRAY);
	  JScrollPane p1 = new JScrollPane(InfoPanel);
	  JPanel j3= new JPanel(new GridLayout(1,1));
	  j3.setBackground(Color.DARK_GRAY);
	  j3.add(p1);
	  j3.setBorder(BorderFactory.createTitledBorder(null, "Info", TitledBorder.CENTER, TitledBorder.TOP,f1, Color.orange));
	  TextPanel.add(j3);
	  p1.setVisible(true);
	  p1.setBorder(BorderFactory.createEmptyBorder());
	  
	  InfoPanel.setLineWrap(true);
	  InfoPanel.setWrapStyleWord(true);
	 
	  Logs = new JTextArea();
	  Logs.setFont(f2);
	  Logs.setForeground(Color.white);
	  Logs.setEditable(false);
	  Logs.setBackground(Color.DARK_GRAY);
	  JScrollPane p2 = new JScrollPane(Logs);
	  JPanel j2= new JPanel(new GridLayout(1,1));
	  j2.setBackground(Color.DARK_GRAY);
	  j2.add(p2);
	  j2.setBorder(BorderFactory.createTitledBorder(null, "Logs", TitledBorder.CENTER, TitledBorder.TOP, f1, Color.orange));
	  p2.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
	        public void adjustmentValueChanged(AdjustmentEvent e) {
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());
	        }
	    });
	  p2.setBorder(BorderFactory.createEmptyBorder());
	  
	  
	  p2.setVisible(true);
 
	  Logs.setLineWrap(true);
	  Logs.setWrapStyleWord(true);
	  
	  ActiveDisasters= new JTextArea();
	  ActiveDisasters.setFont(f2);
	  ActiveDisasters.setForeground(Color.white);
	  

	  ActiveDisasters.setEditable(false);
	  ActiveDisasters.setBackground(Color.DARK_GRAY);
      JScrollPane p4 = new JScrollPane(ActiveDisasters);
      JPanel j4= new JPanel(new GridLayout(1,1));
	  j4.setBackground(Color.DARK_GRAY);
	  j4.add(p4);
      j4.setBorder(BorderFactory.createTitledBorder(null, "Disasters", TitledBorder.CENTER, TitledBorder.TOP, f1, Color.ORANGE));
      TextPanel.add(j4);
      p2.setVisible(true);
      p4.setBorder(BorderFactory.createEmptyBorder());
      
      ActiveDisasters.setLineWrap(true);
      ActiveDisasters.setWrapStyleWord(true);
      
      TextPanel.add(j2);
	  
	}
	
	public void addLogs(String s) {
		Logs.setText(Logs.getText()+"\n"+s);
		Logs.setCaretPosition(0);
	}
	
	public void addButton(JComponent button) {
		button.setFont(f1);
		ExtraButtons.add(button);
		
	}
	public void loadMap()
	{
		  for (int i = 0; i < 10; i++) {
		      for (int j = 0; j < 10; j++)
		      {
		    	  if(b[i][j]==null)
		    	  {
		    	  JButton bu =  new JButton();
		    	  
		    	  ImageIcon icon = new ImageIcon("grass.png");
				  bu.setIcon(icon);
				  float[] hsb = Color.RGBtoHSB(82, 162, 16, null);
				  bu.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(hsb[0],hsb[1],hsb[2]), 2));
		    	  b[i][j] = bu;
		    	  }
		    	  RescuePanel.add(b[i][j]); 
				b[i][j].putClientProperty("Citizens", new ArrayList<Citizen>());
				b[i][j].putClientProperty("Flag", false);


		      }
		  }
		  
		 b[0][0].add( new JLabel(new ImageIcon("base.gif")));
	}
	
	
	public void addText(String s) {
		InfoPanel.setText(s);
		InfoPanel.setCaretPosition(0);
	}
	public String getText() {
		return InfoPanel.getText();

	}
	public void clearText() {
		InfoPanel.setText("");
	}
	public void addIdleUnit(JButton unit) {
		UnitsIdle.add(unit);
	}
	public void removeIdleUnit(JButton unit) {
		UnitsIdle.remove(unit);
	}
	public void addRespondingUnit(JButton unit) {
		UnitsResponding.add(unit);
	}
	public void removeRespondingUnit(JButton unit) {
		UnitsResponding.remove(unit);
	}
	public boolean checkisResponding(JButton unit) {
		if(unit.getParent().equals(UnitsResponding))return true;
		else return false;
	}
	
}


