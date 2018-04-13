<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="../common/header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>系统功能选择树</title>
	<link rel="stylesheet" href=".././css/jquery.tree.css">
	
	<script type="text/javascript" src="../../js/jquery.js"></script>
	<script type="text/javascript" src="../../js/jquery.tree.js"></script>
	
	
    <script type="text/javascript">
        $(document).ready(function() {
            var o = {
            	method: "POST", //默认采用POST提交数据
            	datatype: "json", //数据类型是json   
            	cbiconpath: "../../images/icons/",
            	emptyiconpath:"../../images/s.gif",
            	url: '/func/treeData.do?funcIdInTree='+$("#funcIdInTree").val()+'&r='+new Date().getTime(), //异步请求的url   
                showcheck: true,
                theme: "bbit-tree-arrows" //bbit-tree-lines ,bbit-tree-no-lines,bbit-tree-arrows

            };

            $("#tree").treeview(o);
        });
        
        function getSelectedValues(){
        	var tsn = $("#tree").getTSNs(true);
        	var funcIds = '';
        	for(var i=0;i<tsn.length;i++){
        		funcIds=funcIds+tsn[i].value+","
        	}
        	window.parent.setValue(funcIds);
        	window.parent.closeFuncTree();
        }
    </script>

</head>

<body class='ie'>
  <form action="/func/list.do" method="post">
  	<input type="hidden" name="funcIdInTree" id="funcIdInTree" value="${funcIdInTree}" styleId="funcIdInTree"/>
  	
  </form>
  <div id="tree"></div>

    <tr align="left">
    	<input type="submit" onclick="getSelectedValues()" value="确定"/>	
    </tr>
</body>
</html>
