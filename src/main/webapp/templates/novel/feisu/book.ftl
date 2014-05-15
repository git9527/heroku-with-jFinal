<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<#include "/templates/common.ftl">
<title>${bookName}</title>
<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/feisu.css" />
</head>

<body>
	<div class="chaptercontent">
		<div class="title">${bookName}</div>
		<div id="body">
			<div class= "chapterlist">
				${chapters}
			</div>
		</div>
	</div>
</body>
</html>