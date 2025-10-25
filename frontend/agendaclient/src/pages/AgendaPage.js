import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { getEventos } from '../services/api';
import { Button, Container, Typography, Box, List, ListItem, ListItemText, Card, CardContent, CircularProgress, Alert } from '@mui/material';

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
        <Typography variant="h4" component="h1">
          Agenda de {user ? user.nombre : ''}
        </Typography>
        <Box>
          <Button variant="contained" color="primary" sx={{ mr: 2 }} onClick={handleAddEvent}>
            Agregar Nuevo Evento
          </Button>
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
    </Container>
  );
};

export default AgendaPage;
