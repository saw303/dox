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

                $log.debug("form items %s", form.length);

                for (var i = 0; i < form.length; i++) {
                    $log.debug("Service: %s", form[i].name);
                }
            }
        };
    }]);