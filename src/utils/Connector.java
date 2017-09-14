package utils;
import java.sql.*;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Connector {
	Connection conn;
	PreparedStatement stm;
	ResultSet rs;
	public Connector(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/swingapp","root","admin1234");
            
		}catch( Exception e ){
			System.out.println(e);
			e.printStackTrace();
		}
	}
	public boolean insertRecord(String userName, String sex){
		String query = "Insert into biodata(name, sex) values ('" + userName +"', '" + sex +"')";
		
		try{
			stm = conn.prepareStatement(query);
			int result = stm.executeUpdate();
			System.out.println(result);
			return true;
		} catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
	public ResultSet fetchRecords(){
		String query = "Select * from biodata";
		
		try{
			stm = conn.prepareStatement(query);
			rs = stm.executeQuery();
		}catch(Exception e){
			System.out.println(e);
		}
		return rs;
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs)
		    throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();

		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
		    columnNames.add(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
		    Vector<Object> vector = new Vector<Object>();
		    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
		        vector.add(rs.getObject(columnIndex));
		    }
		    data.add(vector);
		}

		return new DefaultTableModel(data, columnNames);

		}
}
