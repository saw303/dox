angular.module('dox', ['dox.controllers', 'ngRoute'])

    .run(function ($rootScope, $templateCache) {
        $rootScope.$on('$viewContentLoaded', function () {
            $templateCache.removeAll();
        });
    })

    .config(function ($routeProvider, $locationProvider) {

        $routeProvider.when('/ui/', {
            templateUrl: '/partials/query.html'
        });

        $routeProvider.when('/ui/import', {
            templateUrl: '/partials/importDocument.html'
        });

        $routeProvider.otherwise({
            redirectTo: '/ui/'
        });

        $locationProvider.html5Mode(true);
    });
