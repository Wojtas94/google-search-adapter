package com.avenga.google_search_adapter.mapper;

import java.util.ArrayList;
import java.util.List;

import org.openapitools.model.Details;
import org.openapitools.model.SearchResult;
import org.springframework.stereotype.Component;

import com.avenga.google_search_adapter.googlemodel.Hcard;
import com.avenga.google_search_adapter.googlemodel.Item;
import com.avenga.google_search_adapter.googlemodel.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonToSearchResultsMapperImpl implements JsonToSearchResultsMapper {

	@Override
	public List<SearchResult> convertJsonToListOfSearchResults(String jsonBody) {
		ObjectMapper om = new ObjectMapper();
		List<SearchResult> searchResults = new ArrayList<SearchResult>();
		
        try {
			 om.readValue(jsonBody, Root.class).items.forEach(item -> searchResults.addAll(convertToSearchResultList(item)));

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
		return searchResults;
	}

	private List<SearchResult> convertToSearchResultList(Item item) {
		List<SearchResult> searchResults = new ArrayList<SearchResult>();
		
		if (item.title != null && item.pagemap != null && item.pagemap.hcard != null) {
			List<Details> details = item.pagemap.hcard.stream()
					.map(hcard -> convertHcardToDetails(hcard))
					.toList();
			searchResults.add(SearchResult.builder()
					.title(item.title)
					.details(details).build());
		}
		
		return searchResults;
	}
	
	private Details convertHcardToDetails(Hcard hcard) {
		return Details.builder()
				.firstname(hcard.fn)
				.birthday(hcard.bday)
				.category(hcard.category)
				.nickname(hcard.nickname)
				.label(hcard.label)
				.role(hcard.role)
				.url(hcard.url).build();
	}
}
