import java.util.ArrayList; 
import java.util.Arrays; 
import java.util.Collections;

public class Route { 
	
	private ArrayList<City> cities = new ArrayList<City>();

	public String toString(){ return Arrays.toString(cities.toArray());}

	//constructeurs 
	
	public Route(Route route) {
		//-->� partird'une autre route 
	
		this.cities.addAll(route.cities) ; 
		/*
		 * ajouter les villes de la route en param comme villes de cette route 
		 * 
		 * */
		//� compl�ter 
	}
	
	public Route(ArrayList<City> cities){ 
		//-->� partir  d'une liste de villes qu'on m�lange pour ne pas garder le m�me ordre 
	
		this.cities.addAll(cities); 
		//m�langer al�atoirement les villes de la route 
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
