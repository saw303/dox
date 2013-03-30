alter table DOX_DOMAIN ADD COLUMN strict boolean not null default 1;

update DOX_DOMAIN SET strict = 0 WHERE shortName IN ('company', 'banks');

