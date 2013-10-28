package service;
import java.util.Date;
import java.util.List;
import service.domain.News;
public interface NewsService {
	List<News> getNews();
	String saveNews(Date date,String title,String content);
	String updateNews(Integer key,Date date,String title,String content);
	String removeNews(Integer key);
	
	String getNewsJSON();
}