package service.impl;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.NewsDAO;
import service.NewsService;
import service.domain.News;
public class NewsServiceImpl implements NewsService {
	private NewsDAO dao = new NewsDAOImpl();
	@Override
	public List<News> getNews() {return dao.findNews();}
	@Override
	public String getNewsJSON() {
		List<News> list = getNews();
		JSONObject jObj = new JSONObject();
		JSONArray jAry=new JSONArray();
		for(News news:list)
			jAry.put(new JSONObject(news));
		try {
			jObj.put("rows",jAry);
			jObj.put("total",list!=null?list.size():"-1");
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
	@Override
	public String saveNews(Date date, String title, String content) {
		News news = new News();
		news.setDate(date);
		news.setTitle(title);
		news.setContent(content);
		JSONObject jObj=new JSONObject();
		try {
			jObj.put("count",dao.insertNews(news));
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
	@Override
	public String updateNews(Integer key, Date date, String title,String content) {
		News news = new News();
		news.setKey(key);
		news.setDate(date);
		news.setTitle(title);
		news.setContent(content);
		JSONObject jObj=new JSONObject();
		try {
			jObj.put("count",dao.updateNews(news));
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
	@Override
	public String removeNews(Integer key) {
		JSONObject jObj=new JSONObject();
		try {
			jObj.put("count",dao.deleteNews(key));
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
}