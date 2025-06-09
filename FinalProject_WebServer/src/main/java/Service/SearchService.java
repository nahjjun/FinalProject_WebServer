package Service;

import java.util.List;
import java.util.Map;

import Repository.SearchRepository;

public class SearchService {
	private SearchRepository searchRepository= new SearchRepository();
	public SearchService() {
	}

	public List<Map<String, Object>> getSearchInfoList(String searchQuery){
		String query = "%" + searchQuery + "%";
		return searchRepository.getSearchInfoList(query);
	}
}
