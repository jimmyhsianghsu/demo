package servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DbSource;
import service.SlideService;
import service.impl.DbSourceImpl;
import service.impl.SlideServiceImpl;
public class SlideServlet extends HttpServlet {
	private SlideService service = new SlideServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		String key=req.getParameter("key");
		if(key!=null)
			service.getImg(res, Integer.valueOf(key));
		else{
			res.setContentType("text/html; charset=utf8");
			res.getWriter().print(service.getImgsJSON());
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		Map<String,String> map=service.saveImg(req, "imgSlide");
		if(map.get("key")==null)
			res.getWriter().print(map.get("imgSlide"));
		else{
			res.setContentType("text/html; charset=utf8");
			String action=map.get("action");
			if("updateSlide".equals(action))
				res.getWriter().print(service.updateSlide(Integer.valueOf(map.get("key")), Integer.valueOf(map.get("onshelf"))));
			else if("removeSlide".equals(action))
				res.getWriter().print(service.removeSlide(Integer.valueOf(map.get("key"))));
		}
	}
	@Override
	public void init() throws ServletException {
		DbSource db = new DbSourceImpl();
		Connection con=db.getCon();
		try {
			con.createStatement().execute("create table slide (key int primary key,img blob,onshelf int)");
			service.saveImg(getServletContext(),"/back/initSlide");
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
		}
		super.init();
	}
}
