
## Tratarea problemei N + 1 in JPA

Suntem in contextul unei librarii online. O carte poate avea zero sau mai multe recenzii scrise de catre cititori. O recenzie este pentru o singura carte.

In JPA modelam aceasta prin doua clase "Entity" Book si respectiv BookReview. Conform bunelor practici am modelat acesta asociere unidirectional si am ales  "LAZY" la asocierea "@ManyToOne(fetch = FetchType.LAZY).

Se cere:
- lista tuturor cartilor ordonate dupa titlu. Fiecare carte va avea toate recenziile.
- lista tuturor cartilor ordonate dupa titlu paginat cate 2 carti pe fiecare pagina. Fiecare carte va avea toate recenziile.

Vom avea o implementare in care "Repository" intoarce informatiile incapsulate in clase  "Entity" (calea usoara) si o implementare care intoarce informatiile incapsulate in clase "Dto" ( calea de urmat conform bunelor practici).