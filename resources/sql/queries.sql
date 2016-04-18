-- :name get-food-groups :? :*
-- :doc retrieve all food groups
SELECT COUNT(*) AS food_count, fg.id AS id, fg.name AS name FROM food AS f
JOIN food_group AS fg ON fg.id = f.food_group_id
GROUP BY fg.id;

-- :name get-food-by-food-group :? :*
-- :doc retrieve food by food group (with paging)
SELECT * FROM food WHERE food_group_id = :food_group_id
ORDER BY id LIMIT :limit OFFSET :offset;

-- :name get-nutrition-by-food :? :*
-- :doc retrieve nutrition by food
SELECT u.id AS id, u.name AS name, n.amount AS amount, u.units AS units
FROM nutrition AS n JOIN nutrient AS u ON u.id = n.nutrient_id
WHERE food_id = :food_id;

-- :name get-nutrients :? :*
-- :doc retrieve nutrients
SELECT * FROM nutrient;

-- :name get-common-nutrients :? :*
-- :doc retrieve common nutrients
SELECT n.id, n.units, n.tagname, n.name, n.num_decimal_places, n.sr_order
FROM common_nutrient AS c JOIN nutrient AS n ON c.id = n.id;