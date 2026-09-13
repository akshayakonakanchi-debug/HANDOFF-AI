export const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

const handleResponse = async (response) => {
  if (!response.ok) {
    throw new Error(`API Error: ${response.status}`);
  }
  return response.json();
};

export const api = {
  // Projects
  getAllProjects: async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/projects`);
      return handleResponse(response);
    } catch (error) {
      console.error('Error fetching projects:', error);
      return [];
    }
  },

  getProjectById: async (id) => {
    try {
      const response = await fetch(`${API_BASE_URL}/projects/${id}`);
      return handleResponse(response);
    } catch (error) {
      console.error('Error fetching project:', error);
      return null;
    }
  },

  createProject: async (project) => {
    try {
      const response = await fetch(`${API_BASE_URL}/projects`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(project),
      });
      return handleResponse(response);
    } catch (error) {
      console.error('Error creating project:', error);
      return null;
    }
  },

  updateProject: async (id, project) => {
    try {
      const response = await fetch(`${API_BASE_URL}/projects/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(project),
      });
      return handleResponse(response);
    } catch (error) {
      console.error('Error updating project:', error);
      return null;
    }
  },

  // Handoff
  createHandoff: async (projectId) => {
    try {
      const response = await fetch(`${API_BASE_URL}/projects/${projectId}/handoff`, {
        method: 'POST',
      });
      return handleResponse(response);
    } catch (error) {
      console.error('Error creating handoff:', error);
      return null;
    }
  },

  // Questions
  askQuestion: async (projectId, question) => {
    try {
      const response = await fetch(`${API_BASE_URL}/projects/${projectId}/questions`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ question }),
      });
      return handleResponse(response);
    } catch (error) {
      console.error('Error asking question:', error);
      return null;
    }
  },
};
