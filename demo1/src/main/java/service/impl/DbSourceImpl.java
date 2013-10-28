package service.impl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import service.DbSource;
public class DbSourceImpl implements DbSource{
	private static final String url="jdbc:hsqldb:mem:demo1";
	private static final String user="abcd";
	private static final String pwd="1234";
	static{
		try {
			Class.forName("org.hsqldb.jdbcDriver" );
		} catch (Exception e) {
			System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
			e.printStackTrace();
		}
	}
	@Override
	public Connection getCon(){
		Connection con=null;
		try {
			con = DriverManager.getConnection(url, user, pwd);
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return con;
	}
	@Override
	public void closeCon(Connection con){
		if(con!=null)
			try {
				con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
	}
}