package servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.DbSource;
import service.ProdService;
import service.impl.DbSourceImpl;
import service.impl.ImgServiceImpl;
import service.impl.ProdServiceImpl;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import service.NewsService;
import service.impl.NewsServiceImpl;
import service.domain.User;
public class Back extends HttpServlet {
	private ImgServiceImpl imgSvc= new ImgServiceImpl();
	private ProdService prodSvc = new ProdServiceImpl();
	private NewsService newsSvc = new NewsServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		if(!login(req,res))return;
		res.setContentType("text/html; charset=utf8");
		String action = req.getParameter("action");
		String cat = req.getParameter("cat");
		String key = req.getParameter("key");
		if("getUser".equals(action))
			res.getWriter().print(((User)req.getSession().getAttribute("user")).getName());
		else if("getImgsJSON".equals(action))
			res.getWriter().print(imgSvc.getImgsJSON());
		else if("getCatsJSON".equals(action))
			res.getWriter().print(prodSvc.getCatsJSON());
		else if("getProdsJSON".equals(action))
			res.getWriter().print(prodSvc.getProdsJSON());
		else if("getProdsByCatJSON".equals(action))
			res.getWriter().print(prodSvc.getProdsByCatJSON(Integer.valueOf(cat)));
		else if("getProdByKeyJSON".equals(action))
			res.getWriter().print(prodSvc.getProdByKeyJSON(Integer.valueOf(key)));
		else if("getNewsJSON".equals(action))
			res.getWriter().print(newsSvc.getNewsJSON());
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		if(!login(req,res))return;
		res.setContentType("text/html; charset=utf8");
		if(imgSvc.isMultipartContent(req)){
			Map<String,String> map=imgSvc.saveImg(req,"imgFile");
			String img = map.get("imgFile");
			if(img==null)img=map.get("img");
			Integer intImg=null;
			if(img!=null && !img.isEmpty() && !img.equals("0"))intImg=Integer.valueOf(img);
			
			String action = map.get("action");
			String key = map.get("key");
			String no = map.get("no");
			String name=map.get("name");
			String cat= map.get("cat");
			String desc= map.get("desc");
			String price= map.get("price");
			String onshelf= map.get("onshelf");
			String dateString=map.get("date");
			String title=map.get("title");
			String content=map.get("content");
			
			Date date=null;
			if(dateString!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date=sdf.parse(dateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			if("saveCat".equals(action))
				res.getWriter().print(prodSvc.saveCat(name));
			else if("updateCat".equals(action))
				res.getWriter().print(prodSvc.updateCat(Integer.valueOf(key),name));
			else if("removeCat".equals(action))
				res.getWriter().print(prodSvc.removeCat(Integer.valueOf(key)));
			else if("saveProd".equals(action))
				res.getWriter().print(prodSvc.saveProd(no, name, Integer.valueOf(cat),intImg, desc, Integer.valueOf(price),Integer.valueOf(onshelf)));
			else if("updateProd".equals(action))
				res.getWriter().print(prodSvc.updateProd(Integer.valueOf(key), no, name, Integer.valueOf(cat),intImg, desc, Integer.valueOf(price), Integer.valueOf(onshelf)));
			else if("removeProd".equals(action))
				res.getWriter().print(prodSvc.removeProd(Integer.valueOf(key)));
			else if("saveNews".equals(action))
				res.getWriter().print(newsSvc.saveNews(date,title,content));
			else if("updateNews".equals(action))
				res.getWriter().print(newsSvc.updateNews(Integer.valueOf(key),date,title,content));
			else if("removeNews".equals(action))
				res.getWriter().print(newsSvc.removeNews(Integer.valueOf(key)));
		}
	}
	@Override
	public void init() throws ServletException {
		DbSource db = new DbSourceImpl();
		Connection con=db.getCon();
		BufferedReader br=null;
		try {
			con.createStatement().execute("create table img (key int primary key,data blob)");
			imgSvc.saveImg(getServletContext(),"/back/initImg");
			br=new BufferedReader(new InputStreamReader(getServletContext().getResourceAsStream("/back/initDb.txt"),"utf8"));
			String s=null;
			while((s=br.readLine())!=null)
				con.createStatement().execute(s);
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
			if(br!=null)
				try {br.close();} catch (IOException e) {e.printStackTrace();}
		}
		super.init();
	}
	private boolean login(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException{
		User user=(User)req.getSession().getAttribute("user");
		String action=req.getParameter("action");
		if(user==null){
			String name=req.getParameter("name");
			String pwd = req.getParameter("pwd");
			String errMsg=null;
			if(name==null)
				errMsg="請輸入帳號!";
			else if(pwd==null)
				errMsg="請輸入密碼!";
			else if(!"abcd".equals(name))
				errMsg="帳號錯誤!";
			else if(!"1234".equals(pwd))
				errMsg="密碼錯誤!";
			else
				req.getSession().setAttribute("user", new User(name,pwd));
			if(errMsg!=null){
				req.setAttribute("errMsg",errMsg);
				req.getRequestDispatcher("/back/login.jsp").forward(req,res);
				return false;
			}else if("login".equals(action)){
				req.getRequestDispatcher("/WEB-INF/page/main.html").forward(req,res);
				return false;
			}
		}
		if("login".equals(action) || "logout".equals(action)){
			req.getSession().invalidate();
			req.getRequestDispatcher("/back/login.jsp").forward(req,res);
			return false;
		}
		return true;
	}
}