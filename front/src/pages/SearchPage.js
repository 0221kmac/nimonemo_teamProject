import { useLocation } from "react-router-dom";
import SearchComponent from "../components/SearchComponent";
import { LuSearchCheck } from "react-icons/lu";

const SearchPage = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  return (
    <div className="flex flex-col w-full">
      <div className="h-11 bg-gray-700 text-white flex items-center pl-8 sticky top-0 z-50">
        <LuSearchCheck className="w-6 h-6 mr-2" />"
        {searchParams.get("pname") || ""}"의 검색 결과
      </div>
      <div className="w-full h-full">
        <SearchComponent />
      </div>
    </div>
  );
};

export default SearchPage;
