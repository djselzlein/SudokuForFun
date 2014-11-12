package com.example.sudokuforfun.gameboard;

import java.util.HashSet;
import java.util.Set;

public class Cube {

	private Set<Integer> cubeValues;
	private CubeTypes cubeName;
	private Set<Integer> leftOutValues;
	private Integer rowfromIndex, rowtoIndex, colfromIndex, coltoIndex;

	public Cube(Set<Integer> vals) {
		leftOutValues = new HashSet<Integer>();
		cubeValues = new HashSet<Integer>();
		// System.out.println("VALUES:" + vals.toString());
		cubeValues.addAll(vals);
	}

	public void removeleftOutValue(Integer val) {
		System.out.println("; Removing " + val);
		this.leftOutValues.remove(val);
	}

	public Set<Integer> getCubeValues() {
		return cubeValues;
	}

	@Override
	public String toString() {
		System.out.println(cubeName.toString() + " contains: " + 
				cubeValues + " missing: " + leftOutValues);
		return "";
	}

	public void setCubeName(CubeTypes type) {
		this.cubeName = type;
	}

	public void setCubeValues(Set<Integer> cubeValues) {
		this.cubeValues = cubeValues;
	}
	
	public CubeTypes getCubeName() {
		return cubeName;
	}

	public Set<Integer> cubeValuesNotGiven() {
		for (int i = 1; i < 10; i++) {
			if (!this.cubeValues.contains(i)) {
				leftOutValues.add(i);
			}
		}

		return leftOutValues;

	}

	public Set<Integer> getLeftOutValues() {
		return leftOutValues;
	}

	public void setLeftOutValues(Set<Integer> leftOutValues) {
		this.leftOutValues = leftOutValues;
	}

	public Integer getRowfromIndex() {
		return rowfromIndex;
	}

	public void setRowfromIndex(Integer rowfromIndex) {
		this.rowfromIndex = rowfromIndex;
	}

	public Integer getRowtoIndex() {
		return rowtoIndex;
	}

	public void setRowtoIndex(Integer rowtoIndex) {
		this.rowtoIndex = rowtoIndex;
	}

	public Integer getColfromIndex() {
		return colfromIndex;
	}

	public void setColfromIndex(Integer colfromIndex) {
		this.colfromIndex = colfromIndex;
	}

	public Integer getColtoIndex() {
		return coltoIndex;
	}

	public void setColtoIndex(Integer coltoIndex) {
		this.coltoIndex = coltoIndex;
	}
	
	/*
	* Creates a Cube
	*/
	public static Cube createCube(CubeTypes ctype, Board board ) {
	
		int rowfromIndex = 0, rowtoIndex = 0;
		int colfromIndex = 0, coltoIndex = 0;
		
		switch (ctype) {
		
			case TOPLEFT_11:
				rowfromIndex = 0;
				rowtoIndex = 3;
				colfromIndex = 0;
				coltoIndex = 3;
				break;
			case TOPMIDDLE_12:
				rowfromIndex = 0;
				rowtoIndex = 3;
				colfromIndex = 3;
				coltoIndex = 6;
				break;
			case TOPRIGHT_13:
				rowfromIndex = 0;
				rowtoIndex = 3;
				colfromIndex = 6;
				coltoIndex = 9;
				break;
			
			case MIDDLELEFT_21:
				rowfromIndex = 3;
				rowtoIndex = 6;
				colfromIndex = 0;
				coltoIndex = 3;
				break;
			case MIDDLEMIDDLE_22:
				rowfromIndex = 3;
				rowtoIndex = 6;
				colfromIndex = 3;
				coltoIndex = 6;
				break;
			case MIDDLERIGHT_23:
				rowfromIndex = 3;
				rowtoIndex = 6;
				colfromIndex = 6;
				coltoIndex = 9;
				break;
			
			case BOTTOMLEFT_31:
				rowfromIndex = 6;
				rowtoIndex = 9;
				colfromIndex = 0;
				coltoIndex = 3;
				break;
			case BOTTOMMIDDLE_32:
				rowfromIndex = 6;
				rowtoIndex = 9;
				colfromIndex = 3;
				coltoIndex = 6;
				break;
			case BOTTOMRIGHT_33:
				rowfromIndex = 6;
				rowtoIndex = 9;
				colfromIndex = 6;
				coltoIndex = 9;
				break;
			default:
				break;
		}

		Set<Integer> h = new HashSet<Integer>();
		
		int value;
		
		for (int i = rowfromIndex; i < rowtoIndex; i++) {
			for (int j = colfromIndex; j < coltoIndex; j++) {

				value = board.getIntValue(j, i);
			
				if (value != 0) {
					h.add(value);
				}
			}
		}
		

		Cube c = new Cube(h);
		c.setCubeName(ctype);
		c.setRowfromIndex(rowfromIndex);
		c.setRowtoIndex(rowtoIndex);
		c.setColfromIndex(colfromIndex);
		c.setColtoIndex(coltoIndex);
		c.cubeValuesNotGiven();
		return c;
	}

}
