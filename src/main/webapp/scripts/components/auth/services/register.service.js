'use strict';

angular.module('coursetrackerApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


