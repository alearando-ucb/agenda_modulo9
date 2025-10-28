import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import LoginPage from './pages/LoginPage';
import AgendaPage from './pages/AgendaPage';
import CreateEventPage from './pages/CreateEventPage';
import RegisterPage from './pages/RegisterPage'; // New import
import { CssBaseline } from '@mui/material';

// A wrapper for protected routes
const ProtectedRoute = ({ children }) => {
  const { isAuthenticated, user } = useAuth(); // Also get user for more context
  console.log('ProtectedRoute: isAuthenticated =', isAuthenticated, 'User =', user);
  if (!isAuthenticated) {
    // user is not authenticated
    return <Navigate to="/login" />;
  }
  return children;
};

// The main App component with routing
const AppRoutes = () => {
  return (
    <Router>
      <CssBaseline />
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} /> {/* New Route */}
        <Route 
          path="/agenda" 
          element={
            <ProtectedRoute>
              <AgendaPage />
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/agenda/nuevo" 
          element={
            <ProtectedRoute>
              <CreateEventPage />
            </ProtectedRoute>
          } 
        />
        <Route path="*" element={<Navigate to="/login" />} />
      </Routes>
    </Router>
  );
}

// The main export, wrapping the app in the AuthProvider
function App() {
  return (
    <AuthProvider>
      <AppRoutes />
    </AuthProvider>
  );
}

export default App;
