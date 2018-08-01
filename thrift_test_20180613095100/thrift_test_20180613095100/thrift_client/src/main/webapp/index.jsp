<html>
<body>
<%
    String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<li><a href="<%=contextPath%>/ThriftClientTestController/test001" target="_blank">ThriftClientTestController-test001</a></li>
</body>
</html>
