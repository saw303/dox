angular.module('dox', ['ngResource'])

    .controller('SettingsController', function ($scope, Settings) {
        Settings.query(function (settings) {
            $scope.settings = settings;
            console.log('Retrieved ' + $scope.settings.length + ' settings for current user');
        });

        $scope.save = function () {

            angular.forEach($scope.settings, function (setting, index) {
                console.log('About to save \'%s\' and setting \'%s\'', setting.key, setting.value);
                setting.$save();
            });
        };
    })

    .factory('Settings', function ($resource) {
        return $resource('/api/v1/settings/:settingId', {settingId: '@id'});
    });
