import React, { useState, useEffect } from 'react';
import Navbar from './components/Navbar';
import StatusSummary from './components/StatusSummary';
import WorkState from './components/WorkState';
import WorkMap from './components/WorkMap';
import RiskPanel from './components/RiskPanel';
import NextActionsPanel from './components/NextActionsPanel';
import QAPanel from './components/QAPanel';
import { api } from './services/api';
import { DEMO_PROJECT } from './data/demoData';
import './App.css';

const App = () => {
  const [currentProject, setCurrentProject] = useState(null);
  const [handoff, setHandoff] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleLoadDemoProject = async () => {
    setLoading(true);
    setError(null);
    try {
      const project = await api.createProject(DEMO_PROJECT);
      if (project) {
        setCurrentProject(project);
        setHandoff(null);
      } else {
        setError('Failed to load demo project');
      }
    } catch (err) {
      setError('Error loading demo project: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateHandoff = async () => {
    if (!currentProject) {
      setError('Please load or select a project first');
      return;
    }

    setLoading(true);
    setError(null);
    try {
      const result = await api.createHandoff(currentProject.id);
      if (result) {
        setHandoff(result);
      } else {
        setError('Failed to create handoff');
      }
    } catch (err) {
      setError('Error creating handoff: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app">
      <Navbar title="🚀 HANDOFF AI" subtitle="Never start from zero again." />

      <main className="app-container">
        <section className="controls-section">
          <div className="button-group">
            <button
              className="btn btn-primary"
              onClick={handleLoadDemoProject}
              disabled={loading}
            >
              {loading && currentProject === null ? 'Loading...' : 'LOAD DEMO PROJECT'}
            </button>
            <button
              className="btn btn-success"
              onClick={handleCreateHandoff}
              disabled={loading || !currentProject}
            >
              {loading && currentProject && currentProject.id ? 'Creating...' : 'CREATE HANDOFF'}
            </button>
          </div>

          {currentProject && (
            <div className="project-info">
              <h2>{currentProject.name}</h2>
              {currentProject.description && <p>{currentProject.description}</p>}
            </div>
          )}

          {error && (
            <div className="error-message">
              <strong>Error:</strong> {error}
            </div>
          )}
        </section>

        {handoff && (
          <>
            <StatusSummary handoff={handoff} />

            <section className="main-content">
              <WorkState handoff={handoff} />
              <WorkMap handoff={handoff} />
              <RiskPanel handoff={handoff} />
              <NextActionsPanel handoff={handoff} />
              <QAPanel projectId={currentProject.id} api={api} />
            </section>
          </>
        )}

        {!handoff && currentProject && !loading && (
          <div className="empty-state">
            <p>Click "CREATE HANDOFF" to analyze the project and generate insights.</p>
          </div>
        )}

        {!currentProject && !loading && (
          <div className="empty-state">
            <p>Click "LOAD DEMO PROJECT" to get started with a sample project.</p>
          </div>
        )}
      </main>

      <footer className="app-footer">
        <p>HANDOFF AI - Making knowledge transfer seamless</p>
      </footer>
    </div>
  );
};

export default App;
