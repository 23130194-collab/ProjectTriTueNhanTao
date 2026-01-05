package sudoku.controller;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import sudoku.model.SudokuEngine;
import sudoku.model.SudokuGenerator;
import sudoku.view.SudokuFrame;

public class SudokuController {
    private SudokuFrame view;
    private SudokuEngine engine;
    private SudokuGenerator generator;
    private boolean isRunning = false;

    public SudokuController(SudokuFrame view) {
        this.view = view;
        // Khởi tạo các thành phần Model
        this.engine = new SudokuEngine();
        this.generator = new SudokuGenerator();
        
        // Gắn sự kiện cho các nút bấm
        initController();
    }

    private void initController() {
        // xử lý nút "Tạo Mới"
        view.getBtnGenerate().addActionListener(e -> {
            if (isRunning) return; // Nếu đang giải thì không cho bấm
            
            // Tạo mới
            int[][] newBoard = generator.generate(45);
            view.setBoardData(newBoard);
            view.updateStatus("Đã tạo mới. Nhấn Giải để bắt đầu.");
        });

        //xử lý nút "Tự Nhập / Xóa"
        view.getBtnClear().addActionListener(e -> {
            if (isRunning) return;
            
            view.clearBoard();
            view.updateStatus("Mời bạn nhập đề sudoku...");
        });

        //xử lý nút "GIẢI / DỪNG"
        view.getBtnSolve().addActionListener(e -> {
            if (isRunning) {
                // Nếu đang chạy -> Bấm để dừng
                stop();
            } else {
                // Nếu đang dừng -> Bấm để chạy
                start();
            }
        });
    }

    private void start() {
        //Lấy dữ liệu từ giao diện (bao gồm cả số người dùng tự nhập)
        int[][] inputBoard = view.getBoardData();
        
        //format lại giao diện
        // Dòng này sẽ biến các số người dùng vừa nhập thành màu xanh
        // và khóa lại (setEditable=false) giống đề bài ngẫu nhiên.
        view.setBoardData(inputBoard);

        // cấu hình Engine để cập nhật giao diện khi chạy
        engine.setOnGenerationEvolved(ind -> {
            SwingUtilities.invokeLater(() -> {
                view.updateBoardFromIndividual(ind);
                view.updateStatus("Fitness: " + ind.getFitness() + "/162 | Gen: đang chạy...");
            });
        });

        // Cập nhật trạng thái nút bấm
        isRunning = true;
        view.getBtnSolve().setText("DỪNG");
        view.getBtnGenerate().setEnabled(false);
        view.getBtnClear().setEnabled(false);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Hàm này sẽ chạy tốn thời gian
                engine.solve(inputBoard);
                return null;
            }

            @Override
            protected void done() {
                // Kết thúc:tìm thành công hoặc bị dừng
                stop();
                view.updateStatus("Thành công hoặc đã dừng!");
            }
        };
        
        worker.execute();
    }

    private void stop() {
        engine.stop(); // gửi lệnh dừng vào Model
        isRunning = false;
        
        // Reset lại giao diện nút
        view.getBtnSolve().setText("GIẢI (Start)");
        view.getBtnGenerate().setEnabled(true);
        view.getBtnClear().setEnabled(true);
    }
}