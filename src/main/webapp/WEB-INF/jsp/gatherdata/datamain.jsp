<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/default.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/jquery-ui.css'/>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link
	href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/beauty.css'/>" rel="stylesheet"
	rel="stylesheet" />
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-ui.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/My97DatePicker/WdatePicker.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/cvi_busy_lib.js'/>"></script>
<script type="text/javascript">
	$().ready(function() {
		$("#form1").submit(function() {
			var url = $('#form1').attr("action");
			block_viewport();
			$.post(url, $('#form1').serialize(), function(data) {
				xval.remove();
				$("#data_list").html("");
				$("#data_list").html(data);
			});
			return false; //防止重复提交
		});
		$("#form1").submit();
	});
</script>
<script type="text/javascript">
	function submitPost(url) {
		$.post(url, $('#form1').serialize(), function(data) {
			$("#data_list").html("");
			$("#data_list").html(data);
		});
		return false; //防止重复提交
	}

	function pagechange(index) {
		var url = $('#form1').attr("action");
		url += "?pn=" + index;
		submitPost(url);
	}
	function doExport1() {
		document.all.exportForm.action = "<c:url value='/jsp/gatherdata/export'/>";
		var str = document.getElementById("countdata").outerHTML;
		document.all.excelText.value = str;
		document.all.exportForm.submit();
	}
	var idTmr;
	function getExplorer() {
		var explorer = window.navigator.userAgent;
		//ie 
		if (explorer.indexOf("MSIE") >= 0) {
			return 'ie';
		}
		//firefox 
		else if (explorer.indexOf("Firefox") >= 0) {
			return 'Firefox';
		}
		//Chrome
		else if (explorer.indexOf("Chrome") >= 0) {
			return 'Chrome';
		}
		//Opera
		else if (explorer.indexOf("Opera") >= 0) {
			return 'Opera';
		}
		//Safari
		else if (explorer.indexOf("Safari") >= 0) {
			return 'Safari';
		}
	}
	function doExport(tableid) {//整个表格拷贝到EXCEL中
		if (getExplorer() == 'ie') {
			var curTbl = document.getElementById(tableid);
			var oXL = new ActiveXObject("Excel.Application");
			//创建AX对象excel 
			var oWB = oXL.Workbooks.Add();
			//获取workbook对象 
			var xlsheet = oWB.Worksheets(1);
			//激活当前sheet 
			var sel = document.body.createTextRange();
			sel.moveToElementText(curTbl);
			//把表格中的内容移到TextRange中 
			sel.select();
			//全选TextRange中内容 
			sel.execCommand("Copy");
			//复制TextRange中内容  
			xlsheet.Paste();
			//粘贴到活动的EXCEL中       
			oXL.Visible = true;
			//设置excel可见属性
			try {
				var fname = oXL.Application.GetSaveAsFilename("Excel.xls",
						"Excel Spreadsheets (*.xls), *.xls");
			} catch (e) {
				print("Nested catch caught " + e);
			} finally {
				oWB.SaveAs(fname);
				oWB.Close(savechanges = false);
				//xls.visible = false;
				oXL.Quit();
				oXL = null;
				//结束excel进程，退出完成
				//window.setInterval("Cleanup();",1);
				idTmr = window.setInterval("Cleanup();", 1);

			}
		} else {
			tableToExcel(tableid);
		}
	}
	function Cleanup() {
		window.clearInterval(idTmr);
		CollectGarbage();
	}
	var tableToExcel = (function() {
		var uri = 'data:application/vnd.ms-excel;base64,';
		var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
		var base64 = function(s) {
			return window.btoa(unescape(encodeURIComponent(s)))
		}
		var format = function(s, c) {
			return s.replace(/{(\w+)}/g, function(m, p) {
				return c[p];
			})
		}
		return function(table, name) {
			if (!table.nodeType)
				table = document.getElementById(table)
			var ctx = {
				worksheet : name || 'Worksheet',
				table : table.innerHTML
			}
			window.location.href = uri + base64(format(template, ctx))
		}
	})()
</script>
</head>
<body class="main">
	<div class="query-table" style="clear: both">
		<div style="display: inline-block">
			<form id="form1" class="form-inline"
				action="<c:url value='/countdata/dataList'/>">
				<span class="inlinetitle">开始时间:</span> <input id="start"
					name="start_date" value="${start_date}"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'end\')}'});"
					readonly /> <span class="inlinetitle">结束时间:</span> <input id="end"
					name="end_date" value="${end_date}"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'start\')}',maxDate:'%y-%M-%d-%H-%m-%s'});"
					readonly />
				<c:if test="${empty priv }">
					<span>渠道：</span>
					<select name="channel">
						<option value="-1">----所有----</option>
						<c:forEach items="${channels}" var="item">
							<option value="${item.idx }">${item.name }</option>
						</c:forEach>
					</select>
				</c:if>
				<select name="model">
					<option value="0">详细</option>
					<option value="1">汇总</option>
				</select> <input type="submit" value="查询" class="btn btn-primary" /> <input
					type="hidden" name="mentityId" value="${mentityId }"
					class="input-medium focused fuck" /> <input type="hidden"
					name="order" value="${order }" class="input-medium focused fuck" />
			</form>
		</div>
		<div style="display: inline-block">
			<form name="exportForm" method="post" action="">
				<input type="hidden" name="excelText" id="excelText" /> <input
					name="exportBtn" type="button" value="导出Excel"
					onclick="doExport('countdata')" class="btn btn-primary">
			</form>
		</div>
	</div>
	<div id="data_list"></div>
</body>
</html>