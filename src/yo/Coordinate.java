package yo;

public class Coordinate {
	public final String first;
	public final String second;
	public Coordinate(String coord){
		String[] c = coord.split(",");
		first = c[1];
		second = c[0];
	}
	public String toString(){
		return first+","+second;
	}
}
