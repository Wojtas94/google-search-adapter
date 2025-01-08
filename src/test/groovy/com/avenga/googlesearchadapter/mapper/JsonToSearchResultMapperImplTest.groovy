package com.avenga.googlesearchadapter.mapper

import org.openapitools.model.SearchResult
import org.springframework.boot.test.context.SpringBootTest

import spock.lang.Specification

@SpringBootTest
class JsonToSearchResultMapperImplTest extends Specification {
	
	private JsonToSearchResultsMapper mapper = new JsonToSearchResultsMapperImpl();
	
	def "should return list converted from json"() {
		given:
		String json = "{\"items\":[{\"title\":\"RobertLewandowski-Wikipedia\",\"pagemap\":{\"hcard\":[{\"role\":\"Striker\",\"bday\":\"1988-08-21\",\"fn\":\"RobertLewandowski\",\"nickname\":\"RobertLewandowski[1]\"}]}},{\"title\":\"ListofinternationalgoalsscoredbyRobertLewandowski-Wikipedia\"},{\"title\":\"ListofBundesligatopscorersbyseason-Wikipedia\",\"pagemap\":{\"hcard\":[{\"fn\":\"UweSeeler\"},{\"fn\":\"RudolfBrunnenmeier\"},{\"fn\":\"1860Munich\"},{\"fn\":\"LotharEmmerich\"},{\"fn\":\"BorussiaDortmund\"},{\"fn\":\"LotharEmmerich\"},{\"fn\":\"BorussiaDortmund\"},{\"fn\":\"BayernMunich\"},{\"fn\":\"JohannesLöhr\"},{\"fn\":\"1.FCKöln\"}]}},{\"title\":\"AnnaLewandowska-Wikipedia\",\"pagemap\":{\"hcard\":[{\"role\":\"karateka,personaltrainer,dietician,entrepreneur,TVpresenter\",\"bday\":\"1988-09-07\",\"fn\":\"AnnaLewandowska\",\"category\":\"Poland\"}]}},{\"title\":\"Lewandowski-Wikipedia\"},{\"title\":\"GerdMüllerTrophy-Wikipedia\"},{\"title\":\"BrothersLewandowski-Wikipedia\"},{\"title\":\"File:RobertLewandowski2018(cropped).jpg-WikimediaCommons\"},{\"title\":\"File:RobertLewandowskiTrainingFCBayernMünchen-3.jpg...\"},{\"title\":\"ListofUEFAChampionsLeaguehat-tricks-Wikipedia\",\"pagemap\":{\"hcard\":[{\"fn\":\"JuulEllerman\"},{\"fn\":\"Romário\",\"nickname\":\"Romário\"},{\"fn\":\"MarcovanBasten\"},{\"fn\":\"FranckSauzée\"},{\"fn\":\"SergeyRodionov\"},{\"fn\":\"BerndHobsch\"},{\"fn\":\"LucNilis\"},{\"fn\":\"JariLitmanen\"},{\"fn\":\"Raúl\",\"nickname\":\"Raúl\"},{\"fn\":\"MikeNewell\"}]}}]}"
		
		when:
		List<SearchResult> searchResults = mapper.convertJsonToListOfSearchResults(json);
		
		then:
		searchResults.size() == 4
		searchResults.get(0).getTitle() == "RobertLewandowski-Wikipedia";
		searchResults.get(0).getDetails().size() == 1
		searchResults.get(1).getTitle() == "ListofBundesligatopscorersbyseason-Wikipedia";
		searchResults.get(1).getDetails().size() == 10
	}
}
