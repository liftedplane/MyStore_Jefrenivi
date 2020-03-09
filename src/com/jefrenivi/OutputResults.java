package com.jefrenivi;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class OutputResults {

	// displays results when called
	private void displayResults(ResultSet rs) throws SQLException {

		ResultSetMetaData rsmd = rs.getMetaData();

		while (rs.next()) {
			for (int i = 1; i <= rsmd.getColumnCount(); i++)
				System.out.printf("%s:\t%s\n", rsmd.getColumnName(i), rs.getObject(i));
				System.out.println();

		}
	}

}