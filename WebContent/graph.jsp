<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="Data.*" import="Storage.DAO.*" import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>
    Column Chart Example - HTML5 jQuery Chart Plugin by jqChart
</title>
    <link rel="stylesheet" type="text/css" href="./jQchart/jquery.jqChart.css" />
    <link rel="stylesheet" type="text/css" href="./jQchart/jquery.jqRangeSlider.css" />
    <link rel="stylesheet" type="text/css" href="./jQchart/jquery-ui-1.8.21.css" />
    <script src="./jQchart/jquery-1.5.1.min.js" type="text/javascript"></script>
    <script src="./jQchart/jquery.mousewheel.js" type="text/javascript"></script>
    <script src="./jQchart/jquery.jqChart.min.js" type="text/javascript"></script>
    <script src="./jQchart/jquery.jqRangeSlider.min.js" type="text/javascript"></script>
    <script lang="javascript" type="text/javascript" src="./jQchart/excanvas.js"></script>
    
    <script lang="javascript" type="text/javascript">
        $(document).ready(function () {
        	
        <%
	    	String pName = request.getParameter("pName");
	    	String eSizeString = request.getParameter("eSize");
	    	int eSize = Integer.parseInt(eSizeString);
        
			WebSourceDAO wsdao = new WebSourceDAO();
			ArrayList<WebSource> wsl = wsdao.getList("ilbe");
        %>
        	
            $('#jqChart').jqChart({	
                title: { text: '<%=wsl.size()%>' },
                legend: { location: 'bottom' },
                paletteColors:
                {
                    type: 'customColors',
                    customColors: ['#CEF279', '#BD5C24']
                },
                animation: { duration: 1 },
                shadows: {
                    enabled: true
                },
                axes: [
                         {
                             type: 'category',
                             location: 'left',
                             
                             categories:['문자 요소 1-1', '문자 요소 1-2', '문자 요소 1-3']
                         },
                         {
                             type: 'linear',
                             location: 'bottom',
                             extendRangeToOrigin: true
                         }
                      ],
                series: [
                            {
                                type: 'stacked100Bar',
                                title: '긍정',
                                data:['문자 요소 2-1', '문자 요소 2-2', '문자 요소 2-3'],
                                labels: {
                                    stringFormat: '%.2f%%',
                                    valueType: 'percentage',
                                    font: '12px sans-serif'
                                }
                            },
                            {
                                type: 'stacked100Bar',
                                title:'부정',
                                data:['문자 요소 2-1', '문자 요소 2-2', '문자 요소 2-3'],
                                labels: {
                                    stringFormat: '%.2f%%',
                                    valueType: 'percentage',
                                    font: '12px sans-serif'
                                }
                            }
                        ]
            });
        });
    </script>


</head>
<body>
    <div>
        <div id="jqChart" style="width: 100%; height: 470px;"></div>
    </div>
</body>
</html>