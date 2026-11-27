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


## Generación e Instalación del APK Firmado

La aplicación cuenta con una versión de producción (`release`) compilada y firmada digitalmente mediante **APK Signature Scheme v2** (RSA 2048 bits).

## Detalles de la compilación
- **Package Name:** `com.example.proyecto_dsy1105`
- **Versión:** `1.0` (`versionCode: 1`)
- **Archivo generado:** `app-release.apk`
- **Ubicación local por defecto:** `app/build/outputs/apk/release/app-release.apk`
- **Compatibilidad:** Android 7.0 (API 24) o superior.

## Cómo instalar el APK
1. Descarga o copia el archivo `app-release.apk` en tu dispositivo Android (o arrástralo sobre un emulador en ejecución en Android Studio).
2. En el dispositivo, abre el archivo desde el administrador de archivos.
3. Si el sistema lo solicita, habilita la opción **Instalar aplicaciones de origen desconocido** y confirma la instalación.

## Nota sobre la firma digital (Keystore)
- Por motivos de seguridad, el archivo de firma privada (`Key` / `.jks`) y sus credenciales **no se incluyen en este repositorio**.
- Para generar una nueva compilación firmada en Android Studio:
  1. Ir a **Build > Generate Signed App Bundle / APK...**
  2. Seleccionar **APK** y cargar el archivo `Key` local junto con su alias y contraseña.
  3. Elegir la variante **release** y finalizar la compilación.

  Imagen de verificacion de firma digital

<img width="963" height="616" alt="image" src="https://github.com/user-attachments/assets/2ec837c0-e75c-4e74-8867-158dcc419cad" />

Y jks

<img width="83" height="94" alt="image" src="https://github.com/user-attachments/assets/3848e3a5-9c57-4179-a6ee-b5854d049968" />


  
