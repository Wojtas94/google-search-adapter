package com.avenga.googlesearchadapter.controller

import org.openapitools.model.Details
import org.openapitools.model.SearchResult
import org.spockframework.spring.SpringBean
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClientException

import com.avenga.googlesearchadapter.controller.ExceptionHandlerController
import com.avenga.googlesearchadapter.controller.SearchController
import com.avenga.googlesearchadapter.service.SearchService
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import spock.lang.Specification

@WebMvcTest
@AutoConfigureMockMvc
class SearchControllerTest extends Specification {
	
	@SpringBean
	private SearchService searchService = Mock()
	@SpringBean
	private ExceptionHandlerController exceptionHandlerController = new ExceptionHandlerController()
	private SearchController searchController
	private MockMvc mvc
	private ObjectMapper objectMapper = new ObjectMapper()
	
	def setup() {
		searchController = new SearchController(searchService)
		mvc = MockMvcBuilders
				.standaloneSetup(searchController)
				.setControllerAdvice(exceptionHandlerController)
				.build()
	}
	
	def "should return result by search phrase"() {
		given:
		String searchPhrase = "Robert Lewandowski"
		def details = [Details.builder()
							.firstname("Robert")
							.birthday("1988")
							.category("football")
							.nickname("Robert")
							.label("Robert")
							.role("striker")
							.url("robert.com").build()]
		def searchResults = [SearchResult.builder()
										.title(searchPhrase)
										.details(details).build()]								
        searchService.search(searchPhrase) >> searchResults

	    when:
		def response = mvc.perform(get("/search")
			.param("searchPhrase", searchPhrase))
			.andExpect(status().isOk())
			.andReturn()
			
		then: "expect status is 200"
		response.andExpect(status().isOk()) 
		def result = Arrays.asList(objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), SearchResult[].class))
		result == searchResults;	
	}
	
	def "should return status 500" () {
		given:
		String searchPhrase = "Robert Lewandowski"
		searchService.search("Robert Lewandowski") >> { throw new HttpClientErrorException(INTERNAL_SERVER_ERROR) }
		
		when:
		def response = mvc.perform(get("/search")
			.param("searchPhrase", searchPhrase))
			.andExpect(status().isServiceUnavailable())
			
		then: "expect status is 500"
		response.andExpect(status().isInternalServerError())
	}
}
