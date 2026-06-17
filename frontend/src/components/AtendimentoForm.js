import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { atendimentoService, profissionalService } from '../services/api';

function AtendimentoForm() {

    const navigate = useNavigate();
    const { id } = useParams();

    const [profissionais, setProfissionais] = useState([]);

    const [atendimento, setAtendimento] = useState({
        data: '',
        horario: '',
        problemaTexto: '',
        receitaSaude: '',
        profissionalId: ''
    });

    useEffect(() => {
        carregarProfissionais();

        if (id) {
            carregarAtendimento();
        }
    }, [id]);

    const carregarProfissionais = async () => {
        try {
            const response = await profissionalService.listar();
            setProfissionais(response.data);
        } catch (error) {
            alert("Erro ao carregar profissionais.");
        }
    };

    const carregarAtendimento = async () => {
        try {
            const response = await atendimentoService.buscar(id);

            setAtendimento({
                data: response.data.data,
                horario: response.data.horario,
                problemaTexto: response.data.problemaTexto,
                receitaSaude: response.data.receitaSaude,
                profissionalId: response.data.profissional.id
            });

        } catch (error) {
            alert("Erro ao carregar atendimento.");
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;

        setAtendimento({
            ...atendimento,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const dados = {
            data: atendimento.data,
            horario: atendimento.horario,
            problemaTexto: atendimento.problemaTexto,
            receitaSaude: atendimento.receitaSaude,
            profissional: {
                id: Number(atendimento.profissionalId)
            }
        };

        try {

            if (id) {
                await atendimentoService.atualizar(id, dados);
                alert("Atendimento atualizado com sucesso!");
            } else {
                await atendimentoService.criar(dados);
                alert("Atendimento cadastrado com sucesso!");
            }

            navigate('/atendimentos');

        } catch (error) {

            if (error.response?.data) {
                alert(error.response.data);
            } else {
                alert("Erro ao salvar atendimento.");
            }

        }
    };

    return (
        <div style={{ maxWidth: '600px' }}>
            <h3>{id ? '✏️ Editar Atendimento' : '➕ Novo Atendimento'}</h3>

            <form
                onSubmit={handleSubmit}
                style={{
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '10px'
                }}
            >

                <label>Data:</label>
                <input
                    type="date"
                    name="data"
                    value={atendimento.data}
                    onChange={handleChange}
                    required
                />

                <label>Horário:</label>
                <input
                    type="time"
                    name="horario"
                    value={atendimento.horario}
                    onChange={handleChange}
                    required
                />

                <label>Descrição do Problema:</label>
                <textarea
                    name="problemaTexto"
                    value={atendimento.problemaTexto}
                    onChange={handleChange}
                    required
                    rows="4"
                />

                <label>Receita de Saúde:</label>
                <select
                    name="receitaSaude"
                    value={atendimento.receitaSaude}
                    onChange={handleChange}
                    required
                >
                    <option value="">Selecione uma receita</option>
                    <option value="Remédio">Remédio</option>
                    <option value="Atividade Física">Atividade Física</option>
                    <option value="Atividade Mental">Atividade Mental</option>
                </select>

                <label>Profissional:</label>
                <select
                    name="profissionalId"
                    value={atendimento.profissionalId}
                    onChange={handleChange}
                    required
                >
                    <option value="">Selecione um profissional</option>

                    {profissionais.map(profissional => (
                        <option
                            key={profissional.id}
                            value={profissional.id}
                        >
                            {profissional.nome} ({profissional.categoria})
                        </option>
                    ))}
                </select>

                <button
                    type="submit"
                    style={{
                        padding: '10px',
                        marginTop: '15px',
                        backgroundColor: '#007bff',
                        color: 'white',
                        border: 'none',
                        cursor: 'pointer'
                    }}
                >
                    Salvar
                </button>

            </form>
        </div>
    );
}

export default AtendimentoForm;