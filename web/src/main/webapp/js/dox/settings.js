angular.module("dox", ['ngResource'])

    .controller('SettingsController', function ($scope, Settings) {
        $scope.settings = Settings.query();

        $scope.save = function () {
            Settings.save();
        };
    })

    .factory('Settings', function ($resource) {
        return $resource('/api/v1/settings/:settingId', {settingId: '@id'});
    });