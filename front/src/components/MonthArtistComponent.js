import React from "react";

import artistImage from "../images/artist.jpg";
import art1Image from "../images/art1.jpg";
import art2Image from "../images/art2.jpg";

const MonthArtistComponent = () => {
  return (
    <div className="max-w-4xl mx-auto my-8 p-5 bg-gray-100 text-gray-800">
      <h1 className="text-4xl text-center my-8">아리엘 리우</h1>

      <img src={artistImage} alt="아리엘 리우" className="mx-auto" />
      <p className="text-justify my-4">
        현대적이고 창의적인 분위기를 풍기는 아리엘 리우의 스튜디오에서 디지털
        아트 설치작품과 전통 캔버스 사이에 서 있는 아티스트의 모습을 확인할 수
        있습니다.아리엘 리우는 1985년 서울에서 태어나, 어린 시절부터 뛰어난
        예술적 재능을 보였습니다. 미국으로 이주한 후 뉴욕의 프랫
        인스티튜트(Pratt Institute)에서 순수미술을 전공하며 다양한 미디어와
        재료를 실험하기 시작했습니다. 졸업 후 그는 디지털 아트와 전통적인 회화
        기법을 결합한 독특한 스타일로 주목 받기 시작했습니다.
      </p>

      <h2 className="text-3xl my-8">대표작품</h2>
      <div className="my-8 p-4 bg-white shadow-md">
        <h3 className="text-2xl my-4">디지털 자연주의 (Digital Naturism)</h3>
        <img src={art1Image} alt="디지털 자연주의" className="mx-auto" />
        <p className="text-justify mt-2">
          이 작품은 디지털 기술로 재구성된 화려한 자연을 보여주며, 가상의 식물과
          동물들이 관람객들을 매혹시킵니다.
        </p>
      </div>

      <div className="my-4 p-4 bg-white shadow-md">
        <h3 className="text-2xl my-4">인간 조건 (The Human Condition)</h3>
        <img src={art2Image} alt="인간 조건" className="mx-auto" />
        <p className="text-justify mt-2">
          전통적인 캔버스에 현대 디지털 기술을 접목시킨 이 작품은 인간 내면의
          복잡함과 디지털 시대 속 외로움을 표현합니다.
        </p>
      </div>
    </div>
  );
};

export default MonthArtistComponent;
