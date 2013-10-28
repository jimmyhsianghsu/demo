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
import service.ImgDAO;
import service.ImgService;
public class ImgServiceImpl implements ImgService {
	private ImgDAO imgDao= new ImgDAOImpl();
	@Override
	public boolean isMultipartContent(HttpServletRequest req){
		return ServletFileUpload.isMultipartContent(req);
	}
	@Override
	public void saveImg(ServletContext context,String root){
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
			imgDao.insertImg(baos.toByteArray(), Integer.valueOf(key));
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
					value=String.valueOf(imgDao.insertImg(baos.toByteArray()));
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
	public void getImg(HttpServletResponse res,Integer key) {
		byte[] b=imgDao.readImg(key);
		if(b!=null)
			try {
				res.getOutputStream().write(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	@Override
	public List<Integer> getImgs(){
		return imgDao.findImgs();
	}
	@Override
	public String getImgsJSON(){
		List<Integer>list =getImgs();
		JSONObject jObj = new JSONObject();
		JSONArray jAry=new JSONArray();
		for(Integer i:list){
			Map<String,String> map = new HashMap<String,String>();
			map.put("key",String.valueOf(i));
			map.put("img","<img src='../img.jsp?key="+i+"' width='"+width+"px' height='"+height+"px'/>");
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
}