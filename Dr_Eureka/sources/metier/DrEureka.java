package sources.metier;

/*----L'-import-pour-le-metier----*/
import sources.metier.PileCaractere;

/*----Les-imports-pour-la-serialisation----*/
import java.io.Serializable;
import sources.serialisation.Jeu;
import sources.serialisation.DisquetteJeu;

/*----Les-imports-pour-lecture-de-fichier----*/
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import java.util.Random;


/**
	* Classe DrEureka
	* @author 	: -
	* @version 	: 1.0
	* date 		! 28/07/2020
*/

public class DrEureka implements Serializable
{
	/*---------------*/
	/* Les Attributs */
	/*---------------*/
	
	/**
		* Définition du serialVersionUID
	*/
	private static final long serialVersionUID = 1L;
	
	/**
		* Les éléments du jeu
	*/
	private static final 	int 				NB_NIVEAU 				= 5; 	// L'attribut renseigne sur le nombre de niveau.
	private static final 	int 				NB_TUBE 				= 3; 	// L'attribut renseigne sur le nombre de tube.
	private static final 	int 				NB_BILLE_MAXIMUM_TUBE 	= 4; 	// L'attribut renseigne sur le nombre de bille maximum dans un tube.
	private static final 	int 				NB_BILLE_TOTAL 			= 6; 	// L'attribut renseigne sur le nombre de bille au total.
	private 				PileCaractere[][] 	ensObjectif; 					// L'attribut renseigne sur les niveaux et les tubes de l'objectif.	
	private 				PileCaractere[][] 	ensChimiste; 					// L'attribut renseigne sur les niveaux et les tubes du chimiste.
	private 				boolean[][]			bRetourner; 					// L'attribut renseigne sur les tubes à l'endroit.
	private 				int 				niveau; 						// L'attribut renseigne sur le niveau.
	private 				String 				pseudoJoueur;					// L'attribut renseigne sur le pseudo du joueur.
	private String 			messageNonJouable; 									// L'attribut renseigne sur le message lorsque le joueur ne peut pas jouer.
	private boolean 		couleurAffichage; 									// L'attribut renseigne sur la couleur d'affichage.


	/*-------------------*/
	/* Les Constructeurs */
	/*-------------------*/

	/**
		* Constructeur DrEureka
		* À l'instanciation d'un objet DrEureka(), une partie de jeu est initialisée de manière non graphique.
	*/
	public DrEureka()
	{
		/*------------------------*/
		/* Les conditions de jeux */
		/*------------------------*/
		this.ensObjectif 		= new PileCaractere[NB_NIVEAU][NB_TUBE]; 	// L'objectif possède plusieurs niveaux et trois tubes.
		this.ensChimiste 		= new PileCaractere[NB_NIVEAU][NB_TUBE]; 	// Le chimiste possède plusieurs niveaux et trois tubes.
		this.bRetourner 		= new boolean[NB_NIVEAU][NB_TUBE]; 			// Au début du jeu, aucun tube n'est retourné.
		this.niveau 			= 0;										// Au début du jeu, le niveau est à 0 ( niveau_1.dat).
		this.pseudoJoueur 		= "";										// Le joueur n'a pas de pseudo lors de l'initialisation.
		this.messageNonJouable 	= "";										// Au début du jeu, aucun message non jouable.
		this.couleurAffichage 	= false;									// Au début du jeu, l'affichage est clair.

		/*---------------------*/
		/* Les initialisations */
		/*---------------------*/
		for(int cpt=0; cpt<NB_NIVEAU; cpt++)
		{
			this.ensObjectif[cpt] = DrEureka.initObjectif(cpt+1); 
			this.ensChimiste[cpt] = DrEureka.initChimiste();
		}

		// Au début du jeu, tous les tubes sont à l'endroit.
		for(int numNiveau=0; numNiveau<NB_NIVEAU; numNiveau++ )
		{
			for(int numTube=0; numTube<NB_TUBE; numTube++)
			{
				this.bRetourner[numNiveau][numTube] = true;
			}
		}
	}

