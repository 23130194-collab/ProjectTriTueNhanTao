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

    public List<Individual> crossover() {
        List<Individual> children = new ArrayList<>();
        int half = Utility.POPULATION_SIZE / 2;

        //Tách quần thể ban đầu thành quần thể cha và mẹ
        List<Individual> fathers = individuals.subList(0, half);
        List<Individual> mothers = individuals.subList(half, Utility.POPULATION_SIZE);

        //Ghép cặp từng cha với từng mẹ
        for (int i = 0; i < fathers.size(); i++) {
            for (int j = 0; j < mothers.size(); j++) {
                Individual child1 = createChild(fathers.get(i), mothers.get(j));
                Individual child2 = createChild(mothers.get(i), fathers.get(j));
                children.add(child1);
                children.add(child2);
            }
        }
        return children;
    }

    private Individual createChild(Individual father, Individual mother) {
        // TODO Auto-generated method stub
        Individual child = new Individual();
        int split = Utility.SPLIT;

        for (int i = 0; i < split; i++) {
            // Lấy gene tại vị trí i của bố đưa vào con
            child.getGenes().add(father.getGenes().get(i));
        }
        for (int i = split; i < 9; i++) {
            child.getGenes().add(mother.getGenes().get(i));
        }
        return child;
    }
	
}
