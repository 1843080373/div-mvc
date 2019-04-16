<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品界面</title>
</head>
<body>
	<h1>商品</h1>
	<s:actionmessage />
	<table width="600px">
		<tr>
			<th>商品名称</th>
			<th>价格</th>
			<th>操作</th>
		</tr>
		<s:iterator value="#request.list" status="p">
			<tr>
				<th><s:property value="name"/></th>
				<td><s:property value="price"/></td>
				<td><a href="./product_get.action?id=1">修改</a>&nbsp;|&nbsp;<a  href="./product_delete.action?id=1">删除</a></td>
			</tr>
		</s:iterator>
	</table>
</body>
</html>