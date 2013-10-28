<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="newsSvc" class="service.impl.NewsServiceImpl" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="head3.jsp"/>

<body>
<div class="all">
<jsp:include page="menu.jsp"/>

	<div id="middle" class="middle_bg">
		<h1 class="item"><span class="bg-raw">關於我們</span></h1>
    	<div class="middle_bg_min">
			<div class="seabg">
				<p>海康貿易成立於2011年11月，後繼因業務上需求於2012年6月設立高雄據點，主要營業項目為進口高級日本空運冰鮮海鮮食材、以「近乎苛求」的品質理念與要求，成為海康堅實的經營基石。</p>
				<p>結合台灣市場與「健康、美味、精緻」高級日式料理、極品鍋物、鐵板燒及法義式料理，日漸風行的潮流下，海康一本以健康及細膩貼心的服務為導向，進而能在顧客的愛顧及同仁的共同努力下，持續追求無窮無盡的高品質給予餐飲界的伙伴們。</p>
				<p>海康始終秉持著「高品質，合理價位」的經營理念、並基於希望國人能有機會與全球先進國家同步品嚐到高品質食品的概念、提供國內餐飲市場海洋健康融合的滋味及饕客們品鮮的味蕾，帶給國人「吃的安心，又健康」的海鮮產品。</p>
				<p><font color="#000080"><b>海康的精神~~</b></font></p>
				<p><font color="#0000FF"><b>品質的堅持&nbsp;&nbsp;&nbsp;&nbsp; 熱忱的服務 </b></font></p>
				<p style="text-align: right">海康全體員工 </p>
			</div>
			<div class="middle_bg_footer">&nbsp;</div>
		</div>
	</div>
	<div class="clear" style="height:1px; overflow:hidden;">&nbsp;</div>
  	<div id="footer">
		<div class="item"><span><strong>海康貿易有限公司</strong></span></div>
		<table border="0" cellspacing="5" cellpadding="0" style="margin-left:10px;" class="f12px">
			<tr>
				<td align="left" valign="top">台北公司：</td>
				<td align="left" valign="top">10474 台北市中山區民族東路410巷23弄11號1樓 電話： 02-2503-3947   傳真： 02-2507-1672 </td>
			</tr>
			<tr>
				<td align="left" valign="top">高雄公司：</td>
				<td align="left" valign="top">高雄市仁武區八德東路871之1號D棟 電話： 07-3725918   傳真： 07-3724939 </td>
			</tr>
		</table>
	</div>
	<div style="clear:both;"></div>
	
</div>
<jsp:include page="footer.html"/>
</body>
</html>