<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<#include "/templates/common.ftl">
<title>知乎日报 - 满足你的好奇心</title>
<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/zhihu.css" />
</head>

<body>
	<div class="global-header">
		<a title="知乎日报" href="zhihu"> 
		<img class="main-wrap" src="${contextPath}/resources/image/zhihu_logo.png"></a>
	</div>
	<div id="content">
		<#list newsList as news>
			<div class="date">${news.dateDisplay}</div>
			<div>
				<ul id="box" class="box">
					<#list news.news as item>
						<a href="${contextPath}/zhihu/story/${item.id}">
							<li><img src='${item.image}' alt='' class="image" />
								<p class="title">${item.title}</p></li>
						</a>
					</#list>
				</ul>
			</div>
		</#list>
	</div>

	<div class="pages">
		<a href="${contextPath}/zhihu/day/${before}">向前一天</a>
		<#if after??>
			<a href="${contextPath}/zhihu/day/${after}">向后一天</a>
		</#if>
	</div>
</body>
</html>