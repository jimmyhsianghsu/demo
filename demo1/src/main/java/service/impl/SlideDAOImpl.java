package service.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import service.DbSource;
import service.SlideDAO;
import service.domain.Slide;
public class SlideDAOImpl implements SlideDAO {
	private DbSource db = new DbSourceImpl();
	@Override
	public Integer insertImg(byte[] b) {
		Connection con = db.getCon();
		try {
			ResultSet rs = con.createStatement().executeQuery("select max(key) from slide");
			if(rs.next()){
				Integer key=rs.getInt(1)+1;
				PreparedStatement pstmt = con.prepareStatement("insert into slide (key,img,onshelf) values (?,?,1)");
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
			PreparedStatement pstmt = con.prepareStatement("insert into slide (key,img,onshelf) values (?,?,1)");
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
			PreparedStatement pstmt = con.prepareStatement("select img from slide where key=?");
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
	public List<Slide> findImgs() {
		Connection con = db.getCon();
		List<Slide> list= new ArrayList<Slide>();
		try {
			ResultSet rs=con.createStatement().executeQuery("select key,onshelf from slide");
			while(rs.next()){
				Slide slide=new Slide();
				slide.setKey(rs.getInt(1));
				slide.setOnshelf(rs.getInt(2));
				list.add(slide);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		return list;
	}
	@Override
	public Integer updateSlide(Slide slide){
		Connection con = db.getCon();
		try {
			PreparedStatement pstmt=con.prepareStatement("update slide set onshelf=? where key=?");
			pstmt.setInt(1,slide.getOnshelf());
			pstmt.setInt(2,slide.getKey());
			pstmt.execute();
			return pstmt.getUpdateCount();
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		return null;
	}
	@Override
	public Integer deleteSlide(Integer key){
		Connection con = db.getCon();
		try {
			PreparedStatement pstmt=con.prepareStatement("delete from slide where key=?");
			pstmt.setInt(1, key);
			pstmt.execute();
			return pstmt.getUpdateCount();
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		return null;
	}
}