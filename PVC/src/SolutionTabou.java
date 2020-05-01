import java.util.ArrayList;
import java.util.Random;

public class SolutionTabou {

	private final int tailleTabou;
	private Route [] tabou;
	private Route bestPath;
	private Route currentPath;
	private final int nbIterations;
	
	
	//Constructeur
	
	public SolutionTabou (ArrayList<City> cities, int tailleTabou, int nbIterations) {
		this.tailleTabou = tailleTabou;
		this.tabou = new Route [this.tailleTabou]; //le tabou est initialement uniquement constitué d'éléments "null"
		this.currentPath = new Route (cities); //currentPath est une route créée à partir du mélange aléatoire des villes de la liste cities
		this.bestPath = new Route (currentPath); //bestPath est une copie de la route currentPath (même ordre des villes)
		this.nbIterations = nbIterations;
	}
	
	
	
	//Getter
	
	public Route getBestPath() {
		return this.bestPath;
	}

	
	
	//Procédure publique
	
	public void optimiserTabou () { 
		for (int i=0 ; i<this.nbIterations ; i++) { //on effectue la recherche sur un nombre d'itérations défini
			//nbIterations sert de condition d'arrêt pour l'algorithme de recherche
			this.actualiserTabou(); //ajout du currentPath au début du tabou, l'élément le plus ancien disparait
			this.actualiserChemin(); //on actualise le currentPath, et potentiellement le bestPath
		}
	}
	
	
	
	//Méthodes privées
	
	private void actualiserTabou () {
		for (int i=this.tailleTabou-1 ; i>0 ; i--) { //chaque élément est décalé 
				this.tabou[i]=this.tabou[i-1];       //vers le bout de la liste
		}
		this.tabou[0]=this.currentPath; //la solution courante est ajoutée au tabou
	}
	
	
	private Route ajouterVoisin (int i, int k) {
		Route voisin = new Route(this.currentPath); //copie de currentPath destinée à devenir un voisin
		int n = this.currentPath.getCities().size();
		if (i+k < n) { //si il existe une ville i+k dans la route
			City memoire = voisin.getCities().get(i+k);            //on échange
			voisin.getCities().set(i+k, voisin.getCities().get(i)); //les villes
			voisin.getCities().set(i, memoire);                    //i et i+k
		}
		else { //si i+k est plus grand que la longueur de la liste (la ville i+k n'existe pas)
			City memoire = voisin.getCities().get(i+k-n);            //on échange
			voisin.getCities().set(i+k-n, voisin.getCities().get(i)); //les villes
			voisin.getCities().set(i, memoire);                      //i et i+k-n
		}
		return voisin;
	}
		
	
	//on vérifie si la route test appartient à la liste tabou de l'objet
	private boolean existe (Route test) {
		for (int i=0 ; i<this.tailleTabou ; i++) {
			if (this.tabou[i]!=null && test.toString().equals(this.tabou[i].toString())) {
				return true;
			}
		}
		return false;
	}
	
	
	private ArrayList<Route> creerVoisins () {
		ArrayList<Route> voisins = new ArrayList<Route>(); //ArrayList de route destiné à acceuillir les voisins du currentPath
		int n = this.currentPath.getCities().size();
		Random rand = new Random();    //on crée un entier k aléatoire compris entre 1 et n-1 afin de permuter une ville 
		int k = rand.nextInt(n-1) + 1; //avec celle située k rangs plus loins dans la route lors de la créaton de voisins
		for (int i=0 ; i<n ; i++) {  //on crée autant de voisins potentiels que le nombre de villes que contient la route
			Route newVoisin = ajouterVoisin(i, k);
			if (!this.existe(newVoisin)) { //un voisin potentiel est effectivement ajouté 
				voisins.add(newVoisin);    //à la liste des voisins si il n'est pas tabou
			}
		}
		return voisins;
	}
	
		
	private void actualiserChemin () {
		ArrayList<Route> voisins = this.creerVoisins(); //on crée les voisins du currentPath
		double newCurrentDistance = voisins.get(0).getTotalDistance();
		for (Route test : voisins) { //on itère sur tous le voisins
			double distanceTest = test.getTotalDistance();
			if (distanceTest < this.bestPath.getTotalDistance()) { //si un voisin est le meilleur chemin testé depuis
				this.bestPath = test;                              //le début de la recherche, on update le bestPath
			}
			if (distanceTest <= newCurrentDistance) { //Si un voisin est meilleurs que les autres voisins 
				newCurrentDistance = distanceTest;    //déjà testés au cours de cette itération, on update 
				this.currentPath = test;              //le currentPath en vue de l'itération suivante
			}
		}
	}

}


