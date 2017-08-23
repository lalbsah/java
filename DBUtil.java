import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;
/**
 * <h1> Java database Utility! </h1>
 * This utility class is designed to help day to day database task in java 
 * @author Lal Sah
 * @version 1.0
 * 
 */
public enum DBUtil {
	INSTANCE;
	/**
	 * <pre>
	 * {@code
	 * List<Map<K,V>> resultMap = DBUtil.INSTANCE.executeQuery("jdbc/sqlds", sql);
	 * }
	 * </pre>
	 * @param datasource - database datasource JNDI
	 * @param sql - SQL query to execute
	 * @returns List of key-value pair database result rows
	 */
	public <K, V> List<Map<K, V>> executeQuery(String datasource, String sql) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Map<K, V>> data = null;
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(datasource);
			con = ds.getConnection();
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			ResultSetMetaData rsmt = rs.getMetaData();
			List<String> columns = new ArrayList<>(rsmt.getColumnCount());
			for (int i = 1; i <= rsmt.getColumnCount(); i++) {
				columns.add(rsmt.getColumnLabel(i));
			}
			data = new ArrayList<Map<K, V>>();
			while (rs.next()) {
				Map<K, V> row = new HashMap<K, V>();
				for (String col : columns) {
					row.put((K) col, (V) rs.getObject(col));
				}

				data.add(row);
			}
			System.out.println("DBUtil : executeQuery : result : " + data);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return data;
	}

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static <K, V> void main(String[] args) {
		// Usage Example
		String sql ="SAMPLE SQL QUERY";
		try {
			List<Map<K,V>> resultMap = DBUtil.INSTANCE.executeQuery("jdbc/sqlds", sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
