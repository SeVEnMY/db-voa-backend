-- SQLINES DEMO *** QZ_GROUP
ALTER TABLE NQZ_GROUP ADD CONSTRAINT CHK_GROUP_SIZE CHECK (G_SIZE > 1); 

-- SQLINES DEMO *** QZ_MEMBER
ALTER TABLE NQZ_MEMBER ADD CONSTRAINT CHK_MEMBER_DATES CHECK (M_STARTDATE < M_ENDDATE);
ALTER TABLE NQZ_MEMBER ADD CONSTRAINT CHK_M_NUM_PURCHASED CHECK (M_NUM_PURCHASED >= 0);

-- SQLINES DEMO *** QZ_INDIVIDUAL
ALTER TABLE NQZ_INDIVIDUAL ADD CONSTRAINT CHK_I_TIMES_VISIT CHECK (I_TIMES_VISIT >= 0);

ALTER TABLE NQZ_MENU_ITEM ADD CONSTRAINT CHK_MENU_ITEM_PRICE CHECK (MI_UNIT_PRICE > 0);

ALTER TABLE NQZ_CATEGORY ADD CONSTRAINT CHK_CATEGORY_NAME CHECK (CTG_NAME IN ('Food stall', 'Ice cream parlor', 'Restaurant', 'Gift shop', 'Apparels'));

ALTER TABLE NQZ_SH_TYPE ADD CONSTRAINT CHK_SH_TYPE_NAME CHECK (SHTYPE_NAME IN ('drama', 'musical', 'comedy', 'horror', 'adventure'));

	-- SQLINES DEMO *** QZ_SHOW
ALTER TABLE NQZ_SHOW ADD CONSTRAINT CHK_SH_TIME CHECK (SH_START_TIME < SH_END_TIME);
ALTER TABLE NQZ_SHOW ADD CONSTRAINT CHK_SH_PRICE CHECK (SH_PRICE >= 0);
ALTER TABLE NQZ_SHOW ADD CONSTRAINT CHK_SHW_WHLCHR CHECK (SH_WHEELCHAIR_ACC IN ('1', '0'));

-- SQLINES DEMO *** QZ_ATTRACTION
ALTER TABLE NQZ_ATTRACTION ADD CONSTRAINT CHK_ATT_CAPACITY CHECK (ATT_CAPACITY >= 0);
ALTER TABLE NQZ_ATTRACTION ADD CONSTRAINT CHK_ATT_STATUS CHECK (ATT_STATUS IN ('open', 'closed', 'under maintenance'));
ALTER TABLE NQZ_ATTRACTION ADD CONSTRAINT CHK_ATT_MINIMUM_HEIGHT CHECK (ATT_MINIMUM_HEIGHT> 0);
ALTER TABLE NQZ_ATTRACTION ADD CONSTRAINT CHK_ATT_DURATION_TIME CHECK (ATT_DURATION_TIME > 0);


	-- SQLINES DEMO *** QZ_TKT_TYPE
ALTER TABLE NQZ_TKT_TYPE ADD CONSTRAINT CHK_TKT_TYPE_NAME CHECK (TKTTYPE_NAME IN ('child', 'adult', 'senior', 'member'));
	
	-- SQLINES DEMO *** QZ_TICKET
ALTER TABLE NQZ_TICKET ADD CONSTRAINT CHK_TKT_METHOD CHECK (TKT_ONLINE IN ('1', '0'));
ALTER TABLE NQZ_TICKET ADD CONSTRAINT CHK_TKT_PRICE CHECK (TKT_PRICE >= 0);
ALTER TABLE NQZ_TICKET ADD CONSTRAINT CHK_TKT_DISCOUNT CHECK (TKT_DISCOUNT >= 0);
ALTER TABLE NQZ_TICKET ADD CONSTRAINT CHK_TKT_ISPAID CHECK (TKT_ISPAID IN ('1', '0'));

-- SQLINES DEMO *** QZ_PARKING
ALTER TABLE NQZ_PARKING ADD CONSTRAINT CHK_PARKING_TIME CHECK (PARK_TIME_IN < PARK_TIME_OUT);
ALTER TABLE NQZ_PARKING ADD CONSTRAINT CHK_PARK_FEE CHECK (PARK_FEE >= 0);
ALTER TABLE NQZ_PARKING ADD CONSTRAINT CHK_PARK_SPOTNO CHECK (PARK_SPOTNO > 0);

	-- SQLINES DEMO *** QZ_PAYMENT
ALTER TABLE NQZ_PAYMENT ADD CONSTRAINT CHK_PAYMENT_AMOUNT CHECK (PAY_AMOUNT >= 0);

-- SQLINES DEMO *** QZ_CASH
ALTER TABLE NQZ_CASH ADD CONSTRAINT CHK_CA_CHANGE CHECK (CA_CHANGE >= 0);
	
	-- SQLINES DEMO *** QZ_CREDIT_DEBIT
ALTER TABLE NQZ_CREDIT_DEBIT ADD CONSTRAINT CHK_CD_CREDIT CHECK (CD_CREDIT IN ('1', '0'));
	
	-- SQLINES DEMO *** QZ_ORDER
ALTER TABLE NQZ_ORDER ADD CONSTRAINT CHK_O_QUANTITY CHECK (O_QUANTITY > 0);