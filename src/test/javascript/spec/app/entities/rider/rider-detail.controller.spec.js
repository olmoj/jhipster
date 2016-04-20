'use strict';

describe('Controller Tests', function() {

    describe('Rider Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRider, MockEquidae, MockUser, MockLesson;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRider = jasmine.createSpy('MockRider');
            MockEquidae = jasmine.createSpy('MockEquidae');
            MockUser = jasmine.createSpy('MockUser');
            MockLesson = jasmine.createSpy('MockLesson');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Rider': MockRider,
                'Equidae': MockEquidae,
                'User': MockUser,
                'Lesson': MockLesson
            };
            createController = function() {
                $injector.get('$controller')("RiderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'myappApp:riderUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
