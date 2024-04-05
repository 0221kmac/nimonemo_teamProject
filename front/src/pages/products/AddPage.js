import { useSelector } from "react-redux";
import AddComponent_c from "../../components/c_products/AddComponent_c";
import { Navigate, useNavigate } from "react-router-dom";

const AddPage = () => {
  const loginState = useSelector((state) => state.loginSlice);
  const navigate = useNavigate();

  if (!loginState.email) {
    return <Navigate to="/member/login" />;
  }
  return (
    <div className="p-4 w-full bg-white">
      <div className="text-3xl font-extrabold">상품 추가하기</div>

      <AddComponent_c />
    </div>
  );
};

export default AddPage;
