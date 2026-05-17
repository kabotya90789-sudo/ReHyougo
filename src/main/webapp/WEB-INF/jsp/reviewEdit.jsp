<%@ page pageEncoding="UTF-8"%>
<div id="edit_reviewPanel" class="edit-review-panel">
	<div class="panel_section">
		<!--        <button class="close-btn" onclick="closeAddreviewPanel()">×</button>-->
		<div class="title_wrapper">
			<h2>レビューを編集</h2>
			<button onclick="deleteReview()">削除</button>
			<button class="close-btn" onclick="closeEditReviewPanel()">
				<img src="<%=request.getContextPath()%>/img/close.svg" alt="閉じる">
			</button>
		</div>
	</div>
	<div class="panel_section">
		<div class="image_wrapper">
			<div id="panel_img">
				<img id="editReviewSpotImage">
			</div>
			<h3 id="editReviewSpotName"></h3>

			<input type="hidden" id="reviewPinId" name="pinId">
		</div>
		<p class="edit_review_p">レビュータイトル</p>
		<input id="edit_review_title">
		<p class="edit_review_p">レビュー編集</p>
		<div class="edit_review_wrapper">
			<textarea class="text_input" id="edit_review_content" placeholder="250文字以内"></textarea>
		</div>
	</div>
	<button class="edit_review_btn" onclick="editReview()">変更</button>
</div>
