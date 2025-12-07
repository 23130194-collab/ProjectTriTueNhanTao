package sudoku;

import java.util.ArrayList;
import java.util.List;

public class Individual {
	private List<Gene> genes;

	public Individual(int[][] board) {
		super();
		this.genes = new ArrayList<>();
		
		//Khởi tạo 9 Gene (9 ô 3x3)
		for (int i = 0; i < 9; i++) {
			int[] row = board[i];
			genes.add(new Gene(row));
		}
	}

	
	public List<Gene> getGenes() {
		return genes;
	}

	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Gene gene : genes) {
			sb.append(gene.toString()).append("\n");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		// Giả lập một bàn cờ đầu vào (0 là ô trống)
		int[][] board = {
			{8, 0, 0, 0, 0, 0, 7, 0, 9},
			{2, 4, 0, 0, 0, 0, 0, 0, 0},
			{9, 0, 0, 0, 0, 0, 0, 0, 0},
			{8, 0, 0, 0, 0, 0, 0, 0, 0},
			{3, 0, 0, 0, 0, 0, 0, 0, 0},
			{4, 0, 0, 0, 0, 0, 0, 0, 0},
			{7, 0, 0, 0, 0, 0, 0, 0, 5},
			{6, 0, 0, 0, 0, 0, 0, 0, 0},
			{5, 0, 0, 0, 0, 0, 0, 0, 1}
		};
		
		Individual ind = new Individual(board);
		System.out.println(ind);
	}
	
	
	
	
	

}