	/**
		* Constructeur DrEureka(String nomJeu)
		* À l'instanciation d'un objet DrEureka(String nomJeu), une partie est alors chargé avec le nom de la partie renseignée.
		* @param nomJeu : le nom de la partie renseignée pour être chargé.
	*/
	public DrEureka(String nomJeu)
	{
		Jeu jeu = new DisquetteJeu().chargerJeu(nomJeu); 					// Chargement d'une partie.

		/*------------------------------------------------------*/
		/* Adaptation des attributs de DrEureka à partir de jeu */
		/*------------------------------------------------------*/
		this.ensObjectif 		= new PileCaractere[NB_NIVEAU][NB_TUBE]; 	// L'objectif possède plusieurs niveaux et trois tubes.
		this.ensChimiste 		= new PileCaractere[NB_NIVEAU][NB_TUBE]; 	// Le chimiste possède plusieurs niveaux et trois tubes.
		this.bRetourner 		= new boolean[NB_NIVEAU][NB_TUBE]; 			// Au début du jeu, aucun tube n'est retourné.
		this.niveau 			= jeu.getJeu().getNiveau();					// Récupère le niveau.
		this.pseudoJoueur 		= jeu.getJeu().getPseudoJoueur(); 			// Récupère le pseudo du joueur.
		this.messageNonJouable 	= jeu.getJeu().getMessageNonJouable();		// Récupère le message non jouable.
		this.couleurAffichage 	= jeu.getJeu().getCouleurAffichage();		// Récupère la couleur d'affichage.

		for(int numNiveau=0; numNiveau<NB_NIVEAU; numNiveau++ )
		{
			for(int numTube=0; numTube<NB_TUBE; numTube++)
			{
				PileCaractere pTmpObjectif = PileCaractere.creerPileCaractere(NB_BILLE_MAXIMUM_TUBE);
				PileCaractere pTmpChimiste = PileCaractere.creerPileCaractere(NB_BILLE_MAXIMUM_TUBE);

				String sTubeObjectif = jeu.getJeu().getTube('O', numTube);  
				String sTubeChimiste = jeu.getJeu().getTube(' ', numTube);

				for(int cpt=0; cpt<NB_BILLE_MAXIMUM_TUBE; cpt++)
				{
					char caracTmpObjectif = sTubeObjectif.charAt(cpt); 
					char caracTmpChimiste = sTubeChimiste.charAt(cpt);

					pTmpObjectif.empiler(caracTmpObjectif);
					pTmpChimiste.empiler(caracTmpChimiste);
				}

				this.ensObjectif[numNiveau][numTube] 	= pTmpObjectif; 
				this.ensChimiste[numNiveau][numTube] 	= pTmpChimiste;
				this.bRetourner[numNiveau][numTube] 	= jeu.getJeu().getTubeRetourner(numNiveau, numTube);
			}
		}
	}

	/*------------------------------------------------------------------------------------------------------*/
	/* 											LES MÉTHODES 												*/
	/*------------------------------------------------------------------------------------------------------*/

	/*----------------------------------*/
	/*			LES ACCESSEURS			*/
	/*----------------------------------*/

	/**
		* @return Retourne le nombre de niveau.
	*/
	public static int getNbNiveau()
	{
		return DrEureka.NB_NIVEAU;
	}

	/**
		* @return Retourne le nombre de tube.
	*/
	public static int getNbTube()
	{
		return DrEureka.NB_TUBE;
	}

	/**
		* @return Retourne le nombre de bille maximum dans un tube.
	*/
	public static int getNbBilleMaxTube()
	{
		return DrEureka.NB_BILLE_MAXIMUM_TUBE;
	}

	/**
		* @return Retourne le nombre de bille au total.
	*/
	public static int getNbBilleTotal()
	{
		return DrEureka.NB_BILLE_TOTAL;
	}

	/**
		* @return Retourne la pile de caractère d'un tube.
		* @param type : La pile retournée, 'O' pour l'objectif ou par défaut pour le chimiste.
		* @param numTube : Le numéro du tube.
	*/
	public String getTube(char type, int numTube)
	{
		if (type == 'O')
		{
			return this.ensObjectif[this.niveau][numTube].toString();
		}

		return this.ensChimiste[this.niveau][numTube].toString();
	}

	/**
		* @return Retourne le niveau.
	*/
	public int getNiveau()
	{
		return this.niveau;
	}

	/**
		* @return Retourne le pseudo du joueur.
	*/
	public String getPseudoJoueur()
	{
		return this.pseudoJoueur;
	}

	/**
		* @return Retourne true : si le tube est retourné / false : si le tube est à l'endroit.
		* @param numNiveau 	: Le numéro du niveau.
		* @param numTube 	: Le numéro du tube.
	*/
	public boolean getTubeRetourner(int numNiveau, int numTube)
	{
		return this.bRetourner[numNiveau][numTube];
	}

	/**
		* @return Retourne un message indiquant l'action que le joueur ne peut pas réaliser.
	*/
	public String getMessageNonJouable()
	{
		return this.messageNonJouable;
	}

	/**
		* @return Retourne la couleur d'affichage.
	*/
	public boolean getCouleurAffichage()
	{
		return this.couleurAffichage;
	}

