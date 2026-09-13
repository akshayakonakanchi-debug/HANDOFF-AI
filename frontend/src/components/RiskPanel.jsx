import React from 'react';
import './RiskPanel.css';

const RiskPanel = ({ handoff }) => {
  if (!handoff || !handoff.risks || handoff.risks.length === 0) {
    return (
      <div className="risk-panel">
        <h3>Risks & Problems</h3>
        <p className="no-data">No significant risks identified</p>
      </div>
    );
  }

  const getRiskColor = (severity) => {
    switch (severity) {
      case 'CRITICAL':
        return 'critical';
      case 'HIGH':
        return 'high';
      case 'MEDIUM':
        return 'medium';
      case 'LOW':
        return 'low';
      default:
        return 'low';
    }
  };

  return (
    <div className="risk-panel">
      <h3>Risks & Problems</h3>
      <div className="risks-list">
        {handoff.risks.map((risk) => (
          <div key={risk.id} className={`risk-item ${getRiskColor(risk.severity)}`}>
            <div className="risk-header">
              <h4 className="risk-title">{risk.title}</h4>
              <span className="risk-severity">{risk.severity}</span>
            </div>
            {risk.description && <p className="risk-description">{risk.description}</p>}
            {risk.evidence && <p className="risk-evidence">Evidence: {risk.evidence}</p>}
          </div>
        ))}
      </div>
    </div>
  );
};

export default RiskPanel;
