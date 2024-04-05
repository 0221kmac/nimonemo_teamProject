import ListComponent_c from "../../components/c_products/ListComponent_c";
import { FaGift } from "react-icons/fa";

const ListPage = () => {
  return (
    <div className="flex flex-col w-full">
      <div className="h-11 bg-gray-700 text-white flex items-center pl-8 sticky top-0 z-50">
        <FaGift className="w-6 h-6 mr-2" />
        상품 목록
      </div>
      <div className="w-full h-full">
        <ListComponent_c />
      </div>
    </div>
  );
};

export default ListPage;
