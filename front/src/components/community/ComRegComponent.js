import React, { useRef, useState } from "react";
import { regComboard } from "../../api/communityApi";
import useCustomMove from "../../hooks/useCustomMove";
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";
import { useSelector } from "react-redux";

const initState = {
  comBoardTitle: "",
  comBoardContent: "",
  imagesPreview: [], // 추가: 업로드한 이미지 미리보기 배열
};

const ComRegComponent = () => {
  const loginState = useSelector((state) => state.loginSlice);

  const [comBoard, setComBoard] = useState({ ...initState });
  const uploadRef = useRef();

  const [fetching, setFetching] = useState(false);
  const [result, setResult] = useState(null);
  const { moveToList } = useCustomMove();

  const handleChangeComboard = (e) => {
    const { name, value } = e.target;
    setComBoard({ ...comBoard, [name]: value });
  };

  // 이미지 미리보기 업데이트 함수
  const updatePreviewImages = () => {
    const previewImages = [];
    const files = uploadRef.current.files;

    for (let i = 0; i < files.length; i++) {
      previewImages.push(URL.createObjectURL(files[i]));
    }

    setComBoard({ ...comBoard, imagesPreview: previewImages });
  };

  const handleClickReg = async () => {
    const files = uploadRef.current.files;
    const formData = new FormData();

    for (let i = 0; i < files.length; i++) {
      formData.append("files", files[i]);
    }

    formData.append("comBoardTitle", comBoard.comBoardTitle);
    formData.append("comBoardContent", comBoard.comBoardContent);
    formData.append("comBoardWriter", loginState.nickname);

    setFetching(true);
    try {
      const data = await regComboard(formData);
      setResult(data.result);
    } catch (error) {
      console.error(error);
    } finally {
      setFetching(false);
    }
  };

  const closeModal = () => {
    setResult(null);
    moveToList({ page: 1 });
  };

  return (
    <div className="max-w-4xl mx-auto my-10 border rounded-lg shadow-lg overflow-hidden">
      {fetching && <FetchingModal />}
      {result && (
        <ResultModal
          title="게시글 등록 결과"
          content="성공적으로 게시글이 등록되었습니다."
          callbackFn={closeModal}
        />
      )}
      <form className="space-y-6 bg-white p-8">
        {/* 이미지 미리보기 추가 */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <label className="md:col-span-1 flex items-center justify-end text-lg font-medium text-gray-700">
            작성자
          </label>
          <input
            className="md:col-span-2 form-input rounded-md border-gray-300 shadow-sm"
            name="comBoardWriter"
            type="text"
            value={loginState.nickname}
            onChange={handleChangeComboard}
            readOnly
          />
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <label className="md:col-span-1 flex items-center justify-end text-lg font-medium text-gray-700">
            제목
          </label>
          <textarea
            className="md:col-span-2 form-textarea rounded-md border-gray-300 shadow-sm resize-none"
            name="comBoardTitle"
            type="text"
            value={comBoard.comBoardTitle}
            onChange={handleChangeComboard}
          />
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <label className="md:col-span-1 flex items-center justify-end text-lg font-medium text-gray-700">
            내용
          </label>
          <input
            className="md:col-span-2 form-input rounded-md border-gray-300 shadow-sm"
            name="comBoardContent"
            type="text"
            value={comBoard.comBoardContent}
            onChange={handleChangeComboard}
          />
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <label className="md:col-span-1 flex items-center justify-end text-lg font-medium text-gray-700">
            파일
          </label>
          <input
            ref={uploadRef}
            className="md:col-span-2 form-input rounded-md border-gray-300 shadow-sm"
            type={"file"}
            multiple={true}
          />
        </div>

        <div className="flex justify-end mt-4">
          <button
            type="button"
            className="btn bg-gray-600 hover:bg-black-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            onClick={handleClickReg}
          >
            등록하기
          </button>
        </div>
      </form>
    </div>
  );
};

export default ComRegComponent;
