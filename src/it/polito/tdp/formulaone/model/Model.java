package it.polito.tdp.formulaone.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	private List<Season> seasons;
	private FormulaOneDAO fdao;
	private List<Race> races;
	private SimpleWeightedGraph<Race,DefaultWeightedEdge> grafo;
	private RaceIdMap raceIdMap;
	
	public Model() {
		this.races = new LinkedList<Race>();
		this.fdao = new FormulaOneDAO();
		this.seasons = fdao.getAllSeasons();
		this.raceIdMap = new RaceIdMap();
	}
	
	public List<Season> getSeasons() {
		return seasons;
	}
		
	public List<Race> getRaces() {
		return races;
	}

	public void creaGrafo(Season s) {		
		grafo = new SimpleWeightedGraph<Race, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		races = fdao.getAllRacesBySeason(s, raceIdMap);
		Graphs.addAllVertices(grafo, races);
		for (RaceSeasonResult rsr : fdao.getRaceSeasonResults(s, raceIdMap)) {
			Graphs.addEdgeWithVertices(grafo, rsr.getD1(), rsr.getD2(), rsr.getCounter());
		}
		System.out.format("Grafo creato: %d archi, %d nodi\n", grafo.edgeSet().size(), grafo.vertexSet().size());
	}
	
	public String findMaxDegree() {
		
		int max = 0;
		Race temp = null;
		Race temp1 = null;
		List<RaceSeasonResult> result = new ArrayList<RaceSeasonResult>();
		StringBuilder sb = new StringBuilder();
		
		if (grafo == null) {
			new RuntimeException("Creare il grafo!");
		}
		
		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			max = (int) grafo.getEdgeWeight(e);
			temp = grafo.getEdgeSource(e);
			temp1 = grafo.getEdgeTarget(e);
			result.add(new RaceSeasonResult(temp,temp1,max));	
			
		}
		
		Collections.sort(result);
		
		for(RaceSeasonResult rs : result) {
			if(rs.getCounter() == result.get(0).getCounter()) {
				sb.append(rs.toString() + "\n");
			}
		}
				
		return sb.toString();
	}
	
}
