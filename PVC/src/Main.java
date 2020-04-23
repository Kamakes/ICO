import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;


public class Main {
	final static Scanner stdinput = new Scanner(System.in);


	static File 	dirDot;		//	Dossier des fichiers Dot
	static File 	dirPng;		//	Dossier des fichiers Png
	static String	signature;	// 	Chaîne d'identification	


	public static ArrayList<City> creationroute() {

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
		City Angers = new City("Angers", 47.4667, -0.55);
		City Annecy = new City("Annecy", 45.9, 6.1167); 
		City Belfort = new City("Belfort", 47.6333, 6.8667);
		City Caen = new City("Caen", 47.3167, 5.0167);
		City Chartres = new City("Chartres", 48.4469, 1.4892);
		City Grenoble = new City("Grenoble", 45.1667, 5.7167);
		City Montpellier = new City("Montpellier", 43.6, 3.8833);
		City Metz = new City("Metz", 49.1333, 6.16667); 
		City Nancy = new City("Nancy", 48.683, 6.2); 
		
		//creation chemin
		ArrayList<City> cities = new ArrayList<City>();
		cities.add(Paris);
		cities.add(Lyon);
		cities.add(Nantes);
		cities.add(Marseille); 
		cities.add(Dijon);
		cities.add(Bordeaux);
		cities.add(Rennes);
		cities.add(Brest);
		cities.add(Toulouse);
		/*cities.add(Strasbourg);
		cities.add(Nice);
		cities.add(Lille);
		cities.add(Angers);
		cities.add(Annecy);
		cities.add(Belfort);
		cities.add(Caen);
		cities.add(Chartres);
		cities.add(Grenoble);
		cities.add(Montpellier);
		cities.add(Metz);
		cities.add(Nancy);*/
		return cities;
	}
	
		// --------------------------------------------------------------------- //
		//				V A R I A B L E S   D E   C L A S S E					 //
		// --------------------------------------------------------------------- //
	public static void main(String[] args) {
		
			
			int choix;
			//do {
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
			//	} 
			} 
				
	}
		// -------------------------------------------------------------------- //

		// -------------------------------------------------------------------- //
		//								M E N U									//
		// -------------------------------------------------------------------- //
		static int menu() {
			System.out.println();
			System.out.println("Pour tester l'algorithme tabou ...................... tapez 1");
			System.out.println("Pour tester l'algorithme RC ......................... tapez 2");
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
	}

//-------------------------------------------------------------------- //
//							Algorithme RC								//
//-------------------------------------------------------------------- //
	static void testRC() {
	//RC
		ArrayList<City> cities = creationroute();
		Route Route1 = new Route(cities);
		System.out.println(Route1.toString());
		System.out.println(Route1.getTotalDistance());
		
		SolutionRC solution1 = new SolutionRC(100, 0.995);
		Route s_f = solution1.RC(Route1); 
		System.out.print(s_f);
		System.out.print(s_f.getTotalDistance());
	
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
		int generationNumbre = 1;
		while (generationNumbre < SolutionAG.NUM_GENERATIONS) {
			population = ag.evolve(population);
			population.sortRouteByFitness();
			generationNumbre++;

		}
		
		System.out.println(population.getRoutes().get(0));
		System.out.println(" Distance Totale est :"
				+ String.format("%.2f", population.getRoutes().get(0).getTotalDistance()) + "Kms");

	}



//-------------------------------------------------------------------- //
//							Comparaison								//
//-------------------------------------------------------------------- //
	static void compare() {
//comparaison et why not créer un tableau excel ou graphe automatiquement

	}
//-------------------------------------------------------------------- //
//						 Combinaison									//
//-------------------------------------------------------------------- //
	static void combine() {
		
		ArrayList<City> cities = creationroute();
		Route Route1 = new Route(cities);
		
		SolutionRC solution1 = new SolutionRC(100, 0.995);
		Route s_f = solution1.RC(Route1); 
		
		SolutionTabou solution = new SolutionTabou(cities,10);
		solution.optimiserTabou();

		
		try {
			Runtime rt = Runtime.instance();
			Properties p = new ExtendedProperties(); 
			p.setProperty("gui", "true"); 
			ProfileImpl pc = new ProfileImpl(p);
			AgentContainer container = rt.createMainContainer(pc);
			AgentController AgentTabou = container.createNewAgent("AgentTabou", "AgentTabou", new Object[] {solution.getBestPath()});
			AgentController AgentRC = container.createNewAgent("AgentRC", "AgentRC", new Object[] {s_f});

			AgentTabou.start(); 

			AgentRC.start();
			container.start();

			
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}
//-------------------------------------------------------------------- //
//							deuxième test								//
//-------------------------------------------------------------------- //
	static void secondtest() {

	}

}
