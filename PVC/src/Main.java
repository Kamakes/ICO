import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList; 
import java.util.Arrays; 
import java.util.Collections;
public class Main {
	final static Scanner stdinput = new Scanner(System.in);


	static File 	dirDot;		//	Dossier des fichiers Dot
	static File 	dirPng;		//	Dossier des fichiers Png
	static String	signature;	// 	Chaîne d'identification	



	public static ArrayList<City> creationroute() {

		//Declaration villes
		City Paris = new City("Paris", 48.8534, 2.3488);
		City Lyon = new City("Lyon", 45.75, 4.85);
		City Nantes = new City("Nantes", 47.2173, -1.5534); 
		City Marseille = new City("Marseille", 43.3, 5.4);
		City Dijon = new City("Dijon", 47.3167, 5.0167);
		City Bordeaux = new City("Bordeaux", 44.8333, -0.5667);
		City Rennes = new City("Rennes", 48.069, -1.6146);
		City Brest = new City("Brest", 48.309, -4.507);
		City Toulouse = new City("Montpellier", 43.571, 3.9624);
		City Strasbourg = new City("Strasbourg", 48.522, 7.727);
		City Nice = new City("Nice", 43.702, 7.315);
		City Lille = new City("Lille", 50.657, 30.004);
		
		//creation chemin
		ArrayList<City> cities = new ArrayList<City>();
		cities.add(Paris);
		cities.add(Lyon);
		cities.add(Nantes);
		cities.add(Marseille); 
		cities.add(Dijon);
		cities.add(Bordeaux);
		//cities.add(Rennes);
		//cities.add(Brest);
		//cities.add(Toulouse);
		//cities.add(Strasbourg);
		//cities.add(Nice);
		//cities.add(Lille);
		return cities;
	}
	
	

		// --------------------------------------------------------------------- //
		//				V A R I A B L E S   D E   C L A S S E					 //
		// --------------------------------------------------------------------- //
	public static void main(String[] args) {
			try {
				prologue();
			} catch (UnknownHostException e) {
				System.err.println(e);
			}
			
			int choix;
			do {
				choix = menu();
				switch (choix) {
					case 0 : break;
					case 1 : testtabou(); break;
					case 2 : testRC(); break;
					case 3 : testAG(); break;
					case 4 : compare(); break;
					case 5 : combine(); break;
					case 6 : secondtest(); break;
		
					default:
						System.out.println("Choix incorrect. Recommencez !\n\r"); 
						break;
				}
			} while (choix != 0);
				
			epilogue("\n[That's all folks!]");
	}
		// -------------------------------------------------------------------- //

