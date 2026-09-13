import React from 'react';
import './Navbar.css';

const Navbar = ({ title, subtitle }) => {
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <div className="navbar-brand">
          <h1 className="navbar-title">{title}</h1>
          <p className="navbar-subtitle">{subtitle}</p>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
