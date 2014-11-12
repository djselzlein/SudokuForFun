package com.example.sudokuforfun.listeners;

import java.util.ArrayList;
import java.util.List;

import com.example.sudokuforfun.gameboard.Board;
import com.example.sudokuforfun.gameboard.Cube;
import com.example.sudokuforfun.gameboard.CubeTypes;
import com.example.sudokuforfun.gameboard.Square;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;

/**
 * 
 * @author djeisonselzlein
 *
 *	This class checks if the board is correctly filled
 *
 */
public class CheckerButtonListener implements ClickListener {

	private static final long serialVersionUID = 1L;
	private static Cube c1, c2, c3, c4, c5, c6, c7, c8, c9;
	private static List<Cube> cubeList;

	private Board board;

	public CheckerButtonListener(GridLayout grid, Board board) {
		this.board = board;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		cubeList = new ArrayList<Cube>();
		c1 = Cube.createCube(CubeTypes.TOPLEFT_11, board);
		c2 = Cube.createCube(CubeTypes.TOPMIDDLE_12, board);
		c3 = Cube.createCube(CubeTypes.TOPRIGHT_13, board);
		c4 = Cube.createCube(CubeTypes.MIDDLELEFT_21, board);
		c5 = Cube.createCube(CubeTypes.MIDDLEMIDDLE_22, board);
		c6 = Cube.createCube(CubeTypes.MIDDLERIGHT_23, board);
		c7 = Cube.createCube(CubeTypes.BOTTOMLEFT_31, board);
		c8 = Cube.createCube(CubeTypes.BOTTOMMIDDLE_32, board);
		c9 = Cube.createCube(CubeTypes.BOTTOMRIGHT_33, board);
		cubeList.add(c1);
		cubeList.add(c2);
		cubeList.add(c3);
		cubeList.add(c4);
		cubeList.add(c5);
		cubeList.add(c6);
		cubeList.add(c7);
		cubeList.add(c8);
		cubeList.add(c9);

		if (checkSudoku()) {
			Notification.show(" Congratulations, You Won! ");
		}else{
			Notification.show(" Sorry, the puzzle is uncompleted or wrongly filled ");
		}

		System.out.println(checkSudoku());
	}

	/*
	 * Check
	 */
	private boolean checkSudoku() {

		Square cell = EmptySquarePresent();

		if (cell.isEmptySquare()) {
			return false;
		}

		for (int col = 0; col < 9; col++) {
			for (int row = 0; row < 9; row++) {
				// check if no conflicts
				if (AreThereConflicts(board.getIntValue(col, row), row, col)) {
					return false;
				}
			}
		}

		return true;
	}

	/*
	 * Are there any conflicts ?
	 */
	private boolean AreThereConflicts(Integer number, Integer row, Integer col) {
		for (int k = 0; k < 9; k++) {
			if ((number == board.getIntValue(k, row) && (k != col)) || ((board
					.getIntValue(col, k) == number) && (k != row))) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Returns an empty square
	 */
	private Square EmptySquarePresent() {

		Square sq = new Square();
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (board.getIntValue(col, row) == 0) {
					sq.setRowIndex(row);
					sq.setColumnIndex(col);
					sq.setEmptySquare(true);
					return sq;
				}
			}
		}
		return sq;
	}

}
