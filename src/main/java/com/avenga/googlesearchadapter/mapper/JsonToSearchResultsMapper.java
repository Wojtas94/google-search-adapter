package com.avenga.googlesearchadapter.mapper;

import java.util.List;

import org.openapitools.model.SearchResult;


public interface JsonToSearchResultsMapper {

	List<SearchResult> convertJsonToListOfSearchResults(String jsonBody);
}
