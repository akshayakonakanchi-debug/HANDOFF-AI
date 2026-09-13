import React, { useState } from 'react';
import './QAPanel.css';

const QAPanel = ({ projectId, api }) => {
  const [question, setQuestion] = useState('');
  const [answer, setAnswer] = useState('');
  const [loading, setLoading] = useState(false);

  const handleAsk = async (e) => {
    e.preventDefault();
    if (!question.trim()) return;

    setLoading(true);
    try {
      const response = await api.askQuestion(projectId, question);
      if (response && response.answer) {
        setAnswer(response.answer);
      }
    } catch (error) {
      console.error('Error asking question:', error);
      setAnswer('Sorry, I could not process your question. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const suggestedQuestions = [
    'What should I do first?',
    'What is blocked?',
    'What has been completed?',
    'What are the risks?',
    'What should the new person know?'
  ];

  const handleSuggestedQuestion = (q) => {
    setQuestion(q);
  };

  return (
    <div className="qa-panel">
      <h3>Ask HANDOFF</h3>
      <div className="qa-container">
        <form onSubmit={handleAsk} className="qa-form">
          <div className="input-group">
            <input
              type="text"
              value={question}
              onChange={(e) => setQuestion(e.target.value)}
              placeholder="Ask a question about your project..."
              className="qa-input"
              disabled={loading}
            />
            <button type="submit" className="qa-button" disabled={loading}>
              {loading ? 'Thinking...' : 'Ask'}
            </button>
          </div>
        </form>

        <div className="suggested-questions">
          <p className="suggested-label">Suggested questions:</p>
          <div className="suggestions">
            {suggestedQuestions.map((q, index) => (
              <button
                key={index}
                className="suggestion-button"
                onClick={() => handleSuggestedQuestion(q)}
              >
                {q}
              </button>
            ))}
          </div>
        </div>

        {answer && (
          <div className="qa-answer">
            <h4>Answer:</h4>
            <p>{answer}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default QAPanel;
