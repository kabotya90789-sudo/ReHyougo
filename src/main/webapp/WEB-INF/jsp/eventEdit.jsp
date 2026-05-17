<%@ page pageEncoding="UTF-8"%>
<!--詳細→編集-->
<div id="event-editPanel" class="edit-panel">
	<div class="panel_wrap">
		<div class="panel_section">
			<div class="title_wrapper">
				<h2>イベント編集</h2>
				<button onclick="deleteEvent()">削除</button>
				<button class="close-btn" onclick="closeEventDetailEditPanel()">
					<img src="<%=request.getContextPath()%>/img/close.svg" alt="閉じる">
				</button>
			</div>
		</div>
		イベント名： <input id="edit_event_name" placeholder="名前">
		
		<div class="image_edit_wrap">

			<!-- 左：現在の画像 -->
			<div class="current_image">
				<p>現在の画像：</p>
				<img id="edit_event_preview" src="" alt="プレビュー">
			</div>

			<!-- 右：アップロード -->
			<div class="upload_area">

				<label class="file_label"> 
					<span class="file_button">ファイルを選択</span>
					<input id="edit_event_image" class="event_edit_image" type="file" accept="image/*">
				</label>

				<p id="event_file_name">変更されていません</p>

				<label class="checkbox_label" id="event_delete_wrap"> 
					<input type="checkbox" id="delete_event_image"> 画像を削除
				</label>
				 <!-- 新規画像キャンセル -->
	    		<button type="button" id="edit_cancel_event_image_btn">変更をキャンセル</button>

			</div>

		</div> 
<!--			画像： <input-->
<!--			id="event_edit_image" type="file" accept="image/*"> <label>-->
<!--			<input type="checkbox" id="delete_event_image"> 画像を削除する-->
<!--		</label>-->
<!--		<p>現在の画像</p>-->
<!--		<img id="edit_event_preview" src="" alt="プレビュー"-->
<!--			style="max-width: 200px;">-->
			開始日：<input id="edit_event_start" type="date" placeholder="開始日">
			終了日：<input id="edit_event_end" type="date" placeholder="終了日"> 
			住所： <input id="edit_event_address" placeholder="住所"> 
			URL： <input id="edit_event_url" placeholder="URL"> 
			紹介文：
		<textarea id="edit_event_info"></textarea>
		<div class="panel_section"></div>
		<button class=update_event_btn onclick="updateEvent()">更新</button>
	</div>
</div>