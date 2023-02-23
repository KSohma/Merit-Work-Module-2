-- 14. The names of actors who've appeared in the movies in the "Back to the Future Collection" (28 rows)
SELECT person_name
FROM person p
JOIN movie_actor ma ON p.person_id = ma.actor_id
JOIN movie m ON ma.movie_id = m.movie_id
JOIN collection c ON c.collection_id = m.collection_id
WHERE collection_name LIKE 'Back to the Future Collection'
GROUP BY person_name;