'use strict';

angular.module('coursetrackerApp')
    .controller('CourseController', function ($scope, $state, Course) {

        $scope.courses = [];
        $scope.loadAll = function() {
            Course.query(function(result) {
               $scope.courses = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.course = {
                courseName: null,
                author: null,
                id: null
            };
        };
    });
