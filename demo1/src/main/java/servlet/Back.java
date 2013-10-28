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
		try {
			con.createStatement().execute("create table img (key int primary key,data blob)");
			con.createStatement().execute("create table category (key int primary key,name varchar(20))");
			con.createStatement().execute("create table product (key int primary key,no varchar(20) unique,name varchar(20),cat int references category(key),img int references img(key),desc varchar(300),price int,onshelf int)");
			con.createStatement().execute("create table news (key int primary key,date Date,title varchar(50),content varchar(500))");
			con.createStatement().execute("insert into category values(1,'大型冷凍庫')");
			con.createStatement().execute("insert into category values(2,'中型冷凍庫')");
			con.createStatement().execute("insert into category values(3,'小型冷凍庫')");
			con.createStatement().execute("insert into category values(4,'旅行冷凍庫')");
			imgSvc.saveImg(getServletContext(),"/back/initImg");
			con.createStatement().execute("insert into product values(1,'P1','經濟耐久',1,1,'this is P1',100,0)");
			con.createStatement().execute("insert into product values(2,'P2','美觀實用',1,2,'this is P2',200,0)");
			con.createStatement().execute("insert into product values(3,'P3','不鏽鋼冷凍',1,3,'this is P3',300,1)");
			con.createStatement().execute("insert into product values(4,'P4','組合式冷凍',2,4,'this is P4',400,1)");
			con.createStatement().execute("insert into product values(5,'P5','高品質服務',2,5,'this is P5',400,0)");
			con.createStatement().execute("insert into product values(6,'P6','企業經營',2,6,'this is P6',400,0)");
			con.createStatement().execute("insert into product values(7,'P7','鹽化鋼板',3,7,'this is P7',400,1)");
			con.createStatement().execute("insert into product values(8,'P8','高密度硬質PU',3,8,'this is P8',400,0)");
			con.createStatement().execute("insert into product values(9,'P9','保溫效能',3,9,'this is P9',400,1)");
			con.createStatement().execute("insert into product values(10,'P10','耐鹽',4,10,'this is P10',400,0)");
			con.createStatement().execute("insert into product values(11,'P11','耐酸',4,11,'this is P11',400,1)");
			con.createStatement().execute("insert into product values(12,'P12','可調整式置物網架',4,12,'this is P12',400,0)");
			con.createStatement().execute("insert into product values(13,'P13','除霜',2,13,'this is P13',400,1)");
			con.createStatement().execute("insert into news values(1,'1999-9-9','最新消息一','歡迎光臨!!!!!')");
			con.createStatement().execute("insert into news values(2,'2013-10-07','素食明顯減少代謝症候群的風險','代謝症候群的致命四重湊是三酸甘油酯、血糖、血壓和腰圍，會增加糖尿病、心血管疾病、中風和癌症的風險。2010年的研究顯示飲食的模式與代謝症候群有關，但是那一種飲食模式賦予最低的代謝症候群風險則尚無定論...')");
			con.createStatement().execute("insert into news values(3,'2013-10-07','瑜伽、鮮菇 有助調節免疫力','瑜伽、鮮菇 有助調節免疫力◎記者 萬博超 2013/10/01調整字級：   --------------------------------------------------------------------------------想改善免疫力，除了睡眠、飲食外，運動亦是關鍵，然而不同運動對基因的影響也有差異。一項挪威研究顯示，瑜伽可以改善基因表達，增加體內重要免疫細胞-「自然殺手細胞」（NKcell, Natural killer cell）的活性。自然殺手細胞是對抗惡性腫瘤重要的免疫機制之一...')");
			con.createStatement().execute("insert into news values(4,'2013-10-07','《名人開講》 哈莉。諾曼','《名人開講》 哈莉。諾曼由 peace 在 二, 05/14/2013 - 18:50 發表 哈莉．諾曼 非裔芭比娃娃的製作就是以哈莉．諾曼的形相為雛形。從小她就已經展現演藝的天份，從少女時期擔任時裝模特兒開始，到今天成為知名的演員，她所經歷的幸運過程，我們只能說得天獨厚，然而得到上天的眷顧，也是其來有自。她除了具有令人炫目的外表之外，最重要的是她擁有一顆很大的愛心。在許多愛護動物的場合，都可以看到她的身影，選擇純素飲食正是她愛護動物最直接、最美麗的宣告。在生活的每一刻，她也不忘和人分享純素生活的美好，也希望人人都能擺脫動物食品之害、遠離藥物，過個純淨、沒有病痛的生活。哈莉。諾曼   * 談為何素食(英文)      * Dreaming Big with Hayley Marie Norman')");
			con.createStatement().execute("insert into news values(5,'2013-09-17','運動後不做白工　4種食物別亂吃','好不容易辛苦運動1個小時，消耗掉許多卡路里，逐步邁向自己夢想中的體型，卻不慎在運動後胡亂吃進熱量高、不健康的食物，讓你把辛苦運動的收穫付諸流水，得不償失猶如在做白工。究竟運動後哪些東西碰不得呢？美國健康網站epyk幫你整理4大種類的地雷食物。1. 拒絕誘人的鹹味小點心...')");
			con.createStatement().execute("insert into news values(6,'2013-09-13','降低肝指數　自製寬心飲保肝健脾','慢性肝炎是台灣的國病，許多患者尋找各類偏方時常被制止，認為療效缺乏科學根據，不過一名40多歲的男性上班族罹患B型肝炎，因工作忙碌、長期熬夜，肝功能指數高達500U/L，超過正常值的百倍，服用經醫師處方的「寬心飲」補脾扶正後，肝指數恢復正常。而有關寬心飲的療效研究，日前已刊登於國際期刊，證實可改善肝功能...')");
			con.createStatement().execute("insert into news values(7,'2013-09-11','調節免疫力　5種體質茶飲這樣喝','秋天到了，覺得自己總是乏力、想睡還容易感冒嗎？中醫師指出，秋天是季節由涼轉冷之際，處於變化狀態，空氣變得乾燥，而「燥氣」傷肺，會引起口乾舌燥、乾咳，有人還會出現皮膚過敏、呼吸道過敏、感冒等症狀，此時想要提升自體免疫力，不應吃過補的藥方，可依體質喝潤肺茶飲，在養肺同時提升免疫力...')");
		} catch (SQLException se) {
			se.printStackTrace();
		}finally{
			db.closeCon(con);
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