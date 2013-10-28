package service.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.DbSource;
import service.ProdDAO;
import service.domain.Category;
import service.domain.Product;
public class ProdDAOImpl implements ProdDAO {
	private DbSource db = new DbSourceImpl();
	@Override
	public List<Category> findCats(){
		List<Category> list = new ArrayList<Category>();
		Connection con = db.getCon();
		try {
			ResultSet rs = con.createStatement().executeQuery("select key,name from category");
			while(rs.next()){
				Category cat=new Category();
				cat.setKey(rs.getInt(1));
				cat.setName(rs.getString(2));
				list.add(cat);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		return list;
	}
	@Override
	public List<Product> findProds(){
		List<Product> list = new ArrayList<Product>();
		Connection con = db.getCon();
		try {
			ResultSet rs = con.createStatement().executeQuery("select key,no,name,cat,img,desc,price,onshelf from product");
			while(rs.next()){
				Product prod=new Product();
				prod.setKey(rs.getInt(1));
				prod.setNo(rs.getString(2));
				prod.setName(rs.getString(3));
				prod.setCat(rs.getInt(4));
				prod.setImg(rs.getInt(5));
				prod.setDesc(rs.getString(6));
				prod.setPrice(rs.getInt(7));
				prod.setOnshelf(rs.getInt(8));
				list.add(prod);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		return list;
	}
	@Override
	public List<Product> findProdsByCat(Integer cat){
		List<Product> list = new ArrayList<Product>();
		Connection con = db.getCon();
		try {
			PreparedStatement pstmt = con.prepareStatement("select key,no,name,cat,img,desc,price,onshelf from product where cat=?");
			pstmt.setInt(1,cat);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Product prod=new Product();
				prod.setKey(rs.getInt(1));
				prod.setNo(rs.getString(2));
				prod.setName(rs.getString(3));
				prod.setCat(rs.getInt(4));
				prod.setImg(rs.getInt(5));
				prod.setDesc(rs.getString(6));
				prod.setPrice(rs.getInt(7));
				prod.setOnshelf(rs.getInt(8));
				list.add(prod);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		return list;
	}
	@Override
	public Category findCatByKey(Integer key){
		Category cat=null;
		Connection con = db.getCon();
		try {
			PreparedStatement pstmt = con.prepareStatement("select key,name from category where key=?");
			pstmt.setInt(1,key);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				cat=new Category();
				cat.setKey(rs.getInt(1));
				cat.setName(rs.getString(2));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		return cat;
	}
	@Override
	public Product findProdByKey(Integer key){
		Product prod=null;
		Connection con = db.getCon();
		try {
			PreparedStatement pstmt = con.prepareStatement("select key,no,name,cat,img,desc,price,onshelf from product where key=?");
			pstmt.setInt(1,key);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				prod=new Product();
				prod.setKey(rs.getInt(1));
				prod.setNo(rs.getString(2));
				prod.setName(rs.getString(3));
				prod.setCat(rs.getInt(4));
				prod.setImg(rs.getInt(5));
				prod.setDesc(rs.getString(6));
				prod.setPrice(rs.getInt(7));
				prod.setOnshelf(rs.getInt(8));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		return prod;
	}
	
	@Override
	public Integer insertCat(Category cat){
		Connection con = db.getCon();
		try {
			ResultSet rs = con.createStatement().executeQuery("select max(key) from category");
			if(rs.next()){
				Integer key=rs.getInt(1)+1;
				PreparedStatement pstmt=con.prepareStatement("insert into category(key,name) values(?,?)");
				pstmt.setInt(1,key);
				pstmt.setString(2,cat.getName());
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
	public Integer updateCat(Category cat){
		Connection con = db.getCon();
		try {
			PreparedStatement pstmt=con.prepareStatement("update category set name=? where key=?");
			pstmt.setString(1,cat.getName());
			pstmt.setInt(2,cat.getKey());
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
	public Integer deleteCat(Integer key){
		Connection con = db.getCon();
		try {
			PreparedStatement pstmt=con.prepareStatement("delete from category where key=?");
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
	@Override
	public Integer insertProd(Product prod){
		Connection con = db.getCon();
		try {
			ResultSet rs = con.createStatement().executeQuery("select max(key) from product");
			if(rs.next()){
				Integer key=rs.getInt(1)+1;
				PreparedStatement pstmt=null;
				if(prod.getImg()!=null){
					pstmt= con.prepareStatement("insert into product(key,no,name,cat,img,desc,price,onshelf) values(?,?,?,?,?,?,?,?)");
					pstmt.setInt(1,key);
					pstmt.setString(2,prod.getNo());
					pstmt.setString(3,prod.getName());
					pstmt.setInt(4,prod.getCat());
					pstmt.setInt(5,prod.getImg());
					pstmt.setString(6,prod.getDesc());
					pstmt.setInt(7,prod.getPrice());
					pstmt.setInt(8,prod.getOnshelf());
				}else{
					pstmt= con.prepareStatement("insert into product(key,no,name,cat,desc,price,onshelf) values(?,?,?,?,?,?,?)");
					pstmt.setInt(1,key);
					pstmt.setString(2,prod.getNo());
					pstmt.setString(3,prod.getName());
					pstmt.setInt(4,prod.getCat());
					pstmt.setString(5,prod.getDesc());
					pstmt.setInt(6,prod.getPrice());
					pstmt.setInt(7,prod.getOnshelf());
				}
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
	public Integer updateProd(Product prod){
		Connection con = db.getCon();
		try {
			PreparedStatement pstmt=null;
			if(prod.getImg()!=null){
				pstmt = con.prepareStatement("update product set no=?,name=?,cat=?,img=?,desc=?,price=?,onshelf=? where key=?");
				pstmt.setString(1,prod.getNo());
				pstmt.setString(2,prod.getName());
				pstmt.setInt(3,prod.getCat());
				pstmt.setInt(4,prod.getImg());
				pstmt.setString(5,prod.getDesc());
				pstmt.setInt(6,prod.getPrice());
				pstmt.setInt(7,prod.getOnshelf());
				pstmt.setInt(8,prod.getKey());
			}else{
				pstmt = con.prepareStatement("update product set no=?,name=?,cat=?,desc=?,price=?,onshelf=? where key=?");
				pstmt.setString(1,prod.getNo());
				pstmt.setString(2,prod.getName());
				pstmt.setInt(3,prod.getCat());
				pstmt.setString(4,prod.getDesc());
				pstmt.setInt(5,prod.getPrice());
				pstmt.setInt(6,prod.getOnshelf());
				pstmt.setInt(7,prod.getKey());
			}
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
	public Integer deleteProd(Integer key){
		Connection con = db.getCon();
		try {
			PreparedStatement pstmt=con.prepareStatement("delete from product where key=?");
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