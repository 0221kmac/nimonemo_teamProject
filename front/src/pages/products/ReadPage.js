import { useParams } from "react-router-dom";
import ReadComponent_c from "../../components/c_products/ReadComponent_c";

const ReadPage = () => {
  const { pno } = useParams();

  return (
    <div className="p-4 w-full bg-white">
      {/* <div className="text-3xl font-extrabold">상품 정보</div> */}

      <ReadComponent_c pno={pno}></ReadComponent_c>
    </div>
  );
};

export default ReadPage;
