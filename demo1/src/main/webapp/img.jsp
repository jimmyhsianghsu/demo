<%@page contentType="image/gif" trimDirectiveWhitespaces="true"%>
<% new service.impl.ImgServiceImpl().getImg(response,Integer.valueOf(request.getParameter("key")));%>