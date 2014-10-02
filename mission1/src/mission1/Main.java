package mission1;

import java.util.Scanner;
import java.util.EmptyStackException;



public class Main {

	// instructions.

	/*
	 * Fonction qui va lire le document donne en argument. Chaque element separe
	 * par un espace sera un element correct si : - c'est un double ou un
	 * int(dans ce cas cast en double) ==> simple ajout. - c'est une nouvelle
	 * definition (string precede d'un "/") ==> creation d'un nouvel objet
	 * Constante. - c'est un token 'pop' ==> le premier object de la pile saute
	 * - c'est un token 'pstack' ==> la pile est imprimee sur la console et
	 * reste inchangee. - c'est un des token suivants :
	 * 'mul','add','div,'sub','exch,'dup','eq,'ne' ==> Utilisation de
	 * sous-fonctions pour effectuer l'operation voulue. - c'est une constante
	 * deja existante(sous forme d'objet) ==> la valeur de cette Constante est
	 * alors directement ajoutée sur la pile. Sinon le programme s'arrete, il y
	 * a une erreur dans les valeurs d'entree.
	 * 
	 * @pre : fileName : Contient un string du nom du document ou se trouve les
	 * insctructions. Le document est valide.
	 * 
	 * @post : Initialise la pile 'pile' avec ce qu'il y a dans le document et
	 * cree les objects Constant au besoin.
	 */
	public static void main(String[] args) {
		NodeStack<StackingObject> pile = new NodeStack<StackingObject>(); // Pile ou on va
																// empiler les
	// lecture du fichier texte
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Veuillez saisir le path vers le fichier :");
			String fileName = sc.nextLine();
			System.out.println("Vous avez saisi : " + fileName);
			sc.close();

			IO.openReader(fileName);
			String str[];
			int nl = 1;

			while ((str = IO.readInstruction()) != null) {
				for (int i = 0; i < str.length; i++) {
					try {
						double a = Double.parseDouble(str[i]);
						StackingObject obj = new StackingObject(a);
						pile.push(obj);
					} catch (NumberFormatException e) {
						/*if (str[i].charAt(0) == '/') { // il faut alors faire
														// une definition
							new Constante(str[i], str[i + 1]); // on cree le
																// nouvel objet
																// constante.
							i++;
						}*/
						if (str[i].equalsIgnoreCase("pop")) {
							pile.pop();
						} else if (str[i].equalsIgnoreCase("pstack")) { // on
																		// imprime
																		// la
																		// pile.
							System.out.println(pile.toString());
						} else if (str[i].equalsIgnoreCase("mul")) {
							try {
								mul(pile);
							} catch (Exception e1) {
								System.out.println("erreur � la ligne n�" + nl + "\n" + e1);
							}
						} else if (str[i].equalsIgnoreCase("add")) {
							try {
								add(pile);
							} catch (Exception e1) {
								System.out.println("erreur � la ligne n�" + nl + "\n" + e1);
							}
						} else if (str[i].equalsIgnoreCase("sub"))
							try {
								sub(pile);
							} catch (Exception e1) {
								System.out.println("erreur � la ligne n�" + nl + "\n" + e1);
							}
						else if (str[i].equalsIgnoreCase("div"))
							try {
								div(pile);
							} catch (Exception e1) {
								System.out.println("erreur � la ligne n�" + nl + "\n" + e1);
							}
						else if (str[i].equalsIgnoreCase("dup"))
							try {
								dup(pile);
							} catch (Exception e1) {
								System.out.println("erreur � la ligne n�" + nl + "\n" + e1);
							}
						else if (str[i].equalsIgnoreCase("exch"))
							try {
								exch(pile);
							} catch (Exception e1) {
								System.out.println("erreur � la ligne n�" + nl + "\n" + e1);
							}
						else if (str[i].equalsIgnoreCase("eq"))
							try {
								eq(pile);
							} catch (Exception e1) {
								System.out.println("erreur � la ligne n�" + nl + "\n" + e1);
							}
						else if (str[i].equalsIgnoreCase("ne"))
							try {
								ne(pile);
							} catch (Exception e1) {
								System.out.println("erreur � la ligne n�" + nl + "\n" + e1);
							}
						else if (str[i].equalsIgnoreCase("true")) {
							boolean val = true;
							StackingObject obj = new StackingObject(val);
							pile.push(obj);
							
						}
						else if (str[i].equalsIgnoreCase("false")) {
							boolean val = false;
							StackingObject obj = new StackingObject(val);
							pile.push(obj);
						}
						/*else { // Definition deja faite ou bug;
							try {
								double a = Constante.searchNumb(str[i]);
								StackingObject obj = new StackingObject(a);
								pile.push(obj);
							} catch (Exception e1) {
								System.out.println("erreur � la ligne n�" + nl + "\n" + e1);
							}
						}*/
					}
				}
			nl++;
			}
			IO.closeReader();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	/*
	 * @pre le premier element de la pile 'pile' est un double
	 * Sinon une erreur est lancee
	 * 
	 * @post renvoie le premier element de la pile si c est un double
	 */
	public static double getDoubleTop(NodeStack<StackingObject>pile){
		if (pile.top().isDouble())
			return pile.pop().getDouble();
		else
			throw new NumberFormatException();
	}

	/*
	 * @pre : les deux premiers elements de la pile 'pile' sont deux doubles.
	 * Sinon une erreur est lancee
	 * 
	 * @post : les deux elements de la pile 'pile' sont remplaces par un element
	 * qui vaut leur quotient (premier sur le second).
	 */
	public static void div(NodeStack<StackingObject>pile) {
		double a;
		a = getDoubleTop(pile);
		double b;
		b=getDoubleTop(pile);
		double c = a / b;
		StackingObject obj = new StackingObject(c);
		pile.push(obj);
	}


	/*
	 * @pre : les deux premiers elements de la pile 'pile' sont deux doubles.
	 * Sinon une erreur est lancee
	 * 
	 * @post : les deux elements de la pile 'pile' sont remplaces par un element
	 * qui vaut leur produit.
	 */
	public static void mul(NodeStack<StackingObject>pile) {
		double a;
		a=getDoubleTop(pile);
		double b;
		b=getDoubleTop(pile);
		double c = a * b;
		StackingObject obj = new StackingObject(c);
		pile.push(obj);
	}

	/*
	 * @pre : les deux premiers elements de la pile 'pile' sont deux doubles.
	 * Sinon une erreur est lancee
	 * 
	 * @post : les deux elements de la pile 'pile' sont remplaces par un element
	 * qui vaut leur somme.
	 */
	public static void add(NodeStack<StackingObject>pile) {
		double a;
		a=getDoubleTop(pile);
		double b;
		b=getDoubleTop(pile);
		double c = a + b;
		StackingObject obj = new StackingObject(c);
		pile.push(obj);
	}

	/*
	 * @pre : les deux premiers elements de la pile 'pile' sont deux doubles.
	 * Sinon une erreur est lancee
	 * 
	 * @post : les deux elements de la pile 'pile' sont remplaces par un element
	 * qui vaut leur dividende(??).
	 */
	public static void sub(NodeStack<StackingObject>pile) {
		double a;
		a=getDoubleTop(pile);
		double b;
		b=getDoubleTop(pile);
		double c = a / b;
		StackingObject obj = new StackingObject(c);
		pile.push(obj);
	}

	/*
	 * @pre : le premier element de la pile 'pile' est un double. Sinon une
	 * erreur est lancee.
	 * 
	 * @post : le premier element de la pile est dedouble au sommet de la pile.
	 */
	public static void dup(NodeStack<StackingObject>pile) {
		if (pile.top().isDouble()) {
			double a = pile.pop().getDouble();
			StackingObject obj = new StackingObject(a);
			pile.push(obj);
			pile.push(obj);
		}
		else {
			boolean b = pile.pop().getBoolean();
			StackingObject obj = new StackingObject(b);
			pile.push(obj);
			pile.push(obj);
		}
	}

	/*
	 * @pre : les deux premiers elements de la pile 'pile' sont deux doubles.
	 * Sinon une erreur est lancee
	 * 
	 * @post : l'ordre des deux premiers elements de la pile 'pile' est inverse.
	 */
	public static void exch(NodeStack<StackingObject>pile) {
		double a = 0;
		boolean bool1 = false;
		boolean isBool1;
		if (pile.top().isDouble()) {
			a = pile.pop().getDouble();
			isBool1 = false;
		}
		else {
			bool1 = pile.pop().getBoolean();
			isBool1 = true;
		}
		
		double b = 0;
		boolean bool2 = false;
		boolean isBool2;
		if (pile.top().isDouble()) {
			b = pile.pop().getDouble();
			isBool2 = false;
		}
		else {
			bool2 = pile;pop().getBoolean();
			isBool2 = true;
		}
		
		StackingObject obj1;
		StackingObject obj2;
		
		if (isBool1) {
			obj1 = new StackingObject(bool1);
		}
		else {
			obj1 = new StackingObject(a);
		}
		if (isBool2) {
			obj2 = new StackingObject(bool2);
		}
		else {
			obj2 = new StackingObject(b);
		}
		pile.push(obj1);
		pile.push(obj2);
	}

	/*
	 * @pre : les deux premiers elements de la pile 'pile' sont deux doubles.
	 * Sinon une erreur est lancee
	 * 
	 * @post : les deux premiers elements de la pile 'pile' sont remplaces par
	 * un boolean : - true si ils sont egaux - false sinon
	 */
	public static void eq(NodeStack<StackingObject>pile) {
		boolean c;
		double a = 0;
		boolean bool1 = false;
		boolean isBool;
		if (pile.top().isDouble()) {
			a = pile.pop().getDouble();
			isBool = false;
		}	
		else {
			bool1 = pile.pop().getBoolean();
			isBool = true;
		}
		double b;
		boolean bool2;
		if (pile.top().isDouble())
			if (!isBool) {	
				b = pile.pop().getDouble();
				c = a==b;
			}	
			else 
				throw new NumberFormatException();
		else
			if (isBool) {
				bool2 = pile.pop().getBoolean();
				c= bool1 == bool2;
			}
			else 
				throw new NumberFormatException();
		StackingObject obj = new StackingObject(c);
		pile.push(obj);
	}

	/*
	 * @pre : les deux premiers elements de la pile 'pile' sont deux doubles.
	 * Sinon une erreur est lancee
	 * 
	 * @post : les deux premiers elements de la pile 'pile' sont remplaces par
	 * un boolean : - false si ils sont egaux - true sinon
	 */
	public static void ne(NodeStack<StackingObject>pile) {
		boolean c;
		double a = 0;
		boolean bool1 = false;
		boolean isBool;
		if (pile.top().isDouble()) {
			a = pile.pop().getDouble();
			isBool = false;
		}	
		else {
			bool1 = pile.pop().getBoolean();
			isBool = true;
		}
		double b;
		boolean bool2;
		if (pile.top().isDouble())
			if (!isBool) {	
				b = pile.pop().getDouble();
				c = a!=b;
			}	
			else 
				throw new NumberFormatException();
		else
			if (isBool) {
				bool2 = pile.pop().getBoolean();
				c= bool1 != bool2;
			}
			else 
				throw new NumberFormatException();
		StackingObject obj = new StackingObject(c);
		pile.push(obj);
	}
}
