-- 8. The genres of movies that Robert De Niro has appeared in that were released in 2010 or later (6 rows)
SELECT genre_name
FROM genre g
JOIN movie_genre mg ON mg.genre_id = g.genre_id
JOIN movie m ON m.movie_id = mg.movie_id
JOIN movie_actor ma ON ma.movie_id = m.movie_id
JOIN person p ON p.person_id = ma.actor_id
WHERE person_name LIKE 'Robert De Niro'
AND release_date > '2010-01-01'
GROUP BY genre_name;

