import { useEffect, useState } from "react";
import useCustomMove from "../../hooks/useCustomMove";

import { API_SERVER_HOST } from "../../api/todoApi";
import useCustomLogin from "../../hooks/useCustomLogin";
import { comList } from "../../api/communityApi";
import { Link } from "react-router-dom";

const host = API_SERVER_HOST;

const initState = {
  dtoList: [],
  pageNumList: [],
  pageRequestDTO: null,
  next: false,
  totalCount: 0,
  prevPage: 0,
  nextPage: 0,
  totalPage: 0,
  current: 0,
};

const ComListComponent = () => {
  const { exceptionHandle } = useCustomLogin();
  const { page, size, refresh, moveToList, moveToRead } = useCustomMove();

  //serverData는 나중에 사용
  const [serverData, setServerData] = useState(initState);

  useEffect(() => {
    comList({ page, size })
      .then((data) => {
        console.log(data);
        setServerData(data);
      })
      .catch((err) => exceptionHandle(err));
  }, [page, size, refresh]);

  return (
    <div className="border-2 border-gray-300 mt-10 mr-2 ml-2">
      <div className="p-6">
        {serverData.dtoList &&
          serverData.dtoList.map((comBoard) => (
            <div
              key={comBoard.comBoardBno}
              className="rounded shadow-md border-2 mb-4 p-4 bg-white"
              onClick={() => moveToRead(comBoard.comBoardBno)}
            >
              {comBoard.uploadFileNames &&
                comBoard.uploadFileNames.length > 0 && (
                  <div className="w-full overflow-hidden my-4">
                    <img
                      alt="comBoard"
                      className="m-auto rounded-md"
                      style={{ maxWidth: "200px", maxHeight: "200px" }}
                      src={`${host}/api/comBoard/view/${comBoard.uploadFileNames[0]}`}
                    />
                  </div>
                )}
              <div className="font-bold text-xl mb-2">
                {comBoard.comBoardTitle}
              </div>

              <p>{comBoard.comBoardWriter}</p>
            </div>
          ))}
      </div>

      {/* 페이지 목록 렌더링 */}
      {serverData.pageNumList && serverData.pageNumList.length > 0 && (
        <div className="flex justify-center mt-4">
          {serverData.pageNumList.map((pageNum) => (
            <button
              key={pageNum}
              className={`${
                pageNum === serverData.current
                  ? "bg-gray-500 text-white"
                  : "bg-white text-black"
              } border-solid border-2 border-gray-500 p-2 mx-1`}
              onClick={() => moveToList({ page: pageNum })}
            >
              {pageNum}
            </button>
          ))}
        </div>
      )}
      <Link
        to="/comboard/register"
        className="block w-1/3 py-2 bg-black text-white text-center mt-4 hover:bg-gray-700 transition duration-200 ease-in-out mx-auto cursor-pointer"
      >
        게시글 등록
      </Link>
    </div>
  );
};

export default ComListComponent;
