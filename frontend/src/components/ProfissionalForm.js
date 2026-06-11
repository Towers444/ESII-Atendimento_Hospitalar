import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { profissionalService } from '../services/api';

function ProfissionalForm() {
  const navigate = useNavigate();
  const { id } = useParams(); // Pega o ID da URL se estiver editando

  const [profissional, setProfissional] = useState({
    nome: '',
    telefone: '',
    endereco: '',
    categoria: ''
  });

  useEffect(() => {
    if (id) {
      profissionalService.buscar(id).then(response => {
        setProfissional(response.data);
      }).catch(error => alert("Erro ao carregar dados do profissional."));
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProfissional({ ...profissional, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault(); // Evita que a página recarregue ao enviar o form
    try {
      if (id) {
        await profissionalService.atualizar(id, profissional);
        alert("Profissional atualizado com sucesso!");
      } else {
        await profissionalService.criar(profissional);
        alert("Profissional cadastrado com sucesso!");
      }
      navigate('/'); // Redireciona de volta para a lista
    } catch (error) {
      alert("Erro ao salvar. Verifique se os dados estão corretos.");
    }
  };

  return (
    <div style={{ maxWidth: '500px' }}>
      <h3>{id ? '✏️ Editar Profissional' : '➕ Novo Profissional'}</h3>
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
        
        <label>Nome:</label>
        <input type="text" name="nome" value={profissional.nome} onChange={handleChange} required />

        <label>Telefone:</label>
        <input type="text" name="telefone" value={profissional.telefone} onChange={handleChange} />

        <label>Endereço:</label>
        <input type="text" name="endereco" value={profissional.endereco} onChange={handleChange} />

        <label>Categoria:</label>
        <select name="categoria" value={profissional.categoria} onChange={handleChange} required>
          <option value="" disabled>Selecione uma categoria</option>
          <option value="Médico">Médico</option>
          <option value="Psicólogo">Psicólogo</option>
          <option value="Fisioterapeuta">Fisioterapeuta</option>
        </select>

        <button type="submit" style={{ padding: '10px', marginTop: '15px', backgroundColor: '#007bff', color: 'white', border: 'none', cursor: 'pointer' }}>
          Salvar
        </button>
      </form>
    </div>
  );
}

export default ProfissionalForm;