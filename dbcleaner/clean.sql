SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;


--CREATE INDEX items_products_id_fkidx
--   ON items (products_id);


ALTER TABLE customers_balances ALTER COLUMN order_id DROP NOT NULL; 
 UPDATE customers_balances SET order_id = null;

---------------------------------

  DROP INDEX items_idx_orders_id;
  --DROP INDEX items_idx_product_id;
  ALTER TABLE items DROP CONSTRAINT items_fk_1;
  ALTER TABLE items DROP CONSTRAINT items_fk_2;
  ALTER TABLE items DROP CONSTRAINT items_idx_1;
  ALTER TABLE items DROP CONSTRAINT items_ck_2;
	ALTER TABLE items DISABLE TRIGGER check_maney_balance;
  ALTER TABLE items DISABLE TRIGGER check_product_balance;

  
  ALTER TABLE orders DROP CONSTRAINT orders_fk1;
  ALTER TABLE orders DROP CONSTRAINT orders_fk2;
  ALTER TABLE orders DROP CONSTRAINT orders_fk3;
  ALTER TABLE orders DROP CONSTRAINT orders_fk_this_1;
	
  ALTER TABLE orders DROP CONSTRAINT orders_idx_1;
  ALTER TABLE orders DROP CONSTRAINT orders_idx_2;
	
  --ALTER TABLE orders DROP CONSTRAINT orders_ck_1;
  --ALTER TABLE orders DROP CONSTRAINT orders_ck_2;
 
  DROP INDEX orders_index_for_operation_type_code;
---------------------------------



  CREATE TABLE prod_tmp
  (id integer NOT NULL,
   quantity numeric(20,2) NOT NULL);
   

--RAISE NOTICE 'COPYNG ... ';

INSERT INTO prod_tmp 
SELECT id, select_products_quantity_for_id(id) AS quantity FROM products ORDER BY ID;

--RAISE NOTICE 'DELETING ... ';
  
  

--RAISE NOTICE 'DELETING...ITEMS ';
DELETE FROM items;

UPDATE products SET last_quantity_cache_id=null, last_quantity_cache_value=null;

DELETE FROM psl_ppl_files_history;

--RAISE NOTICE 'DELETING...ORDERS';
DELETE FROM orders;

commit;


--DROP FUNCTION clear_all_data_and_save_quntitys();
CREATE OR REPLACE FUNCTION clear_all_data_and_save_quntitys()
  RETURNS void AS
$BODY$ DECLARE
order_id integer;
prod record; 
t_ship_date timestamp;
t_order_date timestamp;
BEGIN 

  RAISE NOTICE 'ORDERING PPL ... ';
  FOR prod IN SELECT id,quantity FROM prod_tmp WHERE quantity > 0 ORDER BY id
  LOOP
    -- t_order_date := timeofday()::timestamp;


    -- INSERT INTO orders(
    --        ship_date, order_date, operation_type_code, description, sub_order_id, user_id)
    -- VALUES (t_order_date, t_order_date, 'ppl', '', null, 1);
		 

    -- order_id := currval('orders_id_seq'); 
    
    order_id = create_order('ppl', '', null, 1);
    
    --PERFORM create_item( order_id, prod.id, 0, prod.quantity, '');

    INSERT INTO items( orders_id, products_id, actual_price, quantity)
      VALUES (order_id, prod.id, 0, prod.quantity);
   
    RAISE NOTICE '%', ( prod.id || ' -> '|| prod.quantity);
    
  END LOOP;

  

  
  
END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

 select clear_all_data_and_save_quntitys();

commit;



  DROP TABLE prod_tmp;  

commit;

  -- ORDERS -------------------------------------------------------------------------------------------
  ALTER TABLE orders
  ADD CONSTRAINT orders_fk1 FOREIGN KEY (operation_type_code)
      REFERENCES orders_operations_types (type_code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

  ALTER TABLE orders
  ADD CONSTRAINT orders_fk2 FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

  ALTER TABLE orders
  ADD CONSTRAINT orders_fk3 FOREIGN KEY (customer_id)
      REFERENCES customers (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

  ALTER TABLE orders
  ADD CONSTRAINT orders_fk_this_1 FOREIGN KEY (sub_order_id)
      REFERENCES orders (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

  --ALTER TABLE orders
  --ADD CONSTRAINT orders_idx_1 UNIQUE(order_date);
	
  
 
  --ALTER TABLE orders
  --ADD CONSTRAINT orders_ck_2 CHECK (credit = true AND customer_id IS NOT NULL OR credit = false);

  --ALTER TABLE orders
  --ADD CONSTRAINT orders_ck_1 CHECK (order_date <= ship_date OR order_date IS NULL);

  CREATE INDEX orders_idx_1
  ON orders
  USING hash
  (order_date);


  CREATE INDEX orders_index_for_operation_type_code
  ON orders
  USING hash
  (operation_type_code);

  



  
  -- ITEMS -------------------------------------------------------------------------------------------

  CREATE INDEX items_idx_orders_id
  ON items
  USING btree
  (orders_id);

  --CREATE INDEX items_idx_product_id
  --ON items
  --USING btree
  --(products_id);




  ALTER TABLE items
  ADD CONSTRAINT items_fk_1 FOREIGN KEY (orders_id)
      REFERENCES orders (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

  ALTER TABLE items
  ADD CONSTRAINT items_fk_2 FOREIGN KEY (products_id)
      REFERENCES products (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

  ALTER TABLE items
  ADD CONSTRAINT items_idx_1 UNIQUE(orders_id, products_id);

  ALTER TABLE items
  ADD CONSTRAINT items_ck_2 CHECK (0::numeric <= get_cass_balance());


  ALTER TABLE items ENABLE TRIGGER check_maney_balance;
  ALTER TABLE items ENABLE TRIGGER check_product_balance;  
  



----------------------------------
commit;

 


