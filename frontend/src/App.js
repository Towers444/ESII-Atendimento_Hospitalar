import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import ProfissionalList from './components/ProfissionalList';
import ProfissionalForm from './components/ProfissionalForm';
import AtendimentoList from './components/AtendimentoList';
import AtendimentoForm from './components/AtendimentoForm';

function App() {
  return (
    <Router>
      <div style={{ fontFamily: 'Arial', padding: '20px' }}>
        <nav style={{ marginBottom: '20px', paddingBottom: '10px', borderBottom: '1px solid #ccc' }}>
          <h2>🏥 Sistema de Atendimento Hospitalar</h2>
          <Link to="/" style={{ marginRight: '15px' }}>Lista de Profissionais</Link>
          <Link to="/profissionais/novo" style={{ marginRight: '15px' }}>Cadastrar Profissional</Link>

          <Link to="/atendimentos" style={{ marginRight: '15px' }}>Lista de Atendimentos</Link>
          <Link to="/atendimentos/novo" style={{ marginRight: '15px' }}> Novo Atendimento</Link>
        </nav>

        <Routes>
          <Route path="/" element={<ProfissionalList />} />
          <Route path="/profissionais/novo" element={<ProfissionalForm />} />
          <Route path="/profissionais/editar/:id" element={<ProfissionalForm />} />

          <Route path="/atendimentos" element={<AtendimentoList />} />
          <Route path="/atendimentos/novo" element={<AtendimentoForm />} />
          <Route path="/atendimentos/editar/:id" element={<AtendimentoForm />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;