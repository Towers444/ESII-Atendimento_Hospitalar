import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { atendimentoService } from '../services/api';

function AtendimentoList() {

  const [atendimentos, setAtendimentos] = useState([]);

  useEffect(() => {
    carregarAtendimentos();
  }, []);

  const carregarAtendimentos = async () => {
    try {
      const response = await atendimentoService.listar();
      setAtendimentos(response.data);
    } catch (error) {
      console.error('Erro ao buscar atendimentos:', error);
    }
  };

  const deletar = async (id) => {
    if (window.confirm('Deseja realmente excluir este atendimento?')) {
      try {
        await atendimentoService.deletar(id);

        setAtendimentos(
          atendimentos.filter(a => a.id !== id)
        );

      } catch (error) {
        alert('Erro ao deletar atendimento.');
      }
    }
  };

  return (
    <div>
      <h3>📋 Lista de Atendimentos</h3>

      <table
        border="1"
        cellPadding="10"
        style={{
          width: '100%',
          borderCollapse: 'collapse',
          marginTop: '15px'
        }}
      >

        <thead>
          <tr style={{ backgroundColor: '#f4f4f4' }}>
            <th>Data</th>
            <th>Horário</th>
            <th>Receita</th>
            <th>Profissional</th>
            <th>Ações</th>
          </tr>
        </thead>

        <tbody>

          {atendimentos.map(a => (

            <tr key={a.id}>
              <td>{new Date(a.data).toLocaleDateString('pt-BR')}</td>
              <td>{a.horario.substring(0, 5)}</td>
              <td>{a.receitaSaude}</td>
              <td>{a.profissional?.nome}</td>

              <td>
                <Link
                  to={`/atendimentos/editar/${a.id}`}
                  style={{ marginRight: '10px' }}
                >
                  Editar
                </Link>

                <button
                  onClick={() => deletar(a.id)}
                  style={{ color: 'red', cursor: 'pointer' }}
                >
                  Excluir
                </button>
              </td>
            </tr>

          ))}

          {atendimentos.length === 0 && (
            <tr>
              <td colSpan="5" style={{ textAlign: 'center' }}>
                Nenhum atendimento cadastrado.
              </td>
            </tr>
          )}

        </tbody>

      </table>
    </div>
  );
}

export default AtendimentoList;