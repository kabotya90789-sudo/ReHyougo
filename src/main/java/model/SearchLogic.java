package model;

import java.util.List;

import dao.EventsDAO;
import dao.PinsDAO;

public class SearchLogic {
	public List<Pin> pinSearch(List<String> wordsList, List<Integer> tags, String userId) {
		PinsDAO dao = new PinsDAO();
		List<Pin> searchResult = dao.searchPins(wordsList, tags, userId);
		return searchResult;
	}
	
	public List<Event> eventSearch(List<String> wordsList, String userId) {
		EventsDAO dao = new EventsDAO();
		List<Event> searchResult = dao.searchEvents(wordsList, userId);
		return searchResult;
	}
}
