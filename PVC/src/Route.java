import java.util.ArrayList; 
import java.util.Arrays; 
import java.util.Collections;

public class Route { 
	
	private ArrayList<City> cities = new ArrayList<City>();

	public String toString(){ return Arrays.toString(cities.toArray());}

	//constructeurs 
	
	public Route(Route route) {
		//-->à partird'une autre route 
	
		this.cities.addAll(route.cities) ; 
		/*
		 * ajouter les villes de la route en param comme villes de cette route 
		 * 
		 * */
		//à compléter 
	}
	
	public Route(ArrayList<City> cities){ 
		//-->à partir  d'une liste de villes qu'on mélange pour ne pas garder le même ordre 
	
		this.cities.addAll(cities); 
		//mélanger aléatoirement les villes de la route 
		Collections.shuffle(this.cities); 

	}
	
	
	public ArrayList<City> getCities(){ 
		return cities;}
	
	public double getTotalDistance(){
		int citiesSize = this.cities.size();
		int s = 0 ; 
		for ( int j = 2 ; j < citiesSize ; j++) {
			s += cities.get(j).measureDistance(cities.get(j-1));
			}
		return s ;} 
	
}
