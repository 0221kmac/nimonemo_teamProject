import { Suspense, lazy } from "react";
import { Navigate } from "react-router-dom";
import LoadingSpinner from "./LoadingSpninner";

const ComBoardList = lazy(() => import("../pages/community/ListPage"));
const ComBoardRegister = lazy(() => import("../pages/community/AddPage"));
const ComBoardModify = lazy(() => import("../pages/community/ModifyPage"));
const ComBoardRead = lazy(() => import("../pages/community/ReadPage"));

const communityRouter = () => {
  return [
    {
      path: "list",
      element: (
        <Suspense fallback={<LoadingSpinner />}>
          <ComBoardList />
        </Suspense>
      ),
    },
    {
      path: "",
      element: <Navigate replace to="/comboard/list" />,
    },
    {
      path: "register",
      element: (
        <Suspense fallback={<LoadingSpinner />}>
          <ComBoardRegister />
        </Suspense>
      ),
    },
    {
      path: "read/:comBoardBno",
      element: (
        <Suspense fallback={<LoadingSpinner />}>
          <ComBoardRead />
        </Suspense>
      ),
    },
    {
      path: "modify/:comBoardBno",
      element: (
        <Suspense fallback={<LoadingSpinner />}>
          <ComBoardModify />
        </Suspense>
      ),
    },
  ];
};
export default communityRouter;
