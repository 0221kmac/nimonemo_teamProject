import { RouterProvider } from "react-router-dom";
import { SearchContext } from "./util/SearchContext";
import "./App.css";
import root from "./router/root";
import { useState } from "react";

function App() {
  const [isSearchFocused, setIsSearchFocused] = useState(false);
  return (
    <SearchContext.Provider value={{ isSearchFocused, setIsSearchFocused }}>
      <RouterProvider router={root} />
    </SearchContext.Provider>
  );
}

export default App;
