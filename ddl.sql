CREATE TYPE sides AS ENUM('ALLIES', 'FASCISTS');
CREATE TYPE ship_types AS ENUM('BATTLESHIP', 'AIRCRAFT_CARRIER',
    'CRUISER', 'DESTROYER', 'SUBMARINE', 'TRADE_SHIP');
CREATE TYPE ship_condition AS ENUM('SUNK', 'DAMAGED', 'UNHARMED');

CREATE SEQUENCE battle_members_sequence
    START 1
    INCREMENT 1;

CREATE TABLE countries(
    name VARCHAR(50),
    --side CHAR(2),
    --CONSTRAINT sides CHECK(side IN ('ALLIES', 'FASCISTS')),
    side sides,
    CONSTRAINT pk_countries PRIMARY KEY(name)
);

CREATE TABLE warships(
    name VARCHAR(50),
    country VARCHAR(50),
    class ship_types,
    commission_date DATE,
    decommission_date DATE,
    CONSTRAINT pk_warships PRIMARY KEY(name),
    CONSTRAINT fk_warships_country FOREIGN KEY(country)
        REFERENCES countries (name),
    CONSTRAINT w_time_constr
        CHECK (commission_date < decommission_date),
    CONSTRAINT w_period_constr 
        CHECK (commission_date < '1945-09-02' AND 
            decommission_date > '1939-09-01')
);

CREATE TABLE battles(
    battle_name VARCHAR(200),
    battle_date DATE,
    CONSTRAINT pk_battles PRIMARY KEY(battle_name),
    CONSTRAINT b_period_constr
        CHECK (battle_date < '1945-09-02' AND 
            battle_date > '1939-09-01')
);

CREATE TABLE battle_members(
    id INTEGER,
    battle_name VARCHAR(200),
    warship_name VARCHAR(50),
    result ship_condition,
    CONSTRAINT pk_battle_members PRIMARY KEY(id),
    CONSTRAINT fk_battle_name FOREIGN KEY(battle_name)
        REFERENCES battles(battle_name) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_battle_warship FOREIGN KEY(warship_name)
        REFERENCES warships(name) ON DELETE RESTRICT ON UPDATE CASCADE;
);
