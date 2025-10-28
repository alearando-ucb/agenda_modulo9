import React, { createContext, useState, useContext } from 'react';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  const login = (userData) => {
    console.log('AuthContext: userData received for login:', userData);
    setUser(userData);
    console.log('AuthContext: user state after login:', userData);
  };

  const logout = () => {
    setUser(null);
    // In a real app, you'd also clear the token from localStorage
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, isAuthenticated: !!user }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  return useContext(AuthContext);
};
