package sudoku.model;

import java.util.ArrayList;
import java.util.*;
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
				temp.remove(Integer.valueOf(row[i]));
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
	
	public Gene(Gene other) {
	    this.number = new ArrayList<>(other.number);
	    this.isFixed = other.isFixed.clone();
	}
	
	// Hàm đột biến: Hoán đổi 2 vị trí không cố định
    public void mutate() {
        Random random = new Random();
        
        // Tìm 2 vị trí ngẫu nhiên để swap
        int index1 = random.nextInt(9);
        int index2 = random.nextInt(9);
        
        // Chỉ swap nếu cả 2 vị trí đều kh phải là số đề bài cho (isFixed = false)
        // lặp tối đa vài lần để tìm cặp hợp lệ, tránh while true gây treo nếu hàng full fixed
        int count = 0;
        while ((isFixed[index1] || isFixed[index2] || index1 == index2) && count < 10) {
            index1 = random.nextInt(9);
            index2 = random.nextInt(9);
            count++;
        }

        // Thực hiện swap nếu tìm được vị trí hợp lệ
        if (!isFixed[index1] && !isFixed[index2] && index1 != index2) {
            Collections.swap(this.number, index1, index2);
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
	
}
