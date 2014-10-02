package mission1;

/* 
 * StackingObject is an object containing a string character "false" or "true"
 * or a double
 */
public class StackingObject  {
	double d;
	boolean b;
	boolean isDouble;
	
	public StackingObject(double doub) {
		d=doub;
		isDouble=true;
	}
	
	public StackingObject(boolean bool) {
		b=bool;
		isDouble=false;
	}
	
	public boolean isDouble() {
		return isDouble;
	}
	
	public double getDouble() {
		return d;
	}
	
	public boolean getBoolean() {
		return b;
	}
	
	public String toString() {
		if (isDouble) {
			return Double.toString(d);
		}
		else {
			return Boolean.toString(b);
		}
	}

}
