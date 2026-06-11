import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_URL
});

export const profissionalService = {
  listar: () => api.get('/profissionais'),
  buscar: (id) => api.get(`/profissionais/${id}`),
  criar: (dados) => api.post('/profissionais', dados),
  atualizar: (id, dados) => api.put(`/profissionais/${id}`, dados),
  deletar: (id) => api.delete(`/profissionais/${id}`)
};

export default api;