CREATE TABLE IF NOT EXISTS entries (
id SERIAL PRIMARY KEY,
guestName TEXT NOT NULL,
message TEXT NOT NULL,
stamp TIMESTAMP NOT NULL
);

CREATE OR REPLACE FUNCTION addEntry (userName TEXT, userMessage TEXT) RETURNS void AS $$
BEGIN
INSERT INTO entries (guestName, message, stamp)
VALUES (userName, userMessage, CURRENT_TIMESTAMP(0));
END;
$$ LANGUAGE plpgsql;

SELECT addEntry ('Bartosz', 'Fajneee');
SELECT addEntry('Adam', 'Słucham');
SELECT addEntry('Mikołaj', 'Absolutnie dziady');

CREATE OR REPLACE FUNCTION getEntries () RETURNS TABLE (returnMessage TEXT, returnName TEXT, returnStamp TIMESTAMP) AS $$
BEGIN
RETURN QUERY (SELECT guestName, message, stamp FROM entries);
END;
$$ LANGUAGE plpgsql;