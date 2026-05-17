<%@ page pageEncoding="UTF-8"%>
<div id="add_reviewPanel" class="add-review-panel">
	<div class="panel_section">
		<!--        <button class="close-btn" onclick="closeAddreviewPanel()">×</button>-->
		<div class="title_wrapper">
			<h2>レビューを追加</h2>
			<!--				<button class="add_review_btn_icon" onclick="#">-->
			<!--					<img src="<%=request.getContextPath()%>/img/add_review_btn.svg"-->
			<!--						alt="add_review" class="add_review_btn_icon">-->
			<!--				</button>-->
			<button class="close-btn" onclick="closeAddreviewPanel()">
				<img src="<%=request.getContextPath()%>/img/close.svg" alt="閉じる">
			</button>
		</div>
	</div>
	<div class="panel_section">
		<div class="image_wrapper">
			<div id="panel_img">
				<img id="spotImage">
			</div>
			<h3 id="reviewName"></h3>
			<!--			<input id="reviewPinId" name="pinId">-->
		</div>
		<h4 class="add_review_p">レビュータイトル</h4>
		<input id="review_title" placeholder="タイトル">
		<h4 class="add_review_p">レビュー追加</h4>
		<div class="add_review_wrapper">
			<textarea class = "text_input" id="review_Content" placeholder="250文字以内"></textarea>
			<!--				<input type="submit" class="add_review_btn" value="追加">-->
		</div>
	</div>
	<button class="add_review_btn" onclick="addReview()">追加</button>
</div>

<%--<%@ page pageEncoding="UTF-8"%>--%>
<!--<form action="ReviewAdd" method="post">-->
<!--	<div id="add_reviewPanel" class="add-review-panel">-->
<!--		<div class="panel_section">-->
<!--			        <button class="close-btn" onclick="closeAddreviewPanel()">×</button>-->
<!--			<div class="title_wrapper">-->
<!--				<h2>レビューを追加</h2>-->
<!--								<button class="add_review_btn_icon" onclick="#">-->
<%--									<img src="<%=request.getContextPath()%>/img/add_review_btn.svg"--%>
<!--										alt="add_review" class="add_review_btn_icon">-->
<!--								</button>-->
<!--				<button type="button" class="close-btn" onclick="closeAddreviewPanel()">-->
<%--				<img src="<%=request.getContextPath()%>/img/close.svg" alt="閉じる">--%>
<!--				</button>-->
<!--			</div>-->
<!--		</div>-->
<!--		<div class="panel_section">-->
<!--			<div class="image_wrapper">-->
<!--				<div id="panel_img">-->
<!--					<img id="spotImage">-->
<!--				</div>-->
<!--				<h3 id="reviewName"></h3>-->
<!--				<input type="hidden" id="reviewPinId" name="pinId">-->
<!--			</div>-->
<!--			<p class="add_review_p">レビュータイトル</p>-->
<!--			<input type="text" id="review_title" name="reviewTitle">-->
<!--			<p class="add_review_p">レビュー追加</p>-->
<!--			<div class="add_review_wrapper">-->
<!--				<textarea class="review_input" name="reviewContent"-->
<!--					placeholder="250文字以内"></textarea>-->
<!--				<input type="submit" class="add_review_btn" value="追加">-->
<!--								<button class="add_review_btn" onclick="addReview()">追加</button>-->
<!--			</div>-->
<!--		</div>-->
<!--	</div>-->
<!--</form>-->