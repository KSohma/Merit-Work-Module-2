-- 11. The titles of the movies in the "Star Wars Collection" ordered by release date, most recent to earliest (9 rows)
SELECT title
FROM movie m
JOIN collection c ON c.collection_id = m.collection_id
WHERE collection_name LIKE 'Star Wars Collection'
ORDER BY release_date DESC;
