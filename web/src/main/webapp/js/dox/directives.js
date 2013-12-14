angular.module('dox.directives', [])

    .directive('uploadForm', ['$log', function ($log) {
        return {
            restrict: 'E',
            template: function() {

                var html = '<input name="documentClassShortName" type="hidden" value="{{documentClass.shortName}}"/>';

                /*documentClass.attributes.forEach(function(attribute) {
                    $log.debug('Processing attribute %s', attribute.shortName)
                });*/

                return html
            },
            replace: true,
            scope: {
                documentClass: '=documentClass'
            }
        };
    }]);