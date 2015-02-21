angular.module('dox.controllers', ['dox.services'])

    .controller('DocumentClassController', ['$scope', '$log', 'DocumentClasses', function ($scope, $log, DocumentClasses) {

        DocumentClasses.query(function (docClasses) {
            $log.debug('Found %s document classes', docClasses.length)
            $scope.documentClasses = docClasses;
        });
    }])

    .controller('SettingsController', ['$scope', '$log', 'Settings', function ($scope, $log, Settings) {
        Settings.query(function (settings) {
            $scope.settings = settings;
            $log.debug('Retrieved %s settings for current user', settings.length);
        });

        $scope.save = function () {

            angular.forEach($scope.settings, function (setting) {
                $log.debug('About to save \'%s\' and setting \'%s\'', setting.key, setting.value);
                setting.$save();
            });
        };
    }])

    .controller('ImportController', ['$scope', '$log', 'DocumentClasses', 'UploadService', '$window', function ($scope, $log, DocumentClasses, UploadService, $window) {

        $scope.documentClass;

        $scope.message = {
            visible: false,
            value: ''
        };

        $scope.isReadyToSubmit = function () {

            var result = $scope.form.$dirty && $scope.form.$valid;
            $log.debug("Import form is ready to submit? %s", result);
            return result;
        }

        var successCallback = function () {
            form.reset();
            $window.location.href = '/ui/importDone';
        }

        var errorCallback = function () {
            $scope.message.visible = true;
            $scope.message.value = this.responseText;
            $scope.$apply(); // otherwise it does not change the view
        }

        $scope.doUpload = function () {
            $log.debug("Starting upload");

            var formData = new FormData();

            for (var i = 0; i < form.length; i++) {
                if (form[i].nodeName == 'INPUT' || form[i].nodeName == 'SELECT') {
                    $log.debug('Appending input field %s to form data', form[i].name);

                    if (form[i].type == 'file') {
                        formData.append(form[i].name, form[i].files[0]);
                    }
                    else {
                        formData.append(form[i].name, form[i].value);
                    }
                }
            }

            UploadService.upload(formData, successCallback, errorCallback);
        }

        DocumentClasses.query(function (docClasses) {

            angular.forEach(docClasses, function (docClass) {
                $log.debug("Document class '%s' with shortname '%s'", docClass.translation, docClass.shortName);
            })

            $scope.documentClasses = docClasses;
        })
    }])

    .controller('DocumentController', ['$scope', '$log', '$routeParams', 'Document', function ($scope, $log, $routeParams, Document) {

        var messageVisible = false;

        $scope.displayMessage = function () {
            return messageVisible;
        }

        Document.get({documentId: $routeParams.id}, function (doc) {
            $scope.document = doc;
        });

        $scope.doSubmit = function () {
            messageVisible = false;
            $log.debug("Updating document %s", $routeParams.id);
            $scope.document.$save();
            $log.debug("Done updating document %s", $routeParams.id);
            messageVisible = true;
        }
    }])

    .controller('LogoutController', ['$scope', '$log', '$http', '$window', 'apiRoot', function ($scope, $log, $http, $window, apiRoot) {

        $scope.logout = function () {
            $log.info('Logging out. Good bye');

            var promise = $http.get(apiRoot + '/logout');

            promise.then(function () {
                $log.debug('Logout successful');
                $window.location.href = apiRoot + '/';
            }, function (data, status, headers, config) {
                $log.error('Error while log out attempt. Status: %s', status);
            });
        }
    }])

    .controller('PartnerController', ['$scope', '$log', function ($scope, $log) {

        $scope.partners = [
            {name: 'Silvio Wangler'},
            {name: 'Angela Wangler'},
            {name: 'Daniela Dolder'}
        ];
    }])

    .controller('QueryController', ['$scope', '$log', '$http', 'Settings', 'apiRoot', '$window', function ($scope, $log, $http, Settings, apiRoot, $window) {

        $scope.query = '';
        $scope.useWildcard = false;
        $scope.findOnlyMyDocuments = false;
        $scope.documents = [];
        $scope.showSpinner = false;

        var executed = false;

        Settings.query(function (settings) {

            angular.forEach(settings, function (setting, index) {
                $log.debug('index %d: %s = %s', index, setting.key, setting.value);
                if (setting.key == 'wq') $scope.useWildcard = (setting.value == 1);
                if (setting.key == 'fomd') $scope.findOnlyMyDocuments = (setting.value == 1);
            });
        });

        $scope.doQuery = function () {

            $scope.showSpinner = true;

            var promise = $http.get(apiRoot + '/api/v1/document?q=' + $scope.query + '&wc=' + $scope.useWildcard + '&uo=' + $scope.findOnlyMyDocuments);

            promise.then(function (response) {
                executed = true;
                $scope.documents = response.data;
                $scope.showSpinner = false;
            }, function (response) {
                $log.error('Something went wrong');
                $scope.showSpinner = false;
            });

            $log.info('submit query %s', $scope.query);
        }

        $scope.isEmptyResult = function () {
            return $scope.documents.length == 0 && executed;
        }

        $scope.deleteDocument = function (document, idx) {
            var promise = $http.delete(apiRoot + '/api/v1/document/' + document.id);

            promise.then(function (response) {
                $scope.documents.splice(idx, 1);
                $log.info('Document %n successfully deleted', document.id);
            }, function (response) {
                $log.error('Something went wrong. Http status code %s', response.status);
            });
        }

        $scope.showDocument = function (document) {
            $log.debug("About to open document %s in a separate window", document.id);
            $window.open(apiRoot + '/document/' + document.id, 'docViewer', "location=no,status=no,menubar=no");
        }

        $scope.retrieveExtension = function (document) {
            if (document.mimeType.indexOf('msword') > -1) return 'doc.png';
            else if (document.mimeType == 'application/vnd.openxmlformats-officedocument.wordprocessingml.document') return 'docx.png';
            else if (document.mimeType.indexOf('tiff') > -1) return 'tif.png';
            return 'pdf.png';
        }
    }]);
