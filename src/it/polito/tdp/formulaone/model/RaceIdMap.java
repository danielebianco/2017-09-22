package it.polito.tdp.formulaone.model;

import java.util.HashMap;

public class RaceIdMap extends HashMap<Integer,Race>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Race get(Race race) {
		Race old = super.get(race.getRaceId());
		
		if (old != null) {
			return old;
		}
		super.put(race.getRaceId(), race);
		
		return race;
	}
	
}
