package mission1;

import java.util.EmptyStackException;

/**
 * @author Ludovic Fastré, source : DSAJ-5
 */
public interface Stack<E> {
	
	/**
     * pre : -
     * post : renvoie la taille en int de la pile
     */
	public int size();
	
	/**
     * pre : -
     * post : renvoie true si elle est vide (size()==0), false sinon
     */
	public boolean isEmpty();
	
	/**
     * pre : la pile est non vide
     * post : retourne l'element au dessus de la pile
     */
	public E top() throws EmptyStackException;
	
	/**
     * pre : -
     * post : ajoute un element au dessus de la pile
     */
	public void push(E element);
	
	/**
     * pre : la pile est non vide
     * post : enleve l'element au dessus de la pile et le renvoie
     */
	public E pop() throws EmptyStackException;
}
