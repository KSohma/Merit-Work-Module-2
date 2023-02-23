-- 9. The titles of movies directed by James Cameron (6 rows)
SELECT title
FROM person p
JOIN movie m ON p.person_id = m.director_id
WHERE person_name = 'James Cameron'
