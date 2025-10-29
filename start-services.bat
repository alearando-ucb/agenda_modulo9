@echo off

echo Iniciando microservicios...

REM Guarda el directorio actual
set CURRENT_DIR=%cd%

REM Iniciar discovery-service (debe ser el primero)
echo Iniciando discovery-service...
cd backend\discoveryservice
start "DiscoveryService" cmd /k "mvn spring-boot:run"

REM Esperar un momento para que Discovery Service se inicie completamente
ping 127.0.0.1 -n 5 > nul

REM Volver al directorio original y luego ir a gateway-service
cd %CURRENT_DIR%
cd backend\gatewayservice
echo Iniciando gateway-service...
start "GatewayService" cmd /k "mvn spring-boot:run"

REM Volver al directorio original y luego ir a userservice
cd %CURRENT_DIR%
cd backend\userservice
echo Iniciando userservice...
start "UserService" cmd /k "mvn spring-boot:run"

REM Volver al directorio original y luego ir a agendaservice
cd %CURRENT_DIR%
cd backend\agendaservice
echo Iniciando agendaservice...
start "AgendaService" cmd /k "mvn spring-boot:run"

cd %CURRENT_DIR%
echo Iniciando agendaclient (React app)...
cd frontend\agendaclient
start "AgendaClient" cmd /k "npm start"

cd %CURRENT_DIR%
echo. 
echo Comandos de inicio enviados. Las ventanas de los servicios se estan abriendo.