import React from 'react';
import './WorkMap.css';

const WorkMap = ({ handoff }) => {
  if (!handoff || !handoff.dependencies || handoff.dependencies.length === 0) {
    return (
      <div className="work-map">
        <h3>Work Map</h3>
        <p>No dependencies configured yet</p>
      </div>
    );
  }

  return (
    <div className="work-map">
      <h3>Work Map - Task Dependencies</h3>
      <div className="dependency-graph">
        {handoff.dependencies.map((dep, index) => (
          <div key={index} className="dependency-flow">
            <div className="task-node">{dep.from}</div>
            <div className="arrow">→</div>
            <div className="task-node">{dep.to}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default WorkMap;