	/*--------------------------------------*/
	/*			LES MODIFICATEURS			*/
	/*--------------------------------------*/

	/**
		* Affecte le niveau suivant.
	*/
	public void niveauSuivant()
	{
		if ( this.niveau == NB_NIVEAU-1 || this.niveau > NB_NIVEAU-1 )
		{
			this.niveau = NB_NIVEAU-1;
		}
		else
		{
			if ( this.niveau >= 0 )
			{
				int niveauPrecedent = this.niveau;
				this.niveau++;
				this.recupererPileCaractere(niveauPrecedent);
			}
		}
	}

	/**
		* Recupère la pile de caractère du niveau précèdent puis l'affecte dans le niveau actuel pour le chimiste.
		* @param niveauPrecedent : Le niveau précèdent.
	*/
	private void recupererPileCaractere(int niveauPrecedent)
	{
		for(int cpt=0; cpt<NB_TUBE; cpt++)
		{
			PileCaractere pTmp = PileCaractere.creerPileCaractere(NB_BILLE_MAXIMUM_TUBE);

			pTmp = this.ensChimiste[niveauPrecedent][cpt];

			this.ensChimiste[this.niveau][cpt] = pTmp;
		}
	}

	/**
		* Affecte un pseudo au joueur.
		* @param pseudoJoueur : le pseudo du joueur.
	*/
	public void setPseudoJoueur(String pseudoJoueur)
	{
		if ( pseudoJoueur == null || pseudoJoueur == "" )
		{
			this.pseudoJoueur = "joueur1"; 
		}
		else
		{
			this.pseudoJoueur = pseudoJoueur;
		}
	}

	/**
		* Fixe le message non jouable.
	*/
	public void setMessageNonJouable()
	{
		this.messageNonJouable = "";
	}

	/**
		* Fixe la couleur d'affichage.
	*/
	public void setCouleurAffichage()
	{
		if ( this.couleurAffichage )
		{
			this.couleurAffichage = false;
		}
		else
		{
			this.couleurAffichage = true;
		}
	}
		
	/*----------------------*/
	/* 		LES ACTIONS		*/
	/*----------------------*/

	/**
		* Retourne un tube.
		* @param numTube : le numéro du tube.
	*/
	public void retournerTube(int numTube)
	{
		this.ensChimiste[this.niveau][numTube].retournerCaractere();

		if ( ! this.bRetourner[this.niveau][numTube] )
		{
			this.bRetourner[this.niveau][numTube] = true;
		}
		else
		{
			this.bRetourner[this.niveau][numTube] = false;
		}
	}

	/**
		* Echange deux tubes.
		* @param numTube1 : le tube numéro 1.
		* @param numTube2 : le tube numéro 2.
	*/
	public void echangerTube(int numTube1, int numTube2)
	{
		// Si le tube source est retourné et le tube destination est à l'endroit
			// Le tube source devient à l'endroit et le tube destination est retourné
		if ( (! this.bRetourner[this.niveau][numTube1] ) && ( this.bRetourner[this.niveau][numTube2] ) ) 
		{
			this.bRetourner[this.niveau][numTube1] = true;
			this.bRetourner[this.niveau][numTube2] = false;
		}
		else
		{
			if ( (! this.bRetourner[this.niveau][numTube2] ) && (this.bRetourner[this.niveau][numTube1]) ) 
			{
				this.bRetourner[this.niveau][numTube1] = false;
				this.bRetourner[this.niveau][numTube2] = true;
			}
		}

		PileCaractere pTmp;

		pTmp = this.ensChimiste[this.niveau][numTube1];
		this.ensChimiste[this.niveau][numTube1] = this.ensChimiste[this.niveau][numTube2];
		this.ensChimiste[this.niveau][numTube2] = pTmp;
	}

