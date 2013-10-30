<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="head1.jsp"/>
<script>$.get('back/main');$.get('back/slide');</script>
<body>
<div class="all">
<jsp:include page="menu.jsp"/>

	<div id="middle">
		<div class="contentstop"  style="border:#CCCCCC solid 1px;"><img src="images/index-map.jpg" width="550" height="321" /></div>
		<h2 class="item"><span>公司資訊</span></h2>
		<div class="contentstop">
			<table border="0" cellspacing="5" cellpadding="0" style="margin-left:10px;">
				<tr>
					<td valign="top"><strong>海康貿易有限公司</strong></td>
					<td valign="top"><strong>台北公司</strong>：</td>
					<td valign="top">10474 台北市中山區民族東路410巷23弄11號1樓 <br />電話： 02-2503-3947   傳真： 02-2507-1672 </td>
				</tr>
        		<tr>
					<td valign="top">&nbsp;</td>
					<td valign="top"><strong>高雄公司</strong>：</td>
					<td valign="top">高雄市仁武區八德東路871之1號D棟 <br />電話： 07-3725918   傳真： 07-3724939 </td>
        		</tr>
			</table>
		</div>
	</div>
	<div id="sidebanner" style="border:#CCCCCC solid 1px;">
		<p class="contentsbanner"><a href="#"><span>日本空運新鮮食材:干貝</span><img src="images/bannerimg1.jpg" border="0"/></a></p>
		<p class="contentsbanner"><a href="#"><span>日本空運新鮮食材:赤上海膽</span><img src="images/bannerimg2.jpg" border="0"/></a></p>
		<p class="contentsbanner"><a href="#"><span>日本空運新鮮食材:毛蟹</span><img src="images/bannerimg3.jpg" border="0"/></a></p>
		<p class="contentsbanner"><a href="#"><span>日本空運新鮮食材:大葉</span><img src="images/bannerimg4.jpg" border="0"/></a></p>
	</div>
	<div style="clear:both;"></div>

</div>
<jsp:include page="footer.html"/>
</body>
</html>
<!-- http://www.hk-seafood.tw/<br/>http://www.cbx.com.tw/news.php<br/>http://www.fansio.com/keyword/freezerbox/about.aspx -->
<%
Object o=session.getAttribute("session");
Object o1=application.getAttribute("count");
Object o2=application.getAttribute("countAll");
Object o3=application.getAttribute("today");
Integer c1=null;
Integer c2=null;
Long c3=null;
if(o1==null)c1=new Integer(0);else c1=Integer.valueOf(o1.toString());
if(o2==null)c2=new Integer(0);else c2=Integer.valueOf(o2.toString());
if(o3==null)c3=new Long(java.sql.Date.valueOf(new java.sql.Date(System.currentTimeMillis()).toString()).getTime());else c3=Long.valueOf(o3.toString());
if(o==null){
	session.setAttribute("session",new service.domain.User());
	Long c4=java.sql.Date.valueOf(new java.sql.Date(System.currentTimeMillis()).toString()).getTime();
	if(c4>c3)c1++;
	c2++;
}
application.setAttribute("count",c1);
application.setAttribute("countAll",c2);
application.setAttribute("today",c3);
%>
<%=c1%><br/><%=c2%>