<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>" rel="stylesheet"/>
<link href="<c:url value='/resources/css/beauty.css'/>" rel="stylesheet" rel="stylesheet"/>
<script type="text/javascript" src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript"><!--
$(function() {
    $('#ready').load('ready');
    $("#form").submit( function(){
		$.post(
				"<c:url value='/mentity/add'/>",
				 $('#form').serialize(),
		            function(data){
		                alert( data );
		            }
	            );
        return false;		
    });
    $("#model_class").focusout(function(){
        $.getJSON(
        		
            "<c:url value='/mentity/getfields'/>",
            { model_class: $('#model_class').val() },
            function(data){
                var last_model_class = $("#last_model_class").val();
                var model_class = $("#model_class").val();
            	if( last_model_class != "" && last_model_class == model_class  ){
 					return;
                }
                if( model_class == "" ){
				   $("#model_table").html( "" );
				    return ;
                }
                $("#model_table").html( "" );
            	$("#last_model_class").val( model_class );
               $("#model_table").addClass( "mentity_add_div" );
           	   $("#model_table").append("<p>设置实体"+$('#model_class').val()+"的属性的中文名、类型、值（不同的值直接请用|分割）</p>" );
           	  
               $.each(data, function(i,item){	
                	 var field_html;
                	 field_html = "<div class=\"item\">";
                	 field_html = field_html + "<div class=\"name\"><span>"+item.name+":</span><input type='text' name="+item.name+" value="+item.name+"></input>";
                	 field_html = field_html + "<input type=\"checkbox\" name=\""+item.name+"_unmodified\">不可修改</input>&nbsp;&nbsp;显示顺序:<input type=\"text\" name=\""+item.name+"_num\" class=\"num-value\" value=\""+i+"\"></input></div>";
                	 field_html = field_html + "<div class=\"type\"><span>类型：</span><select class=\"type-select\" name='"+item.name+"_type'" +"><option value='0' selected>其他</option><option value='1'>枚举</option><option value='2' >日期时间</option><option value='3' >多行文本</option><option value='4' >自定义枚举</option><option value='5' >ajax枚举</option><option value='6' >multi_枚举</option><option value='7' >时间戳</option></select></div>";
                	 field_html = field_html + "<div class=\"wide-value\" style=\"display:none\"><span>值：</span><input class=\"wide-item-value\" name='"+item.name+"_values'/></div>";
                	 field_html = field_html + "<div class=\"wide-value\" style=\"display:none\"><span>默认值为当前时间：</span><input type=\"checkbox\"  name='"+item.name+"_takecurtime'/></div>";               	 
                	 field_html = field_html + "<div class=\"wide-value\" style=\"display:none\"><span>ajax关联输入框：</span><select name='"+item.name+"_ajax_related_input'>";
                	 /*$.each(data, function(i,option){
                    	 if(option.name != item.name){
                    		 field_html = field_html + "<option value='" + option.name + "'>" + option.name + "</option>";
                    	 }
                	 });*/
                	 field_html = field_html + "</select></div>";
                	 
                	 field_html = field_html + "<div class=\"wide-value\" style=\"display:none\"><span>ajaxURL：</span><input name='"+item.name+"_ajax_url'/></div>";
                	 field_html = field_html + "<div class=\"clear\"></div></div>";
                	 field_html = field_html + "<div class=\"clear\"></div></div>";
                	 $("#model_table").append( field_html );

               });
               $(".type-select").change(function() {
            	    $(this).parent().next().css("display", "none");
            	    $(this).parent().next().next().css("display", "none");
            	    $(this).parent().next().next().next().css("display", "none");
            	    $(this).parent().next().next().next().next().css("display", "none");
               		if($(this).attr("value") == "1" || $(this).attr("value") == "4" || $(this).attr("value") == "6" ) {
               			$(this).parent().next().css("display", "inline");
               		} else if( $(this).attr("value") == "2" ) {
						$(this).parent().next().next().css("display", "inline");
               		} else if( $(this).attr("value") == "5" ) {
               			var itemName = $(this).attr("name");
                        itemName = itemName.substring(0, itemName.indexOf('_'));
                        //alert("change test:" + itemName);
						
                   		//将级联的input选项append进去
						var option_html = "";
               			$.each(data, function(i,option){
	                       	 if(option.name != itemName){
	                       		 option_html = option_html + "<option value='" + option.name + "'>" + option.name + "</option>";
	                       	 }
                   	 	});
                   	 	//alert("option_text:" + option_html);
               			$("select[name='" + itemName + "_ajax_related_input" + "']").html("");
               			$("select[name='" + itemName + "_ajax_related_input" + "']").append(option_html);
                   	 	
						$(this).parent().next().css("display", "inline");
						$(this).parent().next().next().next().css("display", "inline");
						$(this).parent().next().next().next().next().css("display", "inline");
                   	}
               });
               $("#submitButton").attr("disabled", false );
               //alert( $("#model_table").html() ); 
            }
        );
        
    });
});
--></script>
</head>
<body class="main">
<input type="hidden" id="last_model_class" value=""/>
<form id="form" method="post">
<div class="item-info">
<table class="list" style="width:700px">
<tr><td class="name">管理的实体名:</td><td class="value"><input name="entityName" class="input-medium focused fuck" type="text" value=""></input></td></tr>
<tr><td>实体的Model定义路径：</td><td><input name="entityClass" class="input-medium focused fuck" type="text" id="model_class" value="com.youai.gs.dzm.model."></input></td></tr>
<tr><td>实体描述信息</td><td><input name="entityDesc" value="管理" class="input-medium focused fuck"></input></td></tr>
<tr><td>是系统配置</td><td><input type="checkbox" name="isSystemConfig" class="input-medium focused fuck"></td></tr>
<tr><td>扩展链接</td><td><input type="text" class="input-xxlarge focused fuck"name="extendLink" /></td></tr>
<tr><td>父目录</td><td><select name="parentNavId">
<option value="0">无</option>
<c:forEach var="item" items="${requestScope.parentNavs}">
 <option value="${item.idx}">${item.name}</option>
</c:forEach>
</select>
</td></tr>
<tr><td></td></tr>
</table>
</div>
<!-- 填充field的div -->
<div id="model_table"></div>
<div id="submit" class="clear">
<input type="submit" class="btn btn-success" disabled="true" id="submitButton"></input>
</div>
</form>

</body>
</html>