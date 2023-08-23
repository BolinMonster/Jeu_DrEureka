package sources;

/*----L'-import-pour-le-metier----*/
import sources.metier.DrEureka;

/*----Les-imports-pour-l'-ihm----*/
import sources.ihm.FrameAccueil;
import sources.ihm.FrameJeu;
import java.awt.Dimension;
import java.awt.Point;

/*----Les-imports-pour-la-serialisation----*/
import sources.serialisation.Jeu;
import sources.serialisation.DisquetteJeu;
import java.util.Stack;


/**
	* Classe Controleur
	* @author 	: -
	* @version 	: 1.0
	* date 		! 02/08/2020
*/

public class Controleur
{
	/*---------------*/
	/* Les Attributs */
	/*---------------*/

	/**
		* Partie Métier
	*/ 
	private DrEureka metier; // L'attribut pour traiter le jeu de façon transparente pour l'utilisateur.

	/**
		* La partie IHM (Interface Graphique c'est l'IHM = Interface Homme Machine).
	*/
	private FrameAccueil 	ihmAccueil; 	// L'attribut de la partie ihm concernant l'accueil du jeu.

	private FrameJeu 		ihmJeu; 		// L'attribut de la partie ihm concernant la fenêtre du jeu.

	/*-----------------*/
	/* Le Constructeur */
	/*-----------------*/

	/**
		* Constructeur Controleur
		* À l'instanciation d'un objet Controleur, une fenêtre d'accueil du jeu apparaît 
		* avec le nom du joueur à saisir.
	*/
	public Controleur()
	{
		int 		largeurEcran, hauteurEcran;
		Dimension 	dimEcran;

		dimEcran     = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		hauteurEcran = (int) dimEcran.getHeight();
		largeurEcran = (int) dimEcran.getWidth();

		this.ihmAccueil = new FrameAccueil(this, largeurEcran, hauteurEcran);
	}

	/*------------------------------------------------------------------------------------------------------*/
	/* 											LES MÉTHODES 												*/
	/*------------------------------------------------------------------------------------------------------*/


	/*--------------------------------------*/
	/* 			LANCEMENT DU JEU 			*/
	/*--------------------------------------*/

	/**
		* Lance une partie de jeu
		* @param pseudoJoueur : Le pseudo du joueur.
	*/
	public void lancerJeu(String pseudoJoueur)
	{
		int 		largeurEcran, hauteurEcran;
		Dimension 	dimEcran;

		dimEcran     = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		hauteurEcran = (int) dimEcran.getHeight();
		largeurEcran = (int) dimEcran.getWidth();

		this.metier = new DrEureka();
		
		this.setPseudoJoueur(pseudoJoueur);

		this.ihmJeu = new FrameJeu(this, largeurEcran, hauteurEcran);
	}

	/*------------------------------------------------------*/
	/*				MÉTHODES DE LA PARTIE METIER 			*/
	/*------------------------------------------------------*/

	/**
		* @return Retourne le nombre de niveau.
	*/
	public int getNbNiveau()
	{
		return DrEureka.getNbNiveau();
	}

	/**
		* @return Retourne le nombre de tube.
	*/
	public int getNbTube()
	{
		return DrEureka.getNbTube();
	}

	/**
		* @return Retourne le nombre de bille maximum dans un tube.
	*/
	public int getNbBilleMaxTube()
	{
		return DrEureka.getNbBilleMaxTube();
	}

	/**
		* @return Retourne le nombre de bille au total.
	*/
	public int getNbBilleTotal()
	{
		return DrEureka.getNbBilleTotal();
	}

	/**
		* @return Retourne la pile de caractère d'un tube.
		* @param type 		: La pile retournée, 'O' pour l'objectif ou par défaut pour le chimiste.
		* @param numTube 	: le numéro du tube.
	*/
	public String getTube(char type, int numTube)
	{
		return this.metier.getTube(type, numTube);
	}

	/**
		* @return Retourne le niveau.
	*/
	public int getNiveau()
	{
		return this.metier.getNiveau();
	}

	/**
		* @return Retourne le pseudo du joueur.
	*/
	public String getPseudoJoueur()
	{
		return this.metier.getPseudoJoueur();
	}

	/**
		* @return Retourne true : si le tube est retournée / false : si le tube est à l'endroit.
		* @param numNiveau 	: Le numéro du niveau.
		* @param numTube 	: Le numéro du tube.
	*/
	public boolean getTubeRetourner(int numNiveau, int numTube)
	{
		return this.metier.getTubeRetourner(numNiveau, numTube);
	}

	/**
		* @return Retourne un message indiquant l'action que le joueur ne peut pas réaliser.
	*/
	public String getMessageNonJouable()
	{
		return this.metier.getMessageNonJouable();
	}

	/**
		* @return Retourne la couleur d'affichage.
	*/
	public boolean getCouleurAffichage()
	{
		return this.metier.getCouleurAffichage();
	}

	/**
		* Affecte le niveau suivant.
	*/
	public void niveauSuivant()
	{
		this.metier.niveauSuivant();
	}

	/**
		* Affecte un pseudo au joueur.
		* @param pseudoJoueur : le pseudo du joueur.
	*/
	public void setPseudoJoueur(String pseudoJoueur)
	{
		this.metier.setPseudoJoueur(pseudoJoueur);
	}

	/**
		* Fixe le message non jouable.
	*/
	public void setMessageNonJouable()
	{
		this.metier.setMessageNonJouable();
	}

