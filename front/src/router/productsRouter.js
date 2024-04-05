import { Suspense, lazy } from "react";
import { Navigate } from "react-router-dom";
import LoadingSpinner from "./LoadingSpninner"; 

const Loading = <div>Loading....</div>;
const ProductsList = lazy(() => import("../pages/products/ListPage"));

const ProductsAdd = lazy(() => import("../pages/products/AddPage"));

const ProductRead = lazy(() => import("../pages/products/ReadPage"));

const ProductModify = lazy(() => import("../pages/products/ModifyPage"));

const Search = lazy(() => import("../pages/SearchPage"));

const productsRouter = () => {
  return [
    {
      path: "list",
      element: (
        <Suspense fallback={<LoadingSpinner />}>
          <ProductsList />
        </Suspense>
      ),
    },
    {
      path: "",
      element: <Navigate replace to="/products/list" />,
    },
    {
      path: "add",
      element: (
        <Suspense fallback={<LoadingSpinner />}>
          <ProductsAdd />
        </Suspense>
      ),
    },
    {
      path: "read/:pno",
      element: (
        <Suspense fallback={<LoadingSpinner />}>
          <ProductRead />
        </Suspense>
      ),
    },
    {
      path: "modify/:pno",
      element: (
        <Suspense fallback={<LoadingSpinner />}>
          <ProductModify />
        </Suspense>
      ),
    },
    {
      path: "search",
      element: (
        <Suspense fallback={<LoadingSpinner />}>
          <Search />
        </Suspense>
      ),
    },
  ];
};

export default productsRouter;
