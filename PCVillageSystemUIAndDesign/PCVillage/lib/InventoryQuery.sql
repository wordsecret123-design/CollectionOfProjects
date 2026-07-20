
SELECT `inventory`.`product_id`,
    `inventory`.`product_type`,
    `inventory`.`product_brand`,
    `inventory`.`product_model`,
    `inventory`.`product_purchase_price`,
    `inventory`.`product_selling_price`,
    `inventory`.`product_quantity`,
    `inventory`.`product_last_changed_date`
FROM `pcvillage`.`inventory`;
