## Tratarea problemei N + 1 in JPA

Suntem in contextul unei librarii online. O carte poate avea zero sau mai multe recenzii scrise de catre cititori. O
recenzie este pentru o singura carte.

In JPA modelam aceasta prin doua clase Book si respectiv BookReview. M-am inspirat din:
- https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
- https://thorben-janssen.com/best-practices-many-one-one-many-associations-mappings/

Se cere:

- lista tuturor cartilor. Fiecare carte va avea toate recenziile.
- lista tuturor cartilor paginat. Fiecare carte va avea toate recenziile.
- Regula: orice lista trebuie ordonata; Daca nu imi vine ca parametru ordonare programatorul face una implicita. 

Conform bunelor practici vom intoarce informatiile incapsulate in clase "Dto".

Atentie la cazul cand o carte nu are nici un review.