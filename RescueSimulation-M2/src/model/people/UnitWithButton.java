package model.people;

import javax.swing.JButton;

import model.units.Unit;

public class UnitWithButton {
	private Unit unit;
	private JButton unitButton;
	
	public UnitWithButton(Unit unit,JButton unitButton)
	{
		this.unit = unit;
		this.unitButton =unitButton;
	}
}
