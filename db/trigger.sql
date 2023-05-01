-- SQLINES LICENSE FOR EVALUATION USE ONLY
DROP TRIGGER IF EXISTS add_new_order;

DELIMITER //

CREATE TRIGGER add_new_order
BEFORE INSERT
    ON nqz_order
    FOR EACH ROW
BEGIN
    DECLARE age_c DOUBLE;
    DECLARE sh_price_c DOUBLE DEFAULT 0;
    DECLARE mi_unit_price_c DOUBLE  DEFAULT 0;
    DECLARE num_purchased_c DOUBLE  DEFAULT 0;
    DECLARE tkt_type_c VARCHAR(10);
    DECLARE discount_c DOUBLE DEFAULT 0;
    DECLARE ticket_price_c DOUBLE DEFAULT 0; 
    DECLARE tkttype_id_c DOUBLE;
    DECLARE visitor_c nqz_visitor%ROWTYPE;

    -- SQLINES DEMO *** sitor
SELECT 
    *
INTO visitor_c FROM
    nqz_visitor
WHERE
    v_id = new.v_id;
    -- SQLINES DEMO *** m birth date
    SET age_c = FLOOR(MONTHS_BETWEEN(new.o_date, visitor_c_v_bdate) /12);

    -- SQLINES DEMO *** gn keys are not null
    -- show order
    IF (NEW.SH_ID IS NOT NULL) THEN
        -- SQLINES DEMO *** arger than 7
        IF (age_c > 7) THEN
            -- SQLINES DEMO ***  to current order
            -- SQLINES LICENSE FOR EVALUATION USE ONLY
            SELECT sh_price INTO sh_price_c FROM nqz_show WHERE sh_id = new.sh_id;
        END IF;
        -- SQLINES DEMO *** _amount
        SET new.o_amount = sh_price_c;
        SET new.o_quantity = 1;
    END IF;

    -- store order
    IF (new.st_id IS NOT NULL AND new.mi_id IS NOT NULL) THEN
        -- SQLINES LICENSE FOR EVALUATION USE ONLY
        SELECT mi_unit_price INTO mi_unit_price_c FROM nqz_menu_item WHERE mi_id = new.mi_id;
        -- SQLINES DEMO *** rder amount
        SET new.o_amount = mi_unit_price_c * new.o_quantity;
        SET mi_unit_price_c =0;
    END IF;

    -- ticket order
    IF (new.tkt_id IS NOT NULL) THEN
        -- SQLINES DEMO ***  count from db or set zero
        IF (visitor_c_v_type = 'M') THEN
            -- SQLINES LICENSE FOR EVALUATION USE ONLY
            SELECT m_num_purchased INTO num_purchased_c FROM nqz_member WHERE v_id = visitor_c_v_id;
        ELSE
            SET num_purchased_c = 0;
        END IF;

        -- SQLINES DEMO ***  citizen and Child discount
        IF (age_c <= 7) THEN
            SET discount_c = 15;
            SET tkt_type_c = 'child';
        ELSEIF (age_c >= 60) THEN
            SET discount_c = 15;
            SET tkt_type_c = 'senior';
        ELSE
            SET discount_c = 0;
            SET tkt_type_c = 'adult';
        END IF;

        -- SQLINES DEMO *** cket
SELECT 
    *
INTO ticket_c FROM
    nqz_ticket
WHERE
    tkt_id = new.tkt_id;

        -- SQLINES DEMO *** nline or not
        IF ticket_c_tkt_online = '1' THEN
            SET discount_c = discount_c + 5;
        END IF;

        -- SQLINES DEMO *** discount
        IF visitor_c_v_type = 'M' AND num_purchased_c < 5 THEN
            SET discount_c = discount_c + 10;
        END IF;

        -- SQLINES DEMO *** holiday and no of tickets bought
        IF (MOD(TO_CHAR(ticket_c_tkt_visit_date, 'J'), 7) + 1 IN (6, 7) OR
            DATE_FORMAT(ticket_c_tkt_visit_date, '%m%d') IN (DATE_FORMAT(STR_TO_DATE('2022-12-24', '%Y-%m-%d'), '%m%d'), DATE_FORMAT(STR_TO_DATE('2022-12-25', '%Y-%m-%d'), '%m%d'))) THEN
            SET discount_c = 0;
        END IF;

        -- SQLINES DEMO *** t to the ticket price
        SET ticket_price_c = ticket_c_tkt_price;
        SET ticket_price_c = ticket_price_c - (ticket_price_c * discount_c / 100);
        -- SQLINES DEMO *** and updated price for the new ticket
SELECT 
    tkttype_id
INTO tkttype_id_c FROM
    nqz_tkt_type
WHERE
    tkttype_name = tkt_type_c;
UPDATE nqz_ticket 
SET 
    tkt_discount = discount_c
WHERE
    nqz_ticket.tkt_id = new.tkt_id;
UPDATE nqz_ticket 
SET 
    tkttype_id = tkttype_id_c
WHERE
    nqz_ticket.tkt_id = new.tkt_id;

        -- SQLINES DEMO *** to O_AMOUNT and set O_QUANTITY to 1
        SET new.o_amount = ticket_price_c;
        SET new.o_quantity = 1;

        SET num_purchased_c = 0;
        SET discount_c = 0;
        SET ticket_price_c = 0;
    END IF;

    -- parking order
    IF new.park_id IS NOT NULL THEN
        -- SQLINES LICENSE FOR EVALUATION USE ONLY
        SELECT * INTO parking_c FROM nqz_parking WHERE park_id = new.park_id;
        -- SQLINES DEMO ***  fee
        SET new.o_amount = parking_c_park_fee * (24 *(parking_c_park_time_out - parking_c_park_time_in));
        SET new.o_quantity = 1;
    END IF;
END;
//

DELIMITER ;


