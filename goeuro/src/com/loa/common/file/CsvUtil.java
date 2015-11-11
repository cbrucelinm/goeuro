package com.loa.common.file;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class CsvUtil {
	private String separator = ",";
	public String getSeparator() {
		return separator;
	}
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	RandomAccessFile rFile = null;
	
	private ArrayList<String> headings = new ArrayList<String>();
	private ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
	public ArrayList<String> getHeadings() {
		return headings;
	}
	public void setHeadings(ArrayList<String> headings) {
		this.headings = headings;
	}
	public ArrayList<ArrayList<String>> getValues() {
		return values;
	}
	public void setValues(ArrayList<ArrayList<String>> values) {
		this.values = values;
	}
	
	public boolean write(String fileName) {
		try {
			File file = new File(fileName);
			if( !file.exists()) {
				rFile = new RandomAccessFile(file, "rw"); 
				writeHeading();
			}
			append();
		} catch (Exception e ) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private void append() {
		try {
			if( values == null || values.size() == 0 ) {
				System.out.print("Invalid data for csv file : " + values);
				return;
			}
			for(ArrayList<String> data : values) {
				write( data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writeHeading() {
		try {
			if( headings == null || headings.size() == 0 ) {
				System.out.print("Invalid header for csv file : " + headings);
				return;
			}
			write(headings);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void write(ArrayList<String> data) {
		try {
			if( data == null || data.size() == 0 ) {
				System.out.print("Invalid data for csv file : " + data);
				return;
			}
			int size = data.size();
			for(int i = 0 ; i < size ; i++  ) {
				rFile.writeBytes(data.get(i) + (i+1 == size?"":separator) ) ;
			}
			rFile.writeByte(Character.LINE_SEPARATOR);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
