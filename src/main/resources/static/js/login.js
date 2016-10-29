angular
	.module('loginApp', ['ngMaterial'])
	.config(function($mdThemingProvider, $httpProvider) {
            	    $mdThemingProvider.theme('default').primaryPalette('blue')
	                        .accentPalette('pink');
	    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	})
	.controller('LoginCtrl', function($scope, $http, $window, $location, $q) {

	var authenticate = function(credentials, response, callback) {
				
		var promise = validateCaptcha(response);
		
		promise.then(function(recaptchaOK) {
			if(recaptchaOK){
				var headers = credentials ? {
					authorization : "Basic "
							+ btoa(credentials.username + ":" + credentials.password)
				} : {};
				
				$http.get('user', 
						{ headers : headers 
				}).success(function(data) {
					if (data.name) {
						console.log(data);
						$scope.authenticated = true;
						$scope.user = data.name;
					} else {
						$scope.authenticated = false;
					}
					callback && callback(true);
				}).error(function() {
					$scope.authenticated = false;
					callback && callback();
				});
			} else {
				$scope.recaptcha.fail = true;
				$scope.recaptcha.required = false;
				$scope.recaptcha.invalid = false;
				grecaptcha.reset();
			}
		});
	};
	
	var validateCaptcha = function(response) {
		
		var deferred = $q.defer();
		
		$http.post('recaptcha-'+response).success(function(data) {
			
			if(data.success) {
				deferred.resolve(true);
			} else {
				deferred.resolve(false);
			}
		});
		
		return deferred.promise;
	};
	
	var checklogged = function() {
		
		var headers = {};
		
		$http.get('user', 
				{ headers : headers 
		}).success(function(data) {
			if (data.name) {
				console.log(data);
				$scope.authenticated = true;
				$window.location.href += 'ui/';
			}
		});
	};

	//authenticate();
	checklogged();
	
	$scope.credentials = {};
	
	$scope.recaptcha = { required : false,
						 invalid : false,
						 fail : false};
	$scope.login = function(isValid) {
		
		var response = grecaptcha.getResponse(); 
		
		if (isValid && response) {
			
			authenticate($scope.credentials, response, function() {
				if ($scope.authenticated) {
					$window.location.href += 'ui/';
				} else {
					$scope.error = true;
					$scope.recaptcha.required = false;
					$scope.recaptcha.fail = false;
					$scope.recaptcha.invalid = true;
					grecaptcha.reset();
				}
			});
		} else {
			$scope.recaptcha.required = true;
			$scope.recaptcha.invalid = false;
			$scope.recaptcha.fail = false;
		}
	};
    
});
