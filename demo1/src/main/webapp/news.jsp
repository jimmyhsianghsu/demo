<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="newsSvc" class="service.impl.NewsServiceImpl" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="head3.jsp"/>

<body>
<div class="all">
<jsp:include page="menu.jsp"/>

	<div id="middle">
		<h1 class="item"><span class="bg-raw">最新消息</span></h1>
		<h2 class="level2">最新消息</h2>
		<div class="commCont newsBox">
			<div class="list">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<caption><span>&nbsp;&nbsp;日&nbsp;期</span><span class="w85">標&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;題</span></caption>
					<c:forEach var="news" items="${newsSvc.news}">
						<tr class="newsTr"><td width="18%" align="center" valign="top">${news.date}</td><td width="10">&nbsp;</td><td><strong class="obj"><a href="#" title="詳全文">${news.title}</a></strong>${news.content}</td></tr>						
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div style="clear:both;"></div>
	
</div>
<jsp:include page="footer.html"/>
</body>
</html>