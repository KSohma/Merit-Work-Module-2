-- 6. The genres of "The Wizard of Oz" (3 rows)
SELECT genre_name
FROM genre g
JOIN movie_genre mg ON g.genre_id = mg.genre_id
JOIN movie m ON m.movie_id = mg.movie_id
WHERE title LIKE 'The Wizard of Oz'


