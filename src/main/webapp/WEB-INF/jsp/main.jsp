<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="model.User"%>
<%
User loginUser = (User) session.getAttribute("loginUser");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reひょうごテスト</title>
<link rel="stylesheet"
	href="https://unpkg.com/leaflet@1.3.0/dist/leaflet.css?v=20260422" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<script src="https://unpkg.com/leaflet@1.3.0/dist/leaflet.js"></script>

<script>
    function init(){
      var map = L.map('mapcontainer',{
        zoomControl:false,
        minZoom: 9,
        maxBounds: [
            [34.0, 134],  // 左上（だいたいの範囲）
            [36.0, 136]   // 右下
        ],
  maxBoundsViscosity: 1.0
      });
      map.setView([35.00, 135], 9);
    //   L.control.scale({maxWidth:200,position:'bottomright',imperial:false}).addTo(map);
      L.control.zoom({position:'topleft'}).addTo(map);
      //オープンストリートマップのタイル
      var osm = L.tileLayer('http://tile.openstreetmap.jp/{z}/{x}/{y}.png',
        {   attribution: "<a href='http://osm.org/copyright'            target='_blank'>OpenStreetMap</a> contributors" });
      //baseMapsオブジェクトのプロパティに3つのタイルを設定
      var baseMaps = {
        "オープンストリートマップ"  : osm
      };

   
   // アイコン定義
      var customIcon1 = L.icon({
      <!--ピン画像-->
        iconUrl: '<%=request.getContextPath()%>/img/pin1.png',
        iconSize: [null, 60],
        iconAnchor: [20, 60],   // ←一旦中央にしておく（安全）
        className: 'spot-pin'
      });
      //アイコン定義
      var customIcon2 = L.icon({
      <!--ピン画像-->
        iconUrl: '<%=request.getContextPath()%>/img/pin2.png',
        iconSize: [null, 60],
        iconAnchor: [20, 60],   // ←一旦中央にしておく（安全）
        className: 'spot-pin'
      });
      //アイコン定義
      var customIcon3 = L.icon({
      <!--ピン画像-->
        iconUrl: '<%=request.getContextPath()%>/img/pin3.png',
        iconSize: [null, 60],
        iconAnchor: [20, 60],   // ←一旦中央にしておく（安全）
        className: 'spot-pin'
      });


osm.addTo(map); //←マップの表示

<!--データを受け取ってピン追加-->
var pins = <%=request.getAttribute("pinsListJson") != null ? request.getAttribute("pinsListJson") : "[]"%>;


  
pins.forEach(function(pin) {
	console.log(pin);
	var lat = parseFloat(pin.lat);
	var lon = parseFloat(pin.lon);
	console.log(pin.pinGrow);
	if(pin.pinGrow <= 2 && pin.pinGrow >= 0){
		var marker = L.marker([lat, lon], { icon: customIcon1, riseOnHover: true }).addTo(map);
		console.log(pin.pinGrow);
	}else if(pin.pinGrow >= 3 && pin.pinGrow <=5){ 
		var marker = L.marker([lat, lon], { icon: customIcon2, riseOnHover: true }).addTo(map);
		console.log(pin.pinGrow);
	}else if(pin.pinGrow >=6){ 
		var marker = L.marker([lat, lon], { icon: customIcon3, riseOnHover: true }).addTo(map);
		console.log(pin.pinGrow);
		}
	marker.on('click', function() {
	loadPinDetail(pin.id);
	});
});

<!--イベントピンアイコン-->
function createEventIcon(imageUrl){

	const safeUrl = imageUrl.split("?")[0] + "?t=" + Date.now();

	return L.divIcon({
		className: "custom-event-pin",
		html:
			'<div class="event-pin-wrapper">' +
				'<img class="event-image" src="' + safeUrl + '">' +
				'<img class="event-frame" src="' + contextPath + '/img/eventpin.svg">' +
			'</div>',
		iconSize: [60, 100],
		iconAnchor: [-10, 120]
	});
}

  <!--データを受け取ってイベント追加-->
  var events = <%=request.getAttribute("eventsListJson") != null ? request.getAttribute("eventsListJson") : "[]"%>;
  events.forEach(function(event) {

  	var lat = parseFloat(event.lat);
  	var lon = parseFloat(event.lon);

  	const imageUrl = event.imageUrl
  	? "<%=request.getContextPath()%>/" + event.imageUrl
  	: "<%=request.getContextPath()%>/img/no_img.png";
  	
  	var marker = L.marker([lat, lon], {
  		icon: createEventIcon(imageUrl), riseOnHover: true
  		}).addTo(map);

	markerMap[event.id] = marker;
  	
  	marker.on('click', function() {
  	loadEventDetail(event.id);
  	});
  });
}

  
 </script>

</head>
<body onload="init()">
	<% if (loginUser != null) { %>
    <input type="hidden" id="loginUserId" value="<%= loginUser.getUserId() %>">
	<% } else { %>
	    <input type="hidden" id="loginUserId" value="">
	<% } %>
	<header class="header"> </header>
	<div id="mapcontainer"
		style="position: absolute; top: 0; left: 0; right: 0; bottom: 0;"></div>

	<%@ include file="detailPanel.jsp"%>
	<%@ include file="pinAdd.jsp"%>
	<%@ include file="pinEdit.jsp"%>
	<%@ include file="eventAdd.jsp"%>
	<%@ include file="eventEdit.jsp"%>
	<%@ include file="reviewAdd.jsp"%>
	<%@ include file="reviewEdit.jsp"%>
	


	<div class="create_pin_btn"></div>


	<script
		src="${pageContext.request.contextPath}/js/detailPanel.js?v=20260422"></script>
	<script
		src="${pageContext.request.contextPath}/js/header.js?v=20260422"></script>
	<script
		src="${pageContext.request.contextPath}/js/pinAdd.js?v=20260422"></script>
	<script
		src="${pageContext.request.contextPath}/js/pinEdit.js?v=20260422"></script>
	<script
		src="${pageContext.request.contextPath}/js/pinDelete.js?v=20260422"></script>
	<script
		src="${pageContext.request.contextPath}/js/eventAdd.js?v=20260422"></script>
	<script
		src="${pageContext.request.contextPath}/js/eventEdit.js?v=20260422"></script>
	<script
		src="${pageContext.request.contextPath}/js/eventDelete.js?v=20260422"></script>
	<script
		src="${pageContext.request.contextPath}/js/reviewAdd.js?v=20260422"></script>
	<script
		src="${pageContext.request.contextPath}/js/reviewEdit.js?v=20260422"></script>
	<script
		src="${pageContext.request.contextPath}/js/reviewDelete.js?v=20260422"></script>
	<script
		src="${pageContext.request.contextPath}/js/reviewFavorite.js?v=20260422"></script>
</body>
</html>