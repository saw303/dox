angular.module('dox.controllers', ['dox.services'])

    .controller('SettingsController', ['$scope', '$log', 'Settings', function ($scope, $log, Settings) {
        Settings.query(function (settings) {
            $scope.settings = settings;
            $log.debug('Retrieved ' + $scope.settings.length + ' settings for current user');
        });

        $scope.save = function () {

            angular.forEach($scope.settings, function (setting) {
                $log.debug('About to save \'%s\' and setting \'%s\'', setting.key, setting.value);
                setting.$save();
            });
        };
    }])

    .controller('ImportController', ['$scope', '$log', 'DocumentClasses', function ($scope, $log, DocumentClasses) {

        $scope.documentClass;

        $scope.isValid = function () {
            return $scope.form.$dirty && $scope.form.$valid
        }

        $scope.doUpload = function() {
            $log.debug("Starting upload");
        }

        DocumentClasses.query(function (docClasses) {

            angular.forEach(docClasses, function (docClass) {
                $log.debug("Document class '%s' with shortname '%s'", docClass.translation, docClass.shortName);
            })

            $scope.documentClasses = docClasses;
        })
    }])

    .controller('QueryController', ['$scope', '$log', '$http', 'Settings', function ($scope, $log, $http, Settings) {

        $scope.query = '';
        $scope.useWildcard = false;
        $scope.findOnlyMyDocuments = false;
        $scope.documents = [];

        var executed = false;

        Settings.query(function (settings) {

            angular.forEach(settings, function (setting, index) {
                $log.debug('index %d: %s = %s', index, setting.key, setting.value);
                if (setting.key == 'wq') $scope.useWildcard = (setting.value == 1);
                if (setting.key == 'fomd') $scope.findOnlyMyDocuments = (setting.value == 1);
            });
        });

        $scope.doQuery = function () {

            var promise = $http.get('/api/v1/document?q=' + $scope.query + '&wc=' + $scope.useWildcard + '&uo=' + $scope.findOnlyMyDocuments);

            promise.then(function (response) {
                executed = true;
                $scope.documents = response.data;
            }, function (response) {
                $log.error('Something went wrong');
            });

            $log.info('submit query %s', $scope.query);
        }

        $scope.isEmptyResult = function () {
            return $scope.documents.length == 0 && executed;
        }

        $scope.deleteDocument = function (document, idx) {
            var promise = $http.delete('/api/v1/document/' + document.id);

            promise.then(function (response) {
                $scope.documents.splice(idx, 1);
                $log.info('Document %n successfully deleted', document.id);
            }, function (response) {
                $log.error('Something went wrong. Http status code %s', response.status);
            });
        }
    }])
