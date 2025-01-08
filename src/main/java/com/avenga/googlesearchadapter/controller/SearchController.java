package com.avenga.googlesearchadapter.controller;

import java.util.List;

import org.openapitools.api.SearchApi;
import org.openapitools.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.avenga.googlesearchadapter.service.SearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


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
