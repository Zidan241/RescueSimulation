package model.infrastructure;

import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.events.SOSListener;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class ResidentialBuilding implements Rescuable, Simulatable 
{

	private Address location;
	private final String allBuildingImagesNames[] = {"building.png","buildingOnFire.gif","buildingCollapse.gif","buildingGasLeak.gif"} ;
    private int structuralIntegrity;
    private int fireDamage;
    private int gasLevel;
    private int foundationDamage;
    private ArrayList<Citizen> occupants;
    private Disaster disaster;
    private ImageIcon currentIcon;
    private JButton buildingButton;
    public JLabel foreground ;
    public boolean gettingHealed;
	public JButton getBuildingButton() {
		return buildingButton;
	}
	public void setBuildingButton(JButton buildingButton) {
		this.buildingButton = buildingButton;
	}

	private SOSListener emergencyService;
	public ResidentialBuilding(Address location) {
		this.location = location;
		this.structuralIntegrity=100;
		currentIcon = new ImageIcon(allBuildingImagesNames[0]);
		occupants= new ArrayList<Citizen>();
		currentIcon = new ImageIcon(allBuildingImagesNames[0]);
		gettingHealed=false;
	}
	public int getStructuralIntegrity() {
		return structuralIntegrity;
	}
	public void setStructuralIntegrity(int structuralIntegrity) {
		this.structuralIntegrity = structuralIntegrity;
		if(structuralIntegrity<=0)
		{
			this.structuralIntegrity=0;
			for(int i = 0 ; i< occupants.size(); i++)
				occupants.get(i).setHp(0);
		}
	}
	public int getFireDamage() {
		return fireDamage;
	}
	public void setFireDamage(int fireDamage) {
		this.fireDamage = fireDamage;
		if(fireDamage<=0)
			this.fireDamage=0;
		else if(fireDamage>=100)
			this.fireDamage=100;
	}
	public int getGasLevel() {
		return gasLevel;
	}
	public void setGasLevel(int gasLevel) {
		this.gasLevel = gasLevel;
		if(this.gasLevel<=0)
			this.gasLevel=0;
		else if(this.gasLevel>=100)
		{
			this.gasLevel=100;
			for(int i = 0 ; i < occupants.size(); i++)
			{
				occupants.get(i).setHp(0);
			}
		}
	}
	public int getFoundationDamage() {
		return foundationDamage;
	}
	public void setFoundationDamage(int foundationDamage) {
		this.foundationDamage = foundationDamage;
		if(this.foundationDamage>=100)
		{
			
			setStructuralIntegrity(0);
		}
			
	}
	public Address getLocation() {
		return location;
	}
	public ArrayList<Citizen> getOccupants() {
		return occupants;
	}
	public ImageIcon getCurrentIcon() {
		return currentIcon;
	}
	public void cycleStepImage()
    {
    if(this.fireDamage==0 && this.foundationDamage==0&& this.gasLevel==0)
    {
    currentIcon = new ImageIcon("building.png");
    }
    else if(this.disaster instanceof Fire)
    {
        currentIcon = new ImageIcon(allBuildingImagesNames[1]);
    }
    else if (this.disaster instanceof Collapse)
    {
        currentIcon = new ImageIcon(allBuildingImagesNames[2]);
    }
    
    else if (this.disaster instanceof GasLeak)
    {
        currentIcon = new ImageIcon(allBuildingImagesNames[3]);
    }
     if(this.structuralIntegrity==0)
	{
 		//buildingButton.add(new JLabel(new ImageIcon("expp.gif")));
    	 gettingHealed =false;
        currentIcon = new ImageIcon("destroyed.png");
	}
    buildingButton.removeAll();
    ImageIcon icon1 = new ImageIcon("grass.png");
	JLabel background = new JLabel(icon1);
	JLabel foreground = new JLabel(currentIcon);
    foreground.setLayout(new GridBagLayout());
    if(gettingHealed)
    {
    foreground.add(new JLabel(new ImageIcon("test.gif")));
    }
    background.setLayout(new GridBagLayout());
    background.add(foreground);
	//JLabel background = new JLabel(icon1);
    foreground.setVisible(true); 
    background.setVisible(true); 
    buildingButton.add(background);
	
   
    }
	public Disaster getDisaster() {
		return disaster;
	}
	public void setEmergencyService(SOSListener emergency) {
		this.emergencyService = emergency;
	}
	@Override
	public void cycleStep() {
	cycleStepImage();
		if(foundationDamage>0)
		{
			
			int damage= (int)((Math.random()*6)+5);
			setStructuralIntegrity(structuralIntegrity-damage);
			
		}
		if(fireDamage>0 &&fireDamage<30)
			setStructuralIntegrity(structuralIntegrity-3);
		else if(fireDamage>=30 &&fireDamage<70)
			setStructuralIntegrity(structuralIntegrity-5);
		else if(fireDamage>=70)
			setStructuralIntegrity(structuralIntegrity-7);
		
	}
	
	@Override
	public void struckBy(Disaster d) {
		if(disaster!=null)
			disaster.setActive(false);
		disaster=d;
		emergencyService.receiveSOSCall(this);
	}
	public void occuppantInTrouble()
	{
	for (Citizen c : occupants)		
	{
	if(c.getDisaster()!= null)
	{}
	}
	}
}
