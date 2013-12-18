angular.module('dox.services', ['ngResource'])

    .factory('Settings', ['$resource', function ($resource) {
        return $resource('/api/v1/settings/:settingId', {settingId: '@id'});
    }])

    .factory('DocumentClasses', ['$resource', function ($resource) {
        return $resource('/api/v1/documentClass/:documentClassId', {documentClassId: '@id'})
    }])

    .factory('UploadService', ['$log' , function ($log) {

        return {
            upload: function(formData, success, error) {

                var xhr = new XMLHttpRequest();
                xhr.open('POST', '/performImport.html', true);

                xhr.onload = function() {

                    $log.debug("Upload returns status %s", this.status);

                    if (this.status == 201) {
                        $log.debug('Server got:', this.response);
                        if (success) success.call();
                    }
                    else {
                        if (error) error.call(this);
                    }
                };

                xhr.send(formData);
            }
        };
    }]);