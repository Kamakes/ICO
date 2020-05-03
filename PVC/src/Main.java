import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import Algorithmes.City;
import Algorithmes.Population;
import Algorithmes.Route;
import Algorithmes.SolutionAG;
import Algorithmes.SolutionRS;
import Algorithmes.SolutionTabou;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;


public class Main {
	final static Scanner stdinput = new Scanner(System.in);
	
	public static ArrayList<City> creationroute() {
		
		// Création d'une liste contenant les villes suivantes : 
		
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
		City  Nîmes = new City ("Nîmes",43.8333,4.35);
		City  Limoges = new City ("Limoges",45.85,1.25);
		City  Clermont_Ferrand = new City ("Clermont-Ferrand",45.7833,3.08333);
		City  Tours = new City ("Tours",47.3833,0.683333);
		City  Amiens = new City ("Amiens",49.9,2.3);
		City  Metz = new City ("Metz",49.1333,6.16667);
		City  Besançon = new City ("Besançon",47.25,6.03333);
		City  Perpignan = new City ("Perpignan",42.6833,2.88333);
		City  Orléans = new City ("Orléans",47.9167,1.9);
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
		City  Créteil = new City ("Créteil",48.7833,2.46667);
		City  Fort_de_France = new City ("Fort-de-France",-61.0732,14.627);
		City  Versailles = new City ("Versailles",48.8,2.13333);
		City  Courbevoie = new City ("Courbevoie",48.8973,2.25222);
		City  Vitry_sur_Seine = new City ("Vitry-sur-Seine",48.7833,2.4);
		City  Pau = new City ("Pau",43.3,-0.366667);
		City  Colombes = new City ("Colombes",48.9167,2.25);
		City  Aulnay_sous_Bois = new City ("Aulnay-sous-Bois",48.95,2.51667);
		City  Asnières_sur_Seine = new City ("Asnières-sur-Seine",48.9112,2.28556);
		City  Saint_Pierre = new City ("Saint-Pierre",6.87387,43.6566);
		City  Rueil_Malmaison = new City ("Rueil-Malmaison",48.8833,2.2);
		City  Antibes = new City ("Antibes",43.5833,7.11667);
		City  La_Rochelle = new City ("La Rochelle",46.1667,-1.15);
		City  Saint_Maur_des_Fossces = new City ("Saint-Maur-des-FossÇ¸s",48.8,2.5);
		City  Champigny_sur_Marne = new City ("Champigny-sur-Marne",48.8167,2.51667);
		City  Calais = new City ("Calais",50.95,1.83333);
		City  Aubervilliers = new City ("Aubervilliers",48.9167,2.38333);
		City  Tampon = new City ("Tampon",55.518,-21.2815);
		City  Cannes = new City ("Cannes",43.55,7.01667);
		City  Béziers = new City ("Béziers",43.35,3.25);
		City  Bourges = new City ("Bourges",47.0833,2.4);
		City  Saint_Nazaire = new City ("Saint-Nazaire",47.2833,-2.2);
		City  Colmar = new City ("Colmar",48.0833,7.36667);
		City  Drancy = new City ("Drancy",48.9333,2.45);
		City  Mérignac = new City ("Mérignac",44.8333,-0.633333);
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
		cities.add(Nîmes);
		cities.add(Limoges);
		cities.add(Clermont_Ferrand);
		cities.add(Tours);
		cities.add(Amiens);
		cities.add(Metz);
		cities.add(Besançon);
		cities.add(Perpignan);
		cities.add(Orléans);
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
		cities.add(Créteil);
		cities.add(Fort_de_France);
		cities.add(Versailles);
		cities.add(Courbevoie);
		cities.add(Vitry_sur_Seine);
		cities.add(Pau);
		cities.add(Colombes);
		cities.add(Aulnay_sous_Bois);
		cities.add(Asnières_sur_Seine);
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
		cities.add(Béziers);
		cities.add(Bourges);
		cities.add(Saint_Nazaire);
		cities.add(Colmar);
		cities.add(Drancy);
		cities.add(Mérignac);
		cities.add(Ajaccio);
		cities.add(Valence);
		cities.add(Quimper);
		cities.add(Villeneuve_dAscq);
		return cities;
	}
	
	// -------------------------------------------------------------------- //
	//								M E N U									//
	// -------------------------------------------------------------------- //

	public static void main(String[] args) {
			// Programme principal : 
		
			ArrayList<City> cities = creationroute();
			System.out.print("La route contient "+ cities.size() + "villes\n"); 
			
			// L'utilisateur choisi quel algorithme exécuter pour résoudre le problème parmis 4 possibles (Tabou, RS, AG, SMA) : 
			int choix;
				choix = menu();
				switch (choix) {
					case 0 : break;
					case 1 : testtabou(); break;
					case 2 : testRS(); break;
					case 3 : testAG(); break;
					case 4 : combine(); break;
		
					default:
						System.out.println("Choix incorrect. Recommencez !\n\r"); 
						break;
			} 
				
	}
		
