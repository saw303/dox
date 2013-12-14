angular.module('dox.services', ['ngResource'])

    .factory('Settings', ['$resource', function ($resource) {
        return $resource('/api/v1/settings/:settingId', {settingId: '@id'});
    }])

    .factory('DocumentClasses', ['$resource', function ($resource) {
        return $resource('/api/v1/documentClass/:documentClassId', {documentClassId: '@id'})
    }])

    .factory('UploadService', ['$log' , function ($log) {

        return {
            upload: function(files, data) {

                data.forEach(function(key) {
                   $log.debug("Service: %s", key);
                });
            }
        };
    }]);