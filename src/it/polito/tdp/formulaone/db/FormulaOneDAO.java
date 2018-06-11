package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.RaceIdMap;
import it.polito.tdp.formulaone.model.RaceSeasonResult;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {

	public List<Season> getAllSeasons() {
		String sql = "SELECT year, url FROM seasons ORDER BY year";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Season> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Race> getAllRacesBySeason(Season s, RaceIdMap raceIdMap) {
		String sql = "SELECT raceId, name, year FROM races WHERE year=?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, s.getYear());
			ResultSet rs = st.executeQuery();

			List<Race> races = new ArrayList<>();
			while (rs.next()) {
				races.add(raceIdMap.get(new Race(rs.getInt("raceId"), rs.getString("name"), rs.getInt("year"))));
			}

			conn.close();
			return races;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}

	public List<RaceSeasonResult> getRaceSeasonResults(Season s, RaceIdMap raceIdMap) {
		String sql = "select r1.raceId as rr1, r2.raceId as rr2, count(*) as cnt " + 
				"from results as re1, results as re2, races as r2, races as r1 " + 
				"where r1.year = ? " + 
				"and r2.year = r1.year " + 
				"and r2.raceId > r1.raceId " + 
				"and re1.raceId = r1.raceId " + 
				"and re2.raceId = r2.raceId " + 
				"and re1.driverId = re2.driverId " + 
				"and re1.statusId = 1 " + 
				"and re2.statusId = 1 " + 
				"group by rr1, rr2";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, s.getYear());
			ResultSet rs = st.executeQuery();

			List<RaceSeasonResult> result = new ArrayList<>();
			while (rs.next()) {
				
				Race d1 = raceIdMap.get(rs.getInt("rr1"));
				Race d2 = raceIdMap.get(rs.getInt("rr2"));
				int cnt = rs.getInt("cnt");
				
				if (d1 == null || d2 == null) {
					System.err.format("Skipping %d %d\n", rs.getInt("rr1"), rs.getInt("rr2"));
				} else {
					result.add(new RaceSeasonResult(d1, d2, cnt));
				}
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}

}
