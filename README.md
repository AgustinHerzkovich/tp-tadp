# Técnicas Avanzadas de Programación – Ruby & Scala

Repositorio que contiene dos trabajos prácticos desarrollados en la materia **Técnicas Avanzadas de Programación – UTN FRBA (1C 2025)**, enfocados en el uso de paradigmas avanzados: **metaprogramación en Ruby** y **programación híbrida objeto-funcional en Scala**.

---

## 🧠 Descripción general

Este repositorio agrupa dos proyectos independientes que exploran conceptos avanzados de diseño de software:

| Proyecto | Lenguaje | Paradigma |
|---------|----------|-----------|
| Contratos | Ruby | Metaprogramación y diseño por contratos |
| Festival de Invierno | Scala | Programación híbrida objeto-funcional |

---

## 🛠️ Tecnologías

- Ruby – metaprogramación, hooks de clases, redefinición dinámica de métodos  
- Scala – programación funcional, objetos, pattern matching, inmutabilidad  
- Paradigmas – Diseño por contratos, polimorfismo paramétrico, funciones de orden superior  

---

## 📁 Estructura del repositorio


---

## 🟥 Proyecto 1 – Contratos (Ruby)

Framework de **diseño por contratos** que permite definir:

- Comportamiento *before* y *after* para cada método de instancia.  
- **Invariantes** de clase validadas tras cada mensaje.  
- **Precondiciones** y **postcondiciones** asociadas declarativamente a métodos.  

### Funcionalidades principales

- Uso de metaprogramación para interceptar la definición de métodos.  
- Ejecución automática de validaciones antes y después de cada llamada.  
- Excepciones automáticas al incumplir contratos.  
- Soporte para múltiples invariantes y múltiples callbacks before/after.  

### Ejemplo

```ruby
class Guerrero
  attr_accessor :vida, :fuerza

  invariant { vida >= 0 }
  invariant { fuerza > 0 && fuerza < 100 }

  def atacar(otro)
    otro.vida -= fuerza
  end
end
```

---

## 🟦 Proyecto 2 – Festival de Invierno (Scala)

Sistema que modela un torneo de vikingos con **postas de pesca, combate y carrera**, incorporando:

- Vikingos, dragones y jinetes.
- Reglas de negocio complejas.
- Torneos con reglas configurables.

### Enfoque de diseño

- Uso intensivo de **inmutabilidad**.
- **Pattern matching** para polimorfismo paramétrico.
- Combinación de objetos con funciones puras.
- Uso de funciones de orden superior y tipos algebraicos.

### Características principales

- Cálculo funcional de resultados sin efectos colaterales.
- Reglas de torneo configurables: estándar, eliminación, inverso, con veto, handicap y por equipos.
- Selección óptima de montura por participante.
- Modelado tipado de participantes, dragones y postas.
