import java.util.ArrayList; 
import java.util.Arrays; 
import java.util.Collections;

public class Route { 
	
	private ArrayList<City> cities = new ArrayList<City>();

	//constructeurs 
	public Route(Route route) {
		//-->à partird'une autre route 
		this.cities.addAll(route.cities) ; 
	}

	public Route(ArrayList<City> cities){ 
		//-->à partir  d'une liste de villes qu'on mélange pour ne pas garder le même ordre 
		this.cities.addAll(cities); 
		Collections.shuffle(this.cities); 
	}
	
	public String toString(){ return Arrays.toString(cities.toArray());}

	public ArrayList<City> getCities(){ return this.cities;}

	public double getTotalDistance(){
		int citiesSize = this.cities.size();
		int s = 0 ; 
		for ( int j = 1 ; j < citiesSize ; j++) {
			s += cities.get(j).measureDistance(cities.get(j-1));
		}
		s += cities.get(citiesSize-1).measureDistance(cities.get(0));
		return s ;} 

}

