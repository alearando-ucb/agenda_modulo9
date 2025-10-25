import axios from 'axios';

const apiClient = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

export const login = (username, password) => {
  return apiClient.post('/clientes/login', { username, password });
};

export const getEventos = (clienteId) => {
  return apiClient.get(`/eventos/${clienteId}`);
};

export default apiClient;
