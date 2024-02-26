## Firebase as database

* Status: ACCEPTED
* Deciders:
  * [Benoit Beaumont](mailto:benoit.beaumont97@gmail.com)
  * [Romain Pani](mailto:romainpanii@gmail.com)
* Date: 2024-02-26

### Context and Problem Statement

In the context of Musigame, we needed to make a decision regarding the selection of a database solution. 

### Considered Options
* Firebase
* PostgreSQL

### Decision Outcome

We have decided to adopt Firebase as our database solution.

### Pros and Cons of the Options

#### Firebase

* Temporary Data Storage: Our project primarily deals with temporary data storage requirements. Firebase offers real-time database capabilities that align well with our need to store and retrieve temporary data efficiently.
* Cloud-Based : we do not need to have an infrastructure to maintain as we would have done with a PostgreSQL instance (K8S, Docker needed...)
* Cost : Firebase is free
* Experience : Our team possesses prior experience with Firebase. Leveraging our existing knowledge and expertise with Firebase reduces the learning curve and enables us to implement the database solution more efficiently.

#### PostgreSQL

* Reliability
* Scalability : the scability on PostgreSQL is cheaper than on firebase.
