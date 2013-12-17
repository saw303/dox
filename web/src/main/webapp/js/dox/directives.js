angular.module('dox.directives', [])

    .directive('doxAttribute', ['$log', function ($log) {
        return {
            restrict: 'E',
            template: '<div><label for="{{name}}">{{label}}: </label><input id="{{name}}" name="document.{{name}}" type="{{type}}"/><div ng-transclude></div></div>',
            replace: true,
            transclude: true,
            scope: {
                type: '=',
                name: '=',
                label: '='
            }
        };
    }]);