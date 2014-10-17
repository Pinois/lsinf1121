package mission2;

import java.io.IOException;
import java.util.EmptyStackException;

public class Main {
	public static void main(String[]args) throws Exception{

		if(args.length!=1){
			System.err.println("Nombre d'arguments incorrect");
			return;
		}

		LinkedRBinaryTree<?> tree=null;
		try{
			IO.openReader(args[0]);
			String line;

			// On parcourt toutes les expressions du fichier une a une
			while((line = IO.readInstruction()) != null)
			{
				try{
					// Tranformation en arbre
					tree=new LinkedRBinaryTree(line);

					// Derivation
					LinkedRBinaryTree<?> d=tree.derive();
					System.out.println("Expression initiale : "+tree+"\n"+"Expression derivee : "+d);
					System.out.println("----------------------------------------------------------");
				}
				catch(IllegalInputException a){
					System.err.println("Erreur lors de la conversion de la chaine de caracteres en arbre");
					System.err.println("Details : "+a.getMessage());
					System.err.println("----------------------------------------------------------");
				}
				catch(EmptyStackException p){
					System.err.println("Erreur : operation pop sur une pile vide. Cela peut etre du a une expression illegale");
					System.err.println("----------------------------------------------------------");
				}
				catch(ClassCastException c){
					System.err.println("Erreur : On n'applique pas les derive d'exposant de fonctions");
					System.err.println("----------------------------------------------------------");
				}
			}
		}
		catch(IOException e){
			System.err.println("Erreur lors de la lecture du fichier d'entree");
			System.err.println("Details : "+e.getMessage());
			System.exit(1);
		}
	}
}
