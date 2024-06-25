# Technical test 05


Terminar de implementar la API Rest de la entidad de dominio Offer.


El proyecto se entrega con un sencilla distribución por paquetes intentado seguir una arquitectura hexagonal y orientda a Domino, un controlador, un dto, una entidad y una excepcion para la capa Rest, los datos deben de guardse una bbdd en memoria, basicamente es implementar un CRUD de Offer.

La única peculiaridad es que el `*productPartnumber*` en bbbd debe de almacenarse en varias columnas, es decir, la entrada es 000100233 (TTMMMMQQQ)
pero en bbbd se almacenará, TTMMMMQQQ:

- Talla = 00
- Modelo = 0100 
- Calidad = 233

Ojo, la entidad Offer no esta asi definida, se puede cambiar.

Este dato tanto en la entrada como en salida debe de mostrarse con este formato:

```
 "productPartnumber":"000100233",
```

Se valorará:

- Test unitarios y de integracion.
- Uso de arquitectura hexagonal y DDD.
- Uso de patrones.
- Validaciones. 
- Control de expceciones.


## Más detalles del proyecto

- Con las depdencias añadidas es suficiente para montar todo lo que se pide, pero se puede añadir alguna más.
- Eres libre de hacer los cambios que estimes en código, paquetes, etc.
- El proyecto debe de compilar, ser operativo y guarda en una bbdd en memoria.
- Subir al repo una colección de postman de ejemplo.

Ejemplo JSON de entrada de creación de la entidad Offer

```
[
   {
      "offerId":1,
      "brandId":1,
      "startDate":"2020-06-14T00.00.00Z",
      "endDate":"2020-12-31T23.59.59Z",
      "priceListId":1,
      "productPartnumber":"000100233",
      "priority":0,
      "price":35.50,
      "currencyIso":"EUR"
   }
]
```
