package service;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.domain.Slide;
public interface SlideService {
	int width=300;
	int height=200;
	
	void saveImg(ServletContext context,String path);
	Map<String,String> saveImg(HttpServletRequest req,String imgFile);
	void getImg(HttpServletResponse res,Integer key);
	List<Slide> getImgs();
	String getImgsJSON();
	String updateSlide(Integer key,Integer onshelf);
	String removeSlide(Integer key);
}