package Algorithmes;
import java.util.ArrayList;
import java.util.Random;

public class SolutionTabou {

	private final int tailleTabou;
	private Route [] tabou;
	private Route bestPath; //meilleur chemin rencontr�
	private Route currentPath; //chemin courant
	private final int nbIterations; //crit�re d'arr�t dans la recherche
	private double time_taken ; //sert pour la mesure de la dur�e de la recherche


		
	//Constructeur
	
	public SolutionTabou (ArrayList<City> cities, int tailleTabou, int nbIterations) {
		this.tailleTabou = tailleTabou;
		this.tabou = new Route [this.tailleTabou]; //le tabou est initialement constitu� d'�l�ments "null"
		this.currentPath = new Route (cities); //On initialise le chemin courant al�atoirement
		this.bestPath = new Route (currentPath); //m�me ordre des villes que currentPath
		this.nbIterations = nbIterations;
	}
	
	
	
	//Getter
	
	public Route getBestPath() {
		return this.bestPath;
	}
	public double time_taken() {return this.time_taken;}


	
	
	//Proc�dure publique
	
	public void optimiserTabou () { 
		long startTime = System.nanoTime(); //d�but de mesure de dur�e
		for (int i=0 ; i<this.nbIterations ; i++) { 
			this.actualiserTabou(); //ajout du currentPath au d�but du tabou, l'�l�ment le plus ancien disparait
			this.actualiserChemin(); //on actualise le currentPath, et potentiellement le bestPath
		}
		time_taken = System.nanoTime() - startTime;
	}
	
	
	
	//M�thodes priv�es
	
	private void actualiserTabou () {
		for (int i=this.tailleTabou-1 ; i>0 ; i--) { 
				this.tabou[i]=this.tabou[i-1]; 
		}
		this.tabou[0]=this.currentPath; //la solution courante est ajout�e au tabou
	}
	
	
	private Route ajouterVoisin (int i, int k) {
		Route voisin = new Route(this.currentPath); //futur voisin
		int n = this.currentPath.getCities().size();
		if (i+k < n) {                //si il existe une ville i+k dans la route
			City memoire = voisin.getCities().get(i+k);             //on �change
			voisin.getCities().set(i+k, voisin.getCities().get(i)); //les villes
			voisin.getCities().set(i, memoire);                     //i et i+k
		}
		else {                                                        //sinon
			City memoire = voisin.getCities().get(i+k-n);             //on �change
			voisin.getCities().set(i+k-n, voisin.getCities().get(i)); //les villes
			voisin.getCities().set(i, memoire);                       //i et i+k-n
		}
		return voisin;
	}
		
	
	//v�rifie si la route test appartient � la liste tabou de l'objet
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
		Random rand = new Random();    //entier k al�atoire compris entre 1 et n-1 afin de permuter une ville avec
		int k = rand.nextInt(n-1) + 1; //celle situ�e k rangs plus loins dans la route lors de la cr�aton de voisins
		for (int i=0 ; i<n ; i++) {
			Route newVoisin = ajouterVoisin(i, k);
			if (!this.existe(newVoisin)) { //un voisin potentiel est effectivement ajout� �
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
				newCurrentDistance = distanceTest;    //d�j� test�s au cours de cette it�ration, on update 
				this.currentPath = test;              //le currentPath en vue de l'it�ration suivante
			}
		}
	}

}


