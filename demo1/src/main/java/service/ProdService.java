package service;
import java.util.List;
import service.domain.Category;
import service.domain.Product;
public interface ProdService {
	void setKey(Integer key);
	void setCat(Integer cat);

	List<Category> getCats();
	List<Product> getProds();
	List<Product> getProdsByCat();
	Category getCatByKey();
	Product getProdByKey();

	String saveCat(String name);
	String updateCat(Integer key,String name);
	String removeCat(Integer key);
	
	String saveProd(String no,String name,Integer cat,Integer img,String desc,Integer price,Integer onshelf);
	String updateProd(Integer key,String no,String name,Integer cat,Integer img,String desc,Integer price,Integer onshelf);
	String removeProd(Integer key);
	
	String getCatsJSON();
	String getProdsJSON();
	String getProdsByCatJSON(Integer cat);
	String getProdByKeyJSON(Integer key);
}