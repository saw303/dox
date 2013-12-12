angular.module('dox', ['dox.controllers','ngResource', 'ngRoute'])

    .run(function ($rootScope, $templateCache) {
        $rootScope.$on('$viewContentLoaded', function () {
            $templateCache.removeAll();
        });
    })

    .config(function ($routeProvider, $locationProvider) {

        $routeProvider.when('/', {
            templateUrl: 'partials/query.html'
        });

        $routeProvider.when('/import', {
            templateUrl: 'partials/importDocument.html'
        });

        $routeProvider.otherwise({
            redirectTo: '/'
        });

        $locationProvider.html5Mode(true);
    });
