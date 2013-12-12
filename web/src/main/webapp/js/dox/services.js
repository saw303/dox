angular.module('dox.services', ['ngResource'])

    .factory('Settings', ['$resource', function ($resource) {
        return $resource('/api/v1/settings/:settingId', {settingId: '@id'});
    }])

    .factory('DocumentClasses', ['$resource', function ($resource) {
        return $resource('/api/v1/documentClass/:documentClassId', {documentClassId: '@id'})
    }]);