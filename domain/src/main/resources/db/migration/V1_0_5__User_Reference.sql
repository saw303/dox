ALTER TABLE DOX_DOC ADD COLUMN userReference VARCHAR(25) NULL;

UPDATE DOX_DOC set userReference = 'saw303';
UPDATE DOX_DOC set userReference = 'angela' WHERE id = 1;
UPDATE DOX_DOC set userReference = 'angela' WHERE id = 2;
UPDATE DOX_DOC set userReference = 'angela' WHERE id = 35;
UPDATE DOX_DOC set userReference = 'angela' WHERE id = 57;

ALTER TABLE DOX_DOC MODIFY COLUMN userReference VARCHAR(25) NOT NULL;