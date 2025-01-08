package com.avenga.googlesearchadapter.service;

import java.util.List;

import org.openapitools.model.SearchResult;

public interface SearchService {

	List<SearchResult> search(String searchPhrase);
}
