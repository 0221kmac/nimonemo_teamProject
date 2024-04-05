import React, { useState } from "react";
import { TbMessageCircleQuestion } from "react-icons/tb";
import { IoClose } from "react-icons/io5";
import { FaArrowUp } from "react-icons/fa";

const ChatBot = () => {
  // 챗봇 표시 상태를 관리하는 state (기본값은 false로 설정하여 초기에 숨김)
  const [showChatbot, setShowChatbot] = useState(false);

  // 챗봇 표시/숨김을 토글하는 함수
  const toggleChatbot = () => {
    setShowChatbot(!showChatbot);
  };

  const handleScrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: "smooth"
    });
  };

  return (
    <div
      className="chatbot-container"
      style={{ position: "fixed", bottom: "20px", right: "20px" }}
    >
      <div className="flex flex-col items-center">
        {showChatbot ? (
          // 챗봇이 표시될 때는 화살표 아이콘이 숨겨짐
          null
        ) : (
          // 챗봇이 숨겨져 있을 때 표시될 화살표 아이콘
          <button
            className="inline-block relative rounded-full w-10 h-10 bg-gray-200 hover:bg-gray-400 transition duration-200 border border-gray-300 mb-3"
            onClick={handleScrollToTop}
          >
            <FaArrowUp className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-7 h-7" />
          </button>
        )}
        {showChatbot ? (
          // 챗봇이 표시될 때는 챗봇 iframe이 보여짐
          <iframe
            title="nimonemo"
            allow="microphone;"
            width="350"
            height="430"
            src="src"
          ></iframe>
        ) : (
          // 챗봇이 숨겨져 있을 때 표시될 채팅 이모티콘 버튼
          <button onClick={toggleChatbot} className="icon-button">
            <div className="inline-block relative rounded-full w-10 h-10 bg-gray-200 hover:bg-gray-400 transition duration-200 border border-gray-300">
              <TbMessageCircleQuestion className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-8 h-8" />
            </div>
          </button>
        )}

        {/* 챗봇이 표시될 때만 닫기 버튼 또는 이모티콘 표시 버튼을 보여줌 */}
        {showChatbot && (
          <button
            onClick={toggleChatbot}
            className="absolute top-0 right-0 p-2 text-white"
          >
            <IoClose className="text-white w-8 h-8" />
          </button>
        )}
      </div>
    </div>
  );
};

export default ChatBot;
