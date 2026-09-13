import React from 'react';
import './StatusSummary.css';

const StatusSummary = ({ handoff }) => {
  if (!handoff) {
    return (
      <div className="status-summary">
        <p>No project data available</p>
      </div>
    );
  }

  const completed = handoff.completed?.length || 0;
  const inProgress = handoff.inProgress?.length || 0;
  const notStarted = handoff.notStarted?.length || 0;
  const blocked = handoff.blocked?.length || 0;

  return (
    <div className="status-summary">
      <div className="status-card completed">
        <div className="status-number">{completed}</div>
        <div className="status-label">Completed</div>
      </div>
      <div className="status-card in-progress">
        <div className="status-number">{inProgress}</div>
        <div className="status-label">In Progress</div>
      </div>
      <div className="status-card not-started">
        <div className="status-number">{notStarted}</div>
        <div className="status-label">Not Started</div>
      </div>
      <div className="status-card blocked">
        <div className="status-number">{blocked}</div>
        <div className="status-label">Blocked</div>
      </div>
    </div>
  );
};

export default StatusSummary;
