import api from "../../api";
import axios from "axios";

export const postUser = async (user) => {
  try {
    const response = await axios.post(
      "http://localhost:8080/auth/register",
      user
    );
    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, data: error };
  }
};

export const getUser = async (user) => {
  try {
    const response = await axios.get("http://localhost:8080/auth/login", user);
    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, data: error };
  }
};
