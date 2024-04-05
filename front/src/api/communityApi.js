import axios from "axios";
import { API_SERVER_HOST } from "./todoApi";
import jwtAxios from "../util/jwtUtil";

// const host = `${API_SERVER_HOST}/api/comboard`;
const host = `${API_SERVER_HOST}/api/comBoard`;

export const comList = async (pageParam) => {
  const { page, size } = pageParam;

  const res = await axios.get(`${host}/list`, {
    params: { page: page, size: size },
  });

  return res.data;
};

export const regComboard = async (comboard) => {
  const header = { headers: { "Content-Type": "multipart/form-data" } };

  const res = await jwtAxios.post(`${host}/register`, comboard, header);

  return res.data;
};

export const getComBoard = async (cbno, page, size) => {
  const res = await axios.get(`${host}/${cbno}`, {
    params: {
      page: page,
      size: size,
    },
  });

  return res.data;
};

export const modComBoard = async (cbno, data) => {
  const header = { headers: { "Content-Type": "multipart/form-data" } };

  const res = await jwtAxios.put(`${host}/${cbno}`, data, header);

  return res.data;
};

export const delComBoard = async (cbno) => {
  const res = await jwtAxios.delete(`${host}/${cbno}`);

  return res.data;
};
