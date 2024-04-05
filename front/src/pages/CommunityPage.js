import React from "react";
import { FaComment } from "react-icons/fa";
import { Outlet, useSearchParams } from "react-router-dom";
import BasicLayout from "../layouts/BasicLayout";

const CommunityPage = () => {
  const [queryParams] = useSearchParams();

  const page = queryParams.get("page") ? parseInt(queryParams.get("page")) : 1;
  const size = queryParams.get("size") ? parseInt(queryParams.get("size")) : 10;

  return (
    <>
      <BasicLayout />
      <div className="flex flex-col w-full"></div>
      {/* 커뮤니티 리스트 컴포넌트 */}
      <div className="w-full h-full">
        <Outlet />
      </div>
    </>
  );
};

export default CommunityPage;
