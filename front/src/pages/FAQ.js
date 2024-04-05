import React from "react";
import BasicLayout from "../layouts/BasicLayout";
import { CiHeadphones } from "react-icons/ci";

const FAQ = () => {
  const faqs = [
    {
      question: "서비스 이용 방법이 궁금해요.",
      answer: "서비스는 웹사이트 및 모바일 앱을 통해 이용 가능합니다.",
    },
    {
      question: "결제 방법을 알려주세요.",
      answer: "신용카드, 계좌이체 등 다양한 결제 방법을 지원합니다.",
    },
    // 추가적인 질문과 답변을 여기에 추가할 수 있습니다.
  ];

  return (
    <BasicLayout>
      <div className="h-11 bg-gray-700 text-white flex items-center pl-8 sticky top-0 z-55">
        <CiHeadphones className="w-6 h-6 mr-2" /> 고객센터
      </div>
      <div className="flex flex-col items-center justify-center">
        <div className="w-full max-w-4xl px-4">
          <div className="mb-14"/>
          <dl className="space-y-6">
            {faqs.map((faq, index) => (
              <div key={index} className="p-4 bg-white shadow-lg rounded-lg">
                <dt className="text-lg font-semibold text-gray-800">
                  {faq.question}
                </dt>
                <dd className="mt-2 ml-4 text-gray-600">{faq.answer}</dd>
              </div>
            ))}
          </dl>
        </div>
      </div>
    </BasicLayout>
  );
};

export default FAQ;
