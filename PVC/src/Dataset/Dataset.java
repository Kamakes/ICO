package Dataset;
/*Pour exécuter ce programme et celui de la classe table il faut télécharger les fichier jar dans le lien suivant :
 * https://downloads.apache.org/poi/release/bin/poi-bin-4.1.2-20200217.zip
 * et ajouter tous les fichiers jar
 * Ce programme a été modifié pour générer les différents tableaux donnés dans le rapport
 */

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Algorithmes.City;
import Algorithmes.Population;
import Algorithmes.Route;
import Algorithmes.SolutionAG;
import Algorithmes.SolutionRS;
import Algorithmes.SolutionTabou;

public class Dataset{
	//le tableau, ici table2, est caractérisé par six colonnes 
	private static String[] columns= {"Méthode","Configuration1","Configuration2","Durée","Ecart","Critère"};
	private static List<table2> tables =new ArrayList<table2>();
	public static ArrayList<City> creationroute() {

		//Declaration villes
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
	public static void main(String[] args) throws IOException{
		ArrayList<City>cities1 = creationroute();//On crée une route initiale de taille 74
		System.out.println(cities1.size());
		ArrayList<City> cities = new ArrayList<City>();//on crée une liste de ville qu'on va remplir à chaque itération
		for ( int k = 0;k<25;k++) {//On commence par un pvc de taille 25
			cities.add(cities1.get(k));
		}
		long start,stop;
		
		int i =25;
		while (i<cities1.size()) {
			Route Route = new Route(cities);
			SolutionRS solution = new SolutionRS(60, 0.996);//D'après les tests cette configuration donne une solution qu'on considère ici idéale, et avec quoi on va calculer l'écart
			Route sf = solution.RS(Route); 
			double T = sf.getTotalDistance();
			int index =0;
		while (index<14) {
			
			//Cette partie faire varier dans la solution RS La température et cooling rate
			Route Route1 = new Route(cities);
			SolutionRS.coolingRate=0.990;
			SolutionRS.T_init=50;
			while (SolutionRS.coolingRate<0.999 && SolutionRS.T_init < 150) {
				start = System.nanoTime();
				SolutionRS solution1 = new SolutionRS(SolutionRS.T_init, SolutionRS.coolingRate);
				Route s_f = solution1.RS(Route1); 
				stop = System.nanoTime();
				double RS = s_f.getTotalDistance();
				double a = (stop-start)/1000000.;
				//Cette ligne ajoute une ligne au tableau
				tables.add(index, new table2("RS",SolutionRS.T_init,SolutionRS.coolingRate,a,(RS - T)/RS,RS));

			SolutionRS.coolingRate+=0.001;
			SolutionRS.T_init+=10;
			index++;
			}
			
			//cette partie fait varier les configuartion de la solution Tabou: nombre d'itérations et la taille de la liste tabou
			SolutionTabou.tailleTabou=3;
			SolutionTabou.nbIterations=100;
			while (SolutionTabou.tailleTabou<10 & SolutionTabou.nbIterations<500) {
				
				start = System.nanoTime();
				SolutionTabou solutionT = new SolutionTabou(cities,SolutionTabou.tailleTabou, SolutionTabou.nbIterations);
				solutionT.optimiserTabou();
				stop = System.nanoTime();
				double b = (stop-start)/1000000.;
				double T2 = solutionT.getBestPath().getTotalDistance();
			tables.add(index, new table2("Tabou",SolutionTabou.tailleTabou,SolutionTabou.nbIterations,b,(T2 - T)/T2,T2));
			SolutionTabou.tailleTabou++;
			SolutionTabou.nbIterations+=50;
			index++;
			}
			
			
			//Cette partie fait varier le nombre de génération et la taille de la population
			SolutionAG.NUM_GENERATIONS=1200;
			SolutionAG.POPULATION_Size=3;
			while (SolutionAG.NUM_GENERATIONS<2999 & SolutionAG.POPULATION_Size<10) {
					
					start=System.nanoTime();
					Population population = new Population(SolutionAG.POPULATION_Size, new Route(cities));
					population.sortRouteByFitness();
					SolutionAG ag = new SolutionAG(cities);
					int generationNumber = 1;
						while (generationNumber < SolutionAG.NUM_GENERATIONS) {
							generationNumber++;
							population = ag.evolve(population);
							population.sortRouteByFitness();
						}
					double AG = population.getRoutes().get(0).getTotalDistance();
					stop=System.nanoTime();
					double c = (stop-start)/1000000.;
					tables.add(index, new table2("AG",(double) SolutionAG.NUM_GENERATIONS,(double)SolutionAG.POPULATION_Size,c,(AG - T)/AG,AG));
				SolutionAG.NUM_GENERATIONS+=200;
				SolutionAG.POPULATION_Size++;
				index++;
			}
		}
		for ( int k = i;k<Math.min(74,i+10);k++) {
			cities.add(cities1.get(k));
		}
		i+=10;
		}
		    System.out.println("ligne créée");

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("dataset3");
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		Row headerRow = sheet.createRow(0);

		for (int q = 0; q < columns.length; q++) {
		  Cell cell = headerRow.createCell(q);
		  cell.setCellValue(columns[q]);
		  cell.setCellStyle(headerCellStyle);
		}
		   int rowNum = 1;
//création des colonnes
		    for (table2 table2 : tables) {
		      Row row = sheet.createRow(rowNum++);
		      row.createCell(0).setCellValue(table2.Méthode);
		      row.createCell(1).setCellValue(table2.Configuration1);
		      row.createCell(2).setCellValue(table2.Configuration2);
		      row.createCell(3).setCellValue(table2.Durée);
		      row.createCell(4).setCellValue(table2.Distance);
		      row.createCell(5).setCellValue(table2.Critère);
		    }

		    for (int p = 0; p < columns.length; p++) {
		      sheet.autoSizeColumn(p);
		    }

		    //Ecrire dans un fichier
		    FileOutputStream fileOut = new FileOutputStream("dataset3.xlsx");
		    workbook.write(fileOut);
		    System.out.println("table créée");
		    fileOut.close();
		    workbook.close();
}
	
}
