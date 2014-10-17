package mission2;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

/**
 * Implementation des arbres d'expression sous forme de structure
 * chainee
 * @param <E> le type de l'element de la racine de l'instance courante
 * @author Ludovic Fastré
 */
public class LinkedRBinaryTree<E> implements RBinaryTree<E>, FormalExpressionTree {

	/** Racine de l'arbre, contient un element de type E*/
	private Position<E> root;

	/**Sous arbre de gauche*/
	private LinkedRBinaryTree<?> leftChild;

	/**Sous arbre de droite*/
	private LinkedRBinaryTree<?> rightChild;

	// initialisation des piles
	private Stack<LinkedRBinaryTree<?>> opStack=new Stack<LinkedRBinaryTree<?>>();
	private Stack<LinkedRBinaryTree<?>> valueStack = new Stack<LinkedRBinaryTree<?>>();
	private int negative = 0;

	/**
	 * Constructeur initialisant toutes les variables d'instance a null
	 */
	public LinkedRBinaryTree() {
		this.root = null;
		this.leftChild = null;
		this.rightChild = null;
	}

	/**
	 * Constructeur ou on speficie, en arguments, la racine et les
	 * sous-arbres de gauche et droite respectivement
	 */
	public LinkedRBinaryTree(Position<E> root, LinkedRBinaryTree<?> leftChild, LinkedRBinaryTree<?> rightChild) {
		this.root = root;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	/**
	 * Constructeur qui cree un arbre a une racine mais dont les
	 * 2 sous-arbres sont null
	 */
	public LinkedRBinaryTree(Position<E> root) {
		this.root=root;
		this.leftChild=null;
		this.rightChild=null;
	}

	/**
	 * Constructeur qui crée un arbre à partir d'un string
	 */
	public LinkedRBinaryTree(String s) throws EmptyStackException, IllegalInputException{

		// caractère courant
		char current;

		// Condition de boucle
		boolean loop=true;

		for(int i=0;i<s.length() && loop;i++){
			current=s.charAt(i);

			// Operateurs arithmetiques
			if(current=='+' || current=='-' || current=='*' || current=='/' || current=='^'){
				opStack.push(new LinkedRBinaryTree<Character>(new Node<Character>(current)));
			}
			else if(current=='s' || current=='c'){ // sin ou cos
				String r=s.substring(i,i+3);
				if(r.equals("cos")){ // cosinus
					opStack.push(new LinkedRBinaryTree<String>(new Node<String>("cos")));
				}
				else if(r.equals("sin")){ // sinus
					opStack.push(new LinkedRBinaryTree<String>(new Node<String>("sin")));
				}
				else{ // chaine commancant par c ou s mais illegale
					throw new IllegalInputException("Operateur inconnu commencant par "+current);
				}
				i=i+2;
			}
			else if(current=='x'){ // variable analytique
				valueStack.push(new LinkedRBinaryTree<Character>(new Node<Character>('x')));
			}
			else if(Character.isDigit(current)){ // nombre
				i=parseInt(s,i);
				if(i<0){
					loop=false;
				}
			}
			else if(current=='('){
				/* On ne fait rien dans le cas de la parenthese entrante*/
			}
			else if(current==')'){ // parenthese fermante
				compute();
			}
			else if(current=='~'){ // signe de negation (different de '-')
				negative++;
			}
			else{
				throw new IllegalInputException("Caractere illegal");
			}
		}

		// vérification sur les piles
		boolean valid=valueStack.size()==1 && opStack.size()==0;
		if(!valid){
			throw new IllegalInputException("Cette chaine n'a pas pu etre transformee en arbre");
		}

		LinkedRBinaryTree<?> parsedTree = valueStack.pop();
		this.root=(Position<E>) parsedTree.root();
		this.leftChild=parsedTree.leftTree();
		this.rightChild=parsedTree.rightTree();
	}

	/**
	 * @pre s!=null && i>=0 && i<s.length()
	 * @post place l'entier en tete de s dans la pile des valeurs et
	 *       renvoie le nouvel indice de lecture sur le caractere qui
	 * 		 est sur le dernier chiffre 
	 * 		(ou -1 si c'est la fin de la chaine)
	 */
	private int parseInt(String s,int i){
		String sub=s.substring(i);
		char current=sub.charAt(0);
		String num=String.valueOf(current);

		/* on parcourt la sous-chaine jusqu'a sortir de celle-ci ou de
		   l'entier, lequel des 2 se produit en premier*/
		int j=1;
		for(j=1;j<sub.length() && Character.isDigit(current);){
			current=sub.charAt(j);
			if(Character.isDigit(current)){
				num=num+current;
				j++;
			}
		}

		// fin de la chaine ?
		boolean end=(j>=sub.length());

		// On ajoute l'entier a la pile en simplifiant les signes moins
		int a=Integer.parseInt(num);
		if(negative%2!=0){
			valueStack.push(new LinkedRBinaryTree<Integer>(new Node<Integer>(-a)));
		}
		else{
			valueStack.push(new LinkedRBinaryTree<Integer>(new Node<Integer>(a)));
		}
		negative=0;

		if(end){
			return -1;
		}
		else{
			return i+j-1;
		}
	}

	/**
	 * Attache un operateur a son ou ses operandes
	 * @throws EmptyStackException si une requete pop a ete faite sur une pile vide
	 */
	private void compute() throws EmptyStackException{
		LinkedRBinaryTree<?> op=opStack.pop();
		LinkedRBinaryTree<?> right=valueStack.pop();
		LinkedRBinaryTree<?> left=null;

		// Ni cos, ni sin
		if(!(op.root().element().equals("sin") || op.root().element().equals("cos"))){
			left=valueStack.pop();
			op.setLeft(left);
			op.setRight(right);
		}
		else{ // sin ou cos
			op.setLeft(right);
		}

		valueStack.push(op);
	}

	public boolean isEmpty() {
		return root==null;
	}

	public int size() {
		int size = 0;
		if (!isEmpty()) {
			size++;
			if (leftChild != null) {
				size += leftChild.size();
			}
			if (rightChild != null) {
				size += rightChild.size();
			}
		}
		return size;
	}

	public Position<E> root() {
		return root;
	}

	public boolean isLeaf() {
		return rightChild==null && leftChild==null;
	}

	public LinkedRBinaryTree<?> leftTree() {
		return leftChild;
	}

	public LinkedRBinaryTree<?> rightTree() {
		return rightChild;
	}

	public void setElement(E o) {
		root = new Node<E>(o);
	}

	public void setLeft(RBinaryTree<?> tree) {
		if(tree instanceof LinkedRBinaryTree)
			leftChild = (LinkedRBinaryTree<?>) tree;
	}

	public void setRight(RBinaryTree<?> tree) {
		if(tree instanceof LinkedRBinaryTree)
			rightChild = (LinkedRBinaryTree<?>) tree;
	}

	public Iterable<Position<?>> positions() {
		ArrayList<Position<?>> collection = new ArrayList<Position<?>>();        
		if(leftChild != null){
			Iterable<Position<?>> collectionLeft = leftChild.positions();
			Iterator<Position<?>> it = collectionLeft.iterator();
			while(it.hasNext()){
				collection.add(it.next());
			}
		}
		collection.add(root);
		if(rightChild != null){
			Iterable<Position<?>> collectionRight = rightChild.positions();
			Iterator<Position<?>> it = collectionRight.iterator();
			while(it.hasNext()){
				collection.add(it.next());
			}            
		}        
		return collection;
	}

	public LinkedRBinaryTree<?> derive() {
		if(isLeaf()){ 
			if(root.element() instanceof Integer){ // nombre
				return new LinkedRBinaryTree<Integer>(new Node<Integer>(0));
			}
			else { // variable arithmétique x
				return new LinkedRBinaryTree<Integer>(new Node<Integer>(1));
			}
		}
		else{
			// On verifie s'il y a correspondance avec un operateur
			if(root.element().equals(new Character('*'))){
				return deriveMul();
			}
			else if (root.element().equals(new Character('+'))){
				return deriveAdd();
			}
			else if (root.element().equals(new Character('-'))){
				return deriveSub();
			}
			else if (root.element().equals(new Character('/'))){
				return deriveDiv();
			}
			else if (root.element().equals(new Character('^'))){
				return deriveExp();
			}
			else if (root.element().equals("cos")){
				return deriveCos();
			}
			else if (root.element().equals("sin")){
				return deriveSin();
			}
			else{ // ni nombre, ni variable, ni operateur
				return null;
			}
		}
	}

	/**
	 * @pre this est un arbre de multiplication
	 * @post renvoie un arbre qui est la derivee de this
	 */
	private LinkedRBinaryTree<?> deriveMul(){
		assert leftTree()!=null && rightTree()!=null;
		LinkedRBinaryTree left=new LinkedRBinaryTree<Character>(new Node<Character>('*'),leftTree().derive(),rightTree());
		LinkedRBinaryTree right=new LinkedRBinaryTree<Character>(new Node<Character>('*'),leftTree(),rightTree().derive());
		return new LinkedRBinaryTree<Character>(new Node<Character>('+'),left,right);
	}

	/**
	 * @pre this est un arbre de soustraction
	 * @post renvoie un arbre qui est la derivee de this
	 */
	private LinkedRBinaryTree<?> deriveSub(){
		assert leftTree()!=null && rightTree()!=null;
		return new LinkedRBinaryTree<Character>(new Node<Character>('-'),leftTree().derive(),rightTree().derive());
	}

	/**
	 * @pre this est un arbre d'addition
	 * @post renvoie un arbre qui est la derivee de this
	 */
	private LinkedRBinaryTree<?> deriveAdd(){
		assert leftTree()!=null && rightTree()!=null;
		return new LinkedRBinaryTree<Character>(new Node<Character>('+'),leftTree().derive(),rightTree().derive());
	}

	/**
	 * @pre this est un arbre de division
	 * @post renvoie un arbre qui est la derivee de this
	 */
	private LinkedRBinaryTree<?> deriveDiv(){
		assert leftTree()!=null && rightTree()!=null;
		LinkedRBinaryTree left=new LinkedRBinaryTree<Character>(new Node<Character>('*'),leftTree().derive(),rightTree());
		LinkedRBinaryTree right=new LinkedRBinaryTree<Character>(new Node<Character>('*'),leftTree(),rightTree().derive());
		LinkedRBinaryTree minus=new LinkedRBinaryTree<Character>(new Node<Character>('-'),left,right);
		LinkedRBinaryTree denom=new LinkedRBinaryTree<Character>(new Node<Character>('^'),rightTree(),new LinkedRBinaryTree<Integer>(new Node<Integer>(2)));
		return new LinkedRBinaryTree<Character>(new Node<Character>('/'),minus,denom);
	}

	/**
	 * @pre this est un arbre de puissance
	 * @post renvoie un arbre qui est la derivee de this
	 */
	private LinkedRBinaryTree<?> deriveExp(){
		assert leftTree()!=null && rightTree()!=null;
		int exp=(Integer)rightTree().root().element();
		LinkedRBinaryTree<Character> e=new LinkedRBinaryTree<Character>(new Node<Character>('^'),leftTree(),new LinkedRBinaryTree<Integer>(new Node<Integer>(exp-1)));
		LinkedRBinaryTree<Character> sub=new LinkedRBinaryTree<Character>(new Node<Character>('*'),rightTree(),e);
		return new LinkedRBinaryTree<Character>(new Node<Character>('*'),sub,leftTree().derive());
	}

	/**
	 * @pre this est un arbre de sinus
	 * @post renvoie un arbre qui est la derivee de this
	 */
	private LinkedRBinaryTree<?> deriveSin(){
		assert leftTree()!=null && rightTree()==null;
		LinkedRBinaryTree<String> c=new LinkedRBinaryTree<String>(new Node<String>("cos"),leftTree(),null);
		return new LinkedRBinaryTree<Character>(new Node<Character>('*'),leftTree().derive(),c);
	}

	/**
	 * @pre this est un arbre de cosinus
	 * @post renvoie un arbre qui est la derivee de this
	 */
	private LinkedRBinaryTree<?> deriveCos(){
		assert leftTree()!=null && rightTree()==null;
		LinkedRBinaryTree<String> s=new LinkedRBinaryTree<String>(new Node<String>("sin"),leftTree(),null);
		LinkedRBinaryTree<Integer> minus=new LinkedRBinaryTree<Integer>(new Node<Integer>(-1));
		LinkedRBinaryTree<Character> prime=new LinkedRBinaryTree<Character>(new Node<Character>('*'),minus,leftTree().derive());
		return new LinkedRBinaryTree<Character>(new Node<Character>('*'),prime,s);
	}

	/**
	 * @pre -
	 * @post this est inchangé
	 */
	public String toString(){
		if(isLeaf()){
			return root.element().toString();
		}
		else if(rightTree()==null){ // sin ou cos
			return root.element()+"("+leftTree().toString()+")";
		}
		else{ // autres operateurs (+ - * / ^)
			return "("+leftTree().toString()+root.element()+rightTree().toString()+")";
		}
	}
}