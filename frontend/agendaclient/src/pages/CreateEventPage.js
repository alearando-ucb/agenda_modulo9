import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { createEvento } from '../services/api';
import { Button, TextField, Container, Typography, Box, Alert } from '@mui/material';

const CreateEventPage = () => {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [titulo, setTitulo] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [fecha, setFecha] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    const eventoData = {
      titulo,
      descripcion,
      fecha,
      clienteId: user.id,
    };

    try {
      await createEvento(eventoData);
      navigate('/agenda'); // Redirect to agenda on success
    } catch (err) {
      if (err.response && err.response.data && err.response.data.error) {
        setError(err.response.data.error);
      } else {
        setError('Ocurrió un error inesperado al crear el evento.');
      }
      console.error('Create event error:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container component="main" maxWidth="sm">
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Typography component="h1" variant="h5">
          Crear Nuevo Evento
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="titulo"
            label="Título del Evento"
            name="titulo"
            autoFocus
            value={titulo}
            onChange={(e) => setTitulo(e.target.value)}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="descripcion"
            label="Descripción"
            id="descripcion"
            multiline
            rows={4}
            value={descripcion}
            onChange={(e) => setDescripcion(e.target.value)}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="fecha"
            label="Fecha y Hora"
            type="datetime-local"
            id="fecha"
            InputLabelProps={{
              shrink: true,
            }}
            value={fecha}
            onChange={(e) => setFecha(e.target.value)}
          />
          {error && (
            <Alert severity="error" sx={{ width: '100%', mt: 2 }}>
              {error}
            </Alert>
          )}
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
            disabled={loading}
          >
            {loading ? 'Creando...' : 'Crear Evento'}
          </Button>
          <Button
            fullWidth
            variant="outlined"
            sx={{ mb: 2 }}
            onClick={() => navigate('/agenda')}
          >
            Cancelar
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default CreateEventPage;
