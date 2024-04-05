import { useEffect, useRef, useState } from "react";
import { getComBoard, modComBoard, delComBoard } from "../../api/communityApi";
import FetchingModal from "../common/FetchingModal";
import { API_SERVER_HOST } from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";
import ResultModal from "../common/ResultModal";

const initState = {
  comBoardBno: 0,
  comBoardTitle: "",
  comBoardContent: "",
  comBoardWriter: "",
  delFlag: false,
  uploadFileNames: [],
};

const ComModComponent = ({ comBoardBno }) => {
  const [comBoard, setComBoard] = useState(initState);
  const [result, setResult] = useState(null);
  const { moveToRead, moveToList } = useCustomMove();
  const [fetching, setFetching] = useState(false);
  const uploadRef = useRef();

  useEffect(() => {
    setFetching(true);
    getComBoard(comBoardBno).then((data) => {
      setComBoard(data);
      setFetching(false);
    });
  }, [comBoardBno]);

  const handleChangeComboard = (e) => {
    comBoard[e.target.name] = e.target.value;

    setComBoard({ ...comBoard });
  };

  const deleteOldImages = (imageName) => {
    const resultFileNames = comBoard.uploadFileNames.filter(
      (fileName) => fileName !== imageName
    );

    comBoard.uploadFileNames = resultFileNames;

    setComBoard({ ...comBoard });
  };

  const handleClickModify = () => {
    const files = uploadRef.current.files;
    const formData = new FormData();

    for (let i = 0; i < files.length; i++) {
      formData.append("files", files[i]);
    }

    formData.append("comBoardTitle", comBoard.comBoardTitle);
    formData.append("comBoardContent", comBoard.comBoardContent);
    // formData.append("comBoardWriter", comBoard.comBoardWriter);
    formData.append("delFlag", comBoard.delFlag);

    for (let i = 0; i < comBoard.uploadFileNames.length; i++) {
      formData.append("uploadFileNames", comBoard.uploadFileNames[i]);
    }

    setFetching(true);
    modComBoard(comBoardBno, formData).then(() => {
      setResult("Modified");
      setFetching(false);
      // console.log(comBoard);
    });
  };

  const handleClickDelete = () => {
    setFetching(true);
    delComBoard(comBoardBno).then(() => {
      setResult("Deleted");
      setFetching(false);
    });
  };
  const handleDeleteImage = (imageName) => {
    // 이미지 삭제 기능 추가
    setComBoard({
      ...comBoard,
      uploadFileNames: comBoard.uploadFileNames.filter(
        (name) => name !== imageName
      ),
    });
  };

  const closeModal = () => {
    if (result === "Modified") {
      moveToRead(comBoardBno);
    } else if (result === "Deleted") {
      moveToList({ page: 1 });
    }

    setResult(null);
  };

  return (
    <div className="border-2 border-gray-300 mt-10 m-2 p-4">
      {fetching && <FetchingModal />}
      {result && (
        <ResultModal
          title={`${result === "Modified" ? "수정 완료" : "삭제 완료"}`}
          content={"정상적으로 처리되었습니다."}
          callbackFn={closeModal}
        />
      )}

      {/* Form content here */}
      <div className="flex justify-center space-y-4 flex-col">
        <div className="flex justify-between items-center">
          <label
            className="block text-gray-700 text-sm font-bold mb-2"
            htmlFor="comBoardTitle"
          >
            제목
          </label>
          <input
            id="comBoardTitle"
            name="comBoardTitle"
            type="text"
            value={comBoard.comBoardTitle}
            onChange={handleChangeComboard}
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          />
        </div>
        <div className="flex justify-between items-center">
          <label
            className="block text-gray-700 text-sm font-bold mb-2"
            htmlFor="comBoardContent"
          >
            내용
          </label>
          <input
            id="comBoardContent"
            name="comBoardContent"
            type="text"
            value={comBoard.comBoardContent}
            onChange={handleChangeComboard}
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          />
        </div>
        <div className="flex justify-between items-center">
          <label
            className="block text-gray-700 text-sm font-bold mb-2"
            htmlFor="comBoardWriter"
          >
            작성자
          </label>
          <input
            id="comBoardWriter"
            name="comBoardWriter"
            type="text"
            value={comBoard.comBoardWriter}
            // onChange={handleChangeComboard}
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          />
        </div>

        {/* More inputs similar to the above */}
        <div>
          <label htmlFor="files">파일 수정:</label>
          <input type="file" id="files" name="files" ref={uploadRef} multiple />
        </div>

        <div>
          {/* 이미지 삭제 기능 추가 */}
          {comBoard.uploadFileNames.map((imageName) => (
            <div key={imageName}>
              <img
                src={`${API_SERVER_HOST}/api/file/${imageName}`}
                alt={imageName}
                className="w-32 h-32 object-cover"
              />
              <button onClick={() => handleDeleteImage(imageName)}>
                이미지 삭제
              </button>
            </div>
          ))}
        </div>

        <div className="flex justify-end space-x-2">
          <button
            onClick={handleClickModify}
            className="bg-black hover:bg-gray-800 text-white font-bold py-2 px-4 rounded"
          >
            수정완료
          </button>
          <button
            onClick={handleClickDelete}
            className="bg-black hover:bg-gray-800 text-white font-bold py-2 px-4 rounded"
          >
            삭제
          </button>
          <button
            onClick={() => moveToList({ page: 1 })}
            className="bg-black hover:bg-gray-800 text-white font-bold py-2 px-4 rounded"
          >
            목록
          </button>
        </div>
      </div>
    </div>
  );
};

export default ComModComponent;
