<%@page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head></head>
<body>
<form action="/IEIGInterface/test.do" method="post" name="testform">
	<div>
		<ul>
			<li><a href="/IEIGInterface/test.do?method=test">测试</a></li>
		</ul>
		<ul>
			<li><a href="/IEIGInterface/test.do?method=FK&appNo=9527">【负控】模拟调试：请修改TestAction 中 updateDa方法的 appNo的值</a></li>
		</ul>
		<ul>
			<li><a href="/IEIGInterface/test.do?method=JC&appNo=100000000002">【集抄】模拟调试：请修改TestAction 中 updateDa方法的 appNo的值</a></li>
		</ul>
		<ul>
			<li><a href="/IEIGInterface/test.do?method=backfeed">反馈营销</a></li>
		</ul>
		<ul>
			<li>
			  <a href="/IEIGInterface/test.do?method=coherenceStatement">加载分布式缓存</a>
			</li>
		</ul>
		<ul>
			<li>
			<a href="/IEIGInterface/test.do?method=isOnline">判断是否在线</a>
			</li>
		</ul>
	</div>
</form>
</body>
</html>