<%@ page pageEncoding="UTF-8"%>
<!--詳細→編集-->
<div id="spot-editPanel" class="edit-panel">
	<div class="panel_wrap">
		<div class="panel_section">
			<div class="title_wrapper">
				<h2>スポット編集</h2>
				<button onclick="deletePin()">削除</button>
				<button class="close-btn" onclick="closePinDetailEditPanel()">
					<img src="<%=request.getContextPath()%>/img/close.svg" alt="閉じる">
				</button>
			</div>
		</div>

		スポット名： <input id="edit_name" placeholder="名前">
		<!--		 画像： <input id="edit_image" type="file" accept="image/*"> <label>-->
		<!--			<input type="checkbox" id="delete_image"> 画像を削除する-->
		<!--		</label>-->
		<!--		<p>現在の画像</p>-->
		<!--		<img id="edit_preview" src="" alt="プレビュー" style="max-width: 200px;">-->
		<div class="image_edit_wrap">

			<div class="image_edit_wrap">

				<!-- 左：現在の画像 -->
				<div class="current_image">
					<p>現在の画像：</p>
					<img id="edit_preview" src="" alt="プレビュー">
				</div>

				<!-- 右：アップロード -->
				<div class="upload_area">

					<label class="file_label"> 
						<span class="file_button">ファイルを選択</span>
						<input id="edit_image" class="edit_image" type="file" accept="image/*">
					</label>

					<p id="file_name">変更されていません</p>

	    			<!-- 元画像削除 -->
	    			<label class="checkbox_label" id="delete_wrap">
	      				<input type="checkbox" id="delete_image"> 画像を削除
	    			</label>
				    <!-- 新規画像キャンセル -->
	    			<button type="button" id="edit_cancel_image_btn">変更をキャンセル</button>
				</div>

			</div>
		</div>
		<br>
		<div class="checkbox">
			タグ：<label><input type="checkbox" class="tag" name="edit_tags" value="食べる">食べる</label> 
				 <label><input type="checkbox" class="tag" name="edit_tags" value="遊ぶ">遊ぶ</label>
				 <label><input type="checkbox" class="tag" name="edit_tags" value="観る">観る</label>
				 <label><input type="checkbox" class="tag" name="edit_tags" value="買う">買う</label> 
				 <label><input type ="checkbox" class="tag" name="edit_tags" value="休む">休む</label>
		</div>
		住所： <input id="edit_address" placeholder="住所"> 
		URL： <input id="edit_url" placeholder="URL"> 
			紹介文：<textarea id="edit_info"></textarea>

		<div class="panel_section"></div>
		<button class= "update_pin_btn" onclick="updatePin()">更新</button>
	</div>
</div>

