package coronavirustracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/*
 * Service class to get the data from source url
 */
import javax.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import coronavirustracker.models.LocationStats;

@Service // The annotation indicates this class as a business service class
public class CoronaVirusDataService {
	
	// Constant variable with the url to make request to
	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	
	// An instance of LocationStats class-model to keep fetched data in memory
	private List<LocationStats> allStats = new ArrayList<>();
	
	// A method to send  http request and get response data
	@PostConstruct // The annotation that tells Spring to execute this method when application starts
	@Scheduled(cron = "* * 1 * * *") // The annotation to tell Spring to run this method in specified schedule
	public void fetchVirusData() throws IOException, InterruptedException {
		
		// Create a new instance of LocationStats class to keep a fresh data fetched from and then populate it into the allStats
		List<LocationStats> newStats = new ArrayList<>();
		
		// Create an object of HttpClient class to operate with http requests and responses
		HttpClient client = HttpClient.newHttpClient();
		
		// Create an object of HttpRequest class with a builder's setup
		HttpRequest request = HttpRequest.newBuilder()
				// Create an uri instance and pass it to the request builder
				.uri(URI.create(VIRUS_DATA_URL)) 
				.build();
		
		// Tell the client to send the http request and get the response back and convert its body into the String 
		// Assign the response string-object to the instance of the HttpResponse class
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
	
		// Create a new StringReader class instance and build it from response body
		StringReader csvBodyReader = new StringReader(httpResponse.body());
		
		// Create a new instance of Iterable class and build it from our StringReader object parsed with CSVFormat library
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		// Loop through the iterable records and headers values for each column in that csv file
		for (CSVRecord record : records) {
			// Create a new instance of LocationStats to accommodate data
			LocationStats locationStat = new LocationStats();
		    locationStat.setState(record.get("Province/State"));
		    locationStat.setCountry(record.get("Country/Region"));
		    locationStat.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));
		    
		    // Print the result
		    System.out.println(locationStat);
		    // Populate new data into the temporary array
		    newStats.add(locationStat);
		    }
		// Add fresh data to the storage
		this.allStats = newStats;		
	}

}
