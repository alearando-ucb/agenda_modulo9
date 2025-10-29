import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { registerClient, uploadAvatar } from '../services/api';
import { Button, TextField, Container, Typography, Box, Alert, Link } from '@mui/material';

const RegisterPage = () => {
  const navigate = useNavigate();
  const [nombre, setNombre] = useState('');
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [avatarFile, setAvatarFile] = useState(null);
  const [fieldErrors, setFieldErrors] = useState({}); // State for field-specific errors
  const [generalError, setGeneralError] = useState(''); // State for general errors
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setFieldErrors({}); // Clear previous field errors
    setGeneralError(''); // Clear previous general error

    if (password !== confirmPassword) {
      setFieldErrors(prevErrors => ({ ...prevErrors, confirmPassword: 'Las contraseñas no coinciden.' }));
      setLoading(false);
      return;
    }

    const clientData = {
      nombre,
      email,
      username,
      password,
    };

    try {
      const response = await registerClient(clientData);
      const clientId = response.id; // Assuming registerClient returns the created client with an id

      if (avatarFile && clientId) {
        await uploadAvatar(clientId, avatarFile);
      }
      navigate('/login'); // Redirect to login on successful registration
    } catch (err) {
      if (err.response && err.response.data) {
        // Backend returns a map of errors for CustomValidationException
        if (err.response.status === 400 && (err.response.data.nombre || err.response.data.email || err.response.data.username || err.response.data.password)) {
          const errors = {};
          if (err.response.data.nombre) errors.nombre = err.response.data.nombre.join('; ');
          if (err.response.data.email) errors.email = err.response.data.email.join('; ');
          if (err.response.data.username) errors.username = err.response.data.username.join('; ');
          if (err.response.data.password) errors.password = err.response.data.password.join('; ');
          setFieldErrors(errors);
        } else if (err.response.data.error) { // Generic error message from backend
          setGeneralError(err.response.data.error);
        } else { // Fallback for other backend errors
          setGeneralError('Ocurrió un error al registrar el cliente.');
        }
      } else { // Network or unexpected error
        setGeneralError('Ocurrió un error inesperado. Por favor, inténtalo de nuevo.');
      }
      console.error('Register error:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container component="main" maxWidth="xs">
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Typography component="h1" variant="h5">
          Registrarse
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="nombre"
            label="Nombre Completo"
            name="nombre"
            autoFocus
            value={nombre}
            onChange={(e) => setNombre(e.target.value)}
            error={!!fieldErrors.nombre} // Highlight field if there's an error
            helperText={fieldErrors.nombre} // Display error message
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="email"
            label="Correo Electrónico"
            name="email"
            autoComplete="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            error={!!fieldErrors.email}
            helperText={fieldErrors.email}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="username"
            label="Nombre de Usuario"
            name="username"
            autoComplete="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            error={!!fieldErrors.username}
            helperText={fieldErrors.username}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="password"
            label="Contraseña"
            type="password"
            id="password"
            autoComplete="new-password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            error={!!fieldErrors.password}
            helperText={fieldErrors.password}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="confirmPassword"
            label="Confirmar Contraseña"
            type="password"
            id="confirmPassword"
            autoComplete="new-password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            error={!!fieldErrors.confirmPassword}
            helperText={fieldErrors.confirmPassword}
          />
          <input
            type="file"
            accept="image/*"
            style={{ marginTop: '16px' }}
            onChange={(e) => setAvatarFile(e.target.files[0])}
          />
          {generalError && ( // Display general errors if any
            <Alert severity="error" sx={{ width: '100%', mt: 2 }}>
              {generalError}
            </Alert>
          )}
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
            disabled={loading}
          >
            {loading ? 'Registrando...' : 'Registrarse'}
          </Button>
          <Link component="button" variant="body2" onClick={() => navigate('/login')}>
            ¿Ya tienes una cuenta? Inicia Sesión
          </Link>
        </Box>
      </Box>
    </Container>
  );
};

export default RegisterPage;
