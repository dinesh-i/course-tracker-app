'use strict';

angular.module('coursetrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('course', {
                parent: 'entity',
                url: '/courses',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Courses'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/course/courses.html',
                        controller: 'CourseController'
                    }
                },
                resolve: {
                }
            })
            .state('course.detail', {
                parent: 'entity',
                url: '/course/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Course'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/course/course-detail.html',
                        controller: 'CourseDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Course', function($stateParams, Course) {
                        return Course.get({id : $stateParams.id});
                    }]
                }
            })
            .state('course.new', {
                parent: 'course',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/course/course-dialog.html',
                        controller: 'CourseDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    courseName: null,
                                    author: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('course', null, { reload: true });
                    }, function() {
                        $state.go('course');
                    })
                }]
            })
            .state('course.edit', {
                parent: 'course',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/course/course-dialog.html',
                        controller: 'CourseDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Course', function(Course) {
                                return Course.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('course', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('course.delete', {
                parent: 'course',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/course/course-delete-dialog.html',
                        controller: 'CourseDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Course', function(Course) {
                                return Course.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('course', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
