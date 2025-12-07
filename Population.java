package sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
	private List<Individual> individuals;

	public Population(int[][] board) {
		super();
		this.individuals = new ArrayList<>();
		
		//Khởi tạo quần thể bằng kích thước
		for (int i = 0; i < Utility.POPULATION_SIZE; i++) {
			this.individuals.add(new Individual(board));
		}
	}

	public List<Individual> getIndividuals() {
		return individuals;
	}

	public void setIndividuals(List<Individual> individuals) {
		this.individuals = individuals;
	}
	
}
