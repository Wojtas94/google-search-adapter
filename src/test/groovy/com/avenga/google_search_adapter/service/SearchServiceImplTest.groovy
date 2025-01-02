package com.avenga.google_search_adapter.service

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.spockframework.spring.SpringBean

import com.avenga.google_search_adapter.mapper.JsonToSearchResultsMapper

import spock.lang.Specification

import static org.springframework.http.HttpStatus.OK

import org.openapitools.model.SearchResult


@SpringBootTest
class SearchServiceImplTest extends Specification {
	
	private RestTemplate restTemplate = Mock()
	@SpringBean
	private JsonToSearchResultsMapper mapper = Mock()
	private SearchService searchService
	
	def setup() {
		searchService = new SearchServiceImpl(mapper, restTemplate)	
	}

	
	def "should return list of Search Result" () {
		given:
		restTemplate.getForEntity(_, String.class) >> new ResponseEntity("", OK)
		mapper.convertJsonToListOfSearchResults(_) >> List.of(new SearchResult())
		
		when:
		List<SearchResult> searchResult = searchService.search("test")

		then:
		searchResult.size() == 1
	}
}
