package service;
import java.util.List;
import service.domain.News;
public interface NewsDAO {
	List<News> findNews();
	Integer insertNews(News news);
	Integer updateNews(News news);
	Integer deleteNews(Integer key);
}