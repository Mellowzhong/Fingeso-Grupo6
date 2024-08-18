import axios from "axios";
import { ACCESS_TOKEN } from "./utilities/constants/constants";
import { useCookies } from "vue3-cookies";

const { cookies } = useCookies();

const api = axios.create({
  baseURL: "http://localhost:8080",
});

api.interceptors.request.use(
  (config) => {
    const token = cookies.get(ACCESS_TOKEN);
    console.log("roken:", token);
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    console.log(config.headers);
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
