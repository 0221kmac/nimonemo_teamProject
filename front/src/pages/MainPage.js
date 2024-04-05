import { Link } from "react-router-dom";
import BasicLayout from "../layouts/BasicLayout";
import { useContext } from "react";
import { SearchContext } from "../util/SearchContext";

const MainPage = () => {
  const { setIsSearchFocused } = useContext(SearchContext);

  const handleButtonClick = () => {
    setIsSearchFocused(true);
    setTimeout(() => setIsSearchFocused(false), 500); // 1초 후 원상태로 복귀
    setTimeout(() => setIsSearchFocused(true), 1000); // 2초 후 다시 변경
    setTimeout(() => setIsSearchFocused(false), 1500); // 3초 후 원상태로 복귀

    // 상단으로 스크롤 이동
    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  return (
    <BasicLayout>
      <div
        className="
        flex-row p-10
        bg-main bg-cover bg-no-repeat
        w-full h-full"
      >
        <div className="flex w-full h-48"></div>
        <h2
          className="
          mt-20
          text-white text-5xl font-medium text-center"
        >
          니모내모에서
          <br />
          당신의 공간을 채워보세요
        </h2>
        <button
          className="
          flex w-48 h-16 text-xl my-10
          bg-transparent hover:bg-white text-white font-semibold hover:text-black py-4 px-8 mx-auto 
          border border-white hover:border-transparent rounded brightness-200"
        >
          <Link to={"/products/"}>지금 쇼핑하기</Link>
        </button>
        <div className="flex w-full h-48"></div>
      </div>
      <div
        className="
        flex-row p-10
        bg-sub bg-cover bg-no-repeat
        w-full h-full"
      >
        <div className="flex w-full h-48"></div>
        <h2
          className="
          mt-20
          text-white text-5xl font-medium text-center"
        >
          니모내모에서
          <br />
          당신의 취향을 찾아드립니다
        </h2>
        <button
          className="
          flex w-48 h-16 text-xl my-10
          bg-transparent hover:bg-white text-white font-semibold hover:text-black py-4 px-8 mx-auto 
          border border-white hover:border-transparent rounded brightness-200"
          onClick={handleButtonClick}
        >
          지금 검색하기
        </button>
        <div className="flex w-full h-48"></div>
      </div>
    </BasicLayout>
  );
};

export default MainPage;
