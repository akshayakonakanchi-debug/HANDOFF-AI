import React from 'react';
import './NextActionsPanel.css';

const NextActionsPanel = ({ handoff }) => {
  if (!handoff || !handoff.nextActions || handoff.nextActions.length === 0) {
    return (
      <div className="next-actions-panel">
        <h3>Recommended Next Actions</h3>
        <p className="no-data">No recommended actions at this time</p>
      </div>
    );
  }

  return (
    <div className="next-actions-panel">
      <h3>Recommended Next Actions</h3>
      <div className="actions-list">
        {handoff.nextActions.map((action) => (
          <div key={action.id} className="action-item">
            <div className="action-priority">{action.priority}</div>
            <div className="action-content">
              <h4 className="action-title">{action.title}</h4>
              {action.description && <p className="action-description">{action.description}</p>}
              {action.reason && <p className="action-reason"><strong>Why:</strong> {action.reason}</p>}
              {action.relatedTask && <p className="action-related"><strong>Related:</strong> {action.relatedTask}</p>}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default NextActionsPanel;
