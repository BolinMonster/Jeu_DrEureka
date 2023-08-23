package sources.metier;

/*----L'-import-pour-la-serialisation----*/
import java.io.Serializable;


/**
	* Classe PileCaractere
	* @author 	: -
	* @version 	: 1.0
	* date 		! 28/07/2020
*/

public class PileCaractere implements Serializable
{
	/*---------------------------------------REPRÉSENTATION-D'UNE-PILE----------------------------------------------*/

				/****************************************************************************

											
							Empiler ->	|							|	<- Dépiler 
										|							|
										|							|
										|							|
										|	[&&&&&&&&&&&&&&&&&&&]	|	<- Sommet
										|							|
										|***************************|
										|							|
										|	[&&&&&&&&&&&&&&&&&&&]	|
										|							|
										|***************************|
										|							|
										|	[&&&&&&&&&&&&&&&&&&&]	|
										|							|
										|***************************|
										|							|
										|	[&&&&&&&&&&&&&&&&&&&]	|
										|							|
										|***************************|
										|							|
										|	[&&&&&&&&&&&&&&&&&&&]	|
										|							|
										|***************************|
										|___________________________|


				****************************************************************************/

	/*--------------------------------------------------------------------------------------------------------------*/

	/*---------------*/
	/* Les Attributs */
	/*---------------*/

	/**
		* Définition du serialVersionUID
	*/
	private static final long serialVersionUID = 1L;


	/**
		* Les éléments de la pile
	*/
	private char[] ensCaractere; 	// L'attribut renseigne sur les billes de la pile.
	private int sommet;				// L'attribut renseigne sur le sommet de la pile.

	/*-----------------*/
	/* Le Constructeur */
	/*-----------------*/

	/**
		* Constructeur PileCaractere
		* À l'instanciation d'un objet PileCaractere(int nbEltTotal), une pile est créée.
		* @param nbEltTotal : nombre d'éléments de la pile.
	*/
	private PileCaractere(int nbEltTotal)
	{
		this.ensCaractere = new char[nbEltTotal];
		this.sommet = -1;	
	}

	/**
		* @return Design Factory afin de vérifier le paramètre.
		* @param nbEltTotal : nombre d'éléments de la pile.
	*/
	public static PileCaractere creerPileCaractere(int nbEltTotal)
	{
		if ( nbEltTotal <= 0 )
		{
			return null;
		}

		return new PileCaractere(nbEltTotal);
	}

	/**
		* @return Retourne le numéro du sommet de la pile.
	*/
	public int getSommet()
	{
		return this.sommet;
	}

	/**
		* @return Retourne le caractère au sommet de la pile sans modifier celle-ci.
	*/
	public char getCaractereSommet()
	{
		if ( this.sommet == -1 )
		{
			return ' ';
		}
		return this.ensCaractere[this.sommet];
	}

	/**
		* @return Retourne le nombre de caractère(s) dans la pile.
	*/
	public int getNbCaracTotal()
	{
		return this.ensCaractere.length;
	}

	/**
		* @return Retourne true : si la pile est vide / false : si la pile n'est pas vide.
	*/
	public boolean estVide()
	{
		return this.sommet == - 1;
	}

	/**
		* @return Retourne true : si la pile contient que des caractères qui sont '.' / false : si la pile contient pas que des caractères qui sont '.'
	*/
	public boolean estVideCaractere()
	{
		boolean bVide = false;

		for(int cpt=this.ensCaractere.length-1; cpt>=0; cpt--)
		{
			if ( this.ensCaractere[cpt] == '.' )
			{
				bVide = true;
			}
			else
			{
				bVide = false;
			}
		}

		return bVide;
	}

	/**
		* @return Retourne true : si la pile est pleine / false : si la pile n'est pas pleine.
	*/
	public boolean estPleine()
	{
		return this.sommet == this.ensCaractere.length - 1;
	}

	/**
		* @return Retourne true : si la pile est pleines de caractères qui ne sont pas des '.' / false : si la pile n'est pas pleines de de caractères qui ne sont pas des '.'.
	*/
	public boolean estPleineCaractere()
	{
		return (this.sommet == this.ensCaractere.length - 1) && (this.ensCaractere[this.sommet] != '.');
	}

	/**
		* @return Retourne true : si l'action empiler a été effectué / false : si l'action empiler n'a pas été effectué.
		* @param couleurBille : la couleur de la bille.
	*/
	public boolean empiler(char couleurBille)
	{
		if( this.estPleine() )
		{
			return false;
		}

		this.ensCaractere[++this.sommet] = couleurBille;
		return true;
	}

	/**
		* @return Retourne le nom d'une carte du sommet de la pile.
	*/
	public char depiler()
	{
		return this.ensCaractere[this.sommet--];
	}

	/**
		* Retourne la pile.
	*/
	public void retourner()
	{
		PileCaractere tmp = new PileCaractere(this.ensCaractere.length);

		while( ! this.estVide() )
		{
			tmp.empiler( this.depiler() );
		}

		this.ensCaractere = tmp.ensCaractere;
		this.sommet = tmp.sommet;
	}

	/**
		* Retourne la pile mais avec les caractères '.' qui sont pris en compte.
	*/
	public void retournerCaractere()
	{
		PileCaractere tmp = new PileCaractere(this.ensCaractere.length);
		int nbCaracPoint = 0;

		while( ! this.estVide() )
		{
			char caracDepiler =  this.depiler();

			if ( caracDepiler != '.' )
			{
				tmp.empiler( caracDepiler );
			}
			else
			{
				nbCaracPoint++;
			}

		}

		for(int cpt=0; cpt<nbCaracPoint; cpt++)
		{
			tmp.empiler( '.' );
		}

		this.ensCaractere = tmp.ensCaractere;
		this.sommet = tmp.sommet;
	}

	/**
		* Mélange les caractères de la pile
	*/
	public void melanger()
	{
		int cpt1=0;
		int cpt2=0;

		int nbCaractere = this.ensCaractere.length;

		while(cpt1<nbCaractere)
		{
			cpt2 = (int) (Math.random() * this.ensCaractere.length);

			while(cpt2<this.ensCaractere.length-cpt1-1)
			{
				char tmp 					= this.ensCaractere[cpt2];
				this.ensCaractere[cpt2] 	= this.ensCaractere[cpt1];
				this.ensCaractere[cpt1] 	= tmp;

				cpt2++;
			}
			cpt1++;
		}
	}

	/**
		* Remplace un caractère vide par un caractère.
		* @param couleurBille : la couleur de la bille.
	*/
	public void remplacerVide(char couleurBille)
	{
		int numVide = 0;
		for (int cpt=0; cpt<this.ensCaractere.length; cpt++) 
		{
			if ( this.ensCaractere[cpt] == '\u0000')
			{
				numVide = cpt;
			}
		}

		this.ensCaractere[numVide] = couleurBille;
	}

	/**
		* @return Retourne true : si la pile de caractères est égal à l'autre pile de caractères / false : la pile de caractères n'est pas égal à l'autre pile de caractères.
		* @param autrePile : l'autre pile.
	*/
	public boolean equals(PileCaractere autrePile)
	{
		if (this.toString().equals(autrePile.toString()) )
		{
			return true;
		}

		return false;
	}

	/**
		* @return Affiche la pile.
	*/
	public String toString()
	{
		String sRet = "";
		for(int cpt=0; cpt<this.sommet+1; cpt++)
		{
			sRet += this.ensCaractere[cpt];
		}
		return sRet;
	}
}