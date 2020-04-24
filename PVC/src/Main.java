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
	static String	signature;	// 	Cha�ne d'identification	


	public static ArrayList<City> creationroute() {

		City  Paris = new City ("Paris",48.86,2.34445);
		City  Marseille = new City ("Marseille",43.2967,5.37639);
		City  Lyon = new City ("Lyon",45.7589,4.84139);
		City  Toulouse = new City ("Toulouse",43.6,1.43333);
		City  Nice = new City ("Nice",43.7,7.25);
		City  Nantes = new City ("Nantes",47.2167,-1.55);
		City  Strasbourg = new City ("Strasbourg",48.5833,7.75);
		City  Montpellier = new City ("Montpellier",43.6,3.88333);
		City  Bordeaux = new City ("Bordeaux",44.8333,-0.566667);
		City  Lille = new City ("Lille",50.6333,3.06667);
		City  Rennes = new City ("Rennes",48.0833,-1.68333);
		City  Reims = new City ("Reims",49.25,4.03333);
		City  Le_Havre = new City ("Le Havre",49.5,0.133333);
		City  Saint_Etienne = new City ("Saint-Etienne",45.4333,4.4);
		City  Toulon = new City ("Toulon",43.1167,5.93333);
		City  Grenoble = new City ("Grenoble",45.1667,5.71667);
		City  Dijon = new City ("Dijon",47.3167,5.01667);
		City  Angers = new City ("Angers",47.4667,-0.55);
		City  Saint_Denis = new City ("Saint-Denis",1.71819,46.7107);
		City  Le_Mans = new City ("Le Mans",48,0.2);
		City  Aix_en_Provence = new City ("Aix-en-Provence",43.5333,5.43333);
		City  Brest = new City ("Brest",48.4,-4.48333);
		City  Villeurbanne = new City ("Villeurbanne",45.7667,4.88333);
		City  N�mes = new City ("N�mes",43.8333,4.35);
		City  Limoges = new City ("Limoges",45.85,1.25);
		City  Clermont_Ferrand = new City ("Clermont-Ferrand",45.7833,3.08333);
		City  Tours = new City ("Tours",47.3833,0.683333);
		City  Amiens = new City ("Amiens",49.9,2.3);
		City  Metz = new City ("Metz",49.1333,6.16667);
		City  Besan�on = new City ("Besan�on",47.25,6.03333);
		City  Perpignan = new City ("Perpignan",42.6833,2.88333);
		City  Orl�ans = new City ("Orl�ans",47.9167,1.9);
		City  Boulogne_Billancourt = new City ("Boulogne-Billancourt",48.8333,2.25);
		City  Mulhouse = new City ("Mulhouse",47.75,7.33333);
		City  Caen = new City ("Caen",49.1833,-0.35);
		City  Rouen = new City ("Rouen",49.4333,1.08333);
		City  Nancy = new City ("Nancy",48.6833,6.2);
		City  Saint_Paul = new City ("Saint-Paul",55.2833,-21.0033);
		City  Argenteuil = new City ("Argenteuil",48.95,2.25);
		City  Montreuil = new City ("Montreuil",48.8667,2.43333);
		City  Roubaix = new City ("Roubaix",50.7,3.16667);
		City  Dunkerque = new City ("Dunkerque",51.05,2.36667);
		City  Tourcoing = new City ("Tourcoing",50.7167,3.15);
		City  Avignon = new City ("Avignon",43.95,4.81667);
		City  Nanterre = new City ("Nanterre",48.9,2.2);
		City  Poitiers = new City ("Poitiers",46.5833,0.333333);
		City  Cr�teil = new City ("Cr�teil",48.7833,2.46667);
		City  Fort_de_France = new City ("Fort-de-France",-61.0732,14.627);
		City  Versailles = new City ("Versailles",48.8,2.13333);
		City  Courbevoie = new City ("Courbevoie",48.8973,2.25222);
		City  Vitry_sur_Seine = new City ("Vitry-sur-Seine",48.7833,2.4);
		City  Pau = new City ("Pau",43.3,-0.366667);
		City  Colombes = new City ("Colombes",48.9167,2.25);
		City  Aulnay_sous_Bois = new City ("Aulnay-sous-Bois",48.95,2.51667);
		City  Asni�res_sur_Seine = new City ("Asni�res-sur-Seine",48.9112,2.28556);
		City  Saint_Pierre = new City ("Saint-Pierre",6.87387,43.6566);
		City  Rueil_Malmaison = new City ("Rueil-Malmaison",48.8833,2.2);
		City  Antibes = new City ("Antibes",43.5833,7.11667);
		City  La_Rochelle = new City ("La Rochelle",46.1667,-1.15);
		City  Saint_Maur_des_Fossces = new City ("Saint-Maur-des-FossǸs",48.8,2.5);
		City  Champigny_sur_Marne = new City ("Champigny-sur-Marne",48.8167,2.51667);
		City  Calais = new City ("Calais",50.95,1.83333);
		City  Aubervilliers = new City ("Aubervilliers",48.9167,2.38333);
		City  Tampon = new City ("Tampon",55.518,-21.2815);
		City  Cannes = new City ("Cannes",43.55,7.01667);
		City  B�ziers = new City ("B�ziers",43.35,3.25);
		City  Bourges = new City ("Bourges",47.0833,2.4);
		City  Saint_Nazaire = new City ("Saint-Nazaire",47.2833,-2.2);
		City  Colmar = new City ("Colmar",48.0833,7.36667);
		City  Drancy = new City ("Drancy",48.9333,2.45);
		City  M�rignac = new City ("M�rignac",44.8333,-0.633333);
		City  Ajaccio = new City ("Ajaccio",41.9256,8.7364);
		City  Valence = new City ("Valence",44.9333,4.9);
		City  Quimper = new City ("Quimper",48,-4.1);
		City  Villeneuve_dAscq = new City ("Villeneuve-d'Ascq",50.6833,3.14167);

		
		//creation chemin
		ArrayList<City> cities = new ArrayList<City>();
		cities.add(Paris);
		cities.add(Marseille);
		cities.add(Lyon);
		cities.add(Toulouse);
		cities.add(Nice);
		cities.add(Nantes);
		cities.add(Strasbourg);
		cities.add(Montpellier);
		cities.add(Bordeaux);
		cities.add(Lille);
		cities.add(Rennes);
		cities.add(Reims);
		cities.add(Le_Havre);
		cities.add(Saint_Etienne);
		cities.add(Toulon);
		cities.add(Grenoble);
		cities.add(Dijon);
		cities.add(Angers);
		cities.add(Le_Mans);
		cities.add(Aix_en_Provence);
		cities.add(Brest);
		cities.add(Villeurbanne);
		cities.add(N�mes);
		cities.add(Limoges);
		cities.add(Clermont_Ferrand);
		cities.add(Tours);
		cities.add(Amiens);
		cities.add(Metz);
		cities.add(Besan�on);
		cities.add(Perpignan);
		cities.add(Orl�ans);
		cities.add(Boulogne_Billancourt);
		cities.add(Mulhouse);
		cities.add(Caen);
		cities.add(Rouen);
		cities.add(Nancy);
		cities.add(Saint_Denis);
		cities.add(Saint_Paul);
		cities.add(Argenteuil);
		cities.add(Montreuil);
		cities.add(Roubaix);
		cities.add(Dunkerque);
		cities.add(Tourcoing);
		cities.add(Avignon);
		cities.add(Nanterre);
		cities.add(Poitiers);
		cities.add(Cr�teil);
		cities.add(Fort_de_France);
		cities.add(Versailles);
		cities.add(Courbevoie);
		cities.add(Vitry_sur_Seine);
		cities.add(Pau);
		cities.add(Colombes);
		cities.add(Aulnay_sous_Bois);
		cities.add(Asni�res_sur_Seine);
		cities.add(Saint_Pierre);
		cities.add(Rueil_Malmaison);
		cities.add(Antibes);
		cities.add(La_Rochelle);
		cities.add(Saint_Maur_des_Fossces);
		cities.add(Champigny_sur_Marne);
		cities.add(Calais);
		cities.add(Aubervilliers);
		cities.add(Tampon);
		cities.add(Cannes);
		cities.add(B�ziers);
		cities.add(Bourges);
		cities.add(Saint_Nazaire);
		cities.add(Colmar);
		cities.add(Drancy);
		cities.add(M�rignac);
		cities.add(Ajaccio);
		cities.add(Valence);
		cities.add(Quimper);
		cities.add(Villeneuve_dAscq);
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
					case 2 : testRS(); break;
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
			System.out.println("Pour tester l'algorithme RS ......................... tapez 2");
			System.out.println("Pour tester l'algorithme g�n�tique .................. tapez 3");
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
		SolutionTabou solution = new SolutionTabou(cities,3, 500);
		System.out.println(solution.getBestPath().toString());
		System.out.println(solution.getBestPath().getTotalDistance());
		solution.optimiserTabou();
		System.out.println(solution.getBestPath().toString());
		System.out.println(solution.getBestPath().getTotalDistance());
	}

