
public class array {
	 public String name;
     public int array1[];
	public array(String name, int num) {
		super();
		this.name = name;
		this.array1 = new int[num];
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getArray() {
		return array1;
	}
	public void setArray(int[] array) {
		this.array1 = array;
	}
    public void setBarray(int num1 , int num2) {
    	array1[num1] = num2;
    }
    public String putBarray() {
    	String parseresult = "";
    	for(int i = 0; i < array1.length ; i++) {
    		parseresult += (array1[i] + " ");
    	}
    	return parseresult;
    }
}
