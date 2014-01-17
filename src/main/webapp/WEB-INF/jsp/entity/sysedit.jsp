<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/jquery-ui.css'/>" />
<script type="text/javascript" src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui-timepicker-addon.js'/>" ></script>
<script type="text/javascript">
$(function() {
$('#ready').load('ready');
$('.datebox').datetimepicker({
    currentText: 'Now',
    dateFormat: 'yy-mm-dd',
		showSecond: true,
	dateFormate: 'yyyy-MM-dd',
	timeFormat: ' hh:mm:ss'
});
$("#form1").submit( function(){ 
	
	$.post(
			 $('#form1').attr( "action" ),
			 $('#form1').serialize(),
	            function(data){
	                alert( data );
		            }
	 );
     return false;		
    });
});
 </script>
</head>
<body class="frame">
<div class="edit">
<table>
<form id="form1" action="<c:url value='/gameentity/sysentity/edit?'/>mentity_id=<c:out value='${mentity_id}'/>" method="post" >
<tr class="title">
<td width="50"><c:out value="${gameentity.idField.nickname}"/></td><td><c:out value="${gameentity.idField.value}" /></td>
<input type="hidden" value="<c:out value='${gameentity.idField.value}' />" name="gameentity_id"/>
</tr>
<c:forEach var="item" items="${ gameentity.fields}" >
<tr>
<td width="50">
<label><c:out value='${item.nickname}'/></label></td>
<td> 
<c:choose>
<c:when test="${item.unmodified eq 1}">
<span><c:out value='${item.value}' /></span>
<input type="hidden"  name="<c:out value='${item.name}'/>" value="<c:out value='${item.value}' />"></input>
</c:when>
<c:otherwise>
	<c:choose>
	<c:when test="${item.valueUitype eq 1}" >
	<select name="<c:out value='${item.name}'/>">
	<script type="text/javascript" >
	<!--
		var options = jQuery.parseJSON( '${item.fieldRealValues}' );		
			$.each(options, function(i, option) {
			
			var optionstr = "<option value='"+option.key+"' ";
			var optionkey= option.key;
			var itemkey = '${item.value}';
			if( itemkey == optionkey ){
				optionstr += "selected";
			}
			optionstr  +=">"+option.value+"</option>";
			document.write( optionstr );
		});		
	//-->
	</script>
	</select>
	</c:when>
	<c:when test="${item.valueUitype eq 2}" >
		<input type="text"  width="200"  class="datebox" name="<c:out value='${item.name}'/>" value="<c:out value='${item.value}' />"/>
	</c:when>
	<c:when test="${item.valueUitype eq 3}" >
		<textarea  rows="5" cols="50" name="<c:out value='${item.name}'/>" ><c:out value='${item.value}'/></textarea>
	</c:when>
	<c:otherwise>
		<input type="text" class="wide-item-value" name="<c:out value='${item.name}'/>" value="<c:out value='${item.value}' />"></input>
	</c:otherwise>
	</c:choose>
</c:otherwise>
</c:choose>
</td></tr>
</c:forEach>
<tr><td><input type="submit" value="修改"></input></td></tr>
</form>
</table>
</div>
</body>
</html>