'use strict';

angular.module('coursetrackerApp')
    .controller('PipelineController', function ($scope, Principal, Course) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        
        $scope.courses = [];
        
        $scope.loadAll = function() {
            Course.query(function(result) {
               $scope.courses = result;
            });
        };
        
        $scope.loadAll();
    });
