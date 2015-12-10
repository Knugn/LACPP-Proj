package org.uu.lacpp15.g3.antcolony.client.gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.uu.lacpp15.g3.antcolony.simulation.IRPheromoneGrid;
import org.uu.lacpp15.g3.antcolony.simulation.ISimulation;
import org.uu.lacpp15.g3.antcolony.simulation.entities.IREntities;

public class SimulationInfoPanel extends JPanel {
	
	private static final long	serialVersionUID	= 2241730496801731752L;
	
	private JLabel				nAntsLabel, nHivesLabel, nFoodLabel, secPassedLabel, hiveGridResLabel,
			foodGridResLabel;
	
	public SimulationInfoPanel() {
		super();
		
		String[] descriptions = {
				"Time passed",
				"Number of ants",
				"Number of hives",
				"Number of food",
				"Hive grid resolution",
				"Food grid resolution"
		};
		JLabel[] valueLabels = {
				secPassedLabel = createValueLabel(),
				nAntsLabel = createValueLabel(),
				nHivesLabel = createValueLabel(),
				nFoodLabel = createValueLabel(),
				hiveGridResLabel = createValueLabel(),
				foodGridResLabel = createValueLabel(),
		};
		int rows = descriptions.length, cols = 2, hgap = 3, vgap = 3;
		this.setLayout(new GridLayout(rows, cols, hgap, vgap));
		for (int i = 0; i < rows; i++) {
			this.add(createDescriptionLabel(descriptions[i]));
			this.add(valueLabels[i]);
		}
	}
	
	public void updateInfo(ISimulation simulation) {
		secPassedLabel.setText(((int) (simulation.getPassedNanoSec() / 1000000000.0)) + "s");
		updateEntitiesSizeText(nAntsLabel, simulation.getWorld().getAllAnts());
		updateEntitiesSizeText(nHivesLabel, simulation.getWorld().getAllHives());
		updateEntitiesSizeText(nFoodLabel, simulation.getWorld().getAllFoodSources());
		updateGridResText(hiveGridResLabel, simulation.getWorld().getHivePheromoneGrid());
		updateGridResText(foodGridResLabel, simulation.getWorld().getFoodPheromoneGrid());
	}
	
	private static void updateEntitiesSizeText(JLabel lbl, IREntities entities) {
		String txt = null;
		if (entities == null)
			txt = "N/A";
		else
			txt = entities.size() + "";
		lbl.setText(txt);
	}
	
	private static void updateGridResText(JLabel lbl, IRPheromoneGrid grid) {
		String txt = null;
		if (grid == null)
			txt = "N/A";
		else
			txt = grid.getResolutionX() + "x" + grid.getResolutionY();
		lbl.setText(txt);
	}
	
	private static JLabel createDescriptionLabel(String desc) {
		return new JLabel(desc);
	}
	
	private static JLabel createValueLabel() {
		return new JLabel();
	}
	
}
