import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api'
});

export const profissionalService = {
  listar: () => api.get('/profissionais'),
  buscar: (id) => api.get(`/profissionais/${id}`),
  criar: (dados) => api.post('/profissionais', dados),
  atualizar: (id, dados) => api.put(`/profissionais/${id}`, dados),
  deletar: (id) => api.delete(`/profissionais/${id}`)
};

export default api;