## Tratarea problemei N + 1 in JPA

Suntem in contextul unei librarii online. O carte poate avea zero sau mai multe recenzii scrise de catre cititori. O
recenzie este pentru o singura carte.

In JPA modelam aceasta prin doua clase Book si respectiv BookReview. M-am inspirat din:
- https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
- https://thorben-janssen.com/best-practices-many-one-one-many-associations-mappings/

Se cere:

- lista tuturor cartilor ordonate. Fiecare carte va avea toate recenziile.
- lista cartilor paginat ordonate. Fiecare carte va avea toate recenziile.

Conform bunelor practici vom intoarce informatiile incapsulate in clase "Dto".

Atentie la cazul cand o care nu are nici un review.