CREATE TABLE DOX_COUNTRY (
  id           BIGINT       NOT NULL AUTO_INCREMENT,
  code2        VARCHAR(255) NOT NULL,
  code3        VARCHAR(255) NOT NULL,
  name         VARCHAR(255) NOT NULL,
  numericCode3 INTEGER      NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE DOX_COUNTRY
ADD CONSTRAINT UK_p0vpjt9yvbrn0iqlk9df02dt9 UNIQUE (numericCode3, code3);

ALTER TABLE DOX_COUNTRY
ADD CONSTRAINT UK_qg3xf66l3gmde38ofonwm276c UNIQUE (code2, code3);

ALTER TABLE DOX_COUNTRY
ADD CONSTRAINT UK_m8mtlm2pc23e0hk204l4r13r3 UNIQUE (code2);

ALTER TABLE DOX_COUNTRY
ADD CONSTRAINT UK_dm8g1px2ah9paopd2601pa0m4 UNIQUE (code3);

INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AF', 'AFG', 4, 'Afghanistan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AL', 'ALB', 8, 'Albania');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('DZ', 'DZA', 12, 'Algeria');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AS', 'ASM', 16, 'American Samoa');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AD', 'AND', 20, 'Andorra');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AO', 'AGO', 24, 'Angola');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AI', 'AIA', 660, 'Anguilla');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AQ', 'ATA', 10, 'Antarctica');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AG', 'ATG', 28, 'Antigua and Barbuda');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AR', 'ARG', 32, 'Argentina');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AM', 'ARM', 51, 'Armenia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AW', 'ABW', 533, 'Aruba');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AU', 'AUS', 36, 'Australia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AT', 'AUT', 40, 'Austria');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AZ', 'AZE', 31, 'Azerbaijan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BS', 'BHS', 44, 'The Bahamas');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BH', 'BHR', 48, 'Bahrain');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BD', 'BGD', 50, 'Bangladesh');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BB', 'BRB', 52, 'Barbados');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BY', 'BLR', 112, 'Belarus');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BE', 'BEL', 56, 'Belgium');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BZ', 'BLZ', 84, 'Belize');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BJ', 'BEN', 204, 'Benin');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BM', 'BMU', 60, 'Bermuda');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BT', 'BTN', 64, 'Bhutan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BO', 'BOL', 68, 'Bolivia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BA', 'BIH', 70, 'Bosnia and Herzegovina');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BW', 'BWA', 72, 'Botswana');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BV', 'BVT', 74, 'Bouvet Island');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BR', 'BRA', 76, 'Brazil');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('IO', 'IOT', 92, 'British Indian Ocean Territory');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('VG', 'VGB', 92, 'British Virgin Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BN', 'BRN', 96, 'Brunei');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BG', 'BGR', 100, 'Bulgaria');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BF', 'BFA', 854, 'Burkina Faso');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MM', 'MMR', 104, 'Burma');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BI', 'BDI', 108, 'Burundi');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CV', 'CPV', 132, 'Cape Verde');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('KH', 'KHM', 116, 'Cambodia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CM', 'CMR', 120, 'Cameroon');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CA', 'CAN', 124, 'Canada');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('KY', 'CYM', 136, 'Cayman Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CF', 'CAF', 140, 'Central African Republic');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TD', 'TCD', 148, 'Chad');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CL', 'CHL', 152, 'Chile');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CN', 'CHN', 156, 'China');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CX', 'CXR', 162, 'Christmas Island');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CC', 'CCK', 166, 'Cocos (Keeling) Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CO', 'COL', 170, 'Colombia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('KM', 'COM', 174, 'Comoros');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name)
VALUES ('CD', 'COD', 180, 'Democratic Republic of the Congo');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CG', 'COG', 178, 'Congo');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CK', 'COK', 184, 'Cook Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CR', 'CRI', 188, 'Costa Rica');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CI', 'CIV', 384, 'Côte D''ivoire');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('HR', 'HRV', 191, 'Croatia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CU', 'CUB', 192, 'Cuba');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CW', 'CUW', 531, 'Curaçao');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CY', 'CYP', 196, 'Cyprus');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CZ', 'CZE', 203, 'Czech Republic');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('DK', 'DNK', 208, 'Denmark');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('DJ', 'DJI', 262, 'Djibouti');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('DM', 'DMA', 212, 'Dominica');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('DO', 'DOM', 214, 'The Dominican');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('EC', 'ECU', 218, 'Ecuador');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('EG', 'EGY', 818, 'Egypt');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SV', 'SLV', 222, 'El Salvador');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GQ', 'GNQ', 226, 'Equatorial Guinea');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('ER', 'ERI', 232, 'Eritrea');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('EE', 'EST', 233, 'Estonia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('ET', 'ETH', 231, 'Ethiopia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name)
VALUES ('FK', 'FLK', 238, 'Falkland Islands (Islas Malvinas)');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('FO', 'FRO', 234, 'Faroe Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('FJ', 'FJI', 242, 'Fiji');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('FI', 'FIN', 246, 'Finland');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('FR', 'FRA', 250, 'France');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GF', 'GUF', 254, 'French Guiana');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PF', 'PYF', 258, 'French Polynesia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name)
VALUES ('TF', 'ATF', 260, 'French Southern and Antarctic Lands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GA', 'GAB', 266, 'Gabon');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GM', 'GMB', 270, 'Gambia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GE', 'GEO', 268, 'Georgia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('DE', 'DEU', 276, 'Germany');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GH', 'GHA', 288, 'Ghana');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GI', 'GIB', 292, 'Gibraltar');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GR', 'GRC', 300, 'Greece');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GL', 'GRL', 304, 'Greenland');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GD', 'GRD', 308, 'Grenada');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GP', 'GLP', 312, 'Guadeloupe');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GU', 'GUM', 316, 'Guam');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GT', 'GTM', 320, 'Guatemala');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GG', 'GGY', 831, 'Guernsey');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GN', 'GIN', 324, 'Guinea');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GW', 'GNB', 624, 'Guinea-Bissau');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GY', 'GUY', 328, 'Guyana');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('HT', 'HTI', 332, 'Haiti');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name)
VALUES ('HM', 'HMD', 334, 'Heard Island and McDonald Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('VA', 'VAT', 336, 'Vatican City');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('HN', 'HND', 340, 'Honduras');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('HK', 'HKG', 344, 'Hong Kong');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('HU', 'HUN', 348, 'Hungary');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('IN', 'IND', 356, 'India');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('ID', 'IDN', 360, 'Indonesia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('IR', 'IRN', 364, 'Iran');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('IQ', 'IRQ', 368, 'Iraq');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('IE', 'IRL', 372, 'Ireland');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('IM', 'IMN', 833, 'Isle of Man');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('IL', 'ISR', 376, 'Israel');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('IT', 'ITA', 380, 'Italy');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('JM', 'JAM', 388, 'Jamaica');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('JP', 'JPN', 392, 'Japan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('JE', 'JEY', 832, 'Jersey');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('JO', 'JOR', 400, 'Jordan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('KZ', 'KAZ', 398, 'Kazakhstan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('KE', 'KEN', 404, 'Kenya');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('KI', 'KIR', 296, 'Kiribati');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('KP', 'PRK', 408, 'North Korea');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('KR', 'KOR', 410, 'South Korea');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('KW', 'KWT', 414, 'Kuwait');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('KG', 'KGZ', 417, 'Kyrgyzstan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('LA', 'LAO', 418, 'Laos');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('LV', 'LVA', 428, 'Latvia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('LB', 'LBN', 422, 'Lebanon');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('LS', 'LSO', 426, 'Lesotho');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('LR', 'LBR', 430, 'Liberia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('LY', 'LBY', 434, 'Libya');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('LI', 'LIE', 438, 'Liechtenstein');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('LT', 'LTU', 440, 'Lithuania');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('LU', 'LUX', 442, 'Luxembourg');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MO', 'MAC', 446, 'Macau');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MK', 'MKD', 807, 'Macedonia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MG', 'MDG', 450, 'Madagascar');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MW', 'MWI', 454, 'Malawi');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MY', 'MYS', 458, 'Malaysia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MV', 'MDV', 462, 'Maldives');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('ML', 'MLI', 466, 'Mali');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MT', 'MLT', 470, 'Malta');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MH', 'MHL', 584, 'Marshall Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MQ', 'MTQ', 474, 'Martinique');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MR', 'MRT', 478, 'Mauritania');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MU', 'MUS', 480, 'Mauritius');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('YT', 'MYT', 175, 'Mayotte');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MX', 'MEX', 484, 'Mexico');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('FM', 'FSM', 583, 'Federated States of Micronesia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MD', 'MDA', 498, 'Moldova');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MC', 'MCO', 492, 'Monaco');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MN', 'MNG', 496, 'Mongolia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('ME', 'MNE', 499, 'Montenegro');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MS', 'MSR', 500, 'Montserrat');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MA', 'MAR', 504, 'Morocco');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MZ', 'MOZ', 508, 'Mozambique');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('NA', 'NAM', 516, 'Namibia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('NR', 'NRU', 520, 'Nauru');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('NP', 'NPL', 524, 'Nepal');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('NL', 'NLD', 528, 'Netherlands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('NC', 'NCL', 540, 'New Caledonia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('NZ', 'NZL', 554, 'New Zealand');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('NI', 'NIC', 558, 'Nicaragua');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('NE', 'NER', 562, 'Niger');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('NG', 'NGA', 566, 'Nigeria');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('NU', 'NIU', 570, 'Niue');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('NF', 'NFK', 574, 'Norfolk Island');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MP', 'MNP', 580, 'Northern Mariana Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('NO', 'NOR', 578, 'Norway');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('OM', 'OMN', 512, 'Oman');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PK', 'PAK', 586, 'Pakistan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PW', 'PLW', 585, 'Palau');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PA', 'PAN', 591, 'Panama');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PG', 'PNG', 598, 'Papua New Guinea');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PY', 'PRY', 600, 'Paraguay');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PE', 'PER', 604, 'Peru');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PH', 'PHL', 608, 'Philippines');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PN', 'PCN', 612, 'Pitcairn Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PL', 'POL', 616, 'Poland');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PT', 'PRT', 620, 'Portugal');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PR', 'PRI', 630, 'Puerto Rico');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('QA', 'QAT', 634, 'Qatar');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('RE', 'REU', 638, 'Réunion');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('RO', 'ROU', 642, 'Romania');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('RU', 'RUS', 643, 'Russia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('RW', 'RWA', 646, 'Rwanda');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('BL', 'BLM', 652, 'Saint Barthélemy');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SH', 'SHN', 654, 'Saint Helena');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('KN', 'KNA', 659, 'Saint Kitts and Nevis');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('LC', 'LCA', 662, 'Saint Lucia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('MF', 'MAF', 663, 'Saint Martin');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PM', 'SPM', 666, 'Saint Pierre and Miquelon');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name)
VALUES ('VC', 'VCT', 670, 'Saint Vincent and the Grenadines');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('WS', 'WSM', 882, 'Samoa');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SM', 'SMR', 674, 'San Marino');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('ST', 'STP', 678, 'Sao Tome and Principe');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SA', 'SAU', 682, 'Saudi Arabia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SN', 'SEN', 686, 'Senegal');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('RS', 'SRB', 688, 'Serbia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SC', 'SYC', 690, 'Seychelles');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SL', 'SLE', 694, 'Sierra Leone');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SG', 'SGP', 702, 'Singapore');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SX', 'SXM', 534, 'Sint Maarten (Dutch Part)');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SK', 'SVK', 703, 'Slovakia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SI', 'SVN', 705, 'Slovenia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SB', 'SLB', 90, 'Solomon Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SO', 'SOM', 706, 'Somalia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('ZA', 'ZAF', 710, 'South Africa');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name)
VALUES ('GS', 'SGS', 239, 'South Georgia and South Sandwich Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SS', 'SSD', 728, 'South Sudan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('ES', 'ESP', 724, 'Spain');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('LK', 'LKA', 144, 'Sri Lanka');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SD', 'SDN', 729, 'Sudan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SR', 'SUR', 740, 'Suriname');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name)
VALUES ('SJ', 'SJM', 744, 'Svalbard (sometimes referred to as Spitsbergen)');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SZ', 'SWZ', 748, 'Swaziland');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SE', 'SWE', 752, 'Sweden');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('CH', 'CHE', 756, 'Switzerland');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('SY', 'SYR', 760, 'Syria');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TW', 'TWN', 158, 'Taiwan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TJ', 'TJK', 762, 'Tajikistan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TZ', 'TZA', 834, 'Tanzania');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TH', 'THA', 764, 'Thailand');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TL', 'TLS', 626, 'Timor-Leste');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TG', 'TGO', 768, 'Togo');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TK', 'TKL', 772, 'Tokelau');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TO', 'TON', 776, 'Tonga');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TT', 'TTO', 780, 'Trinidad and Tobago');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TN', 'TUN', 788, 'Tunisia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TR', 'TUR', 792, 'Turkey');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TM', 'TKM', 795, 'Turkmenistan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TC', 'TCA', 796, 'Turks and Caicos Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('TV', 'TUV', 798, 'Tuvalu');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('UG', 'UGA', 800, 'Uganda');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('UA', 'UKR', 804, 'Ukraine');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AE', 'ARE', 784, 'United Arab Emirates');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('GB', 'GBR', 826, 'United Kingdom');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('US', 'USA', 840, 'United States');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name)
VALUES ('UM', 'UMI', 581, 'United States Minor Outlying Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('UY', 'URY', 858, 'Uruguay');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('UZ', 'UZB', 860, 'Uzbekistan');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('VU', 'VUT', 548, 'Vanuatu');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('VE', 'VEN', 862, 'Venezuela');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('VN', 'VNM', 704, 'Vietnam');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('VI', 'VIR', 850, 'United States Virgin Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('WF', 'WLF', 876, 'Wallis and Futuna');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('EH', 'ESH', 732, 'Western Sahara');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('YE', 'YEM', 887, 'Yemen');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('ZM', 'ZMB', 894, 'Zambia');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('ZW', 'ZWE', 716, 'Zimbabwe');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('AX', 'ALA', 248, 'Åland Islands');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name)
VALUES ('BQ', 'BES', 535, 'Bonaire, Saint Eustatius and Saba');
INSERT INTO DOX_COUNTRY (code2, code3, numericCode3, name) VALUES ('PS', 'PSE', 275, 'State of Palestine');

