package com.xirality.worlds.model;

public class GameWorld {
	private String id;
	private String country;
	private String language;
	private String url;
	private String mapURL;
	private String name;
	private WorldStatus worldStatus;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMapURL() {
		return mapURL;
	}
	public void setMapURL(String mapURL) {
		this.mapURL = mapURL;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public WorldStatus getWorldStatus() {
		return worldStatus;
	}
	public void setWorldStatus(WorldStatus worldStatus) {
		this.worldStatus = worldStatus;
	}
}
