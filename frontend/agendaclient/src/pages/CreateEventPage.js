import React, { useState, useCallback, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { createEvento } from '../services/api';
import { Button, TextField, Container, Typography, Box, Alert, CircularProgress } from '@mui/material';
import { MapContainer, TileLayer, Marker, useMapEvents } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';

// Fix for default marker icon issue with Webpack
delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png'),
});

const CreateEventPage = () => {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [titulo, setTitulo] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [fecha, setFecha] = useState('');
  const [latitude, setLatitude] = useState(-19.0333); // Default to Sucre, Bolivia
  const [longitude, setLongitude] = useState(-65.2627); // Default to Sucre, Bolivia
  const [fieldErrors, setFieldErrors] = useState({}); // State for field-specific errors
  const [generalError, setGeneralError] = useState(''); // State for general errors
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setFieldErrors({}); // Clear previous field errors
    setGeneralError(''); // Clear previous general error

    const eventoData = {
      titulo,
      descripcion,
      fecha,
      clienteId: user.id,
      latitude,
      longitude,
    };

    try {
      await createEvento(eventoData);
      navigate('/agenda'); // Redirect to agenda on success
    } catch (err) {
      if (err.response && err.response.data) {
        // Backend returns a map of errors for CustomValidationException
        if (err.response.status === 400 && (err.response.data.titulo || err.response.data.descripcion || err.response.data.fecha)) {
          const errors = {};
          if (err.response.data.titulo) errors.titulo = err.response.data.titulo.join('; ');
          if (err.response.data.descripcion) errors.descripcion = err.response.data.descripcion.join('; ');
          if (err.response.data.fecha) errors.fecha = err.response.data.fecha.join('; ');
          setFieldErrors(errors);
        } else if (err.response.data.error) { // Generic error message from backend
          setGeneralError(err.response.data.error);
        } else { // Fallback for other backend errors
          setGeneralError('Ocurrió un error al crear el evento.');
        }
      } else { // Network or unexpected error
        setGeneralError('Ocurrió un error inesperado. Por favor, inténtalo de nuevo.');
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
            error={!!fieldErrors.titulo}
            helperText={fieldErrors.titulo}
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
            error={!!fieldErrors.descripcion}
            helperText={fieldErrors.descripcion}
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
            error={!!fieldErrors.fecha}
            helperText={fieldErrors.fecha}
          />

          <Typography variant="subtitle1" sx={{ mt: 2, mb: 1 }}>
            Ubicación del Evento (Arrastra el marcador o haz click en el mapa)
          </Typography>
          <MapContainer
            center={[-19.0333, -65.2627]} // Default center (Sucre, Bolivia)
            zoom={13}
            style={{ height: '400px', width: '100%', marginBottom: '16px' }}
          >
            <TileLayer
              attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            {latitude !== null && longitude !== null && (
              <Marker
                position={[latitude, longitude]}
                draggable={true}
                eventHandlers={{
                  dragend: (e) => {
                    const marker = e.target;
                    const position = marker.getLatLng();
                    setLatitude(position.lat);
                    setLongitude(position.lng);
                  },
                }}
              />
            )}
            <MapClickHandler setLatitude={setLatitude} setLongitude={setLongitude} />
          </MapContainer>

          {generalError && (
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

const MapClickHandler = ({ setLatitude, setLongitude }) => {
  useMapEvents({
    click: (e) => {
      setLatitude(e.latlng.lat);
      setLongitude(e.latlng.lng);
    },
  });
  return null;
};
