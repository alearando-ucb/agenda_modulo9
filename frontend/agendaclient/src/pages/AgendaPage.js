import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { getEventos } from '../services/api';
import { Button, Container, Typography, Box, List, ListItem, ListItemText, Card, CardContent, CircularProgress, Alert, Link } from '@mui/material';

const AgendaPage = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [eventos, setEventos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    if (user && user.id) {
      getEventos(user.id)
        .then(response => {
          setEventos(response.data);
          setLoading(false);
        })
        .catch(err => {
          console.error('Error fetching events:', err);
          setError('No se pudieron cargar los eventos.');
          setLoading(false);
        });
    }
  }, [user]);

  const handleLogout = () => {
    logout();
    // The router will automatically redirect to login
  };

  const handleAddEvent = () => {
    navigate('/agenda/nuevo');
  };

  return (
    <Container maxWidth="md">
      <Box sx={{ my: 4, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <Typography variant="h4" component="h1" sx={{ display: 'flex', alignItems: 'center' }}>
          {user && user.avatarUrl && (
            <img src={`http://localhost:8081${user.avatarUrl}`} alt="Avatar" style={{ width: 50, height: 50, borderRadius: '50%', marginRight: 10 }} />
          )}
          Agenda de {user ? user.nombre : ''}
        </Typography>
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-end' }}>
            <Button variant="outlined" color="secondary" onClick={handleLogout}>
              Cerrar Sesión
            </Button>
          </Box>
      </Box>

      {loading ? (
        <CircularProgress />
      ) : error ? (
        <Alert severity="error">{error}</Alert>
      ) : (
        <Card>
          <CardContent>
            <List>
              {eventos.length > 0 ? (
                eventos.map(evento => (
                  <ListItem key={evento.id} divider>
                    <ListItemText
                      primary={evento.titulo}
                      secondary={
                        <>
                          <Typography
                            sx={{ display: 'inline' }}
                            component="span"
                            variant="body2"
                            color="text.primary"
                          >
                            {new Date(evento.fecha).toLocaleString()}
                          </Typography>
                          {` — ${evento.descripcion}`}
                          {evento.latitude && evento.longitude && (
                            <Typography
                              sx={{ display: 'block' }}
                              component="span"
                              variant="body2"
                              color="text.secondary"
                            >
                              <Link
                                href={`https://www.openstreetmap.org/?mlat=${evento.latitude}&mlon=${evento.longitude}#map=15/${evento.latitude}/${evento.longitude}`}
                                target="_blank"
                                rel="noopener noreferrer"
                              >
                                Ver en Mapa
                              </Link>
                            </Typography>
                          )}
                        </>
                      }
                    />
                  </ListItem>
                ))
              ) : (
                <Typography>No tienes eventos en tu agenda.</Typography>
              )}
            </List>
          </CardContent>
        </Card>
      )}

      <Box sx={{ mt: 3, display: 'flex', justifyContent: 'flex-end' }}>
        <Button variant="contained" color="primary" onClick={handleAddEvent}>
          Agregar Nuevo Evento
        </Button>
      </Box>
    </Container>
  );
};

export default AgendaPage;
