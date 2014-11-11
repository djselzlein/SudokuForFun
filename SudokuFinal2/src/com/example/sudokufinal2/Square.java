package com.example.sudokufinal2;

public class Square {

	private boolean isEmptySquare;
	private Integer rowIndex;
	private Integer columnIndex;

	public boolean isEmptySquare() {
		return isEmptySquare;
	}

	public void setEmptySquare(boolean isEmptySquare) {
		this.isEmptySquare = isEmptySquare;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Integer getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}
	
	public String toString()
	{
		return( "Square at row: " + rowIndex + " col: " + columnIndex );
	}

}
