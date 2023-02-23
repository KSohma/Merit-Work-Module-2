-- 15. The title of the movie and the name of director for movies where the director was also an actor in the same movie (73 rows)
SELECT title, p.person_name
FROM movie m
JOIN person p ON p.person_id = m.director_id
JOIN movie_actor ma ON ma.movie_id = m.movie_id
JOIN person p2 ON p2.person_id = ma.actor_id
WHERE p2.person_id = m.director_id;
