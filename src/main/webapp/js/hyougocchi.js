"use strict";

// ===== キャラ画像ランダム表示 =====

const char = document.getElementById("char");

// キャラ画像一覧
const chars = [
  "hyougocchi1.png",
  "taberu1.png",
  "taberu2.png",
  "asobu1.png",
  "asobu2.png",
  "kau1.png",
  "kau2.png",
  "yasumu1.png",
  "yasumu2.png",
  "miru1.png",
  "miru2.png"
];

// ランダム番号
const randomIndex = Math.floor(Math.random() * chars.length);

// キャラ画像変更
char.src = `${contextPath}/img/${chars[randomIndex]}`;



// ==== アニメーション ====
const wrap = document.getElementById("charWrap");

let x = 0;

function sleep(ms) {
  return new Promise(r => setTimeout(r, ms));
}

async function jump() {
  const char = wrap.querySelector(".char");

  char.classList.add("jump");

  await sleep(600);

  char.classList.remove("jump");

  await sleep(200);
}

async function loop() {

  while (true) {

    // 右200
    x += 120;
    wrap.style.transform = `translateX(calc(-50% + ${x}px))`;
    await sleep(2000);

    await sleep(700);

    await jump();

    // 左200
    x -= 120;
    wrap.style.transform = `translateX(calc(-50% + ${x}px))`;
    await sleep(2000);

    await sleep(1000);

    await jump();

    // 左200
    x -= 120;
    wrap.style.transform = `translateX(calc(-50% + ${x}px))`;
    await sleep(2000);

    await sleep(1000);

    await jump();

    // 右200
    x += 120;
    wrap.style.transform = `translateX(calc(-50% + ${x}px))`;
    await sleep(2000);

    await sleep(1000);

    await jump();
  }
}

loop();