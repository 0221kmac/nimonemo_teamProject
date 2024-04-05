import { Navigate, useParams } from "react-router-dom";
import ComModComponent from "../../components/community/ComModComponent";
import { useSelector } from "react-redux";

const ModifyPage = () => {
  const { comBoardBno } = useParams();
  const loginState = useSelector((state) => state.loginSlice);
  if (!loginState.email) {
    return <Navigate to="/member/login" />;
  }
  return (
    <div className="p-4 w-full bg-white">
      <div className="text-3xl font-extrabold">게시글 수정하기</div>

      <ComModComponent comBoardBno={comBoardBno} />
    </div>
  );
};

export default ModifyPage;
