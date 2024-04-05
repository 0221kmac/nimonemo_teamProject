import { useEffect, useState } from "react";
import { getComBoard } from "../../api/communityApi";
import { API_SERVER_HOST } from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";
import FetchingModal from "../common/FetchingModal";
import useCustomLogin from "../../hooks/useCustomLogin";

const initState = {
  comBoardBno: 0,
  comBoardTitle: "",
  comBoardContent: "",
  comBoardWriter: 0,
  uploadFileNames: [],
};

const host = API_SERVER_HOST;

const ComReadComponent = ({ comBoardBno }) => {
  const [comBoard, setComBoard] = useState(initState);
  //화면 이동용 함수
  const { moveToList, moveToModify } = useCustomMove();
  //fetching
  const [fetching, setFetching] = useState(false);

  // 로그인 정보
  const { loginState } = useCustomLogin();

  useEffect(() => {
    setFetching(true);

    getComBoard(comBoardBno).then((data) => {
      setComBoard(data);
      setFetching(false);
    });
  }, [comBoardBno]);

  console.log("이미지정보" + comBoard.uploadFileNames);
  return (
    <div className="border-2 border-gray-300 mt-10 m-2 p-4">
      {fetching ? <FetchingModal /> : <></>}

      <div
        id="comboard_image_zone"
        className="w-full justify-center flex  flex-col m-auto items-center"
      >
        {comBoard.uploadFileNames &&
          comBoard.uploadFileNames.map((fileName, i) => (
            <img
              alt="comBoard"
              key={i}
              className="p-4 w-1/2"
              src={`${host}/api/comBoard/view/${fileName}`}
            />
          ))}
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">제목</div>
          <div className="w-4/5 p-6 rounded-r border border-solid shadow-md">
            {comBoard.comBoardTitle}
          </div>
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">내용</div>
          <div className="w-4/5 p-6 rounded-r border border-solid shadow-md">
            {comBoard.comBoardContent}
          </div>
        </div>
      </div>
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">작성자</div>
          <div className="w-4/5 p-6 rounded-r border border-solid shadow-md">
            {comBoard.comBoardWriter}
          </div>
        </div>
      </div>

      <div
        id="product_read_buttons"
        className="flex justify-end p-4 text-sm text-white"
      >
        <button
          type="button"
          className="inline-block rounded p-4 m-2 w-32 bg-gray-800"
          onClick={() => moveToModify(comBoardBno)}
        >
          게시글
          <br />
          수정
        </button>
        {/* <button
          type="button"
          className="rounded p-4 m-2 w-32 bg-gray-800"
          onClick={moveToList}
        >
          목록으로
          <br />
          돌아가기
        </button> */}
      </div>
    </div>
  );
};

export default ComReadComponent;
