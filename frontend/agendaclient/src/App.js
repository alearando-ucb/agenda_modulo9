import React from 'react';
import Login from './components/Login';
import { CssBaseline, Container } from '@mui/material';

function App() {
  return (
    <>
      <CssBaseline />
      <Container 
        component="main" 
        sx={{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          minHeight: '100vh'
        }}
      >
        <Login />
      </Container>
    </>
  );
}

export default App;
