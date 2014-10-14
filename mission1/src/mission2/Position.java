package mission2;

/**
 * Interface qui represente une abstraction de ce qu'est le "noeud"
 * d'un arbre
 * 
 * @param <E> Type de l'element contenu dans le noeud
 * @author DSAJ-5
 */
public interface Position<E> {
	
  /**
   * @pre /
   * @post retourne l'element contenu dans l'objet position courant
   */
  public E element();
}