import React from 'react';
import TaskCard from './TaskCard';
import './WorkState.css';

const WorkState = ({ handoff }) => {
  if (!handoff) {
    return (
      <div className="work-state">
        <p>No work state data available</p>
      </div>
    );
  }

  return (
    <div className="work-state">
      <div className="work-state-column">
        <h3>✓ Completed ({handoff.completed?.length || 0})</h3>
        <div className="tasks-container">
          {handoff.completed && handoff.completed.length > 0 ? (
            handoff.completed.map((task) => (
              <TaskCard key={task.id} task={task} status="COMPLETED" />
            ))
          ) : (
            <p className="no-tasks">No completed tasks</p>
          )}
        </div>
      </div>

      <div className="work-state-column">
        <h3>⟳ In Progress ({handoff.inProgress?.length || 0})</h3>
        <div className="tasks-container">
          {handoff.inProgress && handoff.inProgress.length > 0 ? (
            handoff.inProgress.map((task) => (
              <TaskCard key={task.id} task={task} status="IN_PROGRESS" />
            ))
          ) : (
            <p className="no-tasks">No in-progress tasks</p>
          )}
        </div>
      </div>

      <div className="work-state-column">
        <h3>○ Not Started ({handoff.notStarted?.length || 0})</h3>
        <div className="tasks-container">
          {handoff.notStarted && handoff.notStarted.length > 0 ? (
            handoff.notStarted.map((task) => (
              <TaskCard key={task.id} task={task} status="NOT_STARTED" />
            ))
          ) : (
            <p className="no-tasks">No pending tasks</p>
          )}
        </div>
      </div>

      <div className="work-state-column">
        <h3>✕ Blocked ({handoff.blocked?.length || 0})</h3>
        <div className="tasks-container">
          {handoff.blocked && handoff.blocked.length > 0 ? (
            handoff.blocked.map((task) => (
              <TaskCard key={task.id} task={task} status="BLOCKED" />
            ))
          ) : (
            <p className="no-tasks">No blocked tasks</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default WorkState;
