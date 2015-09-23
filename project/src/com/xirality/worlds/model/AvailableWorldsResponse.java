package com.xirality.worlds.model;


public class AvailableWorldsResponse {
	private GameWorld[] allAvailableWorlds;

	public GameWorld[] getAllAvailableWorlds() {
		return allAvailableWorlds;
	}

	public void setAllAvailableWorlds(GameWorld[] allAvailableWorlds) {
		this.allAvailableWorlds = allAvailableWorlds;
	}

}
