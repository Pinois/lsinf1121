package mission2;

/**
 * 
 * @author Ludovic Fastré
 *
 * @param <E> Type de l'element contenu dans le noeud
 */
public class Node<E> implements Position<E> {

	/**Element contenu dans le noeud*/
	private E element;
	
	/**Cree un noeud avec l'element specifie*/
	public Node(E elem) {
		this.element = elem;
	}
	
	/**Renvoie l'element contenu dans le noeud*/
	public E element() {
		return element;
	}
}