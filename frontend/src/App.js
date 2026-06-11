import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import ProfissionalList from './components/ProfissionalList';
import ProfissionalForm from './components/ProfissionalForm';

function App() {
  return (
    <Router>
      <div style={{ fontFamily: 'Arial', padding: '20px' }}>
        <nav style={{ marginBottom: '20px', paddingBottom: '10px', borderBottom: '1px solid #ccc' }}>
          <h2>🏥 Sistema de Atendimento Hospitalar</h2>
          <Link to="/" style={{ marginRight: '15px' }}>Lista de Profissionais</Link>
          <Link to="/profissionais/novo">Cadastrar Profissional</Link>
        </nav>

        <Routes>
          <Route path="/" element={<ProfissionalList />} />
          <Route path="/profissionais/novo" element={<ProfissionalForm />} />
          <Route path="/profissionais/editar/:id" element={<ProfissionalForm />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;