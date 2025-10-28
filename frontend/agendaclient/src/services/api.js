import axios from 'axios';

const apiClient = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

export const login = async (username, password) => {
  const response = await apiClient.post('/clientes/login', { username, password });
  return response.data;
};

export const getEventos = (clienteId) => {
  return apiClient.get(`/eventos/${clienteId}`);
};

export const createEvento = (eventoData) => {
  return apiClient.post('/eventos/create', eventoData);
};

export const registerClient = async (clientData) => {
  const response = await apiClient.post('/clientes/create', clientData);
  return response.data;
};

export const uploadAvatar = (clientId, file) => {
  const formData = new FormData();
  formData.append('file', file);

  return apiClient.post(`/clientes/uploadAvatar/${clientId}`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
};

export default apiClient;
