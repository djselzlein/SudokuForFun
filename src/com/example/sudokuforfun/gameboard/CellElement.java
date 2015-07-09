package com.example.sudokuforfun.gameboard;

import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter.ConversionException;

public class CellElement implements Property {
	private int value;
	private boolean readOnly;
	private boolean selected;

	// allow the CellElement to know its board location
	private int col, row;

	public CellElement(int value, boolean readOnly, int col, int row) {
		this.value = value;
		this.readOnly = readOnly;
		this.selected = false;
		this.col = col;
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Integer getIntegerValue() {
		return Integer.valueOf(value);
	}

	public int getIntValue() {
		return value;
	}

	@Override
	public String getValue() {
		if (value == 0)
			return " ";
		else
			return String.valueOf(value);
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException,
			ConversionException {
		if (readOnly)
			throw new ReadOnlyException();

		// Already the same type as the internal representation
		if (newValue instanceof Integer)
			value = (Integer) newValue;

		// Conversion from a string is required
		else if (newValue instanceof String)
			try {
				 value = Integer.parseInt((String) newValue);
			} catch (NumberFormatException e) {
				throw new ConversionException();
			}
		else
			// Don't know how to convert any other types
			throw new ConversionException();
	}

	@Override
	public Class<?> getType() {
		return this.getClass();
	}

	@Override
	public boolean isReadOnly() {
		return this.readOnly;
	}

	@Override
	public void setReadOnly(boolean newStatus) {
		this.readOnly = newStatus;
	}
}
