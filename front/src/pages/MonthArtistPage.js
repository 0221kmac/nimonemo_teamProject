import React from "react";
import MonthArtistComponent from "../components/MonthArtistComponent";
import BasicLayout from "../layouts/BasicLayout";
import { FaRankingStar } from "react-icons/fa6";

const MonthArtistPage = () => {
  return (
    <BasicLayout>
      <div className="h-11 bg-gray-700 text-white flex items-center pl-8 sticky top-0 z-55">
        <FaRankingStar className="w-6 h-6 mr-2" /> 이달의 아티스트
      </div>
      <MonthArtistComponent />
    </BasicLayout>
  );
};

export default MonthArtistPage;
