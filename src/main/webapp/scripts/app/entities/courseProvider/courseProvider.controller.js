'use strict';

angular.module('coursetrackerApp')
    .controller('CourseProviderController', function ($scope, $state, CourseProvider) {

        $scope.courseProviders = [];
        $scope.loadAll = function() {
            CourseProvider.query(function(result) {
               $scope.courseProviders = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.courseProvider = {
                courseProviderName: null,
                id: null
            };
        };
    });
