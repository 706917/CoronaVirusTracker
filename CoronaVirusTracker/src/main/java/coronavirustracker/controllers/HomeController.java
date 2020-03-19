/*
 * Controller to define the http response to the home page
 * It renders the stats from service into the html
 */

package coronavirustracker.controllers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import coronavirustracker.models.LocationStats;
import coronavirustracker.service.CoronaVirusDataService;

@Controller
public class HomeController {
	
	@Autowired // inject a service class instance
	CoronaVirusDataService coronaVirusDataService;
	
	@GetMapping("/")
	public String home(Model model) {
		// Create a local variable with all statistics object
		List <LocationStats> allStat = coronaVirusDataService.getAllStats();
		
		// Loop through the list of objects of local statistics data, convert it to the stream, get the number of latest cases
		// map it to Integer, and sum it up
		int totalReportedCases = allStat.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		
		// Add the desired attributes to the model to provide it's rendering into html
		model.addAttribute("locationStats", allStat);
		model.addAttribute("totalReportedCases", totalReportedCases);
		return "home";
	}

}
