-- :name get-food-groups :? :*
-- :doc retrieve all food groups
SELECT * FROM food_group;

-- :name get-food-by-food-group :? :*
-- :doc retrieve food by food group (with paging)
SELECT * FROM food WHERE food_group_id = :food_group_id
ORDER BY id LIMIT :limit OFFSET :offset;

-- :name get-nutrition-by-food :? :*
-- :doc retrieve nutrition by food
SELECT * FROM nutrition WHERE food_id = :food_id;

-- :name get-nutrients :? :*
-- :doc retrieve nutrients
SELECT * FROM nutrient;

-- :name get-common-nutrients :? :*
-- :doc retrieve common nutrients
SELECT n.id, n.units, n.tagname, n.name, n.num_decimal_places, n.sr_order
FROM common_nutrient AS c JOIN nutrient AS n ON c.id = n.id;