		// -------------------------------------------------------------------- //
		//								M E N U									//
		// -------------------------------------------------------------------- //
		static int menu() {
			System.out.println();
			System.out.println("Pour tester l'algorithme tabou ...................... tapez 1");
			System.out.println("Pour tester l'algorithme RS ......................... tapez 2");
			System.out.println("Pour tester l'algorithme génétique .................. tapez 3");
			System.out.println("Pour comparer les 3 algorithme....................... tapez 4");
			System.out.println("Pour tester la combinaison des algorithmes  ......... tapez 5");
			System.out.println("Pour comparer les 3 parcours pour un autre trajet ... tapez 6");
			System.out.println("Pour terminer ....................................... tapez 0");

			System.out.print("\nVotre choix : ");
			String s = stdinput.next();
			try {
				return Integer.parseInt(s);
			}
			catch (Exception e) { 
				return -1;
			}

		}	

// -------------------------------------------------------------------- //
//							Algorithme Tabou							//
//-------------------------------------------------------------------- //
static void testtabou(){	
//tabou
	ArrayList<City> cities = creationroute();
	SolutionTabou solution = new SolutionTabou(cities,10);
	System.out.println(solution.getBestPath().toString());
	System.out.println(solution.getBestPath().getTotalDistance());
	solution.optimiserTabou();
	System.out.println(solution.getBestPath().toString());
	System.out.println(solution.getBestPath().getTotalDistance());
epilogue("[Fin de testtabou]");
}

//-------------------------------------------------------------------- //
//							Algorithme RS								//
//-------------------------------------------------------------------- //
static void testRC() {
//RC
	ArrayList<City> cities = creationroute();
	Route Route1 = new Route(cities);
	System.out.println(Route1.toString());
	//System.out.println(Route1.getTotalDistance());
	
	SolutionRC solution1 = new SolutionRC(1000, 1500, 0.1);
	Route s_f = solution1.RC(Route1); 
	System.out.print(s_f);
	System.out.print(s_f.getTotalDistance());

epilogue("[Fin de testRC]");
}
//-------------------------------------------------------------------- //
//							Algorithme AG								//
//-------------------------------------------------------------------- //
static void testAG() {
	ArrayList<City> cities = creationroute();
	Route Route1 = new Route(cities);
	Population population = new Population(SolutionAG.POPULATION_Size, cities);
	population.sortRouteByFitness();
	SolutionAG ag = new SolutionAG(cities);
	int generationNumbre = 0;
	Main.printPopulation(population);
	Main.printHeading(generationNumbre++);
	while (generationNumbre < SolutionAG.NUM_GENERATIONS) {
		Main.printHeading(generationNumbre++);
		population = ag.evolve(population);
		population.sortRouteByFitness();
		Main.printPopulation(population);
}
System.out.println(" la meilleure route trouvee est :" + population.getRoutes().get(0));
System.out.println(" Distance Totale est :"
		+ String.format("%.2f", population.getRoutes().get(0).getTotalDistance()) + "Kms");
}

public static void printPopulation(Population population) {
population.getRoutes().forEach(x -> {
	System.out.print(Arrays.toString(x.getCities().toArray()) + "  |  " + String.format("%.4f", x.getFitness())
			+ "  |  " + String.format("%.2f", x.getTotalDistance()));
});
System.out.println("");
}

public static void printHeading(int generationNumbre) {
	ArrayList<City> cities = creationroute();
	Route Route1 = new Route(cities);
	System.out.println("> Generation #" + generationNumbre);
	String headingColumn1 = "Route";
	String headingColumns = "Fitness |  Distance";
	int cityNames = 0;
	for (int x = 0; x < cities.size(); x++)
		cityNames += cities.get(x).getName().length();
	int arrayLength = cityNames + cities.size() * 2;
	int partialLength = (arrayLength - headingColumn1.length() / 2);
	for (int x = 0; x < partialLength; x++)
		System.out.println("");
	if ((arrayLength % 2) == 0)
		System.out.println("");
	System.out.println("|" + headingColumns);
	cityNames += headingColumns.length() + 3;
	for (int x = 0; x < cityNames + cities.size() * 2; x++)
		System.out.println("-");
	System.out.println("");

epilogue("[Fin de testAG]");
}
//-------------------------------------------------------------------- //
//							Comparaisoon								//
//-------------------------------------------------------------------- //
	static void compare() {
//comparaison et why not créer un tableau excel ou graphe automatiquement

		epilogue("[Fin de compare]");
}
//-------------------------------------------------------------------- //
//						 Combinaison									//
//-------------------------------------------------------------------- //
	static void combine() {
//de même
		epilogue("[Fin de combine]");
}
//-------------------------------------------------------------------- //
//							deuxième test								//
//-------------------------------------------------------------------- //
	static void secondtest() {
//idem
}

	static void prologue() throws UnknownHostException {

//	Création de la chaîne d'identification
		signature = System.getProperty("user.name");
		InetAddress addr = InetAddress.getLocalHost();
		signature += "\t" + addr.getHostAddress() + "\t" + 
				addr.getHostName() + "\t   ";
}

//--------------------------------------------------------------------- //
/**
* Affiche le texte défini comme paramètre puis la signature de 
* l'utiliseur puis la date et l'heure
* 
* @param msg		Message à afficher
*/
	static void epilogue(String msg)  {
		System.out.print(msg + "\n" + signature);
		
		
	}
		
	}
