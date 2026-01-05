package sudoku.main;

import javax.swing.SwingUtilities;
import sudoku.controller.SudokuController;
import sudoku.view.SudokuFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuFrame view = new SudokuFrame();
            new SudokuController(view);
            view.setVisible(true);
        });
    }
}