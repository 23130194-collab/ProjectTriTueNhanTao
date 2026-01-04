package sudoku.view;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import sudoku.model.Individual;

public class SudokuFrame extends JFrame {
    private JTextField[][] cells = new JTextField[9][9];
    private JButton btnSolve, btnGenerate, btnClear;
    private JLabel lblStatus;

    public SudokuFrame() {
        setTitle("Sudoku");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Bàn cờ (CENTER)
        JPanel pnlBoard = new JPanel(new GridLayout(9, 9));
        pnlBoard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font font = new Font("Arial", Font.BOLD, 20);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                cells[i][j].setFont(font);

                // Tạo viền đậm cho khối 3x3
                int top = (i % 3 == 0) ? 2 : 1;
                int left = (j % 3 == 0) ? 2 : 1;
                int bottom = (i == 8) ? 2 : 1;
                int right = (j == 8) ? 2 : 1;
                
                cells[i][j].setBorder(new MatteBorder(top, left, bottom, right, Color.BLACK));
                pnlBoard.add(cells[i][j]);
            }
        }
        add(pnlBoard, BorderLayout.CENTER);

        // 2. Panel điều khiển (SOUTH)
        JPanel pnlControl = new JPanel();
        btnGenerate = new JButton("Tạo Mới");
        btnClear = new JButton("Tự Nhập / Xóa");
        btnSolve = new JButton("GIẢI (Start)");
        lblStatus = new JLabel("Sẵn sàng...");

        pnlControl.add(btnGenerate);
        pnlControl.add(btnClear);
        pnlControl.add(btnSolve);
        pnlControl.add(lblStatus);

        add(pnlControl, BorderLayout.SOUTH);
    }

    // Lấy dữ liệu từ giao diện ra mảng int[][]
    public int[][] getBoardData() {
        int[][] board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = cells[i][j].getText();
                try {
                    board[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
                } catch (NumberFormatException e) {
                    board[i][j] = 0; // Nếu nhập chữ bậy bạ thì coi như là 0
                }
            }
        }
        return board;
    }

    // Hiển thị một đề bài lên giao diện
    public void setBoardData(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0) {
                    cells[i][j].setText(String.valueOf(board[i][j]));
                    cells[i][j].setEditable(false); // Số đề bài thì không được sửa
                    cells[i][j].setForeground(Color.BLUE);
                    cells[i][j].setBackground(new Color(230, 230, 230));
                } else {
                    cells[i][j].setText("");
                    cells[i][j].setEditable(true); // Ô trống cho phép nhập
                    cells[i][j].setForeground(Color.BLACK);
                    cells[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    // Cập nhật kết quả từ thuật toán
    public void updateBoardFromIndividual(Individual ind) {
        // Individual chứa List<Gene>, mỗi Gene là một hàng
        for (int i = 0; i < 9; i++) {
            java.util.List<Integer> rowData = ind.getGenes().get(i).getNumber();
            for (int j = 0; j < 9; j++) {
                // Chỉ cập nhật các ô màu đen (ô giải), giữ nguyên ô màu xanh (ô đề)
                if (cells[i][j].isEditable()) {
                    cells[i][j].setText(String.valueOf(rowData.get(j)));
                }
            }
        }
    }

    public void clearBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setText("");
                cells[i][j].setEditable(true);
                cells[i][j].setForeground(Color.BLACK);
                cells[i][j].setBackground(Color.WHITE);
            }
        }
    }

    public void updateStatus(String msg) {
        lblStatus.setText(msg);
    }
    
    // Getter cho các nút để Controller bắt sự kiện
    public JButton getBtnSolve() { return btnSolve; }
    public JButton getBtnGenerate() { return btnGenerate; }
    public JButton getBtnClear() { return btnClear; }
}
