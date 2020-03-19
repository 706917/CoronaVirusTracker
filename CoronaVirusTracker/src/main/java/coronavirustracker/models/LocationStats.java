/*
 * Class defines a Model instance to save data fetched from source url in memory
 */

package coronavirustracker.models;

public class LocationStats {
	
	// Columns to keep data
	private String state; // name of state or province
	private String country; // name of country
	private int latestTotalCases; // the amount of confirmed cases in the latest date
	
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestTotalCases() {
		return latestTotalCases;
	}
	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
	
	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", latestTotalCases=" + latestTotalCases
				+ "]";
	}
	
	
		
}

