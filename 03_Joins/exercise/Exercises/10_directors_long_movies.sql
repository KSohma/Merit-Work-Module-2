-- 10. The names of directors who have directed a movie over 3 hours [180 minutes] (15 rows)
SELECT person_name
FROM movie m
JOIN person p ON p.person_id = m.director_id
WHERE length_minutes > '180'
GROUP BY person_name;