	/**
		* Déplace une bille dans un tube vers un autre tube.
		* @param numSource 	: le tube d'origine.
		* @param numDest 	: le tube de destination. 
	*/
	public void deplacerBille(int numSource, int numDest)
	{
		// Vérifie que le tube source n'est pas vide et que le tube destination n'est pas plein
		if ( (! this.ensChimiste[this.niveau][numSource].estVideCaractere() )  && (  ! this.ensChimiste[this.niveau][numDest].estPleineCaractere() ) )
		{
			// Vérifie que le tube source et le tube destination sont à l'endroit
			if ( this.getTubeRetourner(this.niveau, numSource) && this.getTubeRetourner(this.niveau, numDest) )
			{
				char caracDeplacerSource = '\u0000';
				int cptSource=0;

				while( this.ensChimiste[this.niveau][numSource].getCaractereSommet() == '.' )
				{
					caracDeplacerSource = this.ensChimiste[this.niveau][numSource].depiler();
					cptSource++;
				}
				caracDeplacerSource = this.ensChimiste[this.niveau][numSource].depiler();

				int cptDest=0;
				char caracDeplacerDest = '\u0000';

				while( this.ensChimiste[this.niveau][numDest].getCaractereSommet() == '.' )
				{
					caracDeplacerDest = this.ensChimiste[this.niveau][numDest].depiler();
					cptDest++;
				}

				// Empile le caractère qui est déplacé dans le tube de destination
				this.ensChimiste[this.niveau][numDest].empiler(caracDeplacerSource);

				// Les espaces vides sont remplacés par des '.'
				cptSource++;
				for(int cpt=0; cpt<cptSource; cpt++)
				{
					this.ensChimiste[this.niveau][numSource].empiler('.');
				}
				
				for(int cpt=0; cpt<cptDest; cpt++)
				{
					this.ensChimiste[this.niveau][numDest].empiler('.');
				}
			}
			else
			{
				int nbErreur = 0;
				int numTubeRetourne = -1;
				if ( ! this.getTubeRetourner(this.niveau, numSource) )
				{
					nbErreur++;
					numTubeRetourne = numSource;
				}
				if ( ! this.getTubeRetourner(this.niveau, numDest) )
				{
					nbErreur++;
					numTubeRetourne = numDest;
				}

				if ( nbErreur > 1 )
				{
					this.messageNonJouable = "les tubes " + (numSource+1) + " et " + (numDest+1) + " sont retournés";
				}
				else
				{
					this.messageNonJouable = "le tube " + (numTubeRetourne+1) + " est retourné";
				}
			}
		}
		else
		{	int nbErreur = 0;
			String messageVideCaractere 	= "";
			String messagePleineCaractere 	= "";
			if ( this.ensChimiste[this.niveau][numSource].estVideCaractere() )
			{
				messageVideCaractere = "le tube " + (numSource+1) + " (source) est vide";
				nbErreur++;

			}
			if ( this.ensChimiste[this.niveau][numDest].estPleineCaractere() )
			{
				messagePleineCaractere = "le tube " + (numDest+1) + " (destination) est pleins de caractères";
				nbErreur++;
			}

			if ( nbErreur > 1 )
			{
				this.messageNonJouable = messageVideCaractere + " et " + messagePleineCaractere;
			}
			else
			{
				this.messageNonJouable = messageVideCaractere + messagePleineCaractere;
			}
		}
	}

	/*--------------------------------------*/
	/*			LES INITIALISATIONS			*/
	/*--------------------------------------*/

	/**
		* @return Initialise les objectifs à réaliser et retourne un tableau de pile de caractères.
		* @param numOjectif : Le numéro de l'objectif.
	*/
	private static PileCaractere[] initObjectif(int numOjectif) 
	{
		PileCaractere[] tabPile = new PileCaractere[NB_TUBE];
		File fichier = new File("./ressources/data/niveau_" + numOjectif + ".dat");

		// Création des tubes (les piles de caractères)
		for(int cpt=0; cpt<tabPile.length; cpt++)
		{
			tabPile[cpt] = PileCaractere.creerPileCaractere(NB_BILLE_MAXIMUM_TUBE);
		}


		// Des objectifs aléatoires sont initialisées si le fichier n'existe pas.
		if ( !fichier.exists() )
		{
			tabPile = DrEureka.melangerObjectif();
		}
		else
		{
			try
			{
				FileReader fr = new FileReader(fichier);
				Scanner sc = new Scanner(fr);

				// Pour chaque ligne
				int cpt1=0;
				do
				{
					String ligne = sc.nextLine();
					String[] tabInfos = ligne.split("\t");

					for(int cpt=0; cpt<NB_TUBE; cpt++)
					{
						tabPile[cpt].empiler(tabInfos[cpt].charAt(0));
					}

				}while( sc.hasNext() );

				// Retourne la pile car la lecture du fichier se fait du haut vers le bas
				for(int cpt=0; cpt<NB_TUBE; cpt++)
				{
					tabPile[cpt].retourner();
				}

				fr.close();
				sc.close();
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}
		}

		return tabPile;
	}
	
