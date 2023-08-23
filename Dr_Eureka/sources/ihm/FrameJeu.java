package sources.ihm;

/*----L'-import-pour-le-contrôleur----*/
import sources.Controleur;

/*----Les-imports-pour-l'-ihm----*/
import sources.ihm.PanelJeu;
import sources.ihm.PanelMenu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager;


/**
	* Classe Frame Jeu
	* @author 	: -
	* @version 	: 1.0
	* date 		! 01/08/2020
*/

public class FrameJeu extends JFrame
{
	/**
		* Définition du serialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	/*----------------------*/
	/* Attribut pour le jeu */
	/*----------------------*/
	private Controleur ctrl; 				// L'attribut renseigne sur le contrôleur.
	
	/*--------------------------------------*/
	/* Attributs pour l'affichage graphique */
	/*--------------------------------------*/
	private PanelMenu 		panelMenu; 		// L'attribut renseigne sur le PanelMenu.
	private PanelJeu 		panelJeu;		// L'attribut renseigne sur le PanelJeu.

	/*-----------------*/
	/* Le Constructeur */
	/*-----------------*/

	/**
		* Constructeur FrameJoueur
		* @param ctrl 			: Le contrôleur.
		* @param largeurEcran 	: La largeur de l'écran de l'utilisateur.
		* @param hauteurEcran 	: La hauteur de l'écran de l'utilisateur.
	*/
	public FrameJeu(Controleur ctrl, int largeurEcran, int hauteurEcran)
	{
		/*------------------------------------*/
		/* Informations sur la Frame du jeu   */
		/*------------------------------------*/
		this.ctrl = ctrl;
		this.setTitle("Le jeu DrEureka");
		double coeffLargeur = (largeurEcran / (double) 800);
		double coeffHauteur = (hauteurEcran / (double) 650);

		int largeurFrameJeu = (int) (largeurEcran / coeffLargeur);
		int hauteurFrameJeu = (int) (hauteurEcran / coeffHauteur);
		
		this.setSize(largeurFrameJeu, hauteurFrameJeu); 
		this.setLocation( (largeurEcran/2)-(this.getWidth()/2), (hauteurEcran/2)-(this.getHeight()/2) );
		this.setLayout( new BorderLayout() );

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.panelMenu = new PanelMenu(ctrl);
		this.panelJeu = new PanelJeu(ctrl);

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(panelMenu, BorderLayout.NORTH);
		this.add(panelJeu, BorderLayout.CENTER);

		/*-------------------------*/
		/* Activation du composant */
		/*-------------------------*/
		this.addWindowListener( new FermetureFrame() );

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/*------------------------------------------------------------------------------------------------------*/
	/* 											LES MÉTHODES 												*/
	/*------------------------------------------------------------------------------------------------------*/

	public char getActionSelection()
	{
		return this.panelJeu.getActionSelection();
	}

	/*------------------------------*/
	/*			L'AFFICHAGE			*/
	/*------------------------------*/

	/**
		* Fixe le mode sombre.
	*/
	public void setModeSombre()
	{
		this.panelMenu.setModeSombre();
		this.panelJeu.setModeSombre();
	}

	/**
		* Fixe le mode clair.
	*/
	public void setModeClair()
	{
		this.panelMenu.setModeClair();
		this.panelJeu.setModeClair();
	}


	/*----------------------------------*/
	/*			LA MISE À JOUR			*/
	/*----------------------------------*/

	/**
		* Met à jour le panel jeu.
	*/
	public void majGraphique()
	{
		this.panelJeu.majGraphique();
	}

	/*----------------------------------*/
	/*			LA FERMETURE			*/
	/*----------------------------------*/

	/**
		* Ferme la FrameJeu.
	*/
	public void fermerFrameJeu()
	{
		this.panelMenu.fermerPanelMenu();
		this.panelJeu.fermerPanelJeu();
		this.dispose();
	}

	/**
		* Fermeture de la fenêtre graphique.
	*/
	private class FermetureFrame extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			FrameJeu.this.ctrl.fermerJeu();
		}
	}
}