import React from 'react';
import './TaskCard.css';

const TaskCard = ({ task, status }) => {
  const getStatusIcon = (status) => {
    switch (status) {
      case 'COMPLETED':
        return '✓';
      case 'IN_PROGRESS':
        return '⟳';
      case 'BLOCKED':
        return '✕';
      case 'NOT_STARTED':
        return '○';
      default:
        return '?';
    }
  };

  return (
    <div className={`task-card ${status.toLowerCase().replace('_', '-')}`}>
      <div className="task-icon">{getStatusIcon(status)}</div>
      <div className="task-content">
        <h3 className="task-title">{task.title}</h3>
        {task.description && <p className="task-description">{task.description}</p>}
      </div>
    </div>
  );
};

export default TaskCard;
