package com.avenga.google_search_adapter.controller;

import java.util.List;

import org.openapitools.api.SearchApi;
import org.openapitools.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.avenga.google_search_adapter.service.SearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class SearchController implements SearchApi {
	
	private final SearchService searchService;
	
	@Autowired
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}
	
	@Override
	public ResponseEntity<List<SearchResult>> getSearchResults(@NotNull @Valid String searchPhrase) {
		List<SearchResult> searchResults = searchService.search(searchPhrase);
		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}
	
	
}
