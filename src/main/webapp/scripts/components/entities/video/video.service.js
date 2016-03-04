'use strict';

angular.module('coursetrackerApp')
    .factory('Video', function ($resource, DateUtils) {
        return $resource('api/videos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
