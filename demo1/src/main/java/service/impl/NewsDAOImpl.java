package service.impl;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import service.DbSource;
import service.NewsDAO;
import service.domain.News;
public class NewsDAOImpl implements NewsDAO{
	private DbSource db = new DbSourceImpl();
	@Override
	public List<News> findNews() {
		List<News> list = new ArrayList<News>();
		Connection con = db.getCon();
		try {
			ResultSet rs = con.createStatement().executeQuery("select key,date,title,content from news order by date desc");
			while(rs.next()){
				News news=new News();
				news.setKey(rs.getInt(1));
				news.setDate(rs.getDate(2));
				news.setTitle(rs.getString(3));
				news.setContent(rs.getString(4));
				list.add(news);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		return list;
	}
	@Override
	public Integer insertNews(News news) {
		Connection con = db.getCon();
		try {
			ResultSet rs = con.createStatement().executeQuery("select max(key) from news");
			if(rs.next()){
				Integer key=rs.getInt(1)+1;
				PreparedStatement pstmt=con.prepareStatement("insert into news(key,date,title,content) values(?,?,?,?)");
				pstmt.setInt(1,key);
				pstmt.setDate(2,new Date(news.getDate().getTime()));
				pstmt.setString(3,news.getTitle());
				pstmt.setString(4,news.getContent());
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
	public Integer updateNews(News news) {
		Connection con = db.getCon();
		try {
			PreparedStatement pstmt=con.prepareStatement("update news set date=?,title=?,content=? where key=?");
			pstmt.setDate(1,new Date(news.getDate().getTime()));
			pstmt.setString(2, news.getTitle());
			pstmt.setString(3, news.getContent());
			pstmt.setInt(4,news.getKey());
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
	public Integer deleteNews(Integer key) {
		Connection con = db.getCon();
		try {
			PreparedStatement pstmt=con.prepareStatement("delete from news where key=?");
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