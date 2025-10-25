@echo off

echo Iniciando microservicios...

REM Guarda el directorio actual
set CURRENT_DIR=%cd%

REM Iniciar userservice
echo Iniciando userservice...
cd backend\userservice
start "UserService" cmd /k "mvn spring-boot:run"

REM Volver al directorio original y luego ir a agendaservice
cd %CURRENT_DIR%
cd backend\agendaservice
echo Iniciando agendaservice...
start "AgendaService" cmd /k "mvn spring-boot:run"

cd %CURRENT_DIR%
echo. 
echo Comandos de inicio enviados. Las ventanas de los servicios se estan abriendo.
