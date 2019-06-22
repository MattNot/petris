package com.managers.record;

import java.util.Map;

public class RecordManager {
	
	private Map<String, String> map;
	boolean newRecord;
	
	public RecordManager() {
		map = System.getenv();
		newRecord = false;
	}
	
	public String getHighUser() {
		if(newRecord)
			return map.get("pandoras_ActualUser");
		else
			return map.get("pandoras_HighUser");
	}

	public String getPoints(String newScore) {
		if(newRecord)
			return newScore;
		else
			return map.get("pandoras_HighScore");
	}
	
	public void changeHighScore(Integer newPoints) {
		String highScore = map.get("pandoras_HighScore");
		if(Integer.parseInt(highScore) < newPoints) {
			newRecord = true;
		}
	}
	
	public void update(String newScore) {
		if(newRecord) {
			System.out.println("ciao");
		}
	}
	
}
