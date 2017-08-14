# PARTridge - 3D Printing Assessment Tool API

The PARTridge API is a RESTful JAVA/Spring API supporting a proof-of-concept assessment and decision support tool ([3D Printing Assessment Client](https://github.com/danielbutts/3d-printing-assessment-client)). The application allows a user to upload a parts list and quickly assess the potential for digitization; focusing engineering time and effort only on the parts most likely to benefit.

## Key Features

- RESTful endpoints for Vendor, Printer, Part, User, and Material entities
- 3D printing price estimator based on part volume, material, process, printer, and vendor markup
- Endpoints secured with JWT authorization

## Built With

* [Java](https://www.java.com/en/)
* [Spring](https://spring.io/)
* [Postgres](https://www.postgresql.org/)
* [Spring Data JPA](https://projects.spring.io/spring-data-jpa/)
* [JSON Web Tokens](https://jwt.io/)

## Author

* [**Daniel Butts**] (https://github.com/danielbutts)

## Other Resources

* [API Documentation](https://documenter.getpostman.com/view/2366499/capstone-crud-endpoints-dev/6fYSNiZ#65b2af27-2b60-2d3e-fd05-1031911edf0c)
* [3D Printing Assessment Client](https://github.com/danielbutts/3d-printing-assessment-client)
* [Presentation Slides](https://docs.google.com/presentation/d/1OZ3dQM0XrzSwwCkKYrvwR0XvF68GWWbcVHSVPATfsIw/edit?usp=sharing)
* [Presentation Video](https://vimeo.com/229517666)

## Acknowledgments

* Many thanks to [3Discovered](http://3discovered.com/) for proposing the project and providing early and continuous feedback on the MVP application.