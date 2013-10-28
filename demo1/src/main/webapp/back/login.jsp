<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html> -->
<html>
<head>
    <title>後端管理系統-登入</title>
	<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
    <link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../easyui/demo.css">
    <script type="text/javascript" src="../easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<div class="easyui-dialog" title="登入" data-options="iconCls:'icon-tip'" style="width:400px;height:230px;padding:10px">
        <div style="padding:10px 0 10px 60px">
	        <form action="main" method="post">
	            <table>
	                <tr><td>帳號:</td><td><input class="easyui-validatebox" type="text" name="name" data-options="required:true"></input></td></tr>
	                <tr><td>密碼:</td><td><input class="easyui-validatebox" type="password" name="pwd" data-options="required:true"></input></td></tr>
	                <tr><td colspan="2"><input type="hidden" name="action" value="login"/></td></tr>
	                <tr><td colspan="2"><span style="color:red;font:large bold">${errMsg}</span></td></tr>
	            </table>
	        </form>
        </div>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('form')[0].submit();">登入</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('form').form('clear');">清除</a>
        </div>
    </div>
</body>
</html>