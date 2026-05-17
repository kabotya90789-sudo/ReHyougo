"use strict";


let currentPinId = null; //詳細編集

// ===== ピンボタン =====
 const pinBtn = document.querySelector('.create_pin_btn');
 if (pinBtn) {
   pinBtn.addEventListener('click', function () {
     document.getElementById("selectPanel").classList.add("open");
   });
 }



