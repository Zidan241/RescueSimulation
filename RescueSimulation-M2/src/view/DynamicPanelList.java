package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import controller.CommandCenter;
import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Unit;

public class DynamicPanelList {
    public ArrayList<Citizen>CitizensOfBuilding;
    public Unit u ;
    private JPanel mainList;
    public String output ;

    public DynamicPanelList(ArrayList<Citizen>CitizensOfBuilding,Unit u,GameGUI m,JButton ub,CommandCenter cm) {
                JFrame frame = new JFrame();
                frame.setUndecorated(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setBackground(new Color(232,213,12));                

             //   ImageIcon v = new ImageIcon("panelBackground.png");
                mainList = new JPanel(new GridBagLayout());
                JPanel P = new JPanel();
                P.setBackground(new Color(232,213,12));                
                frame.revalidate();
        	this.CitizensOfBuilding = CitizensOfBuilding;
        	this.u=u;
            P.setLayout(new BorderLayout());
           
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.weighty = 1;
           // mainList.add(new JPanel(), gbc);
            JScrollPane k =new JScrollPane(mainList); 
            k.setBorder(BorderFactory.createEmptyBorder());
            P.add(k);				
            m.clearText();
            m.addText("Citizens at this Location: \n");
            for (Citizen c :CitizensOfBuilding) {
                	m.addText(m.getText()+cm.printCitizen(c)+"\n========================\n");
                	JPanel box = new JPanel();
                	
                //	box.add(new ImageIcon("panelButton.png"));
                	JPanel infoCitizen = new JPanel();
                	infoCitizen.setLayout(new BorderLayout());
                	JLabel backgroundBut = new JLabel(new ImageIcon("panelButton.png"));
                	backgroundBut.setLayout(new FlowLayout());
                	JLabel cit;
                	if(c.getState()==CitizenState.DECEASED) cit = new JLabel(new ImageIcon("dead.gif"));
                	else cit = new JLabel(new ImageIcon("Citizen.gif"));
                	JTextArea name = new JTextArea("Name:"+c.getName());
                	name.setBackground(Color.yellow);
                	name.setEditable(false);
                	JTextArea health = new JTextArea("HP:"+c.getHp());
                	health.setBackground(Color.yellow);
                	health.setEditable(false);
                	name.setFont(m.f2.deriveFont(Font.BOLD,18));
                	health.setFont(m.f2.deriveFont(Font.BOLD,18));
                	infoCitizen.add(name,BorderLayout.NORTH);
                	infoCitizen.add(health,BorderLayout.SOUTH);
                	JButton but = new JButton("Treat");
                	but.setFont(m.f2);
                	but.setBackground(Color.yellow);
          	       
                	box.setLayout(new BorderLayout());
                	box.add(BorderLayout.WEST,infoCitizen);
                	box.add(BorderLayout.EAST,but);
         	       
         	       but.addActionListener( new ActionListener() {
   					@Override
   					public void actionPerformed(ActionEvent arg0) {
   						int dialogButton = JOptionPane.showConfirmDialog (null, "Are you sure?","WARNING",JOptionPane.YES_NO_OPTION);
			    	    if(dialogButton == JOptionPane.YES_OPTION) {
   					try {
   						//m.clearText();
   						m.setEnabled(true);
   						u.respond(c);
   						//m.addLogs(u.getClass().getSimpleName()+" "+u.getUnitID()+ " is on its way to " + c.getName() +" in building "+ " ("+c.getLocation().getX()+","+c.getLocation().getY()+")");
   		    			boolean flag = false;
   		    			for(JButton n :cm.RespondingUnits)
		    			{
		    			if(n.getClientProperty("ButtonUnit").equals(u))
		    			{
		    			flag =true;
		    			break;
		    			}
		    			}
		    			if(!flag)
		    			{
		    				cm.RespondingUnits.add(cm.UnitNow);
					    	m.addRespondingUnit(cm.UnitNow);
		    			}
		    			cm.SuperButton.setBackground(Color.DARK_GRAY);
				    	
				    	cm.UnitNow=null;
		    			cm.SuperButton = null;
		    			
		    			m.UnitSelect.removeAll();
   		    			m.revalidate();
   						m.repaint();
   						frame.dispose();

   					} 
   					catch(IncompatibleTargetException it) {
   						frame.dispose();
		    			JOptionPane.showMessageDialog(null, new JTextArea("Incompatible Target!"));
		    			
		    		}
		    		catch(CannotTreatException ct) {
		    			frame.dispose();
		    			JOptionPane.showMessageDialog(null, new JTextArea("Cannot Treat Target!"));
		    		}
   					catch(CitizenAlreadyDeadException cd) {
   						frame.dispose();
   						JOptionPane.showMessageDialog(null, new JTextArea("Citizen Already Dead!"));
   					}
   					catch(BuildingAlreadyCollapsedException bc) {
   						frame.dispose();
   						JOptionPane.showMessageDialog(null, new JTextArea("Building Already Collapsed!"));
   					}
   					}else {
   						m.setEnabled(true);
   						frame.dispose();
   					}
   						
   					
			    	    
   					}});
                    gbc.gridwidth = GridBagConstraints.REMAINDER;
                    gbc.weightx = 1;
                    gbc.gridheight = 1;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    backgroundBut.add(cit);
                    backgroundBut.setBackground(new Color (Color.TRANSLUCENT));                
                    backgroundBut.add(box);
                    mainList.add(backgroundBut, gbc);                  
        }
                	JButton cancel = new JButton("Cancel");
                	cancel.setBackground(Color.LIGHT_GRAY);
                	cancel.setFont(m.f1);
                    cancel.addActionListener(new ActionListener() {
        				
        				@Override
        				public void actionPerformed(ActionEvent e) {
        					m.setEnabled(true);
        					frame.dispose();
        					
        				}
        			} );
                   
                   mainList.add(cancel);
                   mainList.setBackground(new Color(232,213,12));                
                   mainList.repaint();
                   mainList.validate();
                  
                   mainList.setBorder(BorderFactory.createTitledBorder(null, "CitizenInLocation", TitledBorder.CENTER, TitledBorder.ABOVE_TOP,m.f1.deriveFont(Font.PLAIN,20), Color.black));
                   JLabel testLabel = new JLabel((new ImageIcon("panelBackground.png")));
                   testLabel.setLayout(new FlowLayout());
                   testLabel.setBackground(new Color(232,213,12));                

                   testLabel.add(P);
                   P.setBorder(BorderFactory.createEmptyBorder());
                   P.repaint();
                   P.revalidate();
                   frame.setContentPane(testLabel);     
            frame.setMaximumSize(new Dimension(50, 50));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.validate();
            frame.repaint();

    }
    }
