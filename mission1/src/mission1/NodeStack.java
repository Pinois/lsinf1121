package mission1;

import java.util.EmptyStackException;

/**
 * classe de la pile
 * @author Ludovic Fastré, source : DSAJ-5
 */
public class NodeStack<E> implements Stack<E>{
    private Node<E> top;
    private int size;
    
    /**
     * Constructeur de la pile vide
     */
    public NodeStack(){
    	top = null;
    	size = 0;
    }
    
	public int size(){
		return size;
	}

    public boolean isEmpty(){
        return size == 0;
    }
    
    public void push(E elem){
        Node<E> newNode = new Node<E>(elem, top);
        top = newNode;
        size++;
    }
    
    public E top() throws EmptyStackException {
        if(isEmpty()) throw new EmptyStackException();
        return top.getElement();
    }
    
    public E pop() throws EmptyStackException {
    	if(isEmpty()) throw new EmptyStackException();
    	E temp = top.getElement();
    	top = top.getNext();
    	size--;
    	return temp;
    }
    
    /**
     * Classe du noeud
     */
    private class Node<E>{
    	private E element;
    	private Node<E> next;
    	
    	/**
    	 * Construction du noeud vide
    	 */
    	public Node(){
    		this(null,null);
    	}

    	/**
    	 * Construction du noeud contenant l'element e et le noeud suivant n
    	 */
		public Node(E e, Node<E> n) {
			element = e;
			next = n;
		}
		
		/**
	     * pre : -
	     * post : renvoie l'element contenu dans le noeud
	     */
		public E getElement(){
			return element;
		}
		
		/**
	     * pre : -
	     * post : renvoie le noeud suivant
	     */
		public Node<E> getNext(){
			return next;
		}
		
		/**
	     * pre : -
	     * post : remplace l'element du noeud par newElem
	     */
		public void setElement(E newElem){
			element = newElem;
		}
		
		/**
	     * pre : -
	     * post : remplace le noeud suivant par newNext
	     */
		public void setNext(Node<E> newNext){
			next = newNext;
		}
    }
}