CREATE TABLE DOX_ADDRESS (
  id          BIGINT       NOT NULL AUTO_INCREMENT,
  city        VARCHAR(255) NOT NULL,
  houseNumber VARCHAR(255) NOT NULL,
  postCode    VARCHAR(255) NOT NULL,
  street      VARCHAR(255) NOT NULL,
  country_id  BIGINT       NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE DOX_ADDRESS
ADD CONSTRAINT UK_e212vhu69mxbwnbxj5bvw65a UNIQUE (country_id);

ALTER TABLE DOX_ADDRESS
ADD CONSTRAINT FK_e212vhu69mxbwnbxj5bvw65a
FOREIGN KEY (country_id)
REFERENCES DOX_COUNTRY (id);

CREATE TABLE DOX_CORP_PARTNER (
  id         BIGINT       NOT NULL AUTO_INCREMENT,
  email      VARCHAR(255),
  name       VARCHAR(255) NOT NULL,
  address_id BIGINT       NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE DOX_CORP_PARTNER
ADD CONSTRAINT UK_dugu8cm1572qx25nt5phgv7rd UNIQUE (address_id);

ALTER TABLE DOX_CORP_PARTNER
ADD CONSTRAINT FK_dugu8cm1572qx25nt5phgv7rd
FOREIGN KEY (address_id)
REFERENCES DOX_ADDRESS (id);

CREATE TABLE DOX_INDV_PARTNER (
  id          BIGINT       NOT NULL AUTO_INCREMENT,
  email       VARCHAR(255),
  name        VARCHAR(255) NOT NULL,
  dateOfBirth DATE,
  address_id  BIGINT       NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE DOX_INDV_PARTNER
ADD CONSTRAINT UK_b903o5773drnj6ml3sysr3y5m UNIQUE (address_id);

ALTER TABLE DOX_INDV_PARTNER
ADD CONSTRAINT FK_b903o5773drnj6ml3sysr3y5m
FOREIGN KEY (address_id)
REFERENCES DOX_ADDRESS (id);