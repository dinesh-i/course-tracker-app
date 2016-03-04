'use strict';

describe('Controller Tests', function() {

    describe('Title Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTitle, MockBook, MockVideo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTitle = jasmine.createSpy('MockTitle');
            MockBook = jasmine.createSpy('MockBook');
            MockVideo = jasmine.createSpy('MockVideo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Title': MockTitle,
                'Book': MockBook,
                'Video': MockVideo
            };
            createController = function() {
                $injector.get('$controller')("TitleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coursetrackerApp:titleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
