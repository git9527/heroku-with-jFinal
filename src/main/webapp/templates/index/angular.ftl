<!doctype html>
<html ng-app>
<head>
<#include "/templates/common.ftl">
<script src="${contextPath}/resources/js/angular.js"></script>
<script src="${contextPath}/resources/js/demo.js"></script>
</head>
<body>
<div ng-controller="InvoiceCntl">
<b>Invoice:</b>
<br>
<br>
<table>
<tr><td>Quantity</td><td>Cost</td></tr>
<tr>
<td><input type="integer" min="0" ng-model="qty" required ></td>
<td><input type="number" ng-model="cost" required ></td>
</tr>
</table>
<hr>
<b>Total:</b> {{qty * cost | currency}}
</div>
</body>
</html>