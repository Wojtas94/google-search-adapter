package com.avenga.googlesearchadapter.mapper;

import java.util.ArrayList;
import java.util.List;

import org.openapitools.model.Details;
import org.openapitools.model.SearchResult;
import org.springframework.stereotype.Component;

import com.avenga.googlesearchadapter.googlemodel.Hcard;
import com.avenga.googlesearchadapter.googlemodel.Item;
import com.avenga.googlesearchadapter.googlemodel.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonToSearchResultsMapperImpl implements JsonToSearchResultsMapper {

	@Override
	public List<SearchResult> convertJsonToListOfSearchResults(String jsonBody) {
		ObjectMapper om = new ObjectMapper();
		List<SearchResult> searchResults = new ArrayList<SearchResult>();
		
        try {
			 om.readValue(jsonBody, Root.class).getItems().forEach(item -> searchResults.addAll(convertToSearchResultList(item)));

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
		return searchResults;
	}

	private List<SearchResult> convertToSearchResultList(Item item) {
		List<SearchResult> searchResults = new ArrayList<SearchResult>();
		
		if (item.getTitle() != null && item.getPagemap() != null && item.getPagemap().getHcard() != null) {
			List<Details> details = item.getPagemap().getHcard().stream()
					.map(hcard -> convertHcardToDetails(hcard))
					.toList();
			searchResults.add(SearchResult.builder()
					.title(item.getTitle())
					.details(details).build());
		}
		
		return searchResults;
	}
	
	private Details convertHcardToDetails(Hcard hcard) {
		return Details.builder()
				.firstname(hcard.getFn())
				.birthday(hcard.getBday())
				.category(hcard.getCategory())
				.nickname(hcard.getNickname())
				.label(hcard.getLabel())
				.role(hcard.getRole())
				.url(hcard.getUrl()).build();
	}
}
