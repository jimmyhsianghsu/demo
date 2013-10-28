<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="prodSvc" class="service.impl.ProdServiceImpl" scope="page"/>
<jsp:setProperty name="prodSvc" property="*"/>
<c:set var="cat" value="${prodSvc.catByKey}" scope="page"/>
<h1 class="item"><span class="bg-raw">${cat.name}</span></h1>
<h2 class="level2">${cat.name}</h2>
<ul class="list">
	<c:forEach var="prod" items="${prodSvc.prodsByCat}">
		<li><a href="img.jsp?key=${prod.img}" rel="lightbox[roadtrip1]" title="${prod.name}"><img src="img.jsp?key=${prod.img}" border="0" /></a>${prod.name}</li>
	</c:forEach>
</ul>