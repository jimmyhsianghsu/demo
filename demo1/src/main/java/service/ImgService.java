package service;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public interface ImgService {
	int width=300;
	int height=200;
	boolean isMultipartContent(HttpServletRequest req);
	void saveImg(ServletContext context,String path);
	Map<String,String> saveImg(HttpServletRequest req,String imgFile);
	void getImg(HttpServletResponse res,Integer key);
	List<Integer> getImgs();
	String getImgsJSON();
}