		static int menu() {
			
			System.out.println("Pour tester l'algorithme tabou ...................... tapez 1");
			System.out.println("Pour tester l'algorithme RS ......................... tapez 2");
			System.out.println("Pour tester l'algorithme génétique .................. tapez 3");
			System.out.println("Pour tester le SMA  ................................. tapez 4");
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
		// On crée une route initiale contenant toutes les villes définies dans creationroute() : 
		ArrayList<City> cities = creationroute();
		SolutionTabou solution = new SolutionTabou(cities,3, 500);
		System.out.println("La route initiale est : " + solution.getBestPath().toString());
		System.out.println("Ce qui correspond à la distance  : " + solution.getBestPath().getTotalDistance());
		// On lance l'algorithme Tabou : 
		solution.optimiserTabou();
		System.out.println("La route déterminée par l'algorithme Tabou est " + solution.getBestPath().toString());
		System.out.println("Ce qui correspond à la distance : "+ solution.getBestPath().getTotalDistance());
	}

	//-------------------------------------------------------------------- //
	//							Algorithme RS								//
	//-------------------------------------------------------------------- //
	static void testRS() {
		// On crée une route initiale contenant toutes les villes définies dans creationroute() : 
		ArrayList<City> cities = creationroute();
		Route Route1 = new Route(cities);
		System.out.println("La route initiale est : " + Route1.toString());
		System.out.println("Ce qui correspond à la distance : " + Route1.getTotalDistance());
		
		// On lance l'algorithme RS avec les paramètres 100 et 0.995 : 
		SolutionRS solution1 = new SolutionRS(100, 0.995);
		// On stock la solution trouvée par l'algorithme RS dans la variable s_f :
		Route s_f = solution1.RS(Route1); 
		
		System.out.print("La route déterminée par l'algorithme RS est : " + s_f);
		System.out.print("\nCe qui correspond à la distance : " + s_f.getTotalDistance());
	
	}
	
	//-------------------------------------------------------------------- //
	//							Algorithme AG								//
	//-------------------------------------------------------------------- //
	static void testAG() {
		// On crée une route initiale contenant toutes les villes définies dans creationroute() : 
		ArrayList<City> cities = creationroute();
		Route Route1 = new Route(cities);
		System.out.println("La route initiale est : " + Route1.toString());
		System.out.println("Ce qui correspond à la distance : " + Route1.getTotalDistance());
		
		// On crée une nouvelle population à partir de la route initiale : 
		Population population = new Population(SolutionAG.POPULATION_Size, Route1);// la taille de la population = 6
		
		// On trie la population selon la fitness
		population.sortRouteByFitness();
		SolutionAG ag = new SolutionAG(cities);
		int generationNumbre = 1;
		while (generationNumbre < SolutionAG.NUM_GENERATIONS) { // le nombre de générations est à priori 1000
			population = ag.evolve(population); //l'évolution de la population
			population.sortRouteByFitness(); //un tri pour travailler qu'avec les meilleurs individus
			generationNumbre++;

		}
		
		System.out.println("La route déterminée par l'algorithme RS est : " + population.getRoutes().get(0));
		System.out.println("Ce qui correspond à la distance : "
				+ String.format("%.2f", population.getRoutes().get(0).getTotalDistance()) + "Kms");

	}


	//-------------------------------------------------------------------- //
	//						 		SMA 									//
	//-------------------------------------------------------------------- //
	static void combine() {
		
		ArrayList<City> cities = creationroute();
		Route Route1 = new Route(cities);
		// On détermine trois solutions initiales pour les agents RS, Tabou et AG : 
		SolutionRS solution1 = new SolutionRS(100, 0.995);
		Route s_f = solution1.RS(Route1); 
		
		SolutionTabou solution = new SolutionTabou(cities,3, 500);
		solution.optimiserTabou();
		
		Population population = new Population(SolutionAG.POPULATION_Size, Route1);

		population.sortRouteByFitness();
		SolutionAG ag = new SolutionAG(cities);
		int generationNumbre = 1;
		while (generationNumbre < SolutionAG.NUM_GENERATIONS) {
			population = ag.evolve(population);
			population.sortRouteByFitness();
			generationNumbre++;

		}
		
		try {
			// On lance le main container : 
			Runtime rt = Runtime.instance();
			Properties p = new ExtendedProperties(); 
			p.setProperty("gui", "true"); 
			ProfileImpl pc = new ProfileImpl(p);
			AgentContainer container = rt.createMainContainer(pc);
			
			// On crée trois agents dans le main container ayant pour arguments les routes déterminées précédemment : 
			AgentController AgentTabou = container.createNewAgent("AgentTabou", "AgentTabou", new Object[] {solution.getBestPath()});
			AgentController AgentRS = container.createNewAgent("AgentRS", "AgentRS", new Object[] {s_f});
			AgentController AgentAG = container.createNewAgent("AgentAG", "AgentAG", new Object[] {population.getRoutes().get(0)});
			
			// On lance les agents ainsi que le container principal : 
			AgentTabou.start(); 
			AgentAG.start();
			AgentRS.start();
			container.start();	
			
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} 
	}
}
