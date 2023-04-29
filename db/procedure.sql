-- SQLINES LICENSE FOR EVALUATION USE ONLY
DROP PROCEDURE IF EXISTS date_summary;

DELIMITER //

CREATE PROCEDURE date_summary(IN IN_DATE VARCHAR(4000))
BEGIN
  DECLARE order_v_v_id INT;
  DECLARE order_v_v_fname VARCHAR(30);
DECLARE order_v_v_lname VARCHAR(30);
  DECLARE l_orders;

DECLARE orders_cur CURSOR FOR SELECT * FROM nqz_order WHERE o_date = str_to_date(IN_DATE, '%Y-%m-%d');
 
    OPEN  orders_cur;
    FETCH orders_cur BULK COLLECT INTO l_orders;
    CLOSE orders_cur;
    DECLARE indx INT = 1;
    WHILE indx <= l_orders.COUNT DO
        -- SQLINES LICENSE FOR EVALUATION USE ONLY
        SELECT * INTO order_v FROM nqz_visitor WHERE nqz_visitor.v_id = l_orders(indx).v_id;
    CASE WHEN l_orders(indx).sh_id IS NOT NULL THEN
            PUT_LINE(Concat('order amount: ' , ifnull(l_orders(indx), ''))Concat(Ifnull(.o_amount, '') , ', source: show, show id: ' , ifnull(l_orders(indx), ''))Concat(Ifnull(.sh_id, '') ,
          ', visitor id: ' , ifnull(order_v_v_id, '') , ', visitor name: ' , ifnull(order_v_v_fname, '') , ' ' , ifnull(order_v_v_lname, '')));
        ELSIF l_orders(indx).park_id IS NOT NULL THEN
      PUT_LINE(Concat('order amount: ' , ifnull(l_orders(indx), ''))Concat(Ifnull(.o_amount, '') , ', source: parking, parking id: ' , ifnull(l_orders(indx), ''))Concat(Ifnull(.park_id, '') ,
          ', visitor id: ' , ifnull(order_v_v_id, '') , ', visitor name: ' , ifnull(order_v_v_fname, '') , ' ' , ifnull(order_v_v_lname, '')));
        ELSIF l_orders(indx).tkt_id IS NOT NULL THEN
          PUT_LINE(Concat('order amount: ' , ifnull(l_orders(indx), ''))Concat(Ifnull(.o_amount, '') , ', source: ticket, ticket id: ' , ifnull(l_orders(indx), ''))Concat(Ifnull(.tkt_id, '') ,
          ', visitor id: ' , ifnull(order_v_v_id, '') , ', visitor name: ' , ifnull(order_v_v_fname, '') , ' ' , ifnull(order_v_v_lname, '')));
    ELSIF l_orders(indx).st_id IS NOT NULL THEN
          PUT_LINE(Concat('order amount: ' , ifnull(l_orders(indx), ''))Concat(Ifnull(.o_amount, '') , ', source: store, store id: ' , ifnull(l_orders(indx), ''))Concat(Ifnull(.st_id, '') ,
          ', visitor id: ' , ifnull(order_v_v_id, '') , ', visitor name: ' , ifnull(order_v_v_fname, '') , ' ' , ifnull(order_v_v_lname, '')));
        FETCH  INTO;
        END
        CLOSE indx; IF;
    END LOOP;
END date_summary;
//

DELIMITER ;


