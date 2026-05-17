"use strict";

document.addEventListener("DOMContentLoaded", function () {

  // ===== ヘッダー生成 =====
  document.querySelector(".header").innerHTML = `

    <div class="header_container">
    <h1 class="header_logo"><a href="#menu_top">Re HYOGO</a></h1>

  <div class="search_box">
  <!-- 上：検索バー -->
  <div class="search_top">
    <form action="/search" method="get" class="search_form">

  <button type="submit" class="search_icon_btn">
  <img src="data/img/search.svg" alt="検索" class="search_icon">
  </button>

  <input type="search" class="search_bar" name="q" placeholder="検索...">

  <button type="submit" class="search_btn">検索</button>

</form>
  </div>

  <!-- 下：サジェスト -->
  <div class="suggest_area">

    <div class="divider"></div>

    <div class="tag_header">
      <span class="tag_text_b">タグ</span>
      <span class="tag_text_s">からさがす</span>
    </div>

    <div class="tag_grid">
      <button class="tag_btn">食べる</button>
      <button class="tag_btn">遊ぶ</button>
      <button class="tag_btn">観る</button>
      <button class="tag_btn">休む</button>
      <button class="tag_btn">買う</button>
      <button class="tag_btn-event">イベント</button>
    </div>

  </div>

</div>
<button type="button" class="hamburger_menu_btn">
      <span></span>
    </button>

    <nav class="nav">
      <ul class="nav_list">
        <li class="nav_item"><a href="#menu_top">トップ</a></li>
        <li class="nav_item"><a href="#menu_mypage">マイページ</a></li>
        <li class="nav_item"><a href="#menu_logout">ログアウト</a></li>
        <li class="nav_item"><a href="#menu_help">ヘルプ</a></li>
      </ul>
    </nav>   
  </div>
  `;

  // ===== ハンバーガー =====
  const hamburger = document.querySelector('.hamburger_menu_btn');
  const nav = document.querySelector('.nav');

  if (hamburger && nav) {
    hamburger.addEventListener('click', function () {
      hamburger.classList.toggle('menu_open');
      nav.classList.toggle('menu_open');
    });
  }

  // ===== 検索UI制御 =====
  const box = document.querySelector(".search_box");
  const input = document.querySelector(".search_bar");
  const tags = document.querySelectorAll(".tag_btn");
  const eventTags = document.querySelectorAll(".tag_btn-event");

  let keepOpen = false;

  if (box && input) {

    input.addEventListener("focus", () => {
      box.classList.add("open");
      keepOpen = true;
    });

    input.addEventListener("input", () => {
      box.classList.add("open");
      keepOpen = true;
    });

    input.addEventListener("blur", () => {
      setTimeout(() => {
        if (!keepOpen) {
          box.classList.remove("open");
        }
      }, 150);
    });
  }

  // ===== 通常タグ =====
  if (input && tags.length > 0) {
    tags.forEach(tag => {
      tag.addEventListener("click", () => {

        let current = input.value.trim();

        // #イベント中は通常タグ無効
        if (current.includes("#イベント")) return;

        const text = tag.textContent.trim();
        const tagText = `#${text}`;

        if (current.includes(tagText)) return;

        input.value = current ? `${current} ${tagText}` : tagText;

        box.classList.add("open");
        keepOpen = true;
        input.focus();
      });
    });
  }

  // ===== eventタグ =====
  if (input && eventTags.length > 0) {
    eventTags.forEach(tag => {
      tag.addEventListener("click", () => {

        const eventTag = "#イベント";

        let current = input.value;

        // ★ #タグだけ削除（普通の文章は残す）
        let textOnly = current.replace(/#[^\s]+/g, "").trim();

        // ★ 最後に #イベント追加
        input.value = textOnly
          ? `${textOnly} ${eventTag}`
          : eventTag;

        box.classList.add("open");
        keepOpen = true;
        input.focus();
      });
    });
  }

  // ===== 外側クリックで閉じる =====
  document.addEventListener("click", (e) => {
    const isInside = e.target.closest(".search_box");

    if (!isInside) {
      box.classList.remove("open");
      keepOpen = false;
    }
  });
});

//===============================================================
//     ■ブックマーク
//============================================================== 
document.querySelectorAll('.search_bookmark').forEach(btn => {
  btn.addEventListener('click', function () {
    this.classList.toggle('is-active');
  });
});