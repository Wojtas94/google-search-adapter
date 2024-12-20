package com.avenga.google_search_adapter.mapper;

import java.util.List;

import org.openapitools.model.SearchResult;


public interface JsonToSearchResultsMapper {

	List<SearchResult> convertJsonToListOfSearchResults(String jsonBody);
}
