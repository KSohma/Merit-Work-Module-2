-- 7. The genres of movies that Christopher Lloyd has appeared in (8 rows)
-- Hint: DISTINCT will prevent duplicate values in your query results.
SELECT genre_name
FROM genre g
JOIN movie_genre mg ON mg.genre_id = g.genre_id
JOIN movie m ON m.movie_id = mg.movie_id
JOIN movie_actor ma ON ma.movie_id = m.movie_id
JOIN person p ON p.person_id = ma.actor_id
WHERE person_name LIKE 'Christopher Lloyd'
GROUP BY genre_name;
