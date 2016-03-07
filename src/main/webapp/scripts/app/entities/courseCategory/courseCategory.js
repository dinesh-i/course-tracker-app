'use strict';

angular.module('coursetrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courseCategory', {
                parent: 'entity',
                url: '/courseCategorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'CourseCategorys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courseCategory/courseCategorys.html',
                        controller: 'CourseCategoryController'
                    }
                },
                resolve: {
                }
            })
            .state('courseCategory.detail', {
                parent: 'entity',
                url: '/courseCategory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'CourseCategory'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courseCategory/courseCategory-detail.html',
                        controller: 'CourseCategoryDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'CourseCategory', function($stateParams, CourseCategory) {
                        return CourseCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('courseCategory.new', {
                parent: 'courseCategory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/courseCategory/courseCategory-dialog.html',
                        controller: 'CourseCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    courseCategoryName: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('courseCategory', null, { reload: true });
                    }, function() {
                        $state.go('courseCategory');
                    })
                }]
            })
            .state('courseCategory.edit', {
                parent: 'courseCategory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/courseCategory/courseCategory-dialog.html',
                        controller: 'CourseCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CourseCategory', function(CourseCategory) {
                                return CourseCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('courseCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('courseCategory.delete', {
                parent: 'courseCategory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/courseCategory/courseCategory-delete-dialog.html',
                        controller: 'CourseCategoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CourseCategory', function(CourseCategory) {
                                return CourseCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('courseCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
