sessionStorage.setItem("test", "1234");
sessionStorage.getItem("test");
localStorage.setItem("test2", "1234");

{
    let $writeBtn = document.querySelector('.add-post-btn');

    $writeBtn.addEventListener('click', function () {
        location.href = '/board/write';
    });
}

{
    $(document).ready(function () {
        $('#chatbot-open').click(function () {
            $('#chatbot').show();
            $('#chatbot-open').hide();
        });

        $('#chatbot-close').click(function () {
            $('#chatbot').hide();
            $('#chatbot-open').show();
        });
    });

// 보내기 버튼 클릭 이벤트
    $('#chatbot-send').on('click', function () {
        // console.log('aaa');
        let message = $('#chatbot-input').val();
        addUserMessage(message);
        $('#chatbot-input').val('');
        sendMessage(message, addBotMessage); // 챗봇 비동기 통신
    });

// input칸 엔터 이벤트
    $('#chatbot-input').on('keypress', function (e) {
        // console.log('bbb');
        // console.log(e.code);

        if (e.code == 'Enter') {
            let message = $(this).val();
            addUserMessage(message);
            $(this).val('');
            sendMessage(message, addBotMessage); // 챗봇 비동기 통신
        }
    });

// 유저 메세지 추가
    function addUserMessage(message) {
        let htmlCode = `<div class="user-message message">
          <div class="message-text">${message}</div>
        </div>`;

        $('.chatbot-body').append(htmlCode);
        $('.chatbot-body')[0].scrollTop = $('.chatbot-body')[0].scrollHeight;// 스크롤 하단으로 이동
    }
// 챗봇 메세지 추가
    function addBotMessage(message) {
        let htmlCode = `<div class="bot-message message">
          <div class="message-text">${message}</div>
        </div>`;

        $('.chatbot-body').append(htmlCode);
        $('.chatbot-body')[0].scrollTop = $('.chatbot-body')[0].scrollHeight;// 스크롤 하단으로 이동
    }

}



function sendMessage(content, callback){
    fetch('/v1/gpt/question', {
        method : 'post',
        headers : {'Content-Type': 'application/json'},
        body : JSON.stringify({content : content})
    }).then(resp => resp.json())
        .then(json => {
            console.log(json)
            callback(json.choices[0].message.content)
        })
}












