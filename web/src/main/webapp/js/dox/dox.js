angular.module('dox', ['dox.controllers', 'ngRoute'])

    .run(['$rootScope', '$templateCache', function ($rootScope, $templateCache) {
        $rootScope.$on('$viewContentLoaded', function () {
            $templateCache.removeAll();
        });
    }])

    .config(['$routeProvider', '$locationProvider', '$provide', function ($routeProvider, $locationProvider, $provide) {

        var basePath = angular.element(document.querySelector('#apiRoot')).attr('href');

        $routeProvider.when(basePath + '/', {
            templateUrl: basePath + '/partials/query.html'
        });

        $routeProvider.when(basePath + '/ui/', {
            templateUrl: basePath + '/partials/query.html'
        });

        $routeProvider.when(basePath + '/ui/import', {
            templateUrl: basePath + '/partials/importDocument.html'
        });

        $routeProvider.when(basePath + '/ui/settings', {
            templateUrl: basePath + '/partials/settings.html'
        });

        /*$routeProvider.otherwise({
            redirectTo: basePath + '/ui/'
        });*/

        $locationProvider.html5Mode(true);


        $provide.value('apiRoot', basePath);
    }]);
