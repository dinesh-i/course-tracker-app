'use strict';

describe('Controller Tests', function() {

    describe('CourseProvider Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCourseProvider, MockCourse;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCourseProvider = jasmine.createSpy('MockCourseProvider');
            MockCourse = jasmine.createSpy('MockCourse');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CourseProvider': MockCourseProvider,
                'Course': MockCourse
            };
            createController = function() {
                $injector.get('$controller')("CourseProviderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coursetrackerApp:courseProviderUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