	/**
		* @return Création d'objectifs aléatoires et retourne un tableau de pile de caractères.
	*/
	private static PileCaractere[] melangerObjectif()
	{
		PileCaractere[] tabPile = new PileCaractere[NB_TUBE];
		char[] couleurBille = { 'R', 'M', 'V'};
		
		// Création d'une pile de six billes
		PileCaractere ensBille = PileCaractere.creerPileCaractere(NB_BILLE_TOTAL);
		// Affectation des caractères RR, MM, VV dans la pile de six billes
		for(int cpt=0; cpt<couleurBille.length; cpt++)
		{
			ensBille.empiler(couleurBille[cpt]);
			ensBille.empiler(couleurBille[cpt]);
		}

		// Mélange la pile de six billes.
		ensBille.melanger();

		int nbBilleTotalMaximum 	= NB_BILLE_MAXIMUM_TUBE;
		int nbBilleAleatoireTube 	= 0; // Le nombre aléatoire de bille que peut prendre un tube
		int nbBilleRestant 			= ensBille.getNbCaracTotal(); // Les billes restantes qui au départ sont de six billes

		
		// Remplir les tubes aléatoirement
		for(int cpt=0; cpt<tabPile.length; cpt++)
		{
			if ( tabPile[cpt] == null )
			{
				tabPile[cpt] = PileCaractere.creerPileCaractere(NB_BILLE_MAXIMUM_TUBE);
			}
			
			nbBilleAleatoireTube = (int) (Math.random() * (nbBilleTotalMaximum) );

			// Les deux tubes ont des billes aléatoirement et le tube restant recupère les billes restantes
			if ( cpt != tabPile.length-1 ) 
			{
				if ( cpt%2 != 0 ) 
				{
					if ( tabPile[cpt-1].estVide() )
					{
						nbBilleAleatoireTube = (int) (Math.random() * (5-2) + 2);
					}
				}
			}
			else
			{
				nbBilleAleatoireTube = nbBilleRestant;
			}

			int cpt2=0;
			while( cpt2 < nbBilleAleatoireTube )
			{
				tabPile[cpt].empiler(ensBille.depiler());
				cpt2++;
			}

			nbBilleTotalMaximum -= nbBilleAleatoireTube;
			nbBilleRestant 		-= nbBilleAleatoireTube;

			if ( nbBilleTotalMaximum <= 0 )
			{
				nbBilleTotalMaximum = 0;
			}
			
			if ( nbBilleRestant <= 0 )
			{
				nbBilleRestant = 0;
			}
		}


		nbBilleRestant 	= ensBille.getNbCaracTotal();


		// Réequilibrage
		int nbTubeAleatoire = 0;
		while( ! ensBille.estVide() )
		{
			System.out.println("etape 2 réequilibrage ");
			nbTubeAleatoire = (int) (Math.random() * NB_TUBE);

			if ( ! tabPile[nbTubeAleatoire].estPleine() )
			{
				System.out.println("tube " + nbTubeAleatoire + " est pas plein ");
				tabPile[nbTubeAleatoire].remplacerVide(ensBille.depiler());
			}
		}

		// Si le tube n'est pas pleins de billes, alors il y a des caractères '.' qui sont ajoutés
		for(int cpt=0; cpt<tabPile.length; cpt++)
		{
			while( ! tabPile[cpt].estPleine() )
			{
				tabPile[cpt].empiler('.');
			}
		}
		
		return tabPile;
	}


	/**
		* @return Initialise les tubes du chimiste et retourne un tableau de pile de caractères.
	*/
	private static PileCaractere[] initChimiste()
	{
		PileCaractere[] tabPile = new PileCaractere[NB_TUBE];

		for(int cpt=0; cpt<tabPile.length; cpt++)
		{
			tabPile[cpt] = PileCaractere.creerPileCaractere(NB_BILLE_MAXIMUM_TUBE);
		}

		tabPile[0].empiler('M');
		tabPile[0].empiler('M');
		tabPile[0].empiler('.');
		tabPile[0].empiler('.');

		tabPile[1].empiler('R');
		tabPile[1].empiler('R');
		tabPile[1].empiler('.');
		tabPile[1].empiler('.');

		tabPile[2].empiler('V');
		tabPile[2].empiler('V');
		tabPile[2].empiler('.');
		tabPile[2].empiler('.');

		return tabPile;
	}

	/*------------------------------------------*/
	/*			VÉRIFICATION DU GAGNANT			*/
	/*------------------------------------------*/

	/**
		* @return Retourne true : si le joueur a gagné / false : si le joueur n'a pas gagné. 
	*/
	public boolean estGagnant()
	{
		boolean bGagnant = false;

		for(int cpt=0; cpt<DrEureka.NB_TUBE; cpt++)
		{
			if ( ! this.ensObjectif[this.niveau][cpt].equals(this.ensChimiste[this.niveau][cpt]) )
			{
				return false;
			}
		}

		return true;
	}
}