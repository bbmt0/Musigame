## Project as monorepo on github

* Status: ACCEPTED
* Deciders:
  * [Benoit Beaumont](mailto:benoit.beaumont97@gmail.com)
  * [Romain Pani](mailto:romainpanii@gmail.com)
* Date: 2024-02-13

### Context and Problem Statement

Musigame will consist in multiple applications, including backend-for-frontend and a frontend.

### Considered Options
* One git repository for each application (multi-repo)
* A global git repository for all applications (monorepo)

### Decision Outcome

The project will be be split in 2 different applications to run independently:

* a frontend application to provide the web views and let users interact with it
* a backend-for-frontend to handle other external APIs and authentication

### Pros and Cons of the Options

#### Multi-repo

* Simplified development as the git repo can be configured properly
* But the development team will have to switch between repos when doing a feature that needs changes in both applications

#### Monorepo

* All application in one directory, so only one single pull request for changes on both applications
* All the documentation is centralized
