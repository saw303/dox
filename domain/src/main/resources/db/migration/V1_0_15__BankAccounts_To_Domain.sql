INSERT INTO DOX_DOMAIN (shortName, strict) VALUES ('bankaccounts', 0);

UPDATE DOX_ATTR
SET domain_id = (SELECT id
                 FROM DOX_DOMAIN
                 WHERE shortName = 'bankaccounts')
WHERE shortName = 'accountNumber';