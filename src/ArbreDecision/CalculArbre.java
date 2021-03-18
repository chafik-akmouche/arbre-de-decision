package ArbreDecision;

import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

public class CalculArbre {
	private int attribut = -1;      // -1 si les fils ont des valeurs differentes
	private int valeur = -1;		// valeur numérique de l'attribut pere, qui enendre ce noeud
	private CalculArbre pere = null;
	private CalculArbre []fils = null;
	private String nomAttributs[];
	private int nbAttributs = 0;			
	private Vector valeurAttributs[];		
	private Vector Exemples = new Vector();  		
	private int etiquette = -1;
	
	GainAttribut gainAttribut;
	
	// Constructeur
	public CalculArbre(Vector[] attributs2,Vector exemples, int attributs, String[] nomAttributs) {
		super();
		Exemples = exemples;
		nbAttributs = attributs;
		valeurAttributs = attributs2;
		this.nomAttributs = nomAttributs;
		gainAttribut = new GainAttribut();
	}
	
	public DefaultMutableTreeNode generationAffichage()	{
		DefaultMutableTreeNode noeud = null;
		if(pere != null && etiquette!=-1) { // feuille
			noeud = new DefaultMutableTreeNode(valeurAttributs[pere.attribut].elementAt(valeur)+" : "+
												nomAttributs[nbAttributs-1]+" = "+
												valeurAttributs[attribut].elementAt(etiquette));
			return noeud;
		}
		
		if(pere!=null && etiquette==-1) { // noeud central
			noeud = new DefaultMutableTreeNode(valeurAttributs[pere.attribut].elementAt(valeur)+" : "+
					nomAttributs[attribut]);
			for(int i = 0;i<fils.length;i++){
				if(fils[i] != null)
					noeud.add(fils[i].generationAffichage());
			}
			return noeud;
		}
		
		if(pere==null && etiquette == -1) { //racine
			noeud = new DefaultMutableTreeNode(nomAttributs[attribut]);
			for(int i = 0;i<fils.length;i++){
				if(fils[i]!=null)
					noeud.add(fils[i].generationAffichage());
			}
			return noeud;
		}
		return noeud;
	}
	
	public void complementNoeud(int valeur, CalculArbre pere) {
		this.valeur = valeur;
		this.pere = pere;
	}

	public void generationArbre() {
		Vector sousExemple;
		attribut = attributGagnant();
		if(attribut!=-1) {
			fils = new CalculArbre[valeurAttributs[attribut].size()];
			for(int i = 0;i<valeurAttributs[attribut].size();i++) {
				sousExemple = sousExemple(attribut,i);
				if(sousExemple.size()>0) {					
					
					fils[i]= new CalculArbre(valeurAttributs,sousExemple,nbAttributs,nomAttributs);
					fils[i].complementNoeud(i,this);
					fils[i].generationArbre();
				}
			}
			
			 // Former les fils et regarder s'ils ont la même etiquette
			 // Si oui alors ce noeud peut devenir une feuille			
			int previous = -1;
			for(int i=0;i<fils.length;i++) {
				// On recherche la premiere etiquette valide
				if(fils[i]!=null && previous==-1)
					previous = fils[i].etiquette;
				// On verifie si les noeuds inferieurs ont la même etiquette
				if(previous!=-1 && fils[i]!=null && fils[i].etiquette!=previous)
					return;
			}
			if(previous==-1)
				return;
			// Les noeuds inferieurs ne servent à rien, donc on les virre
			attribut = nbAttributs-1;
			etiquette = previous;
			fils = null;     
		} else {	// feuille
			attribut = nbAttributs-1;
			etiquette = ((int[])Exemples.elementAt(0))[attribut];
		}
	}
	
	// Donne un sous-ensemble d'exemples pour un attribut et une valeur de cet attribut
	public Vector sousExemple(int pattribut, int pvaleur) {
		Vector sousExemples = new Vector();
		int num = Exemples.size();
		for (int i=0; i< num; i++) {
			int [] exemple = (int[])Exemples.elementAt(i);
			if (exemple[pattribut] == pvaleur) sousExemples.addElement(exemple);
		}
		return sousExemples;
	}
	
	// Determine quel attribut doit figurer dans ce noeud
	public int attributGagnant() {
		double gain[] = new double[nbAttributs-1];
		int attribut = -1;
		double max = -1;
		Vector attributeValues = new Vector();
		for(int i=0;i<nbAttributs-1;i++) {
			attributeValues.removeAllElements();
			for(int j=0;j<Exemples.size();j++) {
				attributeValues.addElement(new Integer(((int[])Exemples.elementAt(j))[i]));
				//System.out.println("**** "+attributeValues.get(j));
			}
			gain[i] = gainAttribut.gainAttribut(attributeValues);
			//System.out.println("Gain["+i+"] = "+gain[i]);
			
		}
		
		for(int i=0;i<gain.length;i++) {
				if (gain[i]>max) {
					max = gain[i];
					//Test taux d'impureté
					//if (max > 0.7) {
						attribut = i;
						//System.out.println("i = "+i+" max = "+max);
					//}
				}
		}
		if (max == 0) return -1;
		else return attribut;
		}
	
}