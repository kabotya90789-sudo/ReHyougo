"use strict";
{
  const hamburger = document.querySelector('.hamburger_menu_btn');
  const nav = document.querySelector('.nav');

  hamburger.addEventListener('click', function() {
    hamburger.classList.toggle('menu_open');
    nav.classList.toggle('menu_open');
  });
}