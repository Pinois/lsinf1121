import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Stack;

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
		Stack<StackingObject> pile = new Stack<StackingObject>(); // Pile ou on va
																// empiler les
	// lecture du fichier texte
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Veuillez saisir le path vers le fichier :");
			String fileName = sc.nextLine();
			System.out.println("Vous avez saisi : " + fileName);
			sc.close();

			InputStream ips = new FileInputStream(fileName);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;

			while ((ligne = br.readLine()) != null) {
				String str[] = ligne.split(" ");
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
						if (str[i].compareTo("pop") == 0) {
							pile.pop();
						} else if (str[i].compareTo("pstack") == 0) { // on
																		// imprime
																		// la
																		// pile.
							System.out.println(pile.toString());
						} else if (str[i].compareTo("mul") == 0) {
							try {
								mul(pile);
							} catch (Exception e1) {
								System.out.println(e1);
							}
						} else if (str[i].compareTo("add") == 0) {
							try {
								add(pile);
							} catch (Exception e1) {
								System.out.println(e1);
							}
						} else if (str[i].compareTo("sub") == 0)
							try {
								sub(pile);
							} catch (Exception e1) {
								System.out.println(e1);
							}
						else if (str[i].compareTo("div") == 0)
							try {
								div(pile);
							} catch (Exception e1) {
								System.out.println(e1);
							}
						else if (str[i].compareTo("dup") == 0)
							try {
								dup(pile);
							} catch (Exception e1) {
								System.out.println(e1);
							}
						else if (str[i].compareTo("exch") == 0)
							try {
								exch(pile);
							} catch (Exception e1) {
								System.out.println(e1);
							}
						else if (str[i].compareTo("eq") == 0)
							try {
								eq(pile);
							} catch (Exception e1) {
								System.out.println(e1);
							}
						else if (str[i].compareTo("ne") == 0)
							try {
								ne(pile);
							} catch (Exception e1) {
								System.out.println(e1);
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
								System.out.println(e1);
							}
						}*/
					}
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/*
	 * @pre : les deux premiers elements de la pile 'pile' sont deux doubles.
	 * Sinon une erreur est lancee
	 * 
	 * @post : les deux elements de la pile 'pile' sont remplaces par un element
	 * qui vaut leur quotient (premier sur le second).
	 */
	public static void div(Stack<StackingObject>pile) {
		double a;
		if (pile.peek().isDouble())
			a = pile.pop().getDouble();
		else
			throw new NumberFormatException();
		double b;
		if (pile.peek().isDouble())
			b = pile.pop().getDouble();
		else
			throw new NumberFormatException();
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
	public static void mul(Stack<StackingObject>pile) {
		double a;
		if (pile.peek().isDouble())
			a = pile.pop().getDouble();
		else
			throw new NumberFormatException();
		double b;
		if (pile.peek().isDouble())
			b = pile.pop().getDouble();
		else
			throw new NumberFormatException();
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
	public static void add(Stack<StackingObject>pile) {
		double a;
		if (pile.peek().isDouble())
			a = pile.pop().getDouble();
		else
			throw new NumberFormatException();
		double b;
		if (pile.peek().isDouble())
			b = pile.pop().getDouble();
		else
			throw new NumberFormatException();
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
	public static void sub(Stack<StackingObject>pile) {
		double a;
		if (pile.peek().isDouble())
			a = pile.pop().getDouble();
		else
			throw new NumberFormatException();
		double b;
		if (pile.peek().isDouble())
			b = pile.pop().getDouble();
		else
			throw new NumberFormatException();
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
	public static void dup(Stack<StackingObject>pile) {
		if (pile.peek().isDouble()) {
			double a = pile.pop().getDouble();
			StackingObject obj = new StackingObject(a);
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
	public static void exch(Stack<StackingObject>pile) {
		double a;
		if (pile.peek().isDouble())
			a = pile.pop().getDouble();
		else
			throw new NumberFormatException();
		double b;
		if (pile.peek().isDouble())
			b = pile.pop().getDouble();
		else
			throw new NumberFormatException();
		StackingObject obj1 = new StackingObject(a);
		StackingObject obj2 = new StackingObject(b);
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
	public static void eq(Stack<StackingObject>pile) {
		boolean c;
		double a;
		boolean bool1;
		boolean isBool;
		if (pile.peek().isDouble()) {
			a = pile.pop().getDouble();
			isBool = false;
		}	
		else {
			bool1 = pile.pop().getBoolean();
			isBool = true;
		}
		double b;
		boolean bool2;
		if (pile.peek().isDouble())
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
	public static void ne(Stack<StackingObject>pile) {
		double a;
		if (pile.peek().isDouble())
			a = pile.pop().getDouble();
		else
			throw new NumberFormatException();
		double b;
		if (pile.peek().isDouble())
			b = pile.pop().getDouble();
		else
			throw new NumberFormatException();
		boolean c = (a != b);
		StackingObject obj = new StackingObject(c);
		pile.push(obj);
	}

}
