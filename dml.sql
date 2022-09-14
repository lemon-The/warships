INSERT INTO battles VALUES('The Battle of La Plata', '1939-12-13');
INSERT INTO battles VALUES('Attack on Pearl Harbor', '1941-12-07');

INSERT INTO countries VALUES('Britain', 'ALLIES');
INSERT INTO countries VALUES('USSR', 'ALLIES');
INSERT INTO countries VALUES('USA', 'ALLIES');
INSERT INTO countries VALUES('Germany', 'FASCISTS');
INSERT INTO countries VALUES('Japan', 'FASCISTS');

INSERT INTO warships VALUES('Admiral Graf Spee', 'Germany',
    'CRUISER', '1936-01-06', '1939-12-17');
INSERT INTO warships VALUES('HMS Ajax', 'Britain',
    'CRUISER', '1934-03-01', '1948-02-01');--damaged
INSERT INTO warships VALUES('HMS Achillies', 'Britain',
    'CRUISER', '1932-09-01', '1946-10-01');
INSERT INTO warships VALUES('HMS Exeter', 'Britain',
    'CRUISER', '1929-07-18', '1942-03-01');--damaged

INSERT INTO warships VALUES('Arizona', 'USA',
    'BATTLESHIP', '1915-06-19', '1941-12-07');
INSERT INTO warships VALUES('Oklahoma', 'USA',
    'BATTLESHIP', '1914-03-23', '1944-09-01');
INSERT INTO warships VALUES('West Virginia', 'USA',
    'BATTLESHIP', '1921-11-17', '1947-01-09');
INSERT INTO warships VALUES('California', 'USA',
    'BATTLESHIP', '1919-11-20', '1947-02-14');
INSERT INTO warships VALUES('Nevada', 'USA',
    'BATTLESHIP', '1914-07-11', '1946-08-28');
INSERT INTO warships VALUES('Shaw', 'USA',
    'DESTROYER', '1935-10-28', '1945-10-02');
INSERT INTO warships VALUES('Maryland', 'USA',
    'BATTLESHIP', '1920-03-20', '1947-04-03');--damaged
INSERT INTO warships VALUES('Tennessee', 'USA',
    'BATTLESHIP', '1919-03-30', '1947-02-14');--damaged
INSERT INTO warships VALUES('Pannsylvania', 'USA',
    'BATTLESHIP', '1915-03-16', '1946-08-29');--damaged
INSERT INTO warships VALUES('Helena', 'USA',
    'CRUISER', '1939-12-14', '1943-07-06');--damaged
INSERT INTO warships VALUES('IJN Akagi', 'Japan',
    'AIRCRAFT_CARRIER', '1925-04-22', '1942-06-05');
INSERT INTO warships VALUES('IJN Kaga', 'Japan',
    'AIRCRAFT_CARRIER', '1921-11-17', '1942-06-04');
INSERT INTO warships VALUES('IJN Shokaku', 'Japan',
    'AIRCRAFT_CARRIER', '1939-06-01', '1944-06-19');

INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'The Battle of La Plata', 'Admiral Graf Spee', 'SURVIVED');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'The Battle of La Plata',
    'HMS Ajax', 'DAMAGED');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'The Battle of La Plata',
    'HMS Achillies', 'SURVIVED');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'The Battle of La Plata',
    'HMS Exeter', 'DAMAGED');

INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'Attack on Pearl Harbor',
    'Arizona', 'SUNK');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'Attack on Pearl Harbor',
    'Oklahoma', 'SUNK');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'Attack on Pearl Harbor',
    'West Virginia', 'SUNK');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'Attack on Pearl Harbor',
    'California', 'SUNK');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'Attack on Pearl Harbor',
    'Nevada', 'SUNK');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'Attack on Pearl Harbor',
    'Shaw', 'SUNK');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'Attack on Pearl Harbor',
    'Maryland', 'SUNK');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'Attack on Pearl Harbor',
    'Tennessee', 'DAMAGED');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'Attack on Pearl Harbor',
    'Pannsylvania', 'DAMAGED');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'Attack on Pearl Harbor',
    'Helena', 'DAMAGED');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'Attack on Pearl Harbor',
    'IJN Kaga', 'SURVIVED');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'Attack on Pearl Harbor',
    'IJN Akagi', 'SURVIVED');
INSERT INTO battle_members VALUES(nextval('battle_members_sequence'),
    'Attack on Pearl Harbor',
    'IJN Shokaku', 'SURVIVED');
