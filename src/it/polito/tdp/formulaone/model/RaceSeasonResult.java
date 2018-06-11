package it.polito.tdp.formulaone.model;

public class RaceSeasonResult implements Comparable<RaceSeasonResult>{

	private Race d1;
	private Race d2;
	private int counter;
	
	public RaceSeasonResult(Race d1, Race d2, int counter) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.counter = counter;
	}
	public Race getD1() {
		return d1;
	}
	public void setD1(Race d1) {
		this.d1 = d1;
	}
	public Race getD2() {
		return d2;
	}
	public void setD2(Race d2) {
		this.d2 = d2;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	@Override
	public String toString() {
		return d1 + " - " + d2 + " : " + counter;
	}
	@Override
	public int compareTo(RaceSeasonResult r) {
		return -(this.getCounter() - r.getCounter());
	}

	
}
