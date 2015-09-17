--
-- PostgreSQL database dump
--

-- Started on 2014-03-03 13:42:09 ALMT

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 662 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

--
-- TOC entry 183 (class 1255 OID 18446)
-- Dependencies: 662 3
-- Name: add_money_to_cass(character varying, numeric, character varying, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION add_money_to_cass(OUT id integer, OUT t_ship_date timestamp without time zone, OUT t_order_date timestamp without time zone, t_operation_type_code character varying, summa numeric, t_description character varying, t_user_id integer) RETURNS record
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  t_ship_date := now();
  t_order_date := t_ship_date;

  INSERT INTO orders(
            ship_date, order_date, operation_type_code, description, user_id)
    VALUES (t_ship_date, t_order_date, t_operation_type_code, t_description, t_user_id);

  id := currval('orders_id_seq'); 

  INSERT INTO items( orders_id, products_id, actual_price, quantity)
    VALUES (id, null, summa, 1);

END; $$;


--
-- TOC entry 184 (class 1255 OID 18447)
-- Dependencies: 662 3
-- Name: add_money_to_cass(numeric, character varying, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION add_money_to_cass(in_summa numeric, in_description character varying, in_user_id integer) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
	t_operation_type_code character varying;
	t_order_id integer;
BEGIN 

  IF in_summa >=0 THEN
	t_operation_type_code := 'mpl';
  ELSE
	t_operation_type_code := 'mmn';
  END IF;
  

  INSERT INTO orders(
            ship_date, order_date, operation_type_code, description, user_id)
    VALUES (now(), now(), t_operation_type_code, in_description, in_user_id);

  t_order_id := currval('orders_id_seq'); 

  INSERT INTO items( orders_id, products_id, actual_price, quantity)
    VALUES (t_order_id, null, abs(in_summa), 1);

END; $$;


--
-- TOC entry 185 (class 1255 OID 18448)
-- Dependencies: 3 662
-- Name: add_or_update_appaccess(character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION add_or_update_appaccess(in_kod character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
	t_acces_is_have integer := null;
BEGIN 

	SELECT 1 into t_acces_is_have FROM appaccess 
		WHERE kod = in_kod;

	IF t_acces_is_have IS null THEN 
	--raise EXCEPTION 'Привет';
		INSERT INTO appaccess(kod)
		VALUES (in_kod);
	--ELSE
	--	UPDATE appaccess
	--		SET description=in_description
	--	WHERE kod = in_kod;
	END IF;

END; $$;


--
-- TOC entry 186 (class 1255 OID 18449)
-- Dependencies: 662 3
-- Name: add_or_update_db_parametr(character varying, character varying, numeric, timestamp without time zone, boolean); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION add_or_update_db_parametr(in_pkey character varying, in_v_text character varying, in_v_numeric numeric, in_v_time timestamp without time zone, in_v_boolean boolean) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
	t_is_have numeric := 1;
BEGIN 

	SELECT 1 into t_is_have FROM db_params 
		WHERE pkey = in_pkey;

	IF t_is_have IS null THEN 
		INSERT INTO db_params(
			pkey, v_text, v_numeric, v_time, v_boolean)
		VALUES (in_pkey, in_v_text, in_v_numeric, in_v_time, in_v_boolean);
	ELSE
		UPDATE db_params
			SET v_text=in_v_text, v_numeric=in_v_numeric, v_time=in_v_time, v_boolean=in_v_boolean
		WHERE pkey=in_pkey;
	END IF;

END; $$;


--
-- TOC entry 187 (class 1255 OID 18450)
-- Dependencies: 662 3
-- Name: add_or_update_db_parametr(character varying, character varying, numeric, timestamp with time zone, boolean); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION add_or_update_db_parametr(in_pkey character varying, in_v_text character varying, in_v_numeric numeric, in_v_time timestamp with time zone, in_v_boolean boolean) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
	t_is_have numeric := 1;
BEGIN 

	SELECT 1 into t_is_have FROM db_params 
		WHERE pkey = in_pkey;

	IF t_is_have IS null THEN 
		INSERT INTO db_params(
			pkey, v_text, v_numeric, v_time, v_boolean)
		VALUES (in_pkey, in_v_text, in_v_numeric, in_v_time, in_v_boolean);
	ELSE
		UPDATE db_params
			SET v_text=in_v_text, v_numeric=in_v_numeric, v_time=in_v_time, v_boolean=in_v_boolean
		WHERE pkey=in_pkey;
	END IF;

END; $$;


--
-- TOC entry 188 (class 1255 OID 18451)
-- Dependencies: 662 3
-- Name: add_psl_ppl_file_to_history(integer, character varying, integer, boolean, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION add_psl_ppl_file_to_history(in_order_id integer, in_file_name character varying, in_file_order_id integer, in_is_created_back_file boolean, in_back_file_name character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 
	INSERT INTO psl_ppl_files_history(
            order_id, file_name, file_order_id, is_created_back_file, back_file_name)
	VALUES (in_order_id, in_file_name, in_file_order_id, in_is_created_back_file, in_back_file_name);

END; $$;


--
-- TOC entry 189 (class 1255 OID 18452)
-- Dependencies: 662 3
-- Name: cass_edit(integer, numeric, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION cass_edit(in_orders_id integer, in_actual_price numeric, in_description character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  --RAISE EXCEPTION '';

  UPDATE items
   SET  actual_price=in_actual_price
  WHERE orders_id=in_orders_id;

  UPDATE orders
   SET description=in_description
  WHERE id = in_orders_id;
  
END; $$;


--
-- TOC entry 190 (class 1255 OID 18453)
-- Dependencies: 3 662
-- Name: cass_select(date, date); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION cass_select(OUT rc refcursor, OUT current_sum numeric, OUT total_sum numeric, bdate date, edate date) RETURNS record
    LANGUAGE plpgsql
    AS $$ DECLARE --rc refcursor; 
BEGIN 

  --RAISE EXCEPTION '';

  OPEN rc FOR 
	SELECT DISTINCT a.id,a.ship_date,a.order_date,a.operation_type_code,b.case_many_summ,a.oot_description,a.description
		FROM v_select_cass a ,( SELECT id,SUM(case_many) as case_many_summ from v_select_cass WHERE date(order_date) BETWEEN bdate and edate GROUP BY id ) b
	WHERE a.id = b.id AND date(a.order_date) BETWEEN bdate and edate;

  -- --------------------------------------------------------
  current_sum := get_cass_balance_date_interval(bdate,edate);
  total_sum := get_cass_balance();

  IF current_sum IS NULL THEN 
    current_sum := 0; 
  END IF;

  IF total_sum IS NULL THEN 
    total_sum := 0; 
  END IF;
  
  --RETURN rc; 

END; $$;


--
-- TOC entry 191 (class 1255 OID 18454)
-- Dependencies: 662 3
-- Name: check_cass_balance(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION check_cass_balance() RETURNS trigger
    LANGUAGE plpgsql
    AS $$ DECLARE
	summ NUMERIC;
BEGIN 

  summ := get_cass_balance();

  IF summ < 0 THEN 
     RAISE EXCEPTION 'Денег в кассе неможет быть меньше нуля!!! %',  summ;
  END IF;

  RETURN NEW;
END; $$;


--
-- TOC entry 192 (class 1255 OID 18455)
-- Dependencies: 3 662
-- Name: check_product_balance(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION check_product_balance() RETURNS trigger
    LANGUAGE plpgsql
    AS $$ DECLARE
	
BEGIN 
	
  IF NEW.products_id IS NULL THEN
	RETURN NEW;
  ELSE 
	IF 0 > select_products_quantity_for_id(NEW.products_id) THEN
		RAISE EXCEPTION 'Количество товара в наличии неможет быть меньше нуля!!!';
	END IF;
  END IF;

  RETURN NEW;
END; $$;


--
-- TOC entry 193 (class 1255 OID 18456)
-- Dependencies: 662 3
-- Name: create_item(integer, integer, numeric, numeric, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION create_item(t_order_id integer, t_product_id integer, t_summa numeric, t_quantity numeric, t_description character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE 
  products_quantity_for_id items.quantity%Type := 0;
  t_operation_type_code orders.operation_type_code%TYPE := null;
BEGIN 

  SELECT o.operation_type_code INTO t_operation_type_code FROM orders o 
    WHERE o.id = t_order_id;

  IF t_operation_type_code IN ('psl') AND t_product_id IS NOT NULL THEN 
    products_quantity_for_id = select_products_quantity_for_id(t_product_id);

    IF  products_quantity_for_id < t_quantity THEN 
      RAISE EXCEPTION 'В данный момент уже не имеется такого колличества товара';
    END IF;
  END IF;

  INSERT INTO items( orders_id, products_id, actual_price, quantity)
      VALUES (t_order_id, t_product_id, t_summa, t_quantity);

  return currval('items_id_seq');

END; $$;


--
-- TOC entry 194 (class 1255 OID 18457)
-- Dependencies: 3 662
-- Name: create_order(character varying, character varying, integer, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION create_order(t_operation_type_code character varying, t_description character varying, t_sub_order_id integer, t_user_id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE 
  order_id integer;
  t_ship_date timestamp;
  t_order_date timestamp;
BEGIN 

  t_ship_date := now();
  t_order_date := t_ship_date;

  INSERT INTO orders(
            ship_date, order_date, operation_type_code, description, sub_order_id, user_id)
    VALUES (t_ship_date, t_order_date, t_operation_type_code, t_description, t_sub_order_id, t_user_id);

  return currval('orders_id_seq'); 

  --INSERT INTO items( orders_id, products_id, actual_price, quantity)
  --  VALUES (id, null, summa, 1);

END; $$;


--
-- TOC entry 195 (class 1255 OID 18458)
-- Dependencies: 3 662
-- Name: create_order(character varying, character varying, integer, integer, integer, boolean); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION create_order(t_operation_type_code character varying, t_description character varying, t_sub_order_id integer, t_user_id integer, t_customer_id integer, t_credit boolean) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE 
  order_id integer;
  t_ship_date timestamp;
  t_order_date timestamp;
BEGIN 

  t_ship_date := now();
  t_order_date := t_ship_date;

  INSERT INTO orders(
            ship_date, order_date, operation_type_code, description, sub_order_id, user_id, customer_id, credit)
    VALUES (t_ship_date, t_order_date, t_operation_type_code, t_description, t_sub_order_id, t_user_id, t_customer_id, t_credit);

  return currval('orders_id_seq'); 

  --INSERT INTO items( orders_id, products_id, actual_price, quantity)
  --  VALUES (id, null, summa, 1);

END; $$;


--
-- TOC entry 196 (class 1255 OID 18459)
-- Dependencies: 662 3
-- Name: create_psl_item(integer, integer, numeric, numeric, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION create_psl_item(t_order_id integer, t_products_id integer, t_summa numeric, t_quantity numeric, t_description character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE 

BEGIN 


  INSERT INTO items( orders_id, products_id, actual_price, quantity)
      VALUES (t_order_id, t_products_id, t_summa, t_quantity, t_description);

END; $$;


--
-- TOC entry 197 (class 1255 OID 18460)
-- Dependencies: 662 3
-- Name: customer_add_credit(integer, integer, numeric, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION customer_add_credit(in_order_id integer, in_customr_id integer, in_summ numeric, in_description character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
  
  
BEGIN 

	INSERT INTO customers_balances(
            order_id, customer_id, summ, description)
	VALUES (in_order_id, in_customr_id , in_summ, in_description);

END; $$;


--
-- TOC entry 198 (class 1255 OID 18461)
-- Dependencies: 662 3
-- Name: customer_add_money_to_balance(integer, numeric, character varying, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION customer_add_money_to_balance(in_customr_id integer, in_summ numeric, in_description character varying, in_user_id integer) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
  balance_summ numeric;	

  t_order_id integer;

  t_ship_date timestamp;
  t_order_date timestamp;
  t_operation_type_code character varying;

  r record;
  
BEGIN 

	IF in_summ >= 0 THEN
		t_operation_type_code := 'mpl';
		balance_summ := in_summ;
	ELSE
		t_operation_type_code := 'mmn';
		balance_summ := in_summ;
	END IF;

	select id into t_order_id from add_money_to_cass( t_operation_type_code , abs(in_summ) , in_description, in_user_id);
	

	INSERT INTO customers_balances(
            order_id, customer_id, summ, description)
	VALUES (t_order_id, in_customr_id , balance_summ, in_description);

END; $$;


--
-- TOC entry 199 (class 1255 OID 18462)
-- Dependencies: 3 662
-- Name: customer_balance(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION customer_balance(in_customer_id integer) RETURNS numeric
    LANGUAGE plpgsql
    AS $$ DECLARE --rc refcursor; 
  balance_sum numeric;
BEGIN 

  --RAISE EXCEPTION '';
  
	SELECT sum(summ) into balance_sum
		FROM customers_balances where customer_id = in_customer_id;
  

	IF balance_sum IS NULL THEN
		balance_sum := 0;
	END IF;
  
  RETURN balance_sum; 

END; $$;


--
-- TOC entry 200 (class 1255 OID 18463)
-- Dependencies: 3 662
-- Name: customer_balance_history(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION customer_balance_history(in_customer_id integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE 
	rc refcursor; 
	t_balance numeric;
BEGIN 

  --RAISE EXCEPTION '';

  t_balance := customer_balance(in_customer_id);

  OPEN rc FOR 
	SELECT cb.id, cb.order_id, o.order_date, cb.summ, t_balance,cb.description
	FROM customers_balances  cb, orders o
	WHERE cb.customer_id = in_customer_id and cb.order_id = o.id ;

  RETURN rc; 

END; $$;


--
-- TOC entry 201 (class 1255 OID 18464)
-- Dependencies: 3 662
-- Name: find_and_show_selling_order(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION find_and_show_selling_order(t_order_id integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  --RAISE EXCEPTION '';

  OPEN rc FOR 
   SELECT i.products_id,
        p.products_groups_id,
        
        p."name" as product_name,
        p.description,
        p.scod,
         
        q.quantity,
        p.measures_id, 
        m."name" as quantityName,
        i.actual_price,
        --trunc(i.quantity * i.actual_price,2) as actual_price_for_all,
        (q.summ - q.quantity) as returned,
        case when(m.mtype IS TRUE) then 0.01 else 1 end AS step
        
    FROM orders o, items i, products p, rb_measures m,
      
      (SELECT  i2.products_id, max(i2.quantity) as quantity, sum(i2.quantity) as summ FROM items i2, orders o2
        WHERE i2.orders_id = o2.id AND (o2.id = t_order_id OR o2.sub_order_id = t_order_id)
        GROUP BY products_id) as q

    WHERE o.id = t_order_id 
      AND o.id = i.orders_id
      AND i.products_id = p.id
      AND p.measures_id = m.id
      AND i.products_id = q.products_id;
    
  RETURN rc; 

END; $$;


--
-- TOC entry 202 (class 1255 OID 18465)
-- Dependencies: 3 662
-- Name: find_product_for_scod(character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION find_product_for_scod(t_scod character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT p.id, p.products_groups_id, p."name", p.description, p.scod, p.quantity, 
		p.measures_id, p.list_price, p.spec_price, p.percent_discount, rbm.mtype
	FROM products p, rb_measures rbm
	WHERE trim(p.scod)=trim(p.t_scod) and p.measures_id=rbm.id;
  RETURN rc; 

END; $$;


--
-- TOC entry 203 (class 1255 OID 18466)
-- Dependencies: 3 662
-- Name: find_product_for_scod_2(character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION find_product_for_scod_2(t_scod character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
  priduct_id integer := null;
  products_quantity NUMERIC(10,3) := 0;
BEGIN 

  select id into priduct_id from products where trim(scod)=trim(t_scod);
  products_quantity := select_products_quantity_for_id(priduct_id);

  IF products_quantity > 0 THEN 
    products_quantity:= 1;
  END IF;

  OPEN rc FOR 
    SELECT p.id,
           p.products_groups_id,
            p."name" as product_name, 
            p.description, 
            p.scod,
            products_quantity as quantity,
            p.measures_id, 
            m."name" as measure_name,
            p.list_price, 
            p.spec_price, 
            trunc(p.percent_discount * 100,2) as percent_discount,
            case when(m.mtype IS TRUE) then 0.01 else 1 end AS step,
            m.mtype
            
    FROM products p, rb_measures m
    WHERE p.id=priduct_id 
          AND p.measures_id = m.id;
          --AND select_products_quantity_for_id(p.id) > 0;

  RETURN rc; 

END; $$;


--
-- TOC entry 204 (class 1255 OID 18467)
-- Dependencies: 662 3
-- Name: find_product_for_scod_with_quantity(character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION find_product_for_scod_with_quantity(t_scod character varying, OUT out_rc refcursor, OUT out_quantity numeric, OUT out_step numeric) RETURNS record
    LANGUAGE plpgsql
    AS $$ DECLARE 
  priduct_id integer := null;
  product_measures_type boolean;
BEGIN 

  select id into priduct_id from products where trim(scod)=trim(t_scod);

  OPEN out_rc FOR 
    SELECT id, products_groups_id, "name", description, scod, quantity, 
		measures_id, list_price, spec_price, percent_discount
	FROM products WHERE id=priduct_id;

  out_quantity := select_products_quantity_for_id(priduct_id);
	
  SELECT rb.mtype INTO product_measures_type 
    FROM rb_measures rb, products p 
    WHERE p.id=priduct_id AND p.measures_id=rb.id;

  IF product_measures_type IS TRUE THEN
    out_step := 0.01;
  ELSE
    out_step := 1;
  END IF;

END; $$;


--
-- TOC entry 205 (class 1255 OID 18468)
-- Dependencies: 3 662
-- Name: find_product_group_id_for_product_scod(character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION find_product_group_id_for_product_scod(t_scod character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE t_product_group_id integer; 
BEGIN 

	SELECT products_groups_id into t_product_group_id
		FROM products WHERE scod=trim(t_scod);

  
	IF t_product_group_id IS NOT NULL THEN
		RETURN t_product_group_id; 
	ELSE 
		RETURN -1;
	END IF;

END; $$;


--
-- TOC entry 206 (class 1255 OID 18469)
-- Dependencies: 3 662
-- Name: find_product_id_for_scod(character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION find_product_id_for_scod(t_scod character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE t_product_id integer; 
BEGIN 

	SELECT id into t_product_id
		FROM products WHERE scod=trim(t_scod);

  
	IF t_product_id IS NOT NULL THEN
		RETURN t_product_id; 
	ELSE 
		RETURN -1;
	END IF;

END; $$;


--
-- TOC entry 207 (class 1255 OID 18470)
-- Dependencies: 3 662
-- Name: get_cass_balance(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION get_cass_balance() RETURNS numeric
    LANGUAGE plpgsql
    AS $$ DECLARE --rc refcursor; 
  total_sum numeric;
BEGIN 

  --RAISE EXCEPTION '';
  SELECT SUM(case_many) into total_sum FROM v_select_cass_after_last_z;

 
  IF total_sum IS NULL THEN 
    total_sum := 0; 
  END IF;
  
  RETURN total_sum; 

END; $$;


--
-- TOC entry 208 (class 1255 OID 18471)
-- Dependencies: 3 662
-- Name: get_cass_balance_date_interval(date, date); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION get_cass_balance_date_interval(bdate date, edate date) RETURNS numeric
    LANGUAGE plpgsql
    AS $$ DECLARE --rc refcursor; 
  total_sum numeric;
BEGIN 

	--RAISE EXCEPTION '';
	SELECT SUM(case_many) into total_sum FROM v_select_cass
	WHERE date(order_date) BETWEEN bdate and edate;

 
  IF total_sum IS NULL THEN 
    total_sum := 0; 
  END IF;
  
  RETURN total_sum; 

END; $$;


--
-- TOC entry 209 (class 1255 OID 18472)
-- Dependencies: 3 662
-- Name: get_db_parametr(character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION get_db_parametr(in_pkey character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
	SELECT * FROM db_params WHERE pkey=in_pkey;
  RETURN rc; 

END; $$;


--
-- TOC entry 210 (class 1255 OID 18473)
-- Dependencies: 3 662
-- Name: get_group_path_n(numeric); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION get_group_path_n(in_id numeric) RETURNS character varying
    LANGUAGE plpgsql
    AS $$ DECLARE 
	t_name character varying;
BEGIN
	select name into t_name from products_groups where id=in_id;

	RETURN get_group_to_sub_path_n(in_id)|| t_name;
	
END; $$;


--
-- TOC entry 211 (class 1255 OID 18474)
-- Dependencies: 3 662
-- Name: get_group_to_sub_path_n(numeric); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION get_group_to_sub_path_n(in_id numeric) RETURNS character varying
    LANGUAGE plpgsql
    AS $$ DECLARE 
	t_sub_id numeric;
	t_name character varying;
BEGIN 

	select sub_id into t_sub_id  from products_groups where id=in_id;

	IF t_sub_id IS NULL THEN
		RETURN '';
	ELSE
		select name into t_name from products_groups where id=t_sub_id;
	
		RETURN get_group_to_sub_path_n(t_sub_id)|| t_name || '/';
	END IF;

END; $$;


--
-- TOC entry 212 (class 1255 OID 18475)
-- Dependencies: 3 662
-- Name: get_last_z_report_id(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION get_last_z_report_id() RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE 
  t_id integer;
BEGIN 
	SELECT max(id) INTO t_id FROM orders 
			WHERE operation_type_code = 'z';

	IF t_id IS NULL THEN
		t_id := 0;
	END IF;

	RETURN t_id;

END; $$;


--
-- TOC entry 213 (class 1255 OID 18476)
-- Dependencies: 3 662
-- Name: get_ppl_files_from_history(character varying, numeric); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION get_ppl_files_from_history(in_file_name character varying, in_file_order_id numeric) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT * FROM ppl_files_history WHERE file_name=in_file_name OR file_order_id=in_file_order_id; 

  RETURN rc; 

END; $$;


--
-- TOC entry 214 (class 1255 OID 18477)
-- Dependencies: 3 662
-- Name: get_prev_z_for_z(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION get_prev_z_for_z(in_z_report_id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE 
  t_id integer;
BEGIN 
	SELECT max(id) INTO t_id FROM orders 
			WHERE operation_type_code = 'z' and id < in_z_report_id;

	IF t_id IS NULL THEN
		t_id := 0;
	END IF;

	RETURN t_id;

END; $$;


--
-- TOC entry 215 (class 1255 OID 18478)
-- Dependencies: 3 662
-- Name: get_prev_z_for_z_refcursor(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION get_prev_z_for_z_refcursor(in_z_report_id integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE 
  DECLARE rc refcursor; 
BEGIN 
	OPEN rc FOR 
		SELECT * FROM orders o WHERE o.id = get_prev_z_for_z(in_z_report_id);

	RETURN rc;

END; $$;


--
-- TOC entry 216 (class 1255 OID 18479)
-- Dependencies: 662 3
-- Name: get_product_path_n_scode(character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION get_product_path_n_scode(in_scod character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$ DECLARE 
	t_products_groups_id integer;
	t_name character varying;
	t_path character varying;
BEGIN

	select name,products_groups_id into t_name,t_products_groups_id from products where scod=in_scod;
	RETURN get_group_path_n(t_products_groups_id)|| '/> ' ||t_name ;
	
END; $$;


--
-- TOC entry 217 (class 1255 OID 18480)
-- Dependencies: 3 662
-- Name: info_about_item(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION info_about_item(in_order_id integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    select
	i.id,
	i.orders_id,

	i.products_id,
	p."name" as product_name,
	p.scod,
	p.measures_id,
	m."name" as measures_name,
	p.description,
	p.products_groups_id,
	pg."name" as products_groups_name,
	pg.description as products_groups_description,
	
	i.actual_price,
	i.quantity,
	i.actual_price * i.quantity AS summ
	

	from items i LEFT OUTER JOIN products p ON (i.products_id = p.id )
	     LEFT OUTER JOIN products_groups pg ON (p.products_groups_id = pg.id)
	     LEFT OUTER JOIN rb_measures m ON (p.measures_id = m.id)
	WHERE i.orders_id = in_order_id; 

  RETURN rc; 

END; $$;


--
-- TOC entry 218 (class 1255 OID 18481)
-- Dependencies: 3 662
-- Name: info_about_order(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION info_about_order(in_order_id integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    select 
	o.id,
	o.order_date,
	o.operation_type_code,
	ot.description as operation_name,

	o.description,
	o.sub_order_id,

	o.user_id,
	u."name" as user_name,
	u.isadmin,
	u.description as user_description,

	o.credit,
	(case when(o.credit is true) then 'да' else 'нет' end ) as credit_ru,
	
	o.customer_id,
	c.fio,
	c.address,
	c.phone_number,
	c.phone_number_2,
	c.email,
	c.description as customer_description,
	c.ur,
	(case when(c.ur is true) then 'да' else 'нет' end ) as ur_ru,
	c.short_name,
	c.doc_type,
	c.doc,
	c.sex,

	(select abs(sum(i.actual_price * i.quantity)) from items i WHERE i.orders_id = o.id) as to_cass,
	abs(cb.summ) as to_balance,
	cb.description as to_balance_description

	from orders o
		LEFT OUTER JOIN customers c ON (o.customer_id = c.id )
		LEFT OUTER JOIN customers_balances cb ON (o.id = cb.order_id)

		
		,orders_operations_types ot, users u
	where  o.id = in_order_id AND o.operation_type_code = type_code
		AND o.user_id = u.id;

  RETURN rc; 

END; $$;


--
-- TOC entry 219 (class 1255 OID 18482)
-- Dependencies: 3 662
-- Name: is_user_have_access(integer, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION is_user_have_access(in_user_id integer, in_access_kod character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$ DECLARE 
	t_is_accesseble integer := null;
BEGIN 

	SELECT 1 into t_is_accesseble FROM user_access ua, appaccess ap
		WHERE ua.user_id = in_user_id AND ua.access_id = ap.id AND ap.kod = in_access_kod;

	IF t_is_accesseble IS NOT NULL THEN 
		RETURN true;
	ELSE 
		RETURN false;
	END IF; 

END; $$;


--
-- TOC entry 220 (class 1255 OID 18483)
-- Dependencies: 3 662
-- Name: make_z_report(character varying, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION make_z_report(OUT id integer, OUT t_ship_date timestamp without time zone, OUT t_order_date timestamp without time zone, OUT summa numeric, t_description character varying, t_user_id integer) RETURNS record
    LANGUAGE plpgsql
    AS $$ DECLARE 
  id numeric;
BEGIN 

  t_ship_date := now();
  t_order_date := t_ship_date;
  summa := get_cass_balance();

  INSERT INTO orders(
            ship_date, order_date, operation_type_code, description, user_id)
    VALUES (t_ship_date, t_order_date, 'z', t_description, t_user_id);

  id := currval('orders_id_seq'); 
	
  INSERT INTO items( orders_id, products_id, actual_price, quantity)
    VALUES (id, null, summa, 1);

END; $$;


--
-- TOC entry 221 (class 1255 OID 18484)
-- Dependencies: 3 662
-- Name: probe(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION probe(OUT out_y integer, OUT out_date timestamp without time zone, in_x integer) RETURNS record
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  out_date := now();

  out_y := in_x * in_x;
  
END; $$;


--
-- TOC entry 222 (class 1255 OID 18485)
-- Dependencies: 662 3
-- Name: product_mov(integer, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION product_mov(product_id integer, group_id integer) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

	UPDATE products
		SET  products_groups_id=group_id
	WHERE id=product_id;

END; $$;


--
-- TOC entry 223 (class 1255 OID 18486)
-- Dependencies: 3 662
-- Name: products_delete(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION products_delete(t_id integer) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  DELETE FROM products 
    WHERE id = t_id; 

END; $$;


--
-- TOC entry 224 (class 1255 OID 18487)
-- Dependencies: 3 662
-- Name: products_group_mov(integer, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION products_group_mov(in_id integer, in_sub_id integer) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
	probe integer := 0;
BEGIN 
	UPDATE products_groups
		SET sub_id=in_sub_id
	WHERE id=in_id;
END; $$;


--
-- TOC entry 225 (class 1255 OID 18488)
-- Dependencies: 662 3
-- Name: products_groups_delete(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION products_groups_delete(t_id integer) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  DELETE FROM products_groups 
    WHERE id = t_id; 

END ; $$;


--
-- TOC entry 226 (class 1255 OID 18489)
-- Dependencies: 662 3
-- Name: products_groups_insert(integer, character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION products_groups_insert(t_sub_id integer, t_name character varying, t_description character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE
	probe integer := 0;
BEGIN

  IF  t_sub_id IS null THEN 
	SELECT 1 INTO probe FROM products_groups
		WHERE (sub_id IS null AND NAME = t_name);
  ELSE
	SELECT 1 INTO probe FROM products_groups
		WHERE (sub_id = t_sub_id AND NAME = t_name);
  END IF;
  
  IF probe = 1 THEN 
	RAISE EXCEPTION 'Ограничение уникальности значения' ;
  END IF;

  INSERT 
    INTO products_groups 
    ( sub_id, NAME, description )
  VALUES (t_sub_id, t_name, t_description ); 

  RETURN currval('products_groups_id_seq'); 

END; $$;


--
-- TOC entry 227 (class 1255 OID 18490)
-- Dependencies: 3 662
-- Name: products_groups_select(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION products_groups_select() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT id, sub_id, NAME, description 
      FROM products_groups  ORDER BY NAME;

  RETURN rc; 

END; $$;


--
-- TOC entry 228 (class 1255 OID 18491)
-- Dependencies: 3 662
-- Name: products_groups_select_cb1(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION products_groups_select_cb1() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT id, NAME , description
      FROM products_groups  ORDER BY sub_id,name;

  RETURN rc; 

END; $$;


--
-- TOC entry 229 (class 1255 OID 18492)
-- Dependencies: 3 662
-- Name: products_groups_update(integer, integer, character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION products_groups_update(t_id integer, t_sub_id integer, t_name character varying, t_description character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
	probe integer := 0;
BEGIN 
  IF  t_sub_id IS null THEN 
	SELECT 1 INTO probe FROM products_groups
		WHERE (sub_id IS null AND NAME = t_name AND id != t_id);
  ELSE
	SELECT 1 INTO probe FROM products_groups
		WHERE (sub_id = t_sub_id AND NAME = t_name AND id != t_id);
  END IF;
  
  IF probe = 1 THEN 
	RAISE EXCEPTION 'Ограничение уникальности значения' ;
  END IF;

  UPDATE products_groups 
      SET sub_id=t_sub_id, NAME=t_name, description=t_description 
    WHERE id = t_id; 

 

END; $$;


--
-- TOC entry 230 (class 1255 OID 18493)
-- Dependencies: 3 662
-- Name: products_insert(integer, character varying, character varying, character varying, numeric, integer, numeric, numeric, numeric); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION products_insert(t_products_groups_id integer, t_name character varying, t_description character varying, t_scod character varying, t_quantity numeric, t_measures_id integer, t_list_price numeric, t_spec_price numeric, t_percent_discount numeric) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  INSERT INTO products(
            products_groups_id, name, description, scod, quantity, measures_id, 
            list_price, spec_price, percent_discount)
    VALUES (t_products_groups_id, t_name, t_description, trim(t_scod), t_quantity, t_measures_id, 
            t_list_price, t_spec_price, t_percent_discount);


  RETURN currval('products_id_seq'); 

END; $$;


--
-- TOC entry 231 (class 1255 OID 18494)
-- Dependencies: 3 662
-- Name: products_insert(integer, character varying, character varying, character varying, integer, numeric, numeric, numeric); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION products_insert(t_products_groups_id integer, t_name character varying, t_description character varying, t_scod character varying, t_measures_id integer, t_list_price numeric, t_spec_price numeric, t_percent_discount numeric) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  INSERT INTO products(
            products_groups_id, name, description, scod, measures_id, 
            list_price, spec_price, percent_discount)
    VALUES (t_products_groups_id, t_name, t_description, trim(t_scod), t_measures_id, 
            t_list_price, t_spec_price, t_percent_discount);


  RETURN currval('products_id_seq'); 

END; $$;


--
-- TOC entry 232 (class 1255 OID 18495)
-- Dependencies: 3 662
-- Name: products_select(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION products_select() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT id, products_groups_id, "name", description, scod, quantity, 
		measures_id, list_price, spec_price, percent_discount,
		trunc((case when (spec_price = 0 AND percent_discount=0) THEN list_price
			when (spec_price > 0 AND percent_discount=0) THEN spec_price
			when (spec_price = 0 AND percent_discount>0) THEN (list_price - (list_price)*percent_discount)
			end),2) as total_price
	FROM products;
  RETURN rc; 

END; $$;


--
-- TOC entry 233 (class 1255 OID 18496)
-- Dependencies: 3 662
-- Name: products_select_by_products_groups_id(numeric); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION products_select_by_products_groups_id(t_products_groups_id numeric) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT p.id, p."name", p.description, p.scod,
      
     q.quantity as quantity,
    
		p.measures_id, m."name",p.list_price, p.spec_price, trunc(p.percent_discount * 100,2) as percent_discount,
		trunc((case when (p.spec_price = 0 AND p.percent_discount=0) THEN p.list_price
			when (p.spec_price > 0 AND p.percent_discount=0) THEN p.spec_price
			when (p.spec_price = 0 AND p.percent_discount>0) THEN (p.list_price - (p.list_price)*p.percent_discount)
			end),2) as spec_price_for_unit, 
		trunc((case when (p.spec_price = 0 AND p.percent_discount=0) THEN p.list_price
			when (p.spec_price > 0 AND p.percent_discount=0) THEN p.spec_price
			when (p.spec_price = 0 AND p.percent_discount>0) THEN (p.list_price - (p.list_price)*p.percent_discount)
			end) * q.quantity,2) as total_price
	FROM  products p, 
        rb_measures m, 
        (
		SELECT p.id, 
			CASE
				WHEN q.sum IS NULL THEN 0::numeric
				ELSE q.sum
			END AS quantity
		FROM products p
		LEFT JOIN ( SELECT i.products_id AS id, sum(
				CASE
				WHEN o.operation_type_code::text = ANY (ARRAY['pmn'::character varying::text, 'psl'::character varying::text]) THEN - i.quantity
				ELSE i.quantity
			END) AS sum
		FROM orders o, items i, products pp
		WHERE o.id = i.orders_id 
		AND (o.operation_type_code::text = ANY (ARRAY['pmn'::character varying::text, 'psl'::character varying::text, 'prt'::character varying::text, 'ppl'::character varying::text])) 
		AND i.products_id IS NOT NULL AND i.products_id = pp.id and pp.products_groups_id = t_products_groups_id
		GROUP BY i.products_id) q ON q.id = p.id

        ) q
        
	WHERE products_groups_id = t_products_groups_id 
        AND p.measures_id = m.id
        AND p.id = q.id
	ORDER BY p.id;
  RETURN rc; 

END; $$;


--
-- TOC entry 234 (class 1255 OID 18497)
-- Dependencies: 3 662
-- Name: products_update(integer, integer, character varying, character varying, character varying, numeric, integer, numeric, numeric, numeric); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION products_update(t_id integer, t_products_groups_id integer, t_name character varying, t_description character varying, t_scod character varying, t_quantity numeric, t_measures_id integer, t_list_price numeric, t_spec_price numeric, t_percent_discount numeric) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  UPDATE products
   SET 	products_groups_id=t_products_groups_id, 
				name=t_name, 
				description=t_description, 
				scod=trim(t_scod), 
				quantity=t_quantity, 
				measures_id=t_measures_id, 
				list_price=t_list_price, 
				spec_price=t_spec_price, 
				percent_discount=t_percent_discount
	WHERE id = t_id;

  RETURN; 

END; $$;


--
-- TOC entry 235 (class 1255 OID 18498)
-- Dependencies: 3 662
-- Name: products_update(integer, integer, character varying, character varying, character varying, integer, numeric, numeric, numeric); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION products_update(t_id integer, t_products_groups_id integer, t_name character varying, t_description character varying, t_scod character varying, t_measures_id integer, t_list_price numeric, t_spec_price numeric, t_percent_discount numeric) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  UPDATE products
   SET 	products_groups_id=t_products_groups_id, 
				name=t_name, 
				description=t_description, 
				scod=trim(t_scod), 
				measures_id=t_measures_id, 
				list_price=t_list_price, 
				spec_price=t_spec_price, 
				percent_discount=t_percent_discount
	WHERE id = t_id;

  RETURN; 

END; $$;


--
-- TOC entry 236 (class 1255 OID 18499)
-- Dependencies: 3 662
-- Name: rb_customers_delete(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_customers_delete(in_id integer) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

	DELETE FROM customers WHERE id=in_id;
  
END; $$;


--
-- TOC entry 237 (class 1255 OID 18500)
-- Dependencies: 3 662
-- Name: rb_customers_insert(character varying, character varying, character varying, character varying, character varying, character varying, boolean, character varying, integer, character varying, numeric, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_customers_insert(in_fio character varying, in_address character varying, in_phone_number character varying, in_phone_number_2 character varying, in_email character varying, in_description character varying, in_ur boolean, in_short_name character varying, in_doc_type integer, in_doc character varying, in_sex numeric, in_bin character varying, in_rnn character varying, in_rsh character varying, in_dc1 character varying, in_dc2 character varying, in_dc3 character varying, in_dc4 character varying, in_dc5 character varying, in_dc6 character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  INSERT INTO customers(
            fio, address, phone_number, phone_number_2, email, description, 
            ur, short_name, doc_type, doc, sex, bin, rnn, rsh, dc1, dc2, dc3, dc4, dc5, dc6)
    VALUES (in_fio, in_address, in_phone_number, in_phone_number_2, in_email, in_description, 
            in_ur, in_short_name, in_doc_type, in_doc, in_sex, in_bin, in_rnn, in_rsh, in_dc1, in_dc2, in_dc3, in_dc4, in_dc5, in_dc6);

  RETURN currval('customers_id_seq'); 

END; $$;


--
-- TOC entry 238 (class 1255 OID 18501)
-- Dependencies: 3 662
-- Name: rb_customers_select(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_customers_select() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT c.id, 
	c.ur, 
	c.doc, 
	
	c.doc_type,
	rdt."name" as doc_type_name, 
	
	c.short_name,
	c.fio, 
	
	c.sex,
	--CASE
        --    WHEN c.sex=0 THEN 'мужской'
        --    ELSE 'женский'
        --END as sex_name,
	
	c.address, 
	c.phone_number, 
	c.phone_number_2, 
	c.email, 
	c.description,
	customer_balance(c.id) as balance,
	c.bin,c.rnn,c.rsh,c.dc1,c.dc2,c.dc3,c.dc4,c.dc5,c.dc6
    FROM customers c, rb_doc_types rdt
    WHERE c.doc_type=rdt.id;

  RETURN rc; 

END; $$;


--
-- TOC entry 240 (class 1255 OID 18502)
-- Dependencies: 3 662
-- Name: rb_customers_select_for_doc(character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_customers_select_for_doc(in_doc character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT c.id, 
	c.ur, 
	c.doc, 
	
	c.doc_type,
	rdt."name" as doc_type_name, 
	
	c.short_name,
	c.fio, 
	
	c.sex,
	--CASE
        --    WHEN c.sex=0 THEN 'мужской'
        --    ELSE 'женский'
        --END as sex_name,
	
	c.address, 
	c.phone_number, 
	c.phone_number_2, 
	c.email, 
	c.description,
	customer_balance(c.id) as balance,
	c.bin,c.rnn,c.rsh,c.dc1,c.dc2,c.dc3,c.dc4,c.dc5,c.dc6
    FROM customers c, rb_doc_types rdt
    WHERE c.doc_type=rdt.id AND c.doc=in_doc;

  RETURN rc; 

END; $$;


--
-- TOC entry 241 (class 1255 OID 18503)
-- Dependencies: 3 662
-- Name: rb_customers_select_for_fio(character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_customers_select_for_fio(in_fio character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT c.id, 
	c.ur, 
	c.doc, 
	
	c.doc_type,
	rdt."name" as doc_type_name, 
	
	c.short_name,
	c.fio, 
	
	c.sex,
	--CASE
        --    WHEN c.sex=0 THEN 'мужской'
        --    ELSE 'женский'
        --END as sex_name,
	
	c.address, 
	c.phone_number, 
	c.phone_number_2, 
	c.email, 
	c.description,
	customer_balance(c.id) as balance,
	c.bin,c.rnn,c.rsh,c.dc1,c.dc2,c.dc3,c.dc4,c.dc5,c.dc6
    FROM customers c, rb_doc_types rdt
    WHERE c.doc_type=rdt.id AND c.fio LIKE '%'||in_fio||'%';

  RETURN rc; 

END; $$;


--
-- TOC entry 242 (class 1255 OID 18504)
-- Dependencies: 3 662
-- Name: rb_customers_select_for_id(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_customers_select_for_id(in_id integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT c.id, 
	c.ur, 
	c.doc, 
	
	c.doc_type,
	rdt."name" as doc_type_name, 
	
	c.short_name,
	c.fio, 
	
	c.sex,
	--CASE
        --    WHEN c.sex=0 THEN 'мужской'
        --    ELSE 'женский'
        --END as sex_name,
	
	c.address, 
	c.phone_number, 
	c.phone_number_2, 
	c.email, 
	c.description,
	customer_balance(c.id) as balance,
	c.bin,c.rnn,c.rsh,c.dc1,c.dc2,c.dc3,c.dc4,c.dc5,c.dc6
    FROM customers c, rb_doc_types rdt
    WHERE c.doc_type=rdt.id AND c.id = in_id;

  RETURN rc; 

END; $$;


--
-- TOC entry 243 (class 1255 OID 18505)
-- Dependencies: 3 662
-- Name: rb_customers_select_for_order_id(integer, boolean); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_customers_select_for_order_id(in_order_id integer, in_select_deps boolean) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;
	t_customer_id integer;
BEGIN 

	SELECT customer_id INTO t_customer_id FROM orders WHERE id = in_order_id;

  OPEN rc FOR 
    SELECT c.id, 
	c.ur, 
	c.doc, 
	
	c.doc_type,
	rdt."name" as doc_type_name, 
	
	c.short_name,
	c.fio, 
	
	c.sex,
	--CASE
        --    WHEN c.sex=0 THEN 'мужской'
        --    ELSE 'женский'
        --END as sex_name,
	
	c.address, 
	c.phone_number, 
	c.phone_number_2, 
	c.email, 
	c.description,
	customer_balance(c.id) as balance
    FROM customers c, rb_doc_types rdt
    WHERE  c.id = t_customer_id
		AND (in_select_deps OR (NOT in_select_deps AND c.doc_type=rdt.id AND c.doc_type IS NOT NULL));

  RETURN rc; 

END; $$;


--
-- TOC entry 244 (class 1255 OID 18506)
-- Dependencies: 3 662
-- Name: rb_customers_select_for_short_name(character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_customers_select_for_short_name(in_short_name character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT c.id, 
	c.ur, 
	c.doc, 
	
	c.doc_type,
	rdt."name" as doc_type_name, 
	
	c.short_name,
	c.fio, 
	
	c.sex,
	--CASE
        --    WHEN c.sex=0 THEN 'мужской'
        --    ELSE 'женский'
        --END as sex_name,
	
	c.address, 
	c.phone_number, 
	c.phone_number_2, 
	c.email, 
	c.description,
	customer_balance(c.id) as balance,
	c.bin,c.rnn,c.rsh,c.dc1,c.dc2,c.dc3,c.dc4,c.dc5,c.dc6
    FROM customers c, rb_doc_types rdt
    WHERE c.doc_type=rdt.id AND c.short_name LIKE '%'||in_short_name||'%';

  RETURN rc; 

END; $$;


--
-- TOC entry 245 (class 1255 OID 18507)
-- Dependencies: 3 662
-- Name: rb_customers_update(integer, character varying, character varying, character varying, character varying, character varying, character varying, boolean, character varying, integer, character varying, numeric, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_customers_update(in_id integer, in_fio character varying, in_address character varying, in_phone_number character varying, in_phone_number_2 character varying, in_email character varying, in_description character varying, in_ur boolean, in_short_name character varying, in_doc_type integer, in_doc character varying, in_sex numeric, in_bin character varying, in_rnn character varying, in_rsh character varying, in_dc1 character varying, in_dc2 character varying, in_dc3 character varying, in_dc4 character varying, in_dc5 character varying, in_dc6 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

   UPDATE customers
   SET fio=in_fio, address=in_address, phone_number=in_phone_number, phone_number_2=in_phone_number_2, email=in_email, 
       description=in_description, ur=in_ur, short_name=in_short_name, doc_type=in_doc_type, doc=in_doc, sex=in_sex, bin=in_bin, rnn=in_rnn , rsh=in_rsh,
       dc1=in_dc1, dc2=in_dc2, dc3=in_dc3, dc4=in_dc4, dc5=in_dc5, dc6=in_dc6 
   WHERE id=in_id;


END; $$;


--
-- TOC entry 246 (class 1255 OID 18508)
-- Dependencies: 3 662
-- Name: rb_dep_customers_delete(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_dep_customers_delete(in_id integer) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN
	DELETE FROM customers WHERE id=in_id;
END; $$;


--
-- TOC entry 247 (class 1255 OID 18509)
-- Dependencies: 3 662
-- Name: rb_dep_customers_insert(character varying, character varying, character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_dep_customers_insert(in_short_name character varying, in_fio character varying, in_address character varying, in_phone_number character varying, in_phone_number_2 character varying, in_email character varying, in_description character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  INSERT INTO customers(short_name, fio, address, phone_number, phone_number_2, email, description, dep)
    VALUES (in_short_name,in_fio, in_address, in_phone_number, in_phone_number_2, in_email, in_description, TRUE);

  RETURN currval('customers_id_seq'); 

END; $$;


--
-- TOC entry 248 (class 1255 OID 18510)
-- Dependencies: 3 662
-- Name: rb_dep_customers_select(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_dep_customers_select() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT c.id,
	c.short_name,
	c.fio, 
	c.address, 
	c.phone_number, 
	c.phone_number_2, 
	c.email, 
	c.description,
	customer_balance(c.id) as balance
    FROM customers c --, rb_doc_types rdt
    WHERE 
	--c.doc_type=rdt.id;
	c.dep IS TRUE;

  RETURN rc; 

END; $$;


--
-- TOC entry 249 (class 1255 OID 18511)
-- Dependencies: 3 662
-- Name: rb_dep_customers_select_for_fio(character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_dep_customers_select_for_fio(in_fio character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT c.id, 
	c.short_name,
	c.fio, 
	c.address, 
	c.phone_number, 
	c.phone_number_2, 
	c.email, 
	c.description,
	customer_balance(c.id) as balance
    FROM customers c --, rb_doc_types rdt
    WHERE 
	--c.doc_type=rdt.id AND 
	c.dep IS TRUE AND
	c.fio LIKE '%'||in_fio||'%';

  RETURN rc; 

END; $$;


--
-- TOC entry 250 (class 1255 OID 18512)
-- Dependencies: 3 662
-- Name: rb_dep_customers_select_for_short_name(character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_dep_customers_select_for_short_name(in_short_name character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT c.id, 
	c.short_name,
	c.fio, 
	c.address, 
	c.phone_number, 
	c.phone_number_2, 
	c.email, 
	c.description,
	customer_balance(c.id) as balance
    FROM customers c --, rb_doc_types rdt
    WHERE 
	--c.doc_type=rdt.id AND 
	c.dep IS TRUE AND
	c.short_name LIKE '%'||in_short_name||'%';

  RETURN rc; 

END; $$;


--
-- TOC entry 252 (class 1255 OID 18513)
-- Dependencies: 3 662
-- Name: rb_dep_customers_update(integer, character varying, character varying, character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_dep_customers_update(in_id integer, in_short_name character varying, in_fio character varying, in_address character varying, in_phone_number character varying, in_phone_number_2 character varying, in_email character varying, in_description character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

   UPDATE customers
   SET fio=in_fio, short_name=in_short_name, 
	address=in_address, phone_number=in_phone_number, phone_number_2=in_phone_number_2, email=in_email, 
       description=in_description
   WHERE id=in_id;


END; $$;


--
-- TOC entry 253 (class 1255 OID 18514)
-- Dependencies: 662 3
-- Name: rb_doc_types_delete(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_doc_types_delete(t_id integer) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  DELETE FROM rb_doc_types
    WHERE id = t_id; 


END; $$;


--
-- TOC entry 254 (class 1255 OID 18515)
-- Dependencies: 662 3
-- Name: rb_doc_types_insert(character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_doc_types_insert(t_name character varying, t_description character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  INSERT INTO rb_doc_types(
            name, description)
    VALUES (t_name, t_description);

  RETURN currval('rb_doc_types_id_seq'); 

END; $$;


--
-- TOC entry 255 (class 1255 OID 18516)
-- Dependencies: 3 662
-- Name: rb_doc_types_select(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_doc_types_select() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT id, name, description
      FROM rb_doc_types;

  RETURN rc; 

END; $$;


--
-- TOC entry 256 (class 1255 OID 18517)
-- Dependencies: 3 662
-- Name: rb_doc_types_update(integer, character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_doc_types_update(t_id integer, t_name character varying, t_description character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 
  
  UPDATE rb_doc_types
      SET name=t_name, description=t_description
    WHERE id = t_id; 

END; $$;


--
-- TOC entry 257 (class 1255 OID 18518)
-- Dependencies: 3 662
-- Name: rb_measures_delete(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_measures_delete(t_id integer) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  DELETE FROM rb_measures
    WHERE id = t_id; 


END; $$;


--
-- TOC entry 258 (class 1255 OID 18519)
-- Dependencies: 3 662
-- Name: rb_measures_insert(character varying, character varying, boolean); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_measures_insert(t_name character varying, t_description character varying, t_mtype boolean) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  INSERT INTO rb_measures(
            name, description,mtype)
    VALUES (t_name, t_description, t_mtype);

  RETURN currval('rb_measures_id_seq'); 

END; $$;


--
-- TOC entry 259 (class 1255 OID 18520)
-- Dependencies: 3 662
-- Name: rb_measures_select(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_measures_select() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT id, name, description, mtype
      FROM rb_measures;

  RETURN rc; 

END; $$;


--
-- TOC entry 260 (class 1255 OID 18521)
-- Dependencies: 3 662
-- Name: rb_measures_update(integer, character varying, character varying, boolean); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rb_measures_update(t_id integer, t_name character varying, t_description character varying, t_mtype boolean) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 
  
  UPDATE rb_measures
      SET name=t_name, description=t_description, mtype=t_mtype
    WHERE id = t_id; 

END; $$;


--
-- TOC entry 261 (class 1255 OID 18522)
-- Dependencies: 3 662
-- Name: receipts_of_the_product(boolean, integer, numeric, numeric, character varying, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION receipts_of_the_product(t_minus boolean, t_products_id integer, t_actual_price numeric, t_quantity numeric, t_description character varying, t_user_id integer) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
  id integer;
  t_ship_date timestamp;
  t_order_date timestamp;
  t_operation_type_code character varying(8);
  tt_quantity numeric;
BEGIN 

  t_ship_date := now();
  t_order_date := t_ship_date;

  IF t_minus IS true THEN
    t_operation_type_code := 'pmn';
    --tt_quantity := - t_quantity;
  ELSE
    t_operation_type_code := 'ppl';
    --tt_quantity := t_quantity;
  END IF;

  INSERT INTO orders(
            ship_date, order_date, operation_type_code, description, user_id)
    VALUES (t_ship_date, t_order_date, t_operation_type_code, t_description, t_user_id);

  id := currval('orders_id_seq'); 

  INSERT INTO items( orders_id, products_id, actual_price, quantity)
    VALUES (id, t_products_id, t_actual_price, t_quantity);

END; $$;


--
-- TOC entry 262 (class 1255 OID 18523)
-- Dependencies: 3 662
-- Name: receipts_of_the_product_as_gr(integer, character varying, numeric, numeric); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION receipts_of_the_product_as_gr(t_order_id integer, t_products_scode character varying, t_actual_price numeric, t_quantity numeric) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
	t_products_id integer := 0;
BEGIN 
	
	SELECT id INTO t_products_id FROM products WHERE scod = t_products_scode;

	INSERT INTO items( orders_id, products_id, actual_price, quantity)
		VALUES (t_order_id, t_products_id, t_actual_price, t_quantity);

END; $$;


--
-- TOC entry 263 (class 1255 OID 18524)
-- Dependencies: 3 662
-- Name: rpt_customer_history(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_customer_history(in_id integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;

BEGIN


  OPEN rc FOR
	SELECT 
	o.id, 
	o.order_date, 
	--o.operation_type_code,
	oot.description,
	--o.user_id, 
	--o.credit,
	(case when(o.credit is true) then 'да' else 'нет' end ) as credit_ru,

	cb1.to_balance,
	cb2.to_credit,
	
	p.name , 
	p.scod, 
	i.actual_price, 
	i.quantity,
	(i.actual_price * i.quantity) as psumm,
	i2.to_cass,
	i3.all_summ

	FROM orders o 
	LEFT OUTER JOIN(SELECT order_id, summ as to_balance FROM customers_balances
		WHERE customer_id = in_id AND summ > 0) cb1 ON (o.id = cb1.order_id )
	LEFT OUTER JOIN(SELECT order_id, summ as to_credit FROM customers_balances
		WHERE customer_id = in_id AND summ < 0) cb2 ON (o.id = cb2.order_id )

	, items i ,
	(SELECT i2.orders_id, sum(i2.actual_price * quantity) as to_cass FROM items i2 GROUP BY i2.orders_id) i2, 
	(SELECT i2.orders_id, sum(i2.actual_price * quantity) as all_summ FROM items i2 WHERE actual_price > 0 GROUP BY i2.orders_id) i3, 
	products p, orders_operations_types oot

	WHERE o.customer_id = in_id
		AND o.id = i.orders_id
		AND o.id = i2.orders_id 
		AND o.id = i3.orders_id 
		AND i.products_id=p.id
		AND o.operation_type_code = oot.type_code
	ORDER BY o.id;

  
RETURN rc;

END; $$;


--
-- TOC entry 264 (class 1255 OID 18525)
-- Dependencies: 3 662
-- Name: rpt_orders_history(date, date); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_orders_history(in_bdate date, in_edate date) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;
BEGIN

  OPEN rc FOR
	SELECT 	o.id, o.order_date, o.operation_type_code, ot.supertype,ot.description as operation_description,
		ppg.product_group_id , ppg.product_group_name,
		i.products_id as product_id, ppg.product_name, ppg.scod,
		i.actual_price, i.quantity, (i.actual_price * i.quantity) as summ, o.description,
		u.id as user_id, u."name" as user_name, u.description as user_description

	FROM orders o,  orders_operations_types ot,  users u, items i

	LEFT OUTER JOIN(SELECT 	pg.id as product_group_id , pg."name" as product_group_name,
				p.id as product_id, p."name" as product_name, p.scod
			FROM products p, products_groups pg
			WHERE p.products_groups_id = pg.id) ppg ON (i.products_id = ppg.product_id)


	WHERE	date(o.order_date) BETWEEN in_bdate and in_edate
		and o.id = i.orders_id
		and o.operation_type_code = ot.type_code
		and u.id = o.user_id
	ORDER BY id;


  RETURN rc;

END; $$;


--
-- TOC entry 265 (class 1255 OID 18526)
-- Dependencies: 3 662
-- Name: rpt_orders_history_m2(date, date); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_orders_history_m2(in_bdate date, in_edate date) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;
BEGIN

  OPEN rc FOR
select  -- Сведения по операции
o.id as oder_id,
o.order_date as order_date,
o.operation_type_code as operation_type_code,
ot.description as order_operation_name,

o.description as order_description,
o.sub_order_id as sub_order_id,

-- Сведения по пользователю
o.user_id as user_id,
u."name" as user_name,
u.isadmin as isadmin,
(case when(u.isadmin is true) then 'да' else 'нет' end ) as isadmin_ru,
u.description as user_description,

-- Сведения по проведенной операции
o.credit as credit,
(case when(o.credit is true) then 'да' else 'нет' end ) as credit_ru,
(select abs(sum(ii.actual_price * ii.quantity))
from items ii WHERE ii.orders_id = o.id) as to_cass,
(case when cb.summ is null then 0 else cb.summ end) as to_balance,
cb.description as to_balance_description,

-- Сведения по клиенту
o.customer_id,
----------
c.short_name,
c.doc_type,
c.doc,
c.ur,
(case when(c.ur is true) then 'да' else 'нет' end ) as ur_ru,
----------
c.fio,
c.sex,
c.address,
c.phone_number,
c.phone_number_2,
c.email,
c.description as customer_description,

-- Сведения по составу операции
-- Группа товаров
p.products_groups_id,
(case when (pg."name" is null) then 'сумма' else pg."name" end) as products_groups_name,
pg.description as products_groups_description,
-- Товар
i.products_id,
(case when (p."name" is null) then 'Касса' else p."name" end) as product_name,
p.scod,
p.measures_id,
ms."name" as measures_name,
p.description as product_description,

-- Оценка
i.actual_price,
i.quantity,
i.actual_price * i.quantity AS summ

from orders o
LEFT OUTER JOIN customers c ON (o.customer_id = c.id )
LEFT OUTER JOIN customers_balances cb ON (o.id = cb.order_id),

     items i
LEFT OUTER JOIN products p ON (i.products_id = p.id )
LEFT OUTER JOIN products_groups pg ON (p.products_groups_id = pg.id)
LEFT OUTER JOIN rb_measures ms ON (p.measures_id = ms.id)

,orders_operations_types ot, users u
where  o.order_date between in_bdate AND in_edate
--o.id = 396 -- ppl Приход товара
--o.id = 527 -- pmn Списание товара

--o.id = 563 --mpl
--o.id = 546 --mmn

--o.id = 539 -- z

--o.id = 562 --psl Продажа товара с кредитом
--or o.id = 567 --prt Возврат товара без балансных операций

AND o.id=i.orders_id AND o.operation_type_code = type_code
AND o.user_id = u.id --AND p.id is not null
AND i.actual_price >= 0
order by o.id;


  RETURN rc;

END; $$;


--
-- TOC entry 266 (class 1255 OID 18527)
-- Dependencies: 3 662
-- Name: rpt_orders_history_m2_for_id(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_orders_history_m2_for_id(in_id integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;
BEGIN

  OPEN rc FOR
select  -- Сведения по операции
o.id as oder_id,
o.order_date as order_date,
o.operation_type_code as operation_type_code,
ot.description as order_operation_name,

o.description as order_description,
o.sub_order_id as sub_order_id,

-- Сведения по пользователю
o.user_id as user_id,
u."name" as user_name,
u.isadmin as isadmin,
(case when(u.isadmin is true) then 'да' else 'нет' end ) as isadmin_ru,
u.description as user_description,

-- Сведения по проведенной операции
o.credit as credit,
(case when(o.credit is true) then 'да' else 'нет' end ) as credit_ru,
(select abs(sum(ii.actual_price * ii.quantity))
from items ii WHERE ii.orders_id = o.id) as to_cass,
(case when cb.summ is null then 0 else cb.summ end) as to_balance,
cb.description as to_balance_description,

-- Сведения по клиенту
o.customer_id,
----------
c.short_name,
c.doc_type,
c.doc,
c.ur,
(case when(c.ur is true) then 'да' else 'нет' end ) as ur_ru,
----------
c.fio,
c.sex,
c.address,
c.phone_number,
c.phone_number_2,
c.email,
c.description as customer_description,

-- Сведения по составу операции
-- Группа товаров
p.products_groups_id,
(case when (pg."name" is null) then 'сумма' else pg."name" end) as products_groups_name,
pg.description as products_groups_description,
-- Товар
i.products_id,
(case when (p."name" is null) then 'Касса' else p."name" end) as product_name,
p.scod,
p.measures_id,
ms."name" as measures_name,
p.description as product_description,

-- Оценка
i.actual_price,
i.quantity,
i.actual_price * i.quantity AS summ

from orders o
LEFT OUTER JOIN customers c ON (o.customer_id = c.id )
LEFT OUTER JOIN customers_balances cb ON (o.id = cb.order_id),

     items i
LEFT OUTER JOIN products p ON (i.products_id = p.id )
LEFT OUTER JOIN products_groups pg ON (p.products_groups_id = pg.id)
LEFT OUTER JOIN rb_measures ms ON (p.measures_id = ms.id)

,orders_operations_types ot, users u

where  o.id = in_id


AND o.id=i.orders_id AND o.operation_type_code = type_code
AND o.user_id = u.id --AND p.id is not null
AND i.actual_price >= 0
order by o.id;


  RETURN rc;

END; $$;


--
-- TOC entry 267 (class 1255 OID 18528)
-- Dependencies: 3 662
-- Name: rpt_ppl_for_date(date, date); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_ppl_for_date(in_bdate date, in_edate date) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;

BEGIN


  OPEN rc FOR
	SELECT o.id ,o.order_date, p.name , p.scod, i.actual_price, i.quantity
	FROM items i , orders o, products p
	WHERE o.order_date between in_bdate AND  in_edate 
	AND i.orders_id=o.id AND o.operation_type_code='ppl'
	AND i.products_id=p.id;

  
RETURN rc;

END; $$;


--
-- TOC entry 268 (class 1255 OID 18529)
-- Dependencies: 3 662
-- Name: rpt_prod_select_moving(date, date); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_prod_select_moving(bdate date, edate date) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 

select 
	pg.id as groups_id,
	pg."name" as groups_name,
	p.id as products_id,
	p."name" as products_name,
	p.scod as scod,
        p.list_price as list_price,
	
	ppl.quantity as ppl_quantity,
	ppl.summ as ppl_summ,

	pmn.quantity as pmn_quantity,
	pmn.summ as pmn_summ,
	
	psl.quantity as psl_quantity,
	psl.summ as psl_summ,

	prt.quantity as prt_quantity,
	prt.summ as prt_summ
	
	--from products_groups pg, products p	
	from products_groups pg, (select DISTINCT v.products_id as id , p."name", p.products_groups_id, p.scod as scod, p.list_price as list_price
				from v_operations v, products p
				where products_id is not null AND v.products_id=p.id AND date(order_date) BETWEEN bdate and edate) p

	LEFT OUTER JOIN(select operation_type_code,products_id,sum(quantity) as quantity,sum(actual_price * quantity) as summ
		from v_operations where operation_type_code='ppl' AND date(order_date) BETWEEN bdate and edate
		group by operation_type_code,products_id ) ppl ON (p.id=ppl.products_id)
		
	LEFT OUTER JOIN(select operation_type_code,products_id,sum(quantity) as quantity,sum(actual_price * quantity) as summ
		from v_operations where operation_type_code='pmn' AND date(order_date) BETWEEN bdate and edate
		group by operation_type_code,products_id ) pmn ON (p.id=pmn.products_id)

	LEFT OUTER JOIN(select operation_type_code,products_id,sum(quantity) as quantity,sum(actual_price * quantity) as summ
		from v_operations where operation_type_code='psl' AND date(order_date) BETWEEN bdate and edate
		group by operation_type_code,products_id ) psl ON (p.id=psl.products_id)

	LEFT OUTER JOIN(select operation_type_code,products_id,sum(quantity) as quantity,sum(actual_price * quantity) as summ
		from v_operations where operation_type_code='prt' AND date(order_date) BETWEEN bdate and edate
		group by operation_type_code,products_id ) prt ON (p.id=prt.products_id)
	
	
where pg.id = p.products_groups_id;
  -- --------------------------------------------------------
  
  RETURN rc; 

END; $$;


--
-- TOC entry 269 (class 1255 OID 18530)
-- Dependencies: 3 662
-- Name: rpt_prod_select_moving_2(date, date); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_prod_select_moving_2(bdate date, edate date) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
	select 
	pg.id as groups_id,
	pg."name" as groups_name,
	p.id as products_id,
	p."name" as products_name,
	
	ppl.quantity as ppl_quantity,
	ppl.summ as ppl_summ,

	pmn.quantity as pmn_quantity,
	pmn.summ as pmn_summ,
	
	psl.quantity as psl_quantity,
	psl.summ as psl_summ,

	prt.quantity as prt_quantity,
	prt.summ as prt_summ
	

	from products_groups pg, products p 

	LEFT OUTER JOIN(select operation_type_code,products_id,sum(quantity) as quantity,sum(actual_price * quantity) as summ
		from v_operations where operation_type_code='ppl' AND date(order_date) BETWEEN bdate and edate
		group by operation_type_code,products_id ) ppl ON (p.id=ppl.products_id)
		
	LEFT OUTER JOIN(select operation_type_code,products_id,sum(quantity) as quantity,sum(actual_price * quantity) as summ
		from v_operations where operation_type_code='pmn' AND date(order_date) BETWEEN bdate and edate
		group by operation_type_code,products_id ) pmn ON (p.id=pmn.products_id)

	LEFT OUTER JOIN(select operation_type_code,products_id,sum(quantity) as quantity,sum(actual_price * quantity) as summ
		from v_operations where operation_type_code='psl' AND date(order_date) BETWEEN bdate and edate
		group by operation_type_code,products_id ) psl ON (p.id=psl.products_id)

	LEFT OUTER JOIN(select operation_type_code,products_id,sum(quantity) as quantity,sum(actual_price * quantity) as summ
		from v_operations where operation_type_code='prt' AND date(order_date) BETWEEN bdate and edate
		group by operation_type_code,products_id ) prt ON (p.id=prt.products_id)
	
	
	where pg.id = p.products_groups_id 


	
order by pg.id,p.id;
  -- --------------------------------------------------------
  
  RETURN rc; 

END; $$;


--
-- TOC entry 270 (class 1255 OID 18531)
-- Dependencies: 3 662
-- Name: rpt_prod_select_moving_v2(date, date, boolean, boolean, boolean, boolean, boolean, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_prod_select_moving_v2(bdate date, edate date, ppl boolean, pmn boolean, psl boolean, prt boolean, for_user boolean, user_id integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

	OPEN rc FOR 
---------------------------------------------------------------------------------

SELECT
	o.operation_type_code, oot.description,
	pg.id as group_id, pg."name" as group_name, 
	o.id as order_id, o.order_date, o.description as order_description,
	o.user_id, u."name" as user_name,
	p."name" as product_name, p.scod, p.list_price, i.actual_price, i.quantity
FROM 
	orders o, items i, products_groups pg, products p, orders_operations_types oot, users u
WHERE
	date(o.order_date) BETWEEN bdate and edate
	AND
	--o.operation_type_code in ('psl','pmn')
	((o.operation_type_code = 'ppl' AND ppl)
	OR (o.operation_type_code = 'pmn' AND pmn)
	OR (o.operation_type_code = 'psl' AND psl)
	OR (o.operation_type_code = 'prt' AND prt))
	
	AND o.operation_type_code = oot.type_code
	AND o.id = i.orders_id  AND i.products_id=p.id AND p.products_groups_id = pg.id 
	AND u.id = o.user_id AND (NOT for_user OR (for_user AND o.user_id = user_id))
	
ORDER BY pg.id, o.operation_type_code , o.id;

----------------------------------------------------------------------------------	

  
	RETURN rc; 

END; $$;


--
-- TOC entry 271 (class 1255 OID 18532)
-- Dependencies: 3 662
-- Name: rpt_prod_select_quantitys(boolean, numeric, numeric, boolean, numeric, boolean, numeric, boolean, numeric); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_prod_select_quantitys(in_isbetween boolean, in_bbetween numeric, in_ebetween numeric, in_ismore boolean, in_more numeric, in_isless boolean, in_less numeric, in_iswell boolean, in_well numeric) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
	SELECT 	pg.id as pg_id, pg.sub_id as pg_sub_id, pg."name" as pg_name, pg.description as pg_description,
		p.id, p.products_groups_id, p."name", p.description, p.scod, vpq.quantity,
		p.measures_id, p.list_price, p.spec_price, p.percent_discount,
		m."name" as measure
	FROM v_products_quantity vpq, products p, products_groups pg, rb_measures m
	WHERE vpq.id = p.id AND pg.id = p.products_groups_id AND m.id = p.measures_id

		AND (NOT in_IsBetween OR (in_IsBetween AND vpq.quantity between in_bBetween and in_eBetween))
		AND (NOT in_IsMore OR (in_IsMore AND vpq.quantity > in_More))
		AND (NOT in_IsLess OR (in_IsLess AND vpq.quantity < in_Less))
		AND (NOT in_IsWell OR (in_IsWell AND vpq.quantity = in_Well))

	ORDER BY pg.id, pg.sub_id, p.id;

  
  RETURN rc; 

END; $$;


--
-- TOC entry 272 (class 1255 OID 18533)
-- Dependencies: 3 662
-- Name: rpt_psl_for_date(date, date); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_psl_for_date(in_bdate date, in_edate date) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;

BEGIN


  OPEN rc FOR
	SELECT 	o.id,
	o.order_date,
	--o.customer_id,
	c2.short_name,
	i.to_cass,
	i2.to_credit

	FROM orders o
	LEFT OUTER JOIN(SELECT i2.orders_id, i2.actual_price as to_credit FROM items i2
			WHERE i2.actual_price < 0) i2 ON (o.id = i2.orders_id )
	LEFT OUTER JOIN(SELECT c2.id, c2.short_name FROM customers c2) c2 ON (o.customer_id = c2.id )
	,(SELECT i1.orders_id, sum(i1.actual_price * i1.quantity) as to_cass FROM items i1 GROUP BY i1.orders_id) i
	WHERE o.id = i.orders_id
	AND o.order_date between in_bdate AND in_edate
	AND o.operation_type_code='psl'
	ORDER BY o.id;

  
RETURN rc;

END; $$;


--
-- TOC entry 273 (class 1255 OID 18534)
-- Dependencies: 3 662
-- Name: rpt_schet_factura_for_id(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_schet_factura_for_id(in_id integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;
BEGIN

  OPEN rc FOR
select  -- Сведения по операции
o.id as oder_id,
o.order_date as order_date,
o.operation_type_code as operation_type_code,
ot.description as order_operation_name,

o.description as order_description,
o.sub_order_id as sub_order_id,

-- Сведения по пользователю
o.user_id as user_id,
u."name" as user_name,
u.isadmin as isadmin,
(case when(u.isadmin is true) then 'да' else 'нет' end ) as isadmin_ru,
u.description as user_description,

-- Сведения по проведенной операции
o.credit as credit,
(case when(o.credit is true) then 'да' else 'нет' end ) as credit_ru,
(select abs(sum(ii.actual_price * ii.quantity))
from items ii WHERE ii.orders_id = o.id) as to_cass,
(case when cb.summ is null then 0 else cb.summ end) as to_balance,
cb.description as to_balance_description,

-- Сведения по клиенту
o.customer_id,
----------
c.short_name,
c.doc_type,
c.doc,
c.ur,
(case when(c.ur is true) then 'да' else 'нет' end ) as ur_ru,
----------
c.fio,
c.sex,
c.address,
c.phone_number,
c.phone_number_2,
c.email,
c.description as customer_description,
c.rnn,
c.bin,
c.rsh,

-- Сведения по составу операции
-- Группа товаров
p.products_groups_id,
(case when (pg."name" is null) then 'сумма' else pg."name" end) as products_groups_name,
pg.description as products_groups_description,
-- Товар
i.products_id,
(case when (p."name" is null) then 'Касса' else p."name" end) as product_name,
p.scod,
p.measures_id,
ms."name" as measures_name,
p.description as product_description,

-- Оценка
i.actual_price,
i.quantity,
i.actual_price * i.quantity AS summ

from orders o
LEFT OUTER JOIN customers c ON (o.customer_id = c.id )
LEFT OUTER JOIN customers_balances cb ON (o.id = cb.order_id),

     items i
LEFT OUTER JOIN products p ON (i.products_id = p.id )
LEFT OUTER JOIN products_groups pg ON (p.products_groups_id = pg.id)
LEFT OUTER JOIN rb_measures ms ON (p.measures_id = ms.id)

,orders_operations_types ot, users u

where  o.id = in_id


AND o.id=i.orders_id AND o.operation_type_code = type_code
AND o.user_id = u.id --AND p.id is not null
AND i.actual_price >= 0
order by o.id;


  RETURN rc;

END; $$;


--
-- TOC entry 274 (class 1255 OID 18535)
-- Dependencies: 3 662
-- Name: rpt_tov_obor_for_date_scod(date, date, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_tov_obor_for_date_scod(in_bdate date, in_edate date, in_scod character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;

BEGIN


  OPEN rc FOR
	SELECT  o.id , o.order_date,
	--select_products_quantity_for_id(i.products_id),
	ppl.quantity as ppl, pmn.quantity as pmn, psl.quantity as psl, prt.quantity as prt,
	select_products_quantity_for_id_to_order_id(i.products_id, o.id) as ost,
	--o.operation_type_code, 
	oot.description as op_name,
	o.description

	FROM orders o
	--------------------------------------
	LEFT OUTER JOIN(

	SELECT  o.id, sum(i.quantity) as quantity
	FROM orders o, items i , products p
	WHERE   
		i.orders_id=o.id AND o.operation_type_code = 'ppl'
		AND i.products_id=p.id 
		AND p.scod=in_scod
	GROUP BY o.id

	) ppl ON (o.id = ppl.id)
	--------------------------------------
	LEFT OUTER JOIN(

	SELECT  o.id, sum(i.quantity) as quantity
	FROM orders o, items i , products p
	WHERE   
		i.orders_id=o.id AND o.operation_type_code = 'pmn'
		AND i.products_id=p.id 
		AND p.scod=in_scod
	GROUP BY o.id

	) pmn ON (o.id = pmn.id)
	--------------------------------------
	LEFT OUTER JOIN(

	SELECT  o.id, sum(i.quantity) as quantity
	FROM orders o, items i , products p
	WHERE   
		i.orders_id=o.id AND o.operation_type_code = 'psl'
		AND i.products_id=p.id 
		AND p.scod=in_scod
	GROUP BY o.id

	) psl ON (o.id = psl.id)
	--------------------------------------
	LEFT OUTER JOIN(

	SELECT  o.id, sum(i.quantity) as quantity
	FROM orders o, items i , products p
	WHERE   
		i.orders_id=o.id AND o.operation_type_code = 'prt'
		AND i.products_id=p.id 
		AND p.scod=in_scod
	GROUP BY o.id

	) prt ON (o.id = prt.id)
	--------------------------------------
	, items i , products p , orders_operations_types oot
	WHERE   
		i.orders_id=o.id 
		AND o.order_date BETWEEN in_bdate AND in_edate
		AND i.products_id=p.id 
		AND p.scod=in_scod
		AND o.operation_type_code = oot.type_code
	ORDER BY ID;

  
RETURN rc;

END; $$;


--
-- TOC entry 275 (class 1255 OID 18536)
-- Dependencies: 3 662
-- Name: rpt_z(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_z(in_z_report_id integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;
preZ integer := 0;
BEGIN
preZ := get_prev_z_for_z(in_z_report_id);

  OPEN rc FOR
((select 0 as rang, 'Кассовые операции' as rang_descr,
o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
0 as product_group_id , 'Касса' as product_group_name,
0 as product_id, 'у.е.' as product_name, '' as scod,
i.actual_price, 1 as quantity, i.actual_price as summ, o.description

from orders o, items i, orders_operations_types ot
where o.id = i.orders_id and o.operation_type_code = ot.type_code
and o.id < in_z_report_id and o.id > preZ
and o.operation_type_code in ('mpl','mmn')
)

union

(select 1 as rang, 'Приход товара' as rang_descr,
o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
pg.id as product_group_id , pg."name" as product_group_name,
i.products_id as product_id, p."name" as product_name, p.scod,
i.actual_price, i.quantity, (i.actual_price * i.quantity) as summ, o.description

from orders o, items i, orders_operations_types ot, products p, products_groups pg
where o.id = i.orders_id and o.operation_type_code = ot.type_code
--and o.id < in_z_report_id and o.id > preZ
and o.id < in_z_report_id and o.id > preZ
and i.products_id = p.id
and p.products_groups_id = pg.id
and o.operation_type_code = 'ppl'
)

union

(select 2 as rang, 'Списание товара' as rang_descr,
o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
pg.id as product_group_id , pg."name" as product_group_name,
i.products_id as product_id, p."name" as product_name, p.scod,
i.actual_price, i.quantity, (i.actual_price * i.quantity) as summ, o.description

from orders o, items i, orders_operations_types ot, products p, products_groups pg
where o.id = i.orders_id and o.operation_type_code = ot.type_code
--and o.id < in_z_report_id and o.id > preZ
and o.id < in_z_report_id and o.id > preZ
and i.products_id = p.id
and p.products_groups_id = pg.id
and o.operation_type_code = 'pmn'
)

union

(select 3 as rang, 'Продажа товара' as rang_descr,
o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
pg.id as product_group_id , pg."name" as product_group_name,
i.products_id as product_id, p."name" as product_name, p.scod,
i.actual_price, i.quantity, (i.actual_price * i.quantity) as summ, o.description

from orders o, items i, orders_operations_types ot, products p, products_groups pg
where o.id = i.orders_id and o.operation_type_code = ot.type_code
--and o.id < in_z_report_id and o.id > preZ
and o.id < in_z_report_id and o.id > preZ
and i.products_id = p.id
and p.products_groups_id = pg.id
and o.operation_type_code = 'psl'
)

union

(select 4 as rang, 'Возврат товара' as rang_descr,
o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
pg.id as product_group_id , pg."name" as product_group_name,
i.products_id as product_id, p."name" as product_name, p.scod,
i.actual_price, i.quantity, (i.actual_price * i.quantity) as summ, o.description

from orders o, items i, orders_operations_types ot, products p, products_groups pg
where o.id = i.orders_id and o.operation_type_code = ot.type_code
--and o.id < in_z_report_id and o.id > preZ
and o.id < in_z_report_id and o.id > preZ
and i.products_id = p.id
and p.products_groups_id = pg.id
and o.operation_type_code = 'prt'
)) ORDER BY rang;

RETURN rc;

END; $$;


--
-- TOC entry 276 (class 1255 OID 18537)
-- Dependencies: 3 662
-- Name: rpt_z_ob_for_date(date, date); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_z_ob_for_date(in_bdate date, in_edate date) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;

BEGIN


  OPEN rc FOR
((select 0 as rang, 'Кассовые операции' as rang_descr,
o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
0 as product_group_id , 'Касса' as product_group_name,
0 as product_id, 'у.е.' as product_name, '' as scod,
i.actual_price, 1 as quantity, i.actual_price as summ, o.description

from orders o, items i, orders_operations_types ot
where o.id = i.orders_id and o.operation_type_code = ot.type_code
and o.order_date between in_bdate and in_edate
and o.operation_type_code in ('mpl','mmn')
)

union

(select 1 as rang, 'Приход товара' as rang_descr,
o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
pg.id as product_group_id , pg."name" as product_group_name,
i.products_id as product_id, p."name" as product_name, p.scod,
i.actual_price, i.quantity, (i.actual_price * i.quantity) as summ, o.description

from orders o, items i, orders_operations_types ot, products p, products_groups pg
where o.id = i.orders_id and o.operation_type_code = ot.type_code
--and o.id < in_z_report_id and o.id > preZ
and o.order_date between in_bdate and in_edate
and i.products_id = p.id
and p.products_groups_id = pg.id
and o.operation_type_code = 'ppl'
)

union

(select 2 as rang, 'Списание товара' as rang_descr,
o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
pg.id as product_group_id , pg."name" as product_group_name,
i.products_id as product_id, p."name" as product_name, p.scod,
i.actual_price, i.quantity, (i.actual_price * i.quantity) as summ, o.description

from orders o, items i, orders_operations_types ot, products p, products_groups pg
where o.id = i.orders_id and o.operation_type_code = ot.type_code
--and o.id < in_z_report_id and o.id > preZ
and o.order_date between in_bdate and in_edate
and i.products_id = p.id
and p.products_groups_id = pg.id
and o.operation_type_code = 'pmn'
)

union

(select 3 as rang, 'Продажа товара' as rang_descr,
o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
pg.id as product_group_id , pg."name" as product_group_name,
i.products_id as product_id, p."name" as product_name, p.scod,
i.actual_price, i.quantity, (i.actual_price * i.quantity) as summ, o.description

from orders o, items i, orders_operations_types ot, products p, products_groups pg
where o.id = i.orders_id and o.operation_type_code = ot.type_code
--and o.id < in_z_report_id and o.id > preZ
and o.order_date between in_bdate and in_edate
and i.products_id = p.id
and p.products_groups_id = pg.id
and o.operation_type_code = 'psl'
)

union

(select 4 as rang, 'Возврат товара' as rang_descr,
o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
pg.id as product_group_id , pg."name" as product_group_name,
i.products_id as product_id, p."name" as product_name, p.scod,
i.actual_price, i.quantity, (i.actual_price * i.quantity) as summ, o.description

from orders o, items i, orders_operations_types ot, products p, products_groups pg
where o.id = i.orders_id and o.operation_type_code = ot.type_code
--and o.id < in_z_report_id and o.id > preZ
and o.order_date between in_bdate and in_edate
and i.products_id = p.id
and p.products_groups_id = pg.id
and o.operation_type_code = 'prt'
)) ORDER BY rang;

RETURN rc;

END; $$;


--
-- TOC entry 277 (class 1255 OID 18538)
-- Dependencies: 3 662
-- Name: rpt_z_report_cass(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_z_report_cass(in_z_report_id integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;
	preZ integer := 0;
BEGIN
	preZ := get_prev_z_for_z(in_z_report_id);

  OPEN rc FOR
select 	o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
	i.actual_price, o.description

from orders o, items i, orders_operations_types ot
where o.id = i.orders_id and o.operation_type_code = ot.type_code
	and o.id < in_z_report_id and o.id > preZ
	and o.operation_type_code in ('mpl','mmn')
order by o.order_date;

RETURN rc;

END; $$;


--
-- TOC entry 278 (class 1255 OID 18539)
-- Dependencies: 662 3
-- Name: rpt_z_report_products(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpt_z_report_products(in_z_report_id integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;
	preZ integer := 0;
BEGIN

	preZ := get_prev_z_for_z(in_z_report_id);

  OPEN rc FOR

(select 1 as rang,
	o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
	pg.id as product_group_id , pg."name" as product_group_name,
	i.products_id as product_id, p."name" as product_name, p.scod,
	i.actual_price, i.quantity, (i.actual_price * i.quantity) as summ, o.description

from orders o, items i, orders_operations_types ot, products p, products_groups pg
where o.id = i.orders_id and o.operation_type_code = ot.type_code
	and o.id < in_z_report_id and o.id > preZ
	and i.products_id = p.id
	and p.products_groups_id = pg.id
	and o.operation_type_code = 'ppl'
order by o.order_date)

union

(select 2 as rang,
	o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
	pg.id as product_group_id , pg."name" as product_group_name,
	i.products_id as product_id, p."name" as product_name, p.scod,
	i.actual_price, i.quantity, (i.actual_price * i.quantity) as summ, o.description

from orders o, items i, orders_operations_types ot, products p, products_groups pg
where o.id = i.orders_id and o.operation_type_code = ot.type_code
	and o.id < in_z_report_id and o.id > preZ
	and i.products_id = p.id
	and p.products_groups_id = pg.id
	and o.operation_type_code = 'pmn'
order by o.order_date)

union

(select 3 as rang,
	o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
	pg.id as product_group_id , pg."name" as product_group_name,
	i.products_id as product_id, p."name" as product_name, p.scod,
	i.actual_price, i.quantity, (i.actual_price * i.quantity) as summ, o.description

from orders o, items i, orders_operations_types ot, products p, products_groups pg
where o.id = i.orders_id and o.operation_type_code = ot.type_code
	and o.id < in_z_report_id and o.id > preZ
	and i.products_id = p.id
	and p.products_groups_id = pg.id
	and o.operation_type_code = 'psl'
order by o.order_date)

union

(select 4 as rang,
	o.id, o.order_date, o.operation_type_code, ot.description as operation_description,
	pg.id as product_group_id , pg."name" as product_group_name,
	i.products_id as product_id, p."name" as product_name, p.scod,
	i.actual_price, i.quantity, (i.actual_price * i.quantity) as summ, o.description

from orders o, items i, orders_operations_types ot, products p, products_groups pg
where o.id = i.orders_id and o.operation_type_code = ot.type_code
	and o.id < in_z_report_id and o.id > preZ
	and i.products_id = p.id
	and p.products_groups_id = pg.id
	and o.operation_type_code = 'prt'
order by o.order_date);

RETURN rc;

END; $$;


--
-- TOC entry 279 (class 1255 OID 18540)
-- Dependencies: 662 3
-- Name: rpta_ppl_time_progress(date, date, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpta_ppl_time_progress(in_bdate date, in_edate date, in_step character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;

BEGIN


  OPEN rc FOR
	SELECT 
	CASE
            WHEN in_step='year' THEN  to_char(ddate,'YYYY')
            WHEN in_step='month' THEN  to_char(ddate,'YYYY.MM')
            WHEN in_step='day' THEN  to_char(ddate,'YYYY.MM.dd')
            WHEN in_step='hour' THEN  to_char(ddate,'YYYY.MM.dd HH24')
            ELSE to_char(ddate,'YYYY.MM.dd HH24:MI:SS')
        END AS tdate
	, a.*, to_credit + to_cass as "sum"
	FROM 
	(SELECT date_trunc(in_step, o.order_date) as ddate , count(*) 
		, sum(i.actual_price * i.quantity) to_cass
		, sum(CASE WHEN i.actual_price < 0 THEN abs(i.actual_price) ELSE 0 END) as to_credit
		FROM orders o
		LEFT OUTER JOIN customers_balances cb ON o.id = cb.order_id 
		, items i
		WHERE o.id = i.orders_id 
			AND o.operation_type_code='psl'
			AND o.order_date between in_bdate AND in_edate
		GROUP BY ddate
		ORDER BY ddate) a;


  
RETURN rc;

END; $$;


--
-- TOC entry 280 (class 1255 OID 18541)
-- Dependencies: 3 662
-- Name: rpta_ppl_users_progress(date, date, boolean); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION rpta_ppl_users_progress(in_bdate date, in_edate date, in_select_all boolean) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor;

BEGIN


  OPEN rc FOR
	SELECT u."name", 
		sum((CASE WHEN i.actual_price > 0 THEN i.actual_price ELSE 0 END) * i.quantity)
		
	FROM orders o, items i, users u
	WHERE o.id = i.orders_id 
		AND o.operation_type_code='psl'
		AND u.id = o.user_id 
		AND (in_select_all OR (NOT in_select_all AND (o.order_date between in_bdate AND in_edate)))
	GROUP BY u."name" ORDER BY sum;


  
RETURN rc;

END; $$;


--
-- TOC entry 281 (class 1255 OID 18542)
-- Dependencies: 3 662
-- Name: select_products_quantity_for_id(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION select_products_quantity_for_id(t_product_id integer) RETURNS numeric
    LANGUAGE plpgsql
    AS $$ DECLARE quantity numeric;
	       t_last_quantity_cache_id integer := 0;
	       t_last_quantity_cache_value numeric(10,3) := 0;
	       t_last_product_order_id integer;
	       t_cond boolean := FALSE;
BEGIN 

  --select sum(case when(o.operation_type_code in ('pmn','psl')) then 
  --            ( - i.quantity) else i.quantity end ) 
  --  into quantity 
  --  from orders o, items i
  --  where o.id = i.orders_id AND i.products_id=t_product_id;

  select max(orders_id) into t_last_product_order_id 
	from items where products_id = t_product_id;

  select last_quantity_cache_id, last_quantity_cache_value 
  into   t_last_quantity_cache_id, t_last_quantity_cache_value
  from products where id = t_product_id;

  RAISE NOTICE '1 %', t_last_product_order_id;
  RAISE NOTICE '2 %', t_last_quantity_cache_id;
  RAISE NOTICE '3 %', t_last_quantity_cache_value;

  t_cond = t_last_quantity_cache_id IS NOT NULL AND t_last_product_order_id - t_last_quantity_cache_id < 1000;

  RAISE NOTICE '4 %', t_cond;
  RAISE NOTICE '5 %', t_product_id;
  
  SELECT sum(case when(o.operation_type_code in ('pmn','psl')) then ( - i.quantity) else i.quantity end )
    INTO quantity
  FROM 
    orders o, items i
    WHERE o.id = i.orders_id 
	AND o.operation_type_code in ('pmn','psl','prt','ppl')
	AND i.products_id=t_product_id
	AND (NOT t_cond OR t_cond AND o.id > t_last_quantity_cache_id);

  RAISE NOTICE '6 %', quantity;

  if quantity is null then
    quantity := 0;
  end if;

  IF NOT t_cond THEN
    RAISE NOTICE '7 %', quantity;	
    UPDATE products
    SET 
       last_quantity_cache_id=t_last_product_order_id, last_quantity_cache_value=quantity
    WHERE id=t_product_id;
  ELSE 
    quantity := quantity + t_last_quantity_cache_value;   
  END IF;

  return quantity;
  

END; $$;


--
-- TOC entry 282 (class 1255 OID 18543)
-- Dependencies: 3 662
-- Name: select_products_quantity_for_id_to_order_id(integer, integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION select_products_quantity_for_id_to_order_id(t_product_id integer, t_order_id integer) RETURNS numeric
    LANGUAGE plpgsql
    AS $$ DECLARE quantity numeric; 
BEGIN 

  select sum(case when(o.operation_type_code in ('pmn','psl')) then 
              ( - i.quantity) else i.quantity end ) 
    into quantity 
    from orders o, items i
    where o.id <= t_order_id AND o.id = i.orders_id AND i.products_id=t_product_id;

  if quantity is not null then
    return quantity;
  else
    return 0;
  end if;

END; $$;


--
-- TOC entry 283 (class 1255 OID 18544)
-- Dependencies: 3 662
-- Name: select_products_quantity_for_id_with_step(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION select_products_quantity_for_id_with_step(t_product_id integer, OUT quantity numeric, OUT step numeric) RETURNS record
    LANGUAGE plpgsql
    AS $$ DECLARE 
  product_measures_type boolean;
BEGIN 

  select sum(case when(o.operation_type_code in ('pmn','psl')) then 
              ( - i.quantity) else i.quantity end ) 
    into quantity 
    from orders o, items i
    where o.id = i.orders_id AND i.products_id=t_product_id;

  if quantity is null then
    quantity := 0;
  end if;

  SELECT rb.mtype INTO product_measures_type 
    FROM rb_measures rb, products p 
    WHERE p.id=t_product_id AND p.measures_id=rb.id;

  IF product_measures_type IS TRUE THEN
    step := 0.01;
  ELSE
    step := 1;
  END IF;
  
END; $$;


--
-- TOC entry 284 (class 1255 OID 18545)
-- Dependencies: 3 662
-- Name: select_psl_ppl_file_history(boolean, boolean, character varying, numeric, boolean, date, date, boolean, character varying, boolean, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION select_psl_ppl_file_history(in_all_history boolean, in_find_files boolean, in_file_name character varying, in_file_order_id numeric, in_select_for_date boolean, in_b_date date, in_e_date date, in_for_operation_type_code boolean, in_operation_type_code character varying, in_is_created_back_file boolean, in_back_file_name character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
	SELECT 	h.order_id, 
	o.order_date,
	h.file_name, 
	h.file_order_id, 
	h.is_created_back_file, 
	h.back_file_name,
	o.operation_type_code,
	ot.description as operation_type_name,
	o.description,
	o.user_id,
	u."name" as user_name,
	i.to_cass,
	o.credit,
	CASE WHEN cb.summ IS NOT NULL THEN cb.summ ELSE 0 END as to_credit,
	o.customer_id,
	c.short_name
  FROM psl_ppl_files_history h, orders o
	LEFT OUTER JOIN customers c ON c.id = o.customer_id 
	LEFT OUTER JOIN customers_balances cb ON cb.order_id=o.id,
   (SELECT ii.orders_id, sum(ii.actual_price * ii.quantity) as to_cass FROM items ii, orders oo
   WHERE oo.id=ii.orders_id
   AND ( NOT in_select_for_date OR (in_select_for_date AND (oo.order_date between in_b_date AND in_e_date)))
   GROUP BY ii.orders_id) i,
   orders_operations_types ot, users u
  WHERE o.id=h.order_id 
	AND o.operation_type_code = ot.type_code 
	AND u.id = o.user_id AND i.orders_id=o.id
	-----------------------------------------
	AND (in_all_history OR ( NOT in_all_history AND 
			( NOT in_find_files OR (in_find_files AND (h.file_name=in_file_name OR h.file_order_id=in_file_order_id)))
			AND 
			( NOT in_select_for_date OR (in_select_for_date AND (o.order_date between in_b_date AND in_e_date)))
			AND 
			( NOT in_for_operation_type_code OR (in_for_operation_type_code AND o.operation_type_code=in_operation_type_code))
			AND 
			( NOT in_is_created_back_file OR ( in_is_created_back_file AND h.is_created_back_file AND h.back_file_name=in_back_file_name))
	))
;
  RETURN rc; 

END; $$;


--
-- TOC entry 285 (class 1255 OID 18546)
-- Dependencies: 3 662
-- Name: select_selling_orders_and_products_all(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION select_selling_orders_and_products_all() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  --RAISE EXCEPTION '';

  OPEN rc FOR 
   SELECT  o.id,
        o.order_date,
        i.products_id,
        p.products_groups_id,
        
        p."name" as product_name,
        p.description,
        p.scod,
        i.quantity,
        p.measures_id, 
        m."name" as quantityName,
        i.actual_price
        
    FROM orders o, items i, products p, rb_measures m

    WHERE o.id = i.orders_id
      AND i.products_id = p.id
      AND p.measures_id = m.id
      AND o.operation_type_code in ('psl')
    ORDER BY o.id;
    
  RETURN rc; 

END; $$;


--
-- TOC entry 286 (class 1255 OID 18547)
-- Dependencies: 3 662
-- Name: select_selling_orders_and_products_by_customer_id(integer, boolean); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION select_selling_orders_and_products_by_customer_id(in_customer_id integer, in_finddepsops boolean) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  --RAISE EXCEPTION '';

  OPEN rc FOR 
   SELECT  o.id,
        o.order_date,
        i.products_id,
        p.products_groups_id,
        
        p."name" as product_name,
        p.description,
        p.scod,
        i.quantity,
        p.measures_id, 
        m."name" as quantityName,
        i.actual_price
        
    FROM orders o, items i, products p, rb_measures m

    WHERE o.customer_id = in_customer_id
      AND o.id = i.orders_id
      AND i.products_id = p.id
      AND p.measures_id = m.id
      AND o.operation_type_code in ('psl')
      AND ((NOT in_findDepsOps AND o.id NOT IN (SELECT order_id FROM psl_ppl_files_history)) OR (in_findDepsOps AND o.id IN (SELECT order_id FROM psl_ppl_files_history)))
    ORDER BY o.id;
    
  RETURN rc; 

END; $$;


--
-- TOC entry 287 (class 1255 OID 18548)
-- Dependencies: 3 662
-- Name: select_selling_orders_and_products_by_date(date, boolean); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION select_selling_orders_and_products_by_date(t_b_ship_date date, in_finddepsops boolean) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  --RAISE EXCEPTION '';

  OPEN rc FOR 
   SELECT  o.id,
        o.order_date,
        i.products_id,
        p.products_groups_id,
        
        p."name" as product_name,
        p.description,
        p.scod,
        i.quantity,
        p.measures_id, 
        m."name" as quantityName,
        i.actual_price
        
    FROM orders o, items i, products p, rb_measures m

    WHERE o.order_date BETWEEN t_b_ship_date AND  (t_b_ship_date + interval '1 day')
      AND o.id = i.orders_id
      AND i.products_id = p.id
      AND p.measures_id = m.id
      AND o.operation_type_code in ('psl')
      AND ((NOT in_findDepsOps AND o.id NOT IN (SELECT order_id FROM psl_ppl_files_history)) OR (in_findDepsOps AND o.id IN (SELECT order_id FROM psl_ppl_files_history)))
    ORDER BY o.id;
    
  RETURN rc; 

END; $$;


--
-- TOC entry 288 (class 1255 OID 18549)
-- Dependencies: 3 662
-- Name: select_selling_orders_and_products_by_order_id(integer, boolean); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION select_selling_orders_and_products_by_order_id(t_order_id integer, in_finddepsops boolean) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  --RAISE EXCEPTION '';

  OPEN rc FOR 
   SELECT  o.id,
        o.order_date,
        i.products_id,
        p.products_groups_id,
        
        p."name" as product_name,
        p.description,
        p.scod,
        i.quantity,
        p.measures_id, 
        m."name" as quantityName,
        i.actual_price
        
    FROM orders o, items i, products p, rb_measures m

    WHERE o.id = t_order_id
      AND o.id = i.orders_id
      AND i.products_id = p.id
      AND p.measures_id = m.id
      AND o.operation_type_code in ('psl')
      AND ((NOT in_findDepsOps AND o.id NOT IN (SELECT order_id FROM psl_ppl_files_history)) OR (in_findDepsOps AND o.id IN (SELECT order_id FROM psl_ppl_files_history)))
    ORDER BY o.id;
    
  RETURN rc; 

END; $$;


--
-- TOC entry 289 (class 1255 OID 18550)
-- Dependencies: 662 3
-- Name: select_selling_orders_and_products_by_scod(character varying, boolean); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION select_selling_orders_and_products_by_scod(t_scod character varying, in_finddepsops boolean) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  --RAISE EXCEPTION '';

  OPEN rc FOR 
   SELECT  o.id,
        o.order_date,
        i.products_id,
        p.products_groups_id,
        
        p."name" as product_name,
        p.description,
        p.scod,
        i.quantity,
        p.measures_id, 
        m."name" as quantityName,
        i.actual_price
        
    FROM orders o, items i, products p, rb_measures m
    WHERE p.scod = t_scod
      AND o.id = i.orders_id
      AND i.products_id = p.id
      AND p.measures_id = m.id
      AND o.operation_type_code in ('psl')
      AND ((NOT in_findDepsOps AND o.id NOT IN (SELECT order_id FROM psl_ppl_files_history)) OR (in_findDepsOps AND o.id IN (SELECT order_id FROM psl_ppl_files_history)))
    ORDER BY o.id;
    
  RETURN rc; 

END; $$;


--
-- TOC entry 290 (class 1255 OID 18551)
-- Dependencies: 662 3
-- Name: select_z_reports(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION select_z_reports() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
	select 	get_prev_z_for_z(o.id) as pre_id,
		o.id, o.order_date, i.actual_price, o.description
	from orders o, items i
	where o.id = i.orders_id 
		and operation_type_code = 'z';
  RETURN rc; 

END; $$;


--
-- TOC entry 291 (class 1255 OID 18552)
-- Dependencies: 3 662
-- Name: set_user_access(integer, character varying, boolean); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION set_user_access(in_user_id integer, in_access_kod character varying, in_access boolean) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE
	t_access_id integer := null;
	t_is_accesseble integer := null;
BEGIN 

	IF in_access IS TRUE THEN -- Если установить то добавляем

		SELECT 1 INTO t_is_accesseble FROM user_access ua, appaccess ap
			WHERE ua.user_id = in_user_id AND ua.access_id = ap.id AND ap.kod = in_access_kod;

		IF t_is_accesseble IS NULL THEN -- Если еще не имеется то добавляем
	
			SELECT id INTO t_access_id FROM appaccess WHERE kod = in_access_kod;
		
			INSERT INTO user_access(
				user_id, access_id)
				VALUES (in_user_id, t_access_id);
	
		END IF; 
	ELSE -- Иначе удаляем
		DELETE FROM user_access WHERE user_id = in_user_id AND access_id = (SELECT id FROM appaccess WHERE kod = in_access_kod);
	END IF;

END; $$;


--
-- TOC entry 292 (class 1255 OID 18553)
-- Dependencies: 3 662
-- Name: user_login(character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION user_login(user_name character varying, user_passwod character varying) RETURNS refcursor
    LANGUAGE plpgsql STRICT SECURITY DEFINER
    AS $$ DECLARE rc refcursor; 

BEGIN 

  OPEN rc FOR 
    SELECT id,"name",isadmin,description,
    (SELECT svalue FROM conf WHERE kod='user') as user,
    (SELECT svalue FROM conf WHERE kod='passwd') as passwd

	FROM users 
	WHERE "name"=user_name AND passwd=user_passwod;

  RETURN rc; 

END; $$;


--
-- TOC entry 293 (class 1255 OID 18554)
-- Dependencies: 662 3
-- Name: users_delete(integer); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION users_delete(t_id integer) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  DELETE FROM users 
    WHERE id = t_id; 

END; $$;


--
-- TOC entry 294 (class 1255 OID 18555)
-- Dependencies: 662 3
-- Name: users_insert(character varying, character varying, boolean, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION users_insert(t_name character varying, t_passwd character varying, t_isadmin boolean, t_description character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  INSERT INTO users ( NAME,passwd,isadmin,description )
    VALUES (t_name,t_passwd,t_isadmin,t_description ); 

  RETURN currval('users_id_seq'); 

END; $$;


--
-- TOC entry 295 (class 1255 OID 18556)
-- Dependencies: 3 662
-- Name: users_select(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION users_select() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT id, "name", passwd, isadmin, description 
      FROM users ORDER BY "name"; 

  RETURN rc; 

END; $$;


--
-- TOC entry 296 (class 1255 OID 18557)
-- Dependencies: 3 662
-- Name: users_select_for_loginer(); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION users_select_for_loginer() RETURNS refcursor
    LANGUAGE plpgsql STRICT SECURITY DEFINER
    AS $$ DECLARE rc refcursor; 
BEGIN 

  OPEN rc FOR 
    SELECT id, "name" 
      FROM users ORDER BY "name"; 

  RETURN rc; 

END; $$;


--
-- TOC entry 297 (class 1255 OID 18558)
-- Dependencies: 662 3
-- Name: users_update(integer, character varying, character varying, boolean, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION users_update(t_id integer, t_name character varying, t_passwd character varying, t_isadmin boolean, t_description character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  UPDATE users 
      SET NAME = t_name,passwd = t_passwd,isadmin=t_isadmin,description=t_description 
    WHERE id = t_id; 

  RETURN; 

END; $$;


--
-- TOC entry 239 (class 1255 OID 18559)
-- Dependencies: 662 3
-- Name: users_update(integer, character varying, boolean, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION users_update(t_id integer, t_name character varying, t_isadmin boolean, t_description character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  UPDATE users 
      SET NAME = t_name,isadmin=t_isadmin,description=t_description 
    WHERE id = t_id; 

  RETURN; 

END; $$;


--
-- TOC entry 251 (class 1255 OID 18560)
-- Dependencies: 3 662
-- Name: users_update_password(integer, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION users_update_password(t_id integer, t_passwd character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$ DECLARE 
BEGIN 

  UPDATE users 
      SET passwd = t_passwd
    WHERE id = t_id; 

  RETURN; 

END; $$;


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 140 (class 1259 OID 18561)
-- Dependencies: 3
-- Name: appaccess; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE appaccess (
    kod character varying(50) NOT NULL,
    id integer NOT NULL
);


--
-- TOC entry 141 (class 1259 OID 18564)
-- Dependencies: 140 3
-- Name: access_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE access_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2113 (class 0 OID 0)
-- Dependencies: 141
-- Name: access_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE access_id_seq OWNED BY appaccess.id;


--
-- TOC entry 142 (class 1259 OID 18566)
-- Dependencies: 3
-- Name: conf; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE conf (
    kod character varying(30) NOT NULL,
    svalue character varying,
    dvalue numeric
);


--
-- TOC entry 143 (class 1259 OID 18572)
-- Dependencies: 1998 1999 2000 3
-- Name: customers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE customers (
    id integer NOT NULL,
    fio character varying(100) NOT NULL,
    address character varying(500),
    phone_number character varying(20),
    phone_number_2 character varying(20),
    email character varying(50),
    description character varying(500),
    ur boolean DEFAULT false NOT NULL,
    short_name character varying(200),
    doc_type integer,
    doc character varying(50),
    sex numeric(1,0) DEFAULT 0 NOT NULL,
    dep boolean DEFAULT false NOT NULL,
    rnn character varying(20),
    rsh character varying(500),
    bin character varying(20),
    dc1 character varying(50),
    dc2 character varying(50),
    dc3 character varying(50),
    dc4 character varying(50),
    dc5 character varying(50),
    dc6 character varying(50)
);


--
-- TOC entry 144 (class 1259 OID 18581)
-- Dependencies: 2002 3
-- Name: customers_balances; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE customers_balances (
    id integer NOT NULL,
    order_id integer NOT NULL,
    customer_id integer NOT NULL,
    summ numeric(20,2) DEFAULT 0 NOT NULL,
    description character varying
);


--
-- TOC entry 145 (class 1259 OID 18588)
-- Dependencies: 3 144
-- Name: customers_balances_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE customers_balances_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2114 (class 0 OID 0)
-- Dependencies: 145
-- Name: customers_balances_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE customers_balances_id_seq OWNED BY customers_balances.id;


--
-- TOC entry 146 (class 1259 OID 18590)
-- Dependencies: 3 143
-- Name: customers_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE customers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2115 (class 0 OID 0)
-- Dependencies: 146
-- Name: customers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE customers_id_seq OWNED BY customers.id;


--
-- TOC entry 147 (class 1259 OID 18592)
-- Dependencies: 3
-- Name: db_params; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE db_params (
    pkey character varying(100) NOT NULL,
    v_text text,
    v_numeric numeric,
    v_time timestamp without time zone,
    v_boolean boolean
);


--
-- TOC entry 148 (class 1259 OID 18598)
-- Dependencies: 2004 2005 2007 3
-- Name: items; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE items (
    id integer NOT NULL,
    orders_id integer NOT NULL,
    products_id integer,
    actual_price numeric(10,2) DEFAULT 0 NOT NULL,
    quantity numeric(10,3) DEFAULT 0 NOT NULL,
    CONSTRAINT items_ck_2 CHECK (((0)::numeric <= get_cass_balance()))
);


--
-- TOC entry 149 (class 1259 OID 18604)
-- Dependencies: 3 148
-- Name: items_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2116 (class 0 OID 0)
-- Dependencies: 149
-- Name: items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE items_id_seq OWNED BY items.id;


--
-- TOC entry 150 (class 1259 OID 18606)
-- Dependencies: 2008 2010 2011 3
-- Name: orders; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE orders (
    id integer NOT NULL,
    ship_date timestamp without time zone NOT NULL,
    order_date timestamp without time zone,
    operation_type_code character varying(3) NOT NULL,
    description character varying(255),
    sub_order_id integer,
    user_id integer NOT NULL,
    customer_id integer,
    credit boolean DEFAULT false NOT NULL,
    CONSTRAINT orders_ck_1 CHECK (((order_date <= ship_date) OR (order_date IS NULL))),
    CONSTRAINT orders_ck_2 CHECK ((((credit = true) AND (customer_id IS NOT NULL)) OR (credit = false)))
);


--
-- TOC entry 151 (class 1259 OID 18612)
-- Dependencies: 3 150
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2117 (class 0 OID 0)
-- Dependencies: 151
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE orders_id_seq OWNED BY orders.id;


--
-- TOC entry 152 (class 1259 OID 18614)
-- Dependencies: 3
-- Name: orders_operations_types; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE orders_operations_types (
    type_code character varying(3) NOT NULL,
    description character varying(255) NOT NULL,
    supertype character varying(20)
);


--
-- TOC entry 153 (class 1259 OID 18617)
-- Dependencies: 2012 2013 2014 2015 2016 2018 2019 2020 3
-- Name: products; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE products (
    id integer NOT NULL,
    products_groups_id integer,
    name character varying(50) NOT NULL,
    description character varying(500),
    scod character varying(255),
    quantity numeric(10,3) DEFAULT 0,
    measures_id integer,
    list_price numeric(10,2) DEFAULT 0 NOT NULL,
    spec_price numeric(10,2) DEFAULT 0 NOT NULL,
    percent_discount numeric(4,4) DEFAULT 0 NOT NULL,
    last_quantity_cache_id integer,
    last_quantity_cache_value numeric(10,3) DEFAULT 0,
    CONSTRAINT products_ck_1 CHECK (((((percent_discount > (0)::numeric) AND (spec_price = (0)::numeric)) OR ((percent_discount = (0)::numeric) AND (spec_price > (0)::numeric))) OR ((percent_discount = (0)::numeric) AND (spec_price = (0)::numeric)))),
    CONSTRAINT products_ck_3 CHECK (((percent_discount <= (100)::numeric) AND (percent_discount >= (0)::numeric))),
    CONSTRAINT products_ck_4 CHECK ((((spec_price >= (0)::numeric) AND (list_price >= (0)::numeric)) AND (quantity >= (0)::numeric)))
);


--
-- TOC entry 154 (class 1259 OID 18632)
-- Dependencies: 3
-- Name: products_groups; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE products_groups (
    id integer NOT NULL,
    sub_id integer,
    name character varying(30) NOT NULL,
    description character varying(500)
);


--
-- TOC entry 155 (class 1259 OID 18638)
-- Dependencies: 3 154
-- Name: products_groups_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE products_groups_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2118 (class 0 OID 0)
-- Dependencies: 155
-- Name: products_groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE products_groups_id_seq OWNED BY products_groups.id;


--
-- TOC entry 156 (class 1259 OID 18640)
-- Dependencies: 3 153
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2119 (class 0 OID 0)
-- Dependencies: 156
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE products_id_seq OWNED BY products.id;


--
-- TOC entry 157 (class 1259 OID 18642)
-- Dependencies: 2022 3
-- Name: psl_ppl_files_history; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE psl_ppl_files_history (
    order_id integer NOT NULL,
    file_name character varying(255) NOT NULL,
    file_order_id integer NOT NULL,
    is_created_back_file boolean DEFAULT false NOT NULL,
    back_file_name character varying(255)
);


--
-- TOC entry 158 (class 1259 OID 18649)
-- Dependencies: 3
-- Name: quantity; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE quantity (
    sum numeric
);


--
-- TOC entry 159 (class 1259 OID 18655)
-- Dependencies: 3
-- Name: rb_doc_types; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE rb_doc_types (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(255)
);


--
-- TOC entry 2120 (class 0 OID 0)
-- Dependencies: 159
-- Name: TABLE rb_doc_types; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE rb_doc_types IS 'Справочник';


--
-- TOC entry 2121 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN rb_doc_types.id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN rb_doc_types.id IS 'Цифровой идентификатор';


--
-- TOC entry 2122 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN rb_doc_types.name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN rb_doc_types.name IS 'Наименование';


--
-- TOC entry 2123 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN rb_doc_types.description; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN rb_doc_types.description IS 'Описание';


--
-- TOC entry 160 (class 1259 OID 18658)
-- Dependencies: 159 3
-- Name: rb_doc_types_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE rb_doc_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2124 (class 0 OID 0)
-- Dependencies: 160
-- Name: rb_doc_types_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE rb_doc_types_id_seq OWNED BY rb_doc_types.id;


--
-- TOC entry 161 (class 1259 OID 18660)
-- Dependencies: 2024 3
-- Name: rb_measures; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE rb_measures (
    id integer NOT NULL,
    name character varying(20) NOT NULL,
    description character varying(255),
    mtype boolean DEFAULT false NOT NULL
);


--
-- TOC entry 2125 (class 0 OID 0)
-- Dependencies: 161
-- Name: TABLE rb_measures; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE rb_measures IS 'Справочник единиц измерений';


--
-- TOC entry 162 (class 1259 OID 18664)
-- Dependencies: 161 3
-- Name: rb_measures_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE rb_measures_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2126 (class 0 OID 0)
-- Dependencies: 162
-- Name: rb_measures_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE rb_measures_id_seq OWNED BY rb_measures.id;


--
-- TOC entry 163 (class 1259 OID 18666)
-- Dependencies: 3
-- Name: t_id; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE t_id (
    max integer
);


--
-- TOC entry 164 (class 1259 OID 18669)
-- Dependencies: 3
-- Name: user_access; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE user_access (
    user_id integer NOT NULL,
    access_id integer NOT NULL
);


--
-- TOC entry 165 (class 1259 OID 18672)
-- Dependencies: 2026 3
-- Name: users; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE users (
    id integer NOT NULL,
    name character varying(30) NOT NULL,
    passwd character varying(32) NOT NULL,
    isadmin boolean DEFAULT false NOT NULL,
    description character varying(255)
);


--
-- TOC entry 2127 (class 0 OID 0)
-- Dependencies: 165
-- Name: COLUMN users.name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.name IS 'Имя пользователя';


--
-- TOC entry 2128 (class 0 OID 0)
-- Dependencies: 165
-- Name: COLUMN users.passwd; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.passwd IS 'Хэш пароля';


--
-- TOC entry 2129 (class 0 OID 0)
-- Dependencies: 165
-- Name: COLUMN users.isadmin; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.isadmin IS 'Признак администратора';


--
-- TOC entry 2130 (class 0 OID 0)
-- Dependencies: 165
-- Name: COLUMN users.description; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.description IS 'Описание';


--
-- TOC entry 166 (class 1259 OID 18676)
-- Dependencies: 165 3
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2131 (class 0 OID 0)
-- Dependencies: 166
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 167 (class 1259 OID 18678)
-- Dependencies: 1802 3
-- Name: v_operations; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW v_operations AS
    SELECT o.id AS orders_id, o.ship_date, o.order_date, o.operation_type_code, o.description, o.sub_order_id, i.id AS items_id, i.products_id, i.actual_price, i.quantity FROM orders o, items i WHERE (o.id = i.orders_id);


--
-- TOC entry 168 (class 1259 OID 18682)
-- Dependencies: 1803 3
-- Name: v_products_quantity; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW v_products_quantity AS
    SELECT p.id, CASE WHEN (q.sum IS NULL) THEN (0)::numeric ELSE q.sum END AS quantity FROM (products p LEFT JOIN (SELECT i.products_id AS id, sum(CASE WHEN ((o.operation_type_code)::text = ANY (ARRAY[('pmn'::character varying)::text, ('psl'::character varying)::text])) THEN (- i.quantity) ELSE i.quantity END) AS sum FROM orders o, items i WHERE (((o.id = i.orders_id) AND ((o.operation_type_code)::text = ANY (ARRAY[('pmn'::character varying)::text, ('psl'::character varying)::text, ('prt'::character varying)::text, ('ppl'::character varying)::text]))) AND (i.products_id IS NOT NULL)) GROUP BY i.products_id) q ON ((q.id = p.id)));


--
-- TOC entry 169 (class 1259 OID 18687)
-- Dependencies: 1804 3
-- Name: v_select_cass; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW v_select_cass AS
    SELECT o.id, o.ship_date, o.order_date, o.operation_type_code, CASE WHEN ((o.operation_type_code)::text = ANY (ARRAY[('mpl'::character varying)::text, ('psl'::character varying)::text])) THEN (i.actual_price * i.quantity) ELSE (- (i.actual_price * i.quantity)) END AS case_many, oot.description AS oot_description, o.description FROM orders o, items i, orders_operations_types oot WHERE (((o.id = i.orders_id) AND ((o.operation_type_code)::text = ANY (ARRAY[('mpl'::character varying)::text, ('mmn'::character varying)::text, ('prt'::character varying)::text, ('psl'::character varying)::text, ('z'::character varying)::text]))) AND ((o.operation_type_code)::text = (oot.type_code)::text));


--
-- TOC entry 170 (class 1259 OID 18691)
-- Dependencies: 1805 3
-- Name: v_select_cass_after_last_z; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW v_select_cass_after_last_z AS
    SELECT o.id, o.ship_date, o.order_date, o.operation_type_code, CASE WHEN ((o.operation_type_code)::text = ANY (ARRAY[('mpl'::character varying)::text, ('psl'::character varying)::text])) THEN (i.actual_price * i.quantity) ELSE (- (i.actual_price * i.quantity)) END AS case_many, oot.description AS oot_description, o.description FROM orders o, items i, orders_operations_types oot WHERE ((((o.id = i.orders_id) AND (o.id > (SELECT get_last_z_report_id() AS get_last_z_report_id))) AND ((o.operation_type_code)::text = ANY (ARRAY[('mpl'::character varying)::text, ('mmn'::character varying)::text, ('prt'::character varying)::text, ('psl'::character varying)::text, ('z'::character varying)::text]))) AND ((o.operation_type_code)::text = (oot.type_code)::text));


--
-- TOC entry 1997 (class 2604 OID 18696)
-- Dependencies: 141 140
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY appaccess ALTER COLUMN id SET DEFAULT nextval('access_id_seq'::regclass);


--
-- TOC entry 2001 (class 2604 OID 18697)
-- Dependencies: 146 143
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY customers ALTER COLUMN id SET DEFAULT nextval('customers_id_seq'::regclass);


--
-- TOC entry 2003 (class 2604 OID 18698)
-- Dependencies: 145 144
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY customers_balances ALTER COLUMN id SET DEFAULT nextval('customers_balances_id_seq'::regclass);


--
-- TOC entry 2006 (class 2604 OID 18699)
-- Dependencies: 149 148
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY items ALTER COLUMN id SET DEFAULT nextval('items_id_seq'::regclass);


--
-- TOC entry 2009 (class 2604 OID 18700)
-- Dependencies: 151 150
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY orders ALTER COLUMN id SET DEFAULT nextval('orders_id_seq'::regclass);


--
-- TOC entry 2017 (class 2604 OID 18701)
-- Dependencies: 156 153
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY products ALTER COLUMN id SET DEFAULT nextval('products_id_seq'::regclass);


--
-- TOC entry 2021 (class 2604 OID 18702)
-- Dependencies: 155 154
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY products_groups ALTER COLUMN id SET DEFAULT nextval('products_groups_id_seq'::regclass);


--
-- TOC entry 2023 (class 2604 OID 18703)
-- Dependencies: 160 159
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY rb_doc_types ALTER COLUMN id SET DEFAULT nextval('rb_doc_types_id_seq'::regclass);


--
-- TOC entry 2025 (class 2604 OID 18704)
-- Dependencies: 162 161
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY rb_measures ALTER COLUMN id SET DEFAULT nextval('rb_measures_id_seq'::regclass);


--
-- TOC entry 2027 (class 2604 OID 18705)
-- Dependencies: 166 165
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- TOC entry 2029 (class 2606 OID 18708)
-- Dependencies: 140 140
-- Name: access_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY appaccess
    ADD CONSTRAINT access_pk PRIMARY KEY (id);


--
-- TOC entry 2031 (class 2606 OID 18710)
-- Dependencies: 140 140
-- Name: appaccess_uk1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY appaccess
    ADD CONSTRAINT appaccess_uk1 UNIQUE (kod);


--
-- TOC entry 2033 (class 2606 OID 18712)
-- Dependencies: 142 142
-- Name: conf_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY conf
    ADD CONSTRAINT conf_pk PRIMARY KEY (kod);


--
-- TOC entry 2039 (class 2606 OID 18714)
-- Dependencies: 144 144
-- Name: customers_balances_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customers_balances
    ADD CONSTRAINT customers_balances_pk PRIMARY KEY (id);


--
-- TOC entry 2041 (class 2606 OID 18716)
-- Dependencies: 144 144
-- Name: customers_balances_uk1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customers_balances
    ADD CONSTRAINT customers_balances_uk1 UNIQUE (order_id);


--
-- TOC entry 2035 (class 2606 OID 18718)
-- Dependencies: 143 143
-- Name: customers_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT customers_pk PRIMARY KEY (id);


--
-- TOC entry 2037 (class 2606 OID 18720)
-- Dependencies: 143 143
-- Name: customers_uk1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT customers_uk1 UNIQUE (short_name);


--
-- TOC entry 2043 (class 2606 OID 18722)
-- Dependencies: 147 147
-- Name: db_params_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY db_params
    ADD CONSTRAINT db_params_pk PRIMARY KEY (pkey);


--
-- TOC entry 2045 (class 2606 OID 18724)
-- Dependencies: 148 148 148
-- Name: items_idx_1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY items
    ADD CONSTRAINT items_idx_1 UNIQUE (orders_id, products_id);


--
-- TOC entry 2049 (class 2606 OID 18726)
-- Dependencies: 148 148
-- Name: items_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY items
    ADD CONSTRAINT items_pk PRIMARY KEY (id);


--
-- TOC entry 2051 (class 2606 OID 18728)
-- Dependencies: 150 150
-- Name: orders_idx_1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_idx_1 UNIQUE (order_date);


--
-- TOC entry 2053 (class 2606 OID 18730)
-- Dependencies: 150 150
-- Name: orders_idx_2; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_idx_2 UNIQUE (ship_date);


--
-- TOC entry 2058 (class 2606 OID 18732)
-- Dependencies: 152 152
-- Name: orders_operations_types_idx1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY orders_operations_types
    ADD CONSTRAINT orders_operations_types_idx1 UNIQUE (description);


--
-- TOC entry 2060 (class 2606 OID 18734)
-- Dependencies: 152 152
-- Name: orders_operations_types_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY orders_operations_types
    ADD CONSTRAINT orders_operations_types_pk PRIMARY KEY (type_code);


--
-- TOC entry 2056 (class 2606 OID 18736)
-- Dependencies: 150 150
-- Name: orders_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_pk PRIMARY KEY (id);


--
-- TOC entry 2070 (class 2606 OID 18738)
-- Dependencies: 154 154 154
-- Name: products_groups_1_idx; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products_groups
    ADD CONSTRAINT products_groups_1_idx UNIQUE (sub_id, name);


--
-- TOC entry 2072 (class 2606 OID 18740)
-- Dependencies: 154 154 154
-- Name: products_groups_2_idx; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products_groups
    ADD CONSTRAINT products_groups_2_idx UNIQUE (id, sub_id);


--
-- TOC entry 2074 (class 2606 OID 18742)
-- Dependencies: 154 154
-- Name: products_groups_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products_groups
    ADD CONSTRAINT products_groups_pk PRIMARY KEY (id);


--
-- TOC entry 2063 (class 2606 OID 18744)
-- Dependencies: 153 153
-- Name: products_idx_1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_idx_1 UNIQUE (scod);


--
-- TOC entry 2065 (class 2606 OID 18746)
-- Dependencies: 153 153 153
-- Name: products_idx_2; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_idx_2 UNIQUE (id, products_groups_id);


--
-- TOC entry 2067 (class 2606 OID 18748)
-- Dependencies: 153 153
-- Name: products_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_pk PRIMARY KEY (id);


--
-- TOC entry 2078 (class 2606 OID 18750)
-- Dependencies: 159 159
-- Name: rb_doc_types_idx_1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY rb_doc_types
    ADD CONSTRAINT rb_doc_types_idx_1 UNIQUE (name);


--
-- TOC entry 2080 (class 2606 OID 18752)
-- Dependencies: 159 159
-- Name: rb_doc_types_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY rb_doc_types
    ADD CONSTRAINT rb_doc_types_pk PRIMARY KEY (id);


--
-- TOC entry 2082 (class 2606 OID 18754)
-- Dependencies: 161 161
-- Name: rb_measures_idx_1; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY rb_measures
    ADD CONSTRAINT rb_measures_idx_1 UNIQUE (name);


--
-- TOC entry 2084 (class 2606 OID 18756)
-- Dependencies: 161 161
-- Name: rb_measures_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY rb_measures
    ADD CONSTRAINT rb_measures_pk PRIMARY KEY (id);


--
-- TOC entry 2086 (class 2606 OID 18758)
-- Dependencies: 164 164 164
-- Name: user_access_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY user_access
    ADD CONSTRAINT user_access_pk PRIMARY KEY (user_id, access_id);


--
-- TOC entry 2088 (class 2606 OID 18760)
-- Dependencies: 165 165
-- Name: users_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_name UNIQUE (name);


--
-- TOC entry 2090 (class 2606 OID 18762)
-- Dependencies: 165 165
-- Name: users_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- TOC entry 2061 (class 1259 OID 18763)
-- Dependencies: 153
-- Name: fki_last_quantity_cache_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_last_quantity_cache_fk ON products USING btree (last_quantity_cache_id);


--
-- TOC entry 2075 (class 1259 OID 18764)
-- Dependencies: 157
-- Name: fki_psl_ppl_files_history_fk1; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_psl_ppl_files_history_fk1 ON psl_ppl_files_history USING btree (order_id);


--
-- TOC entry 2046 (class 1259 OID 18765)
-- Dependencies: 148
-- Name: items_idx_orders_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX items_idx_orders_id ON items USING btree (orders_id);


--
-- TOC entry 2047 (class 1259 OID 18766)
-- Dependencies: 148
-- Name: items_idx_product_id; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX items_idx_product_id ON items USING btree (products_id);


--
-- TOC entry 2054 (class 1259 OID 18767)
-- Dependencies: 150
-- Name: orders_index_for_operation_type_code; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX orders_index_for_operation_type_code ON orders USING hash (operation_type_code);


--
-- TOC entry 2068 (class 1259 OID 18768)
-- Dependencies: 153
-- Name: produsts_index_for_group; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX produsts_index_for_group ON products USING btree (products_groups_id);


--
-- TOC entry 2076 (class 1259 OID 18769)
-- Dependencies: 157
-- Name: psl_ppl_files_history_idx; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX psl_ppl_files_history_idx ON psl_ppl_files_history USING btree (file_name);


--
-- TOC entry 2107 (class 2620 OID 18770)
-- Dependencies: 191 148
-- Name: check_maney_balance; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER check_maney_balance
    AFTER INSERT OR UPDATE ON items
    FOR EACH STATEMENT
    EXECUTE PROCEDURE check_cass_balance();


--
-- TOC entry 2108 (class 2620 OID 18771)
-- Dependencies: 192 148
-- Name: check_product_balance; Type: TRIGGER; Schema: public; Owner: -
--

CREATE TRIGGER check_product_balance
    AFTER INSERT OR UPDATE ON items
    FOR EACH ROW
    EXECUTE PROCEDURE check_product_balance();


--
-- TOC entry 2092 (class 2606 OID 18772)
-- Dependencies: 143 2034 144
-- Name: customer_balances_fk2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customers_balances
    ADD CONSTRAINT customer_balances_fk2 FOREIGN KEY (customer_id) REFERENCES customers(id);


--
-- TOC entry 2093 (class 2606 OID 18777)
-- Dependencies: 144 150 2055
-- Name: customers_balances_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customers_balances
    ADD CONSTRAINT customers_balances_fk1 FOREIGN KEY (order_id) REFERENCES orders(id);


--
-- TOC entry 2091 (class 2606 OID 18782)
-- Dependencies: 159 2079 143
-- Name: customers_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT customers_fk1 FOREIGN KEY (doc_type) REFERENCES rb_doc_types(id);


--
-- TOC entry 2094 (class 2606 OID 18787)
-- Dependencies: 148 2055 150
-- Name: items_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY items
    ADD CONSTRAINT items_fk_1 FOREIGN KEY (orders_id) REFERENCES orders(id);


--
-- TOC entry 2095 (class 2606 OID 18792)
-- Dependencies: 2066 148 153
-- Name: items_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY items
    ADD CONSTRAINT items_fk_2 FOREIGN KEY (products_id) REFERENCES products(id);


--
-- TOC entry 2100 (class 2606 OID 18797)
-- Dependencies: 153 2055 150
-- Name: last_quantity_cache_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT last_quantity_cache_fk FOREIGN KEY (last_quantity_cache_id) REFERENCES orders(id);


--
-- TOC entry 2096 (class 2606 OID 18802)
-- Dependencies: 2059 150 152
-- Name: orders_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_fk1 FOREIGN KEY (operation_type_code) REFERENCES orders_operations_types(type_code);


--
-- TOC entry 2097 (class 2606 OID 18807)
-- Dependencies: 150 165 2089
-- Name: orders_fk2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_fk2 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 2098 (class 2606 OID 18812)
-- Dependencies: 2034 143 150
-- Name: orders_fk3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_fk3 FOREIGN KEY (customer_id) REFERENCES customers(id);


--
-- TOC entry 2099 (class 2606 OID 18817)
-- Dependencies: 150 150 2055
-- Name: orders_fk_this_1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY orders
    ADD CONSTRAINT orders_fk_this_1 FOREIGN KEY (sub_order_id) REFERENCES orders(id);


--
-- TOC entry 2101 (class 2606 OID 18822)
-- Dependencies: 2073 154 153
-- Name: products_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_fk_1 FOREIGN KEY (products_groups_id) REFERENCES products_groups(id);


--
-- TOC entry 2102 (class 2606 OID 18827)
-- Dependencies: 2083 161 153
-- Name: products_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products
    ADD CONSTRAINT products_fk_2 FOREIGN KEY (measures_id) REFERENCES rb_measures(id);


--
-- TOC entry 2103 (class 2606 OID 18832)
-- Dependencies: 154 154 2073
-- Name: products_grous_fk_1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY products_groups
    ADD CONSTRAINT products_grous_fk_1 FOREIGN KEY (sub_id) REFERENCES products_groups(id);


--
-- TOC entry 2104 (class 2606 OID 18837)
-- Dependencies: 157 150 2055
-- Name: psl_ppl_files_history_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY psl_ppl_files_history
    ADD CONSTRAINT psl_ppl_files_history_fk1 FOREIGN KEY (order_id) REFERENCES orders(id);


--
-- TOC entry 2105 (class 2606 OID 18842)
-- Dependencies: 2089 164 165
-- Name: user_access_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_access
    ADD CONSTRAINT user_access_fk1 FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2106 (class 2606 OID 18847)
-- Dependencies: 2028 164 140
-- Name: user_access_fk2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_access
    ADD CONSTRAINT user_access_fk2 FOREIGN KEY (access_id) REFERENCES appaccess(id) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2014-03-03 13:42:12 ALMT

--
-- PostgreSQL database dump complete
--

