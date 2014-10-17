package mission2;

public class IllegalInputException extends Exception{
	
	public IllegalInputException(){
		super();
	}
	
	public IllegalInputException(String msg){
		super(msg);
	}
}