//-------------------------------------------------------------------- //
//							Algorithme RC								//
//-------------------------------------------------------------------- //
	static void testRS() {
	//RC
		ArrayList<City> cities = creationroute();
		Route Route1 = new Route(cities);
		System.out.println(Route1.toString());
		System.out.println(Route1.getTotalDistance());
		
		SolutionRS solution1 = new SolutionRS(100, 0.995);
		Route s_f = solution1.RS(Route1); 
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
//comparaison et why not cr�er un tableau excel ou graphe automatiquement

	}
//-------------------------------------------------------------------- //
//						 Combinaison									//
//-------------------------------------------------------------------- //
	static void combine() {
		
		ArrayList<City> cities = creationroute();
		Route Route1 = new Route(cities);
		
		SolutionRS solution1 = new SolutionRS(100, 0.995);
		Route s_f = solution1.RS(Route1); 
		
		SolutionTabou solution = new SolutionTabou(cities,3, 500);
		solution.optimiserTabou();

		
		try {
			Runtime rt = Runtime.instance();
			Properties p = new ExtendedProperties(); 
			p.setProperty("gui", "true"); 
			ProfileImpl pc = new ProfileImpl(p);
			AgentContainer container = rt.createMainContainer(pc);
			AgentController AgentTabou = container.createNewAgent("AgentTabou", "AgentTabou", new Object[] {solution.getBestPath()});
			AgentController AgentRS = container.createNewAgent("AgentRS", "AgentRS", new Object[] {s_f});

			AgentTabou.start(); 

			AgentRS.start();
			container.start();

			
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}
//-------------------------------------------------------------------- //
//							deuxi�me test								//
//-------------------------------------------------------------------- //
	static void secondtest() {

	}

}
