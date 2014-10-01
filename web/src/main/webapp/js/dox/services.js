angular.module('dox.services', ['ngResource'])

    .factory('Settings', ['$resource', 'apiRoot', function ($resource, apiRoot) {
        return $resource(apiRoot + '/api/v1/settings/:settingId', {settingId: '@id'});
    }])

    .factory('DocumentClasses', ['$resource', 'apiRoot', function ($resource, apiRoot) {
        return $resource(apiRoot + '/api/v1/documentClass/:documentClassId', {documentClassId: '@id'})
    }])

        .factory('Document', ['$resource', 'apiRoot', function ($resource, apiRoot) {
        return $resource(apiRoot + '/api/v1/document/:documentId', {documentId: '@id'})
    }])

    .factory('UploadService', ['$log', 'apiRoot', function ($log, apiRoot) {

        return {
            upload: function(formData, success, error) {

                var xhr = new XMLHttpRequest();
                xhr.open('POST', apiRoot + '/performImport.html', true);

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