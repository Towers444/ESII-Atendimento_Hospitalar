import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { profissionalService } from '../services/api';

function ProfissionalList() {
  const [profissionais, setProfissionais] = useState([]);

  // useEffect executa essa função assim que o componente aparece na tela
  useEffect(() => {
    carregarProfissionais();
  }, []);

  const carregarProfissionais = async () => {
    try {
      const response = await profissionalService.listar();
      setProfissionais(response.data);
    } catch (error) {
      console.error("Erro ao buscar profissionais:", error);
    }
  };

  const deletar = async (id) => {
    if (window.confirm("Deseja realmente excluir este profissional?")) {
      try {
        await profissionalService.deletar(id);
        setProfissionais(profissionais.filter(p => p.id !== id));
      } catch (error) {
        alert("Erro ao deletar profissional.");
      }
    }
  };

  return (
    <div>
      <h3>🩺 Lista de Profissionais de Saúde</h3>
      <table border="1" cellPadding="10" style={{ width: '100%', borderCollapse: 'collapse', marginTop: '15px' }}>
        <thead>
          <tr style={{ backgroundColor: '#f4f4f4' }}>
            <th>Nome</th>
            <th>Categoria</th>
            <th>Telefone</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {profissionais.map(p => (
            <tr key={p.id}>
              <td>{p.nome}</td>
              <td>{p.categoria}</td>
              <td>{p.telefone}</td>
              <td>
                <Link to={`/profissionais/editar/${p.id}`} style={{ marginRight: '10px' }}>Editar</Link>
                <button onClick={() => deletar(p.id)} style={{ color: 'red', cursor: 'pointer' }}>Excluir</button>
              </td>
            </tr>
          ))}
          {profissionais.length === 0 && (
            <tr>
              <td colSpan="4" style={{ textAlign: 'center' }}>Nenhum profissional cadastrado.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default ProfissionalList;