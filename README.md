# Order Platform -- Microservices Architecture

## Descripción del proyecto

**Order Platform** es una aplicación basada en **arquitectura de
microservicios** que implementa un flujo de procesamiento de pedidos
mediante comunicación **asíncrona basada en eventos**.

El sistema está compuesto por múltiples servicios independientes que
interactúan mediante mensajería, permitiendo **desacoplamiento,
escalabilidad y resiliencia**.

La solución implementa además prácticas modernas de **DevOps y Cloud
Native**, incluyendo:

-   Contenerización con Docker
-   Orquestación con Kubernetes
-   Gestión de despliegues con Helm
-   GitOps con ArgoCD
-   Integración continua con GitHub Actions

------------------------------------------------------------------------

# Arquitectura del sistema

La arquitectura sigue un enfoque de **microservicios orientado a eventos
(EDA)** donde los servicios se comunican a través de un **message broker
(RabbitMQ)**.

## Diagrama de arquitectura

Client / Postman\
↓\
API Gateway\
↓\
Order Service\
↓\
RabbitMQ (Event Broker)\
↓\
Inventory Service\
↓\
RabbitMQ (Inventory Updated Event)\
↓\
Order Service + Notification Service

------------------------------------------------------------------------

# Flujo de funcionamiento

1.  Un cliente realiza una solicitud HTTP para crear un pedido.
2.  La solicitud es recibida por el **API Gateway**.
3.  El Gateway redirige la petición al **Order Service**.
4.  El **Order Service** registra el pedido y publica un evento en
    **RabbitMQ**.
5.  **Inventory Service** consume el evento y valida el inventario.
6.  Una vez actualizado el inventario, se publica un nuevo evento.
7.  **Notification Service** consume el evento y envía la notificación
    correspondiente.

Este enfoque permite que los servicios se mantengan **desacoplados**,
facilitando su evolución independiente.

------------------------------------------------------------------------

# Microservicios implementados

## API Gateway

Responsable de centralizar el acceso al sistema.

Funciones principales:

-   Enrutamiento de solicitudes
-   Punto único de entrada
-   Ocultamiento de la arquitectura interna

Tecnología utilizada:

-   Spring Cloud Gateway

------------------------------------------------------------------------

## Order Service

Gestiona la creación y consulta de pedidos.

Endpoints principales:

POST /orders\
GET /orders/{id}

Responsabilidades:

-   Crear pedidos
-   Publicar eventos de pedido

------------------------------------------------------------------------

## Inventory Service

Gestiona la validación del inventario.

Responsabilidades:

-   Consumir eventos de pedido
-   Validar disponibilidad
-   Publicar evento de actualización

------------------------------------------------------------------------

## Notification Service

Responsable de notificar al usuario sobre el estado del pedido.

Responsabilidades:

-   Consumir eventos de inventario
-   Generar notificaciones

------------------------------------------------------------------------

# Infraestructura y despliegue

## Docker

Cada microservicio fue **contenedorizado** utilizando Docker.

Ventajas:

-   Portabilidad
-   Aislamiento de dependencias
-   Facilidad de despliegue

------------------------------------------------------------------------

## Kubernetes

Los contenedores son orquestados utilizando **Kubernetes**, permitiendo:

-   escalabilidad
-   recuperación automática
-   balanceo de carga
-   gestión de pods

Cada servicio cuenta con:

-   Deployment
-   Service

------------------------------------------------------------------------

## Helm

Helm se utiliza como **gestor de paquetes para Kubernetes**.

Permite:

-   parametrizar despliegues
-   reutilizar configuraciones
-   gestionar versiones de despliegue

El chart principal se encuentra en:

helm/order-platform

------------------------------------------------------------------------

## ArgoCD

Se implementó **GitOps** mediante ArgoCD.

ArgoCD sincroniza automáticamente el clúster de Kubernetes con el
repositorio Git.

Flujo:

GitHub → ArgoCD → Kubernetes

Cuando se realiza un cambio en el repositorio, ArgoCD detecta la
diferencia y actualiza el clúster.

------------------------------------------------------------------------

## GitHub Actions (CI Pipeline)

Se implementó un pipeline de integración continua.

El pipeline ejecuta automáticamente:

-   checkout del repositorio
-   compilación con Maven
-   generación de artefactos

Se ejecuta en cada push a la rama main.

------------------------------------------------------------------------

# Tecnologías utilizadas

-   Java 21
-   Spring Boot
-   Spring Cloud Gateway
-   RabbitMQ
-   Docker
-   Kubernetes
-   Helm
-   ArgoCD
-   GitHub Actions

------------------------------------------------------------------------

# Estilos arquitectónicos utilizados

## Arquitectura de microservicios

La aplicación está compuesta por servicios independientes que se
comunican a través de APIs y eventos.

Beneficios:

-   escalabilidad independiente
-   despliegue independiente
-   resiliencia del sistema

------------------------------------------------------------------------

## Event Driven Architecture (EDA)

La comunicación entre servicios se realiza mediante eventos publicados
en RabbitMQ.

Ventajas:

-   desacoplamiento entre servicios
-   procesamiento asíncrono
-   mayor resiliencia

------------------------------------------------------------------------

## API Gateway Pattern

El API Gateway actúa como punto de entrada único al sistema.

Beneficios:

-   centralización del acceso
-   simplificación del cliente
-   control de rutas

------------------------------------------------------------------------

# Patrones de diseño utilizados

## Publish--Subscribe

Los servicios publican eventos en RabbitMQ y otros servicios se
suscriben a ellos.

Esto permite comunicación desacoplada.

------------------------------------------------------------------------

## Producer--Consumer

Los servicios productores envían mensajes al broker y los consumidores
los procesan.

------------------------------------------------------------------------

## Gateway Pattern

El API Gateway encapsula la arquitectura interna y maneja el
enrutamiento.

------------------------------------------------------------------------

# Estructura del proyecto

order-platform │ ├── api-gateway ├── order-service ├── inventory-service
├── notification-service │ ├── helm │ └── order-platform │ ├── .github │
└── workflows │ └── ci.yml │ └── README.md

------------------------------------------------------------------------

# Pruebas

Las pruebas del sistema pueden realizarse mediante herramientas como:

-   Postman
-   curl

Ejemplo:

POST /orders\
GET /orders/{id}

------------------------------------------------------------------------

# Conclusión

El proyecto demuestra la implementación de una arquitectura moderna
basada en microservicios, utilizando comunicación orientada a eventos y
herramientas de infraestructura Cloud Native.

La integración de Docker, Kubernetes, Helm, ArgoCD y GitHub Actions
permite construir una plataforma robusta que sigue principios de
automatización, escalabilidad y despliegue continuo.
