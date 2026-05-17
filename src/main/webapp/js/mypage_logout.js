"use strict";

document.addEventListener("DOMContentLoaded", function () {
    const logoutArea = document.querySelector(".logout");
    if (!logoutArea) return;

    // 1. HTMLを流し込む（完了用モーダルも追加）
    logoutArea.innerHTML = `
    <!-- ログアウトモーダル -->
    <div id="modal_logout_wrap" style="display:none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 10000; justify-content: center; align-items: center;">
           <div class="mypage_logout_modal">
               <div class="mypage_logout_modal_ttl"><h2>ログアウト</h2></div>
               <p style="text-align:center;">ログアウトします。よろしいですか。</p>
               <div class="mypage_logout_modal_btn">
                   <a href="Logout" id="mypage_logout_btn_yes">はい</a>
                   <a id="mypage_logout_closeBtn" class="mypage_logout_btn_no" style="cursor:pointer;">いいえ</a>
               </div>
           </div>
    </div>

    <!-- 退会最終確認モーダル -->
    <div id="modal_delete_wrap" style="display:none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 10000; justify-content: center; align-items: center;">
           <div class="mypage_logout_modal">
               <div class="mypage_logout_modal_ttl"><h2>退会確認</h2></div>
               <p style="text-align:center;">本当に退会しますか？<br><span style="font-size: 0.8em; color: #666;">この操作は取り消せません。</span></p>
               <div class="mypage_logout_modal_btn">
                   <a id="final_delete_execute_btn" class="mypage_logout_btn_no" style="cursor:pointer;">退会する</a>
                   <a id="final_delete_closeBtn" class="mypage_logout_btn_no" style="cursor:pointer;">キャンセル</a>
               </div>
           </div>
    </div>

    <!-- 退会完了モーダル（新設） -->
    <div id="modal_complete_wrap" style="display:none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 10001; justify-content: center; align-items: center;">
           <div class="mypage_logout_modal">
               <div class="mypage_logout_modal_ttl"><h2>退会完了</h2></div>
               <p style="text-align:center;">退会処理が完了しました。<br>ご利用ありがとうございました。</p>
               <div class="mypage_logout_modal_btn">
                   <a href="javascript:void(0);" id="complete_ok_btn" class="mypage_logout_btn_no" style="cursor:pointer; width: 150px;">トップページへ</a>
               </div>
           </div>
    </div>`;

	logoutArea.innerHTML += `
	<div id="modal_pass_success_wrap" style="display:none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 10002; justify-content: center; align-items: center;">
	       <div class="mypage_logout_modal"> <!-- 既存CSSを流用 -->
	           <div class="mypage_logout_modal_ttl"><h2>変更完了</h2></div>
	           <p style="text-align:center;">パスワードを変更しました。</p>
	           <div class="mypage_logout_modal_btn">
	               <a href="Mypage" class="mypage_logout_btn_no" style="width: 150px; text-decoration: none;">マイページへ</a>
	           </div>
	       </div>
	</div>`;
	
	logoutArea.innerHTML += `
	<!-- パスワード変更確認モーダル -->
	<div id="modal_pass_confirm_wrap" style="display:none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 10003; justify-content: center; align-items: center;">
	       <div class="mypage_logout_modal">
	           <div class="mypage_logout_modal_ttl"><h2>変更確認</h2></div>
	           <p style="text-align:center;">パスワードを変更します。<br>よろしいですか？</p>
	           <div class="mypage_logout_modal_btn">
	               <a id="pass_edit_execute_btn" class="mypage_logout_btn_no" style="cursor:pointer; background-color: #f0f0f0;">はい</a>
	               <a id="pass_edit_cancel_btn" class="mypage_logout_btn_no" style="cursor:pointer;">いいえ</a>
	           </div>
	       </div>
	</div>`;
	
    // 2. モーダル制御関数
    const setupModal = (wrapId, openBtnId, closeBtnId) => {
        const wrap = document.getElementById(wrapId);
        const openBtn = document.getElementById(openBtnId);
        const closeBtn = document.getElementById(closeBtnId);

        if (openBtn && wrap) {
            openBtn.addEventListener('click', (e) => {
                e.preventDefault();
                wrap.style.display = "flex"; 
            });
        }
        if (closeBtn && wrap) {
            closeBtn.addEventListener('click', () => {
                wrap.style.display = "none";
            });
        }
    };

    // 3. 基本イベントの登録
    if(document.getElementById('logout_btn')) setupModal('modal_logout_wrap', 'logout_btn', 'mypage_logout_closeBtn');
    if(document.getElementById('final_delete_open_btn')) setupModal('modal_delete_wrap', 'final_delete_open_btn', 'final_delete_closeBtn');

    // 4. 退会フローの制御
    const deleteBtn = document.getElementById('final_delete_execute_btn'); // 「退会する」ボタン
    const completeModal = document.getElementById('modal_complete_wrap'); // 完了モーダル
    const confirmModal = document.getElementById('modal_delete_wrap');   // 確認モーダル
    const okBtn = document.getElementById('complete_ok_btn');             // 完了モーダルの「OK」
    const deleteForm = document.getElementById('delete_form');

    if (deleteBtn && completeModal && confirmModal) {
        // 「本当に退会する」を押したとき
        deleteBtn.addEventListener('click', (e) => {
            e.preventDefault();
            confirmModal.style.display = "none";  // 確認モーダルを消す
            completeModal.style.display = "flex"; // 完了モーダルを出す
        });
    }

    if (okBtn && deleteForm) {
        // 完了モーダルの「トップページへ」を押したときに送信
        okBtn.addEventListener('click', () => {
            deleteForm.submit();
        });
    }
	
	// パスワード編集確認モーダル
	const successFlag = document.getElementById('edit_success_flag');
	const passSuccessModal = document.getElementById('modal_pass_success_wrap');

	if (successFlag && successFlag.value === 'true' && passSuccessModal) {
	    passSuccessModal.style.display = "flex"; // ここで「ふわっ」と出す
	}
	
	
	// パスワード編集完了モーダル
	const passEditForm = document.getElementById('edit_pass_form'); // JSPのform ID
	const passConfirmModal = document.getElementById('modal_pass_confirm_wrap');
	const passExecuteBtn = document.getElementById('pass_edit_execute_btn');
	const passCancelBtn = document.getElementById('pass_edit_cancel_btn');

	// 1. 「変更」ボタン（submitボタン）が押された時の動きを乗っ取る
	if (passEditForm && passConfirmModal) {
	    passEditForm.addEventListener('submit', (e) => {
	        e.preventDefault(); // すぐに送信されるのを防ぐ
	        passConfirmModal.style.display = "flex"; // 確認モーダルを出す
	    });
	}

	// 2. 「いいえ（キャンセル）」を押したとき
	if (passCancelBtn) {
	    passCancelBtn.addEventListener('click', () => {
	        passConfirmModal.style.display = "none";
	    });
	}

	// 3. 「はい（実行）」を押したとき
	if (passExecuteBtn && passEditForm) {
	    passExecuteBtn.addEventListener('click', () => {
	        passEditForm.submit(); // ここで初めてJavaにデータが飛ぶ！
	    });
	}
});