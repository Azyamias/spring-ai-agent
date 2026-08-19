const chatBox = document.getElementById("chatBox");
const messageInput = document.getElementById("messageInput");
const sendBtn = document.getElementById("sendBtn");
const newChat = document.getElementById("newChat");

let convId = crypto.randomUUID();

function addMessage(content, type) {

    const div = document.createElement("div");

    div.className = `message ${type}`;

    div.textContent = content;

    chatBox.appendChild(div);

    chatBox.scrollTop = chatBox.scrollHeight;

    return div;
}

async function sendMessage() {

    const msg = messageInput.value.trim();

    if (!msg) {
        return;
    }

    addMessage(msg, "user");

    messageInput.value = "";

    const aiMessage = addMessage("", "ai");

    try {

        const response = await fetch(
            `/chat?msg=${encodeURIComponent(msg)}&convId=${convId}`
        );

        const reader = response.body.getReader();

        const decoder = new TextDecoder();

        while (true) {

            const {done, value} =
                await reader.read();

            if (done) {
                break;
            }

            aiMessage.textContent +=
                decoder.decode(value);

            chatBox.scrollTop =
                chatBox.scrollHeight;
        }

    } catch (e) {

        aiMessage.textContent =
            "服务器连接失败";
    }
}

sendBtn.addEventListener(
    "click",
    sendMessage
);

messageInput.addEventListener(
    "keydown",
    function (e) {

        if (e.key === "Enter" && !e.shiftKey) {

            e.preventDefault();

            sendMessage();
        }
    }
);

newChat.addEventListener(
    "click",
    function () {

        convId = crypto.randomUUID();

        chatBox.innerHTML = "";

        addMessage(
            "你好，我是 AI 助手，请开始聊天。",
            "ai"
        );
    }
);