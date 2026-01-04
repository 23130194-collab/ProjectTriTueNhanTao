package model;

import java.util.function.Consumer;

import model.Individual;
import model.Population;

public class SudokuEngine {
    
    // Biến cờ để kiểm soát vòng lặp (cho phép nút Stop hoạt động)
    private boolean isRunning = false;

    // Interface Consumer đóng vai trò là "người lắng nghe" (Callback)
    // Giúp Engine gửi dữ liệu (Individual tốt nhất) ra bên ngoài (Controller/View)
    private Consumer<Individual> onGenerationEvolved;

    
    public Consumer<Individual> getOnGenerationEvolved() {
		return onGenerationEvolved;
	}

	// Hàm để Controller đăng ký nhận dữ liệu
    public void setOnGenerationEvolved(Consumer<Individual> callback) {
        this.onGenerationEvolved = callback;
    }

    // Hàm dừng thuật toán
    public void stop() {
        isRunning = false;
    }

    // Hàm chính để giải Sudoku
    public void solve(int[][] initialBoard) {
        isRunning = true;
        
        // Khởi tạo quần thể ban đầu
        Population pop = new Population(initialBoard);
        
        int generation = 0;
        int bestFitness = 0;
        int count = 0;

        while (isRunning) {
            // 1. Tiến hóa thế hệ tiếp theo
            pop.evolve();

            // 2. Lấy ra cá thể tốt nhất hiện tại
            Individual best = pop.getIndividuals().get(0);
            int currentFitness = best.getFitness();

            // 3. Gửi cá thể tốt nhất ra ngoài (cho Controller/View cập nhật)
            if (onGenerationEvolved != null) {
                onGenerationEvolved.accept(best);
            }

            // 4. Kiểm tra điều kiện dừng (Đã tìm ra đáp án: 162 điểm)
            if (currentFitness == 162) {
                System.out.println("Giải thành công tại thế hệ: " + generation);
                isRunning = false;
                break;
            }

            // 5. Xử lý kẹt (Local Optima) - Logic Restart
            if (currentFitness == bestFitness) {
                count++;
            } else {
                bestFitness = currentFitness;
                count = 0;
            }

            // Nếu điểm không đổi trong 100 thế hệ -> Kẹt -> Tạo quần thể mới hoàn toàn
            // (Con số 100 có thể tùy chỉnh, càng lớn thì càng kiên nhẫn)
            if (count > 50) {
                System.out.println("!!!Không thành công, số điểm cao nhất " + currentFitness + "! Khởi tạo quần thể mới...");
                pop = new Population(initialBoard);
                count = 0;
                bestFitness = 0;
                generation = 0;
            }
            generation++;
        }
    }
}