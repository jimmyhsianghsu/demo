package service.domain;
public class Product {
	private Integer key;
	private String no;
	private String name;
	private Integer cat;
	private Integer img;
	private String desc;
	private Integer price;
	private Integer onshelf;
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCat() {
		return cat;
	}
	public void setCat(Integer cat) {
		this.cat = cat;
	}
	public Integer getImg() {
		return img;
	}
	public void setImg(Integer img) {
		this.img = img;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getOnshelf() {
		return onshelf;
	}
	public void setOnshelf(Integer onshelf) {
		this.onshelf = onshelf;
	}

}
