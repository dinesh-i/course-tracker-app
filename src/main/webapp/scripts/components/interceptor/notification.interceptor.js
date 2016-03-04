 'use strict';

angular.module('coursetrackerApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-coursetrackerApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-coursetrackerApp-params')});
                }
                return response;
            }
        };
    });
