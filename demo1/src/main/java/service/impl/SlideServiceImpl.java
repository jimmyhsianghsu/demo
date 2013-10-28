package service.impl;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.SlideDAO;
import service.SlideService;
import service.domain.Category;
import service.domain.Slide;
public class SlideServiceImpl implements SlideService {
	private SlideDAO dao=new SlideDAOImpl();
	@Override
	public void saveImg(ServletContext context, String root) {
		Set<String> paths=context.getResourcePaths(root);
		for(String path:paths){
			String name=path.substring(path.lastIndexOf('/')+1);
			String key=name.substring(0,name.indexOf('.'));
			InputStream is = context.getResourceAsStream(path);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] b=new byte[1024];
			int n =0;
			try {
				while((n=is.read(b))>0)
					baos.write(b,0,n);
			} catch (IOException e) {
				e.printStackTrace();
			}
			dao.insertImg(baos.toByteArray(), Integer.valueOf(key));
		}
	}
	@Override
	public Map<String,String> saveImg(HttpServletRequest req,String imgFile) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			FileItemIterator iterator = new ServletFileUpload().getItemIterator(req);
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				InputStream is = item.openStream();
				String name=item.getFieldName();
				String value=null;
				if(item.isFormField())
					value=Streams.asString(is,"UTF-8");
				else if(name.equals(imgFile)){
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] b=new byte[1024];
					int n =0;
					while((n=is.read(b))>0)
						baos.write(b,0,n);
					JSONObject jObj= new JSONObject();
					try {
						jObj.put("count", dao.insertImg(baos.toByteArray()));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					value=jObj.toString();
				}
				map.put(name,value);
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	@Override
	public void getImg(HttpServletResponse res, Integer key) {
		byte[] b=dao.readImg(key);
		if(b!=null)
			try {
				res.getOutputStream().write(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	@Override
	public List<Slide> getImgs() {
		return dao.findImgs();
	}
	@Override
	public String getImgsJSON() {
		List<Slide>list =getImgs();
		JSONObject jObj = new JSONObject();
		JSONArray jAry=new JSONArray();
		for(Slide i:list){
			Map<String,String> map = new HashMap<String,String>();
			map.put("key",String.valueOf(i.getKey()));
			map.put("img","<img src='../back/slide?key="+i.getKey()+"' width='"+width+"px' height='"+height+"px'/>");
			map.put("onshelf",String.valueOf(i.getOnshelf()));
			jAry.put(new JSONObject(map));
		}
		try {
			jObj.put("rows",jAry);
			jObj.put("total",list!=null?list.size():"-1");
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
	@Override
	public String updateSlide(Integer key,Integer onshelf){
		Slide slide = new Slide();
		slide.setKey(key);
		slide.setOnshelf(onshelf);
		JSONObject jObj=new JSONObject();
		try {
			jObj.put("count",dao.updateSlide(slide));
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
	@Override
	public String removeSlide(Integer key){
		JSONObject jObj=new JSONObject();
		try {
			jObj.put("count",dao.deleteSlide(key));
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
}