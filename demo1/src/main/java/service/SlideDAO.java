package service;
import java.util.List;
import service.domain.Slide;
public interface SlideDAO {
	Integer insertImg(byte[] b);
	Integer insertImg(byte[] b,Integer key);
	byte[] readImg(Integer key);
	List<Slide> findImgs();
	Integer updateSlide(Slide slide);
	Integer deleteSlide(Integer key);
}