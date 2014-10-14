import java.util.Arrays;


public class ElementPossible {
	public static final String[] fonctions = {"^", "sin", "cos"};
	public static final String[] operateursArithmetiques = {"+", "-", "*", "/"};
	public static final String[] inconnus = {"x"};
	public static final int NONREPERTORIE = 0;
	public static final int FONCTION = 1;
	public static final int OPERATEURARITHMETIQUE = 2;
	public static final int INCONNU = 3;
	public static final int ENTIER = 4;
	private int type;
	private String value;
	
	public ElementPossible(String value) {
		this.value = value;
		if(Arrays.asList(fonctions).contains(value)) type = FONCTION;
		else if(Arrays.asList(operateursArithmetiques).contains(value)) type = OPERATEURARITHMETIQUE;
		else if(Arrays.asList(inconnus).contains(value)) type = INCONNU;
		else if(isInteger(value)) type = ENTIER;
		else type = NONREPERTORIE;
	}
	
	public static void main(String[] args) {
		ElementPossible e1 = new ElementPossible("3");
		ElementPossible e2 = new ElementPossible("sin");
		ElementPossible e3 = new ElementPossible("y");
		System.out.println(e1.getType());
		System.out.println(e2.getType());
		System.out.println(e3.getType());
	}
	
	/**
	 * v�rifie si le token est un integer ou non
	 * @param token
	 * 						le token � traiter
	 * @return false si le token n'est pas un "integer"
	 */
	public boolean isInteger(String token){
		try{
			Integer.valueOf(token);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}

	public int getType() {
		return type;
	}

	public String getValue() {
		return value;
	}
}
