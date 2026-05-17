"use strict";

//===============================================================
//     ■ブックマーク（誤発火防止版）
//=============================================================== 
document.querySelectorAll('.search_bookmark').forEach(btn => {

  // ★ ボタンとして扱う
  btn.setAttribute("type", "button");

  btn.addEventListener('click', function (e) {

    // ★ 検索フォームの submit を完全に止める
    e.preventDefault();
    e.stopPropagation();

    this.classList.toggle('is-active');

    const action = this.classList.contains('is-active') ? "add" : "remove";
    alert(action === "add" ? "お気に入り登録しました" : "お気に入り解除しました");

    // ★ form を作ってサーバーへ POST 送信
    const form = document.createElement("form");
    form.method = "POST";
    form.action = "FavoriteUpdateServlet";

    const userId = this.dataset.userid;
    const pinId = this.dataset.pinid || null;
    const evtId = this.dataset.evtid || null;
    const keyword = this.dataset.keyword;

    const userInput = document.createElement("input");
    userInput.type = "hidden";
    userInput.name = "userId";
    userInput.value = userId;
    form.appendChild(userInput);

    if (pinId !== null) {
      const pinInput = document.createElement("input");
      pinInput.type = "hidden";
      pinInput.name = "pinId";
      pinInput.value = pinId;
      form.appendChild(pinInput);
    }

    if (evtId !== null) {
      const evtInput = document.createElement("input");
      evtInput.type = "hidden";
      evtInput.name = "evtId";
      evtInput.value = evtId;
      form.appendChild(evtInput);
    }

    const actInput = document.createElement("input");
    actInput.type = "hidden";
    actInput.name = "action";
    actInput.value = action;
    form.appendChild(actInput);

    const keywordInput = document.createElement("input");
    keywordInput.type = "hidden";
    keywordInput.name = "keyword";
    keywordInput.value = keyword;
    form.appendChild(keywordInput);

    document.body.appendChild(form);
    form.submit();
  });
});
