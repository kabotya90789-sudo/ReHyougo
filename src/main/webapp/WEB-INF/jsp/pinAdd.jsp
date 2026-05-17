<%@ page pageEncoding="UTF-8" %>
  <!-- 種別選択パネル -->
<div id="selectPanel" class="select-panel">
	<button onclick="openAddPanel('event')">イベントを追加</button>
	<button onclick="openAddPanel('spot')">スポットを追加</button>
</div>
<!-- 追加パネル（input版） -->
<!--	スポット-->
<div id="spot-addPanel" class="add-panel">
	<div class="panel_section">
		<div class="title_wrapper">
			<h2>スポット追加</h2>
			<button class="close-btn" onclick="closePinAddPanel()">
				<img src="<%=request.getContextPath()%>/img/close.svg" alt="閉じる">
			</button>
		</div>
	</div>
	スポット名：<input id="add_name" placeholder="名前"><br>
	<!--    <input id="add_date" placeholder="日付">-->
	<div class="pa_image_add_wrap">
		<div class="pa_current_image">
			<img id="preview" src="" src="<%=request.getContextPath()%>/img/no_img.png" alt="プレビュー">
		</div>
  		<div class="pa_upload_area">
    		<label class="pa_file_label"> 
      			<span class="pa_file_button">ファイルを選択</span>
      			<input id="add_image" type="file" accept="image/*">
    		</label>
			<p id="pa_file_name">選択されていません</p>
			<button type="button" id="cancel_image_btn">画像を取消</button>
		</div>
	</div>
	<br>
	<div class="checkbox">
	タグ：<label><input type="checkbox" class="tag" name="tags" value="食べる">食べる</label> 
		 <label><input type="checkbox" class="tag" name="tags" value="遊ぶ">遊ぶ</label>
		 <label><input type="checkbox" class="tag" name="tags" value="観る">観る</label>
		 <label><input type="checkbox" class="tag" name="tags" value="買う">買う</label> 
		 <label><input type ="checkbox" class="tag" name="tags" value="休む">休む</label>
	</div>

	住所：<input id="add_address" placeholder="住所">

	URL：<input id="add_url" placeholder="URL">
	紹介文：
	<textarea id="add_info" placeholder="250文字以内"></textarea>
	<div class="panel_section"></div>
	<button class= "add_pin_btn" type="button" onclick="submitPin()">登録</button>
</div>
