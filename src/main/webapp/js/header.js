"use strict";

// ===== ログイン状態 =====
const userName = document.getElementById("loginUserId")?.value || "";
const isLogin = userName !== "";

const loginHtml = isLogin
  ? `${userName}さん<br>ログイン中`
  : `ログイン<br>していません`;

// ===== ヘッダー生成 =====
document.querySelector(".header").innerHTML = `
  <div class="header_container">
    <h1 class="header_logo"><a href="Main">Re HYOGO</a></h1>

    <div class="search_box">
      <div class="search_top">
        <form action="Search" method="get" class="search_form">
          <button class="search_icon_btn" id="searchFormSubmit">
            <img src="img/search.svg" alt="検索" class="search_icon">
          </button>
          <input type="search" class="search_bar" name="keyword" placeholder="検索..." id="keyword">
          <button  class="search_btn">検索</button>
        </form>
      </div>

      <div class="suggest_area">
        <div class="divider"></div>
        <div class="tag_header">
          <span class="tag_text_b">タグ</span>
          <span class="tag_text_s">からさがす</span>
        </div>
        <div class="tag_grid">
          <button class="tag_btn" type="button">食べる</button>
          <button class="tag_btn" type="button">遊ぶ</button>
          <button class="tag_btn" type="button">観る</button>
          <button class="tag_btn" type="button">休む</button>
          <button class="tag_btn" type="button">買う</button>
          <button class="tag_btn-event" type="button">イベント</button>
        </div>
      </div>
    </div>

    <div class="login_status">${loginHtml}</div>

    <button type="button" class="hamburger_menu_btn">
      <span></span>
    </button>

    <nav class="nav">
      <ul class="nav_list">
        <li class="nav_item"><a href="Main">トップ</a></li>
        <li class="nav_item"><a href="Mypage">マイページ</a></li>
        <li class="nav_item"><a href="LoginServlet">ログイン</a></li>
        <li class="nav_item"><a href="Logout">ログアウト</a></li>
        <li class="nav_item"><a href="Help">ヘルプ</a></li>
      </ul>
    </nav>
  </div>
`;

// ===== ハンバーガー =====
const hamburger = document.querySelector('.hamburger_menu_btn');
const nav = document.querySelector('.nav');
const header = document.querySelector('.header');

if (hamburger && nav) {
  hamburger.addEventListener('click', function () {
    hamburger.classList.toggle('menu_open');
    nav.classList.toggle('menu_open');
    header.classList.toggle('menu_open');
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
tags.forEach(tag => {
  tag.addEventListener("click", () => {
    let current = input.value.trim();

    if (current.includes("#イベント")) return;

    const text = tag.textContent.trim();
    const tagText = `#${text}`;

    if (!current.includes(tagText)) {
      input.value = current ? `${current} ${tagText}` : tagText;
    }

    box.classList.add("open");
    keepOpen = true;
    input.focus();
  });
});

// ===== eventタグ =====
eventTags.forEach(tag => {
  tag.addEventListener("click", () => {
    const eventTag = "#イベント";
    let current = input.value;

    let textOnly = current.replace(/#[^\s]+/g, "").trim();

    input.value = textOnly ? `${textOnly} ${eventTag}` : eventTag;

    box.classList.add("open");
    keepOpen = true;
    input.focus();
  });
});

// ===== 外側クリックで閉じる（お気に入りは除外） =====
document.addEventListener("click", (e) => {

  // ★ お気に入りボタンなら検索UIを閉じない
  if (e.target.closest(".search_bookmark")) return;

  const isInside = e.target.closest(".search_box");

  if (!isInside) {
    box.classList.remove("open");
    keepOpen = false;
  }
});

document.getElementById("searchFormSubmit").addEventListener("click", (e) => {
  e.preventDefault();      // ★ デフォルトの submit を止める
  e.stopPropagation();     // ★ 外側クリック判定を止める

  document.querySelector(".search_form").submit();
});


