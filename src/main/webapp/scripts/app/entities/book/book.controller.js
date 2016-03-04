'use strict';

angular.module('coursetrackerApp')
    .controller('BookController', function ($scope, $state, Book) {

        $scope.books = [];
        $scope.loadAll = function() {
            Book.query(function(result) {
               $scope.books = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.book = {
                bookName: null,
                author: null,
                isCompleted: null,
                id: null
            };
        };
    });
