angular.module('dox', ['ngResource'])

    .controller('SettingsController', function ($scope, Settings) {
        Settings.query(function (settings) {
            $scope.settings = settings;
            console.log('Retrieved ' + $scope.settings.length + ' settings for current user');
        });

        $scope.save = function () {

            angular.forEach($scope.settings, function (value, key) {
                console.log('About to save \'' + value.key + '\' and value \'' + value.value + '\'');
                Settings.save(value);
            });
        };
    })

    .factory('Settings', function ($resource) {
        return $resource('/api/v1/settings/:settingId', {settingId: '@id'});
    });