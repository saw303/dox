var app = angular.module('BlankApp', ['ngMaterial']);

app.controller('MyController', ['$scope', '$mdSidenav', function ($scope, $mdSidenav) {

    var ctrl = this;

    ctrl.openLeftMenu = function () {
        $mdSidenav('left').toggle();
    };
}]);

app.controller('DocumentController', ['$scope', '$log', function ($scope, $log) {

    var ctrl = this;

    ctrl.results = [];

    ctrl.clearResults = function () {
        ctrl.results = [];
    };

    ctrl.performSearch = function () {

        $log.debug('Query executed');

        ctrl.results = [{
            "hash": "7c75201a1484608d259d2933009653074c04f1e7ac754f4884b8ea6506621e33",
            "id": 564,
            "pageCount": 2,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "VARIA",
                "client": "wangler",
                "translation": "Varia"
            },
            "indices": {
                "title": {
                    "value": "Meldebestätigung Angela Wangler in Opfikon 2015 mit Selina",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                }
            },
            "fileName": "BRW008092881FD2_002982.pdf",
            "userReference": "saw303",
            "fileSize": 110331,
            "creationDate": "2015-12-23T12:34:57.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "8914721993fc030e5270e96136af4a89f0334d1bf1a5ce6f5403bd6fad165bb0",
            "id": 563,
            "pageCount": 2,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "VARIA",
                "client": "wangler",
                "translation": "Varia"
            },
            "indices": {
                "title": {
                    "value": "Meldebestätigung Silvio Wangler in Opfikon 2015 mit Selina",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                }
            },
            "fileName": "BRW008092881FD2_002984.pdf",
            "userReference": "saw303",
            "fileSize": 108844,
            "creationDate": "2015-12-23T12:34:00.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "89da77c236842ba5b8157e8d3ec5bc85697992256fab24f0f6fc73962dad4207",
            "id": 558,
            "pageCount": 1,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "INVOICE",
                "client": "wangler",
                "translation": "Invoice"
            },
            "indices": {
                "company": {
                    "value": "Dr. Simeon",
                    "attribute": {
                        "shortName": "company",
                        "optional": false,
                        "domain": {
                            "shortName": "company",
                            "values": ["Sunrise", "Swisscom", "Jemako", "Coop Supercard", "Silvaplana Tourismus Information", "GE Money Bank", "Betty Bossi", "Medplace AG Gesundheitszentrum Wallisellen", "Nestlé Nespresso S.A.", "Teleclub", "GitHub", "Silvio & Angela", "AXA Winterthur", "Cablecom", "Elektro Wehrli AG", "Buchhaus.ch - Lüthy Balmer Stocker", "Java User Group Switzerland", "Schubiger Möbel", "Lüthy Balmer Stocker", "Zalando", "KTV Albisrieden", "Energie Opfikon", "Zahnarztpraxis Glattbrugg", "Server4you", "Reinhard Zanin", "Adcubum AG", "Ochsner Hockey AG", "Treuhand Abt AG", "ZSC Supporter", "Brack.ch", "digitec AG", "Fotoschule Baur", "Medialinx AG", "Hockeystock.ch", "Schubi Weine", "Coop Rechtsschutz", "Fleurop", "QoQa Services SA", "Nettoshop.ch", "Hotel Tannhof", "Concordia", "Raiffeisen", "nic.io", "Post CH AG", "Radisson Hotel", "VZ Vermögenszentrum AG", "SWITCH", "Stadt Kloten - Zivilstandsamt", "Billag AG", "Liluca Pronuptia Suisse", "Schild AG", "Syndicom", "Kuoni", "Foto Studio Tre", "Stadt Opfikon", "Helsana", "VSAO", "Abredeversicherung Schlössli", "Acamed Binz", "Allianz", "ifolor", "Medica", "Nespresso", "PC Ostschweiz", "S + R Glattbrugg GmbH", "Garage Leu", "UGM Abo- und Bestellservice", "Herr Zanin ", "Galaxus (Schweiz) AG", "upc cablecom", "Tamedia AG", "Computec Media GmbH", "Gisler Sport Arosa", "Silhoutte Cruises", "saisonküche", "ToysRUs AG", "Buttinette", "Cembra Money Bank AG", "Migros Fitnesspark", "Hatze Lea", "4mybaby AG", "VBS Hobby Service GmbH", "Ackermann", "Nestl? Nespresso S.A.", "Delticom AG", "NOVATREND Services GmbH", "Leserservice Software & Support", "KSW", "FMH", "Kantonspolizei Graub?nden", "Mclinsen.ch GmbH", "Clienia Schl?ssli AG", "Supercard Visa ", "BuxbaumAG", "Prophylaxezentrum Z?rich Nord", "Dr. Maurer", "Dr. Simeon", "Universit?tsspital", "Officina Ford Cecina", "Happy Baby", "Psychcentral ", "Atlentis", "Digitec Galaxus AG", "W?ger", "La Cucina Gew?rze und Tee", "BabyJoe", "Holzpunkt - Woca Holzbodenprodukte", "Zahnmedizin Z?rich Nord Dentalhygiene", "Vertbaudet", "Windeln.ch", "Psychcentral", "Jetbrains", "Dr. Ren? Simeon", "Ursula Sp?rri", "Sendmoments", "Hirslanden", "Kantonales Steueramt Zuerich", "Strassenverkehrsamt Zuerich", "Hawk", "Europcar", "Steueramt der Stadt Opfikon", "Ernst Ruckstuhl AG"],
                            "translation": "Company"
                        },
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Company",
                        "mappingColumn": "S_01"
                    }
                },
                "invoiceAmount": {
                    "value": "CHF 4560",
                    "attribute": {
                        "shortName": "invoiceAmount",
                        "optional": false,
                        "domain": null,
                        "dataType": "CURRENCY",
                        "modifiable": true,
                        "translation": "Invoice amount",
                        "mappingColumn": "C_01"
                    }
                },
                "invoiceDate": {
                    "value": "2015-12-23",
                    "attribute": {
                        "shortName": "invoiceDate",
                        "optional": false,
                        "domain": null,
                        "dataType": "DATE",
                        "modifiable": true,
                        "translation": "Invoice date",
                        "mappingColumn": "LD_01"
                    }
                }
            },
            "fileName": "Simeon-Rechnung-Geburt-Selina-2015-Hirslanden.pdf",
            "userReference": "saw303",
            "fileSize": 87309,
            "creationDate": "2015-12-23T12:29:13.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "6858199448681df6e661e2a8c031c0295804d99f06c9ea629409a58552b3b00d",
            "id": 557,
            "pageCount": 2,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "VARIA",
                "client": "wangler",
                "translation": "Varia"
            },
            "indices": {
                "title": {
                    "value": "Gesundheitsfragen Selina Concordia Tiku 2014",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                }
            },
            "fileName": "Gesundheitsfragen-Selina-Concordia-Tiku.pdf",
            "userReference": "saw303",
            "fileSize": 201850,
            "creationDate": "2015-12-23T12:26:32.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "e2c67c3ef76e46cca189a3fd3df11cb5bbed251b2fb201eac754a473bc771d81",
            "id": 552,
            "pageCount": 2,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "CONTRACTS",
                "client": "wangler",
                "translation": "Contracts"
            },
            "indices": {
                "title": {
                    "value": "Versicherungspolice Concordia Selina Privat 2016",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                },
                "company": {
                    "value": "Concordia",
                    "attribute": {
                        "shortName": "company",
                        "optional": false,
                        "domain": {
                            "shortName": "company",
                            "values": ["Sunrise", "Swisscom", "Jemako", "Coop Supercard", "Silvaplana Tourismus Information", "GE Money Bank", "Betty Bossi", "Medplace AG Gesundheitszentrum Wallisellen", "Nestlé Nespresso S.A.", "Teleclub", "GitHub", "Silvio & Angela", "AXA Winterthur", "Cablecom", "Elektro Wehrli AG", "Buchhaus.ch - Lüthy Balmer Stocker", "Java User Group Switzerland", "Schubiger Möbel", "Lüthy Balmer Stocker", "Zalando", "KTV Albisrieden", "Energie Opfikon", "Zahnarztpraxis Glattbrugg", "Server4you", "Reinhard Zanin", "Adcubum AG", "Ochsner Hockey AG", "Treuhand Abt AG", "ZSC Supporter", "Brack.ch", "digitec AG", "Fotoschule Baur", "Medialinx AG", "Hockeystock.ch", "Schubi Weine", "Coop Rechtsschutz", "Fleurop", "QoQa Services SA", "Nettoshop.ch", "Hotel Tannhof", "Concordia", "Raiffeisen", "nic.io", "Post CH AG", "Radisson Hotel", "VZ Vermögenszentrum AG", "SWITCH", "Stadt Kloten - Zivilstandsamt", "Billag AG", "Liluca Pronuptia Suisse", "Schild AG", "Syndicom", "Kuoni", "Foto Studio Tre", "Stadt Opfikon", "Helsana", "VSAO", "Abredeversicherung Schlössli", "Acamed Binz", "Allianz", "ifolor", "Medica", "Nespresso", "PC Ostschweiz", "S + R Glattbrugg GmbH", "Garage Leu", "UGM Abo- und Bestellservice", "Herr Zanin ", "Galaxus (Schweiz) AG", "upc cablecom", "Tamedia AG", "Computec Media GmbH", "Gisler Sport Arosa", "Silhoutte Cruises", "saisonküche", "ToysRUs AG", "Buttinette", "Cembra Money Bank AG", "Migros Fitnesspark", "Hatze Lea", "4mybaby AG", "VBS Hobby Service GmbH", "Ackermann", "Nestl? Nespresso S.A.", "Delticom AG", "NOVATREND Services GmbH", "Leserservice Software & Support", "KSW", "FMH", "Kantonspolizei Graub?nden", "Mclinsen.ch GmbH", "Clienia Schl?ssli AG", "Supercard Visa ", "BuxbaumAG", "Prophylaxezentrum Z?rich Nord", "Dr. Maurer", "Dr. Simeon", "Universit?tsspital", "Officina Ford Cecina", "Happy Baby", "Psychcentral ", "Atlentis", "Digitec Galaxus AG", "W?ger", "La Cucina Gew?rze und Tee", "BabyJoe", "Holzpunkt - Woca Holzbodenprodukte", "Zahnmedizin Z?rich Nord Dentalhygiene", "Vertbaudet", "Windeln.ch", "Psychcentral", "Jetbrains", "Dr. Ren? Simeon", "Ursula Sp?rri", "Sendmoments", "Hirslanden", "Kantonales Steueramt Zuerich", "Strassenverkehrsamt Zuerich", "Hawk", "Europcar", "Steueramt der Stadt Opfikon", "Ernst Ruckstuhl AG"],
                            "translation": "Company"
                        },
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Company",
                        "mappingColumn": "S_01"
                    }
                }
            },
            "fileName": "Police-Concordia-Selina-Privat-2016.pdf",
            "userReference": "saw303",
            "fileSize": 154335,
            "creationDate": "2015-12-23T12:18:29.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "ca80b20017628f61ec5eef0e7a7904bcd924e0e706e2096952ad71d24a2ebf0f",
            "id": 551,
            "pageCount": 2,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "CONTRACTS",
                "client": "wangler",
                "translation": "Contracts"
            },
            "indices": {
                "title": {
                    "value": "Versicherungspolice Selina 2015 Privat",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                },
                "company": {
                    "value": "Concordia",
                    "attribute": {
                        "shortName": "company",
                        "optional": false,
                        "domain": {
                            "shortName": "company",
                            "values": ["Sunrise", "Swisscom", "Jemako", "Coop Supercard", "Silvaplana Tourismus Information", "GE Money Bank", "Betty Bossi", "Medplace AG Gesundheitszentrum Wallisellen", "Nestlé Nespresso S.A.", "Teleclub", "GitHub", "Silvio & Angela", "AXA Winterthur", "Cablecom", "Elektro Wehrli AG", "Buchhaus.ch - Lüthy Balmer Stocker", "Java User Group Switzerland", "Schubiger Möbel", "Lüthy Balmer Stocker", "Zalando", "KTV Albisrieden", "Energie Opfikon", "Zahnarztpraxis Glattbrugg", "Server4you", "Reinhard Zanin", "Adcubum AG", "Ochsner Hockey AG", "Treuhand Abt AG", "ZSC Supporter", "Brack.ch", "digitec AG", "Fotoschule Baur", "Medialinx AG", "Hockeystock.ch", "Schubi Weine", "Coop Rechtsschutz", "Fleurop", "QoQa Services SA", "Nettoshop.ch", "Hotel Tannhof", "Concordia", "Raiffeisen", "nic.io", "Post CH AG", "Radisson Hotel", "VZ Vermögenszentrum AG", "SWITCH", "Stadt Kloten - Zivilstandsamt", "Billag AG", "Liluca Pronuptia Suisse", "Schild AG", "Syndicom", "Kuoni", "Foto Studio Tre", "Stadt Opfikon", "Helsana", "VSAO", "Abredeversicherung Schlössli", "Acamed Binz", "Allianz", "ifolor", "Medica", "Nespresso", "PC Ostschweiz", "S + R Glattbrugg GmbH", "Garage Leu", "UGM Abo- und Bestellservice", "Herr Zanin ", "Galaxus (Schweiz) AG", "upc cablecom", "Tamedia AG", "Computec Media GmbH", "Gisler Sport Arosa", "Silhoutte Cruises", "saisonküche", "ToysRUs AG", "Buttinette", "Cembra Money Bank AG", "Migros Fitnesspark", "Hatze Lea", "4mybaby AG", "VBS Hobby Service GmbH", "Ackermann", "Nestl? Nespresso S.A.", "Delticom AG", "NOVATREND Services GmbH", "Leserservice Software & Support", "KSW", "FMH", "Kantonspolizei Graub?nden", "Mclinsen.ch GmbH", "Clienia Schl?ssli AG", "Supercard Visa ", "BuxbaumAG", "Prophylaxezentrum Z?rich Nord", "Dr. Maurer", "Dr. Simeon", "Universit?tsspital", "Officina Ford Cecina", "Happy Baby", "Psychcentral ", "Atlentis", "Digitec Galaxus AG", "W?ger", "La Cucina Gew?rze und Tee", "BabyJoe", "Holzpunkt - Woca Holzbodenprodukte", "Zahnmedizin Z?rich Nord Dentalhygiene", "Vertbaudet", "Windeln.ch", "Psychcentral", "Jetbrains", "Dr. Ren? Simeon", "Ursula Sp?rri", "Sendmoments", "Hirslanden", "Kantonales Steueramt Zuerich", "Strassenverkehrsamt Zuerich", "Hawk", "Europcar", "Steueramt der Stadt Opfikon", "Ernst Ruckstuhl AG"],
                            "translation": "Company"
                        },
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Company",
                        "mappingColumn": "S_01"
                    }
                }
            },
            "fileName": "Police-Concordia-Selina-Privat-2015.pdf",
            "userReference": "saw303",
            "fileSize": 154055,
            "creationDate": "2015-12-23T12:17:52.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "23fdccf47fb6205bb721484ba47ee840944b9bcca76f069e8fe29071144a49a0",
            "id": 545,
            "pageCount": 2,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "VARIA",
                "client": "wangler",
                "translation": "Varia"
            },
            "indices": {
                "title": {
                    "value": "Versicherungsdaten von Selina, Concordia",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                }
            },
            "fileName": "VersicherungsdatenSelina-Concordia.pdf",
            "userReference": "angela",
            "fileSize": 115707,
            "creationDate": "2015-11-26T12:30:37.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "79f9cff9d1417c98bbb9a298819d7885f73a77a393fd938d3e11a2f31e80d2fd",
            "id": 541,
            "pageCount": 2,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "VARIA",
                "client": "wangler",
                "translation": "Varia"
            },
            "indices": {
                "title": {
                    "value": "Police Selina allgemein Concordia",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                }
            },
            "fileName": "PoliceAllgSelina-Concordia.pdf",
            "userReference": "angela",
            "fileSize": 150413,
            "creationDate": "2015-11-24T11:40:53.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "91a3f9b3c52c46e1170cc3600f3cb41649919f2dc6882b99856f2bc7eed314a8",
            "id": 537,
            "pageCount": 2,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "VARIA",
                "client": "wangler",
                "translation": "Varia"
            },
            "indices": {
                "title": {
                    "value": "Verwaltungsbeh?rde Opfikon Police Selina 2015",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                }
            },
            "fileName": "BRW008092881FD2_003050.pdf",
            "userReference": "saw303",
            "fileSize": 120232,
            "creationDate": "2015-11-23T19:27:42.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "3dd56e2131d1549cbcf0e04355f09c2e1218a906c1e02d9052e0a3cf2e8cde9f",
            "id": 533,
            "pageCount": 2,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "CONTRACTS",
                "client": "wangler",
                "translation": "Contracts"
            },
            "indices": {
                "title": {
                    "value": "Versicherungspolice Selina 2015",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                },
                "company": {
                    "value": "Concordia",
                    "attribute": {
                        "shortName": "company",
                        "optional": false,
                        "domain": {
                            "shortName": "company",
                            "values": ["Sunrise", "Swisscom", "Jemako", "Coop Supercard", "Silvaplana Tourismus Information", "GE Money Bank", "Betty Bossi", "Medplace AG Gesundheitszentrum Wallisellen", "Nestlé Nespresso S.A.", "Teleclub", "GitHub", "Silvio & Angela", "AXA Winterthur", "Cablecom", "Elektro Wehrli AG", "Buchhaus.ch - Lüthy Balmer Stocker", "Java User Group Switzerland", "Schubiger Möbel", "Lüthy Balmer Stocker", "Zalando", "KTV Albisrieden", "Energie Opfikon", "Zahnarztpraxis Glattbrugg", "Server4you", "Reinhard Zanin", "Adcubum AG", "Ochsner Hockey AG", "Treuhand Abt AG", "ZSC Supporter", "Brack.ch", "digitec AG", "Fotoschule Baur", "Medialinx AG", "Hockeystock.ch", "Schubi Weine", "Coop Rechtsschutz", "Fleurop", "QoQa Services SA", "Nettoshop.ch", "Hotel Tannhof", "Concordia", "Raiffeisen", "nic.io", "Post CH AG", "Radisson Hotel", "VZ Vermögenszentrum AG", "SWITCH", "Stadt Kloten - Zivilstandsamt", "Billag AG", "Liluca Pronuptia Suisse", "Schild AG", "Syndicom", "Kuoni", "Foto Studio Tre", "Stadt Opfikon", "Helsana", "VSAO", "Abredeversicherung Schlössli", "Acamed Binz", "Allianz", "ifolor", "Medica", "Nespresso", "PC Ostschweiz", "S + R Glattbrugg GmbH", "Garage Leu", "UGM Abo- und Bestellservice", "Herr Zanin ", "Galaxus (Schweiz) AG", "upc cablecom", "Tamedia AG", "Computec Media GmbH", "Gisler Sport Arosa", "Silhoutte Cruises", "saisonküche", "ToysRUs AG", "Buttinette", "Cembra Money Bank AG", "Migros Fitnesspark", "Hatze Lea", "4mybaby AG", "VBS Hobby Service GmbH", "Ackermann", "Nestl? Nespresso S.A.", "Delticom AG", "NOVATREND Services GmbH", "Leserservice Software & Support", "KSW", "FMH", "Kantonspolizei Graub?nden", "Mclinsen.ch GmbH", "Clienia Schl?ssli AG", "Supercard Visa ", "BuxbaumAG", "Prophylaxezentrum Z?rich Nord", "Dr. Maurer", "Dr. Simeon", "Universit?tsspital", "Officina Ford Cecina", "Happy Baby", "Psychcentral ", "Atlentis", "Digitec Galaxus AG", "W?ger", "La Cucina Gew?rze und Tee", "BabyJoe", "Holzpunkt - Woca Holzbodenprodukte", "Zahnmedizin Z?rich Nord Dentalhygiene", "Vertbaudet", "Windeln.ch", "Psychcentral", "Jetbrains", "Dr. Ren? Simeon", "Ursula Sp?rri", "Sendmoments", "Hirslanden", "Kantonales Steueramt Zuerich", "Strassenverkehrsamt Zuerich", "Hawk", "Europcar", "Steueramt der Stadt Opfikon", "Ernst Ruckstuhl AG"],
                            "translation": "Company"
                        },
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Company",
                        "mappingColumn": "S_01"
                    }
                }
            },
            "fileName": "Versicherungspolice-Selina-2015-Allgemein.pdf",
            "userReference": "saw303",
            "fileSize": 150457,
            "creationDate": "2015-11-18T18:32:37.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "04484db75616c30ee90527508709b9387a0f68f8924139b4c6a7d716809a90af",
            "id": 532,
            "pageCount": 4,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "VARIA",
                "client": "wangler",
                "translation": "Varia"
            },
            "indices": {
                "title": {
                    "value": "Familienzulagen Selina 2015",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                }
            },
            "fileName": "Anmeldung-Kinderzulagen-Selina-2015.pdf",
            "userReference": "saw303",
            "fileSize": 355168,
            "creationDate": "2015-11-18T18:31:19.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "3a12e04dc9c53e3f83a03659e7351240ecf7631478a49f3a612991245991c812",
            "id": 516,
            "pageCount": 2,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "VARIA",
                "client": "wangler",
                "translation": "Varia"
            },
            "indices": {
                "title": {
                    "value": "Geburtsurkunde Selina Wangler",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                }
            },
            "fileName": "Geburtsurkunde-Selina-Wangler.pdf",
            "userReference": "saw303",
            "fileSize": 224810,
            "creationDate": "2015-10-28T09:20:17.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "738e0119d71f536e50812ce6ed90710b3710a1219d209c3a900468dd36951b24",
            "id": 442,
            "pageCount": 5,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "VARIA",
                "client": "wangler",
                "translation": "Varia"
            },
            "indices": {
                "title": {
                    "value": "Prämienofferte Concordia Selina Privat",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                }
            },
            "fileName": "PrämienofferteBaby-Concordia.pdf",
            "userReference": "angela",
            "fileSize": 310196,
            "creationDate": "2015-08-20T12:53:24.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "5f6a18a92c26be46fc88141b53ada47f30321a6e783056d9c691d48a7c315459",
            "id": 427,
            "pageCount": 2,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "VARIA",
                "client": "wangler",
                "translation": "Varia"
            },
            "indices": {
                "title": {
                    "value": "Antrag Lebensversicherung Tiku Selina",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                }
            },
            "fileName": "AntragLebensversicherungBabyTiku-Concordia.pdf",
            "userReference": "angela",
            "fileSize": 165293,
            "creationDate": "2015-08-20T10:54:39.000Z",
            "client": "wangler",
            "tags": []
        }, {
            "hash": "5b2fbc2fe493c971b2469dc934492dd3ec6cc69718e046bec33e214c2133d288",
            "id": 425,
            "pageCount": 5,
            "mimeType": "application/pdf",
            "documentClass": {
                "shortName": "VARIA",
                "client": "wangler",
                "translation": "Varia"
            },
            "indices": {
                "title": {
                    "value": "Versicherungsantrag Selina Concordia Allgemein",
                    "attribute": {
                        "shortName": "title",
                        "optional": false,
                        "domain": null,
                        "dataType": "STRING",
                        "modifiable": true,
                        "translation": "Document title",
                        "mappingColumn": "S_02"
                    }
                }
            },
            "fileName": "VersicherungsantragBabyAllg-Concordia.pdf",
            "userReference": "angela",
            "fileSize": 422250,
            "creationDate": "2015-08-20T10:53:43.000Z",
            "client": "wangler",
            "tags": []
        }];
    };


}]);
