package service.impl;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import service.ProdDAO;
import service.ProdService;
import service.domain.Category;
import service.domain.Product;
public class ProdServiceImpl implements ProdService {
	private ProdDAO dao = new ProdDAOImpl();

	private Integer key;
	private Integer cat;
	@Override
	public void setKey(Integer key) {this.key = key;}
	@Override
	public void setCat(Integer cat) {this.cat = cat;}
	@Override
	public List<Category> getCats(){return dao.findCats();}
	@Override
	public List<Product> getProds(){return dao.findProds();}
	@Override
	public List<Product> getProdsByCat(){return dao.findProdsByCat(cat);}
	@Override
	public Category getCatByKey(){return dao.findCatByKey(cat);}
	@Override
	public Product getProdByKey(){return dao.findProdByKey(key);}
	@Override
	public String getCatsJSON(){
		List<Category> list = getCats();
		JSONObject jObj = new JSONObject();
		JSONArray jAry=new JSONArray();
		for(Category cat:list)
			jAry.put(new JSONObject(cat));
		try {
			jObj.put("rows",jAry);
			jObj.put("total",list!=null?list.size():"-1");
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
	@Override
	public String getProdsJSON(){
		List<Product> list = getProds();
		JSONObject jObj = new JSONObject();
		JSONArray jAry=new JSONArray();
		for(Product prod:list)
			jAry.put(new JSONObject(prod));
		try {
			jObj.put("rows",jAry);
			jObj.put("total",list!=null?list.size():"-1");
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
	@Override
	public String getProdsByCatJSON(Integer cat){
		this.cat=cat;
		List<Product> list = getProdsByCat();
		JSONObject jObj = new JSONObject();
		JSONArray jAry=new JSONArray();
		for(Product prod:list)
			jAry.put(new JSONObject(prod));
		try {
			jObj.put("rows",jAry);
			jObj.put("total",list!=null?list.size():"-1");
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
	@Override
	public String getProdByKeyJSON(Integer key){
		this.key=key;
		Product prod =getProdByKey();
		if(prod!=null)
			return new JSONObject(prod).toString();
		return null;
	}
	@Override
	public String saveCat(String name) {
		Category cat = new Category();
		cat.setName(name);
		JSONObject jObj=new JSONObject();
		try {
			jObj.put("count",dao.insertCat(cat));
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
	@Override
	public String updateCat(Integer key, String name) {
		Category cat = new Category();
		cat.setKey(key);
		cat.setName(name);
		JSONObject jObj=new JSONObject();
		try {
			jObj.put("count",dao.updateCat(cat));
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
	@Override
	public String removeCat(Integer key) {
		JSONObject jObj=new JSONObject();
		try {
			jObj.put("count",dao.deleteCat(key));
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
	@Override
	public String saveProd(String no,String name,Integer cat,Integer img,String desc,Integer price,Integer onshelf){
		Product prod = new Product();
		prod.setNo(no);
		prod.setName(name);
		prod.setCat(cat);
		prod.setImg(img);
		prod.setDesc(desc);
		prod.setPrice(price);
		prod.setOnshelf(onshelf);
		JSONObject jObj=new JSONObject();
		try {
			jObj.put("count",dao.insertProd(prod));
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
	@Override
	public String updateProd(Integer key,String no,String name,Integer cat,Integer img,String desc,Integer price,Integer onshelf){
		Product prod = new Product();
		prod.setKey(key);
		prod.setNo(no);
		prod.setName(name);
		prod.setCat(cat);
		prod.setImg(img);
		prod.setDesc(desc);
		prod.setPrice(price);
		prod.setOnshelf(onshelf);
		JSONObject jObj=new JSONObject();
		try {
			jObj.put("count",dao.updateProd(prod));
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
	@Override
	public String removeProd(Integer key){
		JSONObject jObj=new JSONObject();
		try {
			jObj.put("count",dao.deleteProd(key));
		} catch (JSONException je) {
			je.printStackTrace();
		}
		return jObj.toString();
	}
}