	/**
		* L'ensemble des actions que le joueur peut réaliser.
		* @param typeAction 	: le type de l'action.
		* @param numSource 		: le tube source.
		* @param numDest 		: le tube destination.
	*/
	public void ensembleAction(char typeAction, int numSource, int numDest)
	{
		if ( typeAction == 'R' )
		{
			if ( numSource >= 0 && numSource < this.getNbTube() )
			{
				this.retournerTube(numSource);
			}
		}
		else
		{
			if ( ( numSource >= 0 && numSource < this.getNbTube() ) && (numDest >= 0 && numDest < this.getNbTube() ) )
			{
				switch(typeAction)
				{
					case 'P':
						this.echangerTube(numSource, numDest);
					break;

					case 'D':
						this.deplacerBille(numSource, numDest);
					break;
				}
			}
		}
		
		// Après qu'une action a été effectué, une mise à jour graphique est faite
		this.majGraphique();
	}

	/**
		* Retourne un tube.
		* @param numTube : le numéro du tube.
	*/
	private void retournerTube(int numTube)
	{
		this.metier.retournerTube(numTube);
	}

	/**
		* Echange deux tubes.
		* @param numTube1 : le tube numéro 1.
		* @param numTube2 : le tube numéro 2.
	*/
	private void echangerTube(int numTube1, int numTube2)
	{
		this.metier.echangerTube(numTube1, numTube2);
	}

	/**
		* Déplace une bille dans un tube vers un autre tube.
		* @param numSource 	: le tube d'origine.
		* @param numDest 	: le tube de destination. 
	*/
	private void deplacerBille(int numSource, int numDest)
	{
		this.metier.deplacerBille(numSource, numDest);
	}

	/**
		* @return Retourne true : si le joueur a gagné / false : si le joueur n'a pas gagné. 
	*/
	public boolean estGagnant()
	{
		return this.metier.estGagnant();
	}

	/*------------------------------------------------------*/
	/*				MÉTHODES DE LA PARTIE IHM 				*/
	/*------------------------------------------------------*/

	public void majGraphique()
	{
		if ( this.ihmJeu != null )
		{
			this.ihmJeu.majGraphique();
		}
	}

	public char getActionSelection()
	{
		if ( this.ihmJeu != null )
		{
			return this.ihmJeu.getActionSelection();
		} 

		return ' ';
	}

	/**
		* Fixe le mode sombre.
	*/
	public void setModeSombre()
	{
		this.metier.setCouleurAffichage();
		this.ihmJeu.setModeSombre();
	}

	/**
		* Fixe le mode clair.
	*/
	public void setModeClair()
	{
		this.metier.setCouleurAffichage();
		this.ihmJeu.setModeClair();
	}

	/**
		* Ferme l'interface d'accueil du jeu.
	*/
	public void fermerAccueil()
	{
		this.ihmAccueil.fermerAccueil();
	}

	/**
		* Quitte le jeu.
	*/
	public void fermerJeu()
	{
		this.metier 		= null;
		this.ihmAccueil 	= null;

		this.ihmJeu.fermerFrameJeu();
		this.ihmJeu = null;
	}

	/**
		* Permet de nettoyer la partie.
	*/
	public void nettoyerJeuOuverture()
	{
		this.metier = null;

		if ( this.ihmJeu != null )
		{
			this.ihmJeu.fermerFrameJeu();
		}
		this.ihmJeu = null;
	}

	/*--------------------------------------------------------------*/
	/*				MÉTHODES DE LA PARTIE SÉRIALISATION				*/
	/*--------------------------------------------------------------*/

	/**
		* @return Retourne les noms des parties enregistrées.
	*/
	public String[] getJeuxEnregistres()
	{
		Stack<Jeu> ensJeux = new DisquetteJeu().getPileJeux();
		if ( ensJeux!= null )
		{
			String[] tabNomJeu = new String[ensJeux.size()];
			for(int cpt=0; cpt<tabNomJeu.length; cpt++)
			{
				tabNomJeu[cpt] = ensJeux.get(cpt).getNom();
			}

			return tabNomJeu;
		}
		return null;
	}

	/**
		* Permet de sauvegarder une partie.
		* @param nomJeu : le nom de la partie que le joueur sauvegarde.
	*/
	public void sauvegarder(String nomJeu)
	{
		DisquetteJeu disquetteJeu = new DisquetteJeu();
		disquetteJeu.sauvegardeJeu(nomJeu, this.metier);
	}

	/**
		* Permet d'ouvrir une partie.
		* @param nomJeu : le nom de la partie que le joueur ouvre.
	*/
	public void ouvrir(String nomJeu)
	{
		this.nettoyerJeuOuverture();

		int 		largeurEcran, hauteurEcran;
		Dimension 	dimEcran;

		dimEcran     = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		hauteurEcran = (int) dimEcran.getHeight();
		largeurEcran = (int) dimEcran.getWidth();

		if ( this.metier == null && this.ihmJeu == null)
		{
			this.metier = new DrEureka(nomJeu);
			this.ihmJeu = new FrameJeu(this, largeurEcran, hauteurEcran);
		}

		if ( this.metier.getCouleurAffichage() )
		{
			this.setModeSombre();
		}
	}

	/*--------------------------------------------------------------------------------------*/
	/*							MAIN : ÉXÉCUTION DE L'APPLICATION							*/
	/*--------------------------------------------------------------------------------------*/

	public static void main(String[] args) 
	{
		new Controleur();	
	}

}