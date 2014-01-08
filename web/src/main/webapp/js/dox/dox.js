angular.module('dox', ['dox.controllers', 'ngRoute'])

    .run(['$rootScope', '$templateCache', function ($rootScope, $templateCache) {
        $rootScope.$on('$viewContentLoaded', function () {
            $templateCache.removeAll();
        });
    }])

    .config(['$routeProvider', '$locationProvider', '$provide', function ($routeProvider, $locationProvider, $provide) {

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

        $provide.value('apiRoot', $('#apiRoot').attr('href'));
    }]);
