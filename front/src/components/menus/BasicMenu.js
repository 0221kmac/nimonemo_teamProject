import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useContext } from "react";
import { SearchContext } from "../../util/SearchContext";
import {
  FaCamera,
  // FaCaretDown,
  FaFacebook,
  FaInstagram,
  FaSearch,
  FaTwitter,
} from "react-icons/fa";
import * as mobilenet from "@tensorflow-models/mobilenet";
import "@tensorflow/tfjs";
import useCustomLogin from "../../hooks/useCustomLogin";
import useCustomCart from "../../hooks/useCustomCart";

const BasicMenu = () => {
  const { isSearchFocused } = useContext(SearchContext);

  const navigate = useNavigate();
  // const [isCategoryMenuOpen, setIsCategoryMenuOpen] = useState(false);
  const loginState = useSelector((state) => state.loginSlice);
  const [model, setModel] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const [isImageSelected, setIsImageSelected] = useState(false);
  const { doLogout, moveToPath } = useCustomLogin();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const location = useLocation();
  const { refreshCart, cartItems } = useCustomCart();
  const { isLogin } = useCustomLogin();

  const searchParams = new URLSearchParams(location.search);
  const initialSearchTerm = searchParams.get("pname") || "";
  const [searchTerm, setSearchTerm] = useState(initialSearchTerm);

  useEffect(() => {
    if (isLogin) {
      refreshCart();
    }
  }, [isLogin]);

  useEffect(() => {
    // URL의 쿼리 문자열 pname 값이 변경되었을 때 데이터를 다시 불러옴

    setSearchTerm(initialSearchTerm); // 검색어 업데이트
  }, [location.search]);

  const handleSearch = (e) => {
    e.preventDefault();
    if (searchTerm.trim() === "") {
      return;
    }
    navigate(`/products/search?pname=${searchTerm}`); // 검색 결과 페이지로 이동
  };

  useEffect(() => {
    const loadModel = async () => {
      setIsLoading(true);
      try {
        const mobilenetModel = await mobilenet.load();
        setModel(mobilenetModel);
        console.log("Model loaded.");
      } catch (error) {
        console.error("Load model error:", error);
        setError("Failed to load the model.");
      }
      setIsLoading(false);
    };
    loadModel();
  }, [searchTerm]);

  const clickLogout = () => {
    doLogout();
    moveToPath("/");
  };

  const handleImageUpload = async (event) => {
    const file = event.target.files[0];
    if (!file) {
      console.error("No file selected.");
      setError("No file selected.");
      return;
    }
    setIsImageSelected(true);
    setError("");
    setIsLoading(true);

    try {
      const img = document.createElement("img");
      img.src = URL.createObjectURL(file);
      img.onload = async () => {
        const predictions = await model.classify(img);
        console.log(predictions);

        setIsModalOpen(true); // 여기서 모달을 엽니다
        setTimeout(() => {
          setIsModalOpen(false); // 2초 후 모달을 닫습니다
          determineCategory(predictions);
        }, 2000);
      };
    } catch (error) {
      console.error("이미지 분류 중 오류 발생:", error);
      setError("이미지 분류 중 오류 발생.");
    } finally {
      setIsLoading(false);
    }
  };

  //번역을 해주는 부분
  const translateLabel = async (label, sourceLang, targetLang) => {
    const apiKey = "apiKey";
    const apiUrl = `https://translation.googleapis.com/language/translate/v2?key=${apiKey}`;

    try {
      // Split the label by comma and select the first word
      const firstWord = label.split(",")[0].trim();

      const response = await fetch(apiUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          q: firstWord,
          source: sourceLang,
          target: targetLang,
          format: "text",
        }),
      });

      if (!response.ok) {
        throw new Error("Failed to fetch translation.");
      }

      const data = await response.json();

      // Check if translations are available
      if (
        data.data &&
        data.data.translations &&
        data.data.translations.length > 0
      ) {
        return data.data.translations[0].translatedText;
      } else {
        throw new Error("No translations found.");
      }
    } catch (error) {
      console.error("Translation error:", error);
      // Handle translation error
      throw error;
    }
  };

  const determineCategory = async (predictions) => {
    const firstPrediction = predictions[0];
    const label = firstPrediction.className.toLowerCase();
    const translatedLabel = await translateLabel(label, "en", "ko");

    navigate(`/products/search?pname=${translatedLabel}`); // 검색결과로 리다이렉트
  };

  const Modal = () => (
    <div
      className={`fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50 ${
        isModalOpen ? "opacity-100" : "opacity-0"
      } transition-opacity duration-500`}
      style={{ display: isModalOpen ? "flex" : "none" }}
    >
      <div className="bg-white p-6 rounded-lg shadow-lg max-w-sm w-full mx-4">
        <h2 className="text-lg font-semibold text-gray-900 mb-4">분석중...</h2>
        <p className="text-gray-700">
          잠시 후 해당 이미지 분석 결과로 이동합니다.
        </p>
      </div>
    </div>
  );

  return (
    <nav id="menu" className="bg-gray-900 text-white">
      {isModalOpen && <Modal />}
      <div id="top" className="flex">
        {/* Social Media Icons */}
        <div id="sns" className="w-1/3 flex space-x-4 p-4 text-xl">
          <FaTwitter className="cursor-pointer" />
          <FaFacebook className="cursor-pointer" />
          <FaInstagram className="cursor-pointer" />
        </div>
        {/* Logo */}
        <div id="logo" className="w-1/3 p-6">
          <p className="text-5xl text-center">
            <Link to="/">니모내모</Link>
          </p>
        </div>
        {/* Account Links */}
        <div id="account" className="w-1/3 flex justify-end p-4">
          {!loginState.email ? (
            <ul className="flex space-x-1 font-medium text-xs">
              <li className="pl-3">
                <Link to="/member/login">로그인</Link>
              </li>
            </ul>
          ) : (
            <ul className="flex">
              <li className="text-sm">
                <Link to={"/cart"}>장바구니</Link>
              </li>
              <li className="text-xs pl-1 ml-1 rounded-full bg-red-500 w-4 h-4">
                <Link to={"/cart"}>{cartItems.length}</Link>
              </li>
              <li className="text-sm pl-5">
                <Link to={"/member/mypage"}>마이페이지</Link>
              </li>
              <li className="text-sm pl-5">
                <a onClick={clickLogout} style={{ cursor: "pointer" }}>
                  로그아웃
                </a>
              </li>
            </ul>
          )}
        </div>
      </div>

      {/* Mid section with search and image upload */}
      <div id="mid" className="flex justify-center mb-4">
        <div className="relative w-full max-w-xl">
          <form onSubmit={handleSearch}>
            <input
              className={`pl-10 pr-4 py-2 w-full ${
                isSearchFocused ? "bg-white" : "bg-gray-900"
              } border-2 border-gold-500 text-white placeholder-gray-500 rounded-full focus:outline-none focus:border-gold-600`}
              placeholder="검색..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
            <FaSearch
              onClick={handleSearch}
              className="absolute left-4 top-1/2 transform -translate-y-1/2 cursor-pointer"
            />
            <label
              htmlFor="imageUpload"
              className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gold-500 hover:text-gold-400 cursor-pointer"
            >
              <FaCamera className="absolute right-7 top-1/2 transform -translate-y-1/2 cursor-pointer" />
            </label>
          </form>
          <input
            type="file"
            accept="image/*"
            onChange={handleImageUpload}
            disabled={!model || isLoading}
            className="hidden"
            id="imageUpload"
          />
        </div>
      </div>

      {/* Display analysis result or error */}
      {/* <div className="text-center mt-4 bg-gray-900">
        {isImageSelected &&
          (isLoading ? (
            <p>분석 중...</p>
          ) : error ? (
            <p className="text-red-500">{error}</p>
          ) : (
            <p>카테고리 분석 완료!</p> // This message can be customized
          ))}
      </div> */}

      {/* Bottom section with navigation links */}
      <div id="bot" className="flex-row">
        <div className="flex p-4 justify-center space-x-10 font-medium text-base">
          <div className="pr-4">
            <Link to="/products/">상품 전체보기</Link>
          </div>
          <div className="pr-4">
            <Link to="/comboard">커뮤니티</Link>
          </div>
          <div className="pr-4">
            <Link to="/month">이달의 아티스트</Link>
          </div>
          <div className="pr-4">
            <Link to="/about">고객센터</Link>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default BasicMenu;
