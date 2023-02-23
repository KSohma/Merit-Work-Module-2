-- 12. Create a "Bill Murray Collection" in the collection table. For the movies that have Bill Murray in them, set their collection ID to the "Bill Murray Collection". (1 row, 6 rows)
INSERT INTO collection (collection_name)
VALUES ('Bill Murray Collection');

--SELECT collection_id
--FROM collection
--WHERE collection_name LIKE 'Bill Murray Collection';
--
--SELECT title, collection_id, p.*
--FROM movie m
--JOIN movie_actor ma ON ma.movie_id = m.movie_id
--JOIN person p ON p.person_id = ma.actor_id
--WHERE person_name LIKE 'Bill Murray'
--order by title;

UPDATE movie m
SET collection_id = (SELECT collection_id
                    FROM collection
                    WHERE collection_name LIKE 'Bill Murray Collection')
FROM movie_actor ma
JOIN person p ON p.person_id = ma.actor_id
WHERE ma.movie_id = m.movie_id
AND person_name LIKE 'Bill Murray';