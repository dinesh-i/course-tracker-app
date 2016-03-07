'use strict';

describe('Controller Tests', function() {

    describe('UserCourseTracker Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockUserCourseTracker;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockUserCourseTracker = jasmine.createSpy('MockUserCourseTracker');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'UserCourseTracker': MockUserCourseTracker
            };
            createController = function() {
                $injector.get('$controller')("UserCourseTrackerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coursetrackerApp:userCourseTrackerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
