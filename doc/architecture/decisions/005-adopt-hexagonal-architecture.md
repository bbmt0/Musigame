## Adopt DDD Approach

* Status: ACCEPTED
* Deciders:
  * [Benoit Beaumont](mailto:benoit.beaumont97@gmail.com)
  * [Romain Pani](mailto:romainpanii@gmail.com)
* Date: 2024-02-26

### Context and Problem Statement

[Domain-Driven Design](https://martinfowler.com/bliki/DomainDrivenDesign.html) approach will be used to determine the
[Bounded-Contexts](https://martinfowler.com/bliki/BoundedContext.html) around business capabilities for the whole product

### Considered Options
* Domain-Driven Design
* CRUD

### Decision Outcome

Using DDD instead of CRUD will allow us to have a better understanding of the business logic and to have a better separation of concerns.

### Pros and Cons of the Options

#### DDD

* More maintainable code
* Better separation of concerns
* Better understanding of the business logic
* More complex to implement

#### CRUD

* Easier to implement
* Less maintainable code
* Less separation of concerns
* Less understanding of the business logic
* More complex to maintain