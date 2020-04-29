package model.units;

import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable, SOSResponder {
	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;
	private JLabel unitIcon = new JLabel(new ImageIcon("unitHere.gif"));
	private Rescuable targetPrev ;
	public Unit(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		this.unitID = unitID;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.state = UnitState.IDLE;
		this.worldListener = worldListener;
	}

	public void setWorldListener(WorldListener listener) {
		this.worldListener = listener;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}

	@Override
	public void respond(Rescuable r) throws IncompatibleTargetException,CannotTreatException,CitizenAlreadyDeadException,BuildingAlreadyCollapsedException {
		if(r instanceof Citizen) {
			throw new IncompatibleTargetException(this, r);
		}
		else if(((ResidentialBuilding)r).getStructuralIntegrity()==0) {
			throw new BuildingAlreadyCollapsedException(r.getDisaster());
		}
		else if(!canTreat(r)) {
			throw new CannotTreatException(this, r);
		}
		else if (this instanceof Evacuator&&!(r.getDisaster() instanceof Collapse)) {
				throw new CannotTreatException(this, r);
		}
		
		else if(this instanceof FireTruck&&!(r.getDisaster() instanceof Fire)) {
				throw new CannotTreatException(this, r);
		}
		else if(this instanceof GasControlUnit&&!(r.getDisaster() instanceof GasLeak)) {
				throw new CannotTreatException(this, r);
			}
		else {
		if (target != null && state == UnitState.TREATING)
		{
			reactivateDisaster();
		}
		if(target != null && targetPrev!=null)
		{
			JButton b = ((ResidentialBuilding)targetPrev).getBuildingButton();
			b.removeAll();
			 ((ResidentialBuilding)targetPrev).gettingHealed = false;
			 ImageIcon icon1 = new ImageIcon("grass.png");
				JLabel background = new JLabel(icon1);
				JLabel foreground = new JLabel(((ResidentialBuilding)targetPrev).getCurrentIcon());
			    foreground.setLayout(new GridBagLayout());			  
			    background.setLayout(new GridBagLayout());
			    background.add(foreground);
				//JLabel background = new JLabel(icon1);
			    foreground.setVisible(true); 
			    background.setVisible(true); 
			    b.add(background);
				targetPrev= r;
		}
		if (target == null && targetPrev!=null )
		{
			 ((ResidentialBuilding)targetPrev).gettingHealed = false;
			JButton b = ((ResidentialBuilding)targetPrev).getBuildingButton();
			b.removeAll();
			 ImageIcon icon1 = new ImageIcon("grass.png");
				JLabel background = new JLabel(icon1);
				JLabel foreground = new JLabel(((ResidentialBuilding)targetPrev).getCurrentIcon());
			    foreground.setLayout(new GridBagLayout());			  
			    background.setLayout(new GridBagLayout());
			    background.add(foreground);
				//JLabel background = new JLabel(icon1);
			    foreground.setVisible(true); 
			    background.setVisible(true); 
			    b.add(background);
				targetPrev= r;
		}
		if( targetPrev==null)
		{
			targetPrev = r;
		}
		
		
			finishRespond(r);
		}
		
	}
	

	public void reactivateDisaster() {
		Disaster curr = target.getDisaster();
		curr.setActive(true);
	}

	public void finishRespond(Rescuable r) {
		target = r;
		state = UnitState.RESPONDING;
		Address t = r.getLocation();
		distanceToTarget = Math.abs(t.getX() - location.getX())
				+ Math.abs(t.getY() - location.getY());

	}

	public abstract void treat();

	public void cycleStep() {
		if (state == UnitState.IDLE)
			return;
		if (distanceToTarget > 0) {
			distanceToTarget = distanceToTarget - stepsPerCycle;
			if (distanceToTarget <= 0) {
				distanceToTarget = 0;
				Address t = target.getLocation();
				worldListener.assignAddress(this, t.getX(), t.getY());
			}
		} else {
			state = UnitState.TREATING;
			if(target instanceof ResidentialBuilding)((ResidentialBuilding) (target)).gettingHealed = true;
			treat();
		}
	}

	public void jobsDone() {
		if(this instanceof Evacuator && this.getLocation()== new Address(0, 0))
		{
			JButton b = ((ResidentialBuilding)targetPrev).getBuildingButton();
			b.removeAll();
			 ImageIcon icon1 = new ImageIcon("grass.png");
				JLabel background = new JLabel(icon1);
				JLabel foreground = new JLabel(((ResidentialBuilding)targetPrev).getCurrentIcon());
			    foreground.setLayout(new GridBagLayout());			  
			    background.setLayout(new GridBagLayout());
			    background.add(foreground);
				//JLabel background = new JLabel(icon1);
			    foreground.setVisible(true); 
			    background.setVisible(true); 
			    b.add(background);	
		}
		target = null;
		state = UnitState.IDLE;

	}
	public boolean canTreat(Rescuable r) {
		if(r instanceof Citizen ) {
			if(((Citizen)r).getState()==CitizenState.SAFE)return false;
			else return true;
		}
		else {
			ResidentialBuilding rb = (ResidentialBuilding)r;
			if(rb.getFireDamage()==0&&rb.getFoundationDamage()==0&&rb.getGasLevel()==0)return false;
			else return true;
			
		}
	}
	
}
