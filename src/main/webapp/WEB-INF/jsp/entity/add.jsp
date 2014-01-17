<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/default.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/jquery-ui.css'/>" />
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-ui.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-ui-timepicker-addon.js'/>"></script>
<script type="text/javascript">
	var entityClass = "";
	
	//去除前后空格
	String.prototype.Trim = function(){    
		return this.replace(/(^\s*)|(\s*$)/g, "");    
	}

	$(function() {
		$('.datebox').datetimepicker({
			currentText : 'Now',
			dateFormat : 'yy-mm-dd',
			showSecond : true,
			dateFormate : 'yyyy-MM-dd',
			timeFormat : ' hh:mm:ss'
		});

		entityClass = $("input[name='entity_class']").val();
		entityClass = entityClass.substring(entityClass.lastIndexOf('.') + 1,
				entityClass.length);
		entityClass = entityClass.substring(0, 1).toLowerCase()
				+ entityClass.substring(1, entityClass.length);

		$(".ajax_class").focusout(function() {
							//alert("in");
							//alert("again in");

							var currentName = $(this).attr("id");
							var relatedInput = $("#" + currentName + " option:selected").attr("related_input");

							var relatedInputLabel = $("#" + currentName + " option:selected")
									.attr("related_input_label");
							
							//当某个输入框的下家为空时，不再向后台请求数据
							if (relatedInput == "none" || $(this).val() == -1) {
								return false;
							}

							ajaxfunc(currentName, relatedInput,
									relatedInputLabel);
						});
	});

	function ajaxfunc(currentId, relatedInput, relatedInputLabel) {
		$.getJSON("<c:url value='/ajax/query'/>",
						{
							entity : entityClass,
							field : $("#" + currentId).attr("name"),
							param : $("#" + currentId).val()
						},
						function(optionData) {

							//先将关联的标签给还原到初始到状态
							if(!jQuery.isEmptyObject(optionData.preCleanInputs) ){
								$.each(	optionData.preCleanInputs,function(i, input) {
									//$("#" + input).unbind('focusout');
									var $lab = $("#" + input + "_label");
									var nickName = $lab.attr("sn");
									var inputHtml = "<input type=\"text\" width=\"300\" id=\"" + input +"\" name=\"" + input + "\"></input>"
									$lab.html("");
									$lab.append(nickName);
									$("#" + input).replaceWith(inputHtml);
								});
							}

							//拼被关联的标签
							var option_html = "<select name='" + relatedInput
									+ "' id='" + relatedInput + "' ";

							option_html = option_html + "class='ajax_class' ";
							option_html = option_html + ">";

							option_html = option_html
									+ "<option value='-1'>--please select--</option>";
							$.each(	optionData.options,function(i, option) {
												option_html = option_html
														+ "<option value='" + option.key + "' related_input='" + option.relatedInput + "' related_input_label='" + option.relatedInputLabel + "'>"
														+ option.value
														+ "</option>";
											});
							option_html = option_html + "</select>";

							$("#" + relatedInput).html("");
							$("#" + relatedInput).replaceWith(option_html);
							
							//换掉关联输入框的Label
							if (relatedInputLabel != "none") {
								$("#" + relatedInput + "_label").html("");
								$("#" + relatedInput + "_label").append(
										relatedInputLabel);
							}

							//再次给所有关联标签绑定事件
							$(".ajax_class").unbind();
							$(".ajax_class").focusout(	function() {
										//alert("inner");
										//alert("again innner");

										var currentName = $(this).attr("id");
										var relatedInput = $("#" + currentName + " option:selected")
												.attr("related_input");

										var relatedInputLabel = $("#" + currentName	+ " option:selected")
												.attr("related_input_label");

										//当某个输入框的下家为空时，不再向后台请求数据
										if (relatedInput == "none" || $(this).val() == -1) {
											return false;
										}
										
										ajaxfunc(currentName, relatedInput,
												relatedInputLabel);

									});

						});

	}
</script>
<script type="text/javascript">
	$(function() {
		$('#ready').load('ready');
		$("#form1").submit(
				function() {
					//将所有多选框内的选项，设置为select
					$('.select2').each(function(index){
						 var $options = $('option', this);
						 $($options).each(function(sn){
							 //alert("2 : " + sn + "  " + $(this).val());
							 $(this).attr("selected", "selected");
						 });
					});
					
					//去空格
					$('textarea').each(function(index){
						$(this).attr("value", $(this).val().Trim())
					});
				
					
					// console.log($("#form1").serialize());
					

					$.post($('#form1').attr("action"), $(':input').serialize(),
							function(data) {
								alert(data);
							});
					return false;
				});

	});
	
	$(document).ready(function(){  
        $('.add').click(function(){  
            var tag_name=$(this).attr("sn");
        	var $options = $('#select1_' + tag_name + ' option:selected');//获取当前选中的项  
            //var $remove = $options.remove();//删除下拉列表中选中的项  
            //$remove.appendTo('#select2');//删除和追加可以用appendTo()直接完成  
            $options.appendTo('#select2_' + tag_name); 
        });  
          
        $('.remove').click(function(){  
        	var tag_name=$(this).attr("sn");
        	var $options = $('#select2_' + tag_name + ' option:selected');//获取当前选中的项  
            $options.appendTo('#select1_' + tag_name);
        });  
          
        $('.addAll').click(function(){  
            var tag_name=$(this).attr("sn");
            var $options = $('#select1_' + tag_name + ' option');  
            $options.appendTo('#select2_' + tag_name); 
        });  
          
        $('.removeAll').click(function(){  
            var tag_name=$(this).attr("sn");
            var $options = $('#select2_' + tag_name + ' option');  
            $options.appendTo('#select1_' + tag_name);
        });  
          
        //双击事件  
        $('.select1').dblclick(function(){  
            //var $options = $('#select1 option:selected');  
            var $options = $('option:selected', this);//注意此处“option”与“:”之间的空格，有空格是不可以的  
            var tag_name=$(this).attr("sn");
            $options.appendTo('#select2_' + tag_name);  
        });  
          
        $('.select2').dblclick(function(){  
            //$('#select2 option:selected').appendTo('#select1'); 
        	var $options = $('option:selected', this);//注意此处“option”与“:”之间的空格，有空格是不可以的  
            var tag_name=$(this).attr("sn");
            $options.appendTo('#select1_' + tag_name); 
        });  
          
    });  
	
