import api from "../../api";
import axios from "axios";
import { ACCESS_TOKEN } from "../../utilities/constants/constants";
import { useCookies } from "vue3-cookies";

const { cookies } = useCookies();

export const register = async (user) => {
  try {
    const response = await axios.post(
      "http://localhost:8080/auth/register",
      user
    );
    const { token } = response.data;
    cookies.set(ACCESS_TOKEN, token);

    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, data: error };
  }
};

export const registerCorredor = async (userEmail) => {
  console.log(userEmail);
  try {
    const response = await axios.post(
      "http://localhost:8080/corredor/" + userEmail
    );
    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, data: error };
  }
};

export const login = async (user) => {
  try {
    const response = await api.post("http://localhost:8080/auth/login", user);
    cookies.remove(ACCESS_TOKEN);
    const { token } = response.data;
    cookies.set(ACCESS_TOKEN, token);

    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, data: error };
  }
};

export const getUsuarioByEmail = async (email) => {
  try {
    const response = await api.get(`/user/get/${email}`);
    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, data: error };
  }
};

export const updateUsuario = async (user) => {
  try {
    console.log(user.profile);
    const response = await api.put("/profile/", user.profile);
    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, data: error };
  }
};
