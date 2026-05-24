# Ospedale

Proyecto del Parcial 3 de Programacion Orientada a Objetos.

## Integrantes

- Nombre completo 1 - NRC
- Nombre completo 2 - NRC
- Nombre completo 3 - NRC

## Arquitectura

El proyecto fue refactorizado siguiendo MVC:

- `model`: entidades del dominio y almacenamiento simulado.
- `view`: interfaces Swing existentes, sin cambiar el layout visual.
- `controller`: capa delgada que delega la logica a servicios.
- `service`: reglas de negocio y casos de uso.
- `validator`: validaciones de entrada.
- `observer`: actualizacion automatica de tablas y combos cuando cambian los modelos.

## Funcionalidades cubiertas

- Login por rol.
- Registro y actualizacion de pacientes.
- Registro y actualizacion de doctores.
- Solicitud, aceptacion, completado, cancelacion y reagendamiento de citas.
- Prescripcion de medicamentos durante citas pendientes.
- Solicitud, aprobacion y cancelacion de hospitalizaciones.
- Hospitalizacion directa desde una cita pendiente.
- Carga automatica de datos en vistas y actualizacion automatica por observador.

## Nota

Antes de entregar, reemplacen los datos de ejemplo de la seccion de integrantes por los nombres reales y sus NRC.
