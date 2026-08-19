const messages = document.getElementById("messages");

const input = document.getElementById("input");

const send = document.getElementById("send");

const newChat = document.getElementById("newChat");

const conversationList =
    document.getElementById("conversationList");

let conversations =
    JSON.parse(
        localStorage.getItem("conversations")
    ) || [];

let currentConversation =
    null;

function save() {

    localStorage.setItem(
        "conversations",
        JSON.stringify(conversations)
    );
}

function scrollBottom() {

    messages.scrollTop =
        messages.scrollHeight;
}

function createConversation() {

    const conversation = {

        id: crypto.randomUUID(),

        title: "新会话",

        messages: []
    };

    conversations.unshift(
        conversation
    );

    currentConversation =
        conversation;

    save();

    renderConversationList();

    renderMessages();
}

function renderConversationList() {

    conversationList.innerHTML =
        "";

    conversations.forEach(
        conversation => {

            const item =
                document.createElement(
                    "div"
                );

            item.className =
                "conversation-item";

            item.textContent =
                conversation.title;

            item.onclick =
                () => {

                    currentConversation =
                        conversation;

                    renderMessages();
                };

            conversationList
                .appendChild(
                    item
                );
        }
    );
}

function renderMessages() {

    messages.innerHTML = "";

    if (
        currentConversation.messages.length === 0
    ) {

        messages.innerHTML =
            `
            <div class="welcome">
                <h1>你好 👋</h1>
                <p>我是你的 AI 助手</p>
            </div>
            `;

        return;
    }

    currentConversation.messages
        .forEach(
            message => {

                addMessage(
                    message.text,
                    message.role
                );
            }
        );
}

function addMessage(
    text,
    role
) {

    const wrapper =
        document.createElement(
            "div"
        );

    wrapper.className =
        `message ${role}`;

    const bubble =
        document.createElement(
            "div"
        );

    bubble.className =
        "bubble";

    bubble.textContent =
        text;

    wrapper.appendChild(
        bubble
    );

    messages.appendChild(
        wrapper
    );

    scrollBottom();

    return bubble;
}

async function sendMessage() {

    const text =
        input.value.trim();

    if (!text) {

        return;
    }

    if (
        !currentConversation
    ) {

        createConversation();
    }

    if (
        currentConversation.messages
            .length === 0
    ) {

        currentConversation.title =
            text.substring(
                0,
                20
            );

        renderConversationList();
    }

    currentConversation.messages
        .push({

            role: "user",

            text
        });

    renderMessages();

    input.value = "";

    const bubble =
        addMessage(
            "",
            "assistant"
        );

    let answer = "";

    try {

        const response =
            await fetch(
                `/chat?msg=${encodeURIComponent(text)}&convId=${currentConversation.id}`
            );

        const reader =
            response.body.getReader();

        const decoder =
            new TextDecoder();

        while (true) {

            const {
                done,
                value
            } =
                await reader.read();

            if (done) {

                break;
            }

            let chunk =
                decoder.decode(
                    value
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

            answer += chunk;

            bubble.textContent =
                answer;

            scrollBottom();
        }

        currentConversation.messages
            .push({

                role: "assistant",

                text: answer
            });

        save();

    } catch {

        bubble.textContent =
            "连接失败";
    }
}

send.onclick =
    sendMessage;

input.addEventListener(
    "keydown",
    e => {

        if (
            e.key === "Enter"
            &&
            !e.shiftKey
        ) {

            e.preventDefault();

            sendMessage();
        }
    }
);

newChat.onclick =
    createConversation;

if (
    conversations.length > 0
) {

    currentConversation =
        conversations[0];

    renderConversationList();

    renderMessages();

} else {

    createConversation();
}