<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<html>

<head>
    <title>SD40 Performance indicator</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<script src="/js/jquery-3.1.1.min.js"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css"
          integrity="sha384-AysaV+vQoT3kOAXZkl02PThvDr8HYKPZhNT5h/CXfBThSRXQ6jW5DO2ekP5ViFdi" crossorigin="anonymous"/>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/js/bootstrap.min.js"
            integrity="sha384-BLiI7JTZm+JWlgKa0M0kGRpJbF2J8q+qreVrKBC47e3K6BW78kGLrCkeRX6I9RoK"
            crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.2.0/js/tether.min.js"
        integrity="sha384-Plbmg8JY28KFelvJVai01l8WyZzrYWG825m+cZ0eDDS1f7d/js6ikvy1+X+guPIB"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.17.1/moment.min.js"></script>
    <link rel="stylesheet" href="/css/style.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker3.css"/>

</head>

<body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript">
    	function setupModal(client){
    		$('#formFrom').val(moment().format("DD/MM/YYYY"))
    		$('#formTo').val(moment().format("DD/MM/YYYY"))
    		$('#formTo').datepicker({
    			format: "dd/mm/yyyy"
    		})
    		$('#formFrom').datepicker({
    			format: "dd/mm/yyyy",
    		})

    		$('#formMaxResults').val(100)
    		$('#customForm').attr('action','/history/'+client+'/custom')
    		console.log(moment().format('zz'))
    	}
    </script>
<div class="container">
	<br>
	<div class="alert alert-info">
  		<h3><strong>Attention!</strong><br>All requests for stored data are made with UTC time.<br>Requested data will be displayed in your browsers timezone.</h3>
	</div>
	<br>
    <h1>Client list</h1>
    <table class="table table-striped table-fixed"> <!-- table-bordered  -->
        <thead class="thead-inverse">
            <th class="col-xs-6">Name</th>
            <th class="col-xs-6">Link</th>
        </thead>
         <tbody>
         <% for(String client:(List<String>)request.getAttribute("clients")) { %>
            <tr>
                <td class="col-xs-6">
                    <h2><span><%= client %></span></h2>
                </td>
                <td class="col-xs-6">
                	<a class="btn btn-outline-primary" href='<%= ((Map)request.getAttribute("prevMonthLinks")).get(client) %>'>Previous Month</a>
                    <a class="btn btn-outline-primary" href='<%= ((Map)request.getAttribute("monthlinks")).get(client) %>'>Month</a>
                    <a class="btn btn-outline-primary" href='<%= ((Map)request.getAttribute("last2016links")).get(client) %>'>Last 7 days</a>
                    <a class="btn btn-outline-primary" href='<%= ((Map)request.getAttribute("last864links")).get(client) %>'>Last 3 days</a>
                    <a class="btn btn-outline-primary" href='<%= ((Map)request.getAttribute("links")).get(client) %>'>Yesterday</a>
                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#customModal" onclick="setupModal('<%=client %>')">Custom request</button>
                </td>
            </tr>
           <% } %>
        </tbody>
        </table>
</div>

