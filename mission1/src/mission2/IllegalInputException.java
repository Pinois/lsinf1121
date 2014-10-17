package mission2;

/**
 * 
 * @author Ludovic Fastré
 *
 */
public class IllegalInputException extends Exception{
	
	public IllegalInputException(){
		super();
	}
	
	public IllegalInputException(String msg){
		super(msg);
	}
}