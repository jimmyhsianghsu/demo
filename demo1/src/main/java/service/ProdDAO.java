package service;

import java.util.List;

import service.domain.Category;
import service.domain.Product;

public interface ProdDAO {
	List<Category> findCats();
	List<Product> findProds();
	List<Product> findProdsByCat(Integer cat);
	Category findCatByKey(Integer key);
	Product findProdByKey(Integer key);
	
	Integer insertCat(Category cat);
	Integer updateCat(Category cat);
	Integer deleteCat(Integer key);
	
	Integer insertProd(Product prod);
	Integer updateProd(Product prod);
	Integer deleteProd(Integer key);
}