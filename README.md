# Spyro The Dragon - Guía Interactiva.

## 🌐 Introducción
Esta aplicación inspirada en **Spyro The Dragon** ha sido actualizada por el alumnado (JJZG) con una  **guía interactiva** y dos **easter eggs**.

La guía enseña al usuario a navegar por la app con explicaciones visuales y animadas. Los easter eggs agregan elementos sorpresa y entretenimiento en la aplicación.

---

## 🔧 Características principales

### 📱 Guía Interactiva
- **6 pantallas interactivas** que explican la navegación de la app.
- **Bocadillos animados** con suaves deslizamientos para resaltar elementos clave.
- **Transiciones fluidas** entre pantallas.
- **Sonidos temáticos** inspirados en Spyro.
- **Omitible**: Se muestra solo la primera vez que se abre la app.

### 🌟 Easter Eggs
#### 🎥 **Easter Egg con Video**
- **Ubicación**: Pestaña de coleccionables.
- **Acción**: Pulsar 4 veces sobre las gemas.
- **Efecto**: Se reproduce un **video secreto** en pantalla completa.

#### 🔥 **Easter Egg con Animación**
- **Ubicación**: Pestaña de personajes.
- **Acción**: Pulsación larga sobre Spyro.
- **Efecto**: Aparece una **animación de fuego** saliendo de su boca. (mas o menos)

---

## 📚 Tecnologías utilizadas
- **Lenguaje**: Java.
- **Framework**: Android SDK.
- **Interfaz**:
  - ConstraintLayout, FrameLayout, RecyclerView.
  - XML para diseño de UI.
- **Animaciones**:
  - Animaciones con `Canvas` y `invalidate()`.
  - Animaciones con `AlphaAnimation`, `ScaleAnimation`.
- **Sonido y video**:
  - `MediaPlayer` para efectos sonoros.
  - `VideoView` para reproducción de video.
- **Persistencia de datos**:
  - `SharedPreferences` para mostrar la guía solo la primera vez.
- **Gestos y eventos**:
  - `onClickListener`, `onLongClickListener`.

---

## 🗃️ Instrucciones de uso

### ⚡ Clonar el Repositorio

La clonación de la app, la realizamos a traves de la uri proporcionada por la profesora Lindsay ("https://github.com/lbarmar/SpyroTheDragon.git")
Una vez clonada, eliminamos la fuente en manager remotes, y seleccionamos nuestro propio repositorio, donde se han ido haciendo los commits necesarios para el desarrollo de la app

### 🛠️ Instalar dependencias
Con **Android Studio** instalado y abierto el proyecto.

1. **Abrir Android Studio**.
2. **Importar el proyecto** desde el directorio clonado.
3. **Ejecutar la aplicación** en un emulador o dispositivo físico.

---

## 📚 Conclusiones del desarrollador
El desarrollo de esta actualización ha sido dificil en cuanto a asmiliar los conceptos de formas en el espacio de la app. Tambien en la diferentes formas de creacion de un mismo objetivo, dando como recultado una nueva **gestión de animaciones y eventos de usuario**. Algunos aprendizajes clave:
- **Control de la navegación** con `FrameLayout` y `SharedPreferences`.
- **Movimiento entre pantallas (transiciones).
- **Uso de `Canvas` para crear efectos visuales personalizados**.

El resultado es una aplicación más atractiva e interactiva para los usuarios.

---


