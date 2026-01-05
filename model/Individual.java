package sudoku.model;

import java.util.ArrayList;
import java.util.*;

public class Individual implements Comparable<Individual>{
	private List<Gene> genes;
	private int fitness;

	public Individual(int[][] board) {
		super();
		this.genes = new ArrayList<>();
		
		//Khởi tạo 9 Gene (9 ô 3x3)
		for (int i = 0; i < 9; i++) {
			int[] row = board[i];
			genes.add(new Gene(row));
		}
	}
		
	public Individual() {
		this.genes = new ArrayList<>();
	}

	public List<Gene> getGenes() {
		return genes;
	}

	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}
	
	public int getFitness() {
        return fitness;
    }

    // pthuc thực hiện đột biến trên cá thể
    public void mutate(double mutationRate) {
        Random random = new Random();
        for (Gene gene : genes) {
            // Với mỗi hàng, có xác suất mutationRate sẽ bị đột biến
            if (random.nextDouble() < mutationRate) {
                gene.mutate();
            }
        }
    }

    // pthuc tính toán Fitness
    // Max Fitness = 162 (9 hàng đã đúng, giờ tính 9 cột + 9 ô 3x3)
    // Mỗi cột có 9 số -> 9*9=81
    // mỗi ô 3x3 có 9 số -> 9*9=81 -> 81 + 81 = 162
    public void calculateFitness() {
        int score = 0;

        // Kiểm tra cột
        for (int col = 0; col < 9; col++) {
            Set<Integer> num = new HashSet<>();
            for (int row = 0; row < 9; row++) {
                num.add(genes.get(row).getNumber().get(col));
            }
            score += num.size(); // Càng nhiều số khác nhau càng điểm cao
        }

        //Kiểm tra ô 3x3
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                Set<Integer> num = new HashSet<>();
                
                // Duyệt 9 số trong ô 3x3
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int row = blockRow * 3 + i;
                        int col = blockCol * 3 + j;
                        num.add(genes.get(row).getNumber().get(col));
                    }
                }
                score += num.size();
            }
        }

        this.fitness = score;
    }

    // Để sắp xếp giảm dần theo fitness
    @Override
    public int compareTo(Individual other) {
        return other.fitness - this.fitness; // Sắp xếp ngược để lấy fitness cao -> thấp
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fitness: ").append(fitness).append("\n");
        for (Gene gene : genes) {
            sb.append(gene.toString()).append("\n");
        }
        return sb.toString();
    }
}
