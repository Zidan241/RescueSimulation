package controller;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.DisasterException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.disasters.Disaster;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.FireUnit;
import model.units.GasControlUnit;
import model.units.MedicalUnit;
import model.units.PoliceUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Rescuable;
import simulation.Simulator;
import tests.M2PrivateTests.MyDisaster;
import view.DynamicPanelList;
import view.GameGUI;
import view.GameOver;

public class CommandCenter implements SOSListener {

	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	
	private  GameGUI myGui;
	
	public JButton UnitNow;
	public JButton SuperButton;
	
	public ArrayList<JButton> RespondingUnits;

	@SuppressWarnings("unused")
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Unit> Ambulance;
	private ArrayList<Unit> GasControlUnit;
	private ArrayList<Unit> Evacuator;
	private ArrayList<Unit> DiseaseControlUnit;
	private ArrayList<Unit> FireTruck;
	public ArrayList<Citizen> AllCitizens;
	public ArrayList<Citizen> CitizensWithDisaster;
	
	public JButton UnitNowCheck;

	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();
		myGui= engine.myGu;
		Ambulance = new ArrayList<Unit>();
		GasControlUnit = new ArrayList<Unit>();
		Evacuator = new ArrayList<Unit>();
		DiseaseControlUnit = new ArrayList<Unit>();
		FireTruck = new ArrayList<Unit>();
		
		RespondingUnits = new ArrayList<JButton>();
		
		CitizensWithDisaster = new ArrayList<Citizen>();
		
		AllCitizens = new ArrayList<Citizen>();
		
