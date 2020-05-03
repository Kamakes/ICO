package Algorithmes;
import java.util.ArrayList;
import java.util.Random;

public class SolutionTabou {

	private final int tailleTabou;
	private Route [] tabou;
	private Route bestPath; //meilleur chemin rencontré
	private Route currentPath; //chemin courant
	private final int nbIterations; //critère d'arrêt dans la recherche
	private double time_taken ; //sert pour la mesure de la durée de la recherche


		
	//Constructeur
	
	public SolutionTabou (ArrayList<City> cities, int tailleTabou, int nbIterations) {
		this.tailleTabou = tailleTabou;
		this.tabou = new Route [this.tailleTabou]; //le tabou est initialement constitué d'éléments "null"
		this.currentPath = new Route (cities); //On initialise le chemin courant aléatoirement
		this.bestPath = new Route (currentPath); //même ordre des villes que currentPath
		this.nbIterations = nbIterations;
	}
	
	
	
	//Getter
	
	public Route getBestPath() {
		return this.bestPath;
	}
	public double time_taken() {return this.time_taken;}


	
	
	//Procédure publique
	
	public void optimiserTabou () { 
		long startTime = System.nanoTime(); //début de mesure de durée
		for (int i=0 ; i<this.nbIterations ; i++) { 
			this.actualiserTabou(); //ajout du currentPath au début du tabou, l'élément le plus ancien disparait
			this.actualiserChemin(); //on actualise le currentPath, et potentiellement le bestPath
		}
		time_taken = System.nanoTime() - startTime;
	}
	
	
	
	//Méthodes privées
	
	private void actualiserTabou () {
		for (int i=this.tailleTabou-1 ; i>0 ; i--) { 
				this.tabou[i]=this.tabou[i-1]; 
		}
		this.tabou[0]=this.currentPath; //la solution courante est ajoutée au tabou
	}
	
	
	private Route ajouterVoisin (int i, int k) {
		Route voisin = new Route(this.currentPath); //futur voisin
		int n = this.currentPath.getCities().size();
		if (i+k < n) {                //si il existe une ville i+k dans la route
			City memoire = voisin.getCities().get(i+k);             //on échange
			voisin.getCities().set(i+k, voisin.getCities().get(i)); //les villes
			voisin.getCities().set(i, memoire);                     //i et i+k
		}
		else {                                                        //sinon
			City memoire = voisin.getCities().get(i+k-n);             //on échange
			voisin.getCities().set(i+k-n, voisin.getCities().get(i)); //les villes
			voisin.getCities().set(i, memoire);                       //i et i+k-n
		}
		return voisin;
	}
		
	
	//vérifie si la route test appartient à la liste tabou de l'objet
	private boolean existe (Route test) {
		for (int i=0 ; i<this.tailleTabou ; i++) {
			if (this.tabou[i]!=null && test.toString().equals(this.tabou[i].toString())) {
				return true;
			}
		}
		return false;
	}
	
	
	private ArrayList<Route> creerVoisins () {
		ArrayList<Route> voisins = new ArrayList<Route>(); //futur voisinage du currentPath
		int n = this.currentPath.getCities().size();
		Random rand = new Random();    //entier k aléatoire compris entre 1 et n-1 afin de permuter une ville avec
		int k = rand.nextInt(n-1) + 1; //celle située k rangs plus loins dans la route lors de la créaton de voisins
		for (int i=0 ; i<n ; i++) {
			Route newVoisin = ajouterVoisin(i, k);
			if (!this.existe(newVoisin)) { //un voisin potentiel est effectivement ajouté à
				voisins.add(newVoisin);    // la liste des voisins si il n'est pas tabou
			}
		}
		return voisins;
	}
	
		
	private void actualiserChemin () {
		ArrayList<Route> voisins = this.creerVoisins(); //voisins du currentPath
		double newCurrentDistance = voisins.get(0).getTotalDistance();
		for (Route test : voisins) {
			double distanceTest = test.getTotalDistance();
			if (distanceTest < this.bestPath.getTotalDistance()) { 								
				this.bestPath = test;                              
			}
			if (distanceTest <= newCurrentDistance) { //Si un voisin est meilleur que les autres voisins 
				newCurrentDistance = distanceTest;    //déjà testés au cours de cette itération, on update 
				this.currentPath = test;              //le currentPath en vue de l'itération suivante
			}
		}
	}

}


