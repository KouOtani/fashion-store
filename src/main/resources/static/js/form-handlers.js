'use strict';

function submitForm(index) {
  document.getElementById('updateForm_' + index).submit();
}

document.getElementById('birthday').addEventListener('input', function(e) {
  let input = e.target.value.replace(/[^0-9]/g, ''); // 数字以外の文字を削除
  let formatted = '';

  // 年部分の処理
  if (input.length >= 4) {
    formatted = input.substring(0, 4) + '/';
    input = input.substring(4);
  } else {
    formatted = input;
    input = '';
  }

  // 月部分の処理
  if (input.length >= 2) {
    formatted += input.substring(0, 2) + '/';
    input = input.substring(2);
  } else {
    formatted += input;
    input = '';
  }

  // 日部分の処理
  if (input.length > 0) {
    formatted += input;
  }

  e.target.value = formatted;
});



