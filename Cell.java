import java.util.ArrayList;

public class Cell {
	
	int x;
	int y;
	ArrayList<Integer> options;
	
	public Cell(int x, int y, int o, boolean solved) {
		this.x = x;
		this.y = y;
		options = new ArrayList<Integer>();
		
		if(!solved) {
			for(int i = 1 ; i <= o ; ++i) {
				options.add(i);
			}
		} else {
			options.add(o);
		}
	}
	
	public ArrayList<Integer> getOptions() {
		return options;
	}
	
	public boolean removeOption(int o) {
		if(options.contains((Integer)o)) {	
			options.remove((Integer)o);
			return true;
		}
		return false;
	}
	
	public void setFinalOption(int option) {
		options.clear();
		options.add(option);
	}
	
	public void printOptions() {
		System.out.print("Cell (" + x + "," + y + "): {");
		if(options.size() != 0) {
			for(int i = 0 ; i < options.size() ; ++i) {
				System.out.print(options.get(i));
				if(i == options.size()-1) {
					System.out.println("}");
				} else {
					System.out.print(", ");
				}
			}
		} else {
			System.out.println("}");
		}
	}
}
