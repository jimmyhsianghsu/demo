<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="prodSvc" class="service.impl.ProdServiceImpl" scope="page"/>
<jsp:useBean id="slideSvc" class="service.impl.SlideServiceImpl" scope="page"/>
<div id="logo"><a href="http://cooler.jimmyhsianghsu.cloudbees.net/"><img src="images/logo.gif" alt="江氏貿易有限公司" border="0" /></a></div>
<div id="menu01"><p class="mailmag"><h1 class="fortop">提供冷藏櫃、冷氣總匯、冷凍冰櫃、握式蛋糕櫃等冷藏設備買賣</h1></p></div>
<div id="smoothmenu1" class="ddsmoothmenu">
	<ul>
		<li><a href="index.jsp">首頁</a></li>
		<li><a href="aboutus.jsp">關於我們</a></li>
		<li><a href="#">產品介紹</a>
			<ul><c:forEach var="cat" items="${prodSvc.cats}"><li><a href="cat.jsp?cat=${cat.key}">${cat.name}</a></li></c:forEach></ul>
		</li>
		<li><a href="news.jsp">最新消息</a></li>
		<li><a href="mailto:service@hk-seafood.tw">聯絡我們</a></li>
	</ul>
	<br style="clear: left" />
</div>
<div id="mainbanner00">
	<div id="abgneBlock" align="absmiddle">
		<ul class="slide">
			<c:forEach var="img" items="${slideSvc.imgs}"><c:if test="${img.onshelf==1}"><li><a target="_blank" href="#"><img src="back/slide?key=${img.key}"></a></li></c:if></c:forEach>
		</ul>
	</div>
	<div class="fb-like" style="float:right;margin-top:3px" data-href="http://cooler.jimmyhsianghsu.cloudbees.net/" data-width="60" data-height="20" data-colorscheme="light" data-layout="button_count" data-action="like" data-show-faces="true" data-send="false"></div>
	<div style="clear:both;"></div>
</div>
<div id="fb-root"></div>
<script>
	(function(d, s, id) {
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id)) return;
		js = d.createElement(s); js.id = id;
		js.src = "//connect.facebook.net/zh_TW/all.js#xfbml=1&appId=404604079665864";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
</script>
<style type="text/css">
	#abgneBlock {
		width: 770px;
		height: 220px;
		position: relative;
		overflow: hidden;
		border: 1px solid #ccc;
	}
	#abgneBlock ul.slide {
		padding: 0;
		margin: 0;
		list-style: none;
		position: absolute;
		width: 9999px;
		height: 100%;
	}
	#abgneBlock ul.slide li {
		float: left;
		width: 770px;
		height: 100%;
	}
	#abgneBlock .slide img{
		width: 100%;
		height: 100%;
		border: 0;
	}
	#abgneBlock ul.playerControl {
		margin: 0;
		padding: 0;
		list-style: none;
		position: absolute;
		bottom: 5px;
		right: 5px;
		height: 14px;
	}
	#abgneBlock ul.playerControl li {
		float: left;
		width: 23px;
		height: 14px;
		cursor: pointer;
		margin: 0px 2px;
		background: url(images/rect_ctrl.png) no-repeat 0 0;
	}
	#abgneBlock ul.playerControl li.current { 
		background-position: -23px 0;
	}
</style>
<script type="text/javascript">
	$(function(){
		// 先取得必要的元素並用 jQuery 包裝
		// 再來取得 $block 的高度及設定動畫時間
		var $block = $('#abgneBlock'),
			$slides = $('ul.slide', $block),
			_width = $block.width(),
			$li = $('li', $slides),
			_animateSpeed = 600, 
			// 加入計時器, 輪播時間及控制開關
			timer, _showSpeed = 3000, _stop = false;

		// 產生 li 選項
		var _str = '';
		for(var i=0, j=$li.length;i<j;i++){
			// 每一個 li 都有自己的 className = playerControl_號碼
			_str += '<li class="playerControl_' + (i+1) + '"></li>';
		}

		// 產生 ul 並把 li 選項加到其中
		var $playerControl = $('<ul class="playerControl"></ul>').html(_str).appendTo($slides.parent()).css('left', function(){
			// 把 .playerControl 移到置中的位置
			return (_width - $(this).width()) / 2;
		});
		
		// 幫 li 加上 click 事件
		var $playerControlLi = $playerControl.find('li').mouseover(function(){
			var $this = $(this);
			$this.addClass('current').siblings('.current').removeClass('current');

			clearTimeout(timer);
			// 移動位置到相對應的號碼
			$slides.stop().animate({
				left: _width * $this.index() * -1
			}, _animateSpeed, function(){
				// 當廣告移動到正確位置後, 依判斷來啟動計時器
				if(!_stop) timer = setTimeout(move, _showSpeed);
			});

			return false;
		}).eq(0).mouseover().end();

		// 如果滑鼠移入 $block 時
		$block.hover(function(){
			// 關閉開關及計時器
			_stop = true;
			clearTimeout(timer);
		}, function(){
			// 如果滑鼠移出 $block 時
			// 開啟開關及計時器
			_stop = false;
			timer = setTimeout(move, _showSpeed);
		});
		
		// 計時器使用
		function move(){
			var _index = $('.current').index();
			$playerControlLi.eq((_index + 1) % $playerControlLi.length).mouseover();
		}
	});
</script>
<!-- http://abgne.tw/jquery/apply-jquery/jquery-left-and-right-horizontal-ad.html<br/>http://demonstration.abgne.tw/jquery/0033/0033_3.html -->