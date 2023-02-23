-- 13. The directors of the movies in the "Harry Potter Collection" (4 rows)
SELECT person_name
FROM movie m
JOIN collection c ON c.collection_id = m.collection_id
JOIN person p ON p.person_id = m.director_id
WHERE collection_name LIKE 'Harry Potter Collection'
GROUP BY person_name
ORDER BY person_name;
