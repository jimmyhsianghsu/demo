package service;
import java.util.List;
public interface ImgDAO {
	Integer insertImg(byte[] b);
	Integer insertImg(byte[] b,Integer key);
	byte[] readImg(Integer key);
	List<Integer> findImgs();
}