# CORPALIV – Alba Lab

Aplicación Android desarrollada para la asignatura **DSY1105 – Desarrollo de Aplicaciones Móviles**.

La aplicación presenta información de **CORPALIV y Alba Lab**, sus programas de inclusión social y laboral, productos, impacto social y alternativas de colaboración. También incluye un sistema de cuentas local y un perfil con cámara.

> **Proyecto académico:** versión demostrativa (MVP). Se deben utilizar únicamente datos ficticios para las pruebas.

## Integrantes

- Sebastián Sellao
- Enzo Muñoz

## Funcionalidades

- **Inicio:** presentación de CORPALIV y acceso a las secciones.
- **Nosotros:** información de CORPALIV, Alba Lab y la Escuela Especial Jan Van Dijk.
- **Programas:** iniciativas de formación, inclusión y vinculación laboral.
- **Catálogo:** categorías demostrativas de productos y servicios.
- **Impacto social:** áreas de trabajo e indicadores pendientes de datos oficiales.
- **Colaboraciones:** información sobre donaciones, voluntariado y alianzas.
- **Contacto:** formulario con validaciones básicas (sin envío a servidor).
- **Registro e inicio de sesión:** cuentas locales con nombre, RUT, correo y contraseña.
- **Perfil:** datos del usuario, cámara, selección desde galería y cierre de sesión.
- **Fotografía:** guardado en el almacenamiento interno para conservar la imagen al volver al perfil.

## Herramientas utilizadas

- **Android Studio** y **Kotlin**.
- **Jetpack Compose** y **Material Design 3** para la interfaz.
- **Navigation Compose** para cambiar entre pantallas.
- **ViewModel** para gestionar el estado de autenticación y perfil.
- **SharedPreferences** para las cuentas de demostración y la referencia de la foto guardada.
- **Git y GitHub** para el control de versiones.

Las contraseñas no se guardan en texto plano: se almacena un hash derivado con **PBKDF2 y sal aleatoria**.

## Recursos del dispositivo

- **Cámara:** permite tomar una fotografía de perfil.
- **Galería / selector de imágenes:** permite elegir una imagen del dispositivo.

## Cómo ejecutar el proyecto

1. Clonar este repositorio:

git clone https://github.com/waton123waton/Proyecto_DSY1105.git

2. Abrir la carpeta **Proyecto_DSY1105** en Android Studio.
3. Esperar la sincronización de **Gradle**.
4. Seleccionar un emulador o dispositivo Android y presionar **Run**.
5. Crear una cuenta de prueba e iniciar sesión para acceder al perfil.

Para compilar desde la terminal en Windows:

.\gradlew.bat :app:assembleDebug

## Pendiente / mejoras futuras

- Guardar y consultar las solicitudes de contacto.
- Completar las validaciones visuales por campo y el segundo formulario requerido por la evaluación.
- Verificar las animaciones y los recursos nativos solicitados en la rúbrica.
- Asociar una fotografía diferente a cada cuenta.
- Integrar una API REST en una versión futura.
