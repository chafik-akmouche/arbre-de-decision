package ArbreDecision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

public class ArbreDeDecision {
	private JFrame fenetre;
	private JPanel panel;
	private JPanel panel_arbre, panel_composant;
	private JScrollPane scroll_pane;
	private JButton btn_parcourir, btn_generer_arbre;
	private JTextField profondeur, impurete;
	private JLabel label_chemin_fich;
	private JFileChooser file_chooser;
	private String fichier;
	
	private Vector exemples = null; // contient les exemples sous forme de chiffres
	private int nbr_exemples = 0; // nbr exemples dans le fichier lu
	private int nbr_attributs = 0; // nbr d'attributs dans le fichier lu
	private String nom_attributs[] = null; //Pour stocker les noms des attributs
	private Vector valeur_attributs[];	// tableau contenant les valeurs possibles pour chaque attribut
	private CalculArbre root = null; // noeud racine
	private JTree arbre = null;
	
	private int nbr_d_attributs = 0;
	private int nbr_d_exemples = 0;
	private int nbr_commentaires = 0;
	
	//Constructeur
	public ArbreDeDecision() {		
		init();
	}
	
	public void init() {
		fenetre = new JFrame();
		fenetre.setTitle("Arbre de Décision");
		fenetre.setLocationRelativeTo(null);
		fenetre.setSize(new Dimension(650,500));
		fenetre.setResizable(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel_arbre = new JPanel();
		panel_arbre.setBackground(Color.WHITE);
		panel_composant = new JPanel();
		scroll_pane = new JScrollPane(panel_arbre);
		scroll_pane.setBackground(Color.WHITE);
		panel.setLayout(new BorderLayout());
		panel_composant.setLayout(new GridLayout(1,4,1,1));
		
		
		btn_parcourir = new JButton("Parcourir");
		btn_generer_arbre = new JButton("Générer l'arbre");
		//Le bouton «générer arbre» est désactivé avant qu'un fichier soit chargé
		btn_generer_arbre.setEnabled(false);
		
		profondeur = new JTextField("Profondeur");
		profondeur.setBounds(20,40,200,28);
		profondeur.setEnabled(false);
	    
	    impurete = new JTextField("Taux d'impureté (%)");
	    impurete.setBounds(20,80,200,28);
	    impurete.setEnabled(false);
	    
	    
	    label_chemin_fich = new JLabel();
	    label_chemin_fich.setBackground(Color.white);
		
		panel_composant.add(btn_parcourir);
		panel_composant.add(profondeur);
		panel_composant.add(impurete);
		panel_composant.add(btn_generer_arbre);
		panel.add(panel_composant, BorderLayout.NORTH);
		panel.add(scroll_pane, BorderLayout.CENTER);
		panel.add(label_chemin_fich, BorderLayout.SOUTH);
		
		fenetre.setContentPane(panel);
		fenetre.setVisible(true);
		
		btn_parcourir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				file_chooser = new JFileChooser();
				file_chooser.setCurrentDirectory(new File("fichiers/"));
				file_chooser.setAcceptAllFileFilterUsed(false);
				// N'accepte que les fichiers arff
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers arff.", "arff");
				file_chooser.addChoosableFileFilter(filter);
				int retour = file_chooser.showOpenDialog(null); 
				
				if(retour == JFileChooser.APPROVE_OPTION) {
					fichier = file_chooser.getSelectedFile().getAbsolutePath();
					label_chemin_fich.setText(fichier);
					verifierFichier(fichier);
				}else {
					System.out.println("Aucun fichier n'a été selectionné !");
				}
			}

			public void verifierFichier(String fichier) {
				if(fichier.equals("") || fichier == null) {
					return;
				}	
				//Activer les composants désactivés
				profondeur.setEnabled(true);
				impurete.setEnabled(true);
				//Activer le bouton « générer arbre »
				btn_generer_arbre.setEnabled(true);
				
			}	
			
	    });
		
		btn_generer_arbre.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String profondeurArbre = profondeur.getText();
				String tauxImpurtee = impurete.getText();
				//Integer.parseInt(tauxImpurtee)
				
				//System.out.println("Profondeur de l'arbre : "+profondeurArbre);
				//System.out.println("Taux d'impurté : "+tauxImpurtee+"%");
				
				 try {
					//extraireDonnees(fichier);
					 lireArff (fichier);
					//creerArbre();
					//afficherArbre();
				} catch (IOException e) {
					e.printStackTrace();
				}
				profondeur.setEnabled(false);
				impurete.setEnabled(false);
				btn_generer_arbre.setEnabled(false);
			}
		});		
	}	
	
	public void lireArff (String fichier) throws IOException {
		
		FileInputStream in = new FileInputStream(new File(fichier));
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(in));		
		String input="";
		String nom_fichier = "";
		String extension = ".txt";
	    String encoding = "UTF-8";
		
	    System.out.println("##### AFFICHAGE DES DONNÉES SOUS FORME DE TABLEAU #####\n");
	    
	    while ((input = buffReader.readLine()) != null) {
	    	if (input.startsWith("@relation")) {
				String [] nom_relation = input.split(" ");
				nom_fichier=nom_relation[1];
				System.out.println("NOM DE LA RELATION : "+nom_relation[1]+".arff\n");				
			}
	    	input = "";
	    }	    
	    buffReader.close();
	    
	    PrintWriter writer = new PrintWriter(nom_fichier+extension, encoding);
	    
	    FileInputStream in2 = new FileInputStream(new File(fichier));
		BufferedReader buffReader2 = new BufferedReader(new InputStreamReader(in2));		
		String input2="";
	    while ((input2 = buffReader2.readLine()) != null) {
	    	if (input2.startsWith("@attribute")) {
				String [] sepa = input2.split(" ");
				writer.print(sepa[1]+"\t");
				System.out.print(sepa[1]+"\t");
	    	}
	    	input2 = "";
	    }
	    buffReader2.close();
	    
	    FileInputStream in3 = new FileInputStream(new File(fichier));
		BufferedReader buffReader3 = new BufferedReader(new InputStreamReader(in3));		
		String input3="";
		while ((input3 = buffReader3.readLine()) != null) {
			if (input3.startsWith("@relation")) {
				String [] nom_relation = input3.split(" ");
				
			} else if (input3.startsWith("@attribute")) {
				String [] sepa = input3.split(" ");
				nbr_d_attributs++;
				
			} else if (input3.startsWith("%") || input3.isEmpty()) {
				nbr_commentaires++;
				
			} else {
				if (!input3.startsWith("@data")) {
					int [] exemple = null;
					String [] separateur = input3.split(",");
					String[] tokens = input3.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
					writer.print("\n");
					System.out.print("\n");
					for (int k =0; k < tokens.length; k++) {
						System.out.print(tokens[k]+"\t\t");
						writer.print(tokens[k]+"\t\t");
						if (k+1 == tokens.length) {
							//writer.print("\n");
						}
					}
					nbr_d_exemples++;
				}
				
			}
			input3 = "";
		}
		buffReader3.close();		
		writer.close();
		
		System.out.println("\n\nNOMBRE D'ATTRIBUTS : "+nbr_d_attributs);
		System.out.println("NOMBRE D'ÉXEMPLES : "+nbr_d_exemples);
		
		extraireDonnees(nom_fichier+extension);
		creerArbre();
		afficherArbre();		
	}	
	
	public void extraireDonnees(String nom_fichier) throws IOException {
		exemples = new Vector();
		nbr_exemples = 0;
		//Ouverture du fichier contenant les exemples
		FileInputStream in = null;
  		try {
  			File inputFile = new File(nom_fichier);
  			in = new FileInputStream(inputFile);
		} catch (Exception e){
			return;
		}
  		
  		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String input;
		input = br.readLine();
  		
  		StringTokenizer tokenizer = new StringTokenizer(input);
  		//Nombre d'attributs
		nbr_attributs = tokenizer.countTokens();
		
		nom_attributs = new String[nbr_attributs];		
		for(int i=0; i<nbr_attributs; i++) {
			//Noms des attributs
			nom_attributs[i] = tokenizer.nextToken();
		}
		
		//Valeurs possibles pour chaque attribut
		//Ex : Prévision => soleil, couvert, pluie
		valeur_attributs = new Vector[nbr_attributs];
		for (int i=0; i<nbr_attributs; i++) {
			valeur_attributs[i] = new Vector();
		}
		
		//Récupération des données
		int [] exemple;
		while(true)	{
			exemple = new int[nbr_attributs];
			String value;
			input = br.readLine();		//Nouvel exemple
			if(input == null) break;	//Si input = null fin des exemples
			nbr_exemples++;
			tokenizer = new StringTokenizer(input);
			int nbToken = tokenizer.countTokens();
			for (int attribut = 0; attribut<nbToken; attribut++){
				value = tokenizer.nextToken();
				exemple[attribut] = valeur_attribut(attribut,value);
			}
			exemples.add(exemple);
		}
		br.close();		
	}
	
	public int valeur_attribut(int attribut,String value){
		int valeur = valeur_attributs[attribut].indexOf(value);
		if (valeur < 0){
			valeur_attributs[attribut].addElement(value);
			valeur =  valeur_attributs[attribut].size() -1;
		}
		return valeur;
	}
	
	public void creerArbre() {
		root = new CalculArbre(valeur_attributs,exemples,nbr_attributs,nom_attributs);
		root.generationArbre();
	}

	public void afficherArbre() {
		if(arbre != null)
			panel_arbre.remove(arbre);
		DefaultMutableTreeNode racine = root.generationAffichage();
		arbre = new JTree(racine);
		panel_arbre.add(arbre, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.revalidate();
		panel.repaint();
	}
	
}