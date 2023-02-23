-- 3. For all actors with the last name of "Jones", display the actor's name and movie titles they appeared in. Order the results by the actor names (A-Z). (48 rows)
SELECT person_name, title
FROM person p
JOIN movie_actor a ON p.person_id = a.actor_id
JOIN movie m ON m.movie_id = a.movie_id
WHERE person_name LIKE '% Jones'
ORDER BY person_name;