</script>

</head>
<body class="frame">
<div class="query-table">
<form id="form1"
	action="<c:url value='/gameentity/add?'/>mentity_id=<c:out value='${mentity_id}'/>"
	method="post">
	<table>
		<input type="hidden" name="entity_class" value="${mentity.entityClass }" /> 
		<c:forEach var="item" items="${ mentity.fields }">
		<tr>
			<td with="50"><label id="${item.name}_label"
				sn='${item.nickname}'><c:out value='${item.nickname}' /></label></td>
			<td><c:choose>
				<c:when test="${item.valueUitype eq 1}">
					<select name="<c:out value='${item.name}'/>"
						id="<c:out value='${item.name}'/>">
						<script>
										<!--
											var options = jQuery
													.parseJSON('${item.fieldRealValues}');
											$.each(	options,function(i, option) {
																document.write("<option value='"+option.key+"'>"
																				+ option.value
																				+ "</option>");
															});
										//-->
										</script>
					</select>
				</c:when>
				<c:when test="${item.valueUitype eq 2}">
					<c:choose>
						<c:when test="${item.fieldValues!='1'}">
							<input type="text" width="100" class="datebox"
								name="<c:out value='${item.name}'/>"
								id="<c:out value='${item.name}'/>" />
						</c:when>
						<c:otherwise>
											自动获取系统当前时间
										</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="${item.valueUitype eq 3}">
					<textarea rows="5" cols="50" name="<c:out value='${item.name}'/>"
						id="<c:out value='${item.name}'/>"></textarea>
				</c:when>
				<c:when test="${item.valueUitype eq 4}">
					<select name="<c:out value='${item.name}'/>"
						id="<c:out value='${item.name}'/>">
						<script>
										<!--
											var options = jQuery
													.parseJSON('${item.fieldRealValues}');
											$
													.each(
															options,
															function(i, option) {
																document
																		.write("<option value='"+option.key+"'>"
																				+ option.parentValue
																				+ ":"
																				+ option.value
																				+ "</option>");
															});
										//-->
										</script>
					</select>
				</c:when>
				<c:when test="${item.valueUitype eq 5}">
					<c:choose>
						<c:when	test="${ empty item.fieldRealValues || item.fieldRealValues eq \"\"}">
							<input id="${item.name}" name="${item.name}" class="ajax_class"
								related_input="${item.ajaxRelatedInput }" value="" />
						</c:when>
						<c:otherwise>
							<select id="${item.name}" name="${item.name}" class="ajax_class">
								<script>
												<!--
													var options = jQuery
															.parseJSON('${item.fieldRealValues}');
													var relatedInput = '${item.ajaxRelatedInput}';
													$.each(	options,function(i,	option) {
																		document.write("<option value='"+option.key+"' related_input='"+ relatedInput + "' related_input_label='none'>"
																						+ option.value
																						+ "</option>");
																	});
												//-->
												</script>
							</select>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="${item.valueUitype eq 6}">
					<select multiple id="select1_${item.name}" class="select1"
						sn="${item.name}" style="width: 100px; height: 250px;">
						<script>
										<!--
											var options = jQuery.parseJSON('${item.fieldRealValues}');
											$.each(	options,function(i, option) {
												if(option.key != 0 ){
																document.write("<option value='"+option.key+"'>"
																				+ option.value
																				+ "</option>");
												};
															});
										//-->
										</script>
					</select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
									<select multiple class="select2" id="select2_${item.name}"
						name="${item.name}" sn="${item.name}"
						style="width: 100px; height: 250px;">

					</select>
					<div><span class="add" sn="${item.name}">选中添加到右边&gt;&gt;</span>
					<span class="remove" sn="${item.name}">&lt;&lt;选中添加到左边</span><br>
					<span class="addAll" sn="${item.name}">全部添加到右边&gt;&gt;</span> <span
						class="removeAll" sn="${item.name}">&lt;&lt;全部添加到左边</span></div>
				</c:when>
				<c:otherwise>
					<input type="text" width="300" name="<c:out value='${item.name}'/>"
						id="<c:out value='${item.name}'/>"></input>
				</c:otherwise>
			</c:choose></td>
		</tr>
	</c:forEach>
	<tr>
		<td><input type="submit"></input></td>
	</tr>
	</table>
	</form>
</div>
</body>
</html>