		for(Citizen c : engine.getCitizens()) {
			AllCitizens.add(c);
		}
		
		
		
		
		JButton amb = new JButton("AMB");
		amb.setFont(myGui.f1.deriveFont(Font.PLAIN,10));
		amb.setForeground(Color.white);
		for(Unit u : emergencyUnits) {
			if(u instanceof Ambulance) {
				Ambulance.add(u);
			}
		}
		amb.setBorder(BorderFactory.createEmptyBorder());
		amb.setBackground(Color.DARK_GRAY);
		amb.setName("amb");
		ImageIcon c1 = new ImageIcon("Ambulance.png");
		amb.setIcon(c1);
		amb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//SuperButton = amb;
				unitAction(amb);
				if(SuperButton!=null)
				PrintUnits(Ambulance);
			}
		});
		myGui.addIdleUnit(amb);
		
		
		
		JButton evc = new JButton("EVC");
		evc.setFont(myGui.f1.deriveFont(Font.PLAIN,10));
		evc.setForeground(Color.white);
		for(Unit u : emergencyUnits) {
			if(u instanceof Evacuator) {
				Evacuator.add(u);
			}
		}
		evc.setBorder(BorderFactory.createEmptyBorder());
		evc.setName("evc");
		ImageIcon c2 = new ImageIcon("Evac.png");
		evc.setIcon(c2);
		evc.setBackground(Color.DARK_GRAY);
		evc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//SuperButton = evc;
				unitAction(evc);
				if(SuperButton!=null)
				PrintUnits(Evacuator);
			}
		});
		
		myGui.addIdleUnit(evc);
		
		
		
		JButton dcu = new JButton("DCU");
		dcu.setFont(myGui.f1.deriveFont(Font.PLAIN,10));
		dcu.setForeground(Color.white);
		for(Unit u : emergencyUnits) {
			if(u instanceof DiseaseControlUnit) {
				DiseaseControlUnit.add(u);
			}
		}
		dcu.setBorder(BorderFactory.createEmptyBorder());
		dcu.setBackground(Color.DARK_GRAY);
		dcu.setName("dcu");
		ImageIcon c3 = new ImageIcon("DCU.png");
		dcu.setIcon(c3);
		dcu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//SuperButton = dcu;
				unitAction(dcu);
				if(SuperButton!=null)
				PrintUnits(DiseaseControlUnit);
			}
		});
		myGui.addIdleUnit(dcu);
		
		
		
		JButton ftk = new JButton("FTK");
		ftk.setFont(myGui.f1.deriveFont(Font.PLAIN,10));
		ftk.setForeground(Color.white);
		for(Unit u : emergencyUnits) {
			if(u instanceof FireTruck) {
				FireTruck.add(u);
			}
		}
		ftk.setBorder(BorderFactory.createEmptyBorder());
		ftk.setBackground(Color.DARK_GRAY);
		ftk.setName("ftk");
		ImageIcon c4 = new ImageIcon("FireTruck.png");
		ftk.setIcon(c4);
		ftk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//SuperButton = ftk;
				unitAction(ftk);
				if(SuperButton!=null)
				PrintUnits(FireTruck);
			}
		});
		myGui.addIdleUnit(ftk);
		
		
		
		JButton gcu = new JButton("GCU");
		gcu.setFont(myGui.f1.deriveFont(Font.PLAIN,10));
		gcu.setForeground(Color.white);
		for(Unit u : emergencyUnits) {
			if(u instanceof GasControlUnit) {
				GasControlUnit.add(u);
			}
		}
		gcu.setBorder(BorderFactory.createEmptyBorder());
		gcu.setBackground(Color.DARK_GRAY);
		gcu.setName("gcu");
		ImageIcon c5 = new ImageIcon("GCU.png");
		gcu.setIcon(c5);
		gcu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//SuperButton = gcu;
				unitAction(gcu);
				if(SuperButton!=null)
				PrintUnits(GasControlUnit);
				}
		});
		myGui.addIdleUnit(gcu);
		
		
		
		JButton nextCycle = new JButton("NextCycle");
		ImageIcon icon = new ImageIcon("background.png");
		nextCycle.setIcon(icon);
		nextCycle.setForeground(Color.red);
		nextCycle.setBackground(Color.DARK_GRAY);
		
		JTextPane currentCycle= new JTextPane();
		currentCycle.setEditable(false);
		currentCycle.setBackground(Color.DARK_GRAY);
		currentCycle.setForeground(Color.white);
		
		currentCycle.setBorder(BorderFactory.createTitledBorder(null, "Cycle", TitledBorder.CENTER, TitledBorder.TOP, myGui.f1.deriveFont(Font.PLAIN,25), Color.orange));
		currentCycle.setText(""+engine.getCurrentCycle());

		  
		StyledDocument doc = currentCycle.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		  
		JTextPane Casulaties= new JTextPane();
		Casulaties.setEditable(false);
		Casulaties.setBackground(Color.DARK_GRAY);
		Casulaties.setForeground(Color.white);
		
		Casulaties.setBorder(BorderFactory.createTitledBorder(null, "Casulaties", TitledBorder.CENTER, TitledBorder.TOP, myGui.f1.deriveFont(Font.PLAIN,25), Color.orange));
		Casulaties.setText(""+engine.calculateCasualties());
			  
	    StyledDocument doc1 = Casulaties.getStyledDocument();
		doc1.setParagraphAttributes(0, doc1.getLength(), center, false);
		
		myGui.b[0][0].addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				float[] hsb = Color.RGBtoHSB(82, 162, 16, null);
				myGui.b[0][0].setBorder(BorderFactory.createLineBorder(Color.getHSBColor(hsb[0],hsb[1],hsb[2]), 2));

				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				myGui.b[0][0].setBorder(BorderFactory.createLineBorder(Color.white, 2));
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Citizen> CitizensInBase = new ArrayList<Citizen>();
				ArrayList<Citizen> AllCitizensInBase = new ArrayList<Citizen>();
				for(Citizen c :	engine.getCitizens()) {
                    if (c.getDisaster()!=null&&(c.getLocation().getX()==0&&c.getLocation().getY()==0))CitizensInBase.add(c);
                    if ((c.getLocation().getX()==0&&c.getLocation().getY()==0))AllCitizensInBase.add(c);
                }
				if(UnitNow!=null)
				{
				Unit u= (Unit) UnitNow.getClientProperty("ButtonUnit");
				
				if(u instanceof MedicalUnit) {
					if(CitizensInBase.isEmpty()) {JOptionPane.showMessageDialog(null, new JTextArea("No Injured Citizens Here!"));}
                    else{DynamicPanelList dy = new DynamicPanelList(CitizensInBase,u,myGui,UnitNow,getSuper());myGui.setEnabled(false);}
                }
				else
				{
					JOptionPane.showMessageDialog(null, new JTextArea("Base can't have a Disaster"));	
				}
				}
				else
					myGui.clearText();
					for(Citizen c : AllCitizensInBase) {
						myGui.addText(myGui.getText()+printCitizen(c)+"\n========================\n");
					}
				}
				
			});
		for (ResidentialBuilding r : engine.getBuildings())
		{
		myGui.b[r.getLocation().getX()][r.getLocation().getY()].addMouseListener( new MouseListener() {
			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				float[] hsb = Color.RGBtoHSB(82, 162, 16, null);
				myGui.b[r.getLocation().getX()][r.getLocation().getY()].setBorder(BorderFactory.createLineBorder(Color.getHSBColor(hsb[0],hsb[1],hsb[2]), 2));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				myGui.b[r.getLocation().getX()][r.getLocation().getY()].setBorder(BorderFactory.createLineBorder(Color.white, 2));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(UnitNow!=null)
				{
				Unit u= (Unit) UnitNow.getClientProperty("ButtonUnit");
				
				if(u instanceof MedicalUnit) {
                    ArrayList<Citizen> CitizensInBuiding = new ArrayList<Citizen>();
                    for(Citizen c : r.getOccupants()) {
                        if (c.getDisaster()!=null)CitizensInBuiding.add(c);
                    }
                if(CitizensInBuiding.isEmpty()) {JOptionPane.showMessageDialog(null, new JTextArea("No Injured Citizens Here!"));}
                    else{DynamicPanelList dy = new DynamicPanelList(CitizensInBuiding,u,myGui,UnitNow,getSuper());myGui.setEnabled(false);}
                }
				else if(r.getDisaster()==null)
				{
					JOptionPane.showMessageDialog(null, new JTextArea("Building doesn't have a Disaster"));	
				}
				}
				else
				myGui.addText(printBuilding(r));
				}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		//System.out.println(myGui.b[r.getLocation().getX()][r.getLocation().getY()].getMouseListeners().length);
		}		
		
		
		nextCycle.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon icon = new ImageIcon("background.png");
				nextCycle.setIcon(icon);
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon icon = new ImageIcon("background.gif");
				nextCycle.setIcon(icon);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(engine.checkGameOver()) {GameOver go = new GameOver();myGui.clip.stop();go.t1.setText("Casualities: "+engine.calculateCasualties());go.t2.setText("Cycle: "+engine.getCurrentCycle());;myGui.dispose();}
				else {
					
					
					
					myGui.revalidate();
					myGui.repaint();
				
					engine.nextCycle();
					myGui.addLogs("========================");
					myGui.addLogs("Cycle "+engine.getCurrentCycle());
					myGui.addLogs(">Disaster Strike This Cycle: ");
					myGui.revalidate();
					myGui.repaint();

							
					for(int i=0 ; i<RespondingUnits.size();i++) {
					JButton u = RespondingUnits.get(i);
					Unit unit = (Unit) u.getClientProperty("ButtonUnit");
					if(unit.getState()==UnitState.IDLE) {
						myGui.removeRespondingUnit(u);
						RespondingUnits.remove(u);
						i--;
					}

					}
					
					while(!visibleBuildings.isEmpty()) {
						ResidentialBuilding rb = visibleBuildings.get(0);
						visibleBuildings.remove(0);

						JButton b = myGui.b[(rb.getLocation().getX())][(rb.getLocation().getY())];
						b.setIcon(rb.getCurrentIcon());
						String s = rb.getDisaster().getClass().getSimpleName();	
						myGui.addLogs("     -> Building at ("+rb.getLocation().getX()+","+rb.getLocation().getY()+") was hit by a "+ s+".");	
						if(b.getMouseListeners().length==2) {
							b.addMouseListener(new MouseListener() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void mousePressed(MouseEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void mouseExited(MouseEvent e) {
									// TODO Auto-generated method stub
									float[] hsb = Color.RGBtoHSB(82, 162, 16, null);
									b.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(hsb[0],hsb[1],hsb[2]), 2));
								}
								
								@Override
								public void mouseEntered(MouseEvent e) {
									// TODO Auto-generated method stub
									b.setBorder(BorderFactory.createLineBorder(Color.white, 2));
								}
								
								@Override
								public void mouseClicked(MouseEvent e) {
									if(UnitNow!=null) {
										Unit u = (Unit) UnitNow.getClientProperty("ButtonUnit");
										
								    	if (u instanceof PoliceUnit || u instanceof FireUnit) {
								    		int dialogButton = JOptionPane.showConfirmDialog (null, "Are you sure?","WARNING",JOptionPane.YES_NO_OPTION);
								    	    if(dialogButton == JOptionPane.YES_OPTION) {
								    		try {
								    			u.respond(rb);
								    			boolean flag = false;
								    			for(JButton n :RespondingUnits)
								    			{
								    			if(n.getClientProperty("ButtonUnit").equals(u))
								    			{
								    			flag =true;
								    			break;
								    			}
								    			}
								    			if(!flag)
								    			{
								    				RespondingUnits.add(UnitNow);
											    	myGui.addRespondingUnit(UnitNow);
								    			}
										    	
								    			
										    	SuperButton.setBackground(Color.DARK_GRAY);
										    	
										    	UnitNow=null;
								    			SuperButton = null;
								    			
								    			myGui.UnitSelect.removeAll();
								    			
								    			
								    			myGui.revalidate();
												myGui.repaint();
								    		}
								    		catch(IncompatibleTargetException it) {
								    			JOptionPane.showMessageDialog(null, new JTextArea("Incompatible Target!"));
								    		}
								    		catch(CannotTreatException ct) {
								    			JOptionPane.showMessageDialog(null, new JTextArea("Cannot Treat Target!"));
								    		}
								    		catch(CitizenAlreadyDeadException cd) {
						   						JOptionPane.showMessageDialog(null, new JTextArea("Citizen Already Dead!"));
						   					}
						   					catch(BuildingAlreadyCollapsedException bc) {
						   						JOptionPane.showMessageDialog(null, new JTextArea("Building Already Collapsed!"));
						   					}
								    	}
								    	}
								    	 
								    	}
										
								    }								
								}
							);
						}
							
					}
					while(!visibleCitizens.isEmpty()) {
						Citizen c = visibleCitizens.get(0);
						visibleCitizens.remove(0);
						
						CitizensWithDisaster.add(c);

						JButton b = myGui.b[(c.getLocation().getX())][(c.getLocation().getY())];
						//System.out.println(b.getMouseListeners().length);
     
						((ArrayList)b.getClientProperty("Citizens")).add(c);
						
						String s = c.getDisaster().getClass().getSimpleName();
						myGui.addLogs("     -> Citizen "+c.getName() + " at ("+c.getLocation().getX()+","+c.getLocation().getY()+") was hit by a "+ s+".");
						if(!citizenInBui(c)&&(c.getLocation().getX()!=0&&c.getLocation().getY()!=0)&&b.getMouseListeners().length==1)
						{
							ImageIcon icon1 = new ImageIcon("grass.png");
							ImageIcon icon2 = new ImageIcon("Citizen.gif");
							JLabel background = new JLabel(icon1);
					        JLabel foreground = new JLabel(icon2);
					        background.setLayout(new GridBagLayout());
					        background.add(foreground);
					        background.setVisible(true);
					        b.add(background);
						b.addMouseListener(new MouseListener() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mousePressed(MouseEvent e) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void mouseExited(MouseEvent e) {
								float[] hsb = Color.RGBtoHSB(82, 162, 16, null);
								b.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(hsb[0],hsb[1],hsb[2]), 2));
								
							}
							
							@Override
							public void mouseEntered(MouseEvent e) {
								b.setBorder(BorderFactory.createLineBorder(Color.white, 2));
								
							}
							
							@Override
							public void mouseClicked(MouseEvent e) {
								if(UnitNow!=null) {
						    		Unit u = (Unit) UnitNow.getClientProperty("ButtonUnit"); 
									DynamicPanelList dy = new DynamicPanelList(((ArrayList)b.getClientProperty("Citizens")), u,myGui,UnitNow,getSuper());
									myGui.setEnabled(false);
						    	}
						    	else{
						    		ArrayList<Citizen> carr = ((ArrayList)b.getClientProperty("Citizens"));
						    		myGui.clearText();
						    		for(Citizen c: carr)
						    			myGui.addText(myGui.getText()+printCitizen(c)+"\n========================\n");
						    	}
								
							}
						});
					}}
					myGui.addLogs(">Citizens who died this cycle:");
					int count=0;
					for(int i=0 ; i<AllCitizens.size() ; i++) {
						Citizen c = AllCitizens.get(i);
						if(c.getState()==CitizenState.DECEASED){
							myGui.addLogs("     -> "+c.getName()+ "At ("+c.getLocation().getX()+","+c.getLocation().getY()+")");
							AllCitizens.remove(c);
							i--;
							count++;
						}
					}
					if(count==0)myGui.addLogs("     ->  NONE");
					
					myGui.revalidate();
					myGui.repaint();
					myGui.ActiveDisasters.setText("");
					for(Disaster d: engine.getExecutedDisasters()) {
	                    if(d.isActive()) {
	                        myGui.ActiveDisasters.setText(myGui.ActiveDisasters.getText()+"\n"+ d.getClass().getSimpleName() + " At location (" + d.getTarget().getLocation().getX() + "," + d.getTarget().getLocation().getY() +")");
	                    }

	                }
					
					
					for(Citizen c : CitizensWithDisaster) {
						JButton b = myGui.b[(c.getLocation().getX())][(c.getLocation().getY())];
						ArrayList<Citizen> a = ((ArrayList)b.getClientProperty("Citizens"));
						boolean flag = false;
						for(Citizen c1 : a) {
							if(c1.getState()!=CitizenState.DECEASED) {flag=true;break;}
						}
						if(!flag) {
							ImageIcon icon1 = new ImageIcon("grass.png");
							ImageIcon icon2 = new ImageIcon("dead.gif");
							JLabel background = new JLabel(icon1);
					        JLabel foreground = new JLabel(icon2);
					        background.setLayout(new GridBagLayout());
					        background.add(foreground);
					        background.setVisible(true);
					        b.removeAll();
					        b.add(background);
						}
					}
					
						
					currentCycle.setText(engine.getCurrentCycle()+"");
					
					Casulaties.setText(""+engine.calculateCasualties());
							
			}
				
			}	
		});
			

		myGui.addButton(Casulaties);    
		myGui.addButton(nextCycle);
		myGui.addButton(currentCycle);
		myGui.revalidate();
	}

	@Override
	public void receiveSOSCall(Rescuable r) {
		
		if (r instanceof ResidentialBuilding) {
			
			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);
			
		} else {
			
			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}

	}
	public static String printBuilding(ResidentialBuilding rb) {
        String s = "";
        s += "Location: ("+rb.getLocation().getX()+","+rb.getLocation().getY()+")\n";
        s += "StructualIntegrity: "+rb.getStructuralIntegrity()+"\n";
        s += "Fire Damage: " + rb.getFireDamage()+"\n";
        s += "Gas Level: " + rb.getGasLevel()+"\n";
        s += "Foundation Damage: " + rb.getFoundationDamage()+"\n";
        if(rb.getDisaster()!= null)
        {
        s += "Disaster: " + rb.getDisaster().getClass().getSimpleName() + "\n" ;
        }
        else
        {
        s+=	"No Disaster"+"\n";
        }
        s += "\n";
        s += "Number Of Occupants: " + rb.getOccupants().size()+"\n";
        s += "\n";
        s += "Occupants: \n";
        s +="\n";
        for(Citizen c : rb.getOccupants()) {
            s += printCitizen(c)+"\n";
            s += "\n";
        }

        return s;
    }
