package service;
import java.sql.Connection;
public interface DbSource {
	Connection getCon();
	void closeCon(Connection con);
}
