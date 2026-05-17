
<%@ page pageEncoding="UTF-8"%>
<%@ page import="model.User"%>
<script>
  const contextPath = "<%=request.getContextPath()%>";
<%--  var loginUserId = <%= session.getAttribute("loginUser") != null ? ((User)session.getAttribute("loginUser")).getUserId() : -1 %>;--%>
const loginUserId = "<%=(session.getAttribute("loginUser") != null) ? ((User) session.getAttribute("loginUser")).getUserId() : ""%>";
</script>
<!-- スポット詳細パネル -->
<div id="pinDetailPanel" class="detail-panel">
	<div class="panel_wrap">
		<div class="panel_section">
			<!--				<button class="close-btn" onclick="closePanel()">×</button>閉じボタン仮-->
			<div class="title_wrapper">
				<h2 id="name"></h2>
				<button type="button" class="detail_bookmark" id="pinBookmarkBtn">
					<img src="<%=request.getContextPath()%>/img/bookmark-off.svg" class="icon off">
					<img src="<%=request.getContextPath()%>/img/bookmark-on.svg" class="icon on">
				</button>
			</div>
			<div class="date_edit_wraper">
				<div id="createDate"></div>
				<button class="edit_btn" id="editBtn" style="display: none;">編集/削除</button>
				<!--				<button class="pin_edit_btn" style="display: none;"onclick="openPinDetailEditPanel()">編集/削除</button>-->
			</div>
		</div>
		<div class="panel_section">
			<div class="image_wrapper">
				<div id="panel_img">
					<img id="image">
				</div>
				<div class="panel_tags" id="tags"></div>
				<!--						<button class="panel_tag">うんち</button>-->
				<!--						<button class="panel_tag">遊ぶ</button>-->
				<!--						<button class="panel_tag">観る</button>-->
			</div>
		</div>
		<div class="add_review_wrapper">
			<button class="add_review" onclick="loadReviewPinDetail()">
				<img src="<%=request.getContextPath()%>/img/add_review.svg"
					alt="add_review" class="add_review_icon">
			</button>
			<button type="button" class="add_review_a"
				onclick="loadReviewPinDetail(currentPinId)">レビューを追加</button>
		</div>
		
		<div class="panel_section">
			<p id="address"></p>
		</div>
		<div class="panel_section">
			<p>
				URL： <a id="url" target="_blank">詳細</a>
			</p>
		</div>
		<div class="panel_section">
			<p id="info"></p>
		</div>
		<div class="review_section">
			<h4>レビュー</h4>
			<div id="reviewList"></div>
			<!--			<div class="review_auther_wrapper">-->
			<!--				<p>作成者</p>-->
			<!--				<p>作成日 04/23</p>-->
			<!--				<button class="review_edit_btn">編集/削除</button>-->
			<!--			</div>-->
			<!--			<p>レビューレビューレビューレビューレビューレビューレビューレビューレビューレビューレビューレビューレビューレビューレビューレビューレビューレビューレビューレビューレビューレビューレビュー</p>-->
			<!--			<div class="review_good_wrapper">-->
			<!--				<button class="add_good">-->
			<%--					<img src="<%=request.getContextPath()%>/img/add_good.svg"--%>
			<!--						class="good_icon off"> <img-->
			<%--						src="<%=request.getContextPath()%>/img/add_good-on.svg"--%>
			<!--						class="good_icon on">-->
			<!--				</button>-->

			<!--				<p class="good_count">999</p>-->
			<!--			</div>-->
			<!--		</div>-->
		</div>
	</div>
</div>
<!-- イベント詳細パネル -->
<div id="eventDetailPanel" class="detail-panel">
	<div class="panel_wrap">
		<div class="panel_section">
			<!--				<button class="close-btn" onclick="closePanel()">×</button>閉じボタン仮-->
			<div class="title_wrapper">
				<h2 id="event_name"></h2>
				<button type="button" class="detail_bookmark" id="eventBookmarkBtn">
					<img src="<%=request.getContextPath()%>/img/bookmark-off.svg"
						class="icon off"> <img
						src="<%=request.getContextPath()%>/img/bookmark-on.svg"
						class="icon on">
				</button>

			</div>
			<div class="date_edit_wraper">
				<div id="event_createDate"></div>
				<button class="edit_btn" id="eventEditBtn" style="display: none;">編集/削除</button>
			</div>
		</div>
		<div class="panel_section">
			<div class="image_wrapper">
				<div id="panel_img">
					<img id="event_image">
				</div>
			</div>
		</div>
		
		<div class="panel_section">
			<div id="start"></div>
			<br>
			<div id="end"></div>
		</div>
		<div class="panel_section">
			<p id="event_address"></p>
		</div>
		<div class="panel_section">
			<p>
				URL： <a id="event_url" target="_blank">詳細</a>
			</p>
		</div>
		<div class="panel_section">
			<p id="event_info"></p>
		</div>
	</div>
</div>