<div class="container">
    <h1>Upload log file</h1>
    <form class="form-inline">
    <div class="form-group mx-sm-3 mb-2">
    <label for="inputDBName" class="sr-only">InfluxDB name</label>
    <input type="text" class="form-control" id="inputDBName" placeholder="InfluxDB name" required>
    </div>
    <div class="form-group mb-2">
        <select class="form-control" required>
            <option value="">Select log type</option>
            <option value="sdng">SDNG</option>
            <option value="gc">CG</option>
            <option value="top">Top</option>
        </select>
    </div>
    <div class="form-group mb-2">
    <select class="form-control" required>
    <option value="">Select time zone</option>
    <option value="UTC">Coordinated Universal Time</option>
    <option value="GMT-12:00">(GMT-12:00) International Date Line West</option>
    <option value="GMT-11:00">(GMT-11:00) Midway Island, Samoa</option>
    <option value="GMT-10:00">(GMT-10:00) Hawaii</option>
    <option value="GMT-09:00">(GMT-09:00) Alaska</option>
    <option value="GMT-08:00">(GMT-08:00) Pacific Time (US & Canada)</option>
    <option value="GMT-08:00">(GMT-08:00) Tijuana, Baja California</option>
    <option value="GMT-07:00">(GMT-07:00) Arizona</option>
    <option value="GMT-07:00">(GMT-07:00) Chihuahua, La Paz, Mazatlan</option>
    <option value="GMT-07:00">(GMT-07:00) Mountain Time (US & Canada)</option>
    <option value="GMT-06:00">(GMT-06:00) Central America</option>
    <option value="GMT-06:00">(GMT-06:00) Central Time (US & Canada)</option>
    <option value="GMT-06:00">(GMT-06:00) Guadalajara, Mexico City, Monterrey</option>
    <option value="GMT-06:00">(GMT-06:00) Saskatchewan</option>
    <option value="GMT-05:00">(GMT-05:00) Bogota, Lima, Quito, Rio Branco</option>
    <option value="GMT-05:00">(GMT-05:00) Eastern Time (US & Canada)</option>
    <option value="GMT-05:00">(GMT-05:00) Indiana (East)</option>
    <option value="GMT-04:00">(GMT-04:00) Atlantic Time (Canada)</option>
    <option value="GMT-04:00">(GMT-04:00) Caracas, La Paz</option>
    <option value="GMT-04:00">(GMT-04:00) Manaus</option>
    <option value="GMT-04:00">(GMT-04:00) Santiago</option>
    <option value="GMT-03:30">(GMT-03:30) Newfoundland</option>
    <option value="GMT-03:00">(GMT-03:00) Brasilia</option>
    <option value="GMT-03:00">(GMT-03:00) Buenos Aires, Georgetown</option>
    <option value="GMT-03:00">(GMT-03:00) Greenland</option>
    <option value="GMT-03:00">(GMT-03:00) Montevideo</option>
    <option value="GMT-02:00">(GMT-02:00) Mid-Atlantic</option>
    <option value="GMT-01:00">(GMT-01:00) Cape Verde Is.</option>
    <option value="GMT-01:00">(GMT-01:00) Azores</option>
    <option value="GMT+00:00">(GMT+00:00) Casablanca, Monrovia, Reykjavik</option>
    <option value="GMT+00:00">(GMT+00:00) Greenwich Mean Time : Dublin, Edinburgh, Lisbon, London</option>
    <option value="GMT+01:00">(GMT+01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna</option>
    <option value="GMT+01:00">(GMT+01:00) Belgrade, Bratislava, Budapest, Ljubljana, Prague</option>
    <option value="GMT+01:00">(GMT+01:00) Brussels, Copenhagen, Madrid, Paris</option>
    <option value="GMT+01:00">(GMT+01:00) Sarajevo, Skopje, Warsaw, Zagreb</option>
    <option value="GMT+01:00">(GMT+01:00) West Central Africa</option>
    <option value="GMT+02:00">(GMT+02:00) Amman</option>
    <option value="GMT+02:00">(GMT+02:00) Athens, Bucharest, Istanbul</option>
    <option value="GMT+02:00">(GMT+02:00) Beirut</option>
    <option value="GMT+02:00">(GMT+02:00) Cairo</option>
    <option value="GMT+02:00">(GMT+02:00) Harare, Pretoria</option>
    <option value="GMT+02:00">(GMT+02:00) Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius</option>
    <option value="GMT+02:00">(GMT+02:00) Jerusalem</option>
    <option value="GMT+02:00">(GMT+02:00) Minsk</option>
    <option value="GMT+02:00">(GMT+02:00) Windhoek</option>
    <option value="GMT+03:00">(GMT+03:00) Kuwait, Riyadh, Baghdad</option>
    <option value="GMT+03:00">(GMT+03:00) Moscow, St. Petersburg, Volgograd</option>
    <option value="GMT+03:00">(GMT+03:00) Nairobi</option>
    <option value="GMT+03:00">(GMT+03:00) Tbilisi</option>
    <option value="GMT+03:30">(GMT+03:30) Tehran</option>
    <option value="GMT+04:00">(GMT+04:00) Abu Dhabi, Muscat</option>
    <option value="GMT+04:00">(GMT+04:00) Baku</option>
    <option value="GMT+04:00">(GMT+04:00) Yerevan</option>
    <option value="GMT+04:30">(GMT+04:30) Kabul</option>
    <option value="GMT+05:00">(GMT+05:00) Yekaterinburg</option>
    <option value="GMT+05:00">(GMT+05:00) Islamabad, Karachi, Tashkent</option>
    <option value="GMT+05:30">(GMT+05:30) Sri Jayawardenapura</option>
    <option value="GMT+05:30">(GMT+05:30) Chennai, Kolkata, Mumbai, New Delhi</option>
    <option value="GMT+05:45">(GMT+05:45) Kathmandu</option>
    <option value="GMT+06:00">(GMT+06:00) Almaty, Novosibirsk</option>
    <option value="GMT+06:00">(GMT+06:00) Astana, Dhaka</option>
    <option value="GMT+06:30">(GMT+06:30) Yangon (Rangoon)</option>
    <option value="GMT+07:00">(GMT+07:00) Bangkok, Hanoi, Jakarta</option>
    <option value="GMT+07:00">(GMT+07:00) Krasnoyarsk</option>
    <option value="GMT+08:00">(GMT+08:00) Beijing, Chongqing, Hong Kong, Urumqi</option>
    <option value="GMT+08:00">(GMT+08:00) Kuala Lumpur, Singapore</option>
    <option value="GMT+08:00">(GMT+08:00) Irkutsk, Ulaan Bataar</option>
    <option value="GMT+08:00">(GMT+08:00) Perth</option>
    <option value="GMT+08:00">(GMT+08:00) Taipei</option>
    <option value="GMT+09:00">(GMT+09:00) Osaka, Sapporo, Tokyo</option>
    <option value="GMT+09:00">(GMT+09:00) Seoul</option>
    <option value="GMT+09:00">(GMT+09:00) Yakutsk</option>
    <option value="GMT+09:30">(GMT+09:30) Adelaide</option>
    <option value="GMT+09:30">(GMT+09:30) Darwin</option>
    <option value="GMT+10:00">(GMT+10:00) Brisbane</option>
    <option value="GMT+10:00">(GMT+10:00) Canberra, Melbourne, Sydney</option>
    <option value="GMT+10:00">(GMT+10:00) Hobart</option>
    <option value="GMT+10:00">(GMT+10:00) Guam, Port Moresby</option>
    <option value="GMT+10:00">(GMT+10:00) Vladivostok</option>
    <option value="GMT+11:00">(GMT+11:00) Magadan, Solomon Is., New Caledonia</option>
    <option value="GMT+12:00">(GMT+12:00) Auckland, Wellington</option>
    <option value="GMT+12:00">(GMT+12:00) Fiji, Kamchatka, Marshall Is.</option>
    <option value="GMT+13:00">(GMT+13:00) Nuku'alofa</option>
    </select>
    </div>
    <div class="form-group mb-2">
    <input type="file" class="form-control form-control-file" id="logUploadControl" required>
    </div>
    <div class="form-check mb-2">
    <input class="form-check-input" type="checkbox" id="printTraceResult" value="option1">
    <label class="form-check-label" for="printTraceResult">Print trace result</label>
    </div>

    <button type="submit" class="btn btn-primary mb-2">Upload</button>
    </form>
</div>

<div class="modal fade" id="customModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class=modal-dialog role="dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel">Select dates and max results</h4>
			</div>
			<form id="customForm">
				<div class="modal-body">
				    <div class="row">
				        <div class="col-md-6">
                            <div class="form-group">
                                <label for="formFrom">From</label>
                                <input type="text"class="form-control" id="formFrom" name="from">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="formTo">To</label>
                                <input type="text" class="form-control" id="formTo" name="to">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="formMaxResults">Max results</label>
                        <input class="form-control" type="number" value="42" id="formMaxResults" name="maxResults">
                    </div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Request</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
				</div>
			</form>
		</div>
	</div>
</div>
</body>

</html>