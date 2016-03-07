'use strict';

describe('Controller Tests', function() {

    describe('CourseCategory Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCourseCategory, MockCourse;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCourseCategory = jasmine.createSpy('MockCourseCategory');
            MockCourse = jasmine.createSpy('MockCourse');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CourseCategory': MockCourseCategory,
                'Course': MockCourse
            };
            createController = function() {
                $injector.get('$controller')("CourseCategoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coursetrackerApp:courseCategoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
