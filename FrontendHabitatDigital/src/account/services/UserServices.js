import { axiosClient } from "../../utilities/constants/constants";

export const postUser = async (user) => {
  try {
    const response = await axiosClient.post("/user", user);
    console.log(response);
    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, data: error.response.data };
  }
};
