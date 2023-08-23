package sources.ihm;

/*----L'-import-pour-le-contrôleur----*/
import sources.Controleur;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Font;

import javax.swing.*;

import java.io.File;


/**
	* Classe Panel Jeu
	* @author 	: -
	* @version 	: 1.0
	* date 		! 01/08/2020
*/

public class PanelJeu extends JPanel
{
	/**
		* Définition du serialVersionUID
	*/
	private static final long serialVersionUID = 1L;

	/*--------------------------------------*/
	/* Attributs pour l'affichage graphique */
	/*--------------------------------------*/
	private PanelObjectif 	panelObjectif; 	// L'attribut renseigne sur le PanelObjectif.
	private PanelTube 		panelTube; 		// L'attribut renseigne sur le PanelTube.
	private PanelAction 	panelAction; 	// L'attribut renseigne sur le PanelAction.

	/*-----------------*/
	/* Le Constructeur */
	/*-----------------*/

	/**
		* Constructeur PanelJeu
		* @param ctrl : Le contrôleur.
	*/
	public PanelJeu(Controleur ctrl)
	{
		/*---------------------------*/
		/* Informations sur le panel */
		/*---------------------------*/
		this.setLayout( new BorderLayout(0,10) );
		this.setBackground(Color.WHITE);

		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		this.panelObjectif 	= new PanelObjectif(ctrl);
		this.panelTube 		= new PanelTube(ctrl);
		this.panelAction 	= new PanelAction(ctrl);

		this.panelObjectif.setBorder(BorderFactory.createLineBorder(Color.black));
		this.panelTube.setBorder(BorderFactory.createLineBorder(Color.black));

		/*-------------------------------*/
		/* Positionnement des composants */
		/*-------------------------------*/
		this.add(this.panelObjectif, BorderLayout.NORTH);
		this.add(this.panelTube, BorderLayout.CENTER);
		this.add(this.panelAction, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	/*------------------------------------------------------------------------------------------------------*/
	/* 											LES MÉTHODES 												*/
	/*------------------------------------------------------------------------------------------------------*/
	
	/**
		* @return Retourne l'action sélectionnée.
	*/
	public char getActionSelection()
	{
		return this.panelAction.getActionSelection();
	}

	/*----------------------------------*/
	/*			LA MISE À JOUR			*/
	/*----------------------------------*/

	/**
		* Met à jour le panel objectif et le panel tube.
	*/
	public void majGraphique()
	{
		this.panelObjectif.majGraphique();
		this.panelTube.majGraphique();
	}

	/*------------------------------*/
	/*			L'AFFICHAGE			*/
	/*------------------------------*/

	/**
		* Fixe le mode sombre.
	*/
	public void setModeSombre()
	{
		this.panelObjectif.setModeSombre();
		this.panelTube.setModeSombre();
		this.panelAction.setModeSombre();
		this.setBackground(Color.DARK_GRAY);
	}

	/**
		* Fixe le mode clair.
	*/
	public void setModeClair()
	{
		this.panelObjectif.setModeClair();
		this.panelTube.setModeClair();
		this.panelAction.setModeClair();
		this.setBackground(Color.WHITE);
	}

	/*----------------------------------*/
	/*			LA FERMETURE			*/
	/*----------------------------------*/

	/**
		* Ferme le Panel Jeu.
	*/
	public void fermerPanelJeu()
	{
		this.panelObjectif.fermerPanelObjectif();
		this.panelTube.fermerPanelTube();
		this.panelAction.fermerPanelAction();

		this.removeAll();
		this.setVisible(false);
	}

	private class PanelObjectif extends JPanel
	{
		/**
			* Définition du serialVersionUID
		*/
		private static final long serialVersionUID = 1L;
		
		/*----------------------*/
		/* Attribut pour le jeu */
		/*----------------------*/
		private Controleur ctrl; // L'attribut renseigne sur le contrôleur.

		/*-------------------------------------*/
		/* Attribut pour l'affichage graphique */
		/*-------------------------------------*/
		private JLabel lblTitre; // L'attribut renseigne sur le label titre.

		/*-----------------*/
		/* Le Constructeur */
		/*-----------------*/

		/**
			* Constructeur PanelObjectif
			* @param ctrl : Le contrôleur.
		*/
		public PanelObjectif(Controleur ctrl)
		{
			/*---------------------------*/
			/* Informations sur le panel */
			/*---------------------------*/
			this.ctrl = ctrl;
			this.setLayout( new BorderLayout(0,50) );

			/*--------------------*/
			/* La police utilisée */
			/*--------------------*/
			Font policeTitre = new Font("Calibri", Font.BOLD, 20);

			/*-------------------------*/
			/* Création des composants */
			/*-------------------------*/
			this.lblTitre = new JLabel("Objectif numéro " + (this.ctrl.getNiveau()+1), JLabel.LEFT);
			this.lblTitre.setFont(policeTitre);
			this.lblTitre.setForeground(Color.WHITE);
			this.lblTitre.setOpaque(false);

			/*-----------------------------*/
			/* Positionnement du composant */
			/*-----------------------------*/
			this.add(lblTitre, BorderLayout.NORTH);

			this.setVisible(true);
		}

		/*------------------------------------------------------------------------------------------------------*/
		/* 											LES MÉTHODES 												*/
		/*------------------------------------------------------------------------------------------------------*/

		/*----------------------------------*/
		/*			LA MISE À JOUR			*/
		/*----------------------------------*/

		/**
			* Met à jour le panel objectif.
		*/
		public void majGraphique()
		{
			this.lblTitre.setText("Objectif numéro " + (this.ctrl.getNiveau()+1) );
			this.repaint();
		}

		/*------------------------------*/
		/*			L'AFFICHAGE			*/
		/*------------------------------*/

		/**
			* Fixe le mode sombre.
		*/
		public void setModeSombre()
		{
			this.setBackground(Color.DARK_GRAY);
		}

		/**
			* Fixe le mode clair.
		*/
		public void setModeClair()
		{
			this.setBackground(Color.WHITE);
		}

		/*------------------*/
		/* Le fond du panel */
		/*------------------*/
		public void paintComponent(Graphics g)
		{
			String     sImage;
			Image      img;
			Graphics2D g2 = (Graphics2D) g;

			super.paintComponent(g);

			// Dessine le fond
			sImage = "./ressources/images/Dr_Eureka_background_objectif.png";
			if ( sImage != null )
			{
				img = getToolkit().getImage ( sImage );
				g2.drawImage ( img, 0, 0, this.getWidth(), this.getHeight(), this );
			}

			// Réactif avec les pourcentages
			int largeurEspace 	= 0;
			int hauteurEspace 	= 0;
			int tailleLargeurTube 	= 0;
			int tailleHauteurTube 	= 0;
			int tailleLargeurBille 	= 0;
			int tailleHauteurBille 	= 0;

			// Dessine les tubes
			largeurEspace = (int) ( this.getWidth() * ( (double) 37/100 ) );
			hauteurEspace = (int) ( this.getHeight() * ( (double) 30/100 ) );
			tailleLargeurTube = (int) ( this.getWidth() * ( (double) 8/100 ) );
			tailleHauteurTube = (int) ( this.getHeight() * ( (double) 68/100 ) );

			for(int numTube=0; numTube<this.ctrl.getNbTube(); numTube++)
			{
					img = getToolkit().getImage ( "./ressources/images/tube.png" );
					g2.drawImage ( img, largeurEspace, hauteurEspace, tailleLargeurTube, tailleHauteurTube, this );

					largeurEspace += (int) ( this.getWidth() * ( (double) 10/100 ) );
			}

			largeurEspace 	= 0;
			hauteurEspace 	= 0;

			// Dessine les billes
			largeurEspace 	= (int) ( this.getWidth() * ( (double) 39/100 ) );
			hauteurEspace 	= 0;
			tailleLargeurBille = (int) ( this.getWidth() * ( (double) 4/100 ) );
			tailleHauteurBille = (int) ( this.getHeight() * ( (double) 15/100 ) );
			for(int numTube=0; numTube<this.ctrl.getNbTube(); numTube++)
			{
				hauteurEspace = (int) ( this.getHeight() * ( (double) 82/100 ) );
				String sLienBilleTube = this.ctrl.getTube('O', numTube).toLowerCase();

				for(int numBille=0; numBille<4; numBille++)
				{
					img = getToolkit().getImage ( "./ressources/images/bille_" + sLienBilleTube.charAt(numBille) + ".png" );
					g2.drawImage ( img, largeurEspace, hauteurEspace, tailleLargeurBille, tailleHauteurBille, this );

					hauteurEspace -= (int) ( this.getHeight() * ( (double) 15/100 ) );
				}

				largeurEspace += (int) ( this.getWidth() * ( (double) 10/100 ) );
			}
		}

		/*----------------------------------*/
		/*			LA FERMETURE			*/
		/*----------------------------------*/

		/**
			* Ferme le Panel Objectif.
		*/
		public void fermerPanelObjectif()
		{
			this.removeAll();
			this.setVisible(false);
		}
	}

	private class PanelTube extends JPanel
	{
		/**
			* Définition du serialVersionUID
		*/
		private static final long serialVersionUID = 1L;
		
		/*----------------------*/
		/* Attribut pour le jeu */
		/*----------------------*/
		private Controleur ctrl; // L'attribut renseigne sur le contrôleur.

		/*-----------------*/
		/* Le Constructeur */
		/*-----------------*/

		/**
			* Constructeur PanelTube
			* @param ctrl : Le contrôleur.
		*/
		public PanelTube(Controleur ctrl)
		{
			/*---------------------------*/
			/* Informations sur le panel */
			/*---------------------------*/
			this.ctrl = ctrl;
			this.setLayout( new GridLayout() );

			this.setVisible(true);
		}

		/*------------------------------------------------------------------------------------------------------*/
		/* 											LES MÉTHODES 												*/
		/*------------------------------------------------------------------------------------------------------*/

		/*----------------------------------*/
		/*			LA MISE À JOUR			*/
		/*----------------------------------*/

		/**
			* Met à jour le panel tube.
		*/
		public void majGraphique()
		{
			this.repaint();
		}

		/*------------------------------*/
		/*			L'AFFICHAGE			*/
		/*------------------------------*/

		/**
			* Fixe le mode sombre.
		*/
		public void setModeSombre()
		{
			this.setBackground(Color.DARK_GRAY);
		}

		/**
			* Fixe le mode clair.
		*/
		public void setModeClair()
		{
			this.setBackground(Color.WHITE);
		}

		/*------------------*/
		/* Le fond du panel */
		/*------------------*/
		public void paintComponent(Graphics g)
		{
			String     sImage;
			Image      img;
			Graphics2D g2 = (Graphics2D) g;

			super.paintComponent(g);
			// Dessine le fond
			sImage = "./ressources/images/DrEureka_background_jeu.png";
			if ( sImage != null )
			{
				img = getToolkit().getImage ( sImage );
				g2.drawImage ( img, 0, 0, this.getWidth(), this.getHeight(), this );
			}

			// Réactif avec les pourcentages
			int largeurEspace 	= 0;
			int hauteurEspace 	= 0;
			int tailleLargeurTube 	= 0;
			int tailleHauteurTube 	= 0;
			int tailleLargeurBille 	= 0;
			int tailleHauteurBille 	= 0;

			// Dessine les tubes
			largeurEspace = (int) ( this.getWidth() * ( (double) 5/100 ) );
			hauteurEspace = (int) ( this.getHeight() * ( (double) 28/100 ) );
			tailleLargeurTube = (int) ( this.getWidth() * ( (double) 18/100 ) );
			tailleHauteurTube = (int) ( this.getHeight() * ( (double) 70/100 ) );

			for(int numTube=0; numTube<this.ctrl.getNbTube(); numTube++)
			{
					String fichier = "tube";

					if ( ! this.ctrl.getTubeRetourner(this.ctrl.getNiveau(), numTube) )
					{
						fichier += "_retourne";
					}

					img = getToolkit().getImage ( "./ressources/images/" + fichier + ".png" );

					g2.drawImage ( img, largeurEspace, hauteurEspace, tailleLargeurTube, tailleHauteurTube, this );

					largeurEspace += (int) ( this.getWidth() * ( (double) 35/100 ) );
			}

			largeurEspace 	= 0;
			hauteurEspace 	= 0;

			// Dessine les billes
			largeurEspace 	= (int) ( this.getWidth() * ( (double) 9/100 ) );
			hauteurEspace 	= 0;
			tailleLargeurBille = (int) ( this.getWidth() * ( (double) 10/100 ) );
			tailleHauteurBille = (int) ( this.getHeight() * ( (double) 13/100 ) );
			for(int numTube=0; numTube<this.ctrl.getNbTube(); numTube++)
			{
				hauteurEspace = (int) ( this.getHeight() * ( (double) 80/100 ) );
				String sLienBilleTube = this.ctrl.getTube(' ', numTube).toLowerCase();

				for(int numBille=0; numBille<this.ctrl.getNbBilleMaxTube(); numBille++)
				{
					img = getToolkit().getImage ( "./ressources/images/bille_" + sLienBilleTube.charAt(numBille) + ".png" );
					g2.drawImage ( img, largeurEspace, hauteurEspace, tailleLargeurBille, tailleHauteurBille, this );

					hauteurEspace -= (int) ( this.getHeight() * ( (double) 13/100 ) );
				}
				largeurEspace += (int) ( this.getWidth() * ( (double) 35/100 ) );
			}

			largeurEspace 	= 0;
			hauteurEspace 	= 0;

			// Dessine les numéros des tubes
			int tailleFont = (int) ( (this.getWidth() * (double) 4/100 ) );
			g2.setFont( new Font("Calibri", Font.BOLD, tailleFont) );
			g2.setColor(Color.BLACK);

			largeurEspace 	= (int) ( this.getWidth() * ( (double) 8/100 ) );
			hauteurEspace 	= (int) ( this.getHeight() * ( (double) 98/100 ) );
			for(int numTube=0; numTube<this.ctrl.getNbTube(); numTube++)
			{
				g2.drawString ( "Tube " + (numTube+1), largeurEspace, hauteurEspace);
				largeurEspace += (int) ( this.getWidth() * ( (double) 35/100 ) );
			}
		}

		public void update(Graphics g)
		{
			paint(g);
		}

		/*----------------------------------*/
		/*			LA FERMETURE			*/
		/*----------------------------------*/

		/**
			* Ferme le Panel Tube.
		*/
		public void fermerPanelTube()
		{
			this.removeAll();
			this.setVisible(false);
		}
	}

	private class PanelAction extends JPanel implements ActionListener
	{
		/**
			* Définition du serialVersionUID
		*/
		private static final long serialVersionUID = 1L;
		
		/*----------------------*/
		/* Attribut pour le jeu */
		/*----------------------*/
		private Controleur ctrl; 		// L'attribut renseigne sur le contrôleur.
		private char actionSelection; 	// L'attribut renseigne sur l'action sélectionée.

		/*--------------------------------------*/
		/* Attributs pour l'affichage graphique */
		/*--------------------------------------*/
		private JPanel 					panelBouton; 			// L'attribut renseigne sur le panelBouton.
		private PanelActionSelection 	panelActionSelection; 	// L'attribut renseigne sur le panelActionSelection.
		private JButton 				btnRetourner; 			// L'attribut renseigne sur le bouton retourner.
		private JButton 				btnPermuter; 			// L'attribut renseigne sur le bouton permuter.
		private JButton 				btnDeplacer; 			// L'attribut renseigne sur le bouton deplacer.

		/*-----------------*/
		/* Le Constructeur */
		/*-----------------*/

		/**
			* Constructeur PanelAction
			* @param ctrl : Le contrôleur.
		*/
		public PanelAction(Controleur ctrl)
		{
			/*---------------------------*/
			/* Informations sur le panel */
			/*---------------------------*/
			this.ctrl = ctrl;
			this.actionSelection = ' ';
			this.setLayout( new BorderLayout(10,0) );
			this.setBackground(Color.WHITE);

			/*--------------------*/
			/* La police utilisée */
			/*--------------------*/
			Font policeBouton 	= new Font("Calibri", Font.BOLD, 20);

			/*-------------------------*/
			/* Création des composants */
			/*-------------------------*/
			this.panelBouton = new JPanel();
			this.panelBouton.setLayout( new GridLayout(1, 3, 50, 0) );
			this.panelBouton.setBackground(Color.WHITE);

			this.btnRetourner = new JButton("Retourner");
			this.btnRetourner.setFont(policeBouton);
			this.btnRetourner.setForeground(Color.BLACK);
			this.btnRetourner.setBorder(BorderFactory.createLineBorder(Color.black));

			this.btnPermuter = new JButton("Permuter");
			this.btnPermuter.setFont(policeBouton);
			this.btnPermuter.setForeground(Color.BLACK);
			this.btnPermuter.setBorder(BorderFactory.createLineBorder(Color.black));

			this.btnDeplacer = new JButton("Déplacer");
			this.btnDeplacer.setFont(policeBouton);
			this.btnDeplacer.setForeground(Color.BLACK);
			this.btnDeplacer.setBorder(BorderFactory.createLineBorder(Color.black));


			this.panelActionSelection = new PanelActionSelection(ctrl);
			this.panelActionSelection.setBackground(Color.WHITE);

			/*-------------------------------*/
			/* Positionnement des composants */
			/*-------------------------------*/
			this.panelBouton.add(this.btnRetourner);
			this.panelBouton.add(this.btnPermuter);
			this.panelBouton.add(this.btnDeplacer);

			this.add(this.panelBouton, BorderLayout.NORTH);
			this.add(this.panelActionSelection, BorderLayout.CENTER);

			/*---------------------------*/
			/* Activation des composants */
			/*---------------------------*/
			this.btnRetourner.addActionListener(this);
			this.btnPermuter.addActionListener(this);
			this.btnDeplacer.addActionListener(this);

			this.setVisible(true);
		}


		/*--------------------------------------------------------------------------------------------------*/
		/* 											LES MÉTHODES 											*/
		/*--------------------------------------------------------------------------------------------------*/

		/**
			* @return Retourne l'action sélectionnée.
		*/
		public char getActionSelection()
		{
			return this.actionSelection;
		}

		/*------------------------------*/
		/*			L'AFFICHAGE			*/
		/*------------------------------*/

		/**
			* Fixe le mode sombre.
		*/
		public void setModeSombre()
		{
			this.panelActionSelection.setModeSombre();
			this.panelBouton.setBackground(Color.DARK_GRAY);
			this.panelActionSelection.setBackground(Color.DARK_GRAY);
			this.setBackground(Color.DARK_GRAY);
		}

		/**
			* Fixe le mode clair.
		*/
		public void setModeClair()
		{
			this.panelActionSelection.setModeClair();
			this.panelBouton.setBackground(Color.WHITE);
			this.panelActionSelection.setBackground(Color.WHITE);
			this.setBackground(Color.WHITE);
		}

		/*----------------------------------*/
		/*			LA FERMETURE			*/
		/*----------------------------------*/

		/**
			* Ferme le Panel Action.
		*/
		public void fermerPanelAction()
		{
			this.panelActionSelection.fermerPanelActionSelection();
			this.removeAll();
			this.setVisible(false);
		}

		/*----------------------------------------------*/
		/*			L'ÉVÈNEMENT	ACTION-LISTENER			*/
		/*----------------------------------------------*/
		
		/**
			* Si le joueur clique sur l'un des boutons des actions.
		*/
		public void actionPerformed(ActionEvent e)
		{
			String actionTitre = "Action : ";

			if ( e.getSource() == this.btnRetourner )
			{
				this.actionSelection = 'R';
				actionTitre 	+= "retourner";
			}

			if( e.getSource() == this.btnPermuter )
			{
				this.actionSelection = 'P';
				actionTitre 	+= "permuter";
			}

			if( e.getSource() == this.btnDeplacer )
			{
				this.actionSelection = 'D';
				actionTitre += "déplacer";
			}

			// Avant de changer le panel, nettoyage du panel
			this.panelActionSelection.removeAll();
			this.panelActionSelection.revalidate();
			this.panelActionSelection.repaint();

			this.remove(this.panelActionSelection);

			// Création et Positionnement du nouveau panel
			this.panelActionSelection = new PanelActionSelection(this.ctrl);
			this.add(this.panelActionSelection);
			
			this.panelActionSelection.setLabelTitre(actionTitre);

			if ( this.ctrl.getCouleurAffichage() )
			{
				this.panelActionSelection.setModeSombre();
			}
			else
			{
				this.panelActionSelection.setModeClair();
			}

		}

		private class PanelActionSelection extends JPanel implements ActionListener
		{
			/**
				* Définition du serialVersionUID
			*/
			private static final long serialVersionUID = 1L;
			
			/*----------------------*/
			/* Attribut pour le jeu */
			/*----------------------*/
			private Controleur ctrl; // L'attribut renseigne sur le contrôleur.

			/*--------------------------------------*/
			/* Attributs pour l'affichage graphique */
			/*--------------------------------------*/
			private JPanel 										panelLblAction; 						// L'attribut renseigne sur le panel label action.
			private JPanel 										panelBtnValider; 						// L'attribut renseigne sur le panel bouton valider.
			private JLabel 										lblAction; 								// L'attribut renseigne sur le label action.
			private PanelActionSelectionTubeDisposition 		panelActionSelectionTubeDisposition; 	// L'attribut renseigne sur le panel PanelActionSelectionTubeDisposition.
			private JButton 									btnValider; 							// L'attribut renseigne sur le bouton valider.

			/*-----------------*/
			/* Le Constructeur */
			/*-----------------*/

			/**
				* Constructeur PanelActionSelection
				* @param ctrl : Le contrôleur.
			*/
			public PanelActionSelection(Controleur ctrl)
			{
				/*---------------------------*/
				/* Informations sur le panel */
				/*---------------------------*/
				this.ctrl 				= ctrl;

				this.setLayout( new BorderLayout(10, 10) );
				this.setBackground(Color.WHITE);

				/*--------------------*/
				/* La police utilisée */
				/*--------------------*/
				Font policeLblBtnAction = new Font("Calibri", Font.BOLD, 17);

				/*-------------------------*/
				/* Création des composants */
				/*-------------------------*/
				this.panelLblAction = new JPanel();
				this.panelLblAction.setLayout( new FlowLayout() );
				this.panelLblAction.setBackground(Color.WHITE);

				this.panelBtnValider = new JPanel();
				this.panelBtnValider.setLayout( new FlowLayout(FlowLayout.CENTER) );
				this.panelBtnValider.setBackground(Color.WHITE);

				this.lblAction = new JLabel("Aucune action sélectionnée");
				this.lblAction.setFont(policeLblBtnAction);

				if ( this.ctrl.getActionSelection() != ' ' )
				{
					this.panelActionSelectionTubeDisposition = new PanelActionSelectionTubeDisposition(ctrl);
					this.btnValider = new JButton("Valider");
					this.btnValider.setFont(policeLblBtnAction);
				}

				/*-------------------------------*/
				/* Positionnement des composants */
				/*-------------------------------*/
				this.panelLblAction.add(lblAction, JLabel.CENTER);
				this.add(panelLblAction, BorderLayout.NORTH);

				if ( this.ctrl.getActionSelection() != ' ' )
				{
					this.panelBtnValider.add(this.btnValider);
					this.add(this.panelActionSelectionTubeDisposition, BorderLayout.CENTER);
					this.add(this.panelBtnValider, BorderLayout.SOUTH);
				}

				/*-------------------------*/
				/* Activation du composant */
				/*-------------------------*/
				if ( this.ctrl.getActionSelection() != ' ' )
				{
					this.btnValider.addActionListener(this);
				}

				this.setVisible(true);
			}

			/*--------------------------------------------------------------------------------------------------*/
			/* 											LES MÉTHODES 											*/
			/*--------------------------------------------------------------------------------------------------*/

			/**
				* Affecte un label titre.
				* @param actionTitre : le titre de l'action.
			*/
			public void setLabelTitre(String actionTitre)
			{
				this.lblAction.setText(actionTitre);
			}

			/**
				* JOptionPane lorsque le joueur gagne.
			*/
			public void optionPaneVictoire()
			{
				String messagePartie 	= "";
				String messageTitre 	= "";
				int optConfirme 		= -1;
				String pseudoJoueur = this.ctrl.getPseudoJoueur();

				if ( this.ctrl.getNiveau() < this.ctrl.getNbNiveau()-1 )
				{
					messagePartie = "Bravo " + pseudoJoueur + ", le niveau " + (this.ctrl.getNiveau()+1) + " est terminé";
					messagePartie += "\n" + "Voulez vous passer au niveau suivant ?";

					messageTitre 	= "Victoire";
					optConfirme = JOptionPane.showConfirmDialog(this, messagePartie, messageTitre, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

					if ( optConfirme == JOptionPane.YES_OPTION)
					{
						this.ctrl.niveauSuivant();
						this.ctrl.majGraphique();
					}
					else
					{
						this.ctrl.fermerJeu();
					}
				}
				else
				{
					messagePartie = "Félicitations " + pseudoJoueur + ", vous avez terminé tous les niveaux";
					messagePartie += "\n" + "Voulez vous rejouer ?";

					messageTitre 	= "Jeu terminé";
					optConfirme = JOptionPane.showConfirmDialog(this, messagePartie, messageTitre, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

					if ( optConfirme == JOptionPane.YES_OPTION)
					{
						this.ctrl.fermerJeu();
						this.ctrl.lancerJeu(pseudoJoueur);
					}
					else
					{
						this.ctrl.fermerJeu();
					}

				}
			}

			/*------------------------------*/
			/*			L'AFFICHAGE			*/
			/*------------------------------*/

			/**
				* Fixe le mode sombre.
			*/
			public void setModeSombre()
			{
				if (  this.ctrl.getActionSelection() != ' ' || this.panelActionSelectionTubeDisposition != null )
				{
					this.panelActionSelectionTubeDisposition.setModeSombre();
				}
				this.panelLblAction.setBackground(Color.DARK_GRAY);
				this.panelBtnValider.setBackground(Color.DARK_GRAY);
				this.lblAction.setForeground(Color.WHITE);
				this.setBackground(Color.DARK_GRAY);
			}

			/**
				* Fixe le mode clair.
			*/
			public void setModeClair()
			{
				if (  this.ctrl.getActionSelection() != ' ' || this.panelActionSelectionTubeDisposition != null )
				{
					this.panelActionSelectionTubeDisposition.setModeClair();
				}
				this.panelLblAction.setBackground(Color.WHITE);
				this.panelBtnValider.setBackground(Color.WHITE);
				this.lblAction.setForeground(Color.BLACK);
				this.setBackground(Color.WHITE);
			}

			/*----------------------------------*/
			/*			LA FERMETURE			*/
			/*----------------------------------*/
		
			/**
				* Ferme le Panel Action Selection.
			*/
			public void fermerPanelActionSelection()
			{
				if (  this.ctrl.getActionSelection() != ' ' || this.panelActionSelectionTubeDisposition != null )
				{
					this.panelActionSelectionTubeDisposition.fermerPanelActionSelectionTubeDisposition();
				}
				this.removeAll();
				this.setVisible(false);
			}

			/*----------------------------------------------*/
			/*			L'ÉVÈNEMENT	ACTION-LISTENER			*/
			/*----------------------------------------------*/
			
			/**
				* Si le joueur clique sur le bouton de validation.
			*/
			public void actionPerformed(ActionEvent e)
			{
				if ( e.getSource() == this.btnValider )
				{
					if ( ! this.ctrl.estGagnant() )
					{

						int[] tabRbSelection 	= this.panelActionSelectionTubeDisposition.getRbSelectionAction();
						int cpt 				= 0;
						boolean bErreur 		= false;
						int numTubeSource 		= -1;
						int numTubeDestination 	= -1;

						for(int cptErreur=0; cptErreur<tabRbSelection.length; cptErreur++)
						{
							if ( tabRbSelection[cptErreur] == -1 )
							{
								bErreur = true;
							}
						}

						String sMessErreur = "";

						if ( ! bErreur )
						{
							numTubeSource = tabRbSelection[cpt++];
							
							if ( this.ctrl.getActionSelection() != ' ' )
							{
								if ( this.ctrl.getActionSelection() != 'R' )
								{
									numTubeDestination = tabRbSelection[cpt];
								}

								this.ctrl.ensembleAction(this.ctrl.getActionSelection(), numTubeSource, numTubeDestination);

								if (  this.ctrl.getMessageNonJouable().length() > 0 )
								{
									JOptionPane.showMessageDialog(this, this.ctrl.getMessageNonJouable(), "Action interdite", JOptionPane.ERROR_MESSAGE);
								}
								this.ctrl.setMessageNonJouable();

							}
							else
							{
								sMessErreur = "Aucune action sélectionnée";
								JOptionPane.showMessageDialog(this, sMessErreur, "Erreur lors de la sélection", JOptionPane.ERROR_MESSAGE);
							}
						}
						else
						{
							sMessErreur = "Champ manquant";
							JOptionPane.showMessageDialog(this, sMessErreur, "Erreur lors de la sélection", JOptionPane.ERROR_MESSAGE);
						}

						if ( this.ctrl.estGagnant() )
						{
							this.optionPaneVictoire();
						}
					}
					else
					{
						this.optionPaneVictoire();
					}
				}
			}
		}

		private class PanelActionSelectionTubeDisposition extends JPanel
		{
			/**
				* Définition du serialVersionUID
			*/
			private static final long serialVersionUID = 1L;
			
			/*----------------------*/
			/* Attribut pour le jeu */
			/*----------------------*/
			private Controleur 	ctrl; // L'attribut renseigne sur le contrôleur.

			/*--------------------------------------*/
			/* Attributs pour l'affichage graphique */
			/*--------------------------------------*/
			private PanelActionSelectionTubeCreation panelActionSelectionTubeCreationSource; 			// L'attribut renseigne sur le PanelActionSelectionTubeCreationSource.
			private PanelActionSelectionTubeCreation panelActionSelectionTubeCreationDestination; 		// L'attribut renseigne sur le PanelActionSelectionTubeCreationDestination.
 
			/*-----------------*/
			/* Le Constructeur */
			/*-----------------*/

			/**
				* Constructeur PanelActionSelectionTubeDisposition
				* @param ctrl : Le contrôleur.
			*/
			public PanelActionSelectionTubeDisposition(Controleur ctrl)
			{
				/*---------------------------*/
				/* Informations sur le panel */
				/*---------------------------*/
				this.ctrl 				= ctrl;

				this.setLayout( new GridLayout() );
				this.setBackground(Color.BLUE);

				/*-------------------------*/
				/* Création des composants */
				/*-------------------------*/
				int numPanelActionSelectionTubeCreation = 1;

				this.panelActionSelectionTubeCreationSource 			= new PanelActionSelectionTubeCreation(ctrl, "Tube Source", this);
				this.panelActionSelectionTubeCreationSource.setNumPanelActionSelectionTubeCreation(numPanelActionSelectionTubeCreation++);

				if ( this.ctrl.getActionSelection() != 'R' )
				{
					this.setLayout( new GridLayout(1,2) );
					this.panelActionSelectionTubeCreationDestination 	= new PanelActionSelectionTubeCreation(ctrl, "Tube Destination", this);
					this.panelActionSelectionTubeCreationDestination.setNumPanelActionSelectionTubeCreation(numPanelActionSelectionTubeCreation);
				}

				/*-------------------------------*/
				/* Positionnement des composants */
				/*-------------------------------*/
				this.add(panelActionSelectionTubeCreationSource);
				if ( this.ctrl.getActionSelection() != 'R' )
				{
					this.add(panelActionSelectionTubeCreationSource);
					this.add(panelActionSelectionTubeCreationDestination);
				}

				this.setVisible(true);
			}

			/*--------------------------------------------------------------------------------------------------*/
			/* 											LES MÉTHODES 											*/
			/*--------------------------------------------------------------------------------------------------*/

			/**
				* @return Retourne le ou les radios boutons sélectionnés lors d'une action.
			*/ 
			public int[] getRbSelectionAction()
			{
				int[] tabRbSelection;
				int cpt = 0;
				if ( this.ctrl.getActionSelection() != 'R')
				{
					tabRbSelection = new int[this.ctrl.getNbTube()-1];
					
					tabRbSelection[cpt++] 	= this.panelActionSelectionTubeCreationSource.getNumTubeSelection();
					tabRbSelection[cpt] 	= this.panelActionSelectionTubeCreationDestination.getNumTubeSelection();
				}
				else
				{
					tabRbSelection = new int[this.ctrl.getNbTube()-(this.ctrl.getNbTube()-1)];
					tabRbSelection[cpt] = this.panelActionSelectionTubeCreationSource.getNumTubeSelection();
				}

				return tabRbSelection;
			}

			/**
				* Le radio-bouton du panel qui est soit activé ou désactivé.
				* @param numPanel : le numéro du panel.
			*/ 
			public void panelActiverDesactiverRb(int numPanel)
			{
				if ( this.panelActionSelectionTubeCreationSource.getNumTubeSelection() == -1 )
				{
					this.panelActionSelectionTubeCreationSource.setTubeActifSelection(this.panelActionSelectionTubeCreationDestination.getNumTubeActifSelection()); 
				}

				if (  this.panelActionSelectionTubeCreationDestination.getNumTubeSelection() == -1 )
				{
					this.panelActionSelectionTubeCreationDestination.setTubeActifSelection(this.panelActionSelectionTubeCreationSource.getNumTubeActifSelection());
				}
		
				if ( numPanel == this.panelActionSelectionTubeCreationSource.getNumPanelActionSelectionTubeCreation() )
				{
					this.panelActionSelectionTubeCreationDestination.setTubeActifSelection(this.panelActionSelectionTubeCreationSource.getNumTubeActifSelection());
					this.panelActionSelectionTubeCreationSource.setActionRb('A', this.panelActionSelectionTubeCreationSource.getNumTubeActifSelection());
					this.panelActionSelectionTubeCreationDestination.setActionRb('D', this.panelActionSelectionTubeCreationDestination.getNumTubeActifSelection());
				}
				else
				{
					if ( numPanel == this.panelActionSelectionTubeCreationDestination.getNumPanelActionSelectionTubeCreation() )
					{
						this.panelActionSelectionTubeCreationSource.setTubeActifSelection(this.panelActionSelectionTubeCreationDestination.getNumTubeActifSelection());
						this.panelActionSelectionTubeCreationSource.setActionRb('D', this.panelActionSelectionTubeCreationSource.getNumTubeActifSelection());
						this.panelActionSelectionTubeCreationDestination.setActionRb('A', this.panelActionSelectionTubeCreationDestination.getNumTubeActifSelection());
					}
				}
			}

			/*------------------------------*/
			/*			L'AFFICHAGE			*/
			/*------------------------------*/

			/**
				* Fixe le mode sombre.
			*/
			public void setModeSombre()
			{
				this.panelActionSelectionTubeCreationSource.setModeSombre();
				if ( this.ctrl.getActionSelection() != 'R' || this.panelActionSelectionTubeCreationDestination != null )
				{
					this.panelActionSelectionTubeCreationDestination.setModeSombre();
				}
				this.setBackground(Color.DARK_GRAY);
			}

			/**
				* Fixe le mode clair.
			*/
			public void setModeClair()
			{
				this.panelActionSelectionTubeCreationSource.setModeClair();
				if ( this.ctrl.getActionSelection() != 'R' || this.panelActionSelectionTubeCreationDestination != null )
				{
					this.panelActionSelectionTubeCreationDestination.setModeClair();
				}
				this.setBackground(Color.WHITE);
			}

			/*----------------------------------*/
			/*			LA FERMETURE			*/
			/*----------------------------------*/
		
			/**
				* Ferme le Panel Action Selection Tube Disposition.
			*/
			public void fermerPanelActionSelectionTubeDisposition()
			{
				this.panelActionSelectionTubeCreationSource.fermerPanelActionSelectionTubeCreation();
				if ( this.ctrl.getActionSelection() != 'R' || this.panelActionSelectionTubeCreationDestination != null )
				{
					this.panelActionSelectionTubeCreationDestination.fermerPanelActionSelectionTubeCreation();
				}
				this.removeAll();
				this.setVisible(false);
			}
		}

		private class PanelActionSelectionTubeCreation extends JPanel implements ItemListener
		{
			/**
				* Définition du serialVersionUID
			*/
			private static final long serialVersionUID = 1L;
			
			/*-----------------------*/
			/* Attributs pour le jeu */
			/*-----------------------*/
			private Controleur ctrl; 															// L'attribut renseigne sur le contrôleur.
			private PanelActionSelectionTubeDisposition  panelActionSelectionTubeDisposition;	// L'attribut renseigne sur le panel action selection tube disposition.
			private int numPanelActionSelectionTubeCreation;									// L'attribut renseigne sur le numéro du panel action selection tube creation.
			private int numTubeSelection; 														// L'attribut renseigne sur le numéro du tube sélectionné.
			private int numTubeActifSelection; 													// L'attribut renseigne sur le numéro du tube actif sélectionné.

			/*--------------------------------------*/
			/* Attributs pour l'affichage graphique */
			/*--------------------------------------*/
			private JLabel 				lblTitre; 		// L'attribut renseigne sur le label titre.
			private ButtonGroup 		bgSelection; 	// L'attribut renseigne sur le regroupement des radios-boutons.
			private JRadioButton[] 		rbTube; 		// L'attribut renseigne sur les radios boutons concernant les tubes.
			private JPanel 				panelTitre; 	// L'attribut renseigne sur le panelTitre.
			private JPanel 				panelEnsRb;		// L'attribut renseigne sur le panel contenant l'ensemble des radios boutons.
 
			/*-----------------*/
			/* Le Constructeur */
			/*-----------------*/

			/**
				* Constructeur PanelActionSelectionTubeCreation
				* @param ctrl : Le contrôleur.
				* @param titreLabel : Le titre du tube.
				* @param panelActionSelectionTubeDisposition : Le panel action selection tube disposition. 
			*/
			public PanelActionSelectionTubeCreation(Controleur ctrl, String titreLabel, PanelActionSelectionTubeDisposition panelActionSelectionTubeDisposition)
			{
				/*---------------------------*/
				/* Informations sur le panel */
				/*---------------------------*/
				this.ctrl = ctrl;
				this.panelActionSelectionTubeDisposition = panelActionSelectionTubeDisposition;

				this.numPanelActionSelectionTubeCreation = 0;
				this.numTubeSelection = -1;
				this.numTubeActifSelection = -1;

				this.setLayout( new BorderLayout() );
				this.setBackground(Color.WHITE);

				/*--------------------*/
				/* La police utilisée */
				/*--------------------*/
				Font policeLblTitreRbTube = new Font("Calibri", Font.BOLD, 17);

				/*-------------------------*/
				/* Création des composants */
				/*-------------------------*/
				this.lblTitre 		= new JLabel(titreLabel);
				this.lblTitre.setFont(policeLblTitreRbTube);

				this.bgSelection 	= new ButtonGroup();
				this.rbTube 		= new JRadioButton[this.ctrl.getNbTube()];
				for(int cpt=0; cpt<this.ctrl.getNbTube(); cpt++)
				{
					this.rbTube[cpt] = new JRadioButton("Tube " + (cpt+1));
					this.rbTube[cpt].setForeground(Color.BLACK);
					this.rbTube[cpt].setFont(policeLblTitreRbTube);
					this.rbTube[cpt].setOpaque(false);
					this.bgSelection.add(this.rbTube[cpt]);
				}

				this.panelTitre = new JPanel();
				this.panelTitre.setLayout( new FlowLayout() );
				this.panelTitre.setBackground(Color.WHITE);

				this.panelEnsRb = new JPanel();
				this.panelEnsRb.setLayout( new FlowLayout() );
				this.panelEnsRb.setBackground(Color.WHITE);

				/*-------------------------------*/
				/* Positionnement des composants */
				/*-------------------------------*/
				this.panelTitre.add(this.lblTitre, JLabel.CENTER);

				for(int cpt=0; cpt<this.ctrl.getNbTube(); cpt++)
				{
					this.panelEnsRb.add(this.rbTube[cpt]);
				}

				this.add(this.panelTitre, BorderLayout.NORTH);
				this.add(this.panelEnsRb,  BorderLayout.CENTER);

				/*---------------------------*/
				/* Activation des composants */
				/*---------------------------*/
				for(int cpt=0; cpt<this.ctrl.getNbTube(); cpt++)
				{
					this.rbTube[cpt].addItemListener(this);
				}

				this.setVisible(true);
			}

			/*--------------------------------------------------------------------------------------------------*/
			/* 											LES MÉTHODES 											*/
			/*--------------------------------------------------------------------------------------------------*/

			/**
				* @return Retourne le numéro du tube sélectionné.
			*/
			public int getNumTubeSelection()
			{
				return this.numTubeSelection;
			}

			/**
				* @return Retourne le numéro du tube actif sélectionné.
			*/
			public int getNumTubeActifSelection()
			{
				return this.numTubeActifSelection;
			}

			/**
				* Affecte au tube actif le numéro du radio-bouton selectionné.
				* @param numRbSelection : numéro du radio-bouton selectionné.
			*/
			public void setTubeActifSelection(int numRbSelection)
			{
				if ( numRbSelection >= 0 && numRbSelection < this.ctrl.getNbTube() )
				{
					this.numTubeActifSelection = numRbSelection;
				}
			}

			/**
				* Réactivation des radios-boutons.
			*/
			public void reactiverRb()
			{
				for(int cpt=0; cpt<this.ctrl.getNbTube(); cpt++)
				{
					this.rbTube[cpt].setEnabled(true);
				}
			}

			/**
				* Affecte une action au radio-bouton sélectionné.
				* @param actionType : l'action du radio-bouton soit 'A' pour activer ou bien 'D' pour désactiver.
				* @param numRb : le numéro du radio-bouton. 
			*/
			public void setActionRb(char actionType, int numRb)
			{
				this.reactiverRb();

				if ( numRb >= 0 && numRb < this.ctrl.getNbTube() )
				{
					if ( actionType == 'A' || actionType == 'D' )
					{
						switch(actionType)
						{
							case 'A':
								this.rbTube[numRb].setEnabled(true);
							break;

							case 'D':
								this.rbTube[numRb].setEnabled(false);
							break;
						}
					}
				}
			}

			/**
				* @return Retourne le numéro du panel.
			*/
			public int getNumPanelActionSelectionTubeCreation()
			{
				return this.numPanelActionSelectionTubeCreation;
			}

			/**
				* Affecte un numéro au panel.
				* @param numPanel : le numéro affecté au panel.
			*/
			public void setNumPanelActionSelectionTubeCreation(int numPanel)
			{
				if ( numPanel > 0 && numPanel < this.ctrl.getNbTube() )
				{
					this.numPanelActionSelectionTubeCreation = numPanel;
				}
			}

			/*------------------------------*/
			/*			L'AFFICHAGE			*/
			/*------------------------------*/

			/**
				* Fixe le mode sombre.
			*/
			public void setModeSombre()
			{
				this.panelTitre.setBackground(Color.DARK_GRAY);
				this.panelEnsRb.setBackground(Color.DARK_GRAY);
				this.lblTitre.setForeground(Color.WHITE);
				for(int cpt=0; cpt<this.ctrl.getNbTube(); cpt++)
				{
					this.rbTube[cpt].setForeground(Color.WHITE);
				}
				this.setBackground(Color.DARK_GRAY);
			}

			/**
				* Fixe le mode clair.
			*/
			public void setModeClair()
			{
				this.panelTitre.setBackground(Color.WHITE);
				this.panelEnsRb.setBackground(Color.WHITE);
				this.lblTitre.setForeground(Color.BLACK);
				for(int cpt=0; cpt<this.ctrl.getNbTube(); cpt++)
				{
					this.rbTube[cpt].setForeground(Color.BLACK);
				}
				this.setBackground(Color.WHITE);
			}

			/*----------------------------------*/
			/*			LA FERMETURE			*/
			/*----------------------------------*/
		
			/**
				* Ferme le Panel Action Selection Tube Creation.
			*/
			public void fermerPanelActionSelectionTubeCreation()
			{
				this.removeAll();
				this.setVisible(false);
			}
			
			/*----------------------------------------------*/
			/*			L'ÉVÈNEMENT ITEM-LISTENER			*/
			/*----------------------------------------------*/

			/**
				* Si le joueur sélectionne le bouton radio de l'un des tubes.
			*/
			public void itemStateChanged(ItemEvent e)
			{
				for(int cpt=0; cpt<this.ctrl.getNbTube(); cpt++)
				{
					if ( e.getSource() == this.rbTube[cpt] )
					{
						if ( this.rbTube[cpt].isSelected() )
						{ 
							this.numTubeSelection = this.numTubeActifSelection = cpt;

							if ( this.ctrl.getActionSelection() != 'R')
							{
								this.panelActionSelectionTubeDisposition.panelActiverDesactiverRb(this.getNumPanelActionSelectionTubeCreation());
							}
						}
					}
				}
			}
		}
	}
}