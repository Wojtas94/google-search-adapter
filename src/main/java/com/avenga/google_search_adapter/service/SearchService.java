package com.avenga.google_search_adapter.service;

import java.util.List;

import org.openapitools.model.SearchResult;

public interface SearchService {

	List<SearchResult> search(String searchPhrase);
}
