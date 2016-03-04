'use strict';

describe('Controller Tests', function() {

    describe('Video Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockVideo, MockTitle;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockVideo = jasmine.createSpy('MockVideo');
            MockTitle = jasmine.createSpy('MockTitle');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Video': MockVideo,
                'Title': MockTitle
            };
            createController = function() {
                $injector.get('$controller')("VideoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coursetrackerApp:videoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
