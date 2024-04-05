import ChatBot from "../components/chatbot/ChatBot";
import BasicMenu from "../components/menus/BasicMenu";

const BasicLayout = ({ children }) => {
  return (
    <>
      <div
        style={{
          position: "absolute",
          top: 0,
          left: 0,
          right: 0,
          zIndex: 9999,
          minWidth: 710,
        }}
      >
        <BasicMenu />
      </div>
      <div style={{ paddingTop: "212px", minWidth: 710 }}>{children}</div>

      <ChatBot />
    </>
  );
};

export default BasicLayout;
