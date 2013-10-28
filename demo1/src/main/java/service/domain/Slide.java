package service.domain;
public class Slide {
	private Integer key;
	private byte[] img;
	private Integer onshelf;
	public Integer getKey() {return key;}
	public void setKey(Integer key) {this.key = key;}
	public byte[] getImg() {return img;}
	public void setImg(byte[] img) {this.img = img;}
	public Integer getOnshelf() {return onshelf;}
	public void setOnshelf(Integer onshelf) {this.onshelf = onshelf;}
}