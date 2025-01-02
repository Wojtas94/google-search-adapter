package com.avenga.google_search_adapter.service;

import java.util.List;

import org.openapitools.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.avenga.google_search_adapter.mapper.JsonToSearchResultsMapper;

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${google.search.api.key}")
    private String apiKey;

    @Value("${google.search.engine.id}")
    private String searchEngineId;

    @Value("${google.search.api.url}")
    private String searchApiUrl;
    
    @Value("${google.search.fields}")
    private String fields;
    
    private final JsonToSearchResultsMapper mapper;
    
    private final RestTemplate restTemplate;
        
    @Autowired
	public SearchServiceImpl(JsonToSearchResultsMapper mapper, RestTemplate restTemplate) {
		this.mapper = mapper;
		this.restTemplate = restTemplate;
	}

    @Retryable(retryFor = { HttpClientErrorException.class }, backoff = @Backoff(delay = 2000))
	@Override
	public List<SearchResult> search(String searchPhrase) {
		String url = searchApiUrl + "?key=" + apiKey + "&cx=" + searchEngineId + "&q=" + searchPhrase + "&fields=" + fields;
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
		return mapper.convertJsonToListOfSearchResults(responseEntity.getBody());
	}

}