public static String printCitizen(Citizen c) {
        String s = "";
        s += "Citizen: " + c.getName() + "\n";
        s += "Health:  " + c.getHp() + "\n";
        s += "Age: " + c.getAge() + "\n";
        s += "ID: " + c.getNationalID() + "\n";
        s += "Location: (" + c.getLocation().getX() + "," + c.getLocation().getY() +") \n";
        s += "Blood loss: " + c.getBloodLoss() + "\n"; 
        s += "Toxicity: " +c.getToxicity() + "\n";
        s += "State: " +c.getState() + "\n";
        if (c.getDisaster()!=null) {
            s+= "Disaster: " + c.getDisaster().getClass().getSimpleName();
        }
        return s;
    }
public static String printUnit(Unit u) {
        String s = "";
        s+= u.getClass().getSimpleName() +" "+ u.getUnitID() + "\n";
        s+= "Location: (" + u.getLocation().getX() + "," + u.getLocation().getY() + ") \n";
        s+= "Steps per cycle: " +u.getStepsPerCycle()+"\n";
        if(u.getTarget()!=null) 
        s+= "Target: " + u.getTarget().getClass().getSimpleName() + "at (" + u.getTarget().getLocation().getX() + "," + u.getTarget().getLocation().getY() + ") \n" ;
        s+= "State: " + u.getState() + "\n";
        if(u instanceof Evacuator) {
        	
            s+= "\nNumber of pasengers: " + ((Evacuator) u).getPassengers().size()+"\n";
            s+="\nEvacuator passengers : \n";
            s+="\n";
            for(Citizen c:((Evacuator) u).getPassengers()) {
                s+=printCitizen(c);
                s+="\n";

            }

        }
        return s;
    }
	public void unitAction(JButton b) {
		if(SuperButton==null) {
			SuperButton=b;
			b.setBackground(Color.LIGHT_GRAY);
		}
		else {
			if(SuperButton==b) {
    			b.setBackground(Color.DARK_GRAY);
    			SuperButton = null;
    			UnitNow=null;
    			myGui.clearText();
    			myGui.UnitSelect.removeAll();
    			myGui.revalidate();
    			myGui.repaint();
    		}
			else {
    			SuperButton.setBackground(Color.DARK_GRAY);
    			SuperButton=b;
    			b.setBackground(Color.LIGHT_GRAY);
    			}
		}
		
		}
	public CommandCenter getSuper() {
		return this;
	}
	
	public void PrintUnits(ArrayList<Unit> arr) {
		
		myGui.UnitSelect.removeAll();
		myGui.revalidate();
		myGui.repaint();
	
		myGui.UnitSelect.setLayout(new GridLayout(emergencyUnits.size(),1));
		
		  
        JButton cancel = new JButton("Cancel");
        cancel.setFont(myGui.f1.deriveFont(Font.PLAIN,15));
        cancel.setBackground(Color.LIGHT_GRAY);
        cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 
				myGui.clearText();
		    	SuperButton.setBackground(Color.DARK_GRAY);
		    	
		    	UnitNow=null;
    			SuperButton = null;
    			
				myGui.UnitSelect.removeAll();
				myGui.revalidate();
	            myGui.repaint();
				
			}
		} );
		myGui.addText("");
		
    	for (Unit c :arr) {
    		
    	myGui.addText(myGui.getText()+printUnit(c)+"\n========================\n");
    	
    	JPanel box = new JPanel();
    	box.setLayout(new GridLayout(1,2));
    	
    	JPanel infoUnit = new JPanel();
    	infoUnit.setLayout(new BorderLayout());
	        JTextArea ID = new JTextArea(SuperButton.getName()+" "+c.getUnitID());
	        ID.setBackground(Color.DARK_GRAY);
	        ID.setFont(myGui.f1.deriveFont(Font.PLAIN,20));
	        ID.setForeground(Color.white);
	        JTextArea SPC = new JTextArea("SPC: "+c.getStepsPerCycle());
	        SPC.setBackground(Color.DARK_GRAY);
	        SPC.setForeground(Color.white);
	        SPC.setFont(myGui.f2.deriveFont(Font.PLAIN,15));
	        infoUnit.add(ID,BorderLayout.NORTH);
	        infoUnit.add(SPC,BorderLayout.CENTER);     
	        
	        JButton but = new JButton("Select");
	        but.setFont(myGui.f2.deriveFont(Font.PLAIN,15));
	        but.setBackground(Color.LIGHT_GRAY);
	        box.add(infoUnit);
	        box.add(but);
	       
	        but.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UnitNow = new JButton(c.getClass().getSimpleName()+" "+c.getUnitID());
				UnitNow.setFont(myGui.f2.deriveFont(Font.PLAIN,15));
				UnitNow.setBackground(Color.LIGHT_GRAY);
				UnitNow.putClientProperty("ButtonUnit", c);
				UnitNow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						myGui.addText(printUnit(c));
						//unitActionNow(UnitNow);
						
					}
				});
				//myGui.clearText();
				myGui.UnitSelect.removeAll();
				
				JPanel box = new JPanel();
		    	box.setLayout(new GridLayout(2,1));
		    	JPanel infoUnit = new JPanel();
		    	infoUnit.setLayout(new BorderLayout());
			    JTextArea ID = new JTextArea("Unit Selected:\n");
			    ID.setBackground(Color.DARK_GRAY);
			    ID.setForeground(Color.white);
			    ID.setFont(myGui.f1.deriveFont(Font.PLAIN,17));
			    JTextArea SPC = new JTextArea(c.getClass().getSimpleName()+" "+c.getUnitID()+"\nState: "+c.getState());
			    SPC.setBackground(Color.darkGray);
			    SPC.setForeground(Color.white);
			    SPC.setFont(myGui.f2.deriveFont(Font.PLAIN,15));
			    infoUnit.add(ID,BorderLayout.NORTH);
			    infoUnit.add(SPC,BorderLayout.CENTER);
			    box.add(infoUnit);
		        box.add(cancel);
		        
		        myGui.UnitSelect.setLayout(new GridLayout(1,1));
				myGui.UnitSelect.add(box);
		        
				myGui.revalidate();
				myGui.repaint();
			}});
    	
    	
	        box.add(infoUnit);
	        box.add(but);
	        myGui.UnitSelect.add(box);
    	}
	       
        myGui.UnitSelect.add(cancel);
        myGui.revalidate();
		myGui.repaint();

	}
	
	public static void main(String[] args) throws Exception {
		 new CommandCenter();
	}
	public boolean citizenInBui (Citizen c) {
		for(ResidentialBuilding rb : engine.getBuildings())
		{
			if(rb.getOccupants().contains(c))
			{
				return true;
			}
		}
		return false;
	}
}
