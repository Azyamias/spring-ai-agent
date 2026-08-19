const messages =
    document.getElementById("messages");

const input =
    document.getElementById("input");

const sendButton =
    document.getElementById("send");

const newChatButton =
    document.getElementById("newChat");

let conversationId =
    crypto.randomUUID();

function scrollToBottom() {

    messages.scrollTop =
        messages.scrollHeight;
}

function addMessage(
    text,
    role
) {

    const message =
        document.createElement("div");

    message.className =
        `message ${role}`;

    const bubble =
        document.createElement("div");

    bubble.className =
        "bubble";

    bubble.textContent =
        text;

    message.appendChild(
        bubble
    );

    messages.appendChild(
        message
    );

    scrollToBottom();

    return bubble;
}

async function sendMessage() {

    const text =
        input.value.trim();

    if (!text) {

        return;
    }

    if (
        document.querySelector(
            ".welcome"
        )
    ) {

        document.querySelector(
            ".welcome"
        ).remove();
    }

    addMessage(
        text,
        "user"
    );

    input.value = "";

    const aiBubble =
        addMessage(
            "正在思考...",
            "assistant"
        );

    try {

        const response =
            await fetch(
                `/chat?msg=${encodeURIComponent(text)}&convId=${conversationId}`
            );

        if (!response.ok) {

            aiBubble.textContent =
                "服务器异常";

            return;
        }

        const reader =
            response.body.getReader();

        const decoder =
            new TextDecoder();

        aiBubble.textContent = "";

        while (true) {

            const result =
                await reader.read();

            if (
                result.done
            ) {

                break;
            }

            let chunk =
                decoder.decode(
                    result.value
                );

            chunk =
                chunk
                    .replaceAll(
                        "data:",
                        ""
                    )
                    .replaceAll(
                        "\n",
                        ""
                    );

            aiBubble.textContent +=
                chunk;

            scrollToBottom();
        }

    } catch (error) {

        console.error(
            error
        );

        aiBubble.textContent =
            "连接失败";
    }
}

sendButton.addEventListener(
    "click",
    sendMessage
);

input.addEventListener(
    "keydown",
    function (event) {

        if (
            event.key === "Enter"
            &&
            !event.shiftKey
        ) {

            event.preventDefault();

            sendMessage();
        }
    }
);

newChatButton.addEventListener(
    "click",
    function () {

        conversationId =
            crypto.randomUUID();

        messages.innerHTML =
            `
            <div class="welcome">

                <h1>你好 👋</h1>

                <p>我是你的 AI 助手</p>

            </div>
            `;
    }
);