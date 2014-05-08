<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<#import "/templates/common.ftl" as app>
<title>${chapterName} - ${bookName}</title>
<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/feisu.css" />
</head>

<body>
	<div class="chaptercontent">
		<div class="title">${chapterName}</div>
		<div id="body" style="background-color: rgb(223, 244, 255); color: rgb(0, 0, 0);">
			<div style="line-height: 29px;" class="content">${content}</div>
			<div id="tran" class="clearfix">
				<a id="prev" href="${contextPath}/novel/feisu/${bookId}-${prevPage}">上一页</a><a href="${contextPath}/novel/feisu/${bookId}">返回目录页</a><a id="next" href="${contextPath}/novel/feisu/${bookId}-${nextPage}">下一页</a>
			</div>
		</div>
	</div>
</body>
</html>