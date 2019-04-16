<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商品界面</title>
</head>
<body>
    <h1>修改商品</h1>
    <s:actionmessage/>
    <s:form action="product_update" method="post" namespace="/" theme="simple">
        <table width="600px">
            <tr>
                <th>商品名称</th>
                <td>
                <s:hidden name="id" value="#request.p.id"></s:hidden>
                <s:textfield name="pname" value="#request.p.name"/></td>
            </tr>
            <tr>
                <th>商品价格</th>
                <td><s:textfield name="price" value="#request.p.price"/></td>
            </tr>
            <tr>
                <th colspan="2">
                    <input type="submit" value="保存"/>
                </th>
            </tr>
        </table>
    </s:form>
</body>
</html>