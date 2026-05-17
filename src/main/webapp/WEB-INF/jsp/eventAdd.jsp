<%@ page pageEncoding="UTF-8"%>
<div id="event-addPanel" class="add-panel">
	<div class="panel_section">
		<div class="title_wrapper">
			<h2>イベント追加</h2>
			<button class="close-btn" onclick="closeEventAddPanel()">
				<img src="<%=request.getContextPath()%>/img/close.svg" alt="閉じる">
			</button>
		</div>
	</div>
	イベント名：<input id="add_event_name" placeholder="名前"><br>

	<div class="ea_image_add_wrap">
		<div class="ea_current_image">
			<img id="event_preview" src="" src="<%=request.getContextPath()%>/img/no_img.png" alt="プレビュー">
		</div>
  		<div class="ea_upload_area">
    		<label class="ea_file_label"> 
      			<span class="ea_file_button">ファイルを選択</span>
      			<input id="add_event_image" type="file" accept="image/*">
    		</label>
			<p id="ea_file_name">選択されていません</p>
			<button type="button" id="event_cancel_image_btn">画像を取消</button>
		</div>
	</div>

	開始日：<input id="add_event_start" type = "date" placeholder="開始日">
	終了日：<input id="add_event_end" type = "date" placeholder="終了日">


	住所：<input id="add_event_address" placeholder="住所">

	URL：<input id="add_event_url" placeholder="URL">

	紹介文：
	<textarea id="add_event_info" placeholder="250文字以内"></textarea>
	<div class="panel_section">	</div>
	<button class= "add_event_btn" onclick="submitEvent()">登録</button>

</div>