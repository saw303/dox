angular.module("dox", ['ngResource'])

    .controller('SettingsController', function ($scope, $resource) {

        $scope.settings = [
            {key: 'hello', value: true, description: 'Ich bin eine Beschreibung'},
            {key: 'hullu', value: false, description: 'Und ich bin eine andere Beschreibung'}
        ];

    });