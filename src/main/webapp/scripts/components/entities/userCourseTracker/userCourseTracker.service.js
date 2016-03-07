'use strict';

angular.module('coursetrackerApp')
    .factory('UserCourseTracker', function ($resource, DateUtils) {
        return $resource('api/userCourseTrackers/:id', {}, {
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
