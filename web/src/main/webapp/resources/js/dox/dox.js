angular.module('dox', ['dox.controllers', 'ngRoute', 'ngMaterial'])

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

        $routeProvider.when(basePath + '/ui/importDone', {
            templateUrl: basePath + '/partials/importDocumentDone.html'
        });

        $routeProvider.when(basePath + '/ui/edit/:id', {
            templateUrl: basePath + '/partials/editDocument.html',
            controller: 'DocumentController'
        });

        $routeProvider.when(basePath + '/ui/settings', {
            templateUrl: basePath + '/partials/settings.html'
        });

        $routeProvider.when(basePath + '/ui/partners', {
            templateUrl: basePath + '/partials/partnerList.html'
        });

        $routeProvider.when(basePath + '/ui/postboxes', {
            templateUrl: basePath + '/partials/postboxes.html'
        });

        /*$routeProvider.otherwise({
            redirectTo: basePath + '/ui/'
        });*/

        $locationProvider.html5Mode(true);


        $provide.value('apiRoot', basePath);
    }]);
