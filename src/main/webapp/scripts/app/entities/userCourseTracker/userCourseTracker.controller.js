'use strict';

angular.module('coursetrackerApp')
    .controller('UserCourseTrackerController', function ($scope, $state, UserCourseTracker) {

        $scope.userCourseTrackers = [];
        $scope.loadAll = function() {
            UserCourseTracker.query(function(result) {
               $scope.userCourseTrackers = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.userCourseTracker = {
                id: null
            };
        };
    });
