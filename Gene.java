package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gene {
	private List<Integer> number;
	private boolean[] isFixed;
	
	public Gene(int[] row) {
		super();
		this.number = new ArrayList<>();
		this.isFixed = new boolean[9];
		
		//Tạo một danh sách các số có sẵn
		List<Integer> temp = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
		
		//Duyệt mảng row ban đầu và xác định số đã tồn tại
		//Nếu tồn tại thì thêm vào number, không tồn tại thì thêm vào 0
		for (int i = 0; i < 9; i++) {
			if(row[i] != 0) {
				isFixed[i] = true;
				number.add(row[i]);
				temp.remove(Integer.valueOf(row[i]));				//Xóa giá trị tồn tại -> chỉ còn các số chưa có
			} else {
				isFixed[i] = false;
				number.add(0);
			}
		}
		
		//Trộn thứ tự số chưa tồn tại
		Collections.shuffle(temp);
		
		//Gán vào number các số chưa tồn tại
		int index = 0;
		for (int i = 0; i < 9; i++) {
			if(!isFixed[i]) {
				number.set(i, temp.get(index));
				index++;
			}
		}
		
		
	}

	public List<Integer> getNumber() {
		return number;
	}

	public void setNumber(List<Integer> number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return number + " ";
	}
	
	public static void main(String[] args) {
		List<Integer> number = new ArrayList<>();
		int[] row = {8, 0, 0, 0, 0, 0, 7, 0, 9};
		Gene gen = new Gene(row);
		System.out.println(gen);
	}
	
	
	
	
	
	
}
