import api from "../../api";

export const getProperties = async () => {
  try {
    const response = await api.get("/inmueble");
    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, data: error };
  }
}

export const postProperty = async (userEmail, property) => {
  try {
    const response = await api.post(`/inmueble/${userEmail}`, property);
    return { success: true, data: response.data };
  } catch (error) {
    return { success: false, data: error };
  }
};
