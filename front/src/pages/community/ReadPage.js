import { useParams } from "react-router-dom";
import ComReadComponent from "../../components/community/ComReadComponent";

const ReadPage = () => {
  const { comBoardBno } = useParams();

  return (
    <div className="p-4 w-full bg-white">
      {/* <div className="text-3xl font-extrabold">상품 정보</div> */}

      <ComReadComponent comBoardBno={comBoardBno}></ComReadComponent>
    </div>
  );
};

export default ReadPage;
