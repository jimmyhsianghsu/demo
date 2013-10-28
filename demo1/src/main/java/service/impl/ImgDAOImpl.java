package service.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.DbSource;
import service.ImgDAO;
public class ImgDAOImpl implements ImgDAO {
	private DbSource db = new DbSourceImpl();
	@Override
	public Integer insertImg(byte[] b) {
		Connection con = db.getCon();
		try {
			ResultSet rs = con.createStatement().executeQuery("select max(key) from img");
			if(rs.next()){
				Integer key=rs.getInt(1)+1;
				PreparedStatement pstmt = con.prepareStatement("insert into img (key,data) values (?,?)");
				pstmt.setInt(1,key);
				pstmt.setBytes(2,b);
				pstmt.execute();
				if(pstmt.getUpdateCount()>0)
					return key;
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		return null;
	}
	@Override
	public Integer insertImg(byte[] b,Integer key){
		Connection con = db.getCon();
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into img (key,data) values (?,?)");
			pstmt.setInt(1,key);
			pstmt.setBytes(2,b);
			pstmt.execute();
			if(pstmt.getUpdateCount()>0)
				return key;
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		return null;
	}
	@Override
	public byte[] readImg(Integer key) {
		Connection con = db.getCon();
		try {
			PreparedStatement pstmt = con.prepareStatement("select data from img where key=?");
			pstmt.setInt(1,key);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
				return rs.getBytes(1);
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		return null;
	}
	@Override
	public List<Integer> findImgs(){
		Connection con = db.getCon();
		List<Integer> list= new ArrayList<Integer>();
		try {
			ResultSet rs=con.createStatement().executeQuery("select key from img");
			while(rs.next())
				list.add(rs.getInt(1));
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		return list;
	}
}