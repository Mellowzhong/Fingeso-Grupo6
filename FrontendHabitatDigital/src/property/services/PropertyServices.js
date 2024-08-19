import api from "../../api";

export const postProperty = async (userEmail, property) => {
  try {
    const response = await api.post(`/inmueble/${userEmail}`, property);
    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, data: error };